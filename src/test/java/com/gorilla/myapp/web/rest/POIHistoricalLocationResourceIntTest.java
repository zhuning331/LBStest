package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.POIHistoricalLocation;
import com.gorilla.myapp.repository.POIHistoricalLocationRepository;
import com.gorilla.myapp.service.POIHistoricalLocationService;
import com.gorilla.myapp.repository.search.POIHistoricalLocationSearchRepository;
import com.gorilla.myapp.service.dto.POIHistoricalLocationDTO;
import com.gorilla.myapp.service.mapper.POIHistoricalLocationMapper;
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
 * Test class for the POIHistoricalLocationResource REST controller.
 *
 * @see POIHistoricalLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class POIHistoricalLocationResourceIntTest {

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_ALTITUDE = 1D;
    private static final Double UPDATED_ALTITUDE = 2D;

    private static final Double DEFAULT_BEARING = 1D;
    private static final Double UPDATED_BEARING = 2D;

    private static final Double DEFAULT_SPEED = 1D;
    private static final Double UPDATED_SPEED = 2D;

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private POIHistoricalLocationRepository pOIHistoricalLocationRepository;

    @Autowired
    private POIHistoricalLocationMapper pOIHistoricalLocationMapper;

    @Autowired
    private POIHistoricalLocationService pOIHistoricalLocationService;

    @Autowired
    private POIHistoricalLocationSearchRepository pOIHistoricalLocationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPOIHistoricalLocationMockMvc;

    private POIHistoricalLocation pOIHistoricalLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final POIHistoricalLocationResource pOIHistoricalLocationResource = new POIHistoricalLocationResource(pOIHistoricalLocationService);
        this.restPOIHistoricalLocationMockMvc = MockMvcBuilders.standaloneSetup(pOIHistoricalLocationResource)
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
    public static POIHistoricalLocation createEntity(EntityManager em) {
        POIHistoricalLocation pOIHistoricalLocation = new POIHistoricalLocation()
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .altitude(DEFAULT_ALTITUDE)
            .bearing(DEFAULT_BEARING)
            .speed(DEFAULT_SPEED)
            .time(DEFAULT_TIME);
        return pOIHistoricalLocation;
    }

    @Before
    public void initTest() {
        pOIHistoricalLocationSearchRepository.deleteAll();
        pOIHistoricalLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPOIHistoricalLocation() throws Exception {
        int databaseSizeBeforeCreate = pOIHistoricalLocationRepository.findAll().size();

        // Create the POIHistoricalLocation
        POIHistoricalLocationDTO pOIHistoricalLocationDTO = pOIHistoricalLocationMapper.toDto(pOIHistoricalLocation);
        restPOIHistoricalLocationMockMvc.perform(post("/api/p-oi-historical-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOIHistoricalLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the POIHistoricalLocation in the database
        List<POIHistoricalLocation> pOIHistoricalLocationList = pOIHistoricalLocationRepository.findAll();
        assertThat(pOIHistoricalLocationList).hasSize(databaseSizeBeforeCreate + 1);
        POIHistoricalLocation testPOIHistoricalLocation = pOIHistoricalLocationList.get(pOIHistoricalLocationList.size() - 1);
        assertThat(testPOIHistoricalLocation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testPOIHistoricalLocation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testPOIHistoricalLocation.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testPOIHistoricalLocation.getBearing()).isEqualTo(DEFAULT_BEARING);
        assertThat(testPOIHistoricalLocation.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testPOIHistoricalLocation.getTime()).isEqualTo(DEFAULT_TIME);

        // Validate the POIHistoricalLocation in Elasticsearch
        POIHistoricalLocation pOIHistoricalLocationEs = pOIHistoricalLocationSearchRepository.findOne(testPOIHistoricalLocation.getId());
        assertThat(pOIHistoricalLocationEs).isEqualToComparingFieldByField(testPOIHistoricalLocation);
    }

    @Test
    @Transactional
    public void createPOIHistoricalLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pOIHistoricalLocationRepository.findAll().size();

        // Create the POIHistoricalLocation with an existing ID
        pOIHistoricalLocation.setId(1L);
        POIHistoricalLocationDTO pOIHistoricalLocationDTO = pOIHistoricalLocationMapper.toDto(pOIHistoricalLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPOIHistoricalLocationMockMvc.perform(post("/api/p-oi-historical-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOIHistoricalLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<POIHistoricalLocation> pOIHistoricalLocationList = pOIHistoricalLocationRepository.findAll();
        assertThat(pOIHistoricalLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPOIHistoricalLocations() throws Exception {
        // Initialize the database
        pOIHistoricalLocationRepository.saveAndFlush(pOIHistoricalLocation);

        // Get all the pOIHistoricalLocationList
        restPOIHistoricalLocationMockMvc.perform(get("/api/p-oi-historical-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pOIHistoricalLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].bearing").value(hasItem(DEFAULT_BEARING.doubleValue())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }

    @Test
    @Transactional
    public void getPOIHistoricalLocation() throws Exception {
        // Initialize the database
        pOIHistoricalLocationRepository.saveAndFlush(pOIHistoricalLocation);

        // Get the pOIHistoricalLocation
        restPOIHistoricalLocationMockMvc.perform(get("/api/p-oi-historical-locations/{id}", pOIHistoricalLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pOIHistoricalLocation.getId().intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE.doubleValue()))
            .andExpect(jsonPath("$.bearing").value(DEFAULT_BEARING.doubleValue()))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED.doubleValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingPOIHistoricalLocation() throws Exception {
        // Get the pOIHistoricalLocation
        restPOIHistoricalLocationMockMvc.perform(get("/api/p-oi-historical-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePOIHistoricalLocation() throws Exception {
        // Initialize the database
        pOIHistoricalLocationRepository.saveAndFlush(pOIHistoricalLocation);
        pOIHistoricalLocationSearchRepository.save(pOIHistoricalLocation);
        int databaseSizeBeforeUpdate = pOIHistoricalLocationRepository.findAll().size();

        // Update the pOIHistoricalLocation
        POIHistoricalLocation updatedPOIHistoricalLocation = pOIHistoricalLocationRepository.findOne(pOIHistoricalLocation.getId());
        updatedPOIHistoricalLocation
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .altitude(UPDATED_ALTITUDE)
            .bearing(UPDATED_BEARING)
            .speed(UPDATED_SPEED)
            .time(UPDATED_TIME);
        POIHistoricalLocationDTO pOIHistoricalLocationDTO = pOIHistoricalLocationMapper.toDto(updatedPOIHistoricalLocation);

        restPOIHistoricalLocationMockMvc.perform(put("/api/p-oi-historical-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOIHistoricalLocationDTO)))
            .andExpect(status().isOk());

        // Validate the POIHistoricalLocation in the database
        List<POIHistoricalLocation> pOIHistoricalLocationList = pOIHistoricalLocationRepository.findAll();
        assertThat(pOIHistoricalLocationList).hasSize(databaseSizeBeforeUpdate);
        POIHistoricalLocation testPOIHistoricalLocation = pOIHistoricalLocationList.get(pOIHistoricalLocationList.size() - 1);
        assertThat(testPOIHistoricalLocation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testPOIHistoricalLocation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testPOIHistoricalLocation.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testPOIHistoricalLocation.getBearing()).isEqualTo(UPDATED_BEARING);
        assertThat(testPOIHistoricalLocation.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testPOIHistoricalLocation.getTime()).isEqualTo(UPDATED_TIME);

        // Validate the POIHistoricalLocation in Elasticsearch
        POIHistoricalLocation pOIHistoricalLocationEs = pOIHistoricalLocationSearchRepository.findOne(testPOIHistoricalLocation.getId());
        assertThat(pOIHistoricalLocationEs).isEqualToComparingFieldByField(testPOIHistoricalLocation);
    }

    @Test
    @Transactional
    public void updateNonExistingPOIHistoricalLocation() throws Exception {
        int databaseSizeBeforeUpdate = pOIHistoricalLocationRepository.findAll().size();

        // Create the POIHistoricalLocation
        POIHistoricalLocationDTO pOIHistoricalLocationDTO = pOIHistoricalLocationMapper.toDto(pOIHistoricalLocation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPOIHistoricalLocationMockMvc.perform(put("/api/p-oi-historical-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOIHistoricalLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the POIHistoricalLocation in the database
        List<POIHistoricalLocation> pOIHistoricalLocationList = pOIHistoricalLocationRepository.findAll();
        assertThat(pOIHistoricalLocationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePOIHistoricalLocation() throws Exception {
        // Initialize the database
        pOIHistoricalLocationRepository.saveAndFlush(pOIHistoricalLocation);
        pOIHistoricalLocationSearchRepository.save(pOIHistoricalLocation);
        int databaseSizeBeforeDelete = pOIHistoricalLocationRepository.findAll().size();

        // Get the pOIHistoricalLocation
        restPOIHistoricalLocationMockMvc.perform(delete("/api/p-oi-historical-locations/{id}", pOIHistoricalLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pOIHistoricalLocationExistsInEs = pOIHistoricalLocationSearchRepository.exists(pOIHistoricalLocation.getId());
        assertThat(pOIHistoricalLocationExistsInEs).isFalse();

        // Validate the database is empty
        List<POIHistoricalLocation> pOIHistoricalLocationList = pOIHistoricalLocationRepository.findAll();
        assertThat(pOIHistoricalLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPOIHistoricalLocation() throws Exception {
        // Initialize the database
        pOIHistoricalLocationRepository.saveAndFlush(pOIHistoricalLocation);
        pOIHistoricalLocationSearchRepository.save(pOIHistoricalLocation);

        // Search the pOIHistoricalLocation
        restPOIHistoricalLocationMockMvc.perform(get("/api/_search/p-oi-historical-locations?query=id:" + pOIHistoricalLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pOIHistoricalLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].bearing").value(hasItem(DEFAULT_BEARING.doubleValue())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(POIHistoricalLocation.class);
        POIHistoricalLocation pOIHistoricalLocation1 = new POIHistoricalLocation();
        pOIHistoricalLocation1.setId(1L);
        POIHistoricalLocation pOIHistoricalLocation2 = new POIHistoricalLocation();
        pOIHistoricalLocation2.setId(pOIHistoricalLocation1.getId());
        assertThat(pOIHistoricalLocation1).isEqualTo(pOIHistoricalLocation2);
        pOIHistoricalLocation2.setId(2L);
        assertThat(pOIHistoricalLocation1).isNotEqualTo(pOIHistoricalLocation2);
        pOIHistoricalLocation1.setId(null);
        assertThat(pOIHistoricalLocation1).isNotEqualTo(pOIHistoricalLocation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(POIHistoricalLocationDTO.class);
        POIHistoricalLocationDTO pOIHistoricalLocationDTO1 = new POIHistoricalLocationDTO();
        pOIHistoricalLocationDTO1.setId(1L);
        POIHistoricalLocationDTO pOIHistoricalLocationDTO2 = new POIHistoricalLocationDTO();
        assertThat(pOIHistoricalLocationDTO1).isNotEqualTo(pOIHistoricalLocationDTO2);
        pOIHistoricalLocationDTO2.setId(pOIHistoricalLocationDTO1.getId());
        assertThat(pOIHistoricalLocationDTO1).isEqualTo(pOIHistoricalLocationDTO2);
        pOIHistoricalLocationDTO2.setId(2L);
        assertThat(pOIHistoricalLocationDTO1).isNotEqualTo(pOIHistoricalLocationDTO2);
        pOIHistoricalLocationDTO1.setId(null);
        assertThat(pOIHistoricalLocationDTO1).isNotEqualTo(pOIHistoricalLocationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pOIHistoricalLocationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pOIHistoricalLocationMapper.fromId(null)).isNull();
    }
}
