package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.WorkspaceConfigService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.WorkspaceConfigDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing WorkspaceConfig.
 */
@RestController
@RequestMapping("/api")
public class WorkspaceConfigResource {

    private final Logger log = LoggerFactory.getLogger(WorkspaceConfigResource.class);

    private static final String ENTITY_NAME = "workspaceConfig";

    private final WorkspaceConfigService workspaceConfigService;

    public WorkspaceConfigResource(WorkspaceConfigService workspaceConfigService) {
        this.workspaceConfigService = workspaceConfigService;
    }

    /**
     * POST  /workspace-configs : Create a new workspaceConfig.
     *
     * @param workspaceConfigDTO the workspaceConfigDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workspaceConfigDTO, or with status 400 (Bad Request) if the workspaceConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workspace-configs")
    @Timed
    public ResponseEntity<WorkspaceConfigDTO> createWorkspaceConfig(@RequestBody WorkspaceConfigDTO workspaceConfigDTO) throws URISyntaxException {
        log.debug("REST request to save WorkspaceConfig : {}", workspaceConfigDTO);
        if (workspaceConfigDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new workspaceConfig cannot already have an ID")).body(null);
        }
        WorkspaceConfigDTO result = workspaceConfigService.save(workspaceConfigDTO);
        return ResponseEntity.created(new URI("/api/workspace-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workspace-configs : Updates an existing workspaceConfig.
     *
     * @param workspaceConfigDTO the workspaceConfigDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workspaceConfigDTO,
     * or with status 400 (Bad Request) if the workspaceConfigDTO is not valid,
     * or with status 500 (Internal Server Error) if the workspaceConfigDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workspace-configs")
    @Timed
    public ResponseEntity<WorkspaceConfigDTO> updateWorkspaceConfig(@RequestBody WorkspaceConfigDTO workspaceConfigDTO) throws URISyntaxException {
        log.debug("REST request to update WorkspaceConfig : {}", workspaceConfigDTO);
        if (workspaceConfigDTO.getId() == null) {
            return createWorkspaceConfig(workspaceConfigDTO);
        }
        WorkspaceConfigDTO result = workspaceConfigService.save(workspaceConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workspaceConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workspace-configs : get all the workspaceConfigs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of workspaceConfigs in body
     */
    @GetMapping("/workspace-configs")
    @Timed
    public ResponseEntity<List<WorkspaceConfigDTO>> getAllWorkspaceConfigs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of WorkspaceConfigs");
        Page<WorkspaceConfigDTO> page = workspaceConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workspace-configs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /workspace-configs/:id : get the "id" workspaceConfig.
     *
     * @param id the id of the workspaceConfigDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workspaceConfigDTO, or with status 404 (Not Found)
     */
    @GetMapping("/workspace-configs/{id}")
    @Timed
    public ResponseEntity<WorkspaceConfigDTO> getWorkspaceConfig(@PathVariable Long id) {
        log.debug("REST request to get WorkspaceConfig : {}", id);
        WorkspaceConfigDTO workspaceConfigDTO = workspaceConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workspaceConfigDTO));
    }

    /**
     * DELETE  /workspace-configs/:id : delete the "id" workspaceConfig.
     *
     * @param id the id of the workspaceConfigDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workspace-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkspaceConfig(@PathVariable Long id) {
        log.debug("REST request to delete WorkspaceConfig : {}", id);
        workspaceConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/workspace-configs?query=:query : search for the workspaceConfig corresponding
     * to the query.
     *
     * @param query the query of the workspaceConfig search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/workspace-configs")
    @Timed
    public ResponseEntity<List<WorkspaceConfigDTO>> searchWorkspaceConfigs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of WorkspaceConfigs for query {}", query);
        Page<WorkspaceConfigDTO> page = workspaceConfigService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/workspace-configs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
