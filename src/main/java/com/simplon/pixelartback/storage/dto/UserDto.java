package com.simplon.pixelartback.storage.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplon.pixelartback.storage.entity.pixelart.PixelArtEntity;
import com.simplon.pixelartback.storage.entity.role.RoleEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//The validation logic is defined at that level:
//with @NotNull, @Email
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
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

    @JsonProperty("pixelarts")
    private List<PixelArtEntity> pixelArtEntityList;

}
