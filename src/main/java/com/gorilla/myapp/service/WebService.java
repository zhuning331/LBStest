package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.WebDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Web.
 */
public interface WebService {

    /**
     * Save a web.
     *
     * @param webDTO the entity to save
     * @return the persisted entity
     */
    WebDTO save(WebDTO webDTO);

    /**
     *  Get all the webs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WebDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" web.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WebDTO findOne(Long id);

    /**
     *  Delete the "id" web.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the web corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WebDTO> search(String query, Pageable pageable);
}
