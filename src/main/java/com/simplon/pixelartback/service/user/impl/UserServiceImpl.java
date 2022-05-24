package com.simplon.pixelartback.service.user.impl;

import com.simplon.pixelartback.service.mapper.UserForPixelArtMapper;
import com.simplon.pixelartback.service.mapper.UserGetMapper;
import com.simplon.pixelartback.service.mapper.UserMapper;
import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dao.UserDao;
import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserForPixelArtDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;
import com.simplon.pixelartback.storage.entity.role.RoleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
// A hint for the persistence provider that the transaction should be 'read only'; default value is "false"
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserGetMapper userGetMapper;

    @Autowired
    private UserForPixelArtMapper userForPixelArtMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public List<UserGetDto> getAllUsers() throws EmptyResultDataAccessException {
        return userGetMapper.entitiesToDtos(userDao.findAll());
    }

    @Override
    public UserDto getUserByUuid(UUID uuid) {
        if(uuid == null) {
            throw new IllegalArgumentException("UUID User is missing");
        }
        return userMapper.entityToDto(userDao.findByUuid(uuid));
    }

    @Override
    public UserGetDto getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id PixelArt is missing");
        }
        return userGetMapper.entityToDto(userDao.getUserById(id));
    }

    @Override
    public UserForPixelArtDto getUserForPixelArt(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id PixelArt is missing");
        }
        return userForPixelArtMapper.entityToDto(userDao.getUserById(id));
    }

//    TODO: kell: getMe(); <<< Retourne l'utilisateur connecté
//    TODO: Valameyik methode-hoz kell az "email" cim is, amit sajat magam latok a "My Profile" ful alatt


    @Override
    public UserDto findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email is missing");
        }
        return userMapper.entityToDto(userDao.findByEmail(email));
    }

    @Override
    @Transactional //(readOnly = false) is the default
    public UserDto createUser(UserDto userDto) {
        if(userDto == null) {
            throw new IllegalArgumentException("User is obligatory");
        }
        val entity = userMapper.dtoToEntity(userDto);
        entity.setUuid(UUID.randomUUID());
        entity.setAlias(userDto.getAlias());
        entity.setEmail(userDto.getEmail());
        entity.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        At that 1st phase of development, we only create Users with role "USER"
        entity.setRole(RoleEntity.USER);
        val savedEntity = userDao.save(entity);

        return userMapper.entityToDto(savedEntity);
    }

    @Override
    @Transactional //(readOnly = false)
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID user missing");
        }
        val existingEntity = userDao.getUserById(id);
        if (existingEntity == null) {
            throw new IllegalArgumentException("User unknown : " + id);
        }

        userDao.delete(existingEntity);
    }

    @Override
//    TODO: no need for @Transactional, right?
    public void loginUser(UserDto userDto) throws Exception {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials");
        }
    }
}
