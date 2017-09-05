package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.POIService;
import com.gorilla.myapp.domain.POI;
import com.gorilla.myapp.repository.POIRepository;
import com.gorilla.myapp.repository.search.POISearchRepository;
import com.gorilla.myapp.service.dto.POIDTO;
import com.gorilla.myapp.service.mapper.POIMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing POI.
 */
@Service
@Transactional
public class POIServiceImpl implements POIService{

    private final Logger log = LoggerFactory.getLogger(POIServiceImpl.class);

    private final POIRepository pOIRepository;

    private final POIMapper pOIMapper;

    private final POISearchRepository pOISearchRepository;
    public POIServiceImpl(POIRepository pOIRepository, POIMapper pOIMapper, POISearchRepository pOISearchRepository) {
        this.pOIRepository = pOIRepository;
        this.pOIMapper = pOIMapper;
        this.pOISearchRepository = pOISearchRepository;
    }

    /**
     * Save a pOI.
     *
     * @param pOIDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public POIDTO save(POIDTO pOIDTO) {
        log.debug("Request to save POI : {}", pOIDTO);
        POI pOI = pOIMapper.toEntity(pOIDTO);
        pOI = pOIRepository.save(pOI);
        POIDTO result = pOIMapper.toDto(pOI);
        pOISearchRepository.save(pOI);
        return result;
    }

    /**
     *  Get all the pOIS.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<POIDTO> findAll(Pageable pageable) {
        log.debug("Request to get all POIS");
        return pOIRepository.findAll(pageable)
            .map(pOIMapper::toDto);
    }

    /**
     *  Get one pOI by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public POIDTO findOne(Long id) {
        log.debug("Request to get POI : {}", id);
        POI pOI = pOIRepository.findOneWithEagerRelationships(id);
        return pOIMapper.toDto(pOI);
    }

    /**
     *  Delete the  pOI by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete POI : {}", id);
        pOIRepository.delete(id);
        pOISearchRepository.delete(id);
    }

    /**
     * Search for the pOI corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<POIDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of POIS for query {}", query);
        Page<POI> result = pOISearchRepository.search(queryStringQuery(query), pageable);
        return result.map(pOIMapper::toDto);
    }
}
