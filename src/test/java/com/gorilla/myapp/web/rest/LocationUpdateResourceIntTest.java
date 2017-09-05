package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.LocationUpdate;
import com.gorilla.myapp.repository.LocationUpdateRepository;
import com.gorilla.myapp.service.LocationUpdateService;
import com.gorilla.myapp.repository.search.LocationUpdateSearchRepository;
import com.gorilla.myapp.service.dto.LocationUpdateDTO;
import com.gorilla.myapp.service.mapper.LocationUpdateMapper;
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
 * Test class for the LocationUpdateResource REST controller.
 *
 * @see LocationUpdateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class LocationUpdateResourceIntTest {

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

    private static final Long DEFAULT_LAYER_ID = 1L;
    private static final Long UPDATED_LAYER_ID = 2L;

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LocationUpdateRepository locationUpdateRepository;

    @Autowired
    private LocationUpdateMapper locationUpdateMapper;

    @Autowired
    private LocationUpdateService locationUpdateService;

    @Autowired
    private LocationUpdateSearchRepository locationUpdateSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocationUpdateMockMvc;

    private LocationUpdate locationUpdate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocationUpdateResource locationUpdateResource = new LocationUpdateResource(locationUpdateService);
        this.restLocationUpdateMockMvc = MockMvcBuilders.standaloneSetup(locationUpdateResource)
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
    public static LocationUpdate createEntity(EntityManager em) {
        LocationUpdate locationUpdate = new LocationUpdate()
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .altitude(DEFAULT_ALTITUDE)
            .bearing(DEFAULT_BEARING)
            .speed(DEFAULT_SPEED)
            .layerId(DEFAULT_LAYER_ID)
            .time(DEFAULT_TIME);
        return locationUpdate;
    }

    @Before
    public void initTest() {
        locationUpdateSearchRepository.deleteAll();
        locationUpdate = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocationUpdate() throws Exception {
        int databaseSizeBeforeCreate = locationUpdateRepository.findAll().size();

        // Create the LocationUpdate
        LocationUpdateDTO locationUpdateDTO = locationUpdateMapper.toDto(locationUpdate);
        restLocationUpdateMockMvc.perform(post("/api/location-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationUpdateDTO)))
            .andExpect(status().isCreated());

        // Validate the LocationUpdate in the database
        List<LocationUpdate> locationUpdateList = locationUpdateRepository.findAll();
        assertThat(locationUpdateList).hasSize(databaseSizeBeforeCreate + 1);
        LocationUpdate testLocationUpdate = locationUpdateList.get(locationUpdateList.size() - 1);
        assertThat(testLocationUpdate.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testLocationUpdate.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testLocationUpdate.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testLocationUpdate.getBearing()).isEqualTo(DEFAULT_BEARING);
        assertThat(testLocationUpdate.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testLocationUpdate.getLayerId()).isEqualTo(DEFAULT_LAYER_ID);
        assertThat(testLocationUpdate.getTime()).isEqualTo(DEFAULT_TIME);

        // Validate the LocationUpdate in Elasticsearch
        LocationUpdate locationUpdateEs = locationUpdateSearchRepository.findOne(testLocationUpdate.getId());
        assertThat(locationUpdateEs).isEqualToComparingFieldByField(testLocationUpdate);
    }

    @Test
    @Transactional
    public void createLocationUpdateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationUpdateRepository.findAll().size();

        // Create the LocationUpdate with an existing ID
        locationUpdate.setId(1L);
        LocationUpdateDTO locationUpdateDTO = locationUpdateMapper.toDto(locationUpdate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationUpdateMockMvc.perform(post("/api/location-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationUpdateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LocationUpdate> locationUpdateList = locationUpdateRepository.findAll();
        assertThat(locationUpdateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocationUpdates() throws Exception {
        // Initialize the database
        locationUpdateRepository.saveAndFlush(locationUpdate);

        // Get all the locationUpdateList
        restLocationUpdateMockMvc.perform(get("/api/location-updates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationUpdate.getId().intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].bearing").value(hasItem(DEFAULT_BEARING.doubleValue())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].layerId").value(hasItem(DEFAULT_LAYER_ID.intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }

    @Test
    @Transactional
    public void getLocationUpdate() throws Exception {
        // Initialize the database
        locationUpdateRepository.saveAndFlush(locationUpdate);

        // Get the locationUpdate
        restLocationUpdateMockMvc.perform(get("/api/location-updates/{id}", locationUpdate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locationUpdate.getId().intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE.doubleValue()))
            .andExpect(jsonPath("$.bearing").value(DEFAULT_BEARING.doubleValue()))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED.doubleValue()))
            .andExpect(jsonPath("$.layerId").value(DEFAULT_LAYER_ID.intValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingLocationUpdate() throws Exception {
        // Get the locationUpdate
        restLocationUpdateMockMvc.perform(get("/api/location-updates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocationUpdate() throws Exception {
        // Initialize the database
        locationUpdateRepository.saveAndFlush(locationUpdate);
        locationUpdateSearchRepository.save(locationUpdate);
        int databaseSizeBeforeUpdate = locationUpdateRepository.findAll().size();

        // Update the locationUpdate
        LocationUpdate updatedLocationUpdate = locationUpdateRepository.findOne(locationUpdate.getId());
        updatedLocationUpdate
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .altitude(UPDATED_ALTITUDE)
            .bearing(UPDATED_BEARING)
            .speed(UPDATED_SPEED)
            .layerId(UPDATED_LAYER_ID)
            .time(UPDATED_TIME);
        LocationUpdateDTO locationUpdateDTO = locationUpdateMapper.toDto(updatedLocationUpdate);

        restLocationUpdateMockMvc.perform(put("/api/location-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationUpdateDTO)))
            .andExpect(status().isOk());

        // Validate the LocationUpdate in the database
        List<LocationUpdate> locationUpdateList = locationUpdateRepository.findAll();
        assertThat(locationUpdateList).hasSize(databaseSizeBeforeUpdate);
        LocationUpdate testLocationUpdate = locationUpdateList.get(locationUpdateList.size() - 1);
        assertThat(testLocationUpdate.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testLocationUpdate.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testLocationUpdate.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testLocationUpdate.getBearing()).isEqualTo(UPDATED_BEARING);
        assertThat(testLocationUpdate.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testLocationUpdate.getLayerId()).isEqualTo(UPDATED_LAYER_ID);
        assertThat(testLocationUpdate.getTime()).isEqualTo(UPDATED_TIME);

        // Validate the LocationUpdate in Elasticsearch
        LocationUpdate locationUpdateEs = locationUpdateSearchRepository.findOne(testLocationUpdate.getId());
        assertThat(locationUpdateEs).isEqualToComparingFieldByField(testLocationUpdate);
    }

    @Test
    @Transactional
    public void updateNonExistingLocationUpdate() throws Exception {
        int databaseSizeBeforeUpdate = locationUpdateRepository.findAll().size();

        // Create the LocationUpdate
        LocationUpdateDTO locationUpdateDTO = locationUpdateMapper.toDto(locationUpdate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocationUpdateMockMvc.perform(put("/api/location-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationUpdateDTO)))
            .andExpect(status().isCreated());

        // Validate the LocationUpdate in the database
        List<LocationUpdate> locationUpdateList = locationUpdateRepository.findAll();
        assertThat(locationUpdateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocationUpdate() throws Exception {
        // Initialize the database
        locationUpdateRepository.saveAndFlush(locationUpdate);
        locationUpdateSearchRepository.save(locationUpdate);
        int databaseSizeBeforeDelete = locationUpdateRepository.findAll().size();

        // Get the locationUpdate
        restLocationUpdateMockMvc.perform(delete("/api/location-updates/{id}", locationUpdate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean locationUpdateExistsInEs = locationUpdateSearchRepository.exists(locationUpdate.getId());
        assertThat(locationUpdateExistsInEs).isFalse();

        // Validate the database is empty
        List<LocationUpdate> locationUpdateList = locationUpdateRepository.findAll();
        assertThat(locationUpdateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLocationUpdate() throws Exception {
        // Initialize the database
        locationUpdateRepository.saveAndFlush(locationUpdate);
        locationUpdateSearchRepository.save(locationUpdate);

        // Search the locationUpdate
        restLocationUpdateMockMvc.perform(get("/api/_search/location-updates?query=id:" + locationUpdate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationUpdate.getId().intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].bearing").value(hasItem(DEFAULT_BEARING.doubleValue())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].layerId").value(hasItem(DEFAULT_LAYER_ID.intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationUpdate.class);
        LocationUpdate locationUpdate1 = new LocationUpdate();
        locationUpdate1.setId(1L);
        LocationUpdate locationUpdate2 = new LocationUpdate();
        locationUpdate2.setId(locationUpdate1.getId());
        assertThat(locationUpdate1).isEqualTo(locationUpdate2);
        locationUpdate2.setId(2L);
        assertThat(locationUpdate1).isNotEqualTo(locationUpdate2);
        locationUpdate1.setId(null);
        assertThat(locationUpdate1).isNotEqualTo(locationUpdate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationUpdateDTO.class);
        LocationUpdateDTO locationUpdateDTO1 = new LocationUpdateDTO();
        locationUpdateDTO1.setId(1L);
        LocationUpdateDTO locationUpdateDTO2 = new LocationUpdateDTO();
        assertThat(locationUpdateDTO1).isNotEqualTo(locationUpdateDTO2);
        locationUpdateDTO2.setId(locationUpdateDTO1.getId());
        assertThat(locationUpdateDTO1).isEqualTo(locationUpdateDTO2);
        locationUpdateDTO2.setId(2L);
        assertThat(locationUpdateDTO1).isNotEqualTo(locationUpdateDTO2);
        locationUpdateDTO1.setId(null);
        assertThat(locationUpdateDTO1).isNotEqualTo(locationUpdateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(locationUpdateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(locationUpdateMapper.fromId(null)).isNull();
    }
}
