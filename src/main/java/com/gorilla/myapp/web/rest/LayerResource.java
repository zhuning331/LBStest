package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.LayerService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.LayerDTO;
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
 * REST controller for managing Layer.
 */
@RestController
@RequestMapping("/api")
public class LayerResource {

    private final Logger log = LoggerFactory.getLogger(LayerResource.class);

    private static final String ENTITY_NAME = "layer";

    private final LayerService layerService;

    public LayerResource(LayerService layerService) {
        this.layerService = layerService;
    }

    /**
     * POST  /layers : Create a new layer.
     *
     * @param layerDTO the layerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new layerDTO, or with status 400 (Bad Request) if the layer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/layers")
    @Timed
    public ResponseEntity<LayerDTO> createLayer(@RequestBody LayerDTO layerDTO) throws URISyntaxException {
        log.debug("REST request to save Layer : {}", layerDTO);
        if (layerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new layer cannot already have an ID")).body(null);
        }
        LayerDTO result = layerService.save(layerDTO);
        return ResponseEntity.created(new URI("/api/layers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /layers : Updates an existing layer.
     *
     * @param layerDTO the layerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated layerDTO,
     * or with status 400 (Bad Request) if the layerDTO is not valid,
     * or with status 500 (Internal Server Error) if the layerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/layers")
    @Timed
    public ResponseEntity<LayerDTO> updateLayer(@RequestBody LayerDTO layerDTO) throws URISyntaxException {
        log.debug("REST request to update Layer : {}", layerDTO);
        if (layerDTO.getId() == null) {
            return createLayer(layerDTO);
        }
        LayerDTO result = layerService.save(layerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, layerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /layers : get all the layers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of layers in body
     */
    @GetMapping("/layers")
    @Timed
    public ResponseEntity<List<LayerDTO>> getAllLayers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Layers");
        Page<LayerDTO> page = layerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/layers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /layers/:id : get the "id" layer.
     *
     * @param id the id of the layerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the layerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/layers/{id}")
    @Timed
    public ResponseEntity<LayerDTO> getLayer(@PathVariable Long id) {
        log.debug("REST request to get Layer : {}", id);
        LayerDTO layerDTO = layerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(layerDTO));
    }

    /**
     * DELETE  /layers/:id : delete the "id" layer.
     *
     * @param id the id of the layerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/layers/{id}")
    @Timed
    public ResponseEntity<Void> deleteLayer(@PathVariable Long id) {
        log.debug("REST request to delete Layer : {}", id);
        layerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/layers?query=:query : search for the layer corresponding
     * to the query.
     *
     * @param query the query of the layer search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/layers")
    @Timed
    public ResponseEntity<List<LayerDTO>> searchLayers(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Layers for query {}", query);
        Page<LayerDTO> page = layerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/layers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
