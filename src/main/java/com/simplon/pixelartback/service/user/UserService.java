package com.simplon.pixelartback.service.user;

import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;

import java.util.List;
import java.util.UUID;

//    Focus on the creation of a new User (Sign-up), and on Login in the first phase of dev
public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserByUuid(UUID uuid);

    UserDto getUserById(Long id);

    UserGetDto getSimplifiedUserById(Long id);

    UserDto createUser(UserDto userDto);
}
