package com.tadpole.yojmb.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tadpole.yojmb.domain.JobType;
import com.tadpole.yojmb.repository.JobTypeRepository;
import com.tadpole.yojmb.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JobType.
 */
@RestController
@RequestMapping("/api")
public class JobTypeResource {

    private final Logger log = LoggerFactory.getLogger(JobTypeResource.class);

    @Inject
    private JobTypeRepository jobTypeRepository;

    /**
     * POST  /jobTypes -> Create a new jobType.
     */
    @RequestMapping(value = "/jobTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobType> create(@RequestBody JobType jobType) throws URISyntaxException {
        log.debug("REST request to save JobType : {}", jobType);
        if (jobType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jobType cannot already have an ID").body(null);
        }
        JobType result = jobTypeRepository.save(jobType);
        return ResponseEntity.created(new URI("/api/jobTypes/" + jobType.getId())).body(result);
    }

    /**
     * PUT  /jobTypes -> Updates an existing jobType.
     */
    @RequestMapping(value = "/jobTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobType> update(@RequestBody JobType jobType) throws URISyntaxException {
        log.debug("REST request to update JobType : {}", jobType);
        if (jobType.getId() == null) {
            return create(jobType);
        }
        JobType result = jobTypeRepository.save(jobType);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /jobTypes -> get all the jobTypes.
     */
    @RequestMapping(value = "/jobTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JobType>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<JobType> page = jobTypeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobTypes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jobTypes/:id -> get the "id" jobType.
     */
    @RequestMapping(value = "/jobTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobType> get(@PathVariable Long id) {
        log.debug("REST request to get JobType : {}", id);
        return Optional.ofNullable(jobTypeRepository.findOne(id))
            .map(jobType -> new ResponseEntity<>(
                jobType,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jobTypes/:id -> delete the "id" jobType.
     */
    @RequestMapping(value = "/jobTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete JobType : {}", id);
        jobTypeRepository.delete(id);
    }
}
