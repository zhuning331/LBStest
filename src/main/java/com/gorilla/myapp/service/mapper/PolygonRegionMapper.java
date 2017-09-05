package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.PolygonRegionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PolygonRegion and its DTO PolygonRegionDTO.
 */
@Mapper(componentModel = "spring", uses = {RegionMapper.class, })
public interface PolygonRegionMapper extends EntityMapper <PolygonRegionDTO, PolygonRegion> {

    @Mapping(source = "region.id", target = "regionId")
    PolygonRegionDTO toDto(PolygonRegion polygonRegion); 

    @Mapping(source = "regionId", target = "region")
    @Mapping(target = "nodes", ignore = true)
    PolygonRegion toEntity(PolygonRegionDTO polygonRegionDTO); 
    default PolygonRegion fromId(Long id) {
        if (id == null) {
            return null;
        }
        PolygonRegion polygonRegion = new PolygonRegion();
        polygonRegion.setId(id);
        return polygonRegion;
    }
}
