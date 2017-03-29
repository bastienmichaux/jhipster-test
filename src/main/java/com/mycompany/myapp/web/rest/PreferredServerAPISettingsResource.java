package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.PreferredServerAPISettings;

import com.mycompany.myapp.repository.PreferredServerAPISettingsRepository;
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
 * REST controller for managing PreferredServerAPISettings.
 */
@RestController
@RequestMapping("/api")
public class PreferredServerAPISettingsResource {

    private final Logger log = LoggerFactory.getLogger(PreferredServerAPISettingsResource.class);

    private static final String ENTITY_NAME = "preferredServerAPISettings";
        
    private final PreferredServerAPISettingsRepository preferredServerAPISettingsRepository;

    public PreferredServerAPISettingsResource(PreferredServerAPISettingsRepository preferredServerAPISettingsRepository) {
        this.preferredServerAPISettingsRepository = preferredServerAPISettingsRepository;
    }

    /**
     * POST  /preferred-server-api-settings : Create a new preferredServerAPISettings.
     *
     * @param preferredServerAPISettings the preferredServerAPISettings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new preferredServerAPISettings, or with status 400 (Bad Request) if the preferredServerAPISettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/preferred-server-api-settings")
    @Timed
    public ResponseEntity<PreferredServerAPISettings> createPreferredServerAPISettings(@RequestBody PreferredServerAPISettings preferredServerAPISettings) throws URISyntaxException {
        log.debug("REST request to save PreferredServerAPISettings : {}", preferredServerAPISettings);
        if (preferredServerAPISettings.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new preferredServerAPISettings cannot already have an ID")).body(null);
        }
        PreferredServerAPISettings result = preferredServerAPISettingsRepository.save(preferredServerAPISettings);
        return ResponseEntity.created(new URI("/api/preferred-server-api-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /preferred-server-api-settings : Updates an existing preferredServerAPISettings.
     *
     * @param preferredServerAPISettings the preferredServerAPISettings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated preferredServerAPISettings,
     * or with status 400 (Bad Request) if the preferredServerAPISettings is not valid,
     * or with status 500 (Internal Server Error) if the preferredServerAPISettings couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/preferred-server-api-settings")
    @Timed
    public ResponseEntity<PreferredServerAPISettings> updatePreferredServerAPISettings(@RequestBody PreferredServerAPISettings preferredServerAPISettings) throws URISyntaxException {
        log.debug("REST request to update PreferredServerAPISettings : {}", preferredServerAPISettings);
        if (preferredServerAPISettings.getId() == null) {
            return createPreferredServerAPISettings(preferredServerAPISettings);
        }
        PreferredServerAPISettings result = preferredServerAPISettingsRepository.save(preferredServerAPISettings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, preferredServerAPISettings.getId().toString()))
            .body(result);
    }

    /**
     * GET  /preferred-server-api-settings : get all the preferredServerAPISettings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of preferredServerAPISettings in body
     */
    @GetMapping("/preferred-server-api-settings")
    @Timed
    public List<PreferredServerAPISettings> getAllPreferredServerAPISettings() {
        log.debug("REST request to get all PreferredServerAPISettings");
        List<PreferredServerAPISettings> preferredServerAPISettings = preferredServerAPISettingsRepository.findAll();
        return preferredServerAPISettings;
    }

    /**
     * GET  /preferred-server-api-settings/:id : get the "id" preferredServerAPISettings.
     *
     * @param id the id of the preferredServerAPISettings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the preferredServerAPISettings, or with status 404 (Not Found)
     */
    @GetMapping("/preferred-server-api-settings/{id}")
    @Timed
    public ResponseEntity<PreferredServerAPISettings> getPreferredServerAPISettings(@PathVariable Long id) {
        log.debug("REST request to get PreferredServerAPISettings : {}", id);
        PreferredServerAPISettings preferredServerAPISettings = preferredServerAPISettingsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(preferredServerAPISettings));
    }

    /**
     * DELETE  /preferred-server-api-settings/:id : delete the "id" preferredServerAPISettings.
     *
     * @param id the id of the preferredServerAPISettings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/preferred-server-api-settings/{id}")
    @Timed
    public ResponseEntity<Void> deletePreferredServerAPISettings(@PathVariable Long id) {
        log.debug("REST request to delete PreferredServerAPISettings : {}", id);
        preferredServerAPISettingsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
