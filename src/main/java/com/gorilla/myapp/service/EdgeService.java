package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.EdgeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Edge.
 */
public interface EdgeService {

    /**
     * Save a edge.
     *
     * @param edgeDTO the entity to save
     * @return the persisted entity
     */
    EdgeDTO save(EdgeDTO edgeDTO);

    /**
     *  Get all the edges.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EdgeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" edge.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EdgeDTO findOne(Long id);

    /**
     *  Delete the "id" edge.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the edge corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EdgeDTO> search(String query, Pageable pageable);
}
