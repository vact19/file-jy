package com.gmdrive.gmdrive.domain.storage.file.entity;

import com.gmdrive.gmdrive.domain.common.jpa.BaseEntity;
import com.gmdrive.gmdrive.domain.storage.entity.Storage;
import com.gmdrive.gmdrive.domain.user.entity.User;
import com.gmdrive.gmdrive.global.error.errorcode.StorageFileErrorCode;
import com.gmdrive.gmdrive.global.error.exception.business.BusinessException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class StorageFile extends BaseEntity {
    @Id
    private String id;
    private String name;
    @Column(nullable = false)
    private long sizeInBytes;
    private String storedFilename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id")
    private User uploader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @Builder
    private StorageFile(String name, long sizeInBytes, String storedFilename, Storage storage, User uploader) {
        validateValues(sizeInBytes, storage, uploader);
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.sizeInBytes = sizeInBytes;
        this.storedFilename = storedFilename;
        this.storage = storage;
        this.uploader = uploader;
    }

    private static void validateValues(long sizeInBytes, Storage storage, User uploader) {
        if (sizeInBytes <= 0) {
            throw new BusinessException(StorageFileErrorCode.EMPTY_FILE, sizeInBytes);
        }
        if (uploader == null) {
            throw new BusinessException(StorageFileErrorCode.NO_UPLOADER, uploader);
        }
        if (storage == null) {
            throw new BusinessException(StorageFileErrorCode.NO_STORAGE, storage);
        }
    }

    public String getDisplaySize() {
        if (sizeInBytes < 1024) {
            return sizeInBytes + " B";
        } else if (sizeInBytes <= 1024 * 1024) {
            return String.format("%.2f KB", sizeInBytes / 1024.0);
        } else if (sizeInBytes <= 1024 * 1024 * 1024) {
            return String.format("%.2f MB", sizeInBytes / (1024.0 * 1024.0));
        }
        return String.format("%.2f GB", sizeInBytes / (1024.0 * 1024.0 * 1024.0));
    }
}
