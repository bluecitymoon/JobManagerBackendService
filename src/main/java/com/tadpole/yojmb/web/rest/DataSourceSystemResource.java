package com.tadpole.yojmb.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tadpole.yojmb.domain.DataSourceSystem;
import com.tadpole.yojmb.repository.DataSourceSystemRepository;
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
 * REST controller for managing DataSourceSystem.
 */
@RestController
@RequestMapping("/api")
public class DataSourceSystemResource {

    private final Logger log = LoggerFactory.getLogger(DataSourceSystemResource.class);

    @Inject
    private DataSourceSystemRepository dataSourceSystemRepository;

    /**
     * POST  /dataSourceSystems -> Create a new dataSourceSystem.
     */
    @RequestMapping(value = "/dataSourceSystems",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DataSourceSystem> create(@RequestBody DataSourceSystem dataSourceSystem) throws URISyntaxException {
        log.debug("REST request to save DataSourceSystem : {}", dataSourceSystem);
        if (dataSourceSystem.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dataSourceSystem cannot already have an ID").body(null);
        }
        DataSourceSystem result = dataSourceSystemRepository.save(dataSourceSystem);
        return ResponseEntity.created(new URI("/api/dataSourceSystems/" + dataSourceSystem.getId())).body(result);
    }

    /**
     * PUT  /dataSourceSystems -> Updates an existing dataSourceSystem.
     */
    @RequestMapping(value = "/dataSourceSystems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DataSourceSystem> update(@RequestBody DataSourceSystem dataSourceSystem) throws URISyntaxException {
        log.debug("REST request to update DataSourceSystem : {}", dataSourceSystem);
        if (dataSourceSystem.getId() == null) {
            return create(dataSourceSystem);
        }
        DataSourceSystem result = dataSourceSystemRepository.save(dataSourceSystem);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /dataSourceSystems -> get all the dataSourceSystems.
     */
    @RequestMapping(value = "/dataSourceSystems",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DataSourceSystem>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<DataSourceSystem> page = dataSourceSystemRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dataSourceSystems", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dataSourceSystems/:id -> get the "id" dataSourceSystem.
     */
    @RequestMapping(value = "/dataSourceSystems/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DataSourceSystem> get(@PathVariable Long id) {
        log.debug("REST request to get DataSourceSystem : {}", id);
        return Optional.ofNullable(dataSourceSystemRepository.findOne(id))
            .map(dataSourceSystem -> new ResponseEntity<>(
                dataSourceSystem,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dataSourceSystems/:id -> delete the "id" dataSourceSystem.
     */
    @RequestMapping(value = "/dataSourceSystems/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete DataSourceSystem : {}", id);
        dataSourceSystemRepository.delete(id);
    }
}
