package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.APIServerSettings;

import com.mycompany.myapp.repository.APIServerSettingsRepository;
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
 * REST controller for managing APIServerSettings.
 */
@RestController
@RequestMapping("/api")
public class APIServerSettingsResource {

    private final Logger log = LoggerFactory.getLogger(APIServerSettingsResource.class);

    private static final String ENTITY_NAME = "aPIServerSettings";
        
    private final APIServerSettingsRepository aPIServerSettingsRepository;

    public APIServerSettingsResource(APIServerSettingsRepository aPIServerSettingsRepository) {
        this.aPIServerSettingsRepository = aPIServerSettingsRepository;
    }

    /**
     * POST  /a-pi-server-settings : Create a new aPIServerSettings.
     *
     * @param aPIServerSettings the aPIServerSettings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aPIServerSettings, or with status 400 (Bad Request) if the aPIServerSettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/a-pi-server-settings")
    @Timed
    public ResponseEntity<APIServerSettings> createAPIServerSettings(@RequestBody APIServerSettings aPIServerSettings) throws URISyntaxException {
        log.debug("REST request to save APIServerSettings : {}", aPIServerSettings);
        if (aPIServerSettings.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new aPIServerSettings cannot already have an ID")).body(null);
        }
        APIServerSettings result = aPIServerSettingsRepository.save(aPIServerSettings);
        return ResponseEntity.created(new URI("/api/a-pi-server-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /a-pi-server-settings : Updates an existing aPIServerSettings.
     *
     * @param aPIServerSettings the aPIServerSettings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aPIServerSettings,
     * or with status 400 (Bad Request) if the aPIServerSettings is not valid,
     * or with status 500 (Internal Server Error) if the aPIServerSettings couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/a-pi-server-settings")
    @Timed
    public ResponseEntity<APIServerSettings> updateAPIServerSettings(@RequestBody APIServerSettings aPIServerSettings) throws URISyntaxException {
        log.debug("REST request to update APIServerSettings : {}", aPIServerSettings);
        if (aPIServerSettings.getId() == null) {
            return createAPIServerSettings(aPIServerSettings);
        }
        APIServerSettings result = aPIServerSettingsRepository.save(aPIServerSettings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aPIServerSettings.getId().toString()))
            .body(result);
    }

    /**
     * GET  /a-pi-server-settings : get all the aPIServerSettings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aPIServerSettings in body
     */
    @GetMapping("/a-pi-server-settings")
    @Timed
    public List<APIServerSettings> getAllAPIServerSettings() {
        log.debug("REST request to get all APIServerSettings");
        List<APIServerSettings> aPIServerSettings = aPIServerSettingsRepository.findAll();
        return aPIServerSettings;
    }

    /**
     * GET  /a-pi-server-settings/:id : get the "id" aPIServerSettings.
     *
     * @param id the id of the aPIServerSettings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aPIServerSettings, or with status 404 (Not Found)
     */
    @GetMapping("/a-pi-server-settings/{id}")
    @Timed
    public ResponseEntity<APIServerSettings> getAPIServerSettings(@PathVariable Long id) {
        log.debug("REST request to get APIServerSettings : {}", id);
        APIServerSettings aPIServerSettings = aPIServerSettingsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aPIServerSettings));
    }

    /**
     * DELETE  /a-pi-server-settings/:id : delete the "id" aPIServerSettings.
     *
     * @param id the id of the aPIServerSettings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/a-pi-server-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteAPIServerSettings(@PathVariable Long id) {
        log.debug("REST request to delete APIServerSettings : {}", id);
        aPIServerSettingsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
