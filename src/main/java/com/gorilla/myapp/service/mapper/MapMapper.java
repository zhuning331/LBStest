package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.MapDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Map and its DTO MapDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkspaceMapper.class, LayerMapper.class, WebMapper.class, RegionMapper.class, })
public interface MapMapper extends EntityMapper <MapDTO, Map> {

    @Mapping(source = "workspace.id", target = "workspaceId")

    @Mapping(source = "initLayer.id", target = "initLayerId")
    MapDTO toDto(Map map); 

    @Mapping(source = "workspaceId", target = "workspace")

    @Mapping(source = "initLayerId", target = "initLayer")
    Map toEntity(MapDTO mapDTO); 
    default Map fromId(Long id) {
        if (id == null) {
            return null;
        }
        Map map = new Map();
        map.setId(id);
        return map;
    }
}
