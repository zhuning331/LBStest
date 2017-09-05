package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.PolygonRegionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PolygonRegion.
 */
public interface PolygonRegionService {

    /**
     * Save a polygonRegion.
     *
     * @param polygonRegionDTO the entity to save
     * @return the persisted entity
     */
    PolygonRegionDTO save(PolygonRegionDTO polygonRegionDTO);

    /**
     *  Get all the polygonRegions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PolygonRegionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" polygonRegion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PolygonRegionDTO findOne(Long id);

    /**
     *  Delete the "id" polygonRegion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the polygonRegion corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PolygonRegionDTO> search(String query, Pageable pageable);
}
