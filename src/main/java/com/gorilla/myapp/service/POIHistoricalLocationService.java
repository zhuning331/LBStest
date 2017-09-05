package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.POIHistoricalLocationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing POIHistoricalLocation.
 */
public interface POIHistoricalLocationService {

    /**
     * Save a pOIHistoricalLocation.
     *
     * @param pOIHistoricalLocationDTO the entity to save
     * @return the persisted entity
     */
    POIHistoricalLocationDTO save(POIHistoricalLocationDTO pOIHistoricalLocationDTO);

    /**
     *  Get all the pOIHistoricalLocations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<POIHistoricalLocationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pOIHistoricalLocation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    POIHistoricalLocationDTO findOne(Long id);

    /**
     *  Delete the "id" pOIHistoricalLocation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pOIHistoricalLocation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<POIHistoricalLocationDTO> search(String query, Pageable pageable);
}
