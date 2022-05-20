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

    @JsonProperty("id")
    private Long id;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("role")
    private RoleEntity role;
}
