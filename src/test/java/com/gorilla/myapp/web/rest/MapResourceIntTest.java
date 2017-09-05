package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.Map;
import com.gorilla.myapp.repository.MapRepository;
import com.gorilla.myapp.service.MapService;
import com.gorilla.myapp.repository.search.MapSearchRepository;
import com.gorilla.myapp.service.dto.MapDTO;
import com.gorilla.myapp.service.mapper.MapMapper;
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

import com.gorilla.myapp.domain.enumeration.LayerDisplayMode;
/**
 * Test class for the MapResource REST controller.
 *
 * @see MapResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class MapResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LayerDisplayMode DEFAULT_LAYER_DISPLAY_MODE = LayerDisplayMode.SINGLE;
    private static final LayerDisplayMode UPDATED_LAYER_DISPLAY_MODE = LayerDisplayMode.MULTIPLE;

    private static final String DEFAULT_TILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_TILE_URL = "BBBBBBBBBB";

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_ALTITUDE = 1D;
    private static final Double UPDATED_ALTITUDE = 2D;

    private static final Integer DEFAULT_ZOOM_LEVEL = 1;
    private static final Integer UPDATED_ZOOM_LEVEL = 2;

    private static final Double DEFAULT_ROTATION = 1D;
    private static final Double UPDATED_ROTATION = 2D;

    private static final Boolean DEFAULT_FIX_ROTATION = false;
    private static final Boolean UPDATED_FIX_ROTATION = true;

    private static final Boolean DEFAULT_SHOW_MAP = false;
    private static final Boolean UPDATED_SHOW_MAP = true;

    private static final Boolean DEFAULT_SHOW_LAYER = false;
    private static final Boolean UPDATED_SHOW_LAYER = true;

    private static final Boolean DEFAULT_SHOW_CENTER_AS_POI = false;
    private static final Boolean UPDATED_SHOW_CENTER_AS_POI = true;

    private static final byte[] DEFAULT_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private MapMapper mapMapper;

    @Autowired
    private MapService mapService;

    @Autowired
    private MapSearchRepository mapSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMapMockMvc;

    private Map map;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MapResource mapResource = new MapResource(mapService);
        this.restMapMockMvc = MockMvcBuilders.standaloneSetup(mapResource)
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
    public static Map createEntity(EntityManager em) {
        Map map = new Map()
            .name(DEFAULT_NAME)
            .layerDisplayMode(DEFAULT_LAYER_DISPLAY_MODE)
            .tileURL(DEFAULT_TILE_URL)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .altitude(DEFAULT_ALTITUDE)
            .zoomLevel(DEFAULT_ZOOM_LEVEL)
            .rotation(DEFAULT_ROTATION)
            .fixRotation(DEFAULT_FIX_ROTATION)
            .showMap(DEFAULT_SHOW_MAP)
            .showLayer(DEFAULT_SHOW_LAYER)
            .showCenterAsPOI(DEFAULT_SHOW_CENTER_AS_POI)
            .icon(DEFAULT_ICON)
            .iconContentType(DEFAULT_ICON_CONTENT_TYPE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return map;
    }

    @Before
    public void initTest() {
        mapSearchRepository.deleteAll();
        map = createEntity(em);
    }

    @Test
    @Transactional
    public void createMap() throws Exception {
        int databaseSizeBeforeCreate = mapRepository.findAll().size();

        // Create the Map
        MapDTO mapDTO = mapMapper.toDto(map);
        restMapMockMvc.perform(post("/api/maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mapDTO)))
            .andExpect(status().isCreated());

        // Validate the Map in the database
        List<Map> mapList = mapRepository.findAll();
        assertThat(mapList).hasSize(databaseSizeBeforeCreate + 1);
        Map testMap = mapList.get(mapList.size() - 1);
        assertThat(testMap.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMap.getLayerDisplayMode()).isEqualTo(DEFAULT_LAYER_DISPLAY_MODE);
        assertThat(testMap.getTileURL()).isEqualTo(DEFAULT_TILE_URL);
        assertThat(testMap.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testMap.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testMap.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testMap.getZoomLevel()).isEqualTo(DEFAULT_ZOOM_LEVEL);
        assertThat(testMap.getRotation()).isEqualTo(DEFAULT_ROTATION);
        assertThat(testMap.isFixRotation()).isEqualTo(DEFAULT_FIX_ROTATION);
        assertThat(testMap.isShowMap()).isEqualTo(DEFAULT_SHOW_MAP);
        assertThat(testMap.isShowLayer()).isEqualTo(DEFAULT_SHOW_LAYER);
        assertThat(testMap.isShowCenterAsPOI()).isEqualTo(DEFAULT_SHOW_CENTER_AS_POI);
        assertThat(testMap.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testMap.getIconContentType()).isEqualTo(DEFAULT_ICON_CONTENT_TYPE);
        assertThat(testMap.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testMap.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testMap.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the Map in Elasticsearch
        Map mapEs = mapSearchRepository.findOne(testMap.getId());
        assertThat(mapEs).isEqualToComparingFieldByField(testMap);
    }

    @Test
    @Transactional
    public void createMapWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mapRepository.findAll().size();

        // Create the Map with an existing ID
        map.setId(1L);
        MapDTO mapDTO = mapMapper.toDto(map);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMapMockMvc.perform(post("/api/maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mapDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Map> mapList = mapRepository.findAll();
        assertThat(mapList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMaps() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Get all the mapList
        restMapMockMvc.perform(get("/api/maps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(map.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].layerDisplayMode").value(hasItem(DEFAULT_LAYER_DISPLAY_MODE.toString())))
            .andExpect(jsonPath("$.[*].tileURL").value(hasItem(DEFAULT_TILE_URL.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].zoomLevel").value(hasItem(DEFAULT_ZOOM_LEVEL)))
            .andExpect(jsonPath("$.[*].rotation").value(hasItem(DEFAULT_ROTATION.doubleValue())))
            .andExpect(jsonPath("$.[*].fixRotation").value(hasItem(DEFAULT_FIX_ROTATION.booleanValue())))
            .andExpect(jsonPath("$.[*].showMap").value(hasItem(DEFAULT_SHOW_MAP.booleanValue())))
            .andExpect(jsonPath("$.[*].showLayer").value(hasItem(DEFAULT_SHOW_LAYER.booleanValue())))
            .andExpect(jsonPath("$.[*].showCenterAsPOI").value(hasItem(DEFAULT_SHOW_CENTER_AS_POI.booleanValue())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Get the map
        restMapMockMvc.perform(get("/api/maps/{id}", map.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(map.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.layerDisplayMode").value(DEFAULT_LAYER_DISPLAY_MODE.toString()))
            .andExpect(jsonPath("$.tileURL").value(DEFAULT_TILE_URL.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE.doubleValue()))
            .andExpect(jsonPath("$.zoomLevel").value(DEFAULT_ZOOM_LEVEL))
            .andExpect(jsonPath("$.rotation").value(DEFAULT_ROTATION.doubleValue()))
            .andExpect(jsonPath("$.fixRotation").value(DEFAULT_FIX_ROTATION.booleanValue()))
            .andExpect(jsonPath("$.showMap").value(DEFAULT_SHOW_MAP.booleanValue()))
            .andExpect(jsonPath("$.showLayer").value(DEFAULT_SHOW_LAYER.booleanValue()))
            .andExpect(jsonPath("$.showCenterAsPOI").value(DEFAULT_SHOW_CENTER_AS_POI.booleanValue()))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.icon").value(Base64Utils.encodeToString(DEFAULT_ICON)))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingMap() throws Exception {
        // Get the map
        restMapMockMvc.perform(get("/api/maps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);
        mapSearchRepository.save(map);
        int databaseSizeBeforeUpdate = mapRepository.findAll().size();

        // Update the map
        Map updatedMap = mapRepository.findOne(map.getId());
        updatedMap
            .name(UPDATED_NAME)
            .layerDisplayMode(UPDATED_LAYER_DISPLAY_MODE)
            .tileURL(UPDATED_TILE_URL)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .altitude(UPDATED_ALTITUDE)
            .zoomLevel(UPDATED_ZOOM_LEVEL)
            .rotation(UPDATED_ROTATION)
            .fixRotation(UPDATED_FIX_ROTATION)
            .showMap(UPDATED_SHOW_MAP)
            .showLayer(UPDATED_SHOW_LAYER)
            .showCenterAsPOI(UPDATED_SHOW_CENTER_AS_POI)
            .icon(UPDATED_ICON)
            .iconContentType(UPDATED_ICON_CONTENT_TYPE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        MapDTO mapDTO = mapMapper.toDto(updatedMap);

        restMapMockMvc.perform(put("/api/maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mapDTO)))
            .andExpect(status().isOk());

        // Validate the Map in the database
        List<Map> mapList = mapRepository.findAll();
        assertThat(mapList).hasSize(databaseSizeBeforeUpdate);
        Map testMap = mapList.get(mapList.size() - 1);
        assertThat(testMap.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMap.getLayerDisplayMode()).isEqualTo(UPDATED_LAYER_DISPLAY_MODE);
        assertThat(testMap.getTileURL()).isEqualTo(UPDATED_TILE_URL);
        assertThat(testMap.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testMap.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testMap.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testMap.getZoomLevel()).isEqualTo(UPDATED_ZOOM_LEVEL);
        assertThat(testMap.getRotation()).isEqualTo(UPDATED_ROTATION);
        assertThat(testMap.isFixRotation()).isEqualTo(UPDATED_FIX_ROTATION);
        assertThat(testMap.isShowMap()).isEqualTo(UPDATED_SHOW_MAP);
        assertThat(testMap.isShowLayer()).isEqualTo(UPDATED_SHOW_LAYER);
        assertThat(testMap.isShowCenterAsPOI()).isEqualTo(UPDATED_SHOW_CENTER_AS_POI);
        assertThat(testMap.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testMap.getIconContentType()).isEqualTo(UPDATED_ICON_CONTENT_TYPE);
        assertThat(testMap.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testMap.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testMap.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the Map in Elasticsearch
        Map mapEs = mapSearchRepository.findOne(testMap.getId());
        assertThat(mapEs).isEqualToComparingFieldByField(testMap);
    }

    @Test
    @Transactional
    public void updateNonExistingMap() throws Exception {
        int databaseSizeBeforeUpdate = mapRepository.findAll().size();

        // Create the Map
        MapDTO mapDTO = mapMapper.toDto(map);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMapMockMvc.perform(put("/api/maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mapDTO)))
            .andExpect(status().isCreated());

        // Validate the Map in the database
        List<Map> mapList = mapRepository.findAll();
        assertThat(mapList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);
        mapSearchRepository.save(map);
        int databaseSizeBeforeDelete = mapRepository.findAll().size();

        // Get the map
        restMapMockMvc.perform(delete("/api/maps/{id}", map.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mapExistsInEs = mapSearchRepository.exists(map.getId());
        assertThat(mapExistsInEs).isFalse();

        // Validate the database is empty
        List<Map> mapList = mapRepository.findAll();
        assertThat(mapList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);
        mapSearchRepository.save(map);

        // Search the map
        restMapMockMvc.perform(get("/api/_search/maps?query=id:" + map.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(map.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].layerDisplayMode").value(hasItem(DEFAULT_LAYER_DISPLAY_MODE.toString())))
            .andExpect(jsonPath("$.[*].tileURL").value(hasItem(DEFAULT_TILE_URL.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].zoomLevel").value(hasItem(DEFAULT_ZOOM_LEVEL)))
            .andExpect(jsonPath("$.[*].rotation").value(hasItem(DEFAULT_ROTATION.doubleValue())))
            .andExpect(jsonPath("$.[*].fixRotation").value(hasItem(DEFAULT_FIX_ROTATION.booleanValue())))
            .andExpect(jsonPath("$.[*].showMap").value(hasItem(DEFAULT_SHOW_MAP.booleanValue())))
            .andExpect(jsonPath("$.[*].showLayer").value(hasItem(DEFAULT_SHOW_LAYER.booleanValue())))
            .andExpect(jsonPath("$.[*].showCenterAsPOI").value(hasItem(DEFAULT_SHOW_CENTER_AS_POI.booleanValue())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Map.class);
        Map map1 = new Map();
        map1.setId(1L);
        Map map2 = new Map();
        map2.setId(map1.getId());
        assertThat(map1).isEqualTo(map2);
        map2.setId(2L);
        assertThat(map1).isNotEqualTo(map2);
        map1.setId(null);
        assertThat(map1).isNotEqualTo(map2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MapDTO.class);
        MapDTO mapDTO1 = new MapDTO();
        mapDTO1.setId(1L);
        MapDTO mapDTO2 = new MapDTO();
        assertThat(mapDTO1).isNotEqualTo(mapDTO2);
        mapDTO2.setId(mapDTO1.getId());
        assertThat(mapDTO1).isEqualTo(mapDTO2);
        mapDTO2.setId(2L);
        assertThat(mapDTO1).isNotEqualTo(mapDTO2);
        mapDTO1.setId(null);
        assertThat(mapDTO1).isNotEqualTo(mapDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mapMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mapMapper.fromId(null)).isNull();
    }
}
