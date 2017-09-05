package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.POITypeService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.POITypeDTO;
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
 * REST controller for managing POIType.
 */
@RestController
@RequestMapping("/api")
public class POITypeResource {

    private final Logger log = LoggerFactory.getLogger(POITypeResource.class);

    private static final String ENTITY_NAME = "pOIType";

    private final POITypeService pOITypeService;

    public POITypeResource(POITypeService pOITypeService) {
        this.pOITypeService = pOITypeService;
    }

    /**
     * POST  /p-oi-types : Create a new pOIType.
     *
     * @param pOITypeDTO the pOITypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pOITypeDTO, or with status 400 (Bad Request) if the pOIType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-oi-types")
    @Timed
    public ResponseEntity<POITypeDTO> createPOIType(@RequestBody POITypeDTO pOITypeDTO) throws URISyntaxException {
        log.debug("REST request to save POIType : {}", pOITypeDTO);
        if (pOITypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pOIType cannot already have an ID")).body(null);
        }
        POITypeDTO result = pOITypeService.save(pOITypeDTO);
        return ResponseEntity.created(new URI("/api/p-oi-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-oi-types : Updates an existing pOIType.
     *
     * @param pOITypeDTO the pOITypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pOITypeDTO,
     * or with status 400 (Bad Request) if the pOITypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the pOITypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-oi-types")
    @Timed
    public ResponseEntity<POITypeDTO> updatePOIType(@RequestBody POITypeDTO pOITypeDTO) throws URISyntaxException {
        log.debug("REST request to update POIType : {}", pOITypeDTO);
        if (pOITypeDTO.getId() == null) {
            return createPOIType(pOITypeDTO);
        }
        POITypeDTO result = pOITypeService.save(pOITypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pOITypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-oi-types : get all the pOITypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pOITypes in body
     */
    @GetMapping("/p-oi-types")
    @Timed
    public ResponseEntity<List<POITypeDTO>> getAllPOITypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of POITypes");
        Page<POITypeDTO> page = pOITypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-oi-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-oi-types/:id : get the "id" pOIType.
     *
     * @param id the id of the pOITypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pOITypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/p-oi-types/{id}")
    @Timed
    public ResponseEntity<POITypeDTO> getPOIType(@PathVariable Long id) {
        log.debug("REST request to get POIType : {}", id);
        POITypeDTO pOITypeDTO = pOITypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pOITypeDTO));
    }

    /**
     * DELETE  /p-oi-types/:id : delete the "id" pOIType.
     *
     * @param id the id of the pOITypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-oi-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePOIType(@PathVariable Long id) {
        log.debug("REST request to delete POIType : {}", id);
        pOITypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/p-oi-types?query=:query : search for the pOIType corresponding
     * to the query.
     *
     * @param query the query of the pOIType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/p-oi-types")
    @Timed
    public ResponseEntity<List<POITypeDTO>> searchPOITypes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of POITypes for query {}", query);
        Page<POITypeDTO> page = pOITypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/p-oi-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
