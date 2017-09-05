package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.RegionService;
import com.gorilla.myapp.domain.Region;
import com.gorilla.myapp.repository.RegionRepository;
import com.gorilla.myapp.repository.search.RegionSearchRepository;
import com.gorilla.myapp.service.dto.RegionDTO;
import com.gorilla.myapp.service.mapper.RegionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Region.
 */
@Service
@Transactional
public class RegionServiceImpl implements RegionService{

    private final Logger log = LoggerFactory.getLogger(RegionServiceImpl.class);

    private final RegionRepository regionRepository;

    private final RegionMapper regionMapper;

    private final RegionSearchRepository regionSearchRepository;
    public RegionServiceImpl(RegionRepository regionRepository, RegionMapper regionMapper, RegionSearchRepository regionSearchRepository) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
        this.regionSearchRepository = regionSearchRepository;
    }

    /**
     * Save a region.
     *
     * @param regionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RegionDTO save(RegionDTO regionDTO) {
        log.debug("Request to save Region : {}", regionDTO);
        Region region = regionMapper.toEntity(regionDTO);
        region = regionRepository.save(region);
        RegionDTO result = regionMapper.toDto(region);
        regionSearchRepository.save(region);
        return result;
    }

    /**
     *  Get all the regions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Regions");
        return regionRepository.findAll(pageable)
            .map(regionMapper::toDto);
    }

    /**
     *  Get one region by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RegionDTO findOne(Long id) {
        log.debug("Request to get Region : {}", id);
        Region region = regionRepository.findOneWithEagerRelationships(id);
        return regionMapper.toDto(region);
    }

    /**
     *  Delete the  region by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Region : {}", id);
        regionRepository.delete(id);
        regionSearchRepository.delete(id);
    }

    /**
     * Search for the region corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Regions for query {}", query);
        Page<Region> result = regionSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(regionMapper::toDto);
    }
}
