package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.RegularRegion;
import com.gorilla.myapp.repository.RegularRegionRepository;
import com.gorilla.myapp.service.RegularRegionService;
import com.gorilla.myapp.repository.search.RegularRegionSearchRepository;
import com.gorilla.myapp.service.dto.RegularRegionDTO;
import com.gorilla.myapp.service.mapper.RegularRegionMapper;
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
 * Test class for the RegularRegionResource REST controller.
 *
 * @see RegularRegionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class RegularRegionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIDES = 1;
    private static final Integer UPDATED_SIDES = 2;

    private static final Double DEFAULT_CENTER_LONGITUDE = 1D;
    private static final Double UPDATED_CENTER_LONGITUDE = 2D;

    private static final Double DEFAULT_CENTER_LATITUDE = 1D;
    private static final Double UPDATED_CENTER_LATITUDE = 2D;

    private static final Double DEFAULT_ALTITUDE = 1D;
    private static final Double UPDATED_ALTITUDE = 2D;

    private static final Double DEFAULT_CORNER_LONGITUDE = 1D;
    private static final Double UPDATED_CORNER_LONGITUDE = 2D;

    private static final Double DEFAULT_CORNER_LATITUDE = 1D;
    private static final Double UPDATED_CORNER_LATITUDE = 2D;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RegularRegionRepository regularRegionRepository;

    @Autowired
    private RegularRegionMapper regularRegionMapper;

    @Autowired
    private RegularRegionService regularRegionService;

    @Autowired
    private RegularRegionSearchRepository regularRegionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegularRegionMockMvc;

    private RegularRegion regularRegion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegularRegionResource regularRegionResource = new RegularRegionResource(regularRegionService);
        this.restRegularRegionMockMvc = MockMvcBuilders.standaloneSetup(regularRegionResource)
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
    public static RegularRegion createEntity(EntityManager em) {
        RegularRegion regularRegion = new RegularRegion()
            .name(DEFAULT_NAME)
            .sides(DEFAULT_SIDES)
            .centerLongitude(DEFAULT_CENTER_LONGITUDE)
            .centerLatitude(DEFAULT_CENTER_LATITUDE)
            .altitude(DEFAULT_ALTITUDE)
            .cornerLongitude(DEFAULT_CORNER_LONGITUDE)
            .cornerLatitude(DEFAULT_CORNER_LATITUDE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return regularRegion;
    }

    @Before
    public void initTest() {
        regularRegionSearchRepository.deleteAll();
        regularRegion = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegularRegion() throws Exception {
        int databaseSizeBeforeCreate = regularRegionRepository.findAll().size();

        // Create the RegularRegion
        RegularRegionDTO regularRegionDTO = regularRegionMapper.toDto(regularRegion);
        restRegularRegionMockMvc.perform(post("/api/regular-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regularRegionDTO)))
            .andExpect(status().isCreated());

        // Validate the RegularRegion in the database
        List<RegularRegion> regularRegionList = regularRegionRepository.findAll();
        assertThat(regularRegionList).hasSize(databaseSizeBeforeCreate + 1);
        RegularRegion testRegularRegion = regularRegionList.get(regularRegionList.size() - 1);
        assertThat(testRegularRegion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRegularRegion.getSides()).isEqualTo(DEFAULT_SIDES);
        assertThat(testRegularRegion.getCenterLongitude()).isEqualTo(DEFAULT_CENTER_LONGITUDE);
        assertThat(testRegularRegion.getCenterLatitude()).isEqualTo(DEFAULT_CENTER_LATITUDE);
        assertThat(testRegularRegion.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testRegularRegion.getCornerLongitude()).isEqualTo(DEFAULT_CORNER_LONGITUDE);
        assertThat(testRegularRegion.getCornerLatitude()).isEqualTo(DEFAULT_CORNER_LATITUDE);
        assertThat(testRegularRegion.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testRegularRegion.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testRegularRegion.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the RegularRegion in Elasticsearch
        RegularRegion regularRegionEs = regularRegionSearchRepository.findOne(testRegularRegion.getId());
        assertThat(regularRegionEs).isEqualToComparingFieldByField(testRegularRegion);
    }

    @Test
    @Transactional
    public void createRegularRegionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regularRegionRepository.findAll().size();

        // Create the RegularRegion with an existing ID
        regularRegion.setId(1L);
        RegularRegionDTO regularRegionDTO = regularRegionMapper.toDto(regularRegion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegularRegionMockMvc.perform(post("/api/regular-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regularRegionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RegularRegion> regularRegionList = regularRegionRepository.findAll();
        assertThat(regularRegionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRegularRegions() throws Exception {
        // Initialize the database
        regularRegionRepository.saveAndFlush(regularRegion);

        // Get all the regularRegionList
        restRegularRegionMockMvc.perform(get("/api/regular-regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regularRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].sides").value(hasItem(DEFAULT_SIDES)))
            .andExpect(jsonPath("$.[*].centerLongitude").value(hasItem(DEFAULT_CENTER_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].centerLatitude").value(hasItem(DEFAULT_CENTER_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].cornerLongitude").value(hasItem(DEFAULT_CORNER_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].cornerLatitude").value(hasItem(DEFAULT_CORNER_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getRegularRegion() throws Exception {
        // Initialize the database
        regularRegionRepository.saveAndFlush(regularRegion);

        // Get the regularRegion
        restRegularRegionMockMvc.perform(get("/api/regular-regions/{id}", regularRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regularRegion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.sides").value(DEFAULT_SIDES))
            .andExpect(jsonPath("$.centerLongitude").value(DEFAULT_CENTER_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.centerLatitude").value(DEFAULT_CENTER_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE.doubleValue()))
            .andExpect(jsonPath("$.cornerLongitude").value(DEFAULT_CORNER_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.cornerLatitude").value(DEFAULT_CORNER_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingRegularRegion() throws Exception {
        // Get the regularRegion
        restRegularRegionMockMvc.perform(get("/api/regular-regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegularRegion() throws Exception {
        // Initialize the database
        regularRegionRepository.saveAndFlush(regularRegion);
        regularRegionSearchRepository.save(regularRegion);
        int databaseSizeBeforeUpdate = regularRegionRepository.findAll().size();

        // Update the regularRegion
        RegularRegion updatedRegularRegion = regularRegionRepository.findOne(regularRegion.getId());
        updatedRegularRegion
            .name(UPDATED_NAME)
            .sides(UPDATED_SIDES)
            .centerLongitude(UPDATED_CENTER_LONGITUDE)
            .centerLatitude(UPDATED_CENTER_LATITUDE)
            .altitude(UPDATED_ALTITUDE)
            .cornerLongitude(UPDATED_CORNER_LONGITUDE)
            .cornerLatitude(UPDATED_CORNER_LATITUDE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        RegularRegionDTO regularRegionDTO = regularRegionMapper.toDto(updatedRegularRegion);

        restRegularRegionMockMvc.perform(put("/api/regular-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regularRegionDTO)))
            .andExpect(status().isOk());

        // Validate the RegularRegion in the database
        List<RegularRegion> regularRegionList = regularRegionRepository.findAll();
        assertThat(regularRegionList).hasSize(databaseSizeBeforeUpdate);
        RegularRegion testRegularRegion = regularRegionList.get(regularRegionList.size() - 1);
        assertThat(testRegularRegion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRegularRegion.getSides()).isEqualTo(UPDATED_SIDES);
        assertThat(testRegularRegion.getCenterLongitude()).isEqualTo(UPDATED_CENTER_LONGITUDE);
        assertThat(testRegularRegion.getCenterLatitude()).isEqualTo(UPDATED_CENTER_LATITUDE);
        assertThat(testRegularRegion.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testRegularRegion.getCornerLongitude()).isEqualTo(UPDATED_CORNER_LONGITUDE);
        assertThat(testRegularRegion.getCornerLatitude()).isEqualTo(UPDATED_CORNER_LATITUDE);
        assertThat(testRegularRegion.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testRegularRegion.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testRegularRegion.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the RegularRegion in Elasticsearch
        RegularRegion regularRegionEs = regularRegionSearchRepository.findOne(testRegularRegion.getId());
        assertThat(regularRegionEs).isEqualToComparingFieldByField(testRegularRegion);
    }

    @Test
    @Transactional
    public void updateNonExistingRegularRegion() throws Exception {
        int databaseSizeBeforeUpdate = regularRegionRepository.findAll().size();

        // Create the RegularRegion
        RegularRegionDTO regularRegionDTO = regularRegionMapper.toDto(regularRegion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegularRegionMockMvc.perform(put("/api/regular-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regularRegionDTO)))
            .andExpect(status().isCreated());

        // Validate the RegularRegion in the database
        List<RegularRegion> regularRegionList = regularRegionRepository.findAll();
        assertThat(regularRegionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRegularRegion() throws Exception {
        // Initialize the database
        regularRegionRepository.saveAndFlush(regularRegion);
        regularRegionSearchRepository.save(regularRegion);
        int databaseSizeBeforeDelete = regularRegionRepository.findAll().size();

        // Get the regularRegion
        restRegularRegionMockMvc.perform(delete("/api/regular-regions/{id}", regularRegion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean regularRegionExistsInEs = regularRegionSearchRepository.exists(regularRegion.getId());
        assertThat(regularRegionExistsInEs).isFalse();

        // Validate the database is empty
        List<RegularRegion> regularRegionList = regularRegionRepository.findAll();
        assertThat(regularRegionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRegularRegion() throws Exception {
        // Initialize the database
        regularRegionRepository.saveAndFlush(regularRegion);
        regularRegionSearchRepository.save(regularRegion);

        // Search the regularRegion
        restRegularRegionMockMvc.perform(get("/api/_search/regular-regions?query=id:" + regularRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regularRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].sides").value(hasItem(DEFAULT_SIDES)))
            .andExpect(jsonPath("$.[*].centerLongitude").value(hasItem(DEFAULT_CENTER_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].centerLatitude").value(hasItem(DEFAULT_CENTER_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].cornerLongitude").value(hasItem(DEFAULT_CORNER_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].cornerLatitude").value(hasItem(DEFAULT_CORNER_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegularRegion.class);
        RegularRegion regularRegion1 = new RegularRegion();
        regularRegion1.setId(1L);
        RegularRegion regularRegion2 = new RegularRegion();
        regularRegion2.setId(regularRegion1.getId());
        assertThat(regularRegion1).isEqualTo(regularRegion2);
        regularRegion2.setId(2L);
        assertThat(regularRegion1).isNotEqualTo(regularRegion2);
        regularRegion1.setId(null);
        assertThat(regularRegion1).isNotEqualTo(regularRegion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegularRegionDTO.class);
        RegularRegionDTO regularRegionDTO1 = new RegularRegionDTO();
        regularRegionDTO1.setId(1L);
        RegularRegionDTO regularRegionDTO2 = new RegularRegionDTO();
        assertThat(regularRegionDTO1).isNotEqualTo(regularRegionDTO2);
        regularRegionDTO2.setId(regularRegionDTO1.getId());
        assertThat(regularRegionDTO1).isEqualTo(regularRegionDTO2);
        regularRegionDTO2.setId(2L);
        assertThat(regularRegionDTO1).isNotEqualTo(regularRegionDTO2);
        regularRegionDTO1.setId(null);
        assertThat(regularRegionDTO1).isNotEqualTo(regularRegionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(regularRegionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(regularRegionMapper.fromId(null)).isNull();
    }
}
