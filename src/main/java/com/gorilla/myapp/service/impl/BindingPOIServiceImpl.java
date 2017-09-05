package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.BindingPOIService;
import com.gorilla.myapp.domain.BindingPOI;
import com.gorilla.myapp.repository.BindingPOIRepository;
import com.gorilla.myapp.repository.search.BindingPOISearchRepository;
import com.gorilla.myapp.service.dto.BindingPOIDTO;
import com.gorilla.myapp.service.mapper.BindingPOIMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BindingPOI.
 */
@Service
@Transactional
public class BindingPOIServiceImpl implements BindingPOIService{

    private final Logger log = LoggerFactory.getLogger(BindingPOIServiceImpl.class);

    private final BindingPOIRepository bindingPOIRepository;

    private final BindingPOIMapper bindingPOIMapper;

    private final BindingPOISearchRepository bindingPOISearchRepository;
    public BindingPOIServiceImpl(BindingPOIRepository bindingPOIRepository, BindingPOIMapper bindingPOIMapper, BindingPOISearchRepository bindingPOISearchRepository) {
        this.bindingPOIRepository = bindingPOIRepository;
        this.bindingPOIMapper = bindingPOIMapper;
        this.bindingPOISearchRepository = bindingPOISearchRepository;
    }

    /**
     * Save a bindingPOI.
     *
     * @param bindingPOIDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BindingPOIDTO save(BindingPOIDTO bindingPOIDTO) {
        log.debug("Request to save BindingPOI : {}", bindingPOIDTO);
        BindingPOI bindingPOI = bindingPOIMapper.toEntity(bindingPOIDTO);
        bindingPOI = bindingPOIRepository.save(bindingPOI);
        BindingPOIDTO result = bindingPOIMapper.toDto(bindingPOI);
        bindingPOISearchRepository.save(bindingPOI);
        return result;
    }

    /**
     *  Get all the bindingPOIS.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BindingPOIDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BindingPOIS");
        return bindingPOIRepository.findAll(pageable)
            .map(bindingPOIMapper::toDto);
    }

    /**
     *  Get one bindingPOI by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BindingPOIDTO findOne(Long id) {
        log.debug("Request to get BindingPOI : {}", id);
        BindingPOI bindingPOI = bindingPOIRepository.findOne(id);
        return bindingPOIMapper.toDto(bindingPOI);
    }

    /**
     *  Delete the  bindingPOI by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BindingPOI : {}", id);
        bindingPOIRepository.delete(id);
        bindingPOISearchRepository.delete(id);
    }

    /**
     * Search for the bindingPOI corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BindingPOIDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BindingPOIS for query {}", query);
        Page<BindingPOI> result = bindingPOISearchRepository.search(queryStringQuery(query), pageable);
        return result.map(bindingPOIMapper::toDto);
    }
}
