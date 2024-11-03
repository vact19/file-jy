package com.gmdrive.gmdrive.domain.storage.personal.repository;

import com.gmdrive.gmdrive.domain.storage.personal.entity.PersonalStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PersonalStorageRepository {
    private final PersonalStorageJpaRepository jpaRepository;

    public PersonalStorage save(PersonalStorage personalStorage) {
        return jpaRepository.save(personalStorage);
    }
}
