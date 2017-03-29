package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CApp;

import com.mycompany.myapp.domain.APISettings;
import com.mycompany.myapp.repository.APISettingsRepository;
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
 * Test class for the APISettingsResource REST controller.
 *
 * @see APISettingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CApp.class)
public class APISettingsResourceIntTest {

    private static final String DEFAULT_PREFERRED_API = "AAAAAAAAAA";
    private static final String UPDATED_PREFERRED_API = "BBBBBBBBBB";

    private static final String DEFAULT_PORT_8080_API_LISTENER = "AAAAAAAAAA";
    private static final String UPDATED_PORT_8080_API_LISTENER = "BBBBBBBBBB";

    @Autowired
    private APISettingsRepository aPISettingsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAPISettingsMockMvc;

    private APISettings aPISettings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        APISettingsResource aPISettingsResource = new APISettingsResource(aPISettingsRepository);
        this.restAPISettingsMockMvc = MockMvcBuilders.standaloneSetup(aPISettingsResource)
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
    public static APISettings createEntity(EntityManager em) {
        APISettings aPISettings = new APISettings()
            .preferredAPI(DEFAULT_PREFERRED_API)
            .port8080APIListener(DEFAULT_PORT_8080_API_LISTENER);
        return aPISettings;
    }

    @Before
    public void initTest() {
        aPISettings = createEntity(em);
    }

    @Test
    @Transactional
    public void createAPISettings() throws Exception {
        int databaseSizeBeforeCreate = aPISettingsRepository.findAll().size();

        // Create the APISettings
        restAPISettingsMockMvc.perform(post("/api/a-pi-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aPISettings)))
            .andExpect(status().isCreated());

        // Validate the APISettings in the database
        List<APISettings> aPISettingsList = aPISettingsRepository.findAll();
        assertThat(aPISettingsList).hasSize(databaseSizeBeforeCreate + 1);
        APISettings testAPISettings = aPISettingsList.get(aPISettingsList.size() - 1);
        assertThat(testAPISettings.getPreferredAPI()).isEqualTo(DEFAULT_PREFERRED_API);
        assertThat(testAPISettings.getPort8080APIListener()).isEqualTo(DEFAULT_PORT_8080_API_LISTENER);
    }

    @Test
    @Transactional
    public void createAPISettingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aPISettingsRepository.findAll().size();

        // Create the APISettings with an existing ID
        aPISettings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAPISettingsMockMvc.perform(post("/api/a-pi-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aPISettings)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<APISettings> aPISettingsList = aPISettingsRepository.findAll();
        assertThat(aPISettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAPISettings() throws Exception {
        // Initialize the database
        aPISettingsRepository.saveAndFlush(aPISettings);

        // Get all the aPISettingsList
        restAPISettingsMockMvc.perform(get("/api/a-pi-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aPISettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].preferredAPI").value(hasItem(DEFAULT_PREFERRED_API.toString())))
            .andExpect(jsonPath("$.[*].port8080APIListener").value(hasItem(DEFAULT_PORT_8080_API_LISTENER.toString())));
    }

    @Test
    @Transactional
    public void getAPISettings() throws Exception {
        // Initialize the database
        aPISettingsRepository.saveAndFlush(aPISettings);

        // Get the aPISettings
        restAPISettingsMockMvc.perform(get("/api/a-pi-settings/{id}", aPISettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aPISettings.getId().intValue()))
            .andExpect(jsonPath("$.preferredAPI").value(DEFAULT_PREFERRED_API.toString()))
            .andExpect(jsonPath("$.port8080APIListener").value(DEFAULT_PORT_8080_API_LISTENER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAPISettings() throws Exception {
        // Get the aPISettings
        restAPISettingsMockMvc.perform(get("/api/a-pi-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAPISettings() throws Exception {
        // Initialize the database
        aPISettingsRepository.saveAndFlush(aPISettings);
        int databaseSizeBeforeUpdate = aPISettingsRepository.findAll().size();

        // Update the aPISettings
        APISettings updatedAPISettings = aPISettingsRepository.findOne(aPISettings.getId());
        updatedAPISettings
            .preferredAPI(UPDATED_PREFERRED_API)
            .port8080APIListener(UPDATED_PORT_8080_API_LISTENER);

        restAPISettingsMockMvc.perform(put("/api/a-pi-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAPISettings)))
            .andExpect(status().isOk());

        // Validate the APISettings in the database
        List<APISettings> aPISettingsList = aPISettingsRepository.findAll();
        assertThat(aPISettingsList).hasSize(databaseSizeBeforeUpdate);
        APISettings testAPISettings = aPISettingsList.get(aPISettingsList.size() - 1);
        assertThat(testAPISettings.getPreferredAPI()).isEqualTo(UPDATED_PREFERRED_API);
        assertThat(testAPISettings.getPort8080APIListener()).isEqualTo(UPDATED_PORT_8080_API_LISTENER);
    }

    @Test
    @Transactional
    public void updateNonExistingAPISettings() throws Exception {
        int databaseSizeBeforeUpdate = aPISettingsRepository.findAll().size();

        // Create the APISettings

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAPISettingsMockMvc.perform(put("/api/a-pi-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aPISettings)))
            .andExpect(status().isCreated());

        // Validate the APISettings in the database
        List<APISettings> aPISettingsList = aPISettingsRepository.findAll();
        assertThat(aPISettingsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAPISettings() throws Exception {
        // Initialize the database
        aPISettingsRepository.saveAndFlush(aPISettings);
        int databaseSizeBeforeDelete = aPISettingsRepository.findAll().size();

        // Get the aPISettings
        restAPISettingsMockMvc.perform(delete("/api/a-pi-settings/{id}", aPISettings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<APISettings> aPISettingsList = aPISettingsRepository.findAll();
        assertThat(aPISettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(APISettings.class);
    }
}
