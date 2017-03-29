package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CApp;

import com.mycompany.myapp.domain.Foobarmyentity;
import com.mycompany.myapp.repository.FoobarmyentityRepository;
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
 * Test class for the FoobarmyentityResource REST controller.
 *
 * @see FoobarmyentityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CApp.class)
public class FoobarmyentityResourceIntTest {

    private static final String DEFAULT_M_Y_FOOBARFIELD = "AAAAAAAAAA";
    private static final String UPDATED_M_Y_FOOBARFIELD = "BBBBBBBBBB";

    @Autowired
    private FoobarmyentityRepository foobarmyentityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFoobarmyentityMockMvc;

    private Foobarmyentity foobarmyentity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FoobarmyentityResource foobarmyentityResource = new FoobarmyentityResource(foobarmyentityRepository);
        this.restFoobarmyentityMockMvc = MockMvcBuilders.standaloneSetup(foobarmyentityResource)
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
    public static Foobarmyentity createEntity(EntityManager em) {
        Foobarmyentity foobarmyentity = new Foobarmyentity()
            .mYFoobarfield(DEFAULT_M_Y_FOOBARFIELD);
        return foobarmyentity;
    }

    @Before
    public void initTest() {
        foobarmyentity = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoobarmyentity() throws Exception {
        int databaseSizeBeforeCreate = foobarmyentityRepository.findAll().size();

        // Create the Foobarmyentity
        restFoobarmyentityMockMvc.perform(post("/api/foobarmyentities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foobarmyentity)))
            .andExpect(status().isCreated());

        // Validate the Foobarmyentity in the database
        List<Foobarmyentity> foobarmyentityList = foobarmyentityRepository.findAll();
        assertThat(foobarmyentityList).hasSize(databaseSizeBeforeCreate + 1);
        Foobarmyentity testFoobarmyentity = foobarmyentityList.get(foobarmyentityList.size() - 1);
        assertThat(testFoobarmyentity.getmYFoobarfield()).isEqualTo(DEFAULT_M_Y_FOOBARFIELD);
    }

    @Test
    @Transactional
    public void createFoobarmyentityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foobarmyentityRepository.findAll().size();

        // Create the Foobarmyentity with an existing ID
        foobarmyentity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoobarmyentityMockMvc.perform(post("/api/foobarmyentities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foobarmyentity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Foobarmyentity> foobarmyentityList = foobarmyentityRepository.findAll();
        assertThat(foobarmyentityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFoobarmyentities() throws Exception {
        // Initialize the database
        foobarmyentityRepository.saveAndFlush(foobarmyentity);

        // Get all the foobarmyentityList
        restFoobarmyentityMockMvc.perform(get("/api/foobarmyentities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foobarmyentity.getId().intValue())))
            .andExpect(jsonPath("$.[*].mYFoobarfield").value(hasItem(DEFAULT_M_Y_FOOBARFIELD.toString())));
    }

    @Test
    @Transactional
    public void getFoobarmyentity() throws Exception {
        // Initialize the database
        foobarmyentityRepository.saveAndFlush(foobarmyentity);

        // Get the foobarmyentity
        restFoobarmyentityMockMvc.perform(get("/api/foobarmyentities/{id}", foobarmyentity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foobarmyentity.getId().intValue()))
            .andExpect(jsonPath("$.mYFoobarfield").value(DEFAULT_M_Y_FOOBARFIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFoobarmyentity() throws Exception {
        // Get the foobarmyentity
        restFoobarmyentityMockMvc.perform(get("/api/foobarmyentities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoobarmyentity() throws Exception {
        // Initialize the database
        foobarmyentityRepository.saveAndFlush(foobarmyentity);
        int databaseSizeBeforeUpdate = foobarmyentityRepository.findAll().size();

        // Update the foobarmyentity
        Foobarmyentity updatedFoobarmyentity = foobarmyentityRepository.findOne(foobarmyentity.getId());
        updatedFoobarmyentity
            .mYFoobarfield(UPDATED_M_Y_FOOBARFIELD);

        restFoobarmyentityMockMvc.perform(put("/api/foobarmyentities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFoobarmyentity)))
            .andExpect(status().isOk());

        // Validate the Foobarmyentity in the database
        List<Foobarmyentity> foobarmyentityList = foobarmyentityRepository.findAll();
        assertThat(foobarmyentityList).hasSize(databaseSizeBeforeUpdate);
        Foobarmyentity testFoobarmyentity = foobarmyentityList.get(foobarmyentityList.size() - 1);
        assertThat(testFoobarmyentity.getmYFoobarfield()).isEqualTo(UPDATED_M_Y_FOOBARFIELD);
    }

    @Test
    @Transactional
    public void updateNonExistingFoobarmyentity() throws Exception {
        int databaseSizeBeforeUpdate = foobarmyentityRepository.findAll().size();

        // Create the Foobarmyentity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFoobarmyentityMockMvc.perform(put("/api/foobarmyentities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foobarmyentity)))
            .andExpect(status().isCreated());

        // Validate the Foobarmyentity in the database
        List<Foobarmyentity> foobarmyentityList = foobarmyentityRepository.findAll();
        assertThat(foobarmyentityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFoobarmyentity() throws Exception {
        // Initialize the database
        foobarmyentityRepository.saveAndFlush(foobarmyentity);
        int databaseSizeBeforeDelete = foobarmyentityRepository.findAll().size();

        // Get the foobarmyentity
        restFoobarmyentityMockMvc.perform(delete("/api/foobarmyentities/{id}", foobarmyentity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Foobarmyentity> foobarmyentityList = foobarmyentityRepository.findAll();
        assertThat(foobarmyentityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Foobarmyentity.class);
    }
}
