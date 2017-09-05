package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.BindingPOI;
import com.gorilla.myapp.repository.BindingPOIRepository;
import com.gorilla.myapp.service.BindingPOIService;
import com.gorilla.myapp.repository.search.BindingPOISearchRepository;
import com.gorilla.myapp.service.dto.BindingPOIDTO;
import com.gorilla.myapp.service.mapper.BindingPOIMapper;
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
 * Test class for the BindingPOIResource REST controller.
 *
 * @see BindingPOIResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class BindingPOIResourceIntTest {

    private static final String DEFAULT_BINDING_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BINDING_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_BINDING_KEY = "AAAAAAAAAA";
    private static final String UPDATED_BINDING_KEY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BindingPOIRepository bindingPOIRepository;

    @Autowired
    private BindingPOIMapper bindingPOIMapper;

    @Autowired
    private BindingPOIService bindingPOIService;

    @Autowired
    private BindingPOISearchRepository bindingPOISearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBindingPOIMockMvc;

    private BindingPOI bindingPOI;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BindingPOIResource bindingPOIResource = new BindingPOIResource(bindingPOIService);
        this.restBindingPOIMockMvc = MockMvcBuilders.standaloneSetup(bindingPOIResource)
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
    public static BindingPOI createEntity(EntityManager em) {
        BindingPOI bindingPOI = new BindingPOI()
            .bindingType(DEFAULT_BINDING_TYPE)
            .bindingKey(DEFAULT_BINDING_KEY)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return bindingPOI;
    }

    @Before
    public void initTest() {
        bindingPOISearchRepository.deleteAll();
        bindingPOI = createEntity(em);
    }

    @Test
    @Transactional
    public void createBindingPOI() throws Exception {
        int databaseSizeBeforeCreate = bindingPOIRepository.findAll().size();

        // Create the BindingPOI
        BindingPOIDTO bindingPOIDTO = bindingPOIMapper.toDto(bindingPOI);
        restBindingPOIMockMvc.perform(post("/api/binding-pois")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bindingPOIDTO)))
            .andExpect(status().isCreated());

        // Validate the BindingPOI in the database
        List<BindingPOI> bindingPOIList = bindingPOIRepository.findAll();
        assertThat(bindingPOIList).hasSize(databaseSizeBeforeCreate + 1);
        BindingPOI testBindingPOI = bindingPOIList.get(bindingPOIList.size() - 1);
        assertThat(testBindingPOI.getBindingType()).isEqualTo(DEFAULT_BINDING_TYPE);
        assertThat(testBindingPOI.getBindingKey()).isEqualTo(DEFAULT_BINDING_KEY);
        assertThat(testBindingPOI.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBindingPOI.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testBindingPOI.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the BindingPOI in Elasticsearch
        BindingPOI bindingPOIEs = bindingPOISearchRepository.findOne(testBindingPOI.getId());
        assertThat(bindingPOIEs).isEqualToComparingFieldByField(testBindingPOI);
    }

    @Test
    @Transactional
    public void createBindingPOIWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bindingPOIRepository.findAll().size();

        // Create the BindingPOI with an existing ID
        bindingPOI.setId(1L);
        BindingPOIDTO bindingPOIDTO = bindingPOIMapper.toDto(bindingPOI);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBindingPOIMockMvc.perform(post("/api/binding-pois")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bindingPOIDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BindingPOI> bindingPOIList = bindingPOIRepository.findAll();
        assertThat(bindingPOIList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBindingPOIS() throws Exception {
        // Initialize the database
        bindingPOIRepository.saveAndFlush(bindingPOI);

        // Get all the bindingPOIList
        restBindingPOIMockMvc.perform(get("/api/binding-pois?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bindingPOI.getId().intValue())))
            .andExpect(jsonPath("$.[*].bindingType").value(hasItem(DEFAULT_BINDING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].bindingKey").value(hasItem(DEFAULT_BINDING_KEY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getBindingPOI() throws Exception {
        // Initialize the database
        bindingPOIRepository.saveAndFlush(bindingPOI);

        // Get the bindingPOI
        restBindingPOIMockMvc.perform(get("/api/binding-pois/{id}", bindingPOI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bindingPOI.getId().intValue()))
            .andExpect(jsonPath("$.bindingType").value(DEFAULT_BINDING_TYPE.toString()))
            .andExpect(jsonPath("$.bindingKey").value(DEFAULT_BINDING_KEY.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBindingPOI() throws Exception {
        // Get the bindingPOI
        restBindingPOIMockMvc.perform(get("/api/binding-pois/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBindingPOI() throws Exception {
        // Initialize the database
        bindingPOIRepository.saveAndFlush(bindingPOI);
        bindingPOISearchRepository.save(bindingPOI);
        int databaseSizeBeforeUpdate = bindingPOIRepository.findAll().size();

        // Update the bindingPOI
        BindingPOI updatedBindingPOI = bindingPOIRepository.findOne(bindingPOI.getId());
        updatedBindingPOI
            .bindingType(UPDATED_BINDING_TYPE)
            .bindingKey(UPDATED_BINDING_KEY)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        BindingPOIDTO bindingPOIDTO = bindingPOIMapper.toDto(updatedBindingPOI);

        restBindingPOIMockMvc.perform(put("/api/binding-pois")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bindingPOIDTO)))
            .andExpect(status().isOk());

        // Validate the BindingPOI in the database
        List<BindingPOI> bindingPOIList = bindingPOIRepository.findAll();
        assertThat(bindingPOIList).hasSize(databaseSizeBeforeUpdate);
        BindingPOI testBindingPOI = bindingPOIList.get(bindingPOIList.size() - 1);
        assertThat(testBindingPOI.getBindingType()).isEqualTo(UPDATED_BINDING_TYPE);
        assertThat(testBindingPOI.getBindingKey()).isEqualTo(UPDATED_BINDING_KEY);
        assertThat(testBindingPOI.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBindingPOI.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testBindingPOI.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the BindingPOI in Elasticsearch
        BindingPOI bindingPOIEs = bindingPOISearchRepository.findOne(testBindingPOI.getId());
        assertThat(bindingPOIEs).isEqualToComparingFieldByField(testBindingPOI);
    }

    @Test
    @Transactional
    public void updateNonExistingBindingPOI() throws Exception {
        int databaseSizeBeforeUpdate = bindingPOIRepository.findAll().size();

        // Create the BindingPOI
        BindingPOIDTO bindingPOIDTO = bindingPOIMapper.toDto(bindingPOI);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBindingPOIMockMvc.perform(put("/api/binding-pois")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bindingPOIDTO)))
            .andExpect(status().isCreated());

        // Validate the BindingPOI in the database
        List<BindingPOI> bindingPOIList = bindingPOIRepository.findAll();
        assertThat(bindingPOIList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBindingPOI() throws Exception {
        // Initialize the database
        bindingPOIRepository.saveAndFlush(bindingPOI);
        bindingPOISearchRepository.save(bindingPOI);
        int databaseSizeBeforeDelete = bindingPOIRepository.findAll().size();

        // Get the bindingPOI
        restBindingPOIMockMvc.perform(delete("/api/binding-pois/{id}", bindingPOI.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bindingPOIExistsInEs = bindingPOISearchRepository.exists(bindingPOI.getId());
        assertThat(bindingPOIExistsInEs).isFalse();

        // Validate the database is empty
        List<BindingPOI> bindingPOIList = bindingPOIRepository.findAll();
        assertThat(bindingPOIList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBindingPOI() throws Exception {
        // Initialize the database
        bindingPOIRepository.saveAndFlush(bindingPOI);
        bindingPOISearchRepository.save(bindingPOI);

        // Search the bindingPOI
        restBindingPOIMockMvc.perform(get("/api/_search/binding-pois?query=id:" + bindingPOI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bindingPOI.getId().intValue())))
            .andExpect(jsonPath("$.[*].bindingType").value(hasItem(DEFAULT_BINDING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].bindingKey").value(hasItem(DEFAULT_BINDING_KEY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BindingPOI.class);
        BindingPOI bindingPOI1 = new BindingPOI();
        bindingPOI1.setId(1L);
        BindingPOI bindingPOI2 = new BindingPOI();
        bindingPOI2.setId(bindingPOI1.getId());
        assertThat(bindingPOI1).isEqualTo(bindingPOI2);
        bindingPOI2.setId(2L);
        assertThat(bindingPOI1).isNotEqualTo(bindingPOI2);
        bindingPOI1.setId(null);
        assertThat(bindingPOI1).isNotEqualTo(bindingPOI2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BindingPOIDTO.class);
        BindingPOIDTO bindingPOIDTO1 = new BindingPOIDTO();
        bindingPOIDTO1.setId(1L);
        BindingPOIDTO bindingPOIDTO2 = new BindingPOIDTO();
        assertThat(bindingPOIDTO1).isNotEqualTo(bindingPOIDTO2);
        bindingPOIDTO2.setId(bindingPOIDTO1.getId());
        assertThat(bindingPOIDTO1).isEqualTo(bindingPOIDTO2);
        bindingPOIDTO2.setId(2L);
        assertThat(bindingPOIDTO1).isNotEqualTo(bindingPOIDTO2);
        bindingPOIDTO1.setId(null);
        assertThat(bindingPOIDTO1).isNotEqualTo(bindingPOIDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bindingPOIMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bindingPOIMapper.fromId(null)).isNull();
    }
}
