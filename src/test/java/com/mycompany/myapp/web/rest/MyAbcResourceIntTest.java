package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CApp;

import com.mycompany.myapp.domain.MyAbc;
import com.mycompany.myapp.repository.MyAbcRepository;
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
 * Test class for the MyAbcResource REST controller.
 *
 * @see MyAbcResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CApp.class)
public class MyAbcResourceIntTest {

    private static final String DEFAULT_PREFIX_MY_BOOK_SUFFIX = "AAAAAAAAAA";
    private static final String UPDATED_PREFIX_MY_BOOK_SUFFIX = "BBBBBBBBBB";

    private static final String DEFAULT_PREFIX_MY_FIELD_123_MY_SUFFIX = "AAAAAAAAAA";
    private static final String UPDATED_PREFIX_MY_FIELD_123_MY_SUFFIX = "BBBBBBBBBB";

    @Autowired
    private MyAbcRepository myAbcRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMyAbcMockMvc;

    private MyAbc myAbc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MyAbcResource myAbcResource = new MyAbcResource(myAbcRepository);
        this.restMyAbcMockMvc = MockMvcBuilders.standaloneSetup(myAbcResource)
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
    public static MyAbc createEntity(EntityManager em) {
        MyAbc myAbc = new MyAbc()
            .prefix_myBook_Suffix(DEFAULT_PREFIX_MY_BOOK_SUFFIX)
            .prefix__myField_123_MySuffix(DEFAULT_PREFIX_MY_FIELD_123_MY_SUFFIX);
        return myAbc;
    }

    @Before
    public void initTest() {
        myAbc = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyAbc() throws Exception {
        int databaseSizeBeforeCreate = myAbcRepository.findAll().size();

        // Create the MyAbc
        restMyAbcMockMvc.perform(post("/api/my-abcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myAbc)))
            .andExpect(status().isCreated());

        // Validate the MyAbc in the database
        List<MyAbc> myAbcList = myAbcRepository.findAll();
        assertThat(myAbcList).hasSize(databaseSizeBeforeCreate + 1);
        MyAbc testMyAbc = myAbcList.get(myAbcList.size() - 1);
        assertThat(testMyAbc.getPrefix_myBook_Suffix()).isEqualTo(DEFAULT_PREFIX_MY_BOOK_SUFFIX);
        assertThat(testMyAbc.getPrefix__myField_123_MySuffix()).isEqualTo(DEFAULT_PREFIX_MY_FIELD_123_MY_SUFFIX);
    }

    @Test
    @Transactional
    public void createMyAbcWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myAbcRepository.findAll().size();

        // Create the MyAbc with an existing ID
        myAbc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyAbcMockMvc.perform(post("/api/my-abcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myAbc)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MyAbc> myAbcList = myAbcRepository.findAll();
        assertThat(myAbcList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMyAbcs() throws Exception {
        // Initialize the database
        myAbcRepository.saveAndFlush(myAbc);

        // Get all the myAbcList
        restMyAbcMockMvc.perform(get("/api/my-abcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myAbc.getId().intValue())))
            .andExpect(jsonPath("$.[*].prefix_myBook_Suffix").value(hasItem(DEFAULT_PREFIX_MY_BOOK_SUFFIX.toString())))
            .andExpect(jsonPath("$.[*].prefix__myField_123_MySuffix").value(hasItem(DEFAULT_PREFIX_MY_FIELD_123_MY_SUFFIX.toString())));
    }

    @Test
    @Transactional
    public void getMyAbc() throws Exception {
        // Initialize the database
        myAbcRepository.saveAndFlush(myAbc);

        // Get the myAbc
        restMyAbcMockMvc.perform(get("/api/my-abcs/{id}", myAbc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myAbc.getId().intValue()))
            .andExpect(jsonPath("$.prefix_myBook_Suffix").value(DEFAULT_PREFIX_MY_BOOK_SUFFIX.toString()))
            .andExpect(jsonPath("$.prefix__myField_123_MySuffix").value(DEFAULT_PREFIX_MY_FIELD_123_MY_SUFFIX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyAbc() throws Exception {
        // Get the myAbc
        restMyAbcMockMvc.perform(get("/api/my-abcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyAbc() throws Exception {
        // Initialize the database
        myAbcRepository.saveAndFlush(myAbc);
        int databaseSizeBeforeUpdate = myAbcRepository.findAll().size();

        // Update the myAbc
        MyAbc updatedMyAbc = myAbcRepository.findOne(myAbc.getId());
        updatedMyAbc
            .prefix_myBook_Suffix(UPDATED_PREFIX_MY_BOOK_SUFFIX)
            .prefix__myField_123_MySuffix(UPDATED_PREFIX_MY_FIELD_123_MY_SUFFIX);

        restMyAbcMockMvc.perform(put("/api/my-abcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyAbc)))
            .andExpect(status().isOk());

        // Validate the MyAbc in the database
        List<MyAbc> myAbcList = myAbcRepository.findAll();
        assertThat(myAbcList).hasSize(databaseSizeBeforeUpdate);
        MyAbc testMyAbc = myAbcList.get(myAbcList.size() - 1);
        assertThat(testMyAbc.getPrefix_myBook_Suffix()).isEqualTo(UPDATED_PREFIX_MY_BOOK_SUFFIX);
        assertThat(testMyAbc.getPrefix__myField_123_MySuffix()).isEqualTo(UPDATED_PREFIX_MY_FIELD_123_MY_SUFFIX);
    }

    @Test
    @Transactional
    public void updateNonExistingMyAbc() throws Exception {
        int databaseSizeBeforeUpdate = myAbcRepository.findAll().size();

        // Create the MyAbc

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMyAbcMockMvc.perform(put("/api/my-abcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myAbc)))
            .andExpect(status().isCreated());

        // Validate the MyAbc in the database
        List<MyAbc> myAbcList = myAbcRepository.findAll();
        assertThat(myAbcList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMyAbc() throws Exception {
        // Initialize the database
        myAbcRepository.saveAndFlush(myAbc);
        int databaseSizeBeforeDelete = myAbcRepository.findAll().size();

        // Get the myAbc
        restMyAbcMockMvc.perform(delete("/api/my-abcs/{id}", myAbc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MyAbc> myAbcList = myAbcRepository.findAll();
        assertThat(myAbcList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyAbc.class);
    }
}
