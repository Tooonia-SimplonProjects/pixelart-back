package com.simplon.pixelartback.service.mapper;

import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends AbstractMapper<UserEntity, UserDto> {

    @Override
    UserEntity dtoToEntity(UserDto dto);

    @Override
//    TODO:  elvileg ide is kell  ez a @Mapping: source: UserFullMapper
    @Mapping(target = "password", source = "password", ignore = true)
    void dtoToEntity(UserDto dto, @MappingTarget UserEntity entity);

//    TODO: elvileg no need for this
    @Override
    List<UserEntity> dtosToEntities(List<UserDto> dtos);

//    By the @Mapping ignore=true, we are protecting the password
    @Override
    @Mapping(target = "password", source = "password", ignore = true)
    UserDto entityToDto(UserEntity entity);

    //    TODO: elvileg no need for this
    @Override
    List<UserDto> entitiesToDtos(Collection<UserEntity> entities);
}
