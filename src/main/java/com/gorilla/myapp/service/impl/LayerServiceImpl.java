package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.LayerService;
import com.gorilla.myapp.domain.Layer;
import com.gorilla.myapp.repository.LayerRepository;
import com.gorilla.myapp.repository.search.LayerSearchRepository;
import com.gorilla.myapp.service.dto.LayerDTO;
import com.gorilla.myapp.service.mapper.LayerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Layer.
 */
@Service
@Transactional
public class LayerServiceImpl implements LayerService{

    private final Logger log = LoggerFactory.getLogger(LayerServiceImpl.class);

    private final LayerRepository layerRepository;

    private final LayerMapper layerMapper;

    private final LayerSearchRepository layerSearchRepository;
    public LayerServiceImpl(LayerRepository layerRepository, LayerMapper layerMapper, LayerSearchRepository layerSearchRepository) {
        this.layerRepository = layerRepository;
        this.layerMapper = layerMapper;
        this.layerSearchRepository = layerSearchRepository;
    }

    /**
     * Save a layer.
     *
     * @param layerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LayerDTO save(LayerDTO layerDTO) {
        log.debug("Request to save Layer : {}", layerDTO);
        Layer layer = layerMapper.toEntity(layerDTO);
        layer = layerRepository.save(layer);
        LayerDTO result = layerMapper.toDto(layer);
        layerSearchRepository.save(layer);
        return result;
    }

    /**
     *  Get all the layers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LayerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Layers");
        return layerRepository.findAll(pageable)
            .map(layerMapper::toDto);
    }

    /**
     *  Get one layer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LayerDTO findOne(Long id) {
        log.debug("Request to get Layer : {}", id);
        Layer layer = layerRepository.findOne(id);
        return layerMapper.toDto(layer);
    }

    /**
     *  Delete the  layer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Layer : {}", id);
        layerRepository.delete(id);
        layerSearchRepository.delete(id);
    }

    /**
     * Search for the layer corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LayerDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Layers for query {}", query);
        Page<Layer> result = layerSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(layerMapper::toDto);
    }
}
