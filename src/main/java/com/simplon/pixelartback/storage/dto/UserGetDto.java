package com.simplon.pixelartback.storage.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplon.pixelartback.storage.entity.pixelart.PixelArtEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

// This DTO is a simplified version, and serves to expose the data of a specific User
// No sensitive data, like email or password, are transmitted, so no such fields are required.

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserGetDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("pixelarts")
    private List<PixelArtEntity> pixelArtEntityList;
}
