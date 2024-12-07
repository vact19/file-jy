package com.gmdrive.gmdrive.domain.storage.file.repository;

import com.gmdrive.gmdrive.domain.storage.file.entity.StorageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StorageFileJpaRepository extends JpaRepository<StorageFile, String> {
    @Query("SELECT sfile FROM StorageFile sfile" +
            " JOIN FETCH sfile.storage" +
            " WHERE sfile.id = :id")
    Optional<StorageFile> findByIdFetchStorage(@Param("id") String id);
}
