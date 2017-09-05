package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.RegularRegionService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.RegularRegionDTO;
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
 * REST controller for managing RegularRegion.
 */
@RestController
@RequestMapping("/api")
public class RegularRegionResource {

    private final Logger log = LoggerFactory.getLogger(RegularRegionResource.class);

    private static final String ENTITY_NAME = "regularRegion";

    private final RegularRegionService regularRegionService;

    public RegularRegionResource(RegularRegionService regularRegionService) {
        this.regularRegionService = regularRegionService;
    }

    /**
     * POST  /regular-regions : Create a new regularRegion.
     *
     * @param regularRegionDTO the regularRegionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regularRegionDTO, or with status 400 (Bad Request) if the regularRegion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regular-regions")
    @Timed
    public ResponseEntity<RegularRegionDTO> createRegularRegion(@RequestBody RegularRegionDTO regularRegionDTO) throws URISyntaxException {
        log.debug("REST request to save RegularRegion : {}", regularRegionDTO);
        if (regularRegionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new regularRegion cannot already have an ID")).body(null);
        }
        RegularRegionDTO result = regularRegionService.save(regularRegionDTO);
        return ResponseEntity.created(new URI("/api/regular-regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regular-regions : Updates an existing regularRegion.
     *
     * @param regularRegionDTO the regularRegionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regularRegionDTO,
     * or with status 400 (Bad Request) if the regularRegionDTO is not valid,
     * or with status 500 (Internal Server Error) if the regularRegionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regular-regions")
    @Timed
    public ResponseEntity<RegularRegionDTO> updateRegularRegion(@RequestBody RegularRegionDTO regularRegionDTO) throws URISyntaxException {
        log.debug("REST request to update RegularRegion : {}", regularRegionDTO);
        if (regularRegionDTO.getId() == null) {
            return createRegularRegion(regularRegionDTO);
        }
        RegularRegionDTO result = regularRegionService.save(regularRegionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, regularRegionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regular-regions : get all the regularRegions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of regularRegions in body
     */
    @GetMapping("/regular-regions")
    @Timed
    public ResponseEntity<List<RegularRegionDTO>> getAllRegularRegions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RegularRegions");
        Page<RegularRegionDTO> page = regularRegionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/regular-regions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /regular-regions/:id : get the "id" regularRegion.
     *
     * @param id the id of the regularRegionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regularRegionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/regular-regions/{id}")
    @Timed
    public ResponseEntity<RegularRegionDTO> getRegularRegion(@PathVariable Long id) {
        log.debug("REST request to get RegularRegion : {}", id);
        RegularRegionDTO regularRegionDTO = regularRegionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(regularRegionDTO));
    }

    /**
     * DELETE  /regular-regions/:id : delete the "id" regularRegion.
     *
     * @param id the id of the regularRegionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regular-regions/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegularRegion(@PathVariable Long id) {
        log.debug("REST request to delete RegularRegion : {}", id);
        regularRegionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/regular-regions?query=:query : search for the regularRegion corresponding
     * to the query.
     *
     * @param query the query of the regularRegion search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/regular-regions")
    @Timed
    public ResponseEntity<List<RegularRegionDTO>> searchRegularRegions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RegularRegions for query {}", query);
        Page<RegularRegionDTO> page = regularRegionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/regular-regions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
