package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.WebType;
import com.gorilla.myapp.repository.WebTypeRepository;
import com.gorilla.myapp.service.WebTypeService;
import com.gorilla.myapp.repository.search.WebTypeSearchRepository;
import com.gorilla.myapp.service.dto.WebTypeDTO;
import com.gorilla.myapp.service.mapper.WebTypeMapper;
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
 * Test class for the WebTypeResource REST controller.
 *
 * @see WebTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class WebTypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHOW_WEB = false;
    private static final Boolean UPDATED_SHOW_WEB = true;

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Double DEFAULT_WIDTH = 1D;
    private static final Double UPDATED_WIDTH = 2D;

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
    private WebTypeRepository webTypeRepository;

    @Autowired
    private WebTypeMapper webTypeMapper;

    @Autowired
    private WebTypeService webTypeService;

    @Autowired
    private WebTypeSearchRepository webTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWebTypeMockMvc;

    private WebType webType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WebTypeResource webTypeResource = new WebTypeResource(webTypeService);
        this.restWebTypeMockMvc = MockMvcBuilders.standaloneSetup(webTypeResource)
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
    public static WebType createEntity(EntityManager em) {
        WebType webType = new WebType()
            .type(DEFAULT_TYPE)
            .showWeb(DEFAULT_SHOW_WEB)
            .color(DEFAULT_COLOR)
            .width(DEFAULT_WIDTH)
            .layerId(DEFAULT_LAYER_ID)
            .priority(DEFAULT_PRIORITY)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return webType;
    }

    @Before
    public void initTest() {
        webTypeSearchRepository.deleteAll();
        webType = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebType() throws Exception {
        int databaseSizeBeforeCreate = webTypeRepository.findAll().size();

        // Create the WebType
        WebTypeDTO webTypeDTO = webTypeMapper.toDto(webType);
        restWebTypeMockMvc.perform(post("/api/web-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the WebType in the database
        List<WebType> webTypeList = webTypeRepository.findAll();
        assertThat(webTypeList).hasSize(databaseSizeBeforeCreate + 1);
        WebType testWebType = webTypeList.get(webTypeList.size() - 1);
        assertThat(testWebType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testWebType.isShowWeb()).isEqualTo(DEFAULT_SHOW_WEB);
        assertThat(testWebType.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testWebType.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testWebType.getLayerId()).isEqualTo(DEFAULT_LAYER_ID);
        assertThat(testWebType.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testWebType.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testWebType.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testWebType.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the WebType in Elasticsearch
        WebType webTypeEs = webTypeSearchRepository.findOne(testWebType.getId());
        assertThat(webTypeEs).isEqualToComparingFieldByField(testWebType);
    }

    @Test
    @Transactional
    public void createWebTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webTypeRepository.findAll().size();

        // Create the WebType with an existing ID
        webType.setId(1L);
        WebTypeDTO webTypeDTO = webTypeMapper.toDto(webType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebTypeMockMvc.perform(post("/api/web-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WebType> webTypeList = webTypeRepository.findAll();
        assertThat(webTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWebTypes() throws Exception {
        // Initialize the database
        webTypeRepository.saveAndFlush(webType);

        // Get all the webTypeList
        restWebTypeMockMvc.perform(get("/api/web-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].showWeb").value(hasItem(DEFAULT_SHOW_WEB.booleanValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].layerId").value(hasItem(DEFAULT_LAYER_ID.intValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getWebType() throws Exception {
        // Initialize the database
        webTypeRepository.saveAndFlush(webType);

        // Get the webType
        restWebTypeMockMvc.perform(get("/api/web-types/{id}", webType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(webType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.showWeb").value(DEFAULT_SHOW_WEB.booleanValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.doubleValue()))
            .andExpect(jsonPath("$.layerId").value(DEFAULT_LAYER_ID.intValue()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingWebType() throws Exception {
        // Get the webType
        restWebTypeMockMvc.perform(get("/api/web-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebType() throws Exception {
        // Initialize the database
        webTypeRepository.saveAndFlush(webType);
        webTypeSearchRepository.save(webType);
        int databaseSizeBeforeUpdate = webTypeRepository.findAll().size();

        // Update the webType
        WebType updatedWebType = webTypeRepository.findOne(webType.getId());
        updatedWebType
            .type(UPDATED_TYPE)
            .showWeb(UPDATED_SHOW_WEB)
            .color(UPDATED_COLOR)
            .width(UPDATED_WIDTH)
            .layerId(UPDATED_LAYER_ID)
            .priority(UPDATED_PRIORITY)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        WebTypeDTO webTypeDTO = webTypeMapper.toDto(updatedWebType);

        restWebTypeMockMvc.perform(put("/api/web-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webTypeDTO)))
            .andExpect(status().isOk());

        // Validate the WebType in the database
        List<WebType> webTypeList = webTypeRepository.findAll();
        assertThat(webTypeList).hasSize(databaseSizeBeforeUpdate);
        WebType testWebType = webTypeList.get(webTypeList.size() - 1);
        assertThat(testWebType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testWebType.isShowWeb()).isEqualTo(UPDATED_SHOW_WEB);
        assertThat(testWebType.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testWebType.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testWebType.getLayerId()).isEqualTo(UPDATED_LAYER_ID);
        assertThat(testWebType.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testWebType.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testWebType.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testWebType.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the WebType in Elasticsearch
        WebType webTypeEs = webTypeSearchRepository.findOne(testWebType.getId());
        assertThat(webTypeEs).isEqualToComparingFieldByField(testWebType);
    }

    @Test
    @Transactional
    public void updateNonExistingWebType() throws Exception {
        int databaseSizeBeforeUpdate = webTypeRepository.findAll().size();

        // Create the WebType
        WebTypeDTO webTypeDTO = webTypeMapper.toDto(webType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWebTypeMockMvc.perform(put("/api/web-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the WebType in the database
        List<WebType> webTypeList = webTypeRepository.findAll();
        assertThat(webTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWebType() throws Exception {
        // Initialize the database
        webTypeRepository.saveAndFlush(webType);
        webTypeSearchRepository.save(webType);
        int databaseSizeBeforeDelete = webTypeRepository.findAll().size();

        // Get the webType
        restWebTypeMockMvc.perform(delete("/api/web-types/{id}", webType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean webTypeExistsInEs = webTypeSearchRepository.exists(webType.getId());
        assertThat(webTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<WebType> webTypeList = webTypeRepository.findAll();
        assertThat(webTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWebType() throws Exception {
        // Initialize the database
        webTypeRepository.saveAndFlush(webType);
        webTypeSearchRepository.save(webType);

        // Search the webType
        restWebTypeMockMvc.perform(get("/api/_search/web-types?query=id:" + webType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].showWeb").value(hasItem(DEFAULT_SHOW_WEB.booleanValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].layerId").value(hasItem(DEFAULT_LAYER_ID.intValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebType.class);
        WebType webType1 = new WebType();
        webType1.setId(1L);
        WebType webType2 = new WebType();
        webType2.setId(webType1.getId());
        assertThat(webType1).isEqualTo(webType2);
        webType2.setId(2L);
        assertThat(webType1).isNotEqualTo(webType2);
        webType1.setId(null);
        assertThat(webType1).isNotEqualTo(webType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebTypeDTO.class);
        WebTypeDTO webTypeDTO1 = new WebTypeDTO();
        webTypeDTO1.setId(1L);
        WebTypeDTO webTypeDTO2 = new WebTypeDTO();
        assertThat(webTypeDTO1).isNotEqualTo(webTypeDTO2);
        webTypeDTO2.setId(webTypeDTO1.getId());
        assertThat(webTypeDTO1).isEqualTo(webTypeDTO2);
        webTypeDTO2.setId(2L);
        assertThat(webTypeDTO1).isNotEqualTo(webTypeDTO2);
        webTypeDTO1.setId(null);
        assertThat(webTypeDTO1).isNotEqualTo(webTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(webTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(webTypeMapper.fromId(null)).isNull();
    }
}
