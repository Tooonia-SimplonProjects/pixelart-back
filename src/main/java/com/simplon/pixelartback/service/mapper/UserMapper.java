package com.simplon.pixelartback.service.mapper;

import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<UserEntity, UserDto> {

    @Override
    UserEntity dtoToEntity(UserDto dto);

    @Override
    void dtoToEntity(UserDto dto, @MappingTarget UserEntity entity);

    @Override
    List<UserEntity> dtosToEntities(List<UserDto> dtos);

    @Override
    UserDto entityToDto(UserEntity entity);

    @Override
    List<UserDto> entitiesToDtos(Collection<UserEntity> entities);
}
