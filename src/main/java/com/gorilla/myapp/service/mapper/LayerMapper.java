package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.LayerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Layer and its DTO LayerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LayerMapper extends EntityMapper <LayerDTO, Layer> {
    
    
    default Layer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Layer layer = new Layer();
        layer.setId(id);
        return layer;
    }
}
