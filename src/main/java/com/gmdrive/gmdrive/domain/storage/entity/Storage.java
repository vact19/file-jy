package com.gmdrive.gmdrive.domain.storage.entity;

import com.gmdrive.gmdrive.domain.common.jpa.BaseEntity;
import com.gmdrive.gmdrive.domain.storage.entity.component.StorageType;
import com.gmdrive.gmdrive.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    //todo 생성자 검증
    private Storage(String name, User owner, StorageType storageType) {
        this.name = name;
        this.owner = owner;
        this.storageType = storageType;
        this.uploadedSizeInBytes = 0;
    }

    public static Storage personalStorage(User creator) {
        return new Storage(String.format("%s의 저장소", creator.getUsername()), creator, StorageType.PERSONAL);
    }

    public boolean isStorageOwner(long userId) {
        return id == userId;
    }
}
