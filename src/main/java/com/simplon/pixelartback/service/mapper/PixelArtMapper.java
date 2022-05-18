package com.simplon.pixelartback.service.mapper;

import com.simplon.pixelartback.storage.dto.PixelArtDto;
import com.simplon.pixelartback.storage.entity.pixelart.PixelArtEntity;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
//@Component
public interface PixelArtMapper extends AbstractMapper<PixelArtEntity, PixelArtDto> {

//    PixelArtMapper INSTANCE = Mappers.getMapper(PixelArtMapper.class);
    @Override
//    @InheritInverseConfiguration
//    @Mapping(target = "idPixelArt", source = "idPixelArt", ignore = true)
//    @Mapping(target = "productName", source = "productName", ignore = true)
//    @Mapping(target = "productImage", source = "productImage", ignore = true)
    PixelArtEntity dtoToEntity(PixelArtDto dto);

    @Override
//    @InheritInverseConfiguration
//    @Mapping(target = "idPixelArt", source = "idPixelArt", ignore = true)
//    @Mapping(target = "productName", source = "productName", ignore = true)
//    @Mapping(target = "productImage", source = "productImage", ignore = true)
    void dtoToEntity(PixelArtDto dto, @MappingTarget PixelArtEntity entity);

    @Override
//    @InheritInverseConfiguration
//    @Mapping(target = "idPixelArt", source = "idPixelArt", ignore = true)
//    @Mapping(target = "productName", source = "productName", ignore = true)
//    @Mapping(target = "productImage", source = "productImage", ignore = true)
    List<PixelArtEntity> dtosToEntities(List<PixelArtDto> dtos);

    @Override
//    @Mapping(target = "idPixelArt", source = "idPixelArt", ignore = true)
//    @Mapping(target = "productName", source = "productName", ignore = true)
//    @Mapping(target = "productImage", source = "productImage", ignore = true)
    PixelArtDto entityToDto(PixelArtEntity entity);

    @Override
//    @Mapping(target = "idPixelArt", source = "idPixelArt", ignore = true)
//    @Mapping(target = "productName", source = "productName", ignore = true)
//    @Mapping(target = "productImage", source = "productImage", ignore = true)
    List<PixelArtDto> entitiesToDtos(Collection<PixelArtEntity> entities);
}
