package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.storage.entity.role.RoleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.management.relation.Role;
import java.util.*;

/**
 * Utility service class for retrieving information about the connected user.
 */

@Component
public class ContextHelperUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextHelperUtil.class);

    public AuthenticatedUser getAuthenticatedUser() {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthenticatedUser result = null;
        if (auth == null) {
            LOGGER.error("Null authentification");
        } else {
            final Object detail = auth.getPrincipal();
//            final Object detail = auth.getDetails();
            if (detail == null) {
                LOGGER.error("User detail is null");
            } else {
                if (detail instanceof AuthenticatedUser) {
                    result = (AuthenticatedUser) detail; //TODO ERROR : Can not cast User to AuthenticatedUser!!! >>> throws Unknown authenticated user exception for "getUserByUuid"
                } else {
                    LOGGER.error("Unknown authenticated user {}", auth.getPrincipal());
                }
            }
        }
        return result;
    }

//    TODO: probablement pas besoin de ça!
    public void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticatedUser.getEmail(), null, collectAuthoritiesFromRoles(Collections.singletonList(authenticatedUser.getRole()))
        );
        usernamePasswordAuthenticationToken.setDetails(authenticatedUser);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private List<? extends GrantedAuthority> collectAuthoritiesFromRoles(List<RoleEntity> roles) {
        List<SimpleGrantedAuthority> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (RoleEntity role : roles) {
                result.add(new SimpleGrantedAuthority(role.toString()));
            }
        }
        return result;
    }
}
