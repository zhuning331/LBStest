package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.EdgeService;
import com.gorilla.myapp.domain.Edge;
import com.gorilla.myapp.repository.EdgeRepository;
import com.gorilla.myapp.repository.search.EdgeSearchRepository;
import com.gorilla.myapp.service.dto.EdgeDTO;
import com.gorilla.myapp.service.mapper.EdgeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Edge.
 */
@Service
@Transactional
public class EdgeServiceImpl implements EdgeService{

    private final Logger log = LoggerFactory.getLogger(EdgeServiceImpl.class);

    private final EdgeRepository edgeRepository;

    private final EdgeMapper edgeMapper;

    private final EdgeSearchRepository edgeSearchRepository;
    public EdgeServiceImpl(EdgeRepository edgeRepository, EdgeMapper edgeMapper, EdgeSearchRepository edgeSearchRepository) {
        this.edgeRepository = edgeRepository;
        this.edgeMapper = edgeMapper;
        this.edgeSearchRepository = edgeSearchRepository;
    }

    /**
     * Save a edge.
     *
     * @param edgeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EdgeDTO save(EdgeDTO edgeDTO) {
        log.debug("Request to save Edge : {}", edgeDTO);
        Edge edge = edgeMapper.toEntity(edgeDTO);
        edge = edgeRepository.save(edge);
        EdgeDTO result = edgeMapper.toDto(edge);
        edgeSearchRepository.save(edge);
        return result;
    }

    /**
     *  Get all the edges.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EdgeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Edges");
        return edgeRepository.findAll(pageable)
            .map(edgeMapper::toDto);
    }

    /**
     *  Get one edge by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EdgeDTO findOne(Long id) {
        log.debug("Request to get Edge : {}", id);
        Edge edge = edgeRepository.findOneWithEagerRelationships(id);
        return edgeMapper.toDto(edge);
    }

    /**
     *  Delete the  edge by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Edge : {}", id);
        edgeRepository.delete(id);
        edgeSearchRepository.delete(id);
    }

    /**
     * Search for the edge corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EdgeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Edges for query {}", query);
        Page<Edge> result = edgeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(edgeMapper::toDto);
    }
}
