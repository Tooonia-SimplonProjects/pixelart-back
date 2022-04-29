package com.simplon.pixelartback.storage.dao.pixelart;

import com.simplon.pixelartback.storage.entity.pixelart.PixelArtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PixelArtDao extends JpaRepository<PixelArtEntity, Long> {

    PixelArtEntity findByUuid(UUID uuid); //TODO: eror message: craches with the findById of JpaRepository!?

//    List<PixelArtEntity> findByUser(UUID userUuid); //TODO: implement when UserEntity has been created!
}
