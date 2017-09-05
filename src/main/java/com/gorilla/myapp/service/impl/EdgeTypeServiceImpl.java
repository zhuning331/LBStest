package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.EdgeTypeService;
import com.gorilla.myapp.domain.EdgeType;
import com.gorilla.myapp.repository.EdgeTypeRepository;
import com.gorilla.myapp.repository.search.EdgeTypeSearchRepository;
import com.gorilla.myapp.service.dto.EdgeTypeDTO;
import com.gorilla.myapp.service.mapper.EdgeTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EdgeType.
 */
@Service
@Transactional
public class EdgeTypeServiceImpl implements EdgeTypeService{

    private final Logger log = LoggerFactory.getLogger(EdgeTypeServiceImpl.class);

    private final EdgeTypeRepository edgeTypeRepository;

    private final EdgeTypeMapper edgeTypeMapper;

    private final EdgeTypeSearchRepository edgeTypeSearchRepository;
    public EdgeTypeServiceImpl(EdgeTypeRepository edgeTypeRepository, EdgeTypeMapper edgeTypeMapper, EdgeTypeSearchRepository edgeTypeSearchRepository) {
        this.edgeTypeRepository = edgeTypeRepository;
        this.edgeTypeMapper = edgeTypeMapper;
        this.edgeTypeSearchRepository = edgeTypeSearchRepository;
    }

    /**
     * Save a edgeType.
     *
     * @param edgeTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EdgeTypeDTO save(EdgeTypeDTO edgeTypeDTO) {
        log.debug("Request to save EdgeType : {}", edgeTypeDTO);
        EdgeType edgeType = edgeTypeMapper.toEntity(edgeTypeDTO);
        edgeType = edgeTypeRepository.save(edgeType);
        EdgeTypeDTO result = edgeTypeMapper.toDto(edgeType);
        edgeTypeSearchRepository.save(edgeType);
        return result;
    }

    /**
     *  Get all the edgeTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EdgeTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EdgeTypes");
        return edgeTypeRepository.findAll(pageable)
            .map(edgeTypeMapper::toDto);
    }

    /**
     *  Get one edgeType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EdgeTypeDTO findOne(Long id) {
        log.debug("Request to get EdgeType : {}", id);
        EdgeType edgeType = edgeTypeRepository.findOne(id);
        return edgeTypeMapper.toDto(edgeType);
    }

    /**
     *  Delete the  edgeType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EdgeType : {}", id);
        edgeTypeRepository.delete(id);
        edgeTypeSearchRepository.delete(id);
    }

    /**
     * Search for the edgeType corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EdgeTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EdgeTypes for query {}", query);
        Page<EdgeType> result = edgeTypeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(edgeTypeMapper::toDto);
    }
}
