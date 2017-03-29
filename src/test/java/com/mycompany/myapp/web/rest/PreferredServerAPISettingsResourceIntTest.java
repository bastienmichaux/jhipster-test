package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CApp;

import com.mycompany.myapp.domain.PreferredServerAPISettings;
import com.mycompany.myapp.repository.PreferredServerAPISettingsRepository;
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
 * Test class for the PreferredServerAPISettingsResource REST controller.
 *
 * @see PreferredServerAPISettingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CApp.class)
public class PreferredServerAPISettingsResourceIntTest {

    private static final String DEFAULT_PREFERRED_SERVER_API_SETTINGS_HTTP_PORT = "AAAAAAAAAA";
    private static final String UPDATED_PREFERRED_SERVER_API_SETTINGS_HTTP_PORT = "BBBBBBBBBB";

    @Autowired
    private PreferredServerAPISettingsRepository preferredServerAPISettingsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPreferredServerAPISettingsMockMvc;

    private PreferredServerAPISettings preferredServerAPISettings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PreferredServerAPISettingsResource preferredServerAPISettingsResource = new PreferredServerAPISettingsResource(preferredServerAPISettingsRepository);
        this.restPreferredServerAPISettingsMockMvc = MockMvcBuilders.standaloneSetup(preferredServerAPISettingsResource)
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
    public static PreferredServerAPISettings createEntity(EntityManager em) {
        PreferredServerAPISettings preferredServerAPISettings = new PreferredServerAPISettings()
            .preferredServerAPISettingsHTTPPort(DEFAULT_PREFERRED_SERVER_API_SETTINGS_HTTP_PORT);
        return preferredServerAPISettings;
    }

    @Before
    public void initTest() {
        preferredServerAPISettings = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreferredServerAPISettings() throws Exception {
        int databaseSizeBeforeCreate = preferredServerAPISettingsRepository.findAll().size();

        // Create the PreferredServerAPISettings
        restPreferredServerAPISettingsMockMvc.perform(post("/api/preferred-server-api-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preferredServerAPISettings)))
            .andExpect(status().isCreated());

        // Validate the PreferredServerAPISettings in the database
        List<PreferredServerAPISettings> preferredServerAPISettingsList = preferredServerAPISettingsRepository.findAll();
        assertThat(preferredServerAPISettingsList).hasSize(databaseSizeBeforeCreate + 1);
        PreferredServerAPISettings testPreferredServerAPISettings = preferredServerAPISettingsList.get(preferredServerAPISettingsList.size() - 1);
        assertThat(testPreferredServerAPISettings.getPreferredServerAPISettingsHTTPPort()).isEqualTo(DEFAULT_PREFERRED_SERVER_API_SETTINGS_HTTP_PORT);
    }

    @Test
    @Transactional
    public void createPreferredServerAPISettingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preferredServerAPISettingsRepository.findAll().size();

        // Create the PreferredServerAPISettings with an existing ID
        preferredServerAPISettings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferredServerAPISettingsMockMvc.perform(post("/api/preferred-server-api-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preferredServerAPISettings)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PreferredServerAPISettings> preferredServerAPISettingsList = preferredServerAPISettingsRepository.findAll();
        assertThat(preferredServerAPISettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPreferredServerAPISettings() throws Exception {
        // Initialize the database
        preferredServerAPISettingsRepository.saveAndFlush(preferredServerAPISettings);

        // Get all the preferredServerAPISettingsList
        restPreferredServerAPISettingsMockMvc.perform(get("/api/preferred-server-api-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preferredServerAPISettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].preferredServerAPISettingsHTTPPort").value(hasItem(DEFAULT_PREFERRED_SERVER_API_SETTINGS_HTTP_PORT.toString())));
    }

    @Test
    @Transactional
    public void getPreferredServerAPISettings() throws Exception {
        // Initialize the database
        preferredServerAPISettingsRepository.saveAndFlush(preferredServerAPISettings);

        // Get the preferredServerAPISettings
        restPreferredServerAPISettingsMockMvc.perform(get("/api/preferred-server-api-settings/{id}", preferredServerAPISettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(preferredServerAPISettings.getId().intValue()))
            .andExpect(jsonPath("$.preferredServerAPISettingsHTTPPort").value(DEFAULT_PREFERRED_SERVER_API_SETTINGS_HTTP_PORT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPreferredServerAPISettings() throws Exception {
        // Get the preferredServerAPISettings
        restPreferredServerAPISettingsMockMvc.perform(get("/api/preferred-server-api-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreferredServerAPISettings() throws Exception {
        // Initialize the database
        preferredServerAPISettingsRepository.saveAndFlush(preferredServerAPISettings);
        int databaseSizeBeforeUpdate = preferredServerAPISettingsRepository.findAll().size();

        // Update the preferredServerAPISettings
        PreferredServerAPISettings updatedPreferredServerAPISettings = preferredServerAPISettingsRepository.findOne(preferredServerAPISettings.getId());
        updatedPreferredServerAPISettings
            .preferredServerAPISettingsHTTPPort(UPDATED_PREFERRED_SERVER_API_SETTINGS_HTTP_PORT);

        restPreferredServerAPISettingsMockMvc.perform(put("/api/preferred-server-api-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPreferredServerAPISettings)))
            .andExpect(status().isOk());

        // Validate the PreferredServerAPISettings in the database
        List<PreferredServerAPISettings> preferredServerAPISettingsList = preferredServerAPISettingsRepository.findAll();
        assertThat(preferredServerAPISettingsList).hasSize(databaseSizeBeforeUpdate);
        PreferredServerAPISettings testPreferredServerAPISettings = preferredServerAPISettingsList.get(preferredServerAPISettingsList.size() - 1);
        assertThat(testPreferredServerAPISettings.getPreferredServerAPISettingsHTTPPort()).isEqualTo(UPDATED_PREFERRED_SERVER_API_SETTINGS_HTTP_PORT);
    }

    @Test
    @Transactional
    public void updateNonExistingPreferredServerAPISettings() throws Exception {
        int databaseSizeBeforeUpdate = preferredServerAPISettingsRepository.findAll().size();

        // Create the PreferredServerAPISettings

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPreferredServerAPISettingsMockMvc.perform(put("/api/preferred-server-api-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preferredServerAPISettings)))
            .andExpect(status().isCreated());

        // Validate the PreferredServerAPISettings in the database
        List<PreferredServerAPISettings> preferredServerAPISettingsList = preferredServerAPISettingsRepository.findAll();
        assertThat(preferredServerAPISettingsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePreferredServerAPISettings() throws Exception {
        // Initialize the database
        preferredServerAPISettingsRepository.saveAndFlush(preferredServerAPISettings);
        int databaseSizeBeforeDelete = preferredServerAPISettingsRepository.findAll().size();

        // Get the preferredServerAPISettings
        restPreferredServerAPISettingsMockMvc.perform(delete("/api/preferred-server-api-settings/{id}", preferredServerAPISettings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PreferredServerAPISettings> preferredServerAPISettingsList = preferredServerAPISettingsRepository.findAll();
        assertThat(preferredServerAPISettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreferredServerAPISettings.class);
    }
}
