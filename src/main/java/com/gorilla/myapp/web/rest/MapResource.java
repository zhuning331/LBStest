package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.MapService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.MapDTO;
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
 * REST controller for managing Map.
 */
@RestController
@RequestMapping("/api")
public class MapResource {

    private final Logger log = LoggerFactory.getLogger(MapResource.class);

    private static final String ENTITY_NAME = "map";

    private final MapService mapService;

    public MapResource(MapService mapService) {
        this.mapService = mapService;
    }

    /**
     * POST  /maps : Create a new map.
     *
     * @param mapDTO the mapDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mapDTO, or with status 400 (Bad Request) if the map has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/maps")
    @Timed
    public ResponseEntity<MapDTO> createMap(@RequestBody MapDTO mapDTO) throws URISyntaxException {
        log.debug("REST request to save Map : {}", mapDTO);
        if (mapDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new map cannot already have an ID")).body(null);
        }
        MapDTO result = mapService.save(mapDTO);
        return ResponseEntity.created(new URI("/api/maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /maps : Updates an existing map.
     *
     * @param mapDTO the mapDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mapDTO,
     * or with status 400 (Bad Request) if the mapDTO is not valid,
     * or with status 500 (Internal Server Error) if the mapDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/maps")
    @Timed
    public ResponseEntity<MapDTO> updateMap(@RequestBody MapDTO mapDTO) throws URISyntaxException {
        log.debug("REST request to update Map : {}", mapDTO);
        if (mapDTO.getId() == null) {
            return createMap(mapDTO);
        }
        MapDTO result = mapService.save(mapDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mapDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /maps : get all the maps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of maps in body
     */
    @GetMapping("/maps")
    @Timed
    public ResponseEntity<List<MapDTO>> getAllMaps(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Maps");
        Page<MapDTO> page = mapService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/maps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /maps/:id : get the "id" map.
     *
     * @param id the id of the mapDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mapDTO, or with status 404 (Not Found)
     */
    @GetMapping("/maps/{id}")
    @Timed
    public ResponseEntity<MapDTO> getMap(@PathVariable Long id) {
        log.debug("REST request to get Map : {}", id);
        MapDTO mapDTO = mapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mapDTO));
    }

    /**
     * DELETE  /maps/:id : delete the "id" map.
     *
     * @param id the id of the mapDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/maps/{id}")
    @Timed
    public ResponseEntity<Void> deleteMap(@PathVariable Long id) {
        log.debug("REST request to delete Map : {}", id);
        mapService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/maps?query=:query : search for the map corresponding
     * to the query.
     *
     * @param query the query of the map search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/maps")
    @Timed
    public ResponseEntity<List<MapDTO>> searchMaps(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Maps for query {}", query);
        Page<MapDTO> page = mapService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/maps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
