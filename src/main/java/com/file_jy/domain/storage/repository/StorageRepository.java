package com.file_jy.domain.storage.repository;

import com.file_jy.domain.common.Datasource;
import com.file_jy.domain.storage.entity.Storage;
import com.file_jy.global.error.exception.external.db.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class StorageRepository {
    private final StorageJpaRepository jpaRepository;

    public Storage save(Storage personalStorage) {
        return jpaRepository.save(personalStorage);
    }

    public Storage getById(UUID id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Datasource.STORAGE, id));
    }

    public boolean existsPersonalByOwnerId(Long id) {
        Integer booleanInt = jpaRepository.existsPersonalByOwnerId(id);
        return booleanInt > 0;
    }

    public UUID getPersonalIdByOwnerId(long ownerId) {
        return jpaRepository.getPersonalIdByOwnerId(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException(Datasource.STORAGE, "ownerId", ownerId));
    }

    public UUID getPersonalIdByOwnerLoginId(String loginId) {
        return jpaRepository.getPersonalIdByOwnerLoginId(loginId)
                .orElseThrow(() -> new ResourceNotFoundException(Datasource.STORAGE, "ownerLoginId", loginId));
    }
}
