package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.BindingPOIDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BindingPOI and its DTO BindingPOIDTO.
 */
@Mapper(componentModel = "spring", uses = {POIMapper.class, })
public interface BindingPOIMapper extends EntityMapper <BindingPOIDTO, BindingPOI> {

    @Mapping(source = "poi.id", target = "poiId")
    BindingPOIDTO toDto(BindingPOI bindingPOI); 

    @Mapping(source = "poiId", target = "poi")
    BindingPOI toEntity(BindingPOIDTO bindingPOIDTO); 
    default BindingPOI fromId(Long id) {
        if (id == null) {
            return null;
        }
        BindingPOI bindingPOI = new BindingPOI();
        bindingPOI.setId(id);
        return bindingPOI;
    }
}
