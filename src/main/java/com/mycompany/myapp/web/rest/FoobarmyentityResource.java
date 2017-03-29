package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Foobarmyentity;

import com.mycompany.myapp.repository.FoobarmyentityRepository;
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
 * REST controller for managing Foobarmyentity.
 */
@RestController
@RequestMapping("/api")
public class FoobarmyentityResource {

    private final Logger log = LoggerFactory.getLogger(FoobarmyentityResource.class);

    private static final String ENTITY_NAME = "foobarmyentity";
        
    private final FoobarmyentityRepository foobarmyentityRepository;

    public FoobarmyentityResource(FoobarmyentityRepository foobarmyentityRepository) {
        this.foobarmyentityRepository = foobarmyentityRepository;
    }

    /**
     * POST  /foobarmyentities : Create a new foobarmyentity.
     *
     * @param foobarmyentity the foobarmyentity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new foobarmyentity, or with status 400 (Bad Request) if the foobarmyentity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/foobarmyentities")
    @Timed
    public ResponseEntity<Foobarmyentity> createFoobarmyentity(@RequestBody Foobarmyentity foobarmyentity) throws URISyntaxException {
        log.debug("REST request to save Foobarmyentity : {}", foobarmyentity);
        if (foobarmyentity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new foobarmyentity cannot already have an ID")).body(null);
        }
        Foobarmyentity result = foobarmyentityRepository.save(foobarmyentity);
        return ResponseEntity.created(new URI("/api/foobarmyentities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /foobarmyentities : Updates an existing foobarmyentity.
     *
     * @param foobarmyentity the foobarmyentity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated foobarmyentity,
     * or with status 400 (Bad Request) if the foobarmyentity is not valid,
     * or with status 500 (Internal Server Error) if the foobarmyentity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/foobarmyentities")
    @Timed
    public ResponseEntity<Foobarmyentity> updateFoobarmyentity(@RequestBody Foobarmyentity foobarmyentity) throws URISyntaxException {
        log.debug("REST request to update Foobarmyentity : {}", foobarmyentity);
        if (foobarmyentity.getId() == null) {
            return createFoobarmyentity(foobarmyentity);
        }
        Foobarmyentity result = foobarmyentityRepository.save(foobarmyentity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, foobarmyentity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /foobarmyentities : get all the foobarmyentities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of foobarmyentities in body
     */
    @GetMapping("/foobarmyentities")
    @Timed
    public List<Foobarmyentity> getAllFoobarmyentities() {
        log.debug("REST request to get all Foobarmyentities");
        List<Foobarmyentity> foobarmyentities = foobarmyentityRepository.findAll();
        return foobarmyentities;
    }

    /**
     * GET  /foobarmyentities/:id : get the "id" foobarmyentity.
     *
     * @param id the id of the foobarmyentity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the foobarmyentity, or with status 404 (Not Found)
     */
    @GetMapping("/foobarmyentities/{id}")
    @Timed
    public ResponseEntity<Foobarmyentity> getFoobarmyentity(@PathVariable Long id) {
        log.debug("REST request to get Foobarmyentity : {}", id);
        Foobarmyentity foobarmyentity = foobarmyentityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(foobarmyentity));
    }

    /**
     * DELETE  /foobarmyentities/:id : delete the "id" foobarmyentity.
     *
     * @param id the id of the foobarmyentity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/foobarmyentities/{id}")
    @Timed
    public ResponseEntity<Void> deleteFoobarmyentity(@PathVariable Long id) {
        log.debug("REST request to delete Foobarmyentity : {}", id);
        foobarmyentityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
