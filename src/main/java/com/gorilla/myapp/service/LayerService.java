package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.LayerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Layer.
 */
public interface LayerService {

    /**
     * Save a layer.
     *
     * @param layerDTO the entity to save
     * @return the persisted entity
     */
    LayerDTO save(LayerDTO layerDTO);

    /**
     *  Get all the layers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LayerDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" layer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LayerDTO findOne(Long id);

    /**
     *  Delete the "id" layer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the layer corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LayerDTO> search(String query, Pageable pageable);
}
