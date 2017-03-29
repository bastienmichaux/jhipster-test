package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.FooBarAPISettings;

import com.mycompany.myapp.repository.FooBarAPISettingsRepository;
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
 * REST controller for managing FooBarAPISettings.
 */
@RestController
@RequestMapping("/api")
public class FooBarAPISettingsResource {

    private final Logger log = LoggerFactory.getLogger(FooBarAPISettingsResource.class);

    private static final String ENTITY_NAME = "fooBarAPISettings";
        
    private final FooBarAPISettingsRepository fooBarAPISettingsRepository;

    public FooBarAPISettingsResource(FooBarAPISettingsRepository fooBarAPISettingsRepository) {
        this.fooBarAPISettingsRepository = fooBarAPISettingsRepository;
    }

    /**
     * POST  /foo-bar-api-settings : Create a new fooBarAPISettings.
     *
     * @param fooBarAPISettings the fooBarAPISettings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fooBarAPISettings, or with status 400 (Bad Request) if the fooBarAPISettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/foo-bar-api-settings")
    @Timed
    public ResponseEntity<FooBarAPISettings> createFooBarAPISettings(@RequestBody FooBarAPISettings fooBarAPISettings) throws URISyntaxException {
        log.debug("REST request to save FooBarAPISettings : {}", fooBarAPISettings);
        if (fooBarAPISettings.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fooBarAPISettings cannot already have an ID")).body(null);
        }
        FooBarAPISettings result = fooBarAPISettingsRepository.save(fooBarAPISettings);
        return ResponseEntity.created(new URI("/api/foo-bar-api-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /foo-bar-api-settings : Updates an existing fooBarAPISettings.
     *
     * @param fooBarAPISettings the fooBarAPISettings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fooBarAPISettings,
     * or with status 400 (Bad Request) if the fooBarAPISettings is not valid,
     * or with status 500 (Internal Server Error) if the fooBarAPISettings couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/foo-bar-api-settings")
    @Timed
    public ResponseEntity<FooBarAPISettings> updateFooBarAPISettings(@RequestBody FooBarAPISettings fooBarAPISettings) throws URISyntaxException {
        log.debug("REST request to update FooBarAPISettings : {}", fooBarAPISettings);
        if (fooBarAPISettings.getId() == null) {
            return createFooBarAPISettings(fooBarAPISettings);
        }
        FooBarAPISettings result = fooBarAPISettingsRepository.save(fooBarAPISettings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fooBarAPISettings.getId().toString()))
            .body(result);
    }

    /**
     * GET  /foo-bar-api-settings : get all the fooBarAPISettings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fooBarAPISettings in body
     */
    @GetMapping("/foo-bar-api-settings")
    @Timed
    public List<FooBarAPISettings> getAllFooBarAPISettings() {
        log.debug("REST request to get all FooBarAPISettings");
        List<FooBarAPISettings> fooBarAPISettings = fooBarAPISettingsRepository.findAll();
        return fooBarAPISettings;
    }

    /**
     * GET  /foo-bar-api-settings/:id : get the "id" fooBarAPISettings.
     *
     * @param id the id of the fooBarAPISettings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fooBarAPISettings, or with status 404 (Not Found)
     */
    @GetMapping("/foo-bar-api-settings/{id}")
    @Timed
    public ResponseEntity<FooBarAPISettings> getFooBarAPISettings(@PathVariable Long id) {
        log.debug("REST request to get FooBarAPISettings : {}", id);
        FooBarAPISettings fooBarAPISettings = fooBarAPISettingsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fooBarAPISettings));
    }

    /**
     * DELETE  /foo-bar-api-settings/:id : delete the "id" fooBarAPISettings.
     *
     * @param id the id of the fooBarAPISettings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/foo-bar-api-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteFooBarAPISettings(@PathVariable Long id) {
        log.debug("REST request to delete FooBarAPISettings : {}", id);
        fooBarAPISettingsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
