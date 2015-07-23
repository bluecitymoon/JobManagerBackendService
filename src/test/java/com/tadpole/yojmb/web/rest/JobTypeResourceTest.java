package com.tadpole.yojmb.web.rest;

import com.tadpole.yojmb.Application;
import com.tadpole.yojmb.domain.JobType;
import com.tadpole.yojmb.repository.JobTypeRepository;

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
 * Test class for the JobTypeResource REST controller.
 *
 * @see JobTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JobTypeResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_IDENTITY = "SAMPLE_TEXT";
    private static final String UPDATED_IDENTITY = "UPDATED_TEXT";
    private static final String DEFAULT_COLUMN1 = "SAMPLE_TEXT";
    private static final String UPDATED_COLUMN1 = "UPDATED_TEXT";
    private static final String DEFAULT_VALUE1 = "SAMPLE_TEXT";
    private static final String UPDATED_VALUE1 = "UPDATED_TEXT";
    private static final String DEFAULT_COLUMN2 = "SAMPLE_TEXT";
    private static final String UPDATED_COLUMN2 = "UPDATED_TEXT";
    private static final String DEFAULT_VALUE2 = "SAMPLE_TEXT";
    private static final String UPDATED_VALUE2 = "UPDATED_TEXT";

    @Inject
    private JobTypeRepository jobTypeRepository;

    private MockMvc restJobTypeMockMvc;

    private JobType jobType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobTypeResource jobTypeResource = new JobTypeResource();
        ReflectionTestUtils.setField(jobTypeResource, "jobTypeRepository", jobTypeRepository);
        this.restJobTypeMockMvc = MockMvcBuilders.standaloneSetup(jobTypeResource).build();
    }

    @Before
    public void initTest() {
        jobType = new JobType();
        jobType.setDescription(DEFAULT_DESCRIPTION);
        jobType.setIdentity(DEFAULT_IDENTITY);
        jobType.setColumn1(DEFAULT_COLUMN1);
        jobType.setValue1(DEFAULT_VALUE1);
        jobType.setColumn2(DEFAULT_COLUMN2);
        jobType.setValue2(DEFAULT_VALUE2);
    }

    @Test
    @Transactional
    public void createJobType() throws Exception {
        int databaseSizeBeforeCreate = jobTypeRepository.findAll().size();

        // Create the JobType
        restJobTypeMockMvc.perform(post("/api/jobTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobType)))
                .andExpect(status().isCreated());

        // Validate the JobType in the database
        List<JobType> jobTypes = jobTypeRepository.findAll();
        assertThat(jobTypes).hasSize(databaseSizeBeforeCreate + 1);
        JobType testJobType = jobTypes.get(jobTypes.size() - 1);
        assertThat(testJobType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJobType.getIdentity()).isEqualTo(DEFAULT_IDENTITY);
        assertThat(testJobType.getColumn1()).isEqualTo(DEFAULT_COLUMN1);
        assertThat(testJobType.getValue1()).isEqualTo(DEFAULT_VALUE1);
        assertThat(testJobType.getColumn2()).isEqualTo(DEFAULT_COLUMN2);
        assertThat(testJobType.getValue2()).isEqualTo(DEFAULT_VALUE2);
    }

    @Test
    @Transactional
    public void getAllJobTypes() throws Exception {
        // Initialize the database
        jobTypeRepository.saveAndFlush(jobType);

        // Get all the jobTypes
        restJobTypeMockMvc.perform(get("/api/jobTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jobType.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].identity").value(hasItem(DEFAULT_IDENTITY.toString())))
                .andExpect(jsonPath("$.[*].column1").value(hasItem(DEFAULT_COLUMN1.toString())))
                .andExpect(jsonPath("$.[*].value1").value(hasItem(DEFAULT_VALUE1.toString())))
                .andExpect(jsonPath("$.[*].column2").value(hasItem(DEFAULT_COLUMN2.toString())))
                .andExpect(jsonPath("$.[*].value2").value(hasItem(DEFAULT_VALUE2.toString())));
    }

    @Test
    @Transactional
    public void getJobType() throws Exception {
        // Initialize the database
        jobTypeRepository.saveAndFlush(jobType);

        // Get the jobType
        restJobTypeMockMvc.perform(get("/api/jobTypes/{id}", jobType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jobType.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.identity").value(DEFAULT_IDENTITY.toString()))
            .andExpect(jsonPath("$.column1").value(DEFAULT_COLUMN1.toString()))
            .andExpect(jsonPath("$.value1").value(DEFAULT_VALUE1.toString()))
            .andExpect(jsonPath("$.column2").value(DEFAULT_COLUMN2.toString()))
            .andExpect(jsonPath("$.value2").value(DEFAULT_VALUE2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobType() throws Exception {
        // Get the jobType
        restJobTypeMockMvc.perform(get("/api/jobTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobType() throws Exception {
        // Initialize the database
        jobTypeRepository.saveAndFlush(jobType);

		int databaseSizeBeforeUpdate = jobTypeRepository.findAll().size();

        // Update the jobType
        jobType.setDescription(UPDATED_DESCRIPTION);
        jobType.setIdentity(UPDATED_IDENTITY);
        jobType.setColumn1(UPDATED_COLUMN1);
        jobType.setValue1(UPDATED_VALUE1);
        jobType.setColumn2(UPDATED_COLUMN2);
        jobType.setValue2(UPDATED_VALUE2);
        restJobTypeMockMvc.perform(put("/api/jobTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobType)))
                .andExpect(status().isOk());

        // Validate the JobType in the database
        List<JobType> jobTypes = jobTypeRepository.findAll();
        assertThat(jobTypes).hasSize(databaseSizeBeforeUpdate);
        JobType testJobType = jobTypes.get(jobTypes.size() - 1);
        assertThat(testJobType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobType.getIdentity()).isEqualTo(UPDATED_IDENTITY);
        assertThat(testJobType.getColumn1()).isEqualTo(UPDATED_COLUMN1);
        assertThat(testJobType.getValue1()).isEqualTo(UPDATED_VALUE1);
        assertThat(testJobType.getColumn2()).isEqualTo(UPDATED_COLUMN2);
        assertThat(testJobType.getValue2()).isEqualTo(UPDATED_VALUE2);
    }

    @Test
    @Transactional
    public void deleteJobType() throws Exception {
        // Initialize the database
        jobTypeRepository.saveAndFlush(jobType);

		int databaseSizeBeforeDelete = jobTypeRepository.findAll().size();

        // Get the jobType
        restJobTypeMockMvc.perform(delete("/api/jobTypes/{id}", jobType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JobType> jobTypes = jobTypeRepository.findAll();
        assertThat(jobTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
