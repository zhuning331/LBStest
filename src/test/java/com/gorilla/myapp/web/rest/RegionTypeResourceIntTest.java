package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.RegionType;
import com.gorilla.myapp.repository.RegionTypeRepository;
import com.gorilla.myapp.service.RegionTypeService;
import com.gorilla.myapp.repository.search.RegionTypeSearchRepository;
import com.gorilla.myapp.service.dto.RegionTypeDTO;
import com.gorilla.myapp.service.mapper.RegionTypeMapper;
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
 * Test class for the RegionTypeResource REST controller.
 *
 * @see RegionTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class RegionTypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHOW_REGION = false;
    private static final Boolean UPDATED_SHOW_REGION = true;

    private static final String DEFAULT_BORDER_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BORDER_COLOR = "BBBBBBBBBB";

    private static final byte[] DEFAULT_BACKGROUND_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BACKGROUND_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_BACKGROUND_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BACKGROUND_IMAGE_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_BACKGROUND_IMAGE_SCALE = 1D;
    private static final Double UPDATED_BACKGROUND_IMAGE_SCALE = 2D;

    private static final String DEFAULT_BACKGROUND_IMAGE_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_IMAGE_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_BACKGROUND_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_COLOR = "BBBBBBBBBB";

    private static final Long DEFAULT_LAYER_ID = 1L;
    private static final Long UPDATED_LAYER_ID = 2L;

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RegionTypeRepository regionTypeRepository;

    @Autowired
    private RegionTypeMapper regionTypeMapper;

    @Autowired
    private RegionTypeService regionTypeService;

    @Autowired
    private RegionTypeSearchRepository regionTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegionTypeMockMvc;

    private RegionType regionType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegionTypeResource regionTypeResource = new RegionTypeResource(regionTypeService);
        this.restRegionTypeMockMvc = MockMvcBuilders.standaloneSetup(regionTypeResource)
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
    public static RegionType createEntity(EntityManager em) {
        RegionType regionType = new RegionType()
            .type(DEFAULT_TYPE)
            .showRegion(DEFAULT_SHOW_REGION)
            .borderColor(DEFAULT_BORDER_COLOR)
            .backgroundImage(DEFAULT_BACKGROUND_IMAGE)
            .backgroundImageContentType(DEFAULT_BACKGROUND_IMAGE_CONTENT_TYPE)
            .backgroundImageScale(DEFAULT_BACKGROUND_IMAGE_SCALE)
            .backgroundImageColor(DEFAULT_BACKGROUND_IMAGE_COLOR)
            .backgroundColor(DEFAULT_BACKGROUND_COLOR)
            .layerId(DEFAULT_LAYER_ID)
            .priority(DEFAULT_PRIORITY)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return regionType;
    }

    @Before
    public void initTest() {
        regionTypeSearchRepository.deleteAll();
        regionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegionType() throws Exception {
        int databaseSizeBeforeCreate = regionTypeRepository.findAll().size();

        // Create the RegionType
        RegionTypeDTO regionTypeDTO = regionTypeMapper.toDto(regionType);
        restRegionTypeMockMvc.perform(post("/api/region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the RegionType in the database
        List<RegionType> regionTypeList = regionTypeRepository.findAll();
        assertThat(regionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RegionType testRegionType = regionTypeList.get(regionTypeList.size() - 1);
        assertThat(testRegionType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRegionType.isShowRegion()).isEqualTo(DEFAULT_SHOW_REGION);
        assertThat(testRegionType.getBorderColor()).isEqualTo(DEFAULT_BORDER_COLOR);
        assertThat(testRegionType.getBackgroundImage()).isEqualTo(DEFAULT_BACKGROUND_IMAGE);
        assertThat(testRegionType.getBackgroundImageContentType()).isEqualTo(DEFAULT_BACKGROUND_IMAGE_CONTENT_TYPE);
        assertThat(testRegionType.getBackgroundImageScale()).isEqualTo(DEFAULT_BACKGROUND_IMAGE_SCALE);
        assertThat(testRegionType.getBackgroundImageColor()).isEqualTo(DEFAULT_BACKGROUND_IMAGE_COLOR);
        assertThat(testRegionType.getBackgroundColor()).isEqualTo(DEFAULT_BACKGROUND_COLOR);
        assertThat(testRegionType.getLayerId()).isEqualTo(DEFAULT_LAYER_ID);
        assertThat(testRegionType.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testRegionType.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testRegionType.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testRegionType.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the RegionType in Elasticsearch
        RegionType regionTypeEs = regionTypeSearchRepository.findOne(testRegionType.getId());
        assertThat(regionTypeEs).isEqualToComparingFieldByField(testRegionType);
    }

    @Test
    @Transactional
    public void createRegionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regionTypeRepository.findAll().size();

        // Create the RegionType with an existing ID
        regionType.setId(1L);
        RegionTypeDTO regionTypeDTO = regionTypeMapper.toDto(regionType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionTypeMockMvc.perform(post("/api/region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RegionType> regionTypeList = regionTypeRepository.findAll();
        assertThat(regionTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRegionTypes() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        // Get all the regionTypeList
        restRegionTypeMockMvc.perform(get("/api/region-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].showRegion").value(hasItem(DEFAULT_SHOW_REGION.booleanValue())))
            .andExpect(jsonPath("$.[*].borderColor").value(hasItem(DEFAULT_BORDER_COLOR.toString())))
            .andExpect(jsonPath("$.[*].backgroundImageContentType").value(hasItem(DEFAULT_BACKGROUND_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].backgroundImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_BACKGROUND_IMAGE))))
            .andExpect(jsonPath("$.[*].backgroundImageScale").value(hasItem(DEFAULT_BACKGROUND_IMAGE_SCALE.doubleValue())))
            .andExpect(jsonPath("$.[*].backgroundImageColor").value(hasItem(DEFAULT_BACKGROUND_IMAGE_COLOR.toString())))
            .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR.toString())))
            .andExpect(jsonPath("$.[*].layerId").value(hasItem(DEFAULT_LAYER_ID.intValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getRegionType() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        // Get the regionType
        restRegionTypeMockMvc.perform(get("/api/region-types/{id}", regionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regionType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.showRegion").value(DEFAULT_SHOW_REGION.booleanValue()))
            .andExpect(jsonPath("$.borderColor").value(DEFAULT_BORDER_COLOR.toString()))
            .andExpect(jsonPath("$.backgroundImageContentType").value(DEFAULT_BACKGROUND_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.backgroundImage").value(Base64Utils.encodeToString(DEFAULT_BACKGROUND_IMAGE)))
            .andExpect(jsonPath("$.backgroundImageScale").value(DEFAULT_BACKGROUND_IMAGE_SCALE.doubleValue()))
            .andExpect(jsonPath("$.backgroundImageColor").value(DEFAULT_BACKGROUND_IMAGE_COLOR.toString()))
            .andExpect(jsonPath("$.backgroundColor").value(DEFAULT_BACKGROUND_COLOR.toString()))
            .andExpect(jsonPath("$.layerId").value(DEFAULT_LAYER_ID.intValue()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingRegionType() throws Exception {
        // Get the regionType
        restRegionTypeMockMvc.perform(get("/api/region-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegionType() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);
        regionTypeSearchRepository.save(regionType);
        int databaseSizeBeforeUpdate = regionTypeRepository.findAll().size();

        // Update the regionType
        RegionType updatedRegionType = regionTypeRepository.findOne(regionType.getId());
        updatedRegionType
            .type(UPDATED_TYPE)
            .showRegion(UPDATED_SHOW_REGION)
            .borderColor(UPDATED_BORDER_COLOR)
            .backgroundImage(UPDATED_BACKGROUND_IMAGE)
            .backgroundImageContentType(UPDATED_BACKGROUND_IMAGE_CONTENT_TYPE)
            .backgroundImageScale(UPDATED_BACKGROUND_IMAGE_SCALE)
            .backgroundImageColor(UPDATED_BACKGROUND_IMAGE_COLOR)
            .backgroundColor(UPDATED_BACKGROUND_COLOR)
            .layerId(UPDATED_LAYER_ID)
            .priority(UPDATED_PRIORITY)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        RegionTypeDTO regionTypeDTO = regionTypeMapper.toDto(updatedRegionType);

        restRegionTypeMockMvc.perform(put("/api/region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionTypeDTO)))
            .andExpect(status().isOk());

        // Validate the RegionType in the database
        List<RegionType> regionTypeList = regionTypeRepository.findAll();
        assertThat(regionTypeList).hasSize(databaseSizeBeforeUpdate);
        RegionType testRegionType = regionTypeList.get(regionTypeList.size() - 1);
        assertThat(testRegionType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRegionType.isShowRegion()).isEqualTo(UPDATED_SHOW_REGION);
        assertThat(testRegionType.getBorderColor()).isEqualTo(UPDATED_BORDER_COLOR);
        assertThat(testRegionType.getBackgroundImage()).isEqualTo(UPDATED_BACKGROUND_IMAGE);
        assertThat(testRegionType.getBackgroundImageContentType()).isEqualTo(UPDATED_BACKGROUND_IMAGE_CONTENT_TYPE);
        assertThat(testRegionType.getBackgroundImageScale()).isEqualTo(UPDATED_BACKGROUND_IMAGE_SCALE);
        assertThat(testRegionType.getBackgroundImageColor()).isEqualTo(UPDATED_BACKGROUND_IMAGE_COLOR);
        assertThat(testRegionType.getBackgroundColor()).isEqualTo(UPDATED_BACKGROUND_COLOR);
        assertThat(testRegionType.getLayerId()).isEqualTo(UPDATED_LAYER_ID);
        assertThat(testRegionType.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testRegionType.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testRegionType.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testRegionType.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the RegionType in Elasticsearch
        RegionType regionTypeEs = regionTypeSearchRepository.findOne(testRegionType.getId());
        assertThat(regionTypeEs).isEqualToComparingFieldByField(testRegionType);
    }

    @Test
    @Transactional
    public void updateNonExistingRegionType() throws Exception {
        int databaseSizeBeforeUpdate = regionTypeRepository.findAll().size();

        // Create the RegionType
        RegionTypeDTO regionTypeDTO = regionTypeMapper.toDto(regionType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegionTypeMockMvc.perform(put("/api/region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the RegionType in the database
        List<RegionType> regionTypeList = regionTypeRepository.findAll();
        assertThat(regionTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRegionType() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);
        regionTypeSearchRepository.save(regionType);
        int databaseSizeBeforeDelete = regionTypeRepository.findAll().size();

        // Get the regionType
        restRegionTypeMockMvc.perform(delete("/api/region-types/{id}", regionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean regionTypeExistsInEs = regionTypeSearchRepository.exists(regionType.getId());
        assertThat(regionTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<RegionType> regionTypeList = regionTypeRepository.findAll();
        assertThat(regionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRegionType() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);
        regionTypeSearchRepository.save(regionType);

        // Search the regionType
        restRegionTypeMockMvc.perform(get("/api/_search/region-types?query=id:" + regionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].showRegion").value(hasItem(DEFAULT_SHOW_REGION.booleanValue())))
            .andExpect(jsonPath("$.[*].borderColor").value(hasItem(DEFAULT_BORDER_COLOR.toString())))
            .andExpect(jsonPath("$.[*].backgroundImageContentType").value(hasItem(DEFAULT_BACKGROUND_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].backgroundImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_BACKGROUND_IMAGE))))
            .andExpect(jsonPath("$.[*].backgroundImageScale").value(hasItem(DEFAULT_BACKGROUND_IMAGE_SCALE.doubleValue())))
            .andExpect(jsonPath("$.[*].backgroundImageColor").value(hasItem(DEFAULT_BACKGROUND_IMAGE_COLOR.toString())))
            .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR.toString())))
            .andExpect(jsonPath("$.[*].layerId").value(hasItem(DEFAULT_LAYER_ID.intValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegionType.class);
        RegionType regionType1 = new RegionType();
        regionType1.setId(1L);
        RegionType regionType2 = new RegionType();
        regionType2.setId(regionType1.getId());
        assertThat(regionType1).isEqualTo(regionType2);
        regionType2.setId(2L);
        assertThat(regionType1).isNotEqualTo(regionType2);
        regionType1.setId(null);
        assertThat(regionType1).isNotEqualTo(regionType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegionTypeDTO.class);
        RegionTypeDTO regionTypeDTO1 = new RegionTypeDTO();
        regionTypeDTO1.setId(1L);
        RegionTypeDTO regionTypeDTO2 = new RegionTypeDTO();
        assertThat(regionTypeDTO1).isNotEqualTo(regionTypeDTO2);
        regionTypeDTO2.setId(regionTypeDTO1.getId());
        assertThat(regionTypeDTO1).isEqualTo(regionTypeDTO2);
        regionTypeDTO2.setId(2L);
        assertThat(regionTypeDTO1).isNotEqualTo(regionTypeDTO2);
        regionTypeDTO1.setId(null);
        assertThat(regionTypeDTO1).isNotEqualTo(regionTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(regionTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(regionTypeMapper.fromId(null)).isNull();
    }
}
