package com.simplon.pixelartback.service.mapper;

import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends AbstractMapper<UserEntity, UserDto> {

    @Override
    UserEntity dtoToEntity(UserDto dto);

    @Override
//    TODO: valoszinuleg ide is kell majd ez a @Mapping:
//    @Mapping(target = "password", source = "password", ignore = true)
    void dtoToEntity(UserDto dto, @MappingTarget UserEntity entity);

    @Override
    List<UserEntity> dtosToEntities(List<UserDto> dtos);

    @Override
    @Mapping(target = "password", source = "password", ignore = true)
    UserDto entityToDto(UserEntity entity);

    @Override
    List<UserDto> entitiesToDtos(Collection<UserEntity> entities);
}
