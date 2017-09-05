package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.RegionTypeService;
import com.gorilla.myapp.domain.RegionType;
import com.gorilla.myapp.repository.RegionTypeRepository;
import com.gorilla.myapp.repository.search.RegionTypeSearchRepository;
import com.gorilla.myapp.service.dto.RegionTypeDTO;
import com.gorilla.myapp.service.mapper.RegionTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RegionType.
 */
@Service
@Transactional
public class RegionTypeServiceImpl implements RegionTypeService{

    private final Logger log = LoggerFactory.getLogger(RegionTypeServiceImpl.class);

    private final RegionTypeRepository regionTypeRepository;

    private final RegionTypeMapper regionTypeMapper;

    private final RegionTypeSearchRepository regionTypeSearchRepository;
    public RegionTypeServiceImpl(RegionTypeRepository regionTypeRepository, RegionTypeMapper regionTypeMapper, RegionTypeSearchRepository regionTypeSearchRepository) {
        this.regionTypeRepository = regionTypeRepository;
        this.regionTypeMapper = regionTypeMapper;
        this.regionTypeSearchRepository = regionTypeSearchRepository;
    }

    /**
     * Save a regionType.
     *
     * @param regionTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RegionTypeDTO save(RegionTypeDTO regionTypeDTO) {
        log.debug("Request to save RegionType : {}", regionTypeDTO);
        RegionType regionType = regionTypeMapper.toEntity(regionTypeDTO);
        regionType = regionTypeRepository.save(regionType);
        RegionTypeDTO result = regionTypeMapper.toDto(regionType);
        regionTypeSearchRepository.save(regionType);
        return result;
    }

    /**
     *  Get all the regionTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegionTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegionTypes");
        return regionTypeRepository.findAll(pageable)
            .map(regionTypeMapper::toDto);
    }

    /**
     *  Get one regionType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RegionTypeDTO findOne(Long id) {
        log.debug("Request to get RegionType : {}", id);
        RegionType regionType = regionTypeRepository.findOne(id);
        return regionTypeMapper.toDto(regionType);
    }

    /**
     *  Delete the  regionType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegionType : {}", id);
        regionTypeRepository.delete(id);
        regionTypeSearchRepository.delete(id);
    }

    /**
     * Search for the regionType corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegionTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RegionTypes for query {}", query);
        Page<RegionType> result = regionTypeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(regionTypeMapper::toDto);
    }
}
