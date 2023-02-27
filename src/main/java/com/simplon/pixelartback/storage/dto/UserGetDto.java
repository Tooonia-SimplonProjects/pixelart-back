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

    @JsonProperty("id")
    private Long id;

    @JsonProperty("alias")
    private String alias;

    @Email // Ensures that the email is in a valid format.
    @NotNull // Means it is a required field.
    @JsonProperty("user_email")
    private String email;

    /**
     *  As for the conception of this UserDto element:
     *  here PixelartSimpleDto has to be at its simple form, without the id_user_fk!!! Otherwhise
     *  that would cause a circular referencing and hibernate error.
     */
    @JsonProperty("pixelarts")
    private List<PixelArtSimpleDto> pixelArtEntityList;
}
