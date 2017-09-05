package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.WorkspaceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Workspace.
 */
public interface WorkspaceService {

    /**
     * Save a workspace.
     *
     * @param workspaceDTO the entity to save
     * @return the persisted entity
     */
    WorkspaceDTO save(WorkspaceDTO workspaceDTO);

    /**
     *  Get all the workspaces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WorkspaceDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" workspace.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WorkspaceDTO findOne(Long id);

    /**
     *  Delete the "id" workspace.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the workspace corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WorkspaceDTO> search(String query, Pageable pageable);
}
