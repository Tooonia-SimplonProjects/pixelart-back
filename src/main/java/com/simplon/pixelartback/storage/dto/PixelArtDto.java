package com.simplon.pixelartback.storage.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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
    private UserGetDto userEntity;
//    @JsonProperty("id_user_fk")
//    private UserEntity userEntity;
}
