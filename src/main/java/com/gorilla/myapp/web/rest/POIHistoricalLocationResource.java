package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.POIHistoricalLocationService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.POIHistoricalLocationDTO;
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
 * REST controller for managing POIHistoricalLocation.
 */
@RestController
@RequestMapping("/api")
public class POIHistoricalLocationResource {

    private final Logger log = LoggerFactory.getLogger(POIHistoricalLocationResource.class);

    private static final String ENTITY_NAME = "pOIHistoricalLocation";

    private final POIHistoricalLocationService pOIHistoricalLocationService;

    public POIHistoricalLocationResource(POIHistoricalLocationService pOIHistoricalLocationService) {
        this.pOIHistoricalLocationService = pOIHistoricalLocationService;
    }

    /**
     * POST  /p-oi-historical-locations : Create a new pOIHistoricalLocation.
     *
     * @param pOIHistoricalLocationDTO the pOIHistoricalLocationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pOIHistoricalLocationDTO, or with status 400 (Bad Request) if the pOIHistoricalLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-oi-historical-locations")
    @Timed
    public ResponseEntity<POIHistoricalLocationDTO> createPOIHistoricalLocation(@RequestBody POIHistoricalLocationDTO pOIHistoricalLocationDTO) throws URISyntaxException {
        log.debug("REST request to save POIHistoricalLocation : {}", pOIHistoricalLocationDTO);
        if (pOIHistoricalLocationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pOIHistoricalLocation cannot already have an ID")).body(null);
        }
        POIHistoricalLocationDTO result = pOIHistoricalLocationService.save(pOIHistoricalLocationDTO);
        return ResponseEntity.created(new URI("/api/p-oi-historical-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-oi-historical-locations : Updates an existing pOIHistoricalLocation.
     *
     * @param pOIHistoricalLocationDTO the pOIHistoricalLocationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pOIHistoricalLocationDTO,
     * or with status 400 (Bad Request) if the pOIHistoricalLocationDTO is not valid,
     * or with status 500 (Internal Server Error) if the pOIHistoricalLocationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-oi-historical-locations")
    @Timed
    public ResponseEntity<POIHistoricalLocationDTO> updatePOIHistoricalLocation(@RequestBody POIHistoricalLocationDTO pOIHistoricalLocationDTO) throws URISyntaxException {
        log.debug("REST request to update POIHistoricalLocation : {}", pOIHistoricalLocationDTO);
        if (pOIHistoricalLocationDTO.getId() == null) {
            return createPOIHistoricalLocation(pOIHistoricalLocationDTO);
        }
        POIHistoricalLocationDTO result = pOIHistoricalLocationService.save(pOIHistoricalLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pOIHistoricalLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-oi-historical-locations : get all the pOIHistoricalLocations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pOIHistoricalLocations in body
     */
    @GetMapping("/p-oi-historical-locations")
    @Timed
    public ResponseEntity<List<POIHistoricalLocationDTO>> getAllPOIHistoricalLocations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of POIHistoricalLocations");
        Page<POIHistoricalLocationDTO> page = pOIHistoricalLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-oi-historical-locations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-oi-historical-locations/:id : get the "id" pOIHistoricalLocation.
     *
     * @param id the id of the pOIHistoricalLocationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pOIHistoricalLocationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/p-oi-historical-locations/{id}")
    @Timed
    public ResponseEntity<POIHistoricalLocationDTO> getPOIHistoricalLocation(@PathVariable Long id) {
        log.debug("REST request to get POIHistoricalLocation : {}", id);
        POIHistoricalLocationDTO pOIHistoricalLocationDTO = pOIHistoricalLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pOIHistoricalLocationDTO));
    }

    /**
     * DELETE  /p-oi-historical-locations/:id : delete the "id" pOIHistoricalLocation.
     *
     * @param id the id of the pOIHistoricalLocationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-oi-historical-locations/{id}")
    @Timed
    public ResponseEntity<Void> deletePOIHistoricalLocation(@PathVariable Long id) {
        log.debug("REST request to delete POIHistoricalLocation : {}", id);
        pOIHistoricalLocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/p-oi-historical-locations?query=:query : search for the pOIHistoricalLocation corresponding
     * to the query.
     *
     * @param query the query of the pOIHistoricalLocation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/p-oi-historical-locations")
    @Timed
    public ResponseEntity<List<POIHistoricalLocationDTO>> searchPOIHistoricalLocations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of POIHistoricalLocations for query {}", query);
        Page<POIHistoricalLocationDTO> page = pOIHistoricalLocationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/p-oi-historical-locations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
