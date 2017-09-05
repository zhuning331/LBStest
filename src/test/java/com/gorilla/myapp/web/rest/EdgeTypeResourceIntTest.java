package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.EdgeType;
import com.gorilla.myapp.repository.EdgeTypeRepository;
import com.gorilla.myapp.service.EdgeTypeService;
import com.gorilla.myapp.repository.search.EdgeTypeSearchRepository;
import com.gorilla.myapp.service.dto.EdgeTypeDTO;
import com.gorilla.myapp.service.mapper.EdgeTypeMapper;
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
 * Test class for the EdgeTypeResource REST controller.
 *
 * @see EdgeTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class EdgeTypeResourceIntTest {

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Double DEFAULT_WIDTH = 1D;
    private static final Double UPDATED_WIDTH = 2D;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EdgeTypeRepository edgeTypeRepository;

    @Autowired
    private EdgeTypeMapper edgeTypeMapper;

    @Autowired
    private EdgeTypeService edgeTypeService;

    @Autowired
    private EdgeTypeSearchRepository edgeTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEdgeTypeMockMvc;

    private EdgeType edgeType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EdgeTypeResource edgeTypeResource = new EdgeTypeResource(edgeTypeService);
        this.restEdgeTypeMockMvc = MockMvcBuilders.standaloneSetup(edgeTypeResource)
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
    public static EdgeType createEntity(EntityManager em) {
        EdgeType edgeType = new EdgeType()
            .priority(DEFAULT_PRIORITY)
            .type(DEFAULT_TYPE)
            .color(DEFAULT_COLOR)
            .width(DEFAULT_WIDTH)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return edgeType;
    }

    @Before
    public void initTest() {
        edgeTypeSearchRepository.deleteAll();
        edgeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEdgeType() throws Exception {
        int databaseSizeBeforeCreate = edgeTypeRepository.findAll().size();

        // Create the EdgeType
        EdgeTypeDTO edgeTypeDTO = edgeTypeMapper.toDto(edgeType);
        restEdgeTypeMockMvc.perform(post("/api/edge-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(edgeTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the EdgeType in the database
        List<EdgeType> edgeTypeList = edgeTypeRepository.findAll();
        assertThat(edgeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EdgeType testEdgeType = edgeTypeList.get(edgeTypeList.size() - 1);
        assertThat(testEdgeType.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testEdgeType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEdgeType.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testEdgeType.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testEdgeType.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testEdgeType.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testEdgeType.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the EdgeType in Elasticsearch
        EdgeType edgeTypeEs = edgeTypeSearchRepository.findOne(testEdgeType.getId());
        assertThat(edgeTypeEs).isEqualToComparingFieldByField(testEdgeType);
    }

    @Test
    @Transactional
    public void createEdgeTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = edgeTypeRepository.findAll().size();

        // Create the EdgeType with an existing ID
        edgeType.setId(1L);
        EdgeTypeDTO edgeTypeDTO = edgeTypeMapper.toDto(edgeType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEdgeTypeMockMvc.perform(post("/api/edge-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(edgeTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EdgeType> edgeTypeList = edgeTypeRepository.findAll();
        assertThat(edgeTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEdgeTypes() throws Exception {
        // Initialize the database
        edgeTypeRepository.saveAndFlush(edgeType);

        // Get all the edgeTypeList
        restEdgeTypeMockMvc.perform(get("/api/edge-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(edgeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getEdgeType() throws Exception {
        // Initialize the database
        edgeTypeRepository.saveAndFlush(edgeType);

        // Get the edgeType
        restEdgeTypeMockMvc.perform(get("/api/edge-types/{id}", edgeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(edgeType.getId().intValue()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.doubleValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingEdgeType() throws Exception {
        // Get the edgeType
        restEdgeTypeMockMvc.perform(get("/api/edge-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEdgeType() throws Exception {
        // Initialize the database
        edgeTypeRepository.saveAndFlush(edgeType);
        edgeTypeSearchRepository.save(edgeType);
        int databaseSizeBeforeUpdate = edgeTypeRepository.findAll().size();

        // Update the edgeType
        EdgeType updatedEdgeType = edgeTypeRepository.findOne(edgeType.getId());
        updatedEdgeType
            .priority(UPDATED_PRIORITY)
            .type(UPDATED_TYPE)
            .color(UPDATED_COLOR)
            .width(UPDATED_WIDTH)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        EdgeTypeDTO edgeTypeDTO = edgeTypeMapper.toDto(updatedEdgeType);

        restEdgeTypeMockMvc.perform(put("/api/edge-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(edgeTypeDTO)))
            .andExpect(status().isOk());

        // Validate the EdgeType in the database
        List<EdgeType> edgeTypeList = edgeTypeRepository.findAll();
        assertThat(edgeTypeList).hasSize(databaseSizeBeforeUpdate);
        EdgeType testEdgeType = edgeTypeList.get(edgeTypeList.size() - 1);
        assertThat(testEdgeType.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testEdgeType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEdgeType.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testEdgeType.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testEdgeType.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testEdgeType.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testEdgeType.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the EdgeType in Elasticsearch
        EdgeType edgeTypeEs = edgeTypeSearchRepository.findOne(testEdgeType.getId());
        assertThat(edgeTypeEs).isEqualToComparingFieldByField(testEdgeType);
    }

    @Test
    @Transactional
    public void updateNonExistingEdgeType() throws Exception {
        int databaseSizeBeforeUpdate = edgeTypeRepository.findAll().size();

        // Create the EdgeType
        EdgeTypeDTO edgeTypeDTO = edgeTypeMapper.toDto(edgeType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEdgeTypeMockMvc.perform(put("/api/edge-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(edgeTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the EdgeType in the database
        List<EdgeType> edgeTypeList = edgeTypeRepository.findAll();
        assertThat(edgeTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEdgeType() throws Exception {
        // Initialize the database
        edgeTypeRepository.saveAndFlush(edgeType);
        edgeTypeSearchRepository.save(edgeType);
        int databaseSizeBeforeDelete = edgeTypeRepository.findAll().size();

        // Get the edgeType
        restEdgeTypeMockMvc.perform(delete("/api/edge-types/{id}", edgeType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean edgeTypeExistsInEs = edgeTypeSearchRepository.exists(edgeType.getId());
        assertThat(edgeTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<EdgeType> edgeTypeList = edgeTypeRepository.findAll();
        assertThat(edgeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEdgeType() throws Exception {
        // Initialize the database
        edgeTypeRepository.saveAndFlush(edgeType);
        edgeTypeSearchRepository.save(edgeType);

        // Search the edgeType
        restEdgeTypeMockMvc.perform(get("/api/_search/edge-types?query=id:" + edgeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(edgeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EdgeType.class);
        EdgeType edgeType1 = new EdgeType();
        edgeType1.setId(1L);
        EdgeType edgeType2 = new EdgeType();
        edgeType2.setId(edgeType1.getId());
        assertThat(edgeType1).isEqualTo(edgeType2);
        edgeType2.setId(2L);
        assertThat(edgeType1).isNotEqualTo(edgeType2);
        edgeType1.setId(null);
        assertThat(edgeType1).isNotEqualTo(edgeType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EdgeTypeDTO.class);
        EdgeTypeDTO edgeTypeDTO1 = new EdgeTypeDTO();
        edgeTypeDTO1.setId(1L);
        EdgeTypeDTO edgeTypeDTO2 = new EdgeTypeDTO();
        assertThat(edgeTypeDTO1).isNotEqualTo(edgeTypeDTO2);
        edgeTypeDTO2.setId(edgeTypeDTO1.getId());
        assertThat(edgeTypeDTO1).isEqualTo(edgeTypeDTO2);
        edgeTypeDTO2.setId(2L);
        assertThat(edgeTypeDTO1).isNotEqualTo(edgeTypeDTO2);
        edgeTypeDTO1.setId(null);
        assertThat(edgeTypeDTO1).isNotEqualTo(edgeTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(edgeTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(edgeTypeMapper.fromId(null)).isNull();
    }
}
