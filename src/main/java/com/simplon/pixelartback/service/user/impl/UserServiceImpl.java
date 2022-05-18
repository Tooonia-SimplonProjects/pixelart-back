package com.simplon.pixelartback.service.user.impl;

import com.simplon.pixelartback.service.mapper.UserMapper;
import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dao.UserDao;
import com.simplon.pixelartback.storage.dto.UserDto;
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
    private UserDao userDao;

    @Override
    public List<UserDto> getAllUsers() throws EmptyResultDataAccessException {
        return userMapper.entitiesToDtos(userDao.findAll());
    }

    @Override
    public UserDto getUserByUuid(UUID uuid) {
        if(uuid == null) {
            throw new IllegalArgumentException("UUID User is missing");
        }
        return userMapper.entityToDto(userDao.findByUuid(uuid));
    }

    @Override
    public UserDto getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id PixelArt is missing");
        }
        return userMapper.entityToDto(userDao.getUserById(id));
    }

    @Override
    public UserGetDto getSimplifiedUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id PixelArt is missing");
        }
        return userMapper.entityToGetDto(userDao.getUserById(id));
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
}
