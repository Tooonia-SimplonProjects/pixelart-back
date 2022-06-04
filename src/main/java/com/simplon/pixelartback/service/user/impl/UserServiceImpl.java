package com.simplon.pixelartback.service.user.impl;

import com.simplon.pixelartback.facade.security.PasswordHelper;
import com.simplon.pixelartback.service.mapper.UserGetMapper;
import com.simplon.pixelartback.service.mapper.UserMapper;
import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dao.UserDao;
import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;
import com.simplon.pixelartback.storage.entity.role.RoleEntity;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
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
    private UserDao userDao;

    @Autowired
    private PasswordHelper passwordHelper;

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

    /**
     * This method does not have a separate endpoint in the UserController, only helps here in this UserServiceImpl class.
     */
        @Override
    public UserDto findByEmail(String email, boolean withPassword) {
        if (email == null) {
            throw new IllegalArgumentException("Email is missing");
        }
        UserEntity userEntity = userDao.findByEmail(email);
        UserDto result = userMapper.entityToDto(userEntity);
        if (result != null && withPassword) {
            result.setPassword(userEntity.getPassword());
        }
        return result;
    }
    @Override
    public UserDto findByAlias(String alias) {
        if (alias == null) {
            throw new IllegalArgumentException("Alias is missing");
        }
        UserEntity userEntity = userDao.findByAlias(alias);

        return userMapper.entityToDto(userEntity);
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
        if (!userDto.getPassword().isEmpty()) {
            entity.setPassword(passwordHelper.encodePassword(userDto.getPassword()));
        }
        // At that 1st phase of development, we only create Users with role "USER"
        entity.setRole(RoleEntity.USER);
        val savedEntity = userDao.save(entity);
//        System.out.println(entity.getRole()); <<< right value here : USER
        return userMapper.entityToDto(savedEntity);
    }

//    TODO: updateUser ha lesz idom!!! <<< /my-profile -ban akkor valtoztathatom az alias-t,
//     password-ot! Kell hozza a valideEntity() method is! source: UserServiceImpl

    @Override
    @Transactional //(readOnly = false)
    public void deleteUser(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID user missing");
        }
        val existingEntity = userDao.findByUuid(uuid);
        if (existingEntity == null) {
            throw new IllegalArgumentException("User unknown : " + uuid);
        }
//        TODO: does not work when @PreAuthorize("hasAnyRole('USER')") on method in Controller,
//         de valoszinuleg meg nem volt kesz a method, ami leveszi a "ROLE_3 prefixet!
//        if (existingEntity.getRole().toString() == "USER") {
//        if (Objects.equals(existingEntity.getRole().toString(), "USER")) {
            userDao.delete(existingEntity);
    }

//    @Override
////    No need for @Transactional, right?
//    public void loginUser(UserDto userDto) throws Exception {
//        if(userDto == null) {
//            throw new IllegalArgumentException("User is obligatory");
//        }
//        val entity = userMapper.dtoToEntity(userDto);
//        Authentication authentication;
//        try {
//            authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(entity.getEmail(), entity.getPassword()));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (BadCredentialsException e) {
//            throw new Exception("Invalid credentials");
//        }
//    }
}
