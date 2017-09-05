package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.NodeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Node.
 */
public interface NodeService {

    /**
     * Save a node.
     *
     * @param nodeDTO the entity to save
     * @return the persisted entity
     */
    NodeDTO save(NodeDTO nodeDTO);

    /**
     *  Get all the nodes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NodeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" node.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NodeDTO findOne(Long id);

    /**
     *  Delete the "id" node.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the node corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NodeDTO> search(String query, Pageable pageable);
}
