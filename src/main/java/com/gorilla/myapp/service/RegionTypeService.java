package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.RegionTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RegionType.
 */
public interface RegionTypeService {

    /**
     * Save a regionType.
     *
     * @param regionTypeDTO the entity to save
     * @return the persisted entity
     */
    RegionTypeDTO save(RegionTypeDTO regionTypeDTO);

    /**
     *  Get all the regionTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RegionTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" regionType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RegionTypeDTO findOne(Long id);

    /**
     *  Delete the "id" regionType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the regionType corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RegionTypeDTO> search(String query, Pageable pageable);
}
