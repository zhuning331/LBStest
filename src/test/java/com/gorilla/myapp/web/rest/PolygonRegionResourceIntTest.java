package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.PolygonRegion;
import com.gorilla.myapp.repository.PolygonRegionRepository;
import com.gorilla.myapp.service.PolygonRegionService;
import com.gorilla.myapp.repository.search.PolygonRegionSearchRepository;
import com.gorilla.myapp.service.dto.PolygonRegionDTO;
import com.gorilla.myapp.service.mapper.PolygonRegionMapper;
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
 * Test class for the PolygonRegionResource REST controller.
 *
 * @see PolygonRegionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class PolygonRegionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PolygonRegionRepository polygonRegionRepository;

    @Autowired
    private PolygonRegionMapper polygonRegionMapper;

    @Autowired
    private PolygonRegionService polygonRegionService;

    @Autowired
    private PolygonRegionSearchRepository polygonRegionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPolygonRegionMockMvc;

    private PolygonRegion polygonRegion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PolygonRegionResource polygonRegionResource = new PolygonRegionResource(polygonRegionService);
        this.restPolygonRegionMockMvc = MockMvcBuilders.standaloneSetup(polygonRegionResource)
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
    public static PolygonRegion createEntity(EntityManager em) {
        PolygonRegion polygonRegion = new PolygonRegion()
            .name(DEFAULT_NAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return polygonRegion;
    }

    @Before
    public void initTest() {
        polygonRegionSearchRepository.deleteAll();
        polygonRegion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolygonRegion() throws Exception {
        int databaseSizeBeforeCreate = polygonRegionRepository.findAll().size();

        // Create the PolygonRegion
        PolygonRegionDTO polygonRegionDTO = polygonRegionMapper.toDto(polygonRegion);
        restPolygonRegionMockMvc.perform(post("/api/polygon-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(polygonRegionDTO)))
            .andExpect(status().isCreated());

        // Validate the PolygonRegion in the database
        List<PolygonRegion> polygonRegionList = polygonRegionRepository.findAll();
        assertThat(polygonRegionList).hasSize(databaseSizeBeforeCreate + 1);
        PolygonRegion testPolygonRegion = polygonRegionList.get(polygonRegionList.size() - 1);
        assertThat(testPolygonRegion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPolygonRegion.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testPolygonRegion.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testPolygonRegion.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the PolygonRegion in Elasticsearch
        PolygonRegion polygonRegionEs = polygonRegionSearchRepository.findOne(testPolygonRegion.getId());
        assertThat(polygonRegionEs).isEqualToComparingFieldByField(testPolygonRegion);
    }

    @Test
    @Transactional
    public void createPolygonRegionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = polygonRegionRepository.findAll().size();

        // Create the PolygonRegion with an existing ID
        polygonRegion.setId(1L);
        PolygonRegionDTO polygonRegionDTO = polygonRegionMapper.toDto(polygonRegion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolygonRegionMockMvc.perform(post("/api/polygon-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(polygonRegionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PolygonRegion> polygonRegionList = polygonRegionRepository.findAll();
        assertThat(polygonRegionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPolygonRegions() throws Exception {
        // Initialize the database
        polygonRegionRepository.saveAndFlush(polygonRegion);

        // Get all the polygonRegionList
        restPolygonRegionMockMvc.perform(get("/api/polygon-regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(polygonRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getPolygonRegion() throws Exception {
        // Initialize the database
        polygonRegionRepository.saveAndFlush(polygonRegion);

        // Get the polygonRegion
        restPolygonRegionMockMvc.perform(get("/api/polygon-regions/{id}", polygonRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(polygonRegion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingPolygonRegion() throws Exception {
        // Get the polygonRegion
        restPolygonRegionMockMvc.perform(get("/api/polygon-regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolygonRegion() throws Exception {
        // Initialize the database
        polygonRegionRepository.saveAndFlush(polygonRegion);
        polygonRegionSearchRepository.save(polygonRegion);
        int databaseSizeBeforeUpdate = polygonRegionRepository.findAll().size();

        // Update the polygonRegion
        PolygonRegion updatedPolygonRegion = polygonRegionRepository.findOne(polygonRegion.getId());
        updatedPolygonRegion
            .name(UPDATED_NAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        PolygonRegionDTO polygonRegionDTO = polygonRegionMapper.toDto(updatedPolygonRegion);

        restPolygonRegionMockMvc.perform(put("/api/polygon-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(polygonRegionDTO)))
            .andExpect(status().isOk());

        // Validate the PolygonRegion in the database
        List<PolygonRegion> polygonRegionList = polygonRegionRepository.findAll();
        assertThat(polygonRegionList).hasSize(databaseSizeBeforeUpdate);
        PolygonRegion testPolygonRegion = polygonRegionList.get(polygonRegionList.size() - 1);
        assertThat(testPolygonRegion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPolygonRegion.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testPolygonRegion.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testPolygonRegion.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the PolygonRegion in Elasticsearch
        PolygonRegion polygonRegionEs = polygonRegionSearchRepository.findOne(testPolygonRegion.getId());
        assertThat(polygonRegionEs).isEqualToComparingFieldByField(testPolygonRegion);
    }

    @Test
    @Transactional
    public void updateNonExistingPolygonRegion() throws Exception {
        int databaseSizeBeforeUpdate = polygonRegionRepository.findAll().size();

        // Create the PolygonRegion
        PolygonRegionDTO polygonRegionDTO = polygonRegionMapper.toDto(polygonRegion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPolygonRegionMockMvc.perform(put("/api/polygon-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(polygonRegionDTO)))
            .andExpect(status().isCreated());

        // Validate the PolygonRegion in the database
        List<PolygonRegion> polygonRegionList = polygonRegionRepository.findAll();
        assertThat(polygonRegionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePolygonRegion() throws Exception {
        // Initialize the database
        polygonRegionRepository.saveAndFlush(polygonRegion);
        polygonRegionSearchRepository.save(polygonRegion);
        int databaseSizeBeforeDelete = polygonRegionRepository.findAll().size();

        // Get the polygonRegion
        restPolygonRegionMockMvc.perform(delete("/api/polygon-regions/{id}", polygonRegion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean polygonRegionExistsInEs = polygonRegionSearchRepository.exists(polygonRegion.getId());
        assertThat(polygonRegionExistsInEs).isFalse();

        // Validate the database is empty
        List<PolygonRegion> polygonRegionList = polygonRegionRepository.findAll();
        assertThat(polygonRegionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPolygonRegion() throws Exception {
        // Initialize the database
        polygonRegionRepository.saveAndFlush(polygonRegion);
        polygonRegionSearchRepository.save(polygonRegion);

        // Search the polygonRegion
        restPolygonRegionMockMvc.perform(get("/api/_search/polygon-regions?query=id:" + polygonRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(polygonRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolygonRegion.class);
        PolygonRegion polygonRegion1 = new PolygonRegion();
        polygonRegion1.setId(1L);
        PolygonRegion polygonRegion2 = new PolygonRegion();
        polygonRegion2.setId(polygonRegion1.getId());
        assertThat(polygonRegion1).isEqualTo(polygonRegion2);
        polygonRegion2.setId(2L);
        assertThat(polygonRegion1).isNotEqualTo(polygonRegion2);
        polygonRegion1.setId(null);
        assertThat(polygonRegion1).isNotEqualTo(polygonRegion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolygonRegionDTO.class);
        PolygonRegionDTO polygonRegionDTO1 = new PolygonRegionDTO();
        polygonRegionDTO1.setId(1L);
        PolygonRegionDTO polygonRegionDTO2 = new PolygonRegionDTO();
        assertThat(polygonRegionDTO1).isNotEqualTo(polygonRegionDTO2);
        polygonRegionDTO2.setId(polygonRegionDTO1.getId());
        assertThat(polygonRegionDTO1).isEqualTo(polygonRegionDTO2);
        polygonRegionDTO2.setId(2L);
        assertThat(polygonRegionDTO1).isNotEqualTo(polygonRegionDTO2);
        polygonRegionDTO1.setId(null);
        assertThat(polygonRegionDTO1).isNotEqualTo(polygonRegionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(polygonRegionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(polygonRegionMapper.fromId(null)).isNull();
    }
}
