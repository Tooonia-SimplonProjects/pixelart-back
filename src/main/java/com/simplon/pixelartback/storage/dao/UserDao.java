package com.simplon.pixelartback.storage.dao;

import com.simplon.pixelartback.storage.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//The primary key tipe is "Long"
public interface UserDao extends JpaRepository<UserEntity, Long> {

    UserEntity findByUuid(UUID uuid);

    UserEntity getUserById(Long id);

//    TODO: talan Optional<UserEntity> findByAlias(String alias);
    UserEntity findByEmail(String email);

    UserEntity findByAlias(String alias);
}
