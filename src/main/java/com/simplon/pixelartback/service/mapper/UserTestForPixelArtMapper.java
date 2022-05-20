package com.simplon.pixelartback.service.mapper;

import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserTestForPixelArtMapper extends AbstractMapper<UserEntity, UserDto> {

    @Override
//    @Mapping(target = "user_email", source = "user_email", ignore = true)
//    @Mapping(target = "user_password", source = "user_password", ignore = true)
//    @Mapping(target = "email", source = "email", ignore = true)
//    @Mapping(target = "password", source = "password", ignore = true)
    UserEntity dtoToEntity(UserDto dto);

    @Override
//    @Mapping(target = "user_email", source = "user_email", ignore = true)
//    @Mapping(target = "user_password", source = "user_password", ignore = true)
    UserDto entityToDto(UserEntity entity);
}
