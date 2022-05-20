package com.simplon.pixelartback.service.user.impl;

import com.simplon.pixelartback.service.mapper.UserForPixelArtMapper;
import com.simplon.pixelartback.service.mapper.UserGetMapper;
import com.simplon.pixelartback.service.mapper.UserMapper;
import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dao.UserDao;
import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserForPixelArtDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Override
    public List<UserGetDto> getAllUsers() throws EmptyResultDataAccessException {
        return userGetMapper.entitiesToDtos(userDao.findAll());
    }

    @Override
    public UserGetDto getUserByUuid(UUID uuid) {
        if(uuid == null) {
            throw new IllegalArgumentException("UUID User is missing");
        }
        return userGetMapper.entityToDto(userDao.findByUuid(uuid));
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

    @Override
    @Transactional //(readOnly = false) is the default
    public UserDto createUser(UserDto userDto) {
        if(userDto == null) {
            throw new IllegalArgumentException("User is obligatory");
        }
        val entity = userMapper.dtoToEntity(userDto);
        entity.setUuid(UUID.randomUUID());
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
}
