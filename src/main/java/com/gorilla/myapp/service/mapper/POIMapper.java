package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.POIDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity POI and its DTO POIDTO.
 */
@Mapper(componentModel = "spring", uses = {POITypeMapper.class, })
public interface POIMapper extends EntityMapper <POIDTO, POI> {
    
    
    default POI fromId(Long id) {
        if (id == null) {
            return null;
        }
        POI pOI = new POI();
        pOI.setId(id);
        return pOI;
    }
}
