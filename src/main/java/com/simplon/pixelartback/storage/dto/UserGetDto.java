package com.simplon.pixelartback.storage.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * This DTO is a simplified version, and serves to expose the data of a specific User.
 * No sensitive data, like email or password, are transmitted, so no such fields are required.
 */
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserGetDto {

//    TODO: Dto for art of one User (itt valoszinuleg CSAK az aliast es a pixelArtEntityList-tet akarjuk majd)
//    TODO: for "/api/user/{id}"
    @JsonProperty("id")
    private Long id;

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
}
