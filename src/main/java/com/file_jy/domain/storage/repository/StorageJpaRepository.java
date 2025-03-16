package com.file_jy.domain.storage.repository;

import com.file_jy.domain.storage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface StorageJpaRepository extends JpaRepository<Storage, UUID> {
    String PERSONAL = "'PERSONAL'";

    @Query(value = "SELECT EXISTS (" +
                "SELECT 1 FROM storage s " +
                " WHERE s.owner_id = :ownerId" +
            " AND s.storage_type = " + PERSONAL
            + ")"
            , nativeQuery = true
    )
    Integer existsPersonalByOwnerId(@Param("ownerId") long ownerId);

    @Query(value = "SELECT storage.id FROM Storage storage" +
            " WHERE storage.owner.id = :ownerId" +
            " AND storage.storageType = " + PERSONAL
    )
    Optional<UUID> getPersonalIdByOwnerId(@Param("ownerId") long ownerId);
}
