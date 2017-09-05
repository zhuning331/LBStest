package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.EdgeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Edge and its DTO EdgeDTO.
 */
@Mapper(componentModel = "spring", uses = {NodeMapper.class, EdgeTypeMapper.class, WebMapper.class, })
public interface EdgeMapper extends EntityMapper <EdgeDTO, Edge> {

    @Mapping(source = "from.id", target = "fromId")

    @Mapping(source = "to.id", target = "toId")

    @Mapping(source = "web.id", target = "webId")
    EdgeDTO toDto(Edge edge); 

    @Mapping(source = "fromId", target = "from")

    @Mapping(source = "toId", target = "to")

    @Mapping(source = "webId", target = "web")
    Edge toEntity(EdgeDTO edgeDTO); 
    default Edge fromId(Long id) {
        if (id == null) {
            return null;
        }
        Edge edge = new Edge();
        edge.setId(id);
        return edge;
    }
}
