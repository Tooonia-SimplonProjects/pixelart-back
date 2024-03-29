package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.service.mapper.UserGetMapper;
import com.simplon.pixelartback.service.mapper.UserMapper;
import com.simplon.pixelartback.storage.dao.UserDao;
import com.simplon.pixelartback.storage.dto.PixelArtDto;
import com.simplon.pixelartback.storage.dto.PixelArtSimpleDto;
import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * The implementation of our custom AuthenticationUtil interface for authenticated user info
 *
 * By adding this "@Component" Spring will scan this class automatically when loading the application, it will
 * instantiate it and inject any specified dependencies into it, and then
 * inject it wherever needed. /source: https://www.baeldung.com/spring-component-annotation
 */
@Component
public class AuthenticationUtilImpl implements AuthenticationUtil {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserGetMapper userGetMapper;

    @Autowired
    UserDao userDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationUtilImpl.class);

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

//    /**
//     * Checks existence of an authenticated user, looks for its UUID if user exists, and compare it to the given UUID.
//     * If matches, returned boolean set to "true"
//     * @param uuid
//     * @return
//     */
//    public boolean authenticatedUserHasAccessToUser(UUID uuid) {
//        if (!isAuthenticated()) {
//            LOGGER.error("Null authentification");
//            return false;
//        } else {
//        String currentUserEmail = getUserName();
//            UserDto userDto = userMapper.entityToDto(userDao.findByEmail(currentUserEmail));
//            if (userDto.getUuid().equals(uuid)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }

    @Override
    public boolean authenticatedUserHasAccessToUserById(Long id) {
        if (!isAuthenticated()) {
            LOGGER.error("Null authentification");
            return false;
        } else {
            String currentUserEmail = getUserName();
            UserGetDto userGetDto = userGetMapper.entityToDto(userDao.findByEmail(currentUserEmail));
            if (userGetDto.getId().equals(id)) {
                return true;
            } else {
                return false;
            }
        }
    }

//    @Override
//    public boolean authenticatedUserHasAccessToUserByEmail(String email) {
//        if (!isAuthenticated()) {
//            LOGGER.error("Null authentification");
//            return false;
//        } else {
//            String currentUserEmail = getUserName();
//            UserDto userDto = userMapper.entityToDto(userDao.findByEmail(currentUserEmail));
////            if (userDto.getId().equals(id)) {
////                return true;
////            } else {
////                return false;
////            }
//            if (userDto.getEmail().equals(email)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
    /**
     * Checks existence of an authenticated user, looks for the id of its pixelart if any, and compare it to the given id.
     * If matches, returned boolean set to "true"
     * @param id
     * @return
     */
    public boolean authenticatedUserHasAccessToPixelart(Long id) {
        if (!isAuthenticated()) {
            LOGGER.error("Null authentification");
            return false;
        } else {
            String currentUserEmail = getUserName();
            UserGetDto userGetDto = userGetMapper.entityToDto(userDao.findByEmail(currentUserEmail));
            List<PixelArtSimpleDto> pixelArtSimpleDtos = userGetDto.getPixelArtEntityList();
            for (PixelArtSimpleDto p : pixelArtSimpleDtos) {
                if (p.getId().equals(id)) {
                    return true;
                }
            }
        } return false;
    }


//    /**
//     * Cheks if there is current authentication
//     * p
//     * @return
//     */
    public boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return true;
    }

    /**
     * Returns the email of the current authenticated user
     * @return
     */
    public String getUserName() {
        Authentication authentication = getAuthentication();
        String userEmail = null;
        if (authentication == null) {
            LOGGER.error("Null authentification");
        } else {
            userEmail = authentication.getName();
        }
        return userEmail;
    }
}
