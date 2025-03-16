package com.file_jy.domain.storage.file.service;

import com.file_jy.domain.storage.entity.Storage;
import com.file_jy.domain.storage.entity.component.StorageType;
import com.file_jy.domain.storage.file.api.dto.StorageFileDownloadResponse;
import com.file_jy.domain.storage.file.api.dto.StorageFileListResponse;
import com.file_jy.domain.storage.file.entity.StorageFile;
import com.file_jy.domain.storage.file.repository.StorageFileRepository;
import com.file_jy.domain.storage.file.repository.StorageFileRepository.StorageFileFetch;
import com.file_jy.domain.storage.repository.StorageRepository;
import com.file_jy.domain.user.entity.User;
import com.file_jy.domain.user.repository.UserRepository;
import com.file_jy.global.error.errorcode.StorageFileErrorCode;
import com.file_jy.global.error.exception.business.BusinessException;
import com.file_jy.global.error.exception.common.UncoveredEnumCaseException;
import com.file_jy.global.util.FileManager;
import com.file_jy.global.util.FileManager.FilePrefix;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class StorageFileService {

    private final StorageFileRepository storageFileRepository;
    private final StorageRepository storageRepository;
    private final UserRepository userRepository;
    private final FileManager fileManager;

    public StorageFile save(MultipartFile file, UUID storageId, long uploaderId) {
        // 권한 검사
        User uploader = userRepository.getById(uploaderId);
        Storage storage = storageRepository.getById(storageId);
        validateStorageFileAuthority(storage, uploaderId);

        String storedFilename = fileManager.save(FilePrefix.STORAGE_FILE, file);
        StorageFile storageFile = StorageFile.builder()
                .name(file.getOriginalFilename())
                .sizeInBytes(file.getSize())
                .storedFilename(storedFilename)
                .uploader(uploader)
                .storage(storage)
                .build();
        try {
            return storageFileRepository.save(storageFile);
        } catch (Exception e) {
            fileManager.deleteFile(FilePrefix.STORAGE_FILE, storedFilename);
            throw e;
        }
    }

    public StorageFileDownloadResponse download(UUID fileId, long downloaderId) {
        StorageFile storageFile = storageFileRepository.getById(fileId, StorageFileFetch.STORAGE);
        validateStorageFileAuthority(storageFile.getStorage(), downloaderId);
        byte[] fileData = fileManager.getByteArray(FilePrefix.STORAGE_FILE, storageFile.getStoredFilename());

        return StorageFileDownloadResponse.from(storageFile, fileData);
    }

    public StorageFileListResponse getLists(long requestUserId) {
        List<StorageFile> storageFiles = storageFileRepository.findAllPersonal(requestUserId);
        return StorageFileListResponse.from(storageFiles);
    }

    // StorageType에 따라 적절한 권한 검증을 거친다.
    private void validateStorageFileAuthority(Storage storage, long requestUserId) {
        StorageType storageType = storage.getStorageType();

        switch (storageType) {
            case PERSONAL -> {
                if (! storage.getOwner().getId().equals(requestUserId)) {
                    log.error("{}. 저장소 소유자 ID -> {}, 요청자 ID -> {}"
                            , StorageFileErrorCode.NOT_ENOUGH_AUTHORITY.getErrorStatus().message
                            ,storage.getOwner().getId(), requestUserId
                            );
                    throw new BusinessException(StorageFileErrorCode.NOT_ENOUGH_AUTHORITY);
                }
            }
            default -> throw new UncoveredEnumCaseException();
        }
    }
}
