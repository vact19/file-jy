package com.gmdrive.gmdrive.domain.storage.file.service;

import com.gmdrive.gmdrive.domain.storage.entity.Storage;
import com.gmdrive.gmdrive.domain.storage.file.entity.StorageFile;
import com.gmdrive.gmdrive.domain.storage.file.repository.StorageFileRepository;
import com.gmdrive.gmdrive.domain.storage.repository.StorageRepository;
import com.gmdrive.gmdrive.domain.user.entity.User;
import com.gmdrive.gmdrive.domain.user.repository.UserRepository;
import com.gmdrive.gmdrive.global.error.errorcode.StorageFileErrorCode;
import com.gmdrive.gmdrive.global.error.exception.business.BusinessException;
import com.gmdrive.gmdrive.global.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RequiredArgsConstructor
@Service
public class StorageFileService {

    private final StorageFileRepository storageFileRepository;
    private final StorageRepository storageRepository;
    private final UserRepository userRepository;
    private final FileManager fileManager;

    public StorageFile save(MultipartFile file, long storageId, long uploaderId) {
        // 권한 검사
        User uploader = userRepository.getById(uploaderId);
        Storage storage = storageRepository.getById(storageId);
        if (storage.isStorageOwner(uploader.getId())) {
            throw new BusinessException(StorageFileErrorCode.NOT_ENOUGH_UPLOAD_AUTHORITY);
        }
        // 저장, 파일명 가져오기
        Path uploadedFilePath = fileManager.save(FileManager.FilePrefix.STORAGE_FILE, file);
        StorageFile storageFile = StorageFile.builder()
                .name(file.getOriginalFilename())
                .sizeInBytes(file.getSize())
                .storedPath(uploadedFilePath)
                .uploader(uploader)
                .storage(storage)
                .build();
        return storageFileRepository.save(storageFile);
    }

    public StorageFileDownloadResponse getStorageFile(String fileId, long downloaderId) {
        StorageFile storageFile = storageFileRepository.getById(fileId, StorageFileFetch.STORAGE);
        validateStorageFileAuthority(storageFile.getStorage(), downloaderId);
        byte[] fileData = fileManager.getByteArray(storageFile.getStoredPath());

        return StorageFileDownloadResponse.from(storageFile, fileData);
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
