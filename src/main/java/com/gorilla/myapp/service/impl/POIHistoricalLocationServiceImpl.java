package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.POIHistoricalLocationService;
import com.gorilla.myapp.domain.POIHistoricalLocation;
import com.gorilla.myapp.repository.POIHistoricalLocationRepository;
import com.gorilla.myapp.repository.search.POIHistoricalLocationSearchRepository;
import com.gorilla.myapp.service.dto.POIHistoricalLocationDTO;
import com.gorilla.myapp.service.mapper.POIHistoricalLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing POIHistoricalLocation.
 */
@Service
@Transactional
public class POIHistoricalLocationServiceImpl implements POIHistoricalLocationService{

    private final Logger log = LoggerFactory.getLogger(POIHistoricalLocationServiceImpl.class);

    private final POIHistoricalLocationRepository pOIHistoricalLocationRepository;

    private final POIHistoricalLocationMapper pOIHistoricalLocationMapper;

    private final POIHistoricalLocationSearchRepository pOIHistoricalLocationSearchRepository;
    public POIHistoricalLocationServiceImpl(POIHistoricalLocationRepository pOIHistoricalLocationRepository, POIHistoricalLocationMapper pOIHistoricalLocationMapper, POIHistoricalLocationSearchRepository pOIHistoricalLocationSearchRepository) {
        this.pOIHistoricalLocationRepository = pOIHistoricalLocationRepository;
        this.pOIHistoricalLocationMapper = pOIHistoricalLocationMapper;
        this.pOIHistoricalLocationSearchRepository = pOIHistoricalLocationSearchRepository;
    }

    /**
     * Save a pOIHistoricalLocation.
     *
     * @param pOIHistoricalLocationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public POIHistoricalLocationDTO save(POIHistoricalLocationDTO pOIHistoricalLocationDTO) {
        log.debug("Request to save POIHistoricalLocation : {}", pOIHistoricalLocationDTO);
        POIHistoricalLocation pOIHistoricalLocation = pOIHistoricalLocationMapper.toEntity(pOIHistoricalLocationDTO);
        pOIHistoricalLocation = pOIHistoricalLocationRepository.save(pOIHistoricalLocation);
        POIHistoricalLocationDTO result = pOIHistoricalLocationMapper.toDto(pOIHistoricalLocation);
        pOIHistoricalLocationSearchRepository.save(pOIHistoricalLocation);
        return result;
    }

    /**
     *  Get all the pOIHistoricalLocations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<POIHistoricalLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all POIHistoricalLocations");
        return pOIHistoricalLocationRepository.findAll(pageable)
            .map(pOIHistoricalLocationMapper::toDto);
    }

    /**
     *  Get one pOIHistoricalLocation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public POIHistoricalLocationDTO findOne(Long id) {
        log.debug("Request to get POIHistoricalLocation : {}", id);
        POIHistoricalLocation pOIHistoricalLocation = pOIHistoricalLocationRepository.findOne(id);
        return pOIHistoricalLocationMapper.toDto(pOIHistoricalLocation);
    }

    /**
     *  Delete the  pOIHistoricalLocation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete POIHistoricalLocation : {}", id);
        pOIHistoricalLocationRepository.delete(id);
        pOIHistoricalLocationSearchRepository.delete(id);
    }

    /**
     * Search for the pOIHistoricalLocation corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<POIHistoricalLocationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of POIHistoricalLocations for query {}", query);
        Page<POIHistoricalLocation> result = pOIHistoricalLocationSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(pOIHistoricalLocationMapper::toDto);
    }
}
