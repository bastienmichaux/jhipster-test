package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CApp;

import com.mycompany.myapp.domain.UserIPV6Address;
import com.mycompany.myapp.repository.UserIPV6AddressRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserIPV6AddressResource REST controller.
 *
 * @see UserIPV6AddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CApp.class)
public class UserIPV6AddressResourceIntTest {

    private static final String DEFAULT_ADDRESS_IPV_6 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_IPV_6 = "BBBBBBBBBB";

    @Autowired
    private UserIPV6AddressRepository userIPV6AddressRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserIPV6AddressMockMvc;

    private UserIPV6Address userIPV6Address;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserIPV6AddressResource userIPV6AddressResource = new UserIPV6AddressResource(userIPV6AddressRepository);
        this.restUserIPV6AddressMockMvc = MockMvcBuilders.standaloneSetup(userIPV6AddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserIPV6Address createEntity(EntityManager em) {
        UserIPV6Address userIPV6Address = new UserIPV6Address()
            .addressIPV6(DEFAULT_ADDRESS_IPV_6);
        return userIPV6Address;
    }

    @Before
    public void initTest() {
        userIPV6Address = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserIPV6Address() throws Exception {
        int databaseSizeBeforeCreate = userIPV6AddressRepository.findAll().size();

        // Create the UserIPV6Address
        restUserIPV6AddressMockMvc.perform(post("/api/user-ipv-6-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userIPV6Address)))
            .andExpect(status().isCreated());

        // Validate the UserIPV6Address in the database
        List<UserIPV6Address> userIPV6AddressList = userIPV6AddressRepository.findAll();
        assertThat(userIPV6AddressList).hasSize(databaseSizeBeforeCreate + 1);
        UserIPV6Address testUserIPV6Address = userIPV6AddressList.get(userIPV6AddressList.size() - 1);
        assertThat(testUserIPV6Address.getAddressIPV6()).isEqualTo(DEFAULT_ADDRESS_IPV_6);
    }

    @Test
    @Transactional
    public void createUserIPV6AddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userIPV6AddressRepository.findAll().size();

        // Create the UserIPV6Address with an existing ID
        userIPV6Address.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserIPV6AddressMockMvc.perform(post("/api/user-ipv-6-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userIPV6Address)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserIPV6Address> userIPV6AddressList = userIPV6AddressRepository.findAll();
        assertThat(userIPV6AddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserIPV6Addresses() throws Exception {
        // Initialize the database
        userIPV6AddressRepository.saveAndFlush(userIPV6Address);

        // Get all the userIPV6AddressList
        restUserIPV6AddressMockMvc.perform(get("/api/user-ipv-6-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userIPV6Address.getId().intValue())))
            .andExpect(jsonPath("$.[*].addressIPV6").value(hasItem(DEFAULT_ADDRESS_IPV_6.toString())));
    }

    @Test
    @Transactional
    public void getUserIPV6Address() throws Exception {
        // Initialize the database
        userIPV6AddressRepository.saveAndFlush(userIPV6Address);

        // Get the userIPV6Address
        restUserIPV6AddressMockMvc.perform(get("/api/user-ipv-6-addresses/{id}", userIPV6Address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userIPV6Address.getId().intValue()))
            .andExpect(jsonPath("$.addressIPV6").value(DEFAULT_ADDRESS_IPV_6.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserIPV6Address() throws Exception {
        // Get the userIPV6Address
        restUserIPV6AddressMockMvc.perform(get("/api/user-ipv-6-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserIPV6Address() throws Exception {
        // Initialize the database
        userIPV6AddressRepository.saveAndFlush(userIPV6Address);
        int databaseSizeBeforeUpdate = userIPV6AddressRepository.findAll().size();

        // Update the userIPV6Address
        UserIPV6Address updatedUserIPV6Address = userIPV6AddressRepository.findOne(userIPV6Address.getId());
        updatedUserIPV6Address
            .addressIPV6(UPDATED_ADDRESS_IPV_6);

        restUserIPV6AddressMockMvc.perform(put("/api/user-ipv-6-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserIPV6Address)))
            .andExpect(status().isOk());

        // Validate the UserIPV6Address in the database
        List<UserIPV6Address> userIPV6AddressList = userIPV6AddressRepository.findAll();
        assertThat(userIPV6AddressList).hasSize(databaseSizeBeforeUpdate);
        UserIPV6Address testUserIPV6Address = userIPV6AddressList.get(userIPV6AddressList.size() - 1);
        assertThat(testUserIPV6Address.getAddressIPV6()).isEqualTo(UPDATED_ADDRESS_IPV_6);
    }

    @Test
    @Transactional
    public void updateNonExistingUserIPV6Address() throws Exception {
        int databaseSizeBeforeUpdate = userIPV6AddressRepository.findAll().size();

        // Create the UserIPV6Address

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserIPV6AddressMockMvc.perform(put("/api/user-ipv-6-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userIPV6Address)))
            .andExpect(status().isCreated());

        // Validate the UserIPV6Address in the database
        List<UserIPV6Address> userIPV6AddressList = userIPV6AddressRepository.findAll();
        assertThat(userIPV6AddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserIPV6Address() throws Exception {
        // Initialize the database
        userIPV6AddressRepository.saveAndFlush(userIPV6Address);
        int databaseSizeBeforeDelete = userIPV6AddressRepository.findAll().size();

        // Get the userIPV6Address
        restUserIPV6AddressMockMvc.perform(delete("/api/user-ipv-6-addresses/{id}", userIPV6Address.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserIPV6Address> userIPV6AddressList = userIPV6AddressRepository.findAll();
        assertThat(userIPV6AddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserIPV6Address.class);
    }
}
