package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.MapService;
import com.gorilla.myapp.domain.Map;
import com.gorilla.myapp.repository.MapRepository;
import com.gorilla.myapp.repository.search.MapSearchRepository;
import com.gorilla.myapp.service.dto.MapDTO;
import com.gorilla.myapp.service.mapper.MapMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Map.
 */
@Service
@Transactional
public class MapServiceImpl implements MapService{

    private final Logger log = LoggerFactory.getLogger(MapServiceImpl.class);

    private final MapRepository mapRepository;

    private final MapMapper mapMapper;

    private final MapSearchRepository mapSearchRepository;
    public MapServiceImpl(MapRepository mapRepository, MapMapper mapMapper, MapSearchRepository mapSearchRepository) {
        this.mapRepository = mapRepository;
        this.mapMapper = mapMapper;
        this.mapSearchRepository = mapSearchRepository;
    }

    /**
     * Save a map.
     *
     * @param mapDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MapDTO save(MapDTO mapDTO) {
        log.debug("Request to save Map : {}", mapDTO);
        Map map = mapMapper.toEntity(mapDTO);
        map = mapRepository.save(map);
        MapDTO result = mapMapper.toDto(map);
        mapSearchRepository.save(map);
        return result;
    }

    /**
     *  Get all the maps.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Maps");
        return mapRepository.findAll(pageable)
            .map(mapMapper::toDto);
    }

    /**
     *  Get one map by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MapDTO findOne(Long id) {
        log.debug("Request to get Map : {}", id);
        Map map = mapRepository.findOneWithEagerRelationships(id);
        return mapMapper.toDto(map);
    }

    /**
     *  Delete the  map by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Map : {}", id);
        mapRepository.delete(id);
        mapSearchRepository.delete(id);
    }

    /**
     * Search for the map corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MapDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Maps for query {}", query);
        Page<Map> result = mapSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(mapMapper::toDto);
    }
}
