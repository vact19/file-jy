package com.file_jy.domain.user.repository;

import com.file_jy.domain.common.Datasource;
import com.file_jy.domain.user.entity.User;
import com.file_jy.global.error.exception.external.db.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final UserJpaRepository userJpaRepository;

    public boolean existsByLoginId(String loginId) {
        return userJpaRepository.existsByLoginId(loginId);
    }

    public User save(User user) {
        return userJpaRepository.save(user);
    }

    public User getByLoginId(String loginId) {
        return userJpaRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ResourceNotFoundException(Datasource.USER, "loginId", loginId));
    }

    public User getById(long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Datasource.USER, id));
    }
}
