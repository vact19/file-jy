package com.gmdrive.gmdrive.domain.user.repository;

import com.gmdrive.gmdrive.domain.common.Datasource;
import com.gmdrive.gmdrive.domain.common.jpa.vo.Email;
import com.gmdrive.gmdrive.domain.user.entity.User;
import com.gmdrive.gmdrive.global.error.exception.external.db.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final UserJpaRepository userJpaRepository;

    public boolean existsByEmail(Email email) {
        return userJpaRepository.existsByEmail(email);
    }

    public User save(User user) {
        return userJpaRepository.save(user);
    }

    public User getByEmail(Email email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(Datasource.USER, "email", email.getValue()));
    }
}
