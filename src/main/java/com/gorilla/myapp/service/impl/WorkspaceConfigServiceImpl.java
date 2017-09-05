package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.WorkspaceConfigService;
import com.gorilla.myapp.domain.WorkspaceConfig;
import com.gorilla.myapp.repository.WorkspaceConfigRepository;
import com.gorilla.myapp.repository.search.WorkspaceConfigSearchRepository;
import com.gorilla.myapp.service.dto.WorkspaceConfigDTO;
import com.gorilla.myapp.service.mapper.WorkspaceConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing WorkspaceConfig.
 */
@Service
@Transactional
public class WorkspaceConfigServiceImpl implements WorkspaceConfigService{

    private final Logger log = LoggerFactory.getLogger(WorkspaceConfigServiceImpl.class);

    private final WorkspaceConfigRepository workspaceConfigRepository;

    private final WorkspaceConfigMapper workspaceConfigMapper;

    private final WorkspaceConfigSearchRepository workspaceConfigSearchRepository;
    public WorkspaceConfigServiceImpl(WorkspaceConfigRepository workspaceConfigRepository, WorkspaceConfigMapper workspaceConfigMapper, WorkspaceConfigSearchRepository workspaceConfigSearchRepository) {
        this.workspaceConfigRepository = workspaceConfigRepository;
        this.workspaceConfigMapper = workspaceConfigMapper;
        this.workspaceConfigSearchRepository = workspaceConfigSearchRepository;
    }

    /**
     * Save a workspaceConfig.
     *
     * @param workspaceConfigDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WorkspaceConfigDTO save(WorkspaceConfigDTO workspaceConfigDTO) {
        log.debug("Request to save WorkspaceConfig : {}", workspaceConfigDTO);
        WorkspaceConfig workspaceConfig = workspaceConfigMapper.toEntity(workspaceConfigDTO);
        workspaceConfig = workspaceConfigRepository.save(workspaceConfig);
        WorkspaceConfigDTO result = workspaceConfigMapper.toDto(workspaceConfig);
        workspaceConfigSearchRepository.save(workspaceConfig);
        return result;
    }

    /**
     *  Get all the workspaceConfigs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkspaceConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkspaceConfigs");
        return workspaceConfigRepository.findAll(pageable)
            .map(workspaceConfigMapper::toDto);
    }

    /**
     *  Get one workspaceConfig by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WorkspaceConfigDTO findOne(Long id) {
        log.debug("Request to get WorkspaceConfig : {}", id);
        WorkspaceConfig workspaceConfig = workspaceConfigRepository.findOne(id);
        return workspaceConfigMapper.toDto(workspaceConfig);
    }

    /**
     *  Delete the  workspaceConfig by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkspaceConfig : {}", id);
        workspaceConfigRepository.delete(id);
        workspaceConfigSearchRepository.delete(id);
    }

    /**
     * Search for the workspaceConfig corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkspaceConfigDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WorkspaceConfigs for query {}", query);
        Page<WorkspaceConfig> result = workspaceConfigSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(workspaceConfigMapper::toDto);
    }
}
