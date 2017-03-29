package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CApp;

import com.mycompany.myapp.domain.FooBarAPISettings;
import com.mycompany.myapp.repository.FooBarAPISettingsRepository;
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
 * Test class for the FooBarAPISettingsResource REST controller.
 *
 * @see FooBarAPISettingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CApp.class)
public class FooBarAPISettingsResourceIntTest {

    private static final String DEFAULT_FOO_BAR_API_SETTINGS_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_FOO_BAR_API_SETTINGS_FIELD = "BBBBBBBBBB";

    @Autowired
    private FooBarAPISettingsRepository fooBarAPISettingsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFooBarAPISettingsMockMvc;

    private FooBarAPISettings fooBarAPISettings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FooBarAPISettingsResource fooBarAPISettingsResource = new FooBarAPISettingsResource(fooBarAPISettingsRepository);
        this.restFooBarAPISettingsMockMvc = MockMvcBuilders.standaloneSetup(fooBarAPISettingsResource)
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
    public static FooBarAPISettings createEntity(EntityManager em) {
        FooBarAPISettings fooBarAPISettings = new FooBarAPISettings()
            .fooBarAPISettingsField(DEFAULT_FOO_BAR_API_SETTINGS_FIELD);
        return fooBarAPISettings;
    }

    @Before
    public void initTest() {
        fooBarAPISettings = createEntity(em);
    }

    @Test
    @Transactional
    public void createFooBarAPISettings() throws Exception {
        int databaseSizeBeforeCreate = fooBarAPISettingsRepository.findAll().size();

        // Create the FooBarAPISettings
        restFooBarAPISettingsMockMvc.perform(post("/api/foo-bar-api-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fooBarAPISettings)))
            .andExpect(status().isCreated());

        // Validate the FooBarAPISettings in the database
        List<FooBarAPISettings> fooBarAPISettingsList = fooBarAPISettingsRepository.findAll();
        assertThat(fooBarAPISettingsList).hasSize(databaseSizeBeforeCreate + 1);
        FooBarAPISettings testFooBarAPISettings = fooBarAPISettingsList.get(fooBarAPISettingsList.size() - 1);
        assertThat(testFooBarAPISettings.getFooBarAPISettingsField()).isEqualTo(DEFAULT_FOO_BAR_API_SETTINGS_FIELD);
    }

    @Test
    @Transactional
    public void createFooBarAPISettingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fooBarAPISettingsRepository.findAll().size();

        // Create the FooBarAPISettings with an existing ID
        fooBarAPISettings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFooBarAPISettingsMockMvc.perform(post("/api/foo-bar-api-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fooBarAPISettings)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FooBarAPISettings> fooBarAPISettingsList = fooBarAPISettingsRepository.findAll();
        assertThat(fooBarAPISettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFooBarAPISettings() throws Exception {
        // Initialize the database
        fooBarAPISettingsRepository.saveAndFlush(fooBarAPISettings);

        // Get all the fooBarAPISettingsList
        restFooBarAPISettingsMockMvc.perform(get("/api/foo-bar-api-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fooBarAPISettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].fooBarAPISettingsField").value(hasItem(DEFAULT_FOO_BAR_API_SETTINGS_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getFooBarAPISettings() throws Exception {
        // Initialize the database
        fooBarAPISettingsRepository.saveAndFlush(fooBarAPISettings);

        // Get the fooBarAPISettings
        restFooBarAPISettingsMockMvc.perform(get("/api/foo-bar-api-settings/{id}", fooBarAPISettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fooBarAPISettings.getId().intValue()))
            .andExpect(jsonPath("$.fooBarAPISettingsField").value(DEFAULT_FOO_BAR_API_SETTINGS_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFooBarAPISettings() throws Exception {
        // Get the fooBarAPISettings
        restFooBarAPISettingsMockMvc.perform(get("/api/foo-bar-api-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFooBarAPISettings() throws Exception {
        // Initialize the database
        fooBarAPISettingsRepository.saveAndFlush(fooBarAPISettings);
        int databaseSizeBeforeUpdate = fooBarAPISettingsRepository.findAll().size();

        // Update the fooBarAPISettings
        FooBarAPISettings updatedFooBarAPISettings = fooBarAPISettingsRepository.findOne(fooBarAPISettings.getId());
        updatedFooBarAPISettings
            .fooBarAPISettingsField(UPDATED_FOO_BAR_API_SETTINGS_FIELD);

        restFooBarAPISettingsMockMvc.perform(put("/api/foo-bar-api-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFooBarAPISettings)))
            .andExpect(status().isOk());

        // Validate the FooBarAPISettings in the database
        List<FooBarAPISettings> fooBarAPISettingsList = fooBarAPISettingsRepository.findAll();
        assertThat(fooBarAPISettingsList).hasSize(databaseSizeBeforeUpdate);
        FooBarAPISettings testFooBarAPISettings = fooBarAPISettingsList.get(fooBarAPISettingsList.size() - 1);
        assertThat(testFooBarAPISettings.getFooBarAPISettingsField()).isEqualTo(UPDATED_FOO_BAR_API_SETTINGS_FIELD);
    }

    @Test
    @Transactional
    public void updateNonExistingFooBarAPISettings() throws Exception {
        int databaseSizeBeforeUpdate = fooBarAPISettingsRepository.findAll().size();

        // Create the FooBarAPISettings

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFooBarAPISettingsMockMvc.perform(put("/api/foo-bar-api-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fooBarAPISettings)))
            .andExpect(status().isCreated());

        // Validate the FooBarAPISettings in the database
        List<FooBarAPISettings> fooBarAPISettingsList = fooBarAPISettingsRepository.findAll();
        assertThat(fooBarAPISettingsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFooBarAPISettings() throws Exception {
        // Initialize the database
        fooBarAPISettingsRepository.saveAndFlush(fooBarAPISettings);
        int databaseSizeBeforeDelete = fooBarAPISettingsRepository.findAll().size();

        // Get the fooBarAPISettings
        restFooBarAPISettingsMockMvc.perform(delete("/api/foo-bar-api-settings/{id}", fooBarAPISettings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FooBarAPISettings> fooBarAPISettingsList = fooBarAPISettingsRepository.findAll();
        assertThat(fooBarAPISettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FooBarAPISettings.class);
    }
}
