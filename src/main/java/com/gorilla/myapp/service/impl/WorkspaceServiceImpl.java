package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.WorkspaceService;
import com.gorilla.myapp.domain.Workspace;
import com.gorilla.myapp.repository.WorkspaceRepository;
import com.gorilla.myapp.repository.search.WorkspaceSearchRepository;
import com.gorilla.myapp.service.dto.WorkspaceDTO;
import com.gorilla.myapp.service.mapper.WorkspaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Workspace.
 */
@Service
@Transactional
public class WorkspaceServiceImpl implements WorkspaceService{

    private final Logger log = LoggerFactory.getLogger(WorkspaceServiceImpl.class);

    private final WorkspaceRepository workspaceRepository;

    private final WorkspaceMapper workspaceMapper;

    private final WorkspaceSearchRepository workspaceSearchRepository;
    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository, WorkspaceMapper workspaceMapper, WorkspaceSearchRepository workspaceSearchRepository) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceMapper = workspaceMapper;
        this.workspaceSearchRepository = workspaceSearchRepository;
    }

    /**
     * Save a workspace.
     *
     * @param workspaceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WorkspaceDTO save(WorkspaceDTO workspaceDTO) {
        log.debug("Request to save Workspace : {}", workspaceDTO);
        Workspace workspace = workspaceMapper.toEntity(workspaceDTO);
        workspace = workspaceRepository.save(workspace);
        WorkspaceDTO result = workspaceMapper.toDto(workspace);
        workspaceSearchRepository.save(workspace);
        return result;
    }

    /**
     *  Get all the workspaces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkspaceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Workspaces");
        return workspaceRepository.findAll(pageable)
            .map(workspaceMapper::toDto);
    }

    /**
     *  Get one workspace by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WorkspaceDTO findOne(Long id) {
        log.debug("Request to get Workspace : {}", id);
        Workspace workspace = workspaceRepository.findOne(id);
        return workspaceMapper.toDto(workspace);
    }

    /**
     *  Delete the  workspace by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Workspace : {}", id);
        workspaceRepository.delete(id);
        workspaceSearchRepository.delete(id);
    }

    /**
     * Search for the workspace corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkspaceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Workspaces for query {}", query);
        Page<Workspace> result = workspaceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(workspaceMapper::toDto);
    }
}
