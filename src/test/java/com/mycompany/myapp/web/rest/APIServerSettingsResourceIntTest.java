package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CApp;

import com.mycompany.myapp.domain.APIServerSettings;
import com.mycompany.myapp.repository.APIServerSettingsRepository;
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
 * Test class for the APIServerSettingsResource REST controller.
 *
 * @see APIServerSettingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CApp.class)
public class APIServerSettingsResourceIntTest {

    private static final String DEFAULT_MY_API_SERVER_SETTINGS = "AAAAAAAAAA";
    private static final String UPDATED_MY_API_SERVER_SETTINGS = "BBBBBBBBBB";

    @Autowired
    private APIServerSettingsRepository aPIServerSettingsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAPIServerSettingsMockMvc;

    private APIServerSettings aPIServerSettings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        APIServerSettingsResource aPIServerSettingsResource = new APIServerSettingsResource(aPIServerSettingsRepository);
        this.restAPIServerSettingsMockMvc = MockMvcBuilders.standaloneSetup(aPIServerSettingsResource)
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
    public static APIServerSettings createEntity(EntityManager em) {
        APIServerSettings aPIServerSettings = new APIServerSettings()
            .myAPIServerSettings(DEFAULT_MY_API_SERVER_SETTINGS);
        return aPIServerSettings;
    }

    @Before
    public void initTest() {
        aPIServerSettings = createEntity(em);
    }

    @Test
    @Transactional
    public void createAPIServerSettings() throws Exception {
        int databaseSizeBeforeCreate = aPIServerSettingsRepository.findAll().size();

        // Create the APIServerSettings
        restAPIServerSettingsMockMvc.perform(post("/api/a-pi-server-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aPIServerSettings)))
            .andExpect(status().isCreated());

        // Validate the APIServerSettings in the database
        List<APIServerSettings> aPIServerSettingsList = aPIServerSettingsRepository.findAll();
        assertThat(aPIServerSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        APIServerSettings testAPIServerSettings = aPIServerSettingsList.get(aPIServerSettingsList.size() - 1);
        assertThat(testAPIServerSettings.getMyAPIServerSettings()).isEqualTo(DEFAULT_MY_API_SERVER_SETTINGS);
    }

    @Test
    @Transactional
    public void createAPIServerSettingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aPIServerSettingsRepository.findAll().size();

        // Create the APIServerSettings with an existing ID
        aPIServerSettings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAPIServerSettingsMockMvc.perform(post("/api/a-pi-server-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aPIServerSettings)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<APIServerSettings> aPIServerSettingsList = aPIServerSettingsRepository.findAll();
        assertThat(aPIServerSettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAPIServerSettings() throws Exception {
        // Initialize the database
        aPIServerSettingsRepository.saveAndFlush(aPIServerSettings);

        // Get all the aPIServerSettingsList
        restAPIServerSettingsMockMvc.perform(get("/api/a-pi-server-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aPIServerSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].myAPIServerSettings").value(hasItem(DEFAULT_MY_API_SERVER_SETTINGS.toString())));
    }

    @Test
    @Transactional
    public void getAPIServerSettings() throws Exception {
        // Initialize the database
        aPIServerSettingsRepository.saveAndFlush(aPIServerSettings);

        // Get the aPIServerSettings
        restAPIServerSettingsMockMvc.perform(get("/api/a-pi-server-settings/{id}", aPIServerSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aPIServerSettings.getId().intValue()))
            .andExpect(jsonPath("$.myAPIServerSettings").value(DEFAULT_MY_API_SERVER_SETTINGS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAPIServerSettings() throws Exception {
        // Get the aPIServerSettings
        restAPIServerSettingsMockMvc.perform(get("/api/a-pi-server-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAPIServerSettings() throws Exception {
        // Initialize the database
        aPIServerSettingsRepository.saveAndFlush(aPIServerSettings);
        int databaseSizeBeforeUpdate = aPIServerSettingsRepository.findAll().size();

        // Update the aPIServerSettings
        APIServerSettings updatedAPIServerSettings = aPIServerSettingsRepository.findOne(aPIServerSettings.getId());
        updatedAPIServerSettings
            .myAPIServerSettings(UPDATED_MY_API_SERVER_SETTINGS);

        restAPIServerSettingsMockMvc.perform(put("/api/a-pi-server-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAPIServerSettings)))
            .andExpect(status().isOk());

        // Validate the APIServerSettings in the database
        List<APIServerSettings> aPIServerSettingsList = aPIServerSettingsRepository.findAll();
        assertThat(aPIServerSettingsList).hasSize(databaseSizeBeforeUpdate);
        APIServerSettings testAPIServerSettings = aPIServerSettingsList.get(aPIServerSettingsList.size() - 1);
        assertThat(testAPIServerSettings.getMyAPIServerSettings()).isEqualTo(UPDATED_MY_API_SERVER_SETTINGS);
    }

    @Test
    @Transactional
    public void updateNonExistingAPIServerSettings() throws Exception {
        int databaseSizeBeforeUpdate = aPIServerSettingsRepository.findAll().size();

        // Create the APIServerSettings

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAPIServerSettingsMockMvc.perform(put("/api/a-pi-server-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aPIServerSettings)))
            .andExpect(status().isCreated());

        // Validate the APIServerSettings in the database
        List<APIServerSettings> aPIServerSettingsList = aPIServerSettingsRepository.findAll();
        assertThat(aPIServerSettingsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAPIServerSettings() throws Exception {
        // Initialize the database
        aPIServerSettingsRepository.saveAndFlush(aPIServerSettings);
        int databaseSizeBeforeDelete = aPIServerSettingsRepository.findAll().size();

        // Get the aPIServerSettings
        restAPIServerSettingsMockMvc.perform(delete("/api/a-pi-server-settings/{id}", aPIServerSettings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<APIServerSettings> aPIServerSettingsList = aPIServerSettingsRepository.findAll();
        assertThat(aPIServerSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(APIServerSettings.class);
    }
}
