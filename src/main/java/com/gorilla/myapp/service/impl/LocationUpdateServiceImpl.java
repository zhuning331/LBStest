package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.LocationUpdateService;
import com.gorilla.myapp.domain.LocationUpdate;
import com.gorilla.myapp.repository.LocationUpdateRepository;
import com.gorilla.myapp.repository.search.LocationUpdateSearchRepository;
import com.gorilla.myapp.service.dto.LocationUpdateDTO;
import com.gorilla.myapp.service.mapper.LocationUpdateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing LocationUpdate.
 */
@Service
@Transactional
public class LocationUpdateServiceImpl implements LocationUpdateService{

    private final Logger log = LoggerFactory.getLogger(LocationUpdateServiceImpl.class);

    private final LocationUpdateRepository locationUpdateRepository;

    private final LocationUpdateMapper locationUpdateMapper;

    private final LocationUpdateSearchRepository locationUpdateSearchRepository;
    public LocationUpdateServiceImpl(LocationUpdateRepository locationUpdateRepository, LocationUpdateMapper locationUpdateMapper, LocationUpdateSearchRepository locationUpdateSearchRepository) {
        this.locationUpdateRepository = locationUpdateRepository;
        this.locationUpdateMapper = locationUpdateMapper;
        this.locationUpdateSearchRepository = locationUpdateSearchRepository;
    }

    /**
     * Save a locationUpdate.
     *
     * @param locationUpdateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LocationUpdateDTO save(LocationUpdateDTO locationUpdateDTO) {
        log.debug("Request to save LocationUpdate : {}", locationUpdateDTO);
        LocationUpdate locationUpdate = locationUpdateMapper.toEntity(locationUpdateDTO);
        locationUpdate = locationUpdateRepository.save(locationUpdate);
        LocationUpdateDTO result = locationUpdateMapper.toDto(locationUpdate);
        locationUpdateSearchRepository.save(locationUpdate);
        return result;
    }

    /**
     *  Get all the locationUpdates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LocationUpdateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocationUpdates");
        return locationUpdateRepository.findAll(pageable)
            .map(locationUpdateMapper::toDto);
    }

    /**
     *  Get one locationUpdate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LocationUpdateDTO findOne(Long id) {
        log.debug("Request to get LocationUpdate : {}", id);
        LocationUpdate locationUpdate = locationUpdateRepository.findOne(id);
        return locationUpdateMapper.toDto(locationUpdate);
    }

    /**
     *  Delete the  locationUpdate by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LocationUpdate : {}", id);
        locationUpdateRepository.delete(id);
        locationUpdateSearchRepository.delete(id);
    }

    /**
     * Search for the locationUpdate corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LocationUpdateDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LocationUpdates for query {}", query);
        Page<LocationUpdate> result = locationUpdateSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(locationUpdateMapper::toDto);
    }
}
