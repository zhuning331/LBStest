package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.LocationUpdateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LocationUpdate and its DTO LocationUpdateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocationUpdateMapper extends EntityMapper <LocationUpdateDTO, LocationUpdate> {
    
    
    default LocationUpdate fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocationUpdate locationUpdate = new LocationUpdate();
        locationUpdate.setId(id);
        return locationUpdate;
    }
}
