package com.gmdrive.gmdrive.domain.storage.personal.entity;

import com.gmdrive.gmdrive.domain.common.jpa.BaseEntity;
import com.gmdrive.gmdrive.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PersonalStorage extends BaseEntity {
    private static final int DEFAULT_UPLOAD_LIMIT_GB = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int uploadLimitGb;
    private int uploadedSizeKb;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public PersonalStorage(User owner) {
        this.name = String.format("%s의 저장소", owner.getUsername());
        this.owner = owner;
        this.uploadLimitGb = DEFAULT_UPLOAD_LIMIT_GB;
        this.uploadedSizeKb = 0;
    }
}
