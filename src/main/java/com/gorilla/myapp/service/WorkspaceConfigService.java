package com.gorilla.myapp.service;

import com.gorilla.myapp.service.dto.WorkspaceConfigDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing WorkspaceConfig.
 */
public interface WorkspaceConfigService {

    /**
     * Save a workspaceConfig.
     *
     * @param workspaceConfigDTO the entity to save
     * @return the persisted entity
     */
    WorkspaceConfigDTO save(WorkspaceConfigDTO workspaceConfigDTO);

    /**
     *  Get all the workspaceConfigs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WorkspaceConfigDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" workspaceConfig.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WorkspaceConfigDTO findOne(Long id);

    /**
     *  Delete the "id" workspaceConfig.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the workspaceConfig corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WorkspaceConfigDTO> search(String query, Pageable pageable);
}
