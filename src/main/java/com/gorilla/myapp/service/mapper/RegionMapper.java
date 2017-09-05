package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.RegionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Region and its DTO RegionDTO.
 */
@Mapper(componentModel = "spring", uses = {RegionTypeMapper.class, })
public interface RegionMapper extends EntityMapper <RegionDTO, Region> {
    
    @Mapping(target = "polygonRegions", ignore = true)
    @Mapping(target = "regularRegions", ignore = true)
    Region toEntity(RegionDTO regionDTO); 
    default Region fromId(Long id) {
        if (id == null) {
            return null;
        }
        Region region = new Region();
        region.setId(id);
        return region;
    }
}
