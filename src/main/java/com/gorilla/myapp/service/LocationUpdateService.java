package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.LocationUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing LocationUpdate.
 */
public interface LocationUpdateService {

    /**
     * Save a locationUpdate.
     *
     * @param locationUpdateDTO the entity to save
     * @return the persisted entity
     */
    LocationUpdateDTO save(LocationUpdateDTO locationUpdateDTO);

    /**
     *  Get all the locationUpdates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LocationUpdateDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" locationUpdate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LocationUpdateDTO findOne(Long id);

    /**
     *  Delete the "id" locationUpdate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the locationUpdate corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LocationUpdateDTO> search(String query, Pageable pageable);
}
