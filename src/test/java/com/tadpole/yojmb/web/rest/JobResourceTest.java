package com.tadpole.yojmb.web.rest;

import com.tadpole.yojmb.Application;
import com.tadpole.yojmb.domain.Job;
import com.tadpole.yojmb.repository.JobRepository;

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
 * Test class for the JobResource REST controller.
 *
 * @see JobResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JobResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_WS_TARGET_URL = "SAMPLE_TEXT";
    private static final String UPDATED_WS_TARGET_URL = "UPDATED_TEXT";

    private static final Integer DEFAULT_START_HOUR = 0;
    private static final Integer UPDATED_START_HOUR = 1;

    private static final Integer DEFAULT_START_MIN = 0;
    private static final Integer UPDATED_START_MIN = 1;

    private static final Integer DEFAULT_STOP_HOUR = 0;
    private static final Integer UPDATED_STOP_HOUR = 1;

    private static final Integer DEFAULT_STOP_MIN = 0;
    private static final Integer UPDATED_STOP_MIN = 1;
    private static final String DEFAULT_LOGIN_USER_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_LOGIN_USER_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_LOGIN_PASSWORD = "SAMPLE_TEXT";
    private static final String UPDATED_LOGIN_PASSWORD = "UPDATED_TEXT";

    private static final Integer DEFAULT_INTERVAL_MINUTES = 0;
    private static final Integer UPDATED_INTERVAL_MINUTES = 1;

    @Inject
    private JobRepository jobRepository;

    private MockMvc restJobMockMvc;

    private Job job;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobResource jobResource = new JobResource();
        ReflectionTestUtils.setField(jobResource, "jobRepository", jobRepository);
        this.restJobMockMvc = MockMvcBuilders.standaloneSetup(jobResource).build();
    }

    @Before
    public void initTest() {
        job = new Job();
        job.setName(DEFAULT_NAME);
        job.setDescription(DEFAULT_DESCRIPTION);
        job.setWsTargetUrl(DEFAULT_WS_TARGET_URL);
        job.setStartHour(DEFAULT_START_HOUR);
        job.setStartMin(DEFAULT_START_MIN);
        job.setStopHour(DEFAULT_STOP_HOUR);
        job.setStopMin(DEFAULT_STOP_MIN);
        job.setLoginUserName(DEFAULT_LOGIN_USER_NAME);
        job.setLoginPassword(DEFAULT_LOGIN_PASSWORD);
        job.setIntervalMinutes(DEFAULT_INTERVAL_MINUTES);
    }

    @Test
    @Transactional
    public void createJob() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();

        // Create the Job
        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isCreated());

        // Validate the Job in the database
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeCreate + 1);
        Job testJob = jobs.get(jobs.size() - 1);
        assertThat(testJob.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJob.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJob.getWsTargetUrl()).isEqualTo(DEFAULT_WS_TARGET_URL);
        assertThat(testJob.getStartHour()).isEqualTo(DEFAULT_START_HOUR);
        assertThat(testJob.getStartMin()).isEqualTo(DEFAULT_START_MIN);
        assertThat(testJob.getStopHour()).isEqualTo(DEFAULT_STOP_HOUR);
        assertThat(testJob.getStopMin()).isEqualTo(DEFAULT_STOP_MIN);
        assertThat(testJob.getLoginUserName()).isEqualTo(DEFAULT_LOGIN_USER_NAME);
        assertThat(testJob.getLoginPassword()).isEqualTo(DEFAULT_LOGIN_PASSWORD);
        assertThat(testJob.getIntervalMinutes()).isEqualTo(DEFAULT_INTERVAL_MINUTES);
    }

    @Test
    @Transactional
    public void getAllJobs() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobs
        restJobMockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(job.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].wsTargetUrl").value(hasItem(DEFAULT_WS_TARGET_URL.toString())))
                .andExpect(jsonPath("$.[*].startHour").value(hasItem(DEFAULT_START_HOUR)))
                .andExpect(jsonPath("$.[*].startMin").value(hasItem(DEFAULT_START_MIN)))
                .andExpect(jsonPath("$.[*].stopHour").value(hasItem(DEFAULT_STOP_HOUR)))
                .andExpect(jsonPath("$.[*].stopMin").value(hasItem(DEFAULT_STOP_MIN)))
                .andExpect(jsonPath("$.[*].loginUserName").value(hasItem(DEFAULT_LOGIN_USER_NAME.toString())))
                .andExpect(jsonPath("$.[*].loginPassword").value(hasItem(DEFAULT_LOGIN_PASSWORD.toString())))
                .andExpect(jsonPath("$.[*].intervalMinutes").value(hasItem(DEFAULT_INTERVAL_MINUTES)));
    }

    @Test
    @Transactional
    public void getJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", job.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(job.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.wsTargetUrl").value(DEFAULT_WS_TARGET_URL.toString()))
            .andExpect(jsonPath("$.startHour").value(DEFAULT_START_HOUR))
            .andExpect(jsonPath("$.startMin").value(DEFAULT_START_MIN))
            .andExpect(jsonPath("$.stopHour").value(DEFAULT_STOP_HOUR))
            .andExpect(jsonPath("$.stopMin").value(DEFAULT_STOP_MIN))
            .andExpect(jsonPath("$.loginUserName").value(DEFAULT_LOGIN_USER_NAME.toString()))
            .andExpect(jsonPath("$.loginPassword").value(DEFAULT_LOGIN_PASSWORD.toString()))
            .andExpect(jsonPath("$.intervalMinutes").value(DEFAULT_INTERVAL_MINUTES));
    }

    @Test
    @Transactional
    public void getNonExistingJob() throws Exception {
        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

		int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Update the job
        job.setName(UPDATED_NAME);
        job.setDescription(UPDATED_DESCRIPTION);
        job.setWsTargetUrl(UPDATED_WS_TARGET_URL);
        job.setStartHour(UPDATED_START_HOUR);
        job.setStartMin(UPDATED_START_MIN);
        job.setStopHour(UPDATED_STOP_HOUR);
        job.setStopMin(UPDATED_STOP_MIN);
        job.setLoginUserName(UPDATED_LOGIN_USER_NAME);
        job.setLoginPassword(UPDATED_LOGIN_PASSWORD);
        job.setIntervalMinutes(UPDATED_INTERVAL_MINUTES);
        restJobMockMvc.perform(put("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isOk());

        // Validate the Job in the database
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeUpdate);
        Job testJob = jobs.get(jobs.size() - 1);
        assertThat(testJob.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJob.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJob.getWsTargetUrl()).isEqualTo(UPDATED_WS_TARGET_URL);
        assertThat(testJob.getStartHour()).isEqualTo(UPDATED_START_HOUR);
        assertThat(testJob.getStartMin()).isEqualTo(UPDATED_START_MIN);
        assertThat(testJob.getStopHour()).isEqualTo(UPDATED_STOP_HOUR);
        assertThat(testJob.getStopMin()).isEqualTo(UPDATED_STOP_MIN);
        assertThat(testJob.getLoginUserName()).isEqualTo(UPDATED_LOGIN_USER_NAME);
        assertThat(testJob.getLoginPassword()).isEqualTo(UPDATED_LOGIN_PASSWORD);
        assertThat(testJob.getIntervalMinutes()).isEqualTo(UPDATED_INTERVAL_MINUTES);
    }

    @Test
    @Transactional
    public void deleteJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

		int databaseSizeBeforeDelete = jobRepository.findAll().size();

        // Get the job
        restJobMockMvc.perform(delete("/api/jobs/{id}", job.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
