package com.gorilla.myapp.service.mapper;

import com.gorilla.myapp.domain.*;
import com.gorilla.myapp.service.dto.ServiceConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ServiceConfig and its DTO ServiceConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceConfigMapper extends EntityMapper <ServiceConfigDTO, ServiceConfig> {
    
    
    default ServiceConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setId(id);
        return serviceConfig;
    }
}
