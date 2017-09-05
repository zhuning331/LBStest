package com.gorilla.myapp.web.rest;

import com.gorilla.myapp.LbStestApp;

import com.gorilla.myapp.domain.Node;
import com.gorilla.myapp.repository.NodeRepository;
import com.gorilla.myapp.service.NodeService;
import com.gorilla.myapp.repository.search.NodeSearchRepository;
import com.gorilla.myapp.service.dto.NodeDTO;
import com.gorilla.myapp.service.mapper.NodeMapper;
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
 * Test class for the NodeResource REST controller.
 *
 * @see NodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LbStestApp.class)
public class NodeResourceIntTest {

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_ALTITUDE = 1D;
    private static final Double UPDATED_ALTITUDE = 2D;

    private static final Long DEFAULT_POI_ID = 1L;
    private static final Long UPDATED_POI_ID = 2L;

    private static final Long DEFAULT_LAYER_ID = 1L;
    private static final Long UPDATED_LAYER_ID = 2L;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private NodeSearchRepository nodeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNodeMockMvc;

    private Node node;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NodeResource nodeResource = new NodeResource(nodeService);
        this.restNodeMockMvc = MockMvcBuilders.standaloneSetup(nodeResource)
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
    public static Node createEntity(EntityManager em) {
        Node node = new Node()
            .order(DEFAULT_ORDER)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .altitude(DEFAULT_ALTITUDE)
            .poiId(DEFAULT_POI_ID)
            .layerId(DEFAULT_LAYER_ID)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .deleteTime(DEFAULT_DELETE_TIME);
        return node;
    }

    @Before
    public void initTest() {
        nodeSearchRepository.deleteAll();
        node = createEntity(em);
    }

    @Test
    @Transactional
    public void createNode() throws Exception {
        int databaseSizeBeforeCreate = nodeRepository.findAll().size();

        // Create the Node
        NodeDTO nodeDTO = nodeMapper.toDto(node);
        restNodeMockMvc.perform(post("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeDTO)))
            .andExpect(status().isCreated());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeCreate + 1);
        Node testNode = nodeList.get(nodeList.size() - 1);
        assertThat(testNode.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testNode.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testNode.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testNode.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testNode.getPoiId()).isEqualTo(DEFAULT_POI_ID);
        assertThat(testNode.getLayerId()).isEqualTo(DEFAULT_LAYER_ID);
        assertThat(testNode.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testNode.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testNode.getDeleteTime()).isEqualTo(DEFAULT_DELETE_TIME);

        // Validate the Node in Elasticsearch
        Node nodeEs = nodeSearchRepository.findOne(testNode.getId());
        assertThat(nodeEs).isEqualToComparingFieldByField(testNode);
    }

    @Test
    @Transactional
    public void createNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nodeRepository.findAll().size();

        // Create the Node with an existing ID
        node.setId(1L);
        NodeDTO nodeDTO = nodeMapper.toDto(node);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNodeMockMvc.perform(post("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNodes() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);

        // Get all the nodeList
        restNodeMockMvc.perform(get("/api/nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(node.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].poiId").value(hasItem(DEFAULT_POI_ID.intValue())))
            .andExpect(jsonPath("$.[*].layerId").value(hasItem(DEFAULT_LAYER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void getNode() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);

        // Get the node
        restNodeMockMvc.perform(get("/api/nodes/{id}", node.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(node.getId().intValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE.doubleValue()))
            .andExpect(jsonPath("$.poiId").value(DEFAULT_POI_ID.intValue()))
            .andExpect(jsonPath("$.layerId").value(DEFAULT_LAYER_ID.intValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.deleteTime").value(sameInstant(DEFAULT_DELETE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingNode() throws Exception {
        // Get the node
        restNodeMockMvc.perform(get("/api/nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNode() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);
        nodeSearchRepository.save(node);
        int databaseSizeBeforeUpdate = nodeRepository.findAll().size();

        // Update the node
        Node updatedNode = nodeRepository.findOne(node.getId());
        updatedNode
            .order(UPDATED_ORDER)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .altitude(UPDATED_ALTITUDE)
            .poiId(UPDATED_POI_ID)
            .layerId(UPDATED_LAYER_ID)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .deleteTime(UPDATED_DELETE_TIME);
        NodeDTO nodeDTO = nodeMapper.toDto(updatedNode);

        restNodeMockMvc.perform(put("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeDTO)))
            .andExpect(status().isOk());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeUpdate);
        Node testNode = nodeList.get(nodeList.size() - 1);
        assertThat(testNode.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testNode.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testNode.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testNode.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testNode.getPoiId()).isEqualTo(UPDATED_POI_ID);
        assertThat(testNode.getLayerId()).isEqualTo(UPDATED_LAYER_ID);
        assertThat(testNode.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testNode.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testNode.getDeleteTime()).isEqualTo(UPDATED_DELETE_TIME);

        // Validate the Node in Elasticsearch
        Node nodeEs = nodeSearchRepository.findOne(testNode.getId());
        assertThat(nodeEs).isEqualToComparingFieldByField(testNode);
    }

    @Test
    @Transactional
    public void updateNonExistingNode() throws Exception {
        int databaseSizeBeforeUpdate = nodeRepository.findAll().size();

        // Create the Node
        NodeDTO nodeDTO = nodeMapper.toDto(node);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNodeMockMvc.perform(put("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeDTO)))
            .andExpect(status().isCreated());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNode() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);
        nodeSearchRepository.save(node);
        int databaseSizeBeforeDelete = nodeRepository.findAll().size();

        // Get the node
        restNodeMockMvc.perform(delete("/api/nodes/{id}", node.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean nodeExistsInEs = nodeSearchRepository.exists(node.getId());
        assertThat(nodeExistsInEs).isFalse();

        // Validate the database is empty
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNode() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);
        nodeSearchRepository.save(node);

        // Search the node
        restNodeMockMvc.perform(get("/api/_search/nodes?query=id:" + node.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(node.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].poiId").value(hasItem(DEFAULT_POI_ID.intValue())))
            .andExpect(jsonPath("$.[*].layerId").value(hasItem(DEFAULT_LAYER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].deleteTime").value(hasItem(sameInstant(DEFAULT_DELETE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Node.class);
        Node node1 = new Node();
        node1.setId(1L);
        Node node2 = new Node();
        node2.setId(node1.getId());
        assertThat(node1).isEqualTo(node2);
        node2.setId(2L);
        assertThat(node1).isNotEqualTo(node2);
        node1.setId(null);
        assertThat(node1).isNotEqualTo(node2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NodeDTO.class);
        NodeDTO nodeDTO1 = new NodeDTO();
        nodeDTO1.setId(1L);
        NodeDTO nodeDTO2 = new NodeDTO();
        assertThat(nodeDTO1).isNotEqualTo(nodeDTO2);
        nodeDTO2.setId(nodeDTO1.getId());
        assertThat(nodeDTO1).isEqualTo(nodeDTO2);
        nodeDTO2.setId(2L);
        assertThat(nodeDTO1).isNotEqualTo(nodeDTO2);
        nodeDTO1.setId(null);
        assertThat(nodeDTO1).isNotEqualTo(nodeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(nodeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(nodeMapper.fromId(null)).isNull();
    }
}
