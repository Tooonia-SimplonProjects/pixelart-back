package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.storage.entity.role.RoleEntity;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * The class we use to work with the Security and authentication/authorization of a User
 */
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class AuthenticatedUser {
//public class AuthenticatedUser implements UserDetails {
//public class AuthenticatedUser extends UserDto implements UserDetails {

    private Long id;

    private UUID uuid;

    private String alias;

    private String email;

    private String password;

    private RoleEntity role;

    public AuthenticatedUser(String email) {
        super();
        this.email = email;
    }
//
//    public AuthenticatedUser(String email) {
//        super();
//        this.email = email;
//    }

//    private UserDto userDto;
//    private UserEntity userEntity;

//    public AuthenticatedUser(UserDto userDto) {
//        this.userDto = userDto;
//    public AuthenticatedUser(UserEntity userEntity) {
//        this.userEntity = userEntity;
//    }

//
//    public List<GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(Collections.singleton(userEntity.getRole()))) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(userEntity.getRole().toString()));
//        }
//        return grantedAuthorities;
//    }


//    public String getPassword() {
//        return userEntity.getPassword();
//    }
//
//
    public String getUsername() {
        return this.email;
    }


    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return true;
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return true;
    }
}
