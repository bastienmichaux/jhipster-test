package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.FooBarAPISettings2;

import com.mycompany.myapp.repository.FooBarAPISettings2Repository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FooBarAPISettings2.
 */
@RestController
@RequestMapping("/api")
public class FooBarAPISettings2Resource {

    private final Logger log = LoggerFactory.getLogger(FooBarAPISettings2Resource.class);

    private static final String ENTITY_NAME = "fooBarAPISettings2";
        
    private final FooBarAPISettings2Repository fooBarAPISettings2Repository;

    public FooBarAPISettings2Resource(FooBarAPISettings2Repository fooBarAPISettings2Repository) {
        this.fooBarAPISettings2Repository = fooBarAPISettings2Repository;
    }

    /**
     * POST  /foo-bar-api-settings-2-s : Create a new fooBarAPISettings2.
     *
     * @param fooBarAPISettings2 the fooBarAPISettings2 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fooBarAPISettings2, or with status 400 (Bad Request) if the fooBarAPISettings2 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/foo-bar-api-settings-2-s")
    @Timed
    public ResponseEntity<FooBarAPISettings2> createFooBarAPISettings2(@RequestBody FooBarAPISettings2 fooBarAPISettings2) throws URISyntaxException {
        log.debug("REST request to save FooBarAPISettings2 : {}", fooBarAPISettings2);
        if (fooBarAPISettings2.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fooBarAPISettings2 cannot already have an ID")).body(null);
        }
        FooBarAPISettings2 result = fooBarAPISettings2Repository.save(fooBarAPISettings2);
        return ResponseEntity.created(new URI("/api/foo-bar-api-settings-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /foo-bar-api-settings-2-s : Updates an existing fooBarAPISettings2.
     *
     * @param fooBarAPISettings2 the fooBarAPISettings2 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fooBarAPISettings2,
     * or with status 400 (Bad Request) if the fooBarAPISettings2 is not valid,
     * or with status 500 (Internal Server Error) if the fooBarAPISettings2 couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/foo-bar-api-settings-2-s")
    @Timed
    public ResponseEntity<FooBarAPISettings2> updateFooBarAPISettings2(@RequestBody FooBarAPISettings2 fooBarAPISettings2) throws URISyntaxException {
        log.debug("REST request to update FooBarAPISettings2 : {}", fooBarAPISettings2);
        if (fooBarAPISettings2.getId() == null) {
            return createFooBarAPISettings2(fooBarAPISettings2);
        }
        FooBarAPISettings2 result = fooBarAPISettings2Repository.save(fooBarAPISettings2);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fooBarAPISettings2.getId().toString()))
            .body(result);
    }

    /**
     * GET  /foo-bar-api-settings-2-s : get all the fooBarAPISettings2S.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fooBarAPISettings2S in body
     */
    @GetMapping("/foo-bar-api-settings-2-s")
    @Timed
    public List<FooBarAPISettings2> getAllFooBarAPISettings2S() {
        log.debug("REST request to get all FooBarAPISettings2S");
        List<FooBarAPISettings2> fooBarAPISettings2S = fooBarAPISettings2Repository.findAll();
        return fooBarAPISettings2S;
    }

    /**
     * GET  /foo-bar-api-settings-2-s/:id : get the "id" fooBarAPISettings2.
     *
     * @param id the id of the fooBarAPISettings2 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fooBarAPISettings2, or with status 404 (Not Found)
     */
    @GetMapping("/foo-bar-api-settings-2-s/{id}")
    @Timed
    public ResponseEntity<FooBarAPISettings2> getFooBarAPISettings2(@PathVariable Long id) {
        log.debug("REST request to get FooBarAPISettings2 : {}", id);
        FooBarAPISettings2 fooBarAPISettings2 = fooBarAPISettings2Repository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fooBarAPISettings2));
    }

    /**
     * DELETE  /foo-bar-api-settings-2-s/:id : delete the "id" fooBarAPISettings2.
     *
     * @param id the id of the fooBarAPISettings2 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/foo-bar-api-settings-2-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteFooBarAPISettings2(@PathVariable Long id) {
        log.debug("REST request to delete FooBarAPISettings2 : {}", id);
        fooBarAPISettings2Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
