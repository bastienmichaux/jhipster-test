package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MyAbc;

import com.mycompany.myapp.repository.MyAbcRepository;
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
 * REST controller for managing MyAbc.
 */
@RestController
@RequestMapping("/api")
public class MyAbcResource {

    private final Logger log = LoggerFactory.getLogger(MyAbcResource.class);

    private static final String ENTITY_NAME = "myAbc";
        
    private final MyAbcRepository myAbcRepository;

    public MyAbcResource(MyAbcRepository myAbcRepository) {
        this.myAbcRepository = myAbcRepository;
    }

    /**
     * POST  /my-abcs : Create a new myAbc.
     *
     * @param myAbc the myAbc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myAbc, or with status 400 (Bad Request) if the myAbc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-abcs")
    @Timed
    public ResponseEntity<MyAbc> createMyAbc(@RequestBody MyAbc myAbc) throws URISyntaxException {
        log.debug("REST request to save MyAbc : {}", myAbc);
        if (myAbc.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new myAbc cannot already have an ID")).body(null);
        }
        MyAbc result = myAbcRepository.save(myAbc);
        return ResponseEntity.created(new URI("/api/my-abcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-abcs : Updates an existing myAbc.
     *
     * @param myAbc the myAbc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myAbc,
     * or with status 400 (Bad Request) if the myAbc is not valid,
     * or with status 500 (Internal Server Error) if the myAbc couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-abcs")
    @Timed
    public ResponseEntity<MyAbc> updateMyAbc(@RequestBody MyAbc myAbc) throws URISyntaxException {
        log.debug("REST request to update MyAbc : {}", myAbc);
        if (myAbc.getId() == null) {
            return createMyAbc(myAbc);
        }
        MyAbc result = myAbcRepository.save(myAbc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, myAbc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-abcs : get all the myAbcs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myAbcs in body
     */
    @GetMapping("/my-abcs")
    @Timed
    public List<MyAbc> getAllMyAbcs() {
        log.debug("REST request to get all MyAbcs");
        List<MyAbc> myAbcs = myAbcRepository.findAll();
        return myAbcs;
    }

    /**
     * GET  /my-abcs/:id : get the "id" myAbc.
     *
     * @param id the id of the myAbc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myAbc, or with status 404 (Not Found)
     */
    @GetMapping("/my-abcs/{id}")
    @Timed
    public ResponseEntity<MyAbc> getMyAbc(@PathVariable Long id) {
        log.debug("REST request to get MyAbc : {}", id);
        MyAbc myAbc = myAbcRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(myAbc));
    }

    /**
     * DELETE  /my-abcs/:id : delete the "id" myAbc.
     *
     * @param id the id of the myAbc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-abcs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyAbc(@PathVariable Long id) {
        log.debug("REST request to delete MyAbc : {}", id);
        myAbcRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
