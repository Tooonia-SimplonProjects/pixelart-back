package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.service.mapper.UserMapper;
import com.simplon.pixelartback.storage.dao.UserDao;
import com.simplon.pixelartback.storage.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.UUID;

@Component
public class AuthenticationUtilImpl implements AuthenticationUtil {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserDao userDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationUtilImpl.class);

    @Override
    public Authentication getAuthentication() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if ((authentication == null || authentication instanceof AnonymousAuthenticationToken)) {
//            LOGGER.error("Null authentification");
//            return null;
//        }
//        LOGGER.getName(authentication);
//        return authentication;
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean authenticatedUserHasAccessToUser(UUID uuid) {
//        TODO: this method should verify the connected user (from SecurityContextHolder?) and return one of its parameter
//         so we can compare it to the eg. uuid in the url. If matches, then it gives access to the method!!!
        if (!isAuthenticated()) {
            LOGGER.error("Null authentification");
            return false;
        } else {
//            String currentUserEmail = getCurrentUsername();
        String currentUserEmail = getUserName();
            UserDto userDto = userMapper.entityToDto(userDao.findByEmail(currentUserEmail));
            if (userDto.getUuid().equals(uuid)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return true;
    }

//    TODO: see which one is more appropriate of these 2 methods !!!
    public String getUserName() {
//        String username = getAuthentication().getPrincipal().toString();
//        if (!StringUtils.hasText(username)) {
//            username = "";
//        }
//        return username;
        Authentication authentication = getAuthentication();
        return authentication.getName();
    }

    public String getCurrentUsername() {
        Object principal = getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            return ((UserDetails) principal).getUsername();
//            LOGGER.info(String.valueOf(((UserDetails) principal).getUsername()));
//        }
//        if (principal instanceof Principal) {
//            return ((Principal) principal).getName();
//        }
        return String.valueOf(principal);
    }
}
