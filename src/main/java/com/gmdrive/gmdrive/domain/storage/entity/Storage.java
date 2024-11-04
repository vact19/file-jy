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
    private int uploadedSizeKb;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    private Storage(String name, User creator, StorageType storageType) {
        this.name = name;
        this.creator = creator;
        this.storageType = storageType;
        this.uploadedSizeKb = 0;
    }

    public static Storage personalStorage(User creator) {
        return new Storage(String.format("%s의 저장소", creator.getUsername()), creator, StorageType.PERSONAL);
    }
}
