package com.simplon.pixelartback.storage.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplon.pixelartback.storage.entity.pixelart.PixelArtEntity;
import com.simplon.pixelartback.storage.entity.role.RoleEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

// This DTO is a simplified version, and serves to expose the data of a specific User
// No sensitive data, like email or password, are transmitted, so no such fields are required.

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserGetDto {

//    TODO: itt valoszinuleg CSAK az aliast, emailt es a pixelArtEntityList-tet akarjuk majd
//    TODO: for "my-profile/{uuid}"
//    @JsonProperty("id")
//    private Long id;

//    @JsonProperty("uuid")
//    private UUID uuid;

    @JsonProperty("alias")
    private String alias;

    @Email // Ensures that the email is in a valid format.
    @NotNull // Means it is a required field.
    @JsonProperty("user_email")
    private String email;

//    @JsonProperty("role")
//    private RoleEntity role;

    @JsonProperty("pixelarts")
    private List<PixelArtSimpleDto> pixelArtEntityList;
//    @JsonProperty("pixelarts")
//    private List<PixelArtEntity> pixelArtEntityList;
}
