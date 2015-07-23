package com.tadpole.yojmb.web.rest;

import com.tadpole.yojmb.Application;
import com.tadpole.yojmb.domain.Target;
import com.tadpole.yojmb.repository.TargetRepository;

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
 * Test class for the TargetResource REST controller.
 *
 * @see TargetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TargetResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    @Inject
    private TargetRepository targetRepository;

    private MockMvc restTargetMockMvc;

    private Target target;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TargetResource targetResource = new TargetResource();
        ReflectionTestUtils.setField(targetResource, "targetRepository", targetRepository);
        this.restTargetMockMvc = MockMvcBuilders.standaloneSetup(targetResource).build();
    }

    @Before
    public void initTest() {
        target = new Target();
        target.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTarget() throws Exception {
        int databaseSizeBeforeCreate = targetRepository.findAll().size();

        // Create the Target
        restTargetMockMvc.perform(post("/api/targets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(target)))
                .andExpect(status().isCreated());

        // Validate the Target in the database
        List<Target> targets = targetRepository.findAll();
        assertThat(targets).hasSize(databaseSizeBeforeCreate + 1);
        Target testTarget = targets.get(targets.size() - 1);
        assertThat(testTarget.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllTargets() throws Exception {
        // Initialize the database
        targetRepository.saveAndFlush(target);

        // Get all the targets
        restTargetMockMvc.perform(get("/api/targets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(target.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTarget() throws Exception {
        // Initialize the database
        targetRepository.saveAndFlush(target);

        // Get the target
        restTargetMockMvc.perform(get("/api/targets/{id}", target.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(target.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTarget() throws Exception {
        // Get the target
        restTargetMockMvc.perform(get("/api/targets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarget() throws Exception {
        // Initialize the database
        targetRepository.saveAndFlush(target);

		int databaseSizeBeforeUpdate = targetRepository.findAll().size();

        // Update the target
        target.setName(UPDATED_NAME);
        restTargetMockMvc.perform(put("/api/targets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(target)))
                .andExpect(status().isOk());

        // Validate the Target in the database
        List<Target> targets = targetRepository.findAll();
        assertThat(targets).hasSize(databaseSizeBeforeUpdate);
        Target testTarget = targets.get(targets.size() - 1);
        assertThat(testTarget.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteTarget() throws Exception {
        // Initialize the database
        targetRepository.saveAndFlush(target);

		int databaseSizeBeforeDelete = targetRepository.findAll().size();

        // Get the target
        restTargetMockMvc.perform(delete("/api/targets/{id}", target.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Target> targets = targetRepository.findAll();
        assertThat(targets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
