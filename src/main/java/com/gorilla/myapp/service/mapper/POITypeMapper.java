package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.POITypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity POIType and its DTO POITypeDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkspaceMapper.class, })
public interface POITypeMapper extends EntityMapper <POITypeDTO, POIType> {

    @Mapping(source = "workspace.id", target = "workspaceId")
    POITypeDTO toDto(POIType pOIType); 

    @Mapping(source = "workspaceId", target = "workspace")
    POIType toEntity(POITypeDTO pOITypeDTO); 
    default POIType fromId(Long id) {
        if (id == null) {
            return null;
        }
        POIType pOIType = new POIType();
        pOIType.setId(id);
        return pOIType;
    }
}
