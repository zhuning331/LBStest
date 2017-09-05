package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.POIService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.POIDTO;
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
 * REST controller for managing POI.
 */
@RestController
@RequestMapping("/api")
public class POIResource {

    private final Logger log = LoggerFactory.getLogger(POIResource.class);

    private static final String ENTITY_NAME = "pOI";

    private final POIService pOIService;

    public POIResource(POIService pOIService) {
        this.pOIService = pOIService;
    }

    /**
     * POST  /p-ois : Create a new pOI.
     *
     * @param pOIDTO the pOIDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pOIDTO, or with status 400 (Bad Request) if the pOI has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-ois")
    @Timed
    public ResponseEntity<POIDTO> createPOI(@RequestBody POIDTO pOIDTO) throws URISyntaxException {
        log.debug("REST request to save POI : {}", pOIDTO);
        if (pOIDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pOI cannot already have an ID")).body(null);
        }
        POIDTO result = pOIService.save(pOIDTO);
        return ResponseEntity.created(new URI("/api/p-ois/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-ois : Updates an existing pOI.
     *
     * @param pOIDTO the pOIDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pOIDTO,
     * or with status 400 (Bad Request) if the pOIDTO is not valid,
     * or with status 500 (Internal Server Error) if the pOIDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-ois")
    @Timed
    public ResponseEntity<POIDTO> updatePOI(@RequestBody POIDTO pOIDTO) throws URISyntaxException {
        log.debug("REST request to update POI : {}", pOIDTO);
        if (pOIDTO.getId() == null) {
            return createPOI(pOIDTO);
        }
        POIDTO result = pOIService.save(pOIDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pOIDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-ois : get all the pOIS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pOIS in body
     */
    @GetMapping("/p-ois")
    @Timed
    public ResponseEntity<List<POIDTO>> getAllPOIS(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of POIS");
        Page<POIDTO> page = pOIService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-ois");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-ois/:id : get the "id" pOI.
     *
     * @param id the id of the pOIDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pOIDTO, or with status 404 (Not Found)
     */
    @GetMapping("/p-ois/{id}")
    @Timed
    public ResponseEntity<POIDTO> getPOI(@PathVariable Long id) {
        log.debug("REST request to get POI : {}", id);
        POIDTO pOIDTO = pOIService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pOIDTO));
    }

    /**
     * DELETE  /p-ois/:id : delete the "id" pOI.
     *
     * @param id the id of the pOIDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-ois/{id}")
    @Timed
    public ResponseEntity<Void> deletePOI(@PathVariable Long id) {
        log.debug("REST request to delete POI : {}", id);
        pOIService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/p-ois?query=:query : search for the pOI corresponding
     * to the query.
     *
     * @param query the query of the pOI search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/p-ois")
    @Timed
    public ResponseEntity<List<POIDTO>> searchPOIS(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of POIS for query {}", query);
        Page<POIDTO> page = pOIService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/p-ois");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
