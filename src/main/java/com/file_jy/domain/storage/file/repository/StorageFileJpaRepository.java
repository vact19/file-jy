package com.file_jy.domain.storage.file.repository;

import com.file_jy.domain.storage.file.entity.StorageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StorageFileJpaRepository extends JpaRepository<StorageFile, String> {
    @Query("SELECT sfile FROM StorageFile sfile" +
            " JOIN FETCH sfile.storage" +
            " WHERE sfile.id = :id")
    Optional<StorageFile> findByIdFetchStorage(@Param("id") String id);

    @Query("SELECT sfile FROM StorageFile sfile" +
            " WHERE sfile.storage.id = (" +
                "SELECT s.id FROM Storage s" +
                " WHERE s.owner.id = :userId" +
                " AND s.storageType = 'PERSONAL'" +
            ")" +
            " ORDER BY sfile.createdTime DESC")
    List<StorageFile> findAllPersonal(@Param("userId") long userId);
}
