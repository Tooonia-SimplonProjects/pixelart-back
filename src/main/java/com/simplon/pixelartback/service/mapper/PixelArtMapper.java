package com.simplon.pixelartback.service.mapper;

import com.simplon.pixelartback.storage.dto.PixelArtDto;
import com.simplon.pixelartback.storage.entity.pixelart.PixelArtEntity;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PixelArtMapper extends AbstractMapper<PixelArtEntity, PixelArtDto> {

    @Override
    PixelArtEntity dtoToEntity(PixelArtDto dto);

    @Override
    void dtoToEntity(PixelArtDto dto, @MappingTarget PixelArtEntity entity);

    @Override
    List<PixelArtEntity> dtosToEntities(List<PixelArtDto> dtos);

    @Override
    PixelArtDto entityToDto(PixelArtEntity entity);

    @Override
    List<PixelArtDto> entitiesToDtos(Collection<PixelArtEntity> entities);
}
