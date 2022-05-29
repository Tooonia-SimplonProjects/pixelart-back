package com.simplon.pixelartback.facade.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

//    private String userEmail;
//
//    private String userPassword;

    private String email;
//    private String alias;

//    @NotNull
//    @JsonProperty("user_password")
    private String password;
}
