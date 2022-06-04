package com.simplon.pixelartback.facade.security;

import org.springframework.security.core.Authentication;

import java.util.UUID;

/**
 * Custom interface that allows to retrieve authentication everywere in the coe
 * so not having a static access to it, but hiding it behind a facade.
 * In other terms, that facade exposes the "Authentication" object while hiding
 * the statix state and keeping the code decoupled and fully testable.
 * source: https://www.baeldung.com/get-user-in-spring-security
 */
public interface AuthenticationUtil {

    Authentication getAuthentication();

    boolean authenticatedUserHasAccessToUser(UUID uuid);

    boolean isAuthenticated();

    String getUserName();

    String getCurrentUsername();
}
