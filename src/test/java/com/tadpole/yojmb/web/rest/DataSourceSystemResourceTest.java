package com.tadpole.yojmb.web.rest;

import com.tadpole.yojmb.Application;
import com.tadpole.yojmb.domain.DataSourceSystem;
import com.tadpole.yojmb.repository.DataSourceSystemRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DataSourceSystemResource REST controller.
 *
 * @see DataSourceSystemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DataSourceSystemResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_IDENTITY = "SAMPLE_TEXT";
    private static final String UPDATED_IDENTITY = "UPDATED_TEXT";
    private static final String DEFAULT_LOGIN_PAGE_URL = "SAMPLE_TEXT";
    private static final String UPDATED_LOGIN_PAGE_URL = "UPDATED_TEXT";
    private static final String DEFAULT_QUERY_PAGE_URL = "SAMPLE_TEXT";
    private static final String UPDATED_QUERY_PAGE_URL = "UPDATED_TEXT";
    private static final String DEFAULT_QUERY_PARAMETER1 = "SAMPLE_TEXT";
    private static final String UPDATED_QUERY_PARAMETER1 = "UPDATED_TEXT";
    private static final String DEFAULT_QUERY_PARAMETER2 = "SAMPLE_TEXT";
    private static final String UPDATED_QUERY_PARAMETER2 = "UPDATED_TEXT";
    private static final String DEFAULT_QUERY_PARAMETER3 = "SAMPLE_TEXT";
    private static final String UPDATED_QUERY_PARAMETER3 = "UPDATED_TEXT";
    private static final String DEFAULT_SINGLE_DETAIL_URL = "SAMPLE_TEXT";
    private static final String UPDATED_SINGLE_DETAIL_URL = "UPDATED_TEXT";

    @Inject
    private DataSourceSystemRepository dataSourceSystemRepository;

    private MockMvc restDataSourceSystemMockMvc;

    private DataSourceSystem dataSourceSystem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DataSourceSystemResource dataSourceSystemResource = new DataSourceSystemResource();
        ReflectionTestUtils.setField(dataSourceSystemResource, "dataSourceSystemRepository", dataSourceSystemRepository);
        this.restDataSourceSystemMockMvc = MockMvcBuilders.standaloneSetup(dataSourceSystemResource).build();
    }

    @Before
    public void initTest() {
        dataSourceSystem = new DataSourceSystem();
        dataSourceSystem.setName(DEFAULT_NAME);
        dataSourceSystem.setIdentity(DEFAULT_IDENTITY);
        dataSourceSystem.setLoginPageUrl(DEFAULT_LOGIN_PAGE_URL);
        dataSourceSystem.setQueryPageUrl(DEFAULT_QUERY_PAGE_URL);
        dataSourceSystem.setQueryParameter1(DEFAULT_QUERY_PARAMETER1);
        dataSourceSystem.setQueryParameter2(DEFAULT_QUERY_PARAMETER2);
        dataSourceSystem.setQueryParameter3(DEFAULT_QUERY_PARAMETER3);
        dataSourceSystem.setSingleDetailUrl(DEFAULT_SINGLE_DETAIL_URL);
    }

    @Test
    @Transactional
    public void createDataSourceSystem() throws Exception {
        int databaseSizeBeforeCreate = dataSourceSystemRepository.findAll().size();

        // Create the DataSourceSystem
        restDataSourceSystemMockMvc.perform(post("/api/dataSourceSystems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dataSourceSystem)))
                .andExpect(status().isCreated());

        // Validate the DataSourceSystem in the database
        List<DataSourceSystem> dataSourceSystems = dataSourceSystemRepository.findAll();
        assertThat(dataSourceSystems).hasSize(databaseSizeBeforeCreate + 1);
        DataSourceSystem testDataSourceSystem = dataSourceSystems.get(dataSourceSystems.size() - 1);
        assertThat(testDataSourceSystem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataSourceSystem.getIdentity()).isEqualTo(DEFAULT_IDENTITY);
        assertThat(testDataSourceSystem.getLoginPageUrl()).isEqualTo(DEFAULT_LOGIN_PAGE_URL);
        assertThat(testDataSourceSystem.getQueryPageUrl()).isEqualTo(DEFAULT_QUERY_PAGE_URL);
        assertThat(testDataSourceSystem.getQueryParameter1()).isEqualTo(DEFAULT_QUERY_PARAMETER1);
        assertThat(testDataSourceSystem.getQueryParameter2()).isEqualTo(DEFAULT_QUERY_PARAMETER2);
        assertThat(testDataSourceSystem.getQueryParameter3()).isEqualTo(DEFAULT_QUERY_PARAMETER3);
        assertThat(testDataSourceSystem.getSingleDetailUrl()).isEqualTo(DEFAULT_SINGLE_DETAIL_URL);
    }

    @Test
    @Transactional
    public void getAllDataSourceSystems() throws Exception {
        // Initialize the database
        dataSourceSystemRepository.saveAndFlush(dataSourceSystem);

        // Get all the dataSourceSystems
        restDataSourceSystemMockMvc.perform(get("/api/dataSourceSystems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dataSourceSystem.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].identity").value(hasItem(DEFAULT_IDENTITY.toString())))
                .andExpect(jsonPath("$.[*].loginPageUrl").value(hasItem(DEFAULT_LOGIN_PAGE_URL.toString())))
                .andExpect(jsonPath("$.[*].queryPageUrl").value(hasItem(DEFAULT_QUERY_PAGE_URL.toString())))
                .andExpect(jsonPath("$.[*].queryParameter1").value(hasItem(DEFAULT_QUERY_PARAMETER1.toString())))
                .andExpect(jsonPath("$.[*].queryParameter2").value(hasItem(DEFAULT_QUERY_PARAMETER2.toString())))
                .andExpect(jsonPath("$.[*].queryParameter3").value(hasItem(DEFAULT_QUERY_PARAMETER3.toString())))
                .andExpect(jsonPath("$.[*].singleDetailUrl").value(hasItem(DEFAULT_SINGLE_DETAIL_URL.toString())));
    }

    @Test
    @Transactional
    public void getDataSourceSystem() throws Exception {
        // Initialize the database
        dataSourceSystemRepository.saveAndFlush(dataSourceSystem);

        // Get the dataSourceSystem
        restDataSourceSystemMockMvc.perform(get("/api/dataSourceSystems/{id}", dataSourceSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dataSourceSystem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.identity").value(DEFAULT_IDENTITY.toString()))
            .andExpect(jsonPath("$.loginPageUrl").value(DEFAULT_LOGIN_PAGE_URL.toString()))
            .andExpect(jsonPath("$.queryPageUrl").value(DEFAULT_QUERY_PAGE_URL.toString()))
            .andExpect(jsonPath("$.queryParameter1").value(DEFAULT_QUERY_PARAMETER1.toString()))
            .andExpect(jsonPath("$.queryParameter2").value(DEFAULT_QUERY_PARAMETER2.toString()))
            .andExpect(jsonPath("$.queryParameter3").value(DEFAULT_QUERY_PARAMETER3.toString()))
            .andExpect(jsonPath("$.singleDetailUrl").value(DEFAULT_SINGLE_DETAIL_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDataSourceSystem() throws Exception {
        // Get the dataSourceSystem
        restDataSourceSystemMockMvc.perform(get("/api/dataSourceSystems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataSourceSystem() throws Exception {
        // Initialize the database
        dataSourceSystemRepository.saveAndFlush(dataSourceSystem);

		int databaseSizeBeforeUpdate = dataSourceSystemRepository.findAll().size();

        // Update the dataSourceSystem
        dataSourceSystem.setName(UPDATED_NAME);
        dataSourceSystem.setIdentity(UPDATED_IDENTITY);
        dataSourceSystem.setLoginPageUrl(UPDATED_LOGIN_PAGE_URL);
        dataSourceSystem.setQueryPageUrl(UPDATED_QUERY_PAGE_URL);
        dataSourceSystem.setQueryParameter1(UPDATED_QUERY_PARAMETER1);
        dataSourceSystem.setQueryParameter2(UPDATED_QUERY_PARAMETER2);
        dataSourceSystem.setQueryParameter3(UPDATED_QUERY_PARAMETER3);
        dataSourceSystem.setSingleDetailUrl(UPDATED_SINGLE_DETAIL_URL);
        restDataSourceSystemMockMvc.perform(put("/api/dataSourceSystems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dataSourceSystem)))
                .andExpect(status().isOk());

        // Validate the DataSourceSystem in the database
        List<DataSourceSystem> dataSourceSystems = dataSourceSystemRepository.findAll();
        assertThat(dataSourceSystems).hasSize(databaseSizeBeforeUpdate);
        DataSourceSystem testDataSourceSystem = dataSourceSystems.get(dataSourceSystems.size() - 1);
        assertThat(testDataSourceSystem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataSourceSystem.getIdentity()).isEqualTo(UPDATED_IDENTITY);
        assertThat(testDataSourceSystem.getLoginPageUrl()).isEqualTo(UPDATED_LOGIN_PAGE_URL);
        assertThat(testDataSourceSystem.getQueryPageUrl()).isEqualTo(UPDATED_QUERY_PAGE_URL);
        assertThat(testDataSourceSystem.getQueryParameter1()).isEqualTo(UPDATED_QUERY_PARAMETER1);
        assertThat(testDataSourceSystem.getQueryParameter2()).isEqualTo(UPDATED_QUERY_PARAMETER2);
        assertThat(testDataSourceSystem.getQueryParameter3()).isEqualTo(UPDATED_QUERY_PARAMETER3);
        assertThat(testDataSourceSystem.getSingleDetailUrl()).isEqualTo(UPDATED_SINGLE_DETAIL_URL);
    }

    @Test
    @Transactional
    public void deleteDataSourceSystem() throws Exception {
        // Initialize the database
        dataSourceSystemRepository.saveAndFlush(dataSourceSystem);

		int databaseSizeBeforeDelete = dataSourceSystemRepository.findAll().size();

        // Get the dataSourceSystem
        restDataSourceSystemMockMvc.perform(delete("/api/dataSourceSystems/{id}", dataSourceSystem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DataSourceSystem> dataSourceSystems = dataSourceSystemRepository.findAll();
        assertThat(dataSourceSystems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
