package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.WorkspaceConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WorkspaceConfig and its DTO WorkspaceConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkspaceMapper.class, })
public interface WorkspaceConfigMapper extends EntityMapper <WorkspaceConfigDTO, WorkspaceConfig> {

    @Mapping(source = "workspace.id", target = "workspaceId")
    WorkspaceConfigDTO toDto(WorkspaceConfig workspaceConfig); 

    @Mapping(source = "workspaceId", target = "workspace")
    WorkspaceConfig toEntity(WorkspaceConfigDTO workspaceConfigDTO); 
    default WorkspaceConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkspaceConfig workspaceConfig = new WorkspaceConfig();
        workspaceConfig.setId(id);
        return workspaceConfig;
    }
}
