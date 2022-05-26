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

//     TODO: ez a UserDto, amit a "create" pixelartnal hasznalunk! Itt valoszinuleg eleg lesz egy azonosito: id/uuid/email/alias (ez utobbi talan a legkevesbe, mert valtozhat!)
//      Es leheet, h kell egy kulon, ami csak az alias-t es a pixelArtEntityList-et tartalmazza!!!, arra, ha adott rajz alatt kiirjuk/rakattint valaki.
    @JsonProperty("id")
    private Long id;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("role")
    private RoleEntity role;
}
