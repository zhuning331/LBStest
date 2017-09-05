package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.PolygonRegionService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.PolygonRegionDTO;
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
 * REST controller for managing PolygonRegion.
 */
@RestController
@RequestMapping("/api")
public class PolygonRegionResource {

    private final Logger log = LoggerFactory.getLogger(PolygonRegionResource.class);

    private static final String ENTITY_NAME = "polygonRegion";

    private final PolygonRegionService polygonRegionService;

    public PolygonRegionResource(PolygonRegionService polygonRegionService) {
        this.polygonRegionService = polygonRegionService;
    }

    /**
     * POST  /polygon-regions : Create a new polygonRegion.
     *
     * @param polygonRegionDTO the polygonRegionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new polygonRegionDTO, or with status 400 (Bad Request) if the polygonRegion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/polygon-regions")
    @Timed
    public ResponseEntity<PolygonRegionDTO> createPolygonRegion(@RequestBody PolygonRegionDTO polygonRegionDTO) throws URISyntaxException {
        log.debug("REST request to save PolygonRegion : {}", polygonRegionDTO);
        if (polygonRegionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new polygonRegion cannot already have an ID")).body(null);
        }
        PolygonRegionDTO result = polygonRegionService.save(polygonRegionDTO);
        return ResponseEntity.created(new URI("/api/polygon-regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /polygon-regions : Updates an existing polygonRegion.
     *
     * @param polygonRegionDTO the polygonRegionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated polygonRegionDTO,
     * or with status 400 (Bad Request) if the polygonRegionDTO is not valid,
     * or with status 500 (Internal Server Error) if the polygonRegionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/polygon-regions")
    @Timed
    public ResponseEntity<PolygonRegionDTO> updatePolygonRegion(@RequestBody PolygonRegionDTO polygonRegionDTO) throws URISyntaxException {
        log.debug("REST request to update PolygonRegion : {}", polygonRegionDTO);
        if (polygonRegionDTO.getId() == null) {
            return createPolygonRegion(polygonRegionDTO);
        }
        PolygonRegionDTO result = polygonRegionService.save(polygonRegionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, polygonRegionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /polygon-regions : get all the polygonRegions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of polygonRegions in body
     */
    @GetMapping("/polygon-regions")
    @Timed
    public ResponseEntity<List<PolygonRegionDTO>> getAllPolygonRegions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PolygonRegions");
        Page<PolygonRegionDTO> page = polygonRegionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/polygon-regions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /polygon-regions/:id : get the "id" polygonRegion.
     *
     * @param id the id of the polygonRegionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the polygonRegionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/polygon-regions/{id}")
    @Timed
    public ResponseEntity<PolygonRegionDTO> getPolygonRegion(@PathVariable Long id) {
        log.debug("REST request to get PolygonRegion : {}", id);
        PolygonRegionDTO polygonRegionDTO = polygonRegionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(polygonRegionDTO));
    }

    /**
     * DELETE  /polygon-regions/:id : delete the "id" polygonRegion.
     *
     * @param id the id of the polygonRegionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/polygon-regions/{id}")
    @Timed
    public ResponseEntity<Void> deletePolygonRegion(@PathVariable Long id) {
        log.debug("REST request to delete PolygonRegion : {}", id);
        polygonRegionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/polygon-regions?query=:query : search for the polygonRegion corresponding
     * to the query.
     *
     * @param query the query of the polygonRegion search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/polygon-regions")
    @Timed
    public ResponseEntity<List<PolygonRegionDTO>> searchPolygonRegions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of PolygonRegions for query {}", query);
        Page<PolygonRegionDTO> page = polygonRegionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/polygon-regions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
