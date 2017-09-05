package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.WebDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Web and its DTO WebDTO.
 */
@Mapper(componentModel = "spring", uses = {WebTypeMapper.class, })
public interface WebMapper extends EntityMapper <WebDTO, Web> {
    
    @Mapping(target = "edges", ignore = true)
    Web toEntity(WebDTO webDTO); 
    default Web fromId(Long id) {
        if (id == null) {
            return null;
        }
        Web web = new Web();
        web.setId(id);
        return web;
    }
}
