package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.Slides;
import org.fininfo.elbazar.repository.SlidesRepository;
import org.fininfo.elbazar.repository.search.SlidesSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SlidesResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SlidesResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN = "AAAAAAAAAA";
    private static final String UPDATED_LIEN = "BBBBBBBBBB";

    @Autowired
    private SlidesRepository slidesRepository;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.SlidesSearchRepositoryMockConfiguration
     */
    @Autowired
    private SlidesSearchRepository mockSlidesSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSlidesMockMvc;

    private Slides slides;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Slides createEntity(EntityManager em) {
        Slides slides = new Slides()
            .nom(DEFAULT_NOM)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .type(DEFAULT_TYPE)
            .lien(DEFAULT_LIEN);
        return slides;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Slides createUpdatedEntity(EntityManager em) {
        Slides slides = new Slides()
            .nom(UPDATED_NOM)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .lien(UPDATED_LIEN);
        return slides;
    }

    @BeforeEach
    public void initTest() {
        slides = createEntity(em);
    }

    @Test
    @Transactional
    public void createSlides() throws Exception {
        int databaseSizeBeforeCreate = slidesRepository.findAll().size();
        // Create the Slides
        restSlidesMockMvc.perform(post("/api/slides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(slides)))
            .andExpect(status().isCreated());

        // Validate the Slides in the database
        List<Slides> slidesList = slidesRepository.findAll();
        assertThat(slidesList).hasSize(databaseSizeBeforeCreate + 1);
        Slides testSlides = slidesList.get(slidesList.size() - 1);
        assertThat(testSlides.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSlides.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSlides.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testSlides.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSlides.getLien()).isEqualTo(DEFAULT_LIEN);

        // Validate the Slides in Elasticsearch
        verify(mockSlidesSearchRepository, times(1)).save(testSlides);
    }

    @Test
    @Transactional
    public void createSlidesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = slidesRepository.findAll().size();

        // Create the Slides with an existing ID
        slides.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSlidesMockMvc.perform(post("/api/slides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(slides)))
            .andExpect(status().isBadRequest());

        // Validate the Slides in the database
        List<Slides> slidesList = slidesRepository.findAll();
        assertThat(slidesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Slides in Elasticsearch
        verify(mockSlidesSearchRepository, times(0)).save(slides);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = slidesRepository.findAll().size();
        // set the field null
        slides.setNom(null);

        // Create the Slides, which fails.


        restSlidesMockMvc.perform(post("/api/slides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(slides)))
            .andExpect(status().isBadRequest());

        List<Slides> slidesList = slidesRepository.findAll();
        assertThat(slidesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = slidesRepository.findAll().size();
        // set the field null
        slides.setType(null);

        // Create the Slides, which fails.


        restSlidesMockMvc.perform(post("/api/slides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(slides)))
            .andExpect(status().isBadRequest());

        List<Slides> slidesList = slidesRepository.findAll();
        assertThat(slidesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSlides() throws Exception {
        // Initialize the database
        slidesRepository.saveAndFlush(slides);

        // Get all the slidesList
        restSlidesMockMvc.perform(get("/api/slides?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(slides.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN)));
    }
    
    @Test
    @Transactional
    public void getSlides() throws Exception {
        // Initialize the database
        slidesRepository.saveAndFlush(slides);

        // Get the slides
        restSlidesMockMvc.perform(get("/api/slides/{id}", slides.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(slides.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.lien").value(DEFAULT_LIEN));
    }
    @Test
    @Transactional
    public void getNonExistingSlides() throws Exception {
        // Get the slides
        restSlidesMockMvc.perform(get("/api/slides/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSlides() throws Exception {
        // Initialize the database
        slidesRepository.saveAndFlush(slides);

        int databaseSizeBeforeUpdate = slidesRepository.findAll().size();

        // Update the slides
        Slides updatedSlides = slidesRepository.findById(slides.getId()).get();
        // Disconnect from session so that the updates on updatedSlides are not directly saved in db
        em.detach(updatedSlides);
        updatedSlides
            .nom(UPDATED_NOM)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .lien(UPDATED_LIEN);

        restSlidesMockMvc.perform(put("/api/slides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSlides)))
            .andExpect(status().isOk());

        // Validate the Slides in the database
        List<Slides> slidesList = slidesRepository.findAll();
        assertThat(slidesList).hasSize(databaseSizeBeforeUpdate);
        Slides testSlides = slidesList.get(slidesList.size() - 1);
        assertThat(testSlides.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSlides.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSlides.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testSlides.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSlides.getLien()).isEqualTo(UPDATED_LIEN);

        // Validate the Slides in Elasticsearch
        verify(mockSlidesSearchRepository, times(1)).save(testSlides);
    }

    @Test
    @Transactional
    public void updateNonExistingSlides() throws Exception {
        int databaseSizeBeforeUpdate = slidesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSlidesMockMvc.perform(put("/api/slides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(slides)))
            .andExpect(status().isBadRequest());

        // Validate the Slides in the database
        List<Slides> slidesList = slidesRepository.findAll();
        assertThat(slidesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Slides in Elasticsearch
        verify(mockSlidesSearchRepository, times(0)).save(slides);
    }

    @Test
    @Transactional
    public void deleteSlides() throws Exception {
        // Initialize the database
        slidesRepository.saveAndFlush(slides);

        int databaseSizeBeforeDelete = slidesRepository.findAll().size();

        // Delete the slides
        restSlidesMockMvc.perform(delete("/api/slides/{id}", slides.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Slides> slidesList = slidesRepository.findAll();
        assertThat(slidesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Slides in Elasticsearch
        verify(mockSlidesSearchRepository, times(1)).deleteById(slides.getId());
    }

    @Test
    @Transactional
    public void searchSlides() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        slidesRepository.saveAndFlush(slides);
        when(mockSlidesSearchRepository.search(queryStringQuery("id:" + slides.getId())))
            .thenReturn(Collections.singletonList(slides));

        // Search the slides
        restSlidesMockMvc.perform(get("/api/_search/slides?query=id:" + slides.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(slides.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN)));
    }
}
