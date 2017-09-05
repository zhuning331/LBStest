package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.POIType;
import com.gorilla.myapp.repository.POITypeRepository;
import com.gorilla.myapp.service.POITypeService;
import com.gorilla.myapp.repository.search.POITypeSearchRepository;
import com.gorilla.myapp.service.dto.POITypeDTO;
import com.gorilla.myapp.service.mapper.POITypeMapper;
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
 * Test class for the POITypeResource REST controller.
 *
 * @see POITypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class POITypeResourceIntTest {

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_SHOW_POI = false;
    private static final Boolean UPDATED_SHOW_POI = true;

    private static final Long DEFAULT_LAYER_ID = 1L;
    private static final Long UPDATED_LAYER_ID = 2L;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private POITypeRepository pOITypeRepository;

    @Autowired
    private POITypeMapper pOITypeMapper;

    @Autowired
    private POITypeService pOITypeService;

    @Autowired
    private POITypeSearchRepository pOITypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPOITypeMockMvc;

    private POIType pOIType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final POITypeResource pOITypeResource = new POITypeResource(pOITypeService);
        this.restPOITypeMockMvc = MockMvcBuilders.standaloneSetup(pOITypeResource)
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
    public static POIType createEntity(EntityManager em) {
        POIType pOIType = new POIType()
            .priority(DEFAULT_PRIORITY)
            .type(DEFAULT_TYPE)
            .icon(DEFAULT_ICON)
            .iconContentType(DEFAULT_ICON_CONTENT_TYPE)
            .showPOI(DEFAULT_SHOW_POI)
            .layerId(DEFAULT_LAYER_ID)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return pOIType;
    }

    @Before
    public void initTest() {
        pOITypeSearchRepository.deleteAll();
        pOIType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPOIType() throws Exception {
        int databaseSizeBeforeCreate = pOITypeRepository.findAll().size();

        // Create the POIType
        POITypeDTO pOITypeDTO = pOITypeMapper.toDto(pOIType);
        restPOITypeMockMvc.perform(post("/api/p-oi-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOITypeDTO)))
            .andExpect(status().isCreated());

        // Validate the POIType in the database
        List<POIType> pOITypeList = pOITypeRepository.findAll();
        assertThat(pOITypeList).hasSize(databaseSizeBeforeCreate + 1);
        POIType testPOIType = pOITypeList.get(pOITypeList.size() - 1);
        assertThat(testPOIType.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPOIType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPOIType.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testPOIType.getIconContentType()).isEqualTo(DEFAULT_ICON_CONTENT_TYPE);
        assertThat(testPOIType.isShowPOI()).isEqualTo(DEFAULT_SHOW_POI);
        assertThat(testPOIType.getLayerId()).isEqualTo(DEFAULT_LAYER_ID);
        assertThat(testPOIType.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testPOIType.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testPOIType.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the POIType in Elasticsearch
        POIType pOITypeEs = pOITypeSearchRepository.findOne(testPOIType.getId());
        assertThat(pOITypeEs).isEqualToComparingFieldByField(testPOIType);
    }

    @Test
    @Transactional
    public void createPOITypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pOITypeRepository.findAll().size();

        // Create the POIType with an existing ID
        pOIType.setId(1L);
        POITypeDTO pOITypeDTO = pOITypeMapper.toDto(pOIType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPOITypeMockMvc.perform(post("/api/p-oi-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOITypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<POIType> pOITypeList = pOITypeRepository.findAll();
        assertThat(pOITypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPOITypes() throws Exception {
        // Initialize the database
        pOITypeRepository.saveAndFlush(pOIType);

        // Get all the pOITypeList
        restPOITypeMockMvc.perform(get("/api/p-oi-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pOIType.getId().intValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
            .andExpect(jsonPath("$.[*].showPOI").value(hasItem(DEFAULT_SHOW_POI.booleanValue())))
            .andExpect(jsonPath("$.[*].layerId").value(hasItem(DEFAULT_LAYER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getPOIType() throws Exception {
        // Initialize the database
        pOITypeRepository.saveAndFlush(pOIType);

        // Get the pOIType
        restPOITypeMockMvc.perform(get("/api/p-oi-types/{id}", pOIType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pOIType.getId().intValue()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.icon").value(Base64Utils.encodeToString(DEFAULT_ICON)))
            .andExpect(jsonPath("$.showPOI").value(DEFAULT_SHOW_POI.booleanValue()))
            .andExpect(jsonPath("$.layerId").value(DEFAULT_LAYER_ID.intValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingPOIType() throws Exception {
        // Get the pOIType
        restPOITypeMockMvc.perform(get("/api/p-oi-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePOIType() throws Exception {
        // Initialize the database
        pOITypeRepository.saveAndFlush(pOIType);
        pOITypeSearchRepository.save(pOIType);
        int databaseSizeBeforeUpdate = pOITypeRepository.findAll().size();

        // Update the pOIType
        POIType updatedPOIType = pOITypeRepository.findOne(pOIType.getId());
        updatedPOIType
            .priority(UPDATED_PRIORITY)
            .type(UPDATED_TYPE)
            .icon(UPDATED_ICON)
            .iconContentType(UPDATED_ICON_CONTENT_TYPE)
            .showPOI(UPDATED_SHOW_POI)
            .layerId(UPDATED_LAYER_ID)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        POITypeDTO pOITypeDTO = pOITypeMapper.toDto(updatedPOIType);

        restPOITypeMockMvc.perform(put("/api/p-oi-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOITypeDTO)))
            .andExpect(status().isOk());

        // Validate the POIType in the database
        List<POIType> pOITypeList = pOITypeRepository.findAll();
        assertThat(pOITypeList).hasSize(databaseSizeBeforeUpdate);
        POIType testPOIType = pOITypeList.get(pOITypeList.size() - 1);
        assertThat(testPOIType.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPOIType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPOIType.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testPOIType.getIconContentType()).isEqualTo(UPDATED_ICON_CONTENT_TYPE);
        assertThat(testPOIType.isShowPOI()).isEqualTo(UPDATED_SHOW_POI);
        assertThat(testPOIType.getLayerId()).isEqualTo(UPDATED_LAYER_ID);
        assertThat(testPOIType.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testPOIType.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testPOIType.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the POIType in Elasticsearch
        POIType pOITypeEs = pOITypeSearchRepository.findOne(testPOIType.getId());
        assertThat(pOITypeEs).isEqualToComparingFieldByField(testPOIType);
    }

    @Test
    @Transactional
    public void updateNonExistingPOIType() throws Exception {
        int databaseSizeBeforeUpdate = pOITypeRepository.findAll().size();

        // Create the POIType
        POITypeDTO pOITypeDTO = pOITypeMapper.toDto(pOIType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPOITypeMockMvc.perform(put("/api/p-oi-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOITypeDTO)))
            .andExpect(status().isCreated());

        // Validate the POIType in the database
        List<POIType> pOITypeList = pOITypeRepository.findAll();
        assertThat(pOITypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePOIType() throws Exception {
        // Initialize the database
        pOITypeRepository.saveAndFlush(pOIType);
        pOITypeSearchRepository.save(pOIType);
        int databaseSizeBeforeDelete = pOITypeRepository.findAll().size();

        // Get the pOIType
        restPOITypeMockMvc.perform(delete("/api/p-oi-types/{id}", pOIType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pOITypeExistsInEs = pOITypeSearchRepository.exists(pOIType.getId());
        assertThat(pOITypeExistsInEs).isFalse();

        // Validate the database is empty
        List<POIType> pOITypeList = pOITypeRepository.findAll();
        assertThat(pOITypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPOIType() throws Exception {
        // Initialize the database
        pOITypeRepository.saveAndFlush(pOIType);
        pOITypeSearchRepository.save(pOIType);

        // Search the pOIType
        restPOITypeMockMvc.perform(get("/api/_search/p-oi-types?query=id:" + pOIType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pOIType.getId().intValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
            .andExpect(jsonPath("$.[*].showPOI").value(hasItem(DEFAULT_SHOW_POI.booleanValue())))
            .andExpect(jsonPath("$.[*].layerId").value(hasItem(DEFAULT_LAYER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(POIType.class);
        POIType pOIType1 = new POIType();
        pOIType1.setId(1L);
        POIType pOIType2 = new POIType();
        pOIType2.setId(pOIType1.getId());
        assertThat(pOIType1).isEqualTo(pOIType2);
        pOIType2.setId(2L);
        assertThat(pOIType1).isNotEqualTo(pOIType2);
        pOIType1.setId(null);
        assertThat(pOIType1).isNotEqualTo(pOIType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(POITypeDTO.class);
        POITypeDTO pOITypeDTO1 = new POITypeDTO();
        pOITypeDTO1.setId(1L);
        POITypeDTO pOITypeDTO2 = new POITypeDTO();
        assertThat(pOITypeDTO1).isNotEqualTo(pOITypeDTO2);
        pOITypeDTO2.setId(pOITypeDTO1.getId());
        assertThat(pOITypeDTO1).isEqualTo(pOITypeDTO2);
        pOITypeDTO2.setId(2L);
        assertThat(pOITypeDTO1).isNotEqualTo(pOITypeDTO2);
        pOITypeDTO1.setId(null);
        assertThat(pOITypeDTO1).isNotEqualTo(pOITypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pOITypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pOITypeMapper.fromId(null)).isNull();
    }
}
