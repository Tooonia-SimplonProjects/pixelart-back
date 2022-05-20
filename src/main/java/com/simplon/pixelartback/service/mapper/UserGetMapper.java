package com.simplon.pixelartback.service.mapper;

import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserGetMapper extends AbstractMapper<UserEntity, UserGetDto>{
    //    We need to define an empty mapping method for each of the DTO used.
    //    So for UserGetDto as well.
    @Override
    UserEntity dtoToEntity(UserGetDto dto);

    @Override
    void dtoToEntity(UserGetDto dto, @MappingTarget UserEntity entity);

    @Override
    List<UserEntity> dtosToEntities(List<UserGetDto> dtos);

    @Override
    UserGetDto entityToDto(UserEntity entity);

    @Override
    List<UserGetDto> entitiesToDtos(Collection<UserEntity> entities);
}
