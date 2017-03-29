package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.APISettings;

import com.mycompany.myapp.repository.APISettingsRepository;
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
 * REST controller for managing APISettings.
 */
@RestController
@RequestMapping("/api")
public class APISettingsResource {

    private final Logger log = LoggerFactory.getLogger(APISettingsResource.class);

    private static final String ENTITY_NAME = "aPISettings";
        
    private final APISettingsRepository aPISettingsRepository;

    public APISettingsResource(APISettingsRepository aPISettingsRepository) {
        this.aPISettingsRepository = aPISettingsRepository;
    }

    /**
     * POST  /a-pi-settings : Create a new aPISettings.
     *
     * @param aPISettings the aPISettings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aPISettings, or with status 400 (Bad Request) if the aPISettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/a-pi-settings")
    @Timed
    public ResponseEntity<APISettings> createAPISettings(@RequestBody APISettings aPISettings) throws URISyntaxException {
        log.debug("REST request to save APISettings : {}", aPISettings);
        if (aPISettings.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new aPISettings cannot already have an ID")).body(null);
        }
        APISettings result = aPISettingsRepository.save(aPISettings);
        return ResponseEntity.created(new URI("/api/a-pi-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /a-pi-settings : Updates an existing aPISettings.
     *
     * @param aPISettings the aPISettings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aPISettings,
     * or with status 400 (Bad Request) if the aPISettings is not valid,
     * or with status 500 (Internal Server Error) if the aPISettings couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/a-pi-settings")
    @Timed
    public ResponseEntity<APISettings> updateAPISettings(@RequestBody APISettings aPISettings) throws URISyntaxException {
        log.debug("REST request to update APISettings : {}", aPISettings);
        if (aPISettings.getId() == null) {
            return createAPISettings(aPISettings);
        }
        APISettings result = aPISettingsRepository.save(aPISettings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aPISettings.getId().toString()))
            .body(result);
    }

    /**
     * GET  /a-pi-settings : get all the aPISettings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aPISettings in body
     */
    @GetMapping("/a-pi-settings")
    @Timed
    public List<APISettings> getAllAPISettings() {
        log.debug("REST request to get all APISettings");
        List<APISettings> aPISettings = aPISettingsRepository.findAll();
        return aPISettings;
    }

    /**
     * GET  /a-pi-settings/:id : get the "id" aPISettings.
     *
     * @param id the id of the aPISettings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aPISettings, or with status 404 (Not Found)
     */
    @GetMapping("/a-pi-settings/{id}")
    @Timed
    public ResponseEntity<APISettings> getAPISettings(@PathVariable Long id) {
        log.debug("REST request to get APISettings : {}", id);
        APISettings aPISettings = aPISettingsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aPISettings));
    }

    /**
     * DELETE  /a-pi-settings/:id : delete the "id" aPISettings.
     *
     * @param id the id of the aPISettings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/a-pi-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteAPISettings(@PathVariable Long id) {
        log.debug("REST request to delete APISettings : {}", id);
        aPISettingsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
