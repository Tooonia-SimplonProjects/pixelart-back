package com.simplon.pixelartback.storage.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserGetSlimDto {
//    TODO: maybe no need for 'id' field neither?!
    @JsonProperty("id")
    private Long id;

    @JsonProperty("alias")
    private String alias;

    @Email // Ensures that the email is in a valid format.
    @NotNull // Means it is a required field.
    @JsonProperty("userEmail")
    private String email;
}
