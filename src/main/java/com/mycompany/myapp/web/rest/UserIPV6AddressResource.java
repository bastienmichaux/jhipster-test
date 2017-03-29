package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.UserIPV6Address;

import com.mycompany.myapp.repository.UserIPV6AddressRepository;
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
 * REST controller for managing UserIPV6Address.
 */
@RestController
@RequestMapping("/api")
public class UserIPV6AddressResource {

    private final Logger log = LoggerFactory.getLogger(UserIPV6AddressResource.class);

    private static final String ENTITY_NAME = "userIPV6Address";
        
    private final UserIPV6AddressRepository userIPV6AddressRepository;

    public UserIPV6AddressResource(UserIPV6AddressRepository userIPV6AddressRepository) {
        this.userIPV6AddressRepository = userIPV6AddressRepository;
    }

    /**
     * POST  /user-ipv-6-addresses : Create a new userIPV6Address.
     *
     * @param userIPV6Address the userIPV6Address to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userIPV6Address, or with status 400 (Bad Request) if the userIPV6Address has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-ipv-6-addresses")
    @Timed
    public ResponseEntity<UserIPV6Address> createUserIPV6Address(@RequestBody UserIPV6Address userIPV6Address) throws URISyntaxException {
        log.debug("REST request to save UserIPV6Address : {}", userIPV6Address);
        if (userIPV6Address.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userIPV6Address cannot already have an ID")).body(null);
        }
        UserIPV6Address result = userIPV6AddressRepository.save(userIPV6Address);
        return ResponseEntity.created(new URI("/api/user-ipv-6-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-ipv-6-addresses : Updates an existing userIPV6Address.
     *
     * @param userIPV6Address the userIPV6Address to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userIPV6Address,
     * or with status 400 (Bad Request) if the userIPV6Address is not valid,
     * or with status 500 (Internal Server Error) if the userIPV6Address couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-ipv-6-addresses")
    @Timed
    public ResponseEntity<UserIPV6Address> updateUserIPV6Address(@RequestBody UserIPV6Address userIPV6Address) throws URISyntaxException {
        log.debug("REST request to update UserIPV6Address : {}", userIPV6Address);
        if (userIPV6Address.getId() == null) {
            return createUserIPV6Address(userIPV6Address);
        }
        UserIPV6Address result = userIPV6AddressRepository.save(userIPV6Address);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userIPV6Address.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-ipv-6-addresses : get all the userIPV6Addresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userIPV6Addresses in body
     */
    @GetMapping("/user-ipv-6-addresses")
    @Timed
    public List<UserIPV6Address> getAllUserIPV6Addresses() {
        log.debug("REST request to get all UserIPV6Addresses");
        List<UserIPV6Address> userIPV6Addresses = userIPV6AddressRepository.findAll();
        return userIPV6Addresses;
    }

    /**
     * GET  /user-ipv-6-addresses/:id : get the "id" userIPV6Address.
     *
     * @param id the id of the userIPV6Address to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userIPV6Address, or with status 404 (Not Found)
     */
    @GetMapping("/user-ipv-6-addresses/{id}")
    @Timed
    public ResponseEntity<UserIPV6Address> getUserIPV6Address(@PathVariable Long id) {
        log.debug("REST request to get UserIPV6Address : {}", id);
        UserIPV6Address userIPV6Address = userIPV6AddressRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userIPV6Address));
    }

    /**
     * DELETE  /user-ipv-6-addresses/:id : delete the "id" userIPV6Address.
     *
     * @param id the id of the userIPV6Address to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-ipv-6-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserIPV6Address(@PathVariable Long id) {
        log.debug("REST request to delete UserIPV6Address : {}", id);
        userIPV6AddressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
