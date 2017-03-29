package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CApp;

import com.mycompany.myapp.domain.FooBarAPISettings2;
import com.mycompany.myapp.repository.FooBarAPISettings2Repository;
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
 * Test class for the FooBarAPISettings2Resource REST controller.
 *
 * @see FooBarAPISettings2Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CApp.class)
public class FooBarAPISettings2ResourceIntTest {

    private static final String DEFAULT_FOO_BAR_API_SETTINGS_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FOO_BAR_API_SETTINGS_FIELD_NAME = "BBBBBBBBBB";

    @Autowired
    private FooBarAPISettings2Repository fooBarAPISettings2Repository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFooBarAPISettings2MockMvc;

    private FooBarAPISettings2 fooBarAPISettings2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FooBarAPISettings2Resource fooBarAPISettings2Resource = new FooBarAPISettings2Resource(fooBarAPISettings2Repository);
        this.restFooBarAPISettings2MockMvc = MockMvcBuilders.standaloneSetup(fooBarAPISettings2Resource)
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
    public static FooBarAPISettings2 createEntity(EntityManager em) {
        FooBarAPISettings2 fooBarAPISettings2 = new FooBarAPISettings2()
            .fooBarAPISettingsFieldName(DEFAULT_FOO_BAR_API_SETTINGS_FIELD_NAME);
        return fooBarAPISettings2;
    }

    @Before
    public void initTest() {
        fooBarAPISettings2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createFooBarAPISettings2() throws Exception {
        int databaseSizeBeforeCreate = fooBarAPISettings2Repository.findAll().size();

        // Create the FooBarAPISettings2
        restFooBarAPISettings2MockMvc.perform(post("/api/foo-bar-api-settings-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fooBarAPISettings2)))
            .andExpect(status().isCreated());

        // Validate the FooBarAPISettings2 in the database
        List<FooBarAPISettings2> fooBarAPISettings2List = fooBarAPISettings2Repository.findAll();
        assertThat(fooBarAPISettings2List).hasSize(databaseSizeBeforeCreate + 1);
        FooBarAPISettings2 testFooBarAPISettings2 = fooBarAPISettings2List.get(fooBarAPISettings2List.size() - 1);
        assertThat(testFooBarAPISettings2.getFooBarAPISettingsFieldName()).isEqualTo(DEFAULT_FOO_BAR_API_SETTINGS_FIELD_NAME);
    }

    @Test
    @Transactional
    public void createFooBarAPISettings2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fooBarAPISettings2Repository.findAll().size();

        // Create the FooBarAPISettings2 with an existing ID
        fooBarAPISettings2.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFooBarAPISettings2MockMvc.perform(post("/api/foo-bar-api-settings-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fooBarAPISettings2)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FooBarAPISettings2> fooBarAPISettings2List = fooBarAPISettings2Repository.findAll();
        assertThat(fooBarAPISettings2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFooBarAPISettings2S() throws Exception {
        // Initialize the database
        fooBarAPISettings2Repository.saveAndFlush(fooBarAPISettings2);

        // Get all the fooBarAPISettings2List
        restFooBarAPISettings2MockMvc.perform(get("/api/foo-bar-api-settings-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fooBarAPISettings2.getId().intValue())))
            .andExpect(jsonPath("$.[*].fooBarAPISettingsFieldName").value(hasItem(DEFAULT_FOO_BAR_API_SETTINGS_FIELD_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFooBarAPISettings2() throws Exception {
        // Initialize the database
        fooBarAPISettings2Repository.saveAndFlush(fooBarAPISettings2);

        // Get the fooBarAPISettings2
        restFooBarAPISettings2MockMvc.perform(get("/api/foo-bar-api-settings-2-s/{id}", fooBarAPISettings2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fooBarAPISettings2.getId().intValue()))
            .andExpect(jsonPath("$.fooBarAPISettingsFieldName").value(DEFAULT_FOO_BAR_API_SETTINGS_FIELD_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFooBarAPISettings2() throws Exception {
        // Get the fooBarAPISettings2
        restFooBarAPISettings2MockMvc.perform(get("/api/foo-bar-api-settings-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFooBarAPISettings2() throws Exception {
        // Initialize the database
        fooBarAPISettings2Repository.saveAndFlush(fooBarAPISettings2);
        int databaseSizeBeforeUpdate = fooBarAPISettings2Repository.findAll().size();

        // Update the fooBarAPISettings2
        FooBarAPISettings2 updatedFooBarAPISettings2 = fooBarAPISettings2Repository.findOne(fooBarAPISettings2.getId());
        updatedFooBarAPISettings2
            .fooBarAPISettingsFieldName(UPDATED_FOO_BAR_API_SETTINGS_FIELD_NAME);

        restFooBarAPISettings2MockMvc.perform(put("/api/foo-bar-api-settings-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFooBarAPISettings2)))
            .andExpect(status().isOk());

        // Validate the FooBarAPISettings2 in the database
        List<FooBarAPISettings2> fooBarAPISettings2List = fooBarAPISettings2Repository.findAll();
        assertThat(fooBarAPISettings2List).hasSize(databaseSizeBeforeUpdate);
        FooBarAPISettings2 testFooBarAPISettings2 = fooBarAPISettings2List.get(fooBarAPISettings2List.size() - 1);
        assertThat(testFooBarAPISettings2.getFooBarAPISettingsFieldName()).isEqualTo(UPDATED_FOO_BAR_API_SETTINGS_FIELD_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFooBarAPISettings2() throws Exception {
        int databaseSizeBeforeUpdate = fooBarAPISettings2Repository.findAll().size();

        // Create the FooBarAPISettings2

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFooBarAPISettings2MockMvc.perform(put("/api/foo-bar-api-settings-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fooBarAPISettings2)))
            .andExpect(status().isCreated());

        // Validate the FooBarAPISettings2 in the database
        List<FooBarAPISettings2> fooBarAPISettings2List = fooBarAPISettings2Repository.findAll();
        assertThat(fooBarAPISettings2List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFooBarAPISettings2() throws Exception {
        // Initialize the database
        fooBarAPISettings2Repository.saveAndFlush(fooBarAPISettings2);
        int databaseSizeBeforeDelete = fooBarAPISettings2Repository.findAll().size();

        // Get the fooBarAPISettings2
        restFooBarAPISettings2MockMvc.perform(delete("/api/foo-bar-api-settings-2-s/{id}", fooBarAPISettings2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FooBarAPISettings2> fooBarAPISettings2List = fooBarAPISettings2Repository.findAll();
        assertThat(fooBarAPISettings2List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FooBarAPISettings2.class);
    }
}
