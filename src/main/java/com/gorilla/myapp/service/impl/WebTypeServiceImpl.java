package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.WebTypeService;
import com.gorilla.myapp.domain.WebType;
import com.gorilla.myapp.repository.WebTypeRepository;
import com.gorilla.myapp.repository.search.WebTypeSearchRepository;
import com.gorilla.myapp.service.dto.WebTypeDTO;
import com.gorilla.myapp.service.mapper.WebTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing WebType.
 */
@Service
@Transactional
public class WebTypeServiceImpl implements WebTypeService{

    private final Logger log = LoggerFactory.getLogger(WebTypeServiceImpl.class);

    private final WebTypeRepository webTypeRepository;

    private final WebTypeMapper webTypeMapper;

    private final WebTypeSearchRepository webTypeSearchRepository;
    public WebTypeServiceImpl(WebTypeRepository webTypeRepository, WebTypeMapper webTypeMapper, WebTypeSearchRepository webTypeSearchRepository) {
        this.webTypeRepository = webTypeRepository;
        this.webTypeMapper = webTypeMapper;
        this.webTypeSearchRepository = webTypeSearchRepository;
    }

    /**
     * Save a webType.
     *
     * @param webTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WebTypeDTO save(WebTypeDTO webTypeDTO) {
        log.debug("Request to save WebType : {}", webTypeDTO);
        WebType webType = webTypeMapper.toEntity(webTypeDTO);
        webType = webTypeRepository.save(webType);
        WebTypeDTO result = webTypeMapper.toDto(webType);
        webTypeSearchRepository.save(webType);
        return result;
    }

    /**
     *  Get all the webTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WebTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WebTypes");
        return webTypeRepository.findAll(pageable)
            .map(webTypeMapper::toDto);
    }

    /**
     *  Get one webType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WebTypeDTO findOne(Long id) {
        log.debug("Request to get WebType : {}", id);
        WebType webType = webTypeRepository.findOne(id);
        return webTypeMapper.toDto(webType);
    }

    /**
     *  Delete the  webType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebType : {}", id);
        webTypeRepository.delete(id);
        webTypeSearchRepository.delete(id);
    }

    /**
     * Search for the webType corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WebTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WebTypes for query {}", query);
        Page<WebType> result = webTypeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(webTypeMapper::toDto);
    }
}
