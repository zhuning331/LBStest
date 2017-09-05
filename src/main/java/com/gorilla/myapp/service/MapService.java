package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.MapDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Map.
 */
public interface MapService {

    /**
     * Save a map.
     *
     * @param mapDTO the entity to save
     * @return the persisted entity
     */
    MapDTO save(MapDTO mapDTO);

    /**
     *  Get all the maps.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MapDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" map.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MapDTO findOne(Long id);

    /**
     *  Delete the "id" map.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the map corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MapDTO> search(String query, Pageable pageable);
}
