package com.gorilla.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gorilla.myapp.service.NodeService;
import com.gorilla.myapp.web.rest.util.HeaderUtil;
import com.gorilla.myapp.web.rest.util.PaginationUtil;
import com.gorilla.myapp.service.dto.NodeDTO;
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
 * REST controller for managing Node.
 */
@RestController
@RequestMapping("/api")
public class NodeResource {

    private final Logger log = LoggerFactory.getLogger(NodeResource.class);

    private static final String ENTITY_NAME = "node";

    private final NodeService nodeService;

    public NodeResource(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    /**
     * POST  /nodes : Create a new node.
     *
     * @param nodeDTO the nodeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nodeDTO, or with status 400 (Bad Request) if the node has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nodes")
    @Timed
    public ResponseEntity<NodeDTO> createNode(@RequestBody NodeDTO nodeDTO) throws URISyntaxException {
        log.debug("REST request to save Node : {}", nodeDTO);
        if (nodeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new node cannot already have an ID")).body(null);
        }
        NodeDTO result = nodeService.save(nodeDTO);
        return ResponseEntity.created(new URI("/api/nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nodes : Updates an existing node.
     *
     * @param nodeDTO the nodeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nodeDTO,
     * or with status 400 (Bad Request) if the nodeDTO is not valid,
     * or with status 500 (Internal Server Error) if the nodeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nodes")
    @Timed
    public ResponseEntity<NodeDTO> updateNode(@RequestBody NodeDTO nodeDTO) throws URISyntaxException {
        log.debug("REST request to update Node : {}", nodeDTO);
        if (nodeDTO.getId() == null) {
            return createNode(nodeDTO);
        }
        NodeDTO result = nodeService.save(nodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nodes : get all the nodes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nodes in body
     */
    @GetMapping("/nodes")
    @Timed
    public ResponseEntity<List<NodeDTO>> getAllNodes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Nodes");
        Page<NodeDTO> page = nodeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nodes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /nodes/:id : get the "id" node.
     *
     * @param id the id of the nodeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nodeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/nodes/{id}")
    @Timed
    public ResponseEntity<NodeDTO> getNode(@PathVariable Long id) {
        log.debug("REST request to get Node : {}", id);
        NodeDTO nodeDTO = nodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nodeDTO));
    }

    /**
     * DELETE  /nodes/:id : delete the "id" node.
     *
     * @param id the id of the nodeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nodes/{id}")
    @Timed
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        log.debug("REST request to delete Node : {}", id);
        nodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/nodes?query=:query : search for the node corresponding
     * to the query.
     *
     * @param query the query of the node search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/nodes")
    @Timed
    public ResponseEntity<List<NodeDTO>> searchNodes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Nodes for query {}", query);
        Page<NodeDTO> page = nodeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/nodes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
