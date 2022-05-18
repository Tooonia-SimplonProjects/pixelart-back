package com.simplon.pixelartback.service.pixelart.impl;

import com.simplon.pixelartback.service.mapper.UserMapper;
import com.simplon.pixelartback.storage.dao.UserDao;
import com.simplon.pixelartback.storage.dto.PixelArtDto;
import com.simplon.pixelartback.service.mapper.PixelArtMapper;
import com.simplon.pixelartback.service.pixelart.PixelArtService;
import com.simplon.pixelartback.storage.dao.PixelArtDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//    TODO: if exception / error to handle, is it already at that level? Because can't just write those at Controller?!
@Service
// A hint for the persistence provider that the transaction should be 'read only'; default value is "false"
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PixelArtServiceImpl implements PixelArtService {

//    How to write my code?
//    In "UserServiceImpl" : @Autowired private UserMapper usermapper; @Autowired private RoleDao roleDao;
//    In "ProjectServiceImpl" : private final ProjectMapper projectMapper; private final ProjectDao projectDao;

    @Autowired
    private PixelArtMapper pixelArtMapper;

    @Autowired
    private PixelArtDao pixelArtDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

//    @Autowired
//    public PixelArtServiceImpl(PixelArtDao pixelArtDao, PixelArtMapper pixelArtMapper) {
//        super();
//        this.pixelArtDao = pixelArtDao;
//        this.pixelArtMapper = pixelArtMapper;
//    }
    @Override
    public List<PixelArtDto> getAllPixelArt() throws EmptyResultDataAccessException { //TODO: good exception here?

        return pixelArtMapper.entitiesToDtos(pixelArtDao.findAll());
    }

    //    public PixelArtDto getPixelArtByUuid(UUID uuid) {
//        return pixelArtMapper.entityToDto(pixelArtDao.findByUuid(uuid));
//    }
    @Override
    public PixelArtDto getPixelArtById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id PixelArt is missing");
        }
        return pixelArtMapper.entityToDto(pixelArtDao.getPixelArtById(id));
    }

    @Override
    @Transactional //(readOnly = false) is the default
    public PixelArtDto createPixelArt(PixelArtDto pixelArtDto) {
        if (pixelArtDto == null) {
            throw new IllegalArgumentException("PixelArt is obligatory");
        }
//      Using Lombok 'val' declares the variable as final and automatically infers the type after initializing it:
        val entity = pixelArtMapper.dtoToEntity(pixelArtDto); // Meaning/utility of 'processor' in ProjectServiceImpl?
//        entity.setIdPixelArt(pixelArtDto.getUuidPixelArt());
//        entity.setUuid(UUID.randomUUID());
//    TODO: val userEntity? and operation on it, something of that kind:
//        val userEntity = userDao.getById(pixelArtDto.getUserEntity().getId());
//        if(userEntity == null) {}

//        TODO: why not set/map all the parameters here? according to Dto's values? (getProductImage() does not even exist on pixelArtDto!)
        val savedEntity = pixelArtDao.save(entity);

        return pixelArtMapper.entityToDto(savedEntity);
    }

    @Override
    @Transactional
    public PixelArtDto updatePixelArt(PixelArtDto pixelArtDto) {
        if (pixelArtDto.getId() == null) {
            throw new IllegalArgumentException("ID pixelArt missing");
        }
//        val entity = pixelArtMapper.dtoToEntity(pixelArtDto); // According to UserServiceImpl no need for that!
        val existingEntity = pixelArtDao.getPixelArtById(pixelArtDto.getId());
//        System.out.println(pixelArtDto.getId());
//        System.out.println(existingEntity);
//        TODO: why this if statement is always false if written with 'existingEntity' that is used in UserServiceImpl line 186?
        if (existingEntity == null) {
            throw new IllegalArgumentException("PixelArt unknown : " + pixelArtDto.getId());
        }
//        Updating the existing entity with pixelArtDto values:
        pixelArtMapper.dtoToEntity(pixelArtDto, existingEntity);
//        val savedEntity = pixelArtDao.save(existingEntity);
        pixelArtDao.save(existingEntity);

        return pixelArtMapper.entityToDto(existingEntity);
    }

    @Override
    @Transactional
    public void deletePixelArt(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID pixelArt missing");
        }
        val existingEntity = pixelArtDao.getPixelArtById(id);
        if (existingEntity == null) {
            throw new IllegalArgumentException("PixelArt unknown : " + id);
        }

        pixelArtDao.delete(existingEntity);
    }
}
