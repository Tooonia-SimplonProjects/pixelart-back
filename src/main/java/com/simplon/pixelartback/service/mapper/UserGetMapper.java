package com.simplon.pixelartback.service.mapper;

import com.simplon.pixelartback.storage.dto.UserGetDto;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

/**
 * We need to define an empty mapping method for each of the DTO used.
 * So for UserGetDto as well.
 */
@Mapper(componentModel = "spring")
public interface UserGetMapper extends AbstractMapper<UserEntity, UserGetDto>{
    @Override
    @Mapping(target = "email", source = "email", ignore = true)
    UserEntity dtoToEntity(UserGetDto dto);

    @Override
//    @Mapping(target = "email", source = "email", ignore = true)
    void dtoToEntity(UserGetDto dto, @MappingTarget UserEntity entity);

    @Override
//    @Mapping(target = "email", source = "email", ignore = true)
    List<UserEntity> dtosToEntities(List<UserGetDto> dtos);

    @Override
//    @Mapping(target = "email", source = "email", ignore = true)
    UserGetDto entityToDto(UserEntity entity);

    @Override
//    @Mapping(target = "email", source = "email", ignore = true)
    List<UserGetDto> entitiesToDtos(Collection<UserEntity> entities);
}
