package com.gmdrive.gmdrive.domain.storage.repository;

import com.gmdrive.gmdrive.domain.common.Datasource;
import com.gmdrive.gmdrive.domain.storage.entity.Storage;
import com.gmdrive.gmdrive.global.error.exception.external.db.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StorageRepository {
    private final StorageJpaRepository jpaRepository;

    public Storage save(Storage personalStorage) {
        return jpaRepository.save(personalStorage);
    }

    public Storage getById(long id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Datasource.STORAGE, id));
    }

    public boolean existsPersonalByOwnerId(Long id) {
        Integer booleanInt = jpaRepository.existsPersonalByOwnerId(id);
        return booleanInt > 0;
    }
}
