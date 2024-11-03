package com.gmdrive.gmdrive.domain.storage.personal.repository;

import com.gmdrive.gmdrive.domain.storage.personal.entity.PersonalStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalStorageJpaRepository extends JpaRepository<PersonalStorage, Long> {
}
