package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.WebTypeService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.WebTypeDTO;
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
 * REST controller for managing WebType.
 */
@RestController
@RequestMapping("/api")
public class WebTypeResource {

    private final Logger log = LoggerFactory.getLogger(WebTypeResource.class);

    private static final String ENTITY_NAME = "webType";

    private final WebTypeService webTypeService;

    public WebTypeResource(WebTypeService webTypeService) {
        this.webTypeService = webTypeService;
    }

    /**
     * POST  /web-types : Create a new webType.
     *
     * @param webTypeDTO the webTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new webTypeDTO, or with status 400 (Bad Request) if the webType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/web-types")
    @Timed
    public ResponseEntity<WebTypeDTO> createWebType(@RequestBody WebTypeDTO webTypeDTO) throws URISyntaxException {
        log.debug("REST request to save WebType : {}", webTypeDTO);
        if (webTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new webType cannot already have an ID")).body(null);
        }
        WebTypeDTO result = webTypeService.save(webTypeDTO);
        return ResponseEntity.created(new URI("/api/web-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /web-types : Updates an existing webType.
     *
     * @param webTypeDTO the webTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated webTypeDTO,
     * or with status 400 (Bad Request) if the webTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the webTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/web-types")
    @Timed
    public ResponseEntity<WebTypeDTO> updateWebType(@RequestBody WebTypeDTO webTypeDTO) throws URISyntaxException {
        log.debug("REST request to update WebType : {}", webTypeDTO);
        if (webTypeDTO.getId() == null) {
            return createWebType(webTypeDTO);
        }
        WebTypeDTO result = webTypeService.save(webTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, webTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /web-types : get all the webTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of webTypes in body
     */
    @GetMapping("/web-types")
    @Timed
    public ResponseEntity<List<WebTypeDTO>> getAllWebTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of WebTypes");
        Page<WebTypeDTO> page = webTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/web-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /web-types/:id : get the "id" webType.
     *
     * @param id the id of the webTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the webTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/web-types/{id}")
    @Timed
    public ResponseEntity<WebTypeDTO> getWebType(@PathVariable Long id) {
        log.debug("REST request to get WebType : {}", id);
        WebTypeDTO webTypeDTO = webTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(webTypeDTO));
    }

    /**
     * DELETE  /web-types/:id : delete the "id" webType.
     *
     * @param id the id of the webTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/web-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteWebType(@PathVariable Long id) {
        log.debug("REST request to delete WebType : {}", id);
        webTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/web-types?query=:query : search for the webType corresponding
     * to the query.
     *
     * @param query the query of the webType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/web-types")
    @Timed
    public ResponseEntity<List<WebTypeDTO>> searchWebTypes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of WebTypes for query {}", query);
        Page<WebTypeDTO> page = webTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/web-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
