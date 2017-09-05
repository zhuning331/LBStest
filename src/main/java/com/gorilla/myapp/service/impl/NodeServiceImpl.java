package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.NodeService;
import com.gorilla.myapp.domain.Node;
import com.gorilla.myapp.repository.NodeRepository;
import com.gorilla.myapp.repository.search.NodeSearchRepository;
import com.gorilla.myapp.service.dto.NodeDTO;
import com.gorilla.myapp.service.mapper.NodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Node.
 */
@Service
@Transactional
public class NodeServiceImpl implements NodeService{

    private final Logger log = LoggerFactory.getLogger(NodeServiceImpl.class);

    private final NodeRepository nodeRepository;

    private final NodeMapper nodeMapper;

    private final NodeSearchRepository nodeSearchRepository;
    public NodeServiceImpl(NodeRepository nodeRepository, NodeMapper nodeMapper, NodeSearchRepository nodeSearchRepository) {
        this.nodeRepository = nodeRepository;
        this.nodeMapper = nodeMapper;
        this.nodeSearchRepository = nodeSearchRepository;
    }

    /**
     * Save a node.
     *
     * @param nodeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NodeDTO save(NodeDTO nodeDTO) {
        log.debug("Request to save Node : {}", nodeDTO);
        Node node = nodeMapper.toEntity(nodeDTO);
        node = nodeRepository.save(node);
        NodeDTO result = nodeMapper.toDto(node);
        nodeSearchRepository.save(node);
        return result;
    }

    /**
     *  Get all the nodes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NodeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nodes");
        return nodeRepository.findAll(pageable)
            .map(nodeMapper::toDto);
    }

    /**
     *  Get one node by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NodeDTO findOne(Long id) {
        log.debug("Request to get Node : {}", id);
        Node node = nodeRepository.findOne(id);
        return nodeMapper.toDto(node);
    }

    /**
     *  Delete the  node by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Node : {}", id);
        nodeRepository.delete(id);
        nodeSearchRepository.delete(id);
    }

    /**
     * Search for the node corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NodeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Nodes for query {}", query);
        Page<Node> result = nodeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(nodeMapper::toDto);
    }
}
