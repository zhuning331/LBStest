package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.BindingPOIService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.BindingPOIDTO;
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
 * REST controller for managing BindingPOI.
 */
@RestController
@RequestMapping("/api")
public class BindingPOIResource {

    private final Logger log = LoggerFactory.getLogger(BindingPOIResource.class);

    private static final String ENTITY_NAME = "bindingPOI";

    private final BindingPOIService bindingPOIService;

    public BindingPOIResource(BindingPOIService bindingPOIService) {
        this.bindingPOIService = bindingPOIService;
    }

    /**
     * POST  /binding-pois : Create a new bindingPOI.
     *
     * @param bindingPOIDTO the bindingPOIDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bindingPOIDTO, or with status 400 (Bad Request) if the bindingPOI has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/binding-pois")
    @Timed
    public ResponseEntity<BindingPOIDTO> createBindingPOI(@RequestBody BindingPOIDTO bindingPOIDTO) throws URISyntaxException {
        log.debug("REST request to save BindingPOI : {}", bindingPOIDTO);
        if (bindingPOIDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bindingPOI cannot already have an ID")).body(null);
        }
        BindingPOIDTO result = bindingPOIService.save(bindingPOIDTO);
        return ResponseEntity.created(new URI("/api/binding-pois/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /binding-pois : Updates an existing bindingPOI.
     *
     * @param bindingPOIDTO the bindingPOIDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bindingPOIDTO,
     * or with status 400 (Bad Request) if the bindingPOIDTO is not valid,
     * or with status 500 (Internal Server Error) if the bindingPOIDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/binding-pois")
    @Timed
    public ResponseEntity<BindingPOIDTO> updateBindingPOI(@RequestBody BindingPOIDTO bindingPOIDTO) throws URISyntaxException {
        log.debug("REST request to update BindingPOI : {}", bindingPOIDTO);
        if (bindingPOIDTO.getId() == null) {
            return createBindingPOI(bindingPOIDTO);
        }
        BindingPOIDTO result = bindingPOIService.save(bindingPOIDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bindingPOIDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /binding-pois : get all the bindingPOIS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bindingPOIS in body
     */
    @GetMapping("/binding-pois")
    @Timed
    public ResponseEntity<List<BindingPOIDTO>> getAllBindingPOIS(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BindingPOIS");
        Page<BindingPOIDTO> page = bindingPOIService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/binding-pois");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /binding-pois/:id : get the "id" bindingPOI.
     *
     * @param id the id of the bindingPOIDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bindingPOIDTO, or with status 404 (Not Found)
     */
    @GetMapping("/binding-pois/{id}")
    @Timed
    public ResponseEntity<BindingPOIDTO> getBindingPOI(@PathVariable Long id) {
        log.debug("REST request to get BindingPOI : {}", id);
        BindingPOIDTO bindingPOIDTO = bindingPOIService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bindingPOIDTO));
    }

    /**
     * DELETE  /binding-pois/:id : delete the "id" bindingPOI.
     *
     * @param id the id of the bindingPOIDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/binding-pois/{id}")
    @Timed
    public ResponseEntity<Void> deleteBindingPOI(@PathVariable Long id) {
        log.debug("REST request to delete BindingPOI : {}", id);
        bindingPOIService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/binding-pois?query=:query : search for the bindingPOI corresponding
     * to the query.
     *
     * @param query the query of the bindingPOI search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/binding-pois")
    @Timed
    public ResponseEntity<List<BindingPOIDTO>> searchBindingPOIS(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of BindingPOIS for query {}", query);
        Page<BindingPOIDTO> page = bindingPOIService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/binding-pois");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
