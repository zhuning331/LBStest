package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.EdgeTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EdgeType.
 */
public interface EdgeTypeService {

    /**
     * Save a edgeType.
     *
     * @param edgeTypeDTO the entity to save
     * @return the persisted entity
     */
    EdgeTypeDTO save(EdgeTypeDTO edgeTypeDTO);

    /**
     *  Get all the edgeTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EdgeTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" edgeType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EdgeTypeDTO findOne(Long id);

    /**
     *  Delete the "id" edgeType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the edgeType corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EdgeTypeDTO> search(String query, Pageable pageable);
}
