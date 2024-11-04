package com.gmdrive.gmdrive.domain.storage.repository;

import com.gmdrive.gmdrive.domain.storage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageJpaRepository extends JpaRepository<Storage, Long> {
}
