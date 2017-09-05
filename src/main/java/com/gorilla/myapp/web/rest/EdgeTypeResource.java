package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.EdgeTypeService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.EdgeTypeDTO;
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
 * REST controller for managing EdgeType.
 */
@RestController
@RequestMapping("/api")
public class EdgeTypeResource {

    private final Logger log = LoggerFactory.getLogger(EdgeTypeResource.class);

    private static final String ENTITY_NAME = "edgeType";

    private final EdgeTypeService edgeTypeService;

    public EdgeTypeResource(EdgeTypeService edgeTypeService) {
        this.edgeTypeService = edgeTypeService;
    }

    /**
     * POST  /edge-types : Create a new edgeType.
     *
     * @param edgeTypeDTO the edgeTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new edgeTypeDTO, or with status 400 (Bad Request) if the edgeType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/edge-types")
    @Timed
    public ResponseEntity<EdgeTypeDTO> createEdgeType(@RequestBody EdgeTypeDTO edgeTypeDTO) throws URISyntaxException {
        log.debug("REST request to save EdgeType : {}", edgeTypeDTO);
        if (edgeTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new edgeType cannot already have an ID")).body(null);
        }
        EdgeTypeDTO result = edgeTypeService.save(edgeTypeDTO);
        return ResponseEntity.created(new URI("/api/edge-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /edge-types : Updates an existing edgeType.
     *
     * @param edgeTypeDTO the edgeTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated edgeTypeDTO,
     * or with status 400 (Bad Request) if the edgeTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the edgeTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/edge-types")
    @Timed
    public ResponseEntity<EdgeTypeDTO> updateEdgeType(@RequestBody EdgeTypeDTO edgeTypeDTO) throws URISyntaxException {
        log.debug("REST request to update EdgeType : {}", edgeTypeDTO);
        if (edgeTypeDTO.getId() == null) {
            return createEdgeType(edgeTypeDTO);
        }
        EdgeTypeDTO result = edgeTypeService.save(edgeTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, edgeTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /edge-types : get all the edgeTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of edgeTypes in body
     */
    @GetMapping("/edge-types")
    @Timed
    public ResponseEntity<List<EdgeTypeDTO>> getAllEdgeTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EdgeTypes");
        Page<EdgeTypeDTO> page = edgeTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/edge-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /edge-types/:id : get the "id" edgeType.
     *
     * @param id the id of the edgeTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the edgeTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/edge-types/{id}")
    @Timed
    public ResponseEntity<EdgeTypeDTO> getEdgeType(@PathVariable Long id) {
        log.debug("REST request to get EdgeType : {}", id);
        EdgeTypeDTO edgeTypeDTO = edgeTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(edgeTypeDTO));
    }

    /**
     * DELETE  /edge-types/:id : delete the "id" edgeType.
     *
     * @param id the id of the edgeTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/edge-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEdgeType(@PathVariable Long id) {
        log.debug("REST request to delete EdgeType : {}", id);
        edgeTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/edge-types?query=:query : search for the edgeType corresponding
     * to the query.
     *
     * @param query the query of the edgeType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/edge-types")
    @Timed
    public ResponseEntity<List<EdgeTypeDTO>> searchEdgeTypes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of EdgeTypes for query {}", query);
        Page<EdgeTypeDTO> page = edgeTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/edge-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
