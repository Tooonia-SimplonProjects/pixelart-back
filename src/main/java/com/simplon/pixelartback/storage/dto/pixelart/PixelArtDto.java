package com.simplon.pixelartback.storage.dto.pixelart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
//@Data
public class PixelArtDto {
//    @Valid //TODO: @Valid needed?
//    TODO private or public properties here? In MapStruct, DTO with public properties and no getters/setters?

//    TODO: is this necessary here? Nem kellene valamely ertekeket elrejteni?
    @JsonProperty("id")
    private Long id;
    @JsonProperty("uuid")
    private UUID uuid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("image")
    private String image;
    //    @JsonProperty("idUser")
    //    private UUID idUser;
}
