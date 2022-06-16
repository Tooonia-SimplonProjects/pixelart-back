package com.simplon.pixelartback.service.user;

import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;

import java.util.List;
import java.util.UUID;

/**
 * Focus on the creation of a new User (Signup), and on Login during the first phase of dev
 */
public interface UserService {

    List<UserGetDto> getAllUsers();

    /**
    * For /my-profile, information with email info (private access):
    */
    UserDto getUserByUuid(UUID uuid);

    /**
     * For listing the pixelart of one specific User (public access):
     * @param id
     * @return
     */
//
    UserGetDto getUserById(Long id);

//    /**
//     * GET the connected user TODO: kell?
//     * @return
//     */
//    UserDto getMe();

    UserDto findByEmail(String email, boolean withPassword);

    UserDto createUser(UserDto userDto);

//    void deleteUser(Long id);
    void deleteUser(UUID uuid);

//    void loginUser(UserDto userDto) throws Exception;

    UserDto findByAlias(String alias);
}
