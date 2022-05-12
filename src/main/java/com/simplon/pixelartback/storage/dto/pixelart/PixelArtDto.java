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
//    TODO: Mustapha: probablement cela indique que ce sera dans le JSON body!!!
//    TODO: Il récupère une propriété du Body, alors que nous voulons une partie de l'URL (l'id qui se figure dans le URL!!!
    @JsonProperty("id")
    private Long id;
//    @JsonProperty("uuid")
//    private UUID uuid;
    @JsonProperty("name")
    private String name;
//    @JsonProperty("image")
//    private String image;
    //    @JsonProperty("idUser")
    //    private UUID idUser;

//    public PixelArtDto uuid(UUID uuid) {
//        this.uuid = uuid;
//        return this;
//    }
}
