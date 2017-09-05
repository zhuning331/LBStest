package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.WebService;
import com.gorilla.myapp.domain.Web;
import com.gorilla.myapp.repository.WebRepository;
import com.gorilla.myapp.repository.search.WebSearchRepository;
import com.gorilla.myapp.service.dto.WebDTO;
import com.gorilla.myapp.service.mapper.WebMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Web.
 */
@Service
@Transactional
public class WebServiceImpl implements WebService{

    private final Logger log = LoggerFactory.getLogger(WebServiceImpl.class);

    private final WebRepository webRepository;

    private final WebMapper webMapper;

    private final WebSearchRepository webSearchRepository;
    public WebServiceImpl(WebRepository webRepository, WebMapper webMapper, WebSearchRepository webSearchRepository) {
        this.webRepository = webRepository;
        this.webMapper = webMapper;
        this.webSearchRepository = webSearchRepository;
    }

    /**
     * Save a web.
     *
     * @param webDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WebDTO save(WebDTO webDTO) {
        log.debug("Request to save Web : {}", webDTO);
        Web web = webMapper.toEntity(webDTO);
        web = webRepository.save(web);
        WebDTO result = webMapper.toDto(web);
        webSearchRepository.save(web);
        return result;
    }

    /**
     *  Get all the webs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WebDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Webs");
        return webRepository.findAll(pageable)
            .map(webMapper::toDto);
    }

    /**
     *  Get one web by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WebDTO findOne(Long id) {
        log.debug("Request to get Web : {}", id);
        Web web = webRepository.findOneWithEagerRelationships(id);
        return webMapper.toDto(web);
    }

    /**
     *  Delete the  web by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Web : {}", id);
        webRepository.delete(id);
        webSearchRepository.delete(id);
    }

    /**
     * Search for the web corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WebDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Webs for query {}", query);
        Page<Web> result = webSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(webMapper::toDto);
    }
}
