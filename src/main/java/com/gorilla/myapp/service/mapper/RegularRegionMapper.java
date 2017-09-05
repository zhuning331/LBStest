package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.RegularRegionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RegularRegion and its DTO RegularRegionDTO.
 */
@Mapper(componentModel = "spring", uses = {RegionMapper.class, })
public interface RegularRegionMapper extends EntityMapper <RegularRegionDTO, RegularRegion> {

    @Mapping(source = "region.id", target = "regionId")
    RegularRegionDTO toDto(RegularRegion regularRegion); 

    @Mapping(source = "regionId", target = "region")
    RegularRegion toEntity(RegularRegionDTO regularRegionDTO); 
    default RegularRegion fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegularRegion regularRegion = new RegularRegion();
        regularRegion.setId(id);
        return regularRegion;
    }
}
