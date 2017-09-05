package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.POIDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing POI.
 */
public interface POIService {

    /**
     * Save a pOI.
     *
     * @param pOIDTO the entity to save
     * @return the persisted entity
     */
    POIDTO save(POIDTO pOIDTO);

    /**
     *  Get all the pOIS.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<POIDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pOI.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    POIDTO findOne(Long id);

    /**
     *  Delete the "id" pOI.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pOI corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<POIDTO> search(String query, Pageable pageable);
}
