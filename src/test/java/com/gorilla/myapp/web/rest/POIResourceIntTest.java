package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.POI;
import com.gorilla.myapp.repository.POIRepository;
import com.gorilla.myapp.service.POIService;
import com.gorilla.myapp.repository.search.POISearchRepository;
import com.gorilla.myapp.service.dto.POIDTO;
import com.gorilla.myapp.service.mapper.POIMapper;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the POIResource REST controller.
 *
 * @see POIResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class POIResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_ALTITUDE = 1D;
    private static final Double UPDATED_ALTITUDE = 2D;

    private static final Double DEFAULT_BEARING = 1D;
    private static final Double UPDATED_BEARING = 2D;

    private static final byte[] DEFAULT_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_SPEED = 1D;
    private static final Double UPDATED_SPEED = 2D;

    private static final String DEFAULT_PROFILE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private POIRepository pOIRepository;

    @Autowired
    private POIMapper pOIMapper;

    @Autowired
    private POIService pOIService;

    @Autowired
    private POISearchRepository pOISearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPOIMockMvc;

    private POI pOI;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final POIResource pOIResource = new POIResource(pOIService);
        this.restPOIMockMvc = MockMvcBuilders.standaloneSetup(pOIResource)
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
    public static POI createEntity(EntityManager em) {
        POI pOI = new POI()
            .name(DEFAULT_NAME)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .altitude(DEFAULT_ALTITUDE)
            .bearing(DEFAULT_BEARING)
            .icon(DEFAULT_ICON)
            .iconContentType(DEFAULT_ICON_CONTENT_TYPE)
            .speed(DEFAULT_SPEED)
            .profile(DEFAULT_PROFILE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return pOI;
    }

    @Before
    public void initTest() {
        pOISearchRepository.deleteAll();
        pOI = createEntity(em);
    }

    @Test
    @Transactional
    public void createPOI() throws Exception {
        int databaseSizeBeforeCreate = pOIRepository.findAll().size();

        // Create the POI
        POIDTO pOIDTO = pOIMapper.toDto(pOI);
        restPOIMockMvc.perform(post("/api/p-ois")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOIDTO)))
            .andExpect(status().isCreated());

        // Validate the POI in the database
        List<POI> pOIList = pOIRepository.findAll();
        assertThat(pOIList).hasSize(databaseSizeBeforeCreate + 1);
        POI testPOI = pOIList.get(pOIList.size() - 1);
        assertThat(testPOI.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPOI.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testPOI.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testPOI.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testPOI.getBearing()).isEqualTo(DEFAULT_BEARING);
        assertThat(testPOI.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testPOI.getIconContentType()).isEqualTo(DEFAULT_ICON_CONTENT_TYPE);
        assertThat(testPOI.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testPOI.getProfile()).isEqualTo(DEFAULT_PROFILE);
        assertThat(testPOI.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testPOI.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testPOI.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the POI in Elasticsearch
        POI pOIEs = pOISearchRepository.findOne(testPOI.getId());
        assertThat(pOIEs).isEqualToComparingFieldByField(testPOI);
    }

    @Test
    @Transactional
    public void createPOIWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pOIRepository.findAll().size();

        // Create the POI with an existing ID
        pOI.setId(1L);
        POIDTO pOIDTO = pOIMapper.toDto(pOI);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPOIMockMvc.perform(post("/api/p-ois")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOIDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<POI> pOIList = pOIRepository.findAll();
        assertThat(pOIList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPOIS() throws Exception {
        // Initialize the database
        pOIRepository.saveAndFlush(pOI);

        // Get all the pOIList
        restPOIMockMvc.perform(get("/api/p-ois?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pOI.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].bearing").value(hasItem(DEFAULT_BEARING.doubleValue())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].profile").value(hasItem(DEFAULT_PROFILE.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getPOI() throws Exception {
        // Initialize the database
        pOIRepository.saveAndFlush(pOI);

        // Get the pOI
        restPOIMockMvc.perform(get("/api/p-ois/{id}", pOI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pOI.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE.doubleValue()))
            .andExpect(jsonPath("$.bearing").value(DEFAULT_BEARING.doubleValue()))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.icon").value(Base64Utils.encodeToString(DEFAULT_ICON)))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED.doubleValue()))
            .andExpect(jsonPath("$.profile").value(DEFAULT_PROFILE.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingPOI() throws Exception {
        // Get the pOI
        restPOIMockMvc.perform(get("/api/p-ois/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePOI() throws Exception {
        // Initialize the database
        pOIRepository.saveAndFlush(pOI);
        pOISearchRepository.save(pOI);
        int databaseSizeBeforeUpdate = pOIRepository.findAll().size();

        // Update the pOI
        POI updatedPOI = pOIRepository.findOne(pOI.getId());
        updatedPOI
            .name(UPDATED_NAME)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .altitude(UPDATED_ALTITUDE)
            .bearing(UPDATED_BEARING)
            .icon(UPDATED_ICON)
            .iconContentType(UPDATED_ICON_CONTENT_TYPE)
            .speed(UPDATED_SPEED)
            .profile(UPDATED_PROFILE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        POIDTO pOIDTO = pOIMapper.toDto(updatedPOI);

        restPOIMockMvc.perform(put("/api/p-ois")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOIDTO)))
            .andExpect(status().isOk());

        // Validate the POI in the database
        List<POI> pOIList = pOIRepository.findAll();
        assertThat(pOIList).hasSize(databaseSizeBeforeUpdate);
        POI testPOI = pOIList.get(pOIList.size() - 1);
        assertThat(testPOI.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPOI.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testPOI.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testPOI.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testPOI.getBearing()).isEqualTo(UPDATED_BEARING);
        assertThat(testPOI.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testPOI.getIconContentType()).isEqualTo(UPDATED_ICON_CONTENT_TYPE);
        assertThat(testPOI.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testPOI.getProfile()).isEqualTo(UPDATED_PROFILE);
        assertThat(testPOI.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testPOI.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testPOI.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the POI in Elasticsearch
        POI pOIEs = pOISearchRepository.findOne(testPOI.getId());
        assertThat(pOIEs).isEqualToComparingFieldByField(testPOI);
    }

    @Test
    @Transactional
    public void updateNonExistingPOI() throws Exception {
        int databaseSizeBeforeUpdate = pOIRepository.findAll().size();

        // Create the POI
        POIDTO pOIDTO = pOIMapper.toDto(pOI);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPOIMockMvc.perform(put("/api/p-ois")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOIDTO)))
            .andExpect(status().isCreated());

        // Validate the POI in the database
        List<POI> pOIList = pOIRepository.findAll();
        assertThat(pOIList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePOI() throws Exception {
        // Initialize the database
        pOIRepository.saveAndFlush(pOI);
        pOISearchRepository.save(pOI);
        int databaseSizeBeforeDelete = pOIRepository.findAll().size();

        // Get the pOI
        restPOIMockMvc.perform(delete("/api/p-ois/{id}", pOI.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pOIExistsInEs = pOISearchRepository.exists(pOI.getId());
        assertThat(pOIExistsInEs).isFalse();

        // Validate the database is empty
        List<POI> pOIList = pOIRepository.findAll();
        assertThat(pOIList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPOI() throws Exception {
        // Initialize the database
        pOIRepository.saveAndFlush(pOI);
        pOISearchRepository.save(pOI);

        // Search the pOI
        restPOIMockMvc.perform(get("/api/_search/p-ois?query=id:" + pOI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pOI.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].bearing").value(hasItem(DEFAULT_BEARING.doubleValue())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].profile").value(hasItem(DEFAULT_PROFILE.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(POI.class);
        POI pOI1 = new POI();
        pOI1.setId(1L);
        POI pOI2 = new POI();
        pOI2.setId(pOI1.getId());
        assertThat(pOI1).isEqualTo(pOI2);
        pOI2.setId(2L);
        assertThat(pOI1).isNotEqualTo(pOI2);
        pOI1.setId(null);
        assertThat(pOI1).isNotEqualTo(pOI2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(POIDTO.class);
        POIDTO pOIDTO1 = new POIDTO();
        pOIDTO1.setId(1L);
        POIDTO pOIDTO2 = new POIDTO();
        assertThat(pOIDTO1).isNotEqualTo(pOIDTO2);
        pOIDTO2.setId(pOIDTO1.getId());
        assertThat(pOIDTO1).isEqualTo(pOIDTO2);
        pOIDTO2.setId(2L);
        assertThat(pOIDTO1).isNotEqualTo(pOIDTO2);
        pOIDTO1.setId(null);
        assertThat(pOIDTO1).isNotEqualTo(pOIDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pOIMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pOIMapper.fromId(null)).isNull();
    }
}
