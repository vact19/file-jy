package com.gmdrive.gmdrive.domain.storage.entity;

import com.gmdrive.gmdrive.domain.common.jpa.BaseEntity;
import com.gmdrive.gmdrive.domain.storage.entity.component.StorageType;
import com.gmdrive.gmdrive.domain.user.entity.User;
import com.gmdrive.gmdrive.global.error.errorcode.StorageErrorCode;
import com.gmdrive.gmdrive.global.error.exception.business.BusinessException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Storage extends BaseEntity {
    private static final int DEFAULT_UPLOAD_LIMIT_GB = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private StorageType storageType;
    private long uploadedSizeInBytes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    private Storage(String name, User owner, StorageType storageType) {
        validateValues(name, owner, storageType);
        this.name = name;
        this.owner = owner;
        this.storageType = storageType;
        this.uploadedSizeInBytes = 0;
    }

    private static void validateValues(String name, User owner, StorageType storageType) {
        if (! StringUtils.hasText(name)) {
            throw new BusinessException(StorageErrorCode.NO_STORAGE_NAME, name);
        }
        // if owner, storageType
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
