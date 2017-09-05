package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.RegularRegionService;
import com.gorilla.myapp.domain.RegularRegion;
import com.gorilla.myapp.repository.RegularRegionRepository;
import com.gorilla.myapp.repository.search.RegularRegionSearchRepository;
import com.gorilla.myapp.service.dto.RegularRegionDTO;
import com.gorilla.myapp.service.mapper.RegularRegionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RegularRegion.
 */
@Service
@Transactional
public class RegularRegionServiceImpl implements RegularRegionService{

    private final Logger log = LoggerFactory.getLogger(RegularRegionServiceImpl.class);

    private final RegularRegionRepository regularRegionRepository;

    private final RegularRegionMapper regularRegionMapper;

    private final RegularRegionSearchRepository regularRegionSearchRepository;
    public RegularRegionServiceImpl(RegularRegionRepository regularRegionRepository, RegularRegionMapper regularRegionMapper, RegularRegionSearchRepository regularRegionSearchRepository) {
        this.regularRegionRepository = regularRegionRepository;
        this.regularRegionMapper = regularRegionMapper;
        this.regularRegionSearchRepository = regularRegionSearchRepository;
    }

    /**
     * Save a regularRegion.
     *
     * @param regularRegionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RegularRegionDTO save(RegularRegionDTO regularRegionDTO) {
        log.debug("Request to save RegularRegion : {}", regularRegionDTO);
        RegularRegion regularRegion = regularRegionMapper.toEntity(regularRegionDTO);
        regularRegion = regularRegionRepository.save(regularRegion);
        RegularRegionDTO result = regularRegionMapper.toDto(regularRegion);
        regularRegionSearchRepository.save(regularRegion);
        return result;
    }

    /**
     *  Get all the regularRegions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegularRegionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegularRegions");
        return regularRegionRepository.findAll(pageable)
            .map(regularRegionMapper::toDto);
    }

    /**
     *  Get one regularRegion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RegularRegionDTO findOne(Long id) {
        log.debug("Request to get RegularRegion : {}", id);
        RegularRegion regularRegion = regularRegionRepository.findOne(id);
        return regularRegionMapper.toDto(regularRegion);
    }

    /**
     *  Delete the  regularRegion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegularRegion : {}", id);
        regularRegionRepository.delete(id);
        regularRegionSearchRepository.delete(id);
    }

    /**
     * Search for the regularRegion corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegularRegionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RegularRegions for query {}", query);
        Page<RegularRegion> result = regularRegionSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(regularRegionMapper::toDto);
    }
}
