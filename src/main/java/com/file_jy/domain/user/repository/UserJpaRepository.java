package com.file_jy.domain.user.repository;

import com.file_jy.domain.common.jpa.vo.Email;
import com.file_jy.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(Email email);

    Optional<User> findByEmail(Email email);
}

