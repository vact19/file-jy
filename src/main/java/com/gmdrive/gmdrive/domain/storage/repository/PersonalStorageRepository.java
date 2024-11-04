package com.gmdrive.gmdrive.domain.storage.repository;

import com.gmdrive.gmdrive.domain.storage.entity.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PersonalStorageRepository {
    private final StorageJpaRepository jpaRepository;

    public Storage save(Storage personalStorage) {
        return jpaRepository.save(personalStorage);
    }
}
