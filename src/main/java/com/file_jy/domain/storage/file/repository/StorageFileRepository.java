package com.file_jy.domain.storage.file.repository;

import com.file_jy.domain.common.Datasource;
import com.file_jy.domain.storage.file.entity.StorageFile;
import com.file_jy.global.error.exception.common.UncoveredEnumCaseException;
import com.file_jy.global.error.exception.external.db.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class StorageFileRepository {

    private final StorageFileJpaRepository jpaRepository;

    public StorageFile save(StorageFile storageFile) {
        return jpaRepository.save(storageFile);
    }

    public StorageFile getById(UUID fileId, StorageFileFetch fetch) {
        Optional<StorageFile> storageFile = switch (fetch) {
            case STORAGE -> jpaRepository.findByIdFetchStorage(fileId);
            case UPLOADER -> jpaRepository.findByIdFetchUploader(fileId);
            default -> throw new UncoveredEnumCaseException();
        };

        return storageFile.orElseThrow(() -> new ResourceNotFoundException(Datasource.STORAGE_FILE, fileId));
    }

    public List<StorageFile> findAllPersonalByStorageId(UUID storageId) {
        return jpaRepository.findAllPersonalByStorageId(storageId);
    }

    public List<StorageFile> findAllSharingByStorageId(UUID storageId) {
        return jpaRepository.findAllSharingByStorageId(storageId);
    }

    public enum StorageFileFetch {
        STORAGE
        , UPLOADER
    }
}
