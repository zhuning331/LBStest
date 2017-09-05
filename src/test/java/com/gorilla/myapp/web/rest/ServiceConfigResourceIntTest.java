package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.ServiceConfig;
import com.gorilla.myapp.repository.ServiceConfigRepository;
import com.gorilla.myapp.service.ServiceConfigService;
import com.gorilla.myapp.repository.search.ServiceConfigSearchRepository;
import com.gorilla.myapp.service.dto.ServiceConfigDTO;
import com.gorilla.myapp.service.mapper.ServiceConfigMapper;
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
 * Test class for the ServiceConfigResource REST controller.
 *
 * @see ServiceConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class ServiceConfigResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ServiceConfigRepository serviceConfigRepository;

    @Autowired
    private ServiceConfigMapper serviceConfigMapper;

    @Autowired
    private ServiceConfigService serviceConfigService;

    @Autowired
    private ServiceConfigSearchRepository serviceConfigSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServiceConfigMockMvc;

    private ServiceConfig serviceConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceConfigResource serviceConfigResource = new ServiceConfigResource(serviceConfigService);
        this.restServiceConfigMockMvc = MockMvcBuilders.standaloneSetup(serviceConfigResource)
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
    public static ServiceConfig createEntity(EntityManager em) {
        ServiceConfig serviceConfig = new ServiceConfig()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return serviceConfig;
    }

    @Before
    public void initTest() {
        serviceConfigSearchRepository.deleteAll();
        serviceConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceConfig() throws Exception {
        int databaseSizeBeforeCreate = serviceConfigRepository.findAll().size();

        // Create the ServiceConfig
        ServiceConfigDTO serviceConfigDTO = serviceConfigMapper.toDto(serviceConfig);
        restServiceConfigMockMvc.perform(post("/api/service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceConfig in the database
        List<ServiceConfig> serviceConfigList = serviceConfigRepository.findAll();
        assertThat(serviceConfigList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceConfig testServiceConfig = serviceConfigList.get(serviceConfigList.size() - 1);
        assertThat(testServiceConfig.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testServiceConfig.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testServiceConfig.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testServiceConfig.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testServiceConfig.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the ServiceConfig in Elasticsearch
        ServiceConfig serviceConfigEs = serviceConfigSearchRepository.findOne(testServiceConfig.getId());
        assertThat(serviceConfigEs).isEqualToComparingFieldByField(testServiceConfig);
    }

    @Test
    @Transactional
    public void createServiceConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceConfigRepository.findAll().size();

        // Create the ServiceConfig with an existing ID
        serviceConfig.setId(1L);
        ServiceConfigDTO serviceConfigDTO = serviceConfigMapper.toDto(serviceConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceConfigMockMvc.perform(post("/api/service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ServiceConfig> serviceConfigList = serviceConfigRepository.findAll();
        assertThat(serviceConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllServiceConfigs() throws Exception {
        // Initialize the database
        serviceConfigRepository.saveAndFlush(serviceConfig);

        // Get all the serviceConfigList
        restServiceConfigMockMvc.perform(get("/api/service-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getServiceConfig() throws Exception {
        // Initialize the database
        serviceConfigRepository.saveAndFlush(serviceConfig);

        // Get the serviceConfig
        restServiceConfigMockMvc.perform(get("/api/service-configs/{id}", serviceConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceConfig.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingServiceConfig() throws Exception {
        // Get the serviceConfig
        restServiceConfigMockMvc.perform(get("/api/service-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceConfig() throws Exception {
        // Initialize the database
        serviceConfigRepository.saveAndFlush(serviceConfig);
        serviceConfigSearchRepository.save(serviceConfig);
        int databaseSizeBeforeUpdate = serviceConfigRepository.findAll().size();

        // Update the serviceConfig
        ServiceConfig updatedServiceConfig = serviceConfigRepository.findOne(serviceConfig.getId());
        updatedServiceConfig
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        ServiceConfigDTO serviceConfigDTO = serviceConfigMapper.toDto(updatedServiceConfig);

        restServiceConfigMockMvc.perform(put("/api/service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceConfigDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceConfig in the database
        List<ServiceConfig> serviceConfigList = serviceConfigRepository.findAll();
        assertThat(serviceConfigList).hasSize(databaseSizeBeforeUpdate);
        ServiceConfig testServiceConfig = serviceConfigList.get(serviceConfigList.size() - 1);
        assertThat(testServiceConfig.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testServiceConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testServiceConfig.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testServiceConfig.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testServiceConfig.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the ServiceConfig in Elasticsearch
        ServiceConfig serviceConfigEs = serviceConfigSearchRepository.findOne(testServiceConfig.getId());
        assertThat(serviceConfigEs).isEqualToComparingFieldByField(testServiceConfig);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceConfig() throws Exception {
        int databaseSizeBeforeUpdate = serviceConfigRepository.findAll().size();

        // Create the ServiceConfig
        ServiceConfigDTO serviceConfigDTO = serviceConfigMapper.toDto(serviceConfig);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServiceConfigMockMvc.perform(put("/api/service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceConfig in the database
        List<ServiceConfig> serviceConfigList = serviceConfigRepository.findAll();
        assertThat(serviceConfigList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteServiceConfig() throws Exception {
        // Initialize the database
        serviceConfigRepository.saveAndFlush(serviceConfig);
        serviceConfigSearchRepository.save(serviceConfig);
        int databaseSizeBeforeDelete = serviceConfigRepository.findAll().size();

        // Get the serviceConfig
        restServiceConfigMockMvc.perform(delete("/api/service-configs/{id}", serviceConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean serviceConfigExistsInEs = serviceConfigSearchRepository.exists(serviceConfig.getId());
        assertThat(serviceConfigExistsInEs).isFalse();

        // Validate the database is empty
        List<ServiceConfig> serviceConfigList = serviceConfigRepository.findAll();
        assertThat(serviceConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchServiceConfig() throws Exception {
        // Initialize the database
        serviceConfigRepository.saveAndFlush(serviceConfig);
        serviceConfigSearchRepository.save(serviceConfig);

        // Search the serviceConfig
        restServiceConfigMockMvc.perform(get("/api/_search/service-configs?query=id:" + serviceConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceConfig.class);
        ServiceConfig serviceConfig1 = new ServiceConfig();
        serviceConfig1.setId(1L);
        ServiceConfig serviceConfig2 = new ServiceConfig();
        serviceConfig2.setId(serviceConfig1.getId());
        assertThat(serviceConfig1).isEqualTo(serviceConfig2);
        serviceConfig2.setId(2L);
        assertThat(serviceConfig1).isNotEqualTo(serviceConfig2);
        serviceConfig1.setId(null);
        assertThat(serviceConfig1).isNotEqualTo(serviceConfig2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceConfigDTO.class);
        ServiceConfigDTO serviceConfigDTO1 = new ServiceConfigDTO();
        serviceConfigDTO1.setId(1L);
        ServiceConfigDTO serviceConfigDTO2 = new ServiceConfigDTO();
        assertThat(serviceConfigDTO1).isNotEqualTo(serviceConfigDTO2);
        serviceConfigDTO2.setId(serviceConfigDTO1.getId());
        assertThat(serviceConfigDTO1).isEqualTo(serviceConfigDTO2);
        serviceConfigDTO2.setId(2L);
        assertThat(serviceConfigDTO1).isNotEqualTo(serviceConfigDTO2);
        serviceConfigDTO1.setId(null);
        assertThat(serviceConfigDTO1).isNotEqualTo(serviceConfigDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceConfigMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceConfigMapper.fromId(null)).isNull();
    }
}
