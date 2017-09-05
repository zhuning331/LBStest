package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.LocationUpdateService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.LocationUpdateDTO;
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
 * REST controller for managing LocationUpdate.
 */
@RestController
@RequestMapping("/api")
public class LocationUpdateResource {

    private final Logger log = LoggerFactory.getLogger(LocationUpdateResource.class);

    private static final String ENTITY_NAME = "locationUpdate";

    private final LocationUpdateService locationUpdateService;

    public LocationUpdateResource(LocationUpdateService locationUpdateService) {
        this.locationUpdateService = locationUpdateService;
    }

    /**
     * POST  /location-updates : Create a new locationUpdate.
     *
     * @param locationUpdateDTO the locationUpdateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locationUpdateDTO, or with status 400 (Bad Request) if the locationUpdate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/location-updates")
    @Timed
    public ResponseEntity<LocationUpdateDTO> createLocationUpdate(@RequestBody LocationUpdateDTO locationUpdateDTO) throws URISyntaxException {
        log.debug("REST request to save LocationUpdate : {}", locationUpdateDTO);
        if (locationUpdateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new locationUpdate cannot already have an ID")).body(null);
        }
        LocationUpdateDTO result = locationUpdateService.save(locationUpdateDTO);
        return ResponseEntity.created(new URI("/api/location-updates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /location-updates : Updates an existing locationUpdate.
     *
     * @param locationUpdateDTO the locationUpdateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locationUpdateDTO,
     * or with status 400 (Bad Request) if the locationUpdateDTO is not valid,
     * or with status 500 (Internal Server Error) if the locationUpdateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/location-updates")
    @Timed
    public ResponseEntity<LocationUpdateDTO> updateLocationUpdate(@RequestBody LocationUpdateDTO locationUpdateDTO) throws URISyntaxException {
        log.debug("REST request to update LocationUpdate : {}", locationUpdateDTO);
        if (locationUpdateDTO.getId() == null) {
            return createLocationUpdate(locationUpdateDTO);
        }
        LocationUpdateDTO result = locationUpdateService.save(locationUpdateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, locationUpdateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /location-updates : get all the locationUpdates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of locationUpdates in body
     */
    @GetMapping("/location-updates")
    @Timed
    public ResponseEntity<List<LocationUpdateDTO>> getAllLocationUpdates(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LocationUpdates");
        Page<LocationUpdateDTO> page = locationUpdateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/location-updates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /location-updates/:id : get the "id" locationUpdate.
     *
     * @param id the id of the locationUpdateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locationUpdateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/location-updates/{id}")
    @Timed
    public ResponseEntity<LocationUpdateDTO> getLocationUpdate(@PathVariable Long id) {
        log.debug("REST request to get LocationUpdate : {}", id);
        LocationUpdateDTO locationUpdateDTO = locationUpdateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(locationUpdateDTO));
    }

    /**
     * DELETE  /location-updates/:id : delete the "id" locationUpdate.
     *
     * @param id the id of the locationUpdateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/location-updates/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocationUpdate(@PathVariable Long id) {
        log.debug("REST request to delete LocationUpdate : {}", id);
        locationUpdateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/location-updates?query=:query : search for the locationUpdate corresponding
     * to the query.
     *
     * @param query the query of the locationUpdate search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/location-updates")
    @Timed
    public ResponseEntity<List<LocationUpdateDTO>> searchLocationUpdates(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of LocationUpdates for query {}", query);
        Page<LocationUpdateDTO> page = locationUpdateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/location-updates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
