package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.NodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Node and its DTO NodeDTO.
 */
@Mapper(componentModel = "spring", uses = {PolygonRegionMapper.class, })
public interface NodeMapper extends EntityMapper <NodeDTO, Node> {

    @Mapping(source = "polygonRegion.id", target = "polygonRegionId")
    NodeDTO toDto(Node node); 

    @Mapping(source = "polygonRegionId", target = "polygonRegion")
    Node toEntity(NodeDTO nodeDTO); 
    default Node fromId(Long id) {
        if (id == null) {
            return null;
        }
        Node node = new Node();
        node.setId(id);
        return node;
    }
}
