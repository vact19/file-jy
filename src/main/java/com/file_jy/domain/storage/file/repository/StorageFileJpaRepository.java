package com.file_jy.domain.storage.file.repository;

import com.file_jy.domain.storage.file.entity.StorageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StorageFileJpaRepository extends JpaRepository<StorageFile, UUID> {
    @Query("SELECT sfile FROM StorageFile sfile" +
            " JOIN FETCH sfile.storage" +
            " WHERE sfile.id = :id")
    Optional<StorageFile> findByIdFetchStorage(@Param("id") UUID id);

    @Query("SELECT sfile FROM StorageFile sfile" +
            " WHERE sfile.storage.id = :storageId" +
            " ORDER BY sfile.createdTime DESC")
    List<StorageFile> findAllPersonalByStorageId(@Param("storageId") UUID storageId);
}
