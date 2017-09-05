package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.POIHistoricalLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity POIHistoricalLocation and its DTO POIHistoricalLocationDTO.
 */
@Mapper(componentModel = "spring", uses = {POITypeMapper.class, POIMapper.class, })
public interface POIHistoricalLocationMapper extends EntityMapper <POIHistoricalLocationDTO, POIHistoricalLocation> {

    @Mapping(source = "type.id", target = "typeId")

    @Mapping(source = "poi.id", target = "poiId")
    POIHistoricalLocationDTO toDto(POIHistoricalLocation pOIHistoricalLocation); 

    @Mapping(source = "typeId", target = "type")

    @Mapping(source = "poiId", target = "poi")
    POIHistoricalLocation toEntity(POIHistoricalLocationDTO pOIHistoricalLocationDTO); 
    default POIHistoricalLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        POIHistoricalLocation pOIHistoricalLocation = new POIHistoricalLocation();
        pOIHistoricalLocation.setId(id);
        return pOIHistoricalLocation;
    }
}
