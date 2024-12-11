package com.gmdrive.gmdrive.domain.storage.file.repository;

import com.gmdrive.gmdrive.domain.common.Datasource;
import com.gmdrive.gmdrive.domain.storage.file.entity.StorageFile;
import com.gmdrive.gmdrive.global.error.exception.common.UncoveredEnumCaseException;
import com.gmdrive.gmdrive.global.error.exception.external.db.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class StorageFileRepository {

    private final StorageFileJpaRepository jpaRepository;

    public StorageFile save(StorageFile storageFile) {
        return jpaRepository.save(storageFile);
    }

    public StorageFile getById(String fileId) {
        return jpaRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException(Datasource.STORAGE_FILE, fileId));
    }

    public StorageFile getById(String fileId, StorageFileFetch fetch) {
        Optional<StorageFile> storageFile = switch (fetch) {
            case STORAGE -> jpaRepository.findByIdFetchStorage(fileId);
            default -> throw new UncoveredEnumCaseException();
        };

        return storageFile.orElseThrow(() -> new ResourceNotFoundException(Datasource.STORAGE_FILE, fileId));
    }

    public List<StorageFile> findAllPersonal(long userId) {
        return jpaRepository.findAllPersonal(userId);
    }

    public enum StorageFileFetch {
        STORAGE
        , UPLOADER
    }
}
