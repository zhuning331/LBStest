package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.ServiceConfigService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.ServiceConfigDTO;
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
 * REST controller for managing ServiceConfig.
 */
@RestController
@RequestMapping("/api")
public class ServiceConfigResource {

    private final Logger log = LoggerFactory.getLogger(ServiceConfigResource.class);

    private static final String ENTITY_NAME = "serviceConfig";

    private final ServiceConfigService serviceConfigService;

    public ServiceConfigResource(ServiceConfigService serviceConfigService) {
        this.serviceConfigService = serviceConfigService;
    }

    /**
     * POST  /service-configs : Create a new serviceConfig.
     *
     * @param serviceConfigDTO the serviceConfigDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceConfigDTO, or with status 400 (Bad Request) if the serviceConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-configs")
    @Timed
    public ResponseEntity<ServiceConfigDTO> createServiceConfig(@RequestBody ServiceConfigDTO serviceConfigDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceConfig : {}", serviceConfigDTO);
        if (serviceConfigDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new serviceConfig cannot already have an ID")).body(null);
        }
        ServiceConfigDTO result = serviceConfigService.save(serviceConfigDTO);
        return ResponseEntity.created(new URI("/api/service-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-configs : Updates an existing serviceConfig.
     *
     * @param serviceConfigDTO the serviceConfigDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceConfigDTO,
     * or with status 400 (Bad Request) if the serviceConfigDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceConfigDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-configs")
    @Timed
    public ResponseEntity<ServiceConfigDTO> updateServiceConfig(@RequestBody ServiceConfigDTO serviceConfigDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceConfig : {}", serviceConfigDTO);
        if (serviceConfigDTO.getId() == null) {
            return createServiceConfig(serviceConfigDTO);
        }
        ServiceConfigDTO result = serviceConfigService.save(serviceConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-configs : get all the serviceConfigs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serviceConfigs in body
     */
    @GetMapping("/service-configs")
    @Timed
    public ResponseEntity<List<ServiceConfigDTO>> getAllServiceConfigs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ServiceConfigs");
        Page<ServiceConfigDTO> page = serviceConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/service-configs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /service-configs/:id : get the "id" serviceConfig.
     *
     * @param id the id of the serviceConfigDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceConfigDTO, or with status 404 (Not Found)
     */
    @GetMapping("/service-configs/{id}")
    @Timed
    public ResponseEntity<ServiceConfigDTO> getServiceConfig(@PathVariable Long id) {
        log.debug("REST request to get ServiceConfig : {}", id);
        ServiceConfigDTO serviceConfigDTO = serviceConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(serviceConfigDTO));
    }

    /**
     * DELETE  /service-configs/:id : delete the "id" serviceConfig.
     *
     * @param id the id of the serviceConfigDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceConfig(@PathVariable Long id) {
        log.debug("REST request to delete ServiceConfig : {}", id);
        serviceConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/service-configs?query=:query : search for the serviceConfig corresponding
     * to the query.
     *
     * @param query the query of the serviceConfig search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/service-configs")
    @Timed
    public ResponseEntity<List<ServiceConfigDTO>> searchServiceConfigs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ServiceConfigs for query {}", query);
        Page<ServiceConfigDTO> page = serviceConfigService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/service-configs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
