package com.file_jy.domain.user.repository;

import com.file_jy.domain.common.Datasource;
import com.file_jy.domain.common.jpa.vo.Email;
import com.file_jy.domain.user.entity.User;
import com.file_jy.global.error.exception.external.db.ResourceNotFoundException;
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

    public User getById(long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Datasource.USER, id));
    }
}
