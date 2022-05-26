package com.simplon.pixelartback.service.user;

import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;

import java.util.List;
import java.util.UUID;

//    Focus on the creation of a new User (Signup), and on Login during the first phase of dev
public interface UserService {

    List<UserGetDto> getAllUsers();

//    For /my-profile, information with email (private access):
    UserDto getUserByUuid(UUID uuid);

//    For listing the pixelart of one User (public access):
    UserGetDto getUserById(Long id);

    UserDto findByEmail(String email, boolean withPassword);

    UserDto createUser(UserDto userDto);

    void deleteUser(UUID uuid);

    void loginUser(UserDto userDto) throws Exception;
}
