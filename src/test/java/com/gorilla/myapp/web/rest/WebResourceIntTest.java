package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.Web;
import com.gorilla.myapp.repository.WebRepository;
import com.gorilla.myapp.service.WebService;
import com.gorilla.myapp.repository.search.WebSearchRepository;
import com.gorilla.myapp.service.dto.WebDTO;
import com.gorilla.myapp.service.mapper.WebMapper;
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
 * Test class for the WebResource REST controller.
 *
 * @see WebResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class WebResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private WebRepository webRepository;

    @Autowired
    private WebMapper webMapper;

    @Autowired
    private WebService webService;

    @Autowired
    private WebSearchRepository webSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWebMockMvc;

    private Web web;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WebResource webResource = new WebResource(webService);
        this.restWebMockMvc = MockMvcBuilders.standaloneSetup(webResource)
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
    public static Web createEntity(EntityManager em) {
        Web web = new Web()
            .name(DEFAULT_NAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return web;
    }

    @Before
    public void initTest() {
        webSearchRepository.deleteAll();
        web = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeb() throws Exception {
        int databaseSizeBeforeCreate = webRepository.findAll().size();

        // Create the Web
        WebDTO webDTO = webMapper.toDto(web);
        restWebMockMvc.perform(post("/api/webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webDTO)))
            .andExpect(status().isCreated());

        // Validate the Web in the database
        List<Web> webList = webRepository.findAll();
        assertThat(webList).hasSize(databaseSizeBeforeCreate + 1);
        Web testWeb = webList.get(webList.size() - 1);
        assertThat(testWeb.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWeb.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testWeb.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testWeb.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the Web in Elasticsearch
        Web webEs = webSearchRepository.findOne(testWeb.getId());
        assertThat(webEs).isEqualToComparingFieldByField(testWeb);
    }

    @Test
    @Transactional
    public void createWebWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webRepository.findAll().size();

        // Create the Web with an existing ID
        web.setId(1L);
        WebDTO webDTO = webMapper.toDto(web);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebMockMvc.perform(post("/api/webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Web> webList = webRepository.findAll();
        assertThat(webList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWebs() throws Exception {
        // Initialize the database
        webRepository.saveAndFlush(web);

        // Get all the webList
        restWebMockMvc.perform(get("/api/webs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(web.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getWeb() throws Exception {
        // Initialize the database
        webRepository.saveAndFlush(web);

        // Get the web
        restWebMockMvc.perform(get("/api/webs/{id}", web.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(web.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingWeb() throws Exception {
        // Get the web
        restWebMockMvc.perform(get("/api/webs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeb() throws Exception {
        // Initialize the database
        webRepository.saveAndFlush(web);
        webSearchRepository.save(web);
        int databaseSizeBeforeUpdate = webRepository.findAll().size();

        // Update the web
        Web updatedWeb = webRepository.findOne(web.getId());
        updatedWeb
            .name(UPDATED_NAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        WebDTO webDTO = webMapper.toDto(updatedWeb);

        restWebMockMvc.perform(put("/api/webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webDTO)))
            .andExpect(status().isOk());

        // Validate the Web in the database
        List<Web> webList = webRepository.findAll();
        assertThat(webList).hasSize(databaseSizeBeforeUpdate);
        Web testWeb = webList.get(webList.size() - 1);
        assertThat(testWeb.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWeb.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testWeb.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testWeb.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the Web in Elasticsearch
        Web webEs = webSearchRepository.findOne(testWeb.getId());
        assertThat(webEs).isEqualToComparingFieldByField(testWeb);
    }

    @Test
    @Transactional
    public void updateNonExistingWeb() throws Exception {
        int databaseSizeBeforeUpdate = webRepository.findAll().size();

        // Create the Web
        WebDTO webDTO = webMapper.toDto(web);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWebMockMvc.perform(put("/api/webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webDTO)))
            .andExpect(status().isCreated());

        // Validate the Web in the database
        List<Web> webList = webRepository.findAll();
        assertThat(webList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWeb() throws Exception {
        // Initialize the database
        webRepository.saveAndFlush(web);
        webSearchRepository.save(web);
        int databaseSizeBeforeDelete = webRepository.findAll().size();

        // Get the web
        restWebMockMvc.perform(delete("/api/webs/{id}", web.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean webExistsInEs = webSearchRepository.exists(web.getId());
        assertThat(webExistsInEs).isFalse();

        // Validate the database is empty
        List<Web> webList = webRepository.findAll();
        assertThat(webList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWeb() throws Exception {
        // Initialize the database
        webRepository.saveAndFlush(web);
        webSearchRepository.save(web);

        // Search the web
        restWebMockMvc.perform(get("/api/_search/webs?query=id:" + web.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(web.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Web.class);
        Web web1 = new Web();
        web1.setId(1L);
        Web web2 = new Web();
        web2.setId(web1.getId());
        assertThat(web1).isEqualTo(web2);
        web2.setId(2L);
        assertThat(web1).isNotEqualTo(web2);
        web1.setId(null);
        assertThat(web1).isNotEqualTo(web2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebDTO.class);
        WebDTO webDTO1 = new WebDTO();
        webDTO1.setId(1L);
        WebDTO webDTO2 = new WebDTO();
        assertThat(webDTO1).isNotEqualTo(webDTO2);
        webDTO2.setId(webDTO1.getId());
        assertThat(webDTO1).isEqualTo(webDTO2);
        webDTO2.setId(2L);
        assertThat(webDTO1).isNotEqualTo(webDTO2);
        webDTO1.setId(null);
        assertThat(webDTO1).isNotEqualTo(webDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(webMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(webMapper.fromId(null)).isNull();
    }
}
