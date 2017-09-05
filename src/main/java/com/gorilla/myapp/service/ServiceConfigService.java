package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.ServiceConfigDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ServiceConfig.
 */
public interface ServiceConfigService {

    /**
     * Save a serviceConfig.
     *
     * @param serviceConfigDTO the entity to save
     * @return the persisted entity
     */
    ServiceConfigDTO save(ServiceConfigDTO serviceConfigDTO);

    /**
     *  Get all the serviceConfigs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ServiceConfigDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" serviceConfig.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ServiceConfigDTO findOne(Long id);

    /**
     *  Delete the "id" serviceConfig.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the serviceConfig corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ServiceConfigDTO> search(String query, Pageable pageable);
}
