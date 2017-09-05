package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.POITypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing POIType.
 */
public interface POITypeService {

    /**
     * Save a pOIType.
     *
     * @param pOITypeDTO the entity to save
     * @return the persisted entity
     */
    POITypeDTO save(POITypeDTO pOITypeDTO);

    /**
     *  Get all the pOITypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<POITypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pOIType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    POITypeDTO findOne(Long id);

    /**
     *  Delete the "id" pOIType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pOIType corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<POITypeDTO> search(String query, Pageable pageable);
}
