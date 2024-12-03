package com.gmdrive.gmdrive.domain.storage.file.repository;

import com.gmdrive.gmdrive.domain.storage.file.entity.StorageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageFileJpaRepository extends JpaRepository<StorageFile, Long> {
}
