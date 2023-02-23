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
 * The validation logic is defined at that level: with @NotNull, @Email
 */
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

//    TODO: lehet, h az "id" sem kell! Vagy uuid inkabb helyette?
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

    //    TODO: lehet, h kelleni fog a role?
    @JsonProperty("role")
    private RoleEntity role;

    @JsonProperty("pixelarts")
    private List<PixelArtSimpleDto> pixelArtEntityList;

    //    TODO: lehet, h kelleni fog a lista?
//    @JsonProperty("pixelarts")
//    private List<PixelArtEntity> pixelArtEntityList;

}
