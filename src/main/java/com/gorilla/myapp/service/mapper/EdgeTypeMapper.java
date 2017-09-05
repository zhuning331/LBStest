package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.EdgeTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EdgeType and its DTO EdgeTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EdgeTypeMapper extends EntityMapper <EdgeTypeDTO, EdgeType> {
    
    
    default EdgeType fromId(Long id) {
        if (id == null) {
            return null;
        }
        EdgeType edgeType = new EdgeType();
        edgeType.setId(id);
        return edgeType;
    }
}
