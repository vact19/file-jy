package com.gmdrive.gmdrive.domain.user.repository;

import com.gmdrive.gmdrive.domain.common.jpa.vo.Email;
import com.gmdrive.gmdrive.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(@Param("email") Email email);
}

