package com.simplon.pixelartback.service.pixelart.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.simplon.pixelartback.storage.dto.pixelart.PixelArtDto;
import com.simplon.pixelartback.service.mapper.PixelArtMapper;
import com.simplon.pixelartback.service.pixelart.PixelArtService;
import com.simplon.pixelartback.storage.dao.pixelart.PixelArtDao;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
//@Transactional(readOnly = true) //TODO needed?
//@RequiredArgsConstructor //TODO needed?
@Slf4j //TODO needed?
public class PixelArtServiceImpl implements PixelArtService {

//    How to write my code?
//    TODO: UserServiceImpl : @Autowired private UserMapper usermapper; @Autowired private RoleDao roleDao;
//    TODO: ProjectServiceImpl : private final ProjectMapper projectMapper; private final ProjectDao projectDao;

//    private final PixelArtMapper pixelArtMapper;
    @Autowired
    private PixelArtMapper pixelArtMapper;
//    private final PixelArtDao pixelArtDao;
    @Autowired
    private PixelArtDao pixelArtDao;

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

    @Override
//    public PixelArtDto getPixelArtByUuid(UUID uuid) {
//        return pixelArtMapper.entityToDto(pixelArtDao.findByUuid(uuid));
//    }
//    public PixelArtDto getPixelArtById(Long id) throws IllegalArgumentException { //TODO: good exception here?
    public PixelArtDto getPixelArtById(Long id) { //TODO: good exception here?
        if (id == null) {
            throw new IllegalArgumentException("Id PixelArt is missing");
        }
        return pixelArtMapper.entityToDto(pixelArtDao.getPixelArtById(id));
    }

//    TODO: if exception / error to handle, is it already here? Because can't just write those at Controller?!
    @Override
//    @Transactional(readOnly = false) //TODO: needed?
    public PixelArtDto createPixelArt(PixelArtDto pixelArtDto) {
        if (pixelArtDto == null) {
            throw new IllegalArgumentException("PixelArt is obligatory");
        }
//      Using Lombok 'val' declares the variable as final and automatically infers the type after initializing it:
        val entity = pixelArtMapper.dtoToEntity(pixelArtDto); //TODO: meaning/utility of 'processor' in ProjectServiceImpl?
//        entity.setIdPixelArt(pixelArtDto.getUuidPixelArt());
//        entity.setUuid(UUID.randomUUID());
//        TODO: why not to set/map all the parameters here? according to Dto's values? (getProductImage() does not even exist on pixelArtDto!)
        val savedEntity = pixelArtDao.save(entity);
        //        TODO: try-catch needed?
        return pixelArtMapper.entityToDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public PixelArtDto updatePixelArt(PixelArtDto pixelArtDto) {
        if (pixelArtDto.getId() == null) {
            throw new IllegalArgumentException("ID pixelArt missing");
        }
//        val entity = pixelArtMapper.dtoToEntity(pixelArtDto); //TODO: according to UserServiceImpl no need for that!
        val existingEntity = pixelArtDao.getPixelArtById(pixelArtDto.getId());
        System.out.println(pixelArtDto.getId());
        System.out.println(existingEntity);
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
//    @Transactional(readOnly = false)
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
