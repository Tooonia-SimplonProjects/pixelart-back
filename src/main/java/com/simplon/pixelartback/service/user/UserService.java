package com.simplon.pixelartback.service.user;

import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserForPixelArtDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;

import java.util.List;
import java.util.UUID;

//    Focus on the creation of a new User (Sign-up), and on Login in the first phase of dev
public interface UserService {

//    Normally, all GET methods are configured with a simplified userDto, without password:
    List<UserGetDto> getAllUsers();

    UserDto getUserByUuid(UUID uuid);

    UserGetDto getUserById(Long id);

    UserForPixelArtDto getUserForPixelArt(Long id);

    UserDto findByEmail(String email);

    UserDto createUser(UserDto userDto);

    void deleteUser(Long id);

    void loginUser(UserDto userDto) throws Exception;
}
