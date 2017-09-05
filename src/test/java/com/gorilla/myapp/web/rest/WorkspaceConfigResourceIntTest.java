package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.WorkspaceConfig;
import com.gorilla.myapp.repository.WorkspaceConfigRepository;
import com.gorilla.myapp.service.WorkspaceConfigService;
import com.gorilla.myapp.repository.search.WorkspaceConfigSearchRepository;
import com.gorilla.myapp.service.dto.WorkspaceConfigDTO;
import com.gorilla.myapp.service.mapper.WorkspaceConfigMapper;
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
 * Test class for the WorkspaceConfigResource REST controller.
 *
 * @see WorkspaceConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class WorkspaceConfigResourceIntTest {

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
    private WorkspaceConfigRepository workspaceConfigRepository;

    @Autowired
    private WorkspaceConfigMapper workspaceConfigMapper;

    @Autowired
    private WorkspaceConfigService workspaceConfigService;

    @Autowired
    private WorkspaceConfigSearchRepository workspaceConfigSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkspaceConfigMockMvc;

    private WorkspaceConfig workspaceConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkspaceConfigResource workspaceConfigResource = new WorkspaceConfigResource(workspaceConfigService);
        this.restWorkspaceConfigMockMvc = MockMvcBuilders.standaloneSetup(workspaceConfigResource)
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
    public static WorkspaceConfig createEntity(EntityManager em) {
        WorkspaceConfig workspaceConfig = new WorkspaceConfig()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return workspaceConfig;
    }

    @Before
    public void initTest() {
        workspaceConfigSearchRepository.deleteAll();
        workspaceConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkspaceConfig() throws Exception {
        int databaseSizeBeforeCreate = workspaceConfigRepository.findAll().size();

        // Create the WorkspaceConfig
        WorkspaceConfigDTO workspaceConfigDTO = workspaceConfigMapper.toDto(workspaceConfig);
        restWorkspaceConfigMockMvc.perform(post("/api/workspace-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkspaceConfig in the database
        List<WorkspaceConfig> workspaceConfigList = workspaceConfigRepository.findAll();
        assertThat(workspaceConfigList).hasSize(databaseSizeBeforeCreate + 1);
        WorkspaceConfig testWorkspaceConfig = workspaceConfigList.get(workspaceConfigList.size() - 1);
        assertThat(testWorkspaceConfig.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testWorkspaceConfig.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testWorkspaceConfig.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testWorkspaceConfig.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testWorkspaceConfig.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the WorkspaceConfig in Elasticsearch
        WorkspaceConfig workspaceConfigEs = workspaceConfigSearchRepository.findOne(testWorkspaceConfig.getId());
        assertThat(workspaceConfigEs).isEqualToComparingFieldByField(testWorkspaceConfig);
    }

    @Test
    @Transactional
    public void createWorkspaceConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workspaceConfigRepository.findAll().size();

        // Create the WorkspaceConfig with an existing ID
        workspaceConfig.setId(1L);
        WorkspaceConfigDTO workspaceConfigDTO = workspaceConfigMapper.toDto(workspaceConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkspaceConfigMockMvc.perform(post("/api/workspace-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WorkspaceConfig> workspaceConfigList = workspaceConfigRepository.findAll();
        assertThat(workspaceConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWorkspaceConfigs() throws Exception {
        // Initialize the database
        workspaceConfigRepository.saveAndFlush(workspaceConfig);

        // Get all the workspaceConfigList
        restWorkspaceConfigMockMvc.perform(get("/api/workspace-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workspaceConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getWorkspaceConfig() throws Exception {
        // Initialize the database
        workspaceConfigRepository.saveAndFlush(workspaceConfig);

        // Get the workspaceConfig
        restWorkspaceConfigMockMvc.perform(get("/api/workspace-configs/{id}", workspaceConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workspaceConfig.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingWorkspaceConfig() throws Exception {
        // Get the workspaceConfig
        restWorkspaceConfigMockMvc.perform(get("/api/workspace-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkspaceConfig() throws Exception {
        // Initialize the database
        workspaceConfigRepository.saveAndFlush(workspaceConfig);
        workspaceConfigSearchRepository.save(workspaceConfig);
        int databaseSizeBeforeUpdate = workspaceConfigRepository.findAll().size();

        // Update the workspaceConfig
        WorkspaceConfig updatedWorkspaceConfig = workspaceConfigRepository.findOne(workspaceConfig.getId());
        updatedWorkspaceConfig
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        WorkspaceConfigDTO workspaceConfigDTO = workspaceConfigMapper.toDto(updatedWorkspaceConfig);

        restWorkspaceConfigMockMvc.perform(put("/api/workspace-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceConfigDTO)))
            .andExpect(status().isOk());

        // Validate the WorkspaceConfig in the database
        List<WorkspaceConfig> workspaceConfigList = workspaceConfigRepository.findAll();
        assertThat(workspaceConfigList).hasSize(databaseSizeBeforeUpdate);
        WorkspaceConfig testWorkspaceConfig = workspaceConfigList.get(workspaceConfigList.size() - 1);
        assertThat(testWorkspaceConfig.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testWorkspaceConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testWorkspaceConfig.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testWorkspaceConfig.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testWorkspaceConfig.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the WorkspaceConfig in Elasticsearch
        WorkspaceConfig workspaceConfigEs = workspaceConfigSearchRepository.findOne(testWorkspaceConfig.getId());
        assertThat(workspaceConfigEs).isEqualToComparingFieldByField(testWorkspaceConfig);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkspaceConfig() throws Exception {
        int databaseSizeBeforeUpdate = workspaceConfigRepository.findAll().size();

        // Create the WorkspaceConfig
        WorkspaceConfigDTO workspaceConfigDTO = workspaceConfigMapper.toDto(workspaceConfig);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkspaceConfigMockMvc.perform(put("/api/workspace-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkspaceConfig in the database
        List<WorkspaceConfig> workspaceConfigList = workspaceConfigRepository.findAll();
        assertThat(workspaceConfigList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkspaceConfig() throws Exception {
        // Initialize the database
        workspaceConfigRepository.saveAndFlush(workspaceConfig);
        workspaceConfigSearchRepository.save(workspaceConfig);
        int databaseSizeBeforeDelete = workspaceConfigRepository.findAll().size();

        // Get the workspaceConfig
        restWorkspaceConfigMockMvc.perform(delete("/api/workspace-configs/{id}", workspaceConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean workspaceConfigExistsInEs = workspaceConfigSearchRepository.exists(workspaceConfig.getId());
        assertThat(workspaceConfigExistsInEs).isFalse();

        // Validate the database is empty
        List<WorkspaceConfig> workspaceConfigList = workspaceConfigRepository.findAll();
        assertThat(workspaceConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWorkspaceConfig() throws Exception {
        // Initialize the database
        workspaceConfigRepository.saveAndFlush(workspaceConfig);
        workspaceConfigSearchRepository.save(workspaceConfig);

        // Search the workspaceConfig
        restWorkspaceConfigMockMvc.perform(get("/api/_search/workspace-configs?query=id:" + workspaceConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workspaceConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkspaceConfig.class);
        WorkspaceConfig workspaceConfig1 = new WorkspaceConfig();
        workspaceConfig1.setId(1L);
        WorkspaceConfig workspaceConfig2 = new WorkspaceConfig();
        workspaceConfig2.setId(workspaceConfig1.getId());
        assertThat(workspaceConfig1).isEqualTo(workspaceConfig2);
        workspaceConfig2.setId(2L);
        assertThat(workspaceConfig1).isNotEqualTo(workspaceConfig2);
        workspaceConfig1.setId(null);
        assertThat(workspaceConfig1).isNotEqualTo(workspaceConfig2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkspaceConfigDTO.class);
        WorkspaceConfigDTO workspaceConfigDTO1 = new WorkspaceConfigDTO();
        workspaceConfigDTO1.setId(1L);
        WorkspaceConfigDTO workspaceConfigDTO2 = new WorkspaceConfigDTO();
        assertThat(workspaceConfigDTO1).isNotEqualTo(workspaceConfigDTO2);
        workspaceConfigDTO2.setId(workspaceConfigDTO1.getId());
        assertThat(workspaceConfigDTO1).isEqualTo(workspaceConfigDTO2);
        workspaceConfigDTO2.setId(2L);
        assertThat(workspaceConfigDTO1).isNotEqualTo(workspaceConfigDTO2);
        workspaceConfigDTO1.setId(null);
        assertThat(workspaceConfigDTO1).isNotEqualTo(workspaceConfigDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(workspaceConfigMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(workspaceConfigMapper.fromId(null)).isNull();
    }
}
