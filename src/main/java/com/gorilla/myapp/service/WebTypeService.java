package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.WebTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing WebType.
 */
public interface WebTypeService {

    /**
     * Save a webType.
     *
     * @param webTypeDTO the entity to save
     * @return the persisted entity
     */
    WebTypeDTO save(WebTypeDTO webTypeDTO);

    /**
     *  Get all the webTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WebTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" webType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WebTypeDTO findOne(Long id);

    /**
     *  Delete the "id" webType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the webType corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WebTypeDTO> search(String query, Pageable pageable);
}
