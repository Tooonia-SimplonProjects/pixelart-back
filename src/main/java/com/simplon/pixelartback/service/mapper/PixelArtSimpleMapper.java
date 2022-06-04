package com.simplon.pixelartback.service.mapper;

import com.simplon.pixelartback.storage.dto.PixelArtSimpleDto;
import com.simplon.pixelartback.storage.entity.pixelart.PixelArtEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PixelArtSimpleMapper extends AbstractMapper<PixelArtEntity, PixelArtSimpleDto> {

    @Override
    PixelArtEntity dtoToEntity(PixelArtSimpleDto dto);

    @Override
    void dtoToEntity(PixelArtSimpleDto dto, @MappingTarget PixelArtEntity entity);

    @Override
    List<PixelArtEntity> dtosToEntities(List<PixelArtSimpleDto> dtos);

    @Override
    PixelArtSimpleDto entityToDto(PixelArtEntity entity);

    @Override
    List<PixelArtSimpleDto> entitiesToDtos(Collection<PixelArtEntity> entities);
}
