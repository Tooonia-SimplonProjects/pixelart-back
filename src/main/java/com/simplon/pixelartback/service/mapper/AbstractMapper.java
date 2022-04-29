package com.simplon.pixelartback.service.mapper;

import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

/**
 * Abstract class of all mappers
 *
 * @param <E>
 * @param <D>
 */
public interface AbstractMapper<E, D> {

    /**
     *
     * @param dto to transform to entity
     * @return entity
     */
    E dtoToEntity(D dto);

    /**
     * Update entity with dto
     * @param dto
     * @param entity
     */
    void dtoToEntity(D dto, @MappingTarget E entity);

    /**
     *
     * @param dtos
     * @return the list of the converted entities
     */
    List<E> dtosToEntities(List<D> dtos);

    /**
     *
     * @param entity to transfor to dto
     * @return dto
     */
    D entityToDto(E entity);

    /**
     *
     * @param entities
     * @return the list of the converted dtos
     */
    List<D> entitiesToDtos(Collection<E> entities);

    //TODO needed?: default Page<D> entitiesToDtos(Page<E> entities, Pageable pageable) {}
}
