package com.file_jy.domain.storage.entity;

import com.file_jy.domain.common.jpa.BaseEntity;
import com.file_jy.domain.storage.entity.component.StorageType;
import com.file_jy.domain.user.entity.User;
import com.file_jy.global.error.errorcode.StorageErrorCode;
import com.file_jy.global.error.exception.business.BusinessException;
import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Storage extends BaseEntity {
    private static final int DEFAULT_UPLOAD_LIMIT_GB = 1;
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private StorageType storageType;
    private long uploadedSizeInBytes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    private Storage(String name, User owner, StorageType storageType) {
        validateValues(name, owner, storageType);
        this.id = UuidCreator.getTimeOrderedEpoch();
        this.name = name;
        this.owner = owner;
        this.storageType = storageType;
        this.uploadedSizeInBytes = 0;
    }

    private static void validateValues(String name, User owner, StorageType storageType) {
        if (! StringUtils.hasText(name)) {
            throw new BusinessException(StorageErrorCode.NO_STORAGE_NAME, name);
        }

        if (owner == null) {
            throw new BusinessException(StorageErrorCode.NO_STORAGE_OWNER, owner);
        }
        if (storageType == null) {
            throw new BusinessException(StorageErrorCode.NO_STORAGE_TYPE, storageType);
        }
    }


    public static Storage personalStorage(User creator) {
        return new Storage(String.format("%s의 저장소", creator.getUsername()), creator, StorageType.PERSONAL);
    }

    public boolean isStorageOwner(long userId) {
        return this.owner.getId() == userId;
    }
}
