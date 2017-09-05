package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.RegularRegionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RegularRegion.
 */
public interface RegularRegionService {

    /**
     * Save a regularRegion.
     *
     * @param regularRegionDTO the entity to save
     * @return the persisted entity
     */
    RegularRegionDTO save(RegularRegionDTO regularRegionDTO);

    /**
     *  Get all the regularRegions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RegularRegionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" regularRegion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RegularRegionDTO findOne(Long id);

    /**
     *  Delete the "id" regularRegion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the regularRegion corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RegularRegionDTO> search(String query, Pageable pageable);
}
