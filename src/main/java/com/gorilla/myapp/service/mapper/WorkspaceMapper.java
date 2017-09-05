package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.WorkspaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Workspace and its DTO WorkspaceDTO.
 */
@Mapper(componentModel = "spring", uses = {MapMapper.class, UserMapper.class, })
public interface WorkspaceMapper extends EntityMapper <WorkspaceDTO, Workspace> {

    @Mapping(source = "initMap.id", target = "initMapId")

    @Mapping(source = "owner.id", target = "ownerId")
    WorkspaceDTO toDto(Workspace workspace); 
    @Mapping(target = "configs", ignore = true)
    @Mapping(target = "maps", ignore = true)

    @Mapping(source = "initMapId", target = "initMap")

    @Mapping(source = "ownerId", target = "owner")
    Workspace toEntity(WorkspaceDTO workspaceDTO); 
    default Workspace fromId(Long id) {
        if (id == null) {
            return null;
        }
        Workspace workspace = new Workspace();
        workspace.setId(id);
        return workspace;
    }
}
