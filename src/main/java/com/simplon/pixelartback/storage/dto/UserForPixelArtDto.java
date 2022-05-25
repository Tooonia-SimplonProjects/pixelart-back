package com.simplon.pixelartback.storage.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplon.pixelartback.storage.entity.role.RoleEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserForPixelArtDto {

//    TODO: valoszinuleg ez NEM KELL MAJD
//     itt valszoninuleg CSAK az alias-t es a pixelArtEntityList-et akarjuk majd latni!
//    TODO: ez lesz adott rajz alatt, ha valaki rakattint
    @JsonProperty("id")
    private Long id;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("role")
    private RoleEntity role;
}
