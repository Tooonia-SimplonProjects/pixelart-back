package com.simplon.pixelartback.service.mapper;

import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserForPixelArtDto;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserForPixelArtMapper extends AbstractMapper<UserEntity, UserForPixelArtDto> {

    @Override
//    @Mapping(target = "user_email", source = "user_email", ignore = true)
//    @Mapping(target = "user_password", source = "user_password", ignore = true)
//    @Mapping(target = "email", source = "email", ignore = true)
//    @Mapping(target = "password", source = "password", ignore = true)
    UserEntity dtoToEntity(UserForPixelArtDto dto);

    @Override
    void dtoToEntity(UserForPixelArtDto dto, @MappingTarget UserEntity entity);

    @Override
    List<UserEntity> dtosToEntities(List<UserForPixelArtDto> dtos);

    @Override
    UserForPixelArtDto entityToDto(UserEntity entity);

    @Override
    List<UserForPixelArtDto> entitiesToDtos(Collection<UserEntity> entities);
}
