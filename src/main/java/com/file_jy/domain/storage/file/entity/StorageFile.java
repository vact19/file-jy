package com.file_jy.domain.storage.file.entity;

import com.file_jy.domain.common.jpa.BaseEntity;
import com.file_jy.domain.storage.entity.Storage;
import com.file_jy.domain.user.entity.User;
import com.file_jy.global.error.errorcode.StorageFileErrorCode;
import com.file_jy.global.error.exception.business.BusinessException;
import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class StorageFile extends BaseEntity {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    @Column(nullable = false)
    private long sizeInBytes;
    private String storedFilename;
    private boolean isSharing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id")
    private User uploader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @Builder
    private StorageFile(String name, long sizeInBytes, String storedFilename, Storage storage, User uploader) {
        validateValues(sizeInBytes, storage, uploader);
        this.id = UuidCreator.getTimeOrderedEpoch();
        this.name = name;
        this.sizeInBytes = sizeInBytes;
        this.storedFilename = storedFilename;
        this.storage = storage;
        this.uploader = uploader;
        this.isSharing = false;
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
