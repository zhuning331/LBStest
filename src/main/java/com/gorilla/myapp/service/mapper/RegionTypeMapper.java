package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.RegionTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RegionType and its DTO RegionTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkspaceMapper.class, })
public interface RegionTypeMapper extends EntityMapper <RegionTypeDTO, RegionType> {

    @Mapping(source = "workspace.id", target = "workspaceId")
    RegionTypeDTO toDto(RegionType regionType); 

    @Mapping(source = "workspaceId", target = "workspace")
    RegionType toEntity(RegionTypeDTO regionTypeDTO); 
    default RegionType fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegionType regionType = new RegionType();
        regionType.setId(id);
        return regionType;
    }
}
