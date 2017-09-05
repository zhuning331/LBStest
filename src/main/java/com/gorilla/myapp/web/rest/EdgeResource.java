package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.EdgeService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.EdgeDTO;
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
 * REST controller for managing Edge.
 */
@RestController
@RequestMapping("/api")
public class EdgeResource {

    private final Logger log = LoggerFactory.getLogger(EdgeResource.class);

    private static final String ENTITY_NAME = "edge";

    private final EdgeService edgeService;

    public EdgeResource(EdgeService edgeService) {
        this.edgeService = edgeService;
    }

    /**
     * POST  /edges : Create a new edge.
     *
     * @param edgeDTO the edgeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new edgeDTO, or with status 400 (Bad Request) if the edge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/edges")
    @Timed
    public ResponseEntity<EdgeDTO> createEdge(@RequestBody EdgeDTO edgeDTO) throws URISyntaxException {
        log.debug("REST request to save Edge : {}", edgeDTO);
        if (edgeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new edge cannot already have an ID")).body(null);
        }
        EdgeDTO result = edgeService.save(edgeDTO);
        return ResponseEntity.created(new URI("/api/edges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /edges : Updates an existing edge.
     *
     * @param edgeDTO the edgeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated edgeDTO,
     * or with status 400 (Bad Request) if the edgeDTO is not valid,
     * or with status 500 (Internal Server Error) if the edgeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/edges")
    @Timed
    public ResponseEntity<EdgeDTO> updateEdge(@RequestBody EdgeDTO edgeDTO) throws URISyntaxException {
        log.debug("REST request to update Edge : {}", edgeDTO);
        if (edgeDTO.getId() == null) {
            return createEdge(edgeDTO);
        }
        EdgeDTO result = edgeService.save(edgeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, edgeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /edges : get all the edges.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of edges in body
     */
    @GetMapping("/edges")
    @Timed
    public ResponseEntity<List<EdgeDTO>> getAllEdges(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Edges");
        Page<EdgeDTO> page = edgeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/edges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /edges/:id : get the "id" edge.
     *
     * @param id the id of the edgeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the edgeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/edges/{id}")
    @Timed
    public ResponseEntity<EdgeDTO> getEdge(@PathVariable Long id) {
        log.debug("REST request to get Edge : {}", id);
        EdgeDTO edgeDTO = edgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(edgeDTO));
    }

    /**
     * DELETE  /edges/:id : delete the "id" edge.
     *
     * @param id the id of the edgeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/edges/{id}")
    @Timed
    public ResponseEntity<Void> deleteEdge(@PathVariable Long id) {
        log.debug("REST request to delete Edge : {}", id);
        edgeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/edges?query=:query : search for the edge corresponding
     * to the query.
     *
     * @param query the query of the edge search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/edges")
    @Timed
    public ResponseEntity<List<EdgeDTO>> searchEdges(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Edges for query {}", query);
        Page<EdgeDTO> page = edgeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/edges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
