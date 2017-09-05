package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.WebTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WebType and its DTO WebTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkspaceMapper.class, })
public interface WebTypeMapper extends EntityMapper <WebTypeDTO, WebType> {

    @Mapping(source = "workspace.id", target = "workspaceId")
    WebTypeDTO toDto(WebType webType); 

    @Mapping(source = "workspaceId", target = "workspace")
    WebType toEntity(WebTypeDTO webTypeDTO); 
    default WebType fromId(Long id) {
        if (id == null) {
            return null;
        }
        WebType webType = new WebType();
        webType.setId(id);
        return webType;
    }
}
