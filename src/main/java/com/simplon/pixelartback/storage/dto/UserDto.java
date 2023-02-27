package com.simplon.pixelartback.storage.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplon.pixelartback.storage.entity.role.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * This DTO is a more complete version, with password.
 * The validation logic of certain parameters is defined at that level: with @NotNull, @Email
 */
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("alias")
    private String alias;

    @Email // Ensures that the email is in a valid format.
    @NotNull // Means it is a required field.
    @JsonProperty("user_email")
    private String email;

    @NotNull
    @JsonProperty("user_password")
    private String password;

    @JsonProperty("role")
    private RoleEntity role;

    /**
     *  As for the conception of this UserDto element:
     *  here PixelartSimpleDto has to be at its simple form, without the id_user_fk!!! Otherwhise
     *  that would cause a circular/never ending referencing and hibernate error.
     */
    @JsonProperty("pixelarts")
    private List<PixelArtSimpleDto> pixelArtEntityList;
}
