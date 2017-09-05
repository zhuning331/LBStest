package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.BindingPOIDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BindingPOI.
 */
public interface BindingPOIService {

    /**
     * Save a bindingPOI.
     *
     * @param bindingPOIDTO the entity to save
     * @return the persisted entity
     */
    BindingPOIDTO save(BindingPOIDTO bindingPOIDTO);

    /**
     *  Get all the bindingPOIS.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BindingPOIDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" bindingPOI.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BindingPOIDTO findOne(Long id);

    /**
     *  Delete the "id" bindingPOI.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the bindingPOI corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BindingPOIDTO> search(String query, Pageable pageable);
}
