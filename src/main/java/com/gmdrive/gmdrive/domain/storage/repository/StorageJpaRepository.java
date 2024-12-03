package com.gmdrive.gmdrive.domain.storage.repository;

import com.gmdrive.gmdrive.domain.storage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StorageJpaRepository extends JpaRepository<Storage, Long> {
    @Query("SELECT s.owner.id FROM Storage s" +
            " WHERE s.id = :storageId")
    long getOwnerId(long storageId);
}
