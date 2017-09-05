package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.Edge;
import com.gorilla.myapp.repository.EdgeRepository;
import com.gorilla.myapp.service.EdgeService;
import com.gorilla.myapp.repository.search.EdgeSearchRepository;
import com.gorilla.myapp.service.dto.EdgeDTO;
import com.gorilla.myapp.service.mapper.EdgeMapper;
import com.gorilla.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.gorilla.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EdgeResource REST controller.
 *
 * @see EdgeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class EdgeResourceIntTest {

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EdgeRepository edgeRepository;

    @Autowired
    private EdgeMapper edgeMapper;

    @Autowired
    private EdgeService edgeService;

    @Autowired
    private EdgeSearchRepository edgeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEdgeMockMvc;

    private Edge edge;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EdgeResource edgeResource = new EdgeResource(edgeService);
        this.restEdgeMockMvc = MockMvcBuilders.standaloneSetup(edgeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Edge createEntity(EntityManager em) {
        Edge edge = new Edge()
            .order(DEFAULT_ORDER)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return edge;
    }

    @Before
    public void initTest() {
        edgeSearchRepository.deleteAll();
        edge = createEntity(em);
    }

    @Test
    @Transactional
    public void createEdge() throws Exception {
        int databaseSizeBeforeCreate = edgeRepository.findAll().size();

        // Create the Edge
        EdgeDTO edgeDTO = edgeMapper.toDto(edge);
        restEdgeMockMvc.perform(post("/api/edges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(edgeDTO)))
            .andExpect(status().isCreated());

        // Validate the Edge in the database
        List<Edge> edgeList = edgeRepository.findAll();
        assertThat(edgeList).hasSize(databaseSizeBeforeCreate + 1);
        Edge testEdge = edgeList.get(edgeList.size() - 1);
        assertThat(testEdge.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testEdge.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testEdge.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testEdge.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the Edge in Elasticsearch
        Edge edgeEs = edgeSearchRepository.findOne(testEdge.getId());
        assertThat(edgeEs).isEqualToComparingFieldByField(testEdge);
    }

    @Test
    @Transactional
    public void createEdgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = edgeRepository.findAll().size();

        // Create the Edge with an existing ID
        edge.setId(1L);
        EdgeDTO edgeDTO = edgeMapper.toDto(edge);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEdgeMockMvc.perform(post("/api/edges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(edgeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Edge> edgeList = edgeRepository.findAll();
        assertThat(edgeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEdges() throws Exception {
        // Initialize the database
        edgeRepository.saveAndFlush(edge);

        // Get all the edgeList
        restEdgeMockMvc.perform(get("/api/edges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(edge.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getEdge() throws Exception {
        // Initialize the database
        edgeRepository.saveAndFlush(edge);

        // Get the edge
        restEdgeMockMvc.perform(get("/api/edges/{id}", edge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(edge.getId().intValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingEdge() throws Exception {
        // Get the edge
        restEdgeMockMvc.perform(get("/api/edges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEdge() throws Exception {
        // Initialize the database
        edgeRepository.saveAndFlush(edge);
        edgeSearchRepository.save(edge);
        int databaseSizeBeforeUpdate = edgeRepository.findAll().size();

        // Update the edge
        Edge updatedEdge = edgeRepository.findOne(edge.getId());
        updatedEdge
            .order(UPDATED_ORDER)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        EdgeDTO edgeDTO = edgeMapper.toDto(updatedEdge);

        restEdgeMockMvc.perform(put("/api/edges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(edgeDTO)))
            .andExpect(status().isOk());

        // Validate the Edge in the database
        List<Edge> edgeList = edgeRepository.findAll();
        assertThat(edgeList).hasSize(databaseSizeBeforeUpdate);
        Edge testEdge = edgeList.get(edgeList.size() - 1);
        assertThat(testEdge.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testEdge.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testEdge.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testEdge.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the Edge in Elasticsearch
        Edge edgeEs = edgeSearchRepository.findOne(testEdge.getId());
        assertThat(edgeEs).isEqualToComparingFieldByField(testEdge);
    }

    @Test
    @Transactional
    public void updateNonExistingEdge() throws Exception {
        int databaseSizeBeforeUpdate = edgeRepository.findAll().size();

        // Create the Edge
        EdgeDTO edgeDTO = edgeMapper.toDto(edge);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEdgeMockMvc.perform(put("/api/edges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(edgeDTO)))
            .andExpect(status().isCreated());

        // Validate the Edge in the database
        List<Edge> edgeList = edgeRepository.findAll();
        assertThat(edgeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEdge() throws Exception {
        // Initialize the database
        edgeRepository.saveAndFlush(edge);
        edgeSearchRepository.save(edge);
        int databaseSizeBeforeDelete = edgeRepository.findAll().size();

        // Get the edge
        restEdgeMockMvc.perform(delete("/api/edges/{id}", edge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean edgeExistsInEs = edgeSearchRepository.exists(edge.getId());
        assertThat(edgeExistsInEs).isFalse();

        // Validate the database is empty
        List<Edge> edgeList = edgeRepository.findAll();
        assertThat(edgeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEdge() throws Exception {
        // Initialize the database
        edgeRepository.saveAndFlush(edge);
        edgeSearchRepository.save(edge);

        // Search the edge
        restEdgeMockMvc.perform(get("/api/_search/edges?query=id:" + edge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(edge.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Edge.class);
        Edge edge1 = new Edge();
        edge1.setId(1L);
        Edge edge2 = new Edge();
        edge2.setId(edge1.getId());
        assertThat(edge1).isEqualTo(edge2);
        edge2.setId(2L);
        assertThat(edge1).isNotEqualTo(edge2);
        edge1.setId(null);
        assertThat(edge1).isNotEqualTo(edge2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EdgeDTO.class);
        EdgeDTO edgeDTO1 = new EdgeDTO();
        edgeDTO1.setId(1L);
        EdgeDTO edgeDTO2 = new EdgeDTO();
        assertThat(edgeDTO1).isNotEqualTo(edgeDTO2);
        edgeDTO2.setId(edgeDTO1.getId());
        assertThat(edgeDTO1).isEqualTo(edgeDTO2);
        edgeDTO2.setId(2L);
        assertThat(edgeDTO1).isNotEqualTo(edgeDTO2);
        edgeDTO1.setId(null);
        assertThat(edgeDTO1).isNotEqualTo(edgeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(edgeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(edgeMapper.fromId(null)).isNull();
    }
}
