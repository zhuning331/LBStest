package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.WebService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.WebDTO;
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
 * REST controller for managing Web.
 */
@RestController
@RequestMapping("/api")
public class WebResource {

    private final Logger log = LoggerFactory.getLogger(WebResource.class);

    private static final String ENTITY_NAME = "web";

    private final WebService webService;

    public WebResource(WebService webService) {
        this.webService = webService;
    }

    /**
     * POST  /webs : Create a new web.
     *
     * @param webDTO the webDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new webDTO, or with status 400 (Bad Request) if the web has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/webs")
    @Timed
    public ResponseEntity<WebDTO> createWeb(@RequestBody WebDTO webDTO) throws URISyntaxException {
        log.debug("REST request to save Web : {}", webDTO);
        if (webDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new web cannot already have an ID")).body(null);
        }
        WebDTO result = webService.save(webDTO);
        return ResponseEntity.created(new URI("/api/webs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /webs : Updates an existing web.
     *
     * @param webDTO the webDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated webDTO,
     * or with status 400 (Bad Request) if the webDTO is not valid,
     * or with status 500 (Internal Server Error) if the webDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/webs")
    @Timed
    public ResponseEntity<WebDTO> updateWeb(@RequestBody WebDTO webDTO) throws URISyntaxException {
        log.debug("REST request to update Web : {}", webDTO);
        if (webDTO.getId() == null) {
            return createWeb(webDTO);
        }
        WebDTO result = webService.save(webDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, webDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /webs : get all the webs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of webs in body
     */
    @GetMapping("/webs")
    @Timed
    public ResponseEntity<List<WebDTO>> getAllWebs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Webs");
        Page<WebDTO> page = webService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/webs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /webs/:id : get the "id" web.
     *
     * @param id the id of the webDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the webDTO, or with status 404 (Not Found)
     */
    @GetMapping("/webs/{id}")
    @Timed
    public ResponseEntity<WebDTO> getWeb(@PathVariable Long id) {
        log.debug("REST request to get Web : {}", id);
        WebDTO webDTO = webService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(webDTO));
    }

    /**
     * DELETE  /webs/:id : delete the "id" web.
     *
     * @param id the id of the webDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/webs/{id}")
    @Timed
    public ResponseEntity<Void> deleteWeb(@PathVariable Long id) {
        log.debug("REST request to delete Web : {}", id);
        webService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/webs?query=:query : search for the web corresponding
     * to the query.
     *
     * @param query the query of the web search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/webs")
    @Timed
    public ResponseEntity<List<WebDTO>> searchWebs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Webs for query {}", query);
        Page<WebDTO> page = webService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/webs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
