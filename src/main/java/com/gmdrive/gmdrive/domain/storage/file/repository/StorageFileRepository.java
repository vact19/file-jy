package com.gmdrive.gmdrive.domain.storage.file.repository;

import com.gmdrive.gmdrive.domain.storage.file.entity.StorageFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StorageFileRepository {

    private final StorageFileJpaRepository jpaRepository;

    public StorageFile save(StorageFile storageFile) {
        return jpaRepository.save(storageFile);
    }
}
