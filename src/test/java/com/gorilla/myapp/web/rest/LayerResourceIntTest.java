package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.Layer;
import com.gorilla.myapp.repository.LayerRepository;
import com.gorilla.myapp.service.LayerService;
import com.gorilla.myapp.repository.search.LayerSearchRepository;
import com.gorilla.myapp.service.dto.LayerDTO;
import com.gorilla.myapp.service.mapper.LayerMapper;
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
 * Test class for the LayerResource REST controller.
 *
 * @see LayerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class LayerResourceIntTest {

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_OPACITY = 1D;
    private static final Double UPDATED_OPACITY = 2D;

    private static final Double DEFAULT_ROTATION = 1D;
    private static final Double UPDATED_ROTATION = 2D;

    private static final Double DEFAULT_CENTER_X = 1D;
    private static final Double UPDATED_CENTER_X = 2D;

    private static final Double DEFAULT_CENTER_Y = 1D;
    private static final Double UPDATED_CENTER_Y = 2D;

    private static final Double DEFAULT_SCALE_X = 1D;
    private static final Double UPDATED_SCALE_X = 2D;

    private static final Double DEFAULT_SCALE_Y = 1D;
    private static final Double UPDATED_SCALE_Y = 2D;

    private static final Double DEFAULT_CROP_X_MIN = 1D;
    private static final Double UPDATED_CROP_X_MIN = 2D;

    private static final Double DEFAULT_CROP_Y_MIN = 1D;
    private static final Double UPDATED_CROP_Y_MIN = 2D;

    private static final Double DEFAULT_CROP_X_MAX = 1D;
    private static final Double UPDATED_CROP_X_MAX = 2D;

    private static final Double DEFAULT_CENTER_LONGITUDE = 1D;
    private static final Double UPDATED_CENTER_LONGITUDE = 2D;

    private static final Double DEFAULT_CENTER_LATITUDE = 1D;
    private static final Double UPDATED_CENTER_LATITUDE = 2D;

    private static final Double DEFAULT_ALTITUDE = 1D;
    private static final Double UPDATED_ALTITUDE = 2D;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LayerRepository layerRepository;

    @Autowired
    private LayerMapper layerMapper;

    @Autowired
    private LayerService layerService;

    @Autowired
    private LayerSearchRepository layerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLayerMockMvc;

    private Layer layer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LayerResource layerResource = new LayerResource(layerService);
        this.restLayerMockMvc = MockMvcBuilders.standaloneSetup(layerResource)
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
    public static Layer createEntity(EntityManager em) {
        Layer layer = new Layer()
            .priority(DEFAULT_PRIORITY)
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .opacity(DEFAULT_OPACITY)
            .rotation(DEFAULT_ROTATION)
            .centerX(DEFAULT_CENTER_X)
            .centerY(DEFAULT_CENTER_Y)
            .scaleX(DEFAULT_SCALE_X)
            .scaleY(DEFAULT_SCALE_Y)
            .cropXMin(DEFAULT_CROP_X_MIN)
            .cropYMin(DEFAULT_CROP_Y_MIN)
            .cropXMax(DEFAULT_CROP_X_MAX)
            .centerLongitude(DEFAULT_CENTER_LONGITUDE)
            .centerLatitude(DEFAULT_CENTER_LATITUDE)
            .altitude(DEFAULT_ALTITUDE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return layer;
    }

    @Before
    public void initTest() {
        layerSearchRepository.deleteAll();
        layer = createEntity(em);
    }

    @Test
    @Transactional
    public void createLayer() throws Exception {
        int databaseSizeBeforeCreate = layerRepository.findAll().size();

        // Create the Layer
        LayerDTO layerDTO = layerMapper.toDto(layer);
        restLayerMockMvc.perform(post("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isCreated());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeCreate + 1);
        Layer testLayer = layerList.get(layerList.size() - 1);
        assertThat(testLayer.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testLayer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLayer.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testLayer.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testLayer.getOpacity()).isEqualTo(DEFAULT_OPACITY);
        assertThat(testLayer.getRotation()).isEqualTo(DEFAULT_ROTATION);
        assertThat(testLayer.getCenterX()).isEqualTo(DEFAULT_CENTER_X);
        assertThat(testLayer.getCenterY()).isEqualTo(DEFAULT_CENTER_Y);
        assertThat(testLayer.getScaleX()).isEqualTo(DEFAULT_SCALE_X);
        assertThat(testLayer.getScaleY()).isEqualTo(DEFAULT_SCALE_Y);
        assertThat(testLayer.getCropXMin()).isEqualTo(DEFAULT_CROP_X_MIN);
        assertThat(testLayer.getCropYMin()).isEqualTo(DEFAULT_CROP_Y_MIN);
        assertThat(testLayer.getCropXMax()).isEqualTo(DEFAULT_CROP_X_MAX);
        assertThat(testLayer.getCenterLongitude()).isEqualTo(DEFAULT_CENTER_LONGITUDE);
        assertThat(testLayer.getCenterLatitude()).isEqualTo(DEFAULT_CENTER_LATITUDE);
        assertThat(testLayer.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testLayer.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testLayer.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testLayer.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the Layer in Elasticsearch
        Layer layerEs = layerSearchRepository.findOne(testLayer.getId());
        assertThat(layerEs).isEqualToComparingFieldByField(testLayer);
    }

    @Test
    @Transactional
    public void createLayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = layerRepository.findAll().size();

        // Create the Layer with an existing ID
        layer.setId(1L);
        LayerDTO layerDTO = layerMapper.toDto(layer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLayerMockMvc.perform(post("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLayers() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList
        restLayerMockMvc.perform(get("/api/layers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(layer.getId().intValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].opacity").value(hasItem(DEFAULT_OPACITY.doubleValue())))
            .andExpect(jsonPath("$.[*].rotation").value(hasItem(DEFAULT_ROTATION.doubleValue())))
            .andExpect(jsonPath("$.[*].centerX").value(hasItem(DEFAULT_CENTER_X.doubleValue())))
            .andExpect(jsonPath("$.[*].centerY").value(hasItem(DEFAULT_CENTER_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].scaleX").value(hasItem(DEFAULT_SCALE_X.doubleValue())))
            .andExpect(jsonPath("$.[*].scaleY").value(hasItem(DEFAULT_SCALE_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].cropXMin").value(hasItem(DEFAULT_CROP_X_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].cropYMin").value(hasItem(DEFAULT_CROP_Y_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].cropXMax").value(hasItem(DEFAULT_CROP_X_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].centerLongitude").value(hasItem(DEFAULT_CENTER_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].centerLatitude").value(hasItem(DEFAULT_CENTER_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getLayer() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get the layer
        restLayerMockMvc.perform(get("/api/layers/{id}", layer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(layer.getId().intValue()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.opacity").value(DEFAULT_OPACITY.doubleValue()))
            .andExpect(jsonPath("$.rotation").value(DEFAULT_ROTATION.doubleValue()))
            .andExpect(jsonPath("$.centerX").value(DEFAULT_CENTER_X.doubleValue()))
            .andExpect(jsonPath("$.centerY").value(DEFAULT_CENTER_Y.doubleValue()))
            .andExpect(jsonPath("$.scaleX").value(DEFAULT_SCALE_X.doubleValue()))
            .andExpect(jsonPath("$.scaleY").value(DEFAULT_SCALE_Y.doubleValue()))
            .andExpect(jsonPath("$.cropXMin").value(DEFAULT_CROP_X_MIN.doubleValue()))
            .andExpect(jsonPath("$.cropYMin").value(DEFAULT_CROP_Y_MIN.doubleValue()))
            .andExpect(jsonPath("$.cropXMax").value(DEFAULT_CROP_X_MAX.doubleValue()))
            .andExpect(jsonPath("$.centerLongitude").value(DEFAULT_CENTER_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.centerLatitude").value(DEFAULT_CENTER_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE.doubleValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingLayer() throws Exception {
        // Get the layer
        restLayerMockMvc.perform(get("/api/layers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLayer() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);
        layerSearchRepository.save(layer);
        int databaseSizeBeforeUpdate = layerRepository.findAll().size();

        // Update the layer
        Layer updatedLayer = layerRepository.findOne(layer.getId());
        updatedLayer
            .priority(UPDATED_PRIORITY)
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .opacity(UPDATED_OPACITY)
            .rotation(UPDATED_ROTATION)
            .centerX(UPDATED_CENTER_X)
            .centerY(UPDATED_CENTER_Y)
            .scaleX(UPDATED_SCALE_X)
            .scaleY(UPDATED_SCALE_Y)
            .cropXMin(UPDATED_CROP_X_MIN)
            .cropYMin(UPDATED_CROP_Y_MIN)
            .cropXMax(UPDATED_CROP_X_MAX)
            .centerLongitude(UPDATED_CENTER_LONGITUDE)
            .centerLatitude(UPDATED_CENTER_LATITUDE)
            .altitude(UPDATED_ALTITUDE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        LayerDTO layerDTO = layerMapper.toDto(updatedLayer);

        restLayerMockMvc.perform(put("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isOk());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeUpdate);
        Layer testLayer = layerList.get(layerList.size() - 1);
        assertThat(testLayer.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLayer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLayer.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testLayer.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testLayer.getOpacity()).isEqualTo(UPDATED_OPACITY);
        assertThat(testLayer.getRotation()).isEqualTo(UPDATED_ROTATION);
        assertThat(testLayer.getCenterX()).isEqualTo(UPDATED_CENTER_X);
        assertThat(testLayer.getCenterY()).isEqualTo(UPDATED_CENTER_Y);
        assertThat(testLayer.getScaleX()).isEqualTo(UPDATED_SCALE_X);
        assertThat(testLayer.getScaleY()).isEqualTo(UPDATED_SCALE_Y);
        assertThat(testLayer.getCropXMin()).isEqualTo(UPDATED_CROP_X_MIN);
        assertThat(testLayer.getCropYMin()).isEqualTo(UPDATED_CROP_Y_MIN);
        assertThat(testLayer.getCropXMax()).isEqualTo(UPDATED_CROP_X_MAX);
        assertThat(testLayer.getCenterLongitude()).isEqualTo(UPDATED_CENTER_LONGITUDE);
        assertThat(testLayer.getCenterLatitude()).isEqualTo(UPDATED_CENTER_LATITUDE);
        assertThat(testLayer.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testLayer.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testLayer.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testLayer.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the Layer in Elasticsearch
        Layer layerEs = layerSearchRepository.findOne(testLayer.getId());
        assertThat(layerEs).isEqualToComparingFieldByField(testLayer);
    }

    @Test
    @Transactional
    public void updateNonExistingLayer() throws Exception {
        int databaseSizeBeforeUpdate = layerRepository.findAll().size();

        // Create the Layer
        LayerDTO layerDTO = layerMapper.toDto(layer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLayerMockMvc.perform(put("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isCreated());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLayer() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);
        layerSearchRepository.save(layer);
        int databaseSizeBeforeDelete = layerRepository.findAll().size();

        // Get the layer
        restLayerMockMvc.perform(delete("/api/layers/{id}", layer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean layerExistsInEs = layerSearchRepository.exists(layer.getId());
        assertThat(layerExistsInEs).isFalse();

        // Validate the database is empty
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLayer() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);
        layerSearchRepository.save(layer);

        // Search the layer
        restLayerMockMvc.perform(get("/api/_search/layers?query=id:" + layer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(layer.getId().intValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].opacity").value(hasItem(DEFAULT_OPACITY.doubleValue())))
            .andExpect(jsonPath("$.[*].rotation").value(hasItem(DEFAULT_ROTATION.doubleValue())))
            .andExpect(jsonPath("$.[*].centerX").value(hasItem(DEFAULT_CENTER_X.doubleValue())))
            .andExpect(jsonPath("$.[*].centerY").value(hasItem(DEFAULT_CENTER_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].scaleX").value(hasItem(DEFAULT_SCALE_X.doubleValue())))
            .andExpect(jsonPath("$.[*].scaleY").value(hasItem(DEFAULT_SCALE_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].cropXMin").value(hasItem(DEFAULT_CROP_X_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].cropYMin").value(hasItem(DEFAULT_CROP_Y_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].cropXMax").value(hasItem(DEFAULT_CROP_X_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].centerLongitude").value(hasItem(DEFAULT_CENTER_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].centerLatitude").value(hasItem(DEFAULT_CENTER_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Layer.class);
        Layer layer1 = new Layer();
        layer1.setId(1L);
        Layer layer2 = new Layer();
        layer2.setId(layer1.getId());
        assertThat(layer1).isEqualTo(layer2);
        layer2.setId(2L);
        assertThat(layer1).isNotEqualTo(layer2);
        layer1.setId(null);
        assertThat(layer1).isNotEqualTo(layer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LayerDTO.class);
        LayerDTO layerDTO1 = new LayerDTO();
        layerDTO1.setId(1L);
        LayerDTO layerDTO2 = new LayerDTO();
        assertThat(layerDTO1).isNotEqualTo(layerDTO2);
        layerDTO2.setId(layerDTO1.getId());
        assertThat(layerDTO1).isEqualTo(layerDTO2);
        layerDTO2.setId(2L);
        assertThat(layerDTO1).isNotEqualTo(layerDTO2);
        layerDTO1.setId(null);
        assertThat(layerDTO1).isNotEqualTo(layerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(layerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(layerMapper.fromId(null)).isNull();
    }
}
