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

    @JsonProperty("name")
    private String name;

//    @Column(name = "image", nullable = false)
//    TODO: see format: Blob, BlobType, Image // private Base64 productImage;

    /**
     * As for the conception of this PixelArtDto element:
     * here UserGetDto has to have a simplified version of PixelArts, without the id_user_fk!!!,
     * as PixelArtSimpleDto has only id and name parameters. Otherwhise
     * it would cause a circular referencing and hibernate error.
     */
    @JsonProperty("id_user_fk")
    private UserGetDto userEntity;
}
