package com.simplon.pixelartback.storage.dao;

import com.simplon.pixelartback.storage.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDao extends JpaRepository<UserEntity, Long> {

    UserEntity findByUuid(UUID uuid);

    UserEntity getUserById(Long id);
}
