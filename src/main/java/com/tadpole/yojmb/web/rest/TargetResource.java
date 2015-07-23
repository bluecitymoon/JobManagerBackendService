package com.tadpole.yojmb.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tadpole.yojmb.domain.Target;
import com.tadpole.yojmb.repository.TargetRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Target.
 */
@RestController
@RequestMapping("/api")
public class TargetResource {

    private final Logger log = LoggerFactory.getLogger(TargetResource.class);

    @Inject
    private TargetRepository targetRepository;

    /**
     * POST  /targets -> Create a new target.
     */
    @RequestMapping(value = "/targets",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Target> create(@Valid @RequestBody Target target) throws URISyntaxException {
        log.debug("REST request to save Target : {}", target);
        if (target.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new target cannot already have an ID").body(null);
        }
        Target result = targetRepository.save(target);
        return ResponseEntity.created(new URI("/api/targets/" + target.getId())).body(result);
    }

    /**
     * PUT  /targets -> Updates an existing target.
     */
    @RequestMapping(value = "/targets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Target> update(@Valid @RequestBody Target target) throws URISyntaxException {
        log.debug("REST request to update Target : {}", target);
        if (target.getId() == null) {
            return create(target);
        }
        Target result = targetRepository.save(target);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /targets -> get all the targets.
     */
    @RequestMapping(value = "/targets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Target>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Target> page = targetRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/targets", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /targets/:id -> get the "id" target.
     */
    @RequestMapping(value = "/targets/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Target> get(@PathVariable Long id) {
        log.debug("REST request to get Target : {}", id);
        return Optional.ofNullable(targetRepository.findOne(id))
            .map(target -> new ResponseEntity<>(
                target,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /targets/:id -> delete the "id" target.
     */
    @RequestMapping(value = "/targets/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Target : {}", id);
        targetRepository.delete(id);
    }
}
