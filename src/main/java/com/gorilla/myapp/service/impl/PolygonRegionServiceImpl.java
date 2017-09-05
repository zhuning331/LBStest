package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.PolygonRegionService;
import com.gorilla.myapp.domain.PolygonRegion;
import com.gorilla.myapp.repository.PolygonRegionRepository;
import com.gorilla.myapp.repository.search.PolygonRegionSearchRepository;
import com.gorilla.myapp.service.dto.PolygonRegionDTO;
import com.gorilla.myapp.service.mapper.PolygonRegionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PolygonRegion.
 */
@Service
@Transactional
public class PolygonRegionServiceImpl implements PolygonRegionService{

    private final Logger log = LoggerFactory.getLogger(PolygonRegionServiceImpl.class);

    private final PolygonRegionRepository polygonRegionRepository;

    private final PolygonRegionMapper polygonRegionMapper;

    private final PolygonRegionSearchRepository polygonRegionSearchRepository;
    public PolygonRegionServiceImpl(PolygonRegionRepository polygonRegionRepository, PolygonRegionMapper polygonRegionMapper, PolygonRegionSearchRepository polygonRegionSearchRepository) {
        this.polygonRegionRepository = polygonRegionRepository;
        this.polygonRegionMapper = polygonRegionMapper;
        this.polygonRegionSearchRepository = polygonRegionSearchRepository;
    }

    /**
     * Save a polygonRegion.
     *
     * @param polygonRegionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PolygonRegionDTO save(PolygonRegionDTO polygonRegionDTO) {
        log.debug("Request to save PolygonRegion : {}", polygonRegionDTO);
        PolygonRegion polygonRegion = polygonRegionMapper.toEntity(polygonRegionDTO);
        polygonRegion = polygonRegionRepository.save(polygonRegion);
        PolygonRegionDTO result = polygonRegionMapper.toDto(polygonRegion);
        polygonRegionSearchRepository.save(polygonRegion);
        return result;
    }

    /**
     *  Get all the polygonRegions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PolygonRegionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PolygonRegions");
        return polygonRegionRepository.findAll(pageable)
            .map(polygonRegionMapper::toDto);
    }

    /**
     *  Get one polygonRegion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PolygonRegionDTO findOne(Long id) {
        log.debug("Request to get PolygonRegion : {}", id);
        PolygonRegion polygonRegion = polygonRegionRepository.findOne(id);
        return polygonRegionMapper.toDto(polygonRegion);
    }

    /**
     *  Delete the  polygonRegion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PolygonRegion : {}", id);
        polygonRegionRepository.delete(id);
        polygonRegionSearchRepository.delete(id);
    }

    /**
     * Search for the polygonRegion corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PolygonRegionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PolygonRegions for query {}", query);
        Page<PolygonRegion> result = polygonRegionSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(polygonRegionMapper::toDto);
    }
}
