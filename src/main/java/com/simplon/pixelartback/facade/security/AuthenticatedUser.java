package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.entity.role.RoleEntity;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//public class AuthenticatedUser  {
public class AuthenticatedUser extends UserEntity implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(getRole().toString()));
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
