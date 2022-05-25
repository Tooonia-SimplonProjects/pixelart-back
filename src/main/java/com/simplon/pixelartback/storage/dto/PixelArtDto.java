package com.simplon.pixelartback.storage.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PixelArtDto {

    @JsonProperty("id")
    private Long id;

//    @JsonProperty("uuid")
//    private UUID uuid;

    @JsonProperty("name")
    private String name;

//    @Column(name = "image", nullable = false)
//    TODO: see format: Blob, BlobType, Image // private Base64 productImage;

//    @JsonProperty("id_user_fk")
//    private Long userEntity;
    @JsonProperty("id_user_fk") //TODO: lehet, h ide
    private UserForPixelArtDto userEntity;
//    @JsonProperty("id_user_fk")
//    private UserEntity userEntity;


    //    private UserEntity userEntity;
//    @JsonProperty("id_user_fk")
//    private void unpackNested(Long id_user_fk) {
//        this.userEntity = new UserEntity();
//        userEntity.setId(id_user_fk);
//    }
}
