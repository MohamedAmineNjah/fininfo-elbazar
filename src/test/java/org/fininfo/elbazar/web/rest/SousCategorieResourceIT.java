package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.SousCategorie;
import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.domain.Stock;
import org.fininfo.elbazar.domain.Categorie;
import org.fininfo.elbazar.repository.SousCategorieRepository;
import org.fininfo.elbazar.repository.search.SousCategorieSearchRepository;
import org.fininfo.elbazar.service.SousCategorieService;
import org.fininfo.elbazar.service.dto.SousCategorieDTO;
import org.fininfo.elbazar.service.mapper.SousCategorieMapper;
import org.fininfo.elbazar.service.dto.SousCategorieCriteria;
import org.fininfo.elbazar.service.SousCategorieQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SousCategorieResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SousCategorieResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;
    private static final Integer SMALLER_POSITION = 1 - 1;

    private static final Boolean DEFAULT_ETAT = false;
    private static final Boolean UPDATED_ETAT = true;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_CREE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREE_LE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREE_LE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CREE_PAR = "AAAAAAAAAA";
    private static final String UPDATED_CREE_PAR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIE_LE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_MODIFIE_LE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_MODIFIE_PAR = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIE_PAR = "BBBBBBBBBB";

    @Autowired
    private SousCategorieRepository sousCategorieRepository;

    @Autowired
    private SousCategorieMapper sousCategorieMapper;

    @Autowired
    private SousCategorieService sousCategorieService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.SousCategorieSearchRepositoryMockConfiguration
     */
    @Autowired
    private SousCategorieSearchRepository mockSousCategorieSearchRepository;

    @Autowired
    private SousCategorieQueryService sousCategorieQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSousCategorieMockMvc;

    private SousCategorie sousCategorie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousCategorie createEntity(EntityManager em) {
        SousCategorie sousCategorie = new SousCategorie()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .position(DEFAULT_POSITION)
            .etat(DEFAULT_ETAT)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return sousCategorie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousCategorie createUpdatedEntity(EntityManager em) {
        SousCategorie sousCategorie = new SousCategorie()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .position(UPDATED_POSITION)
            .etat(UPDATED_ETAT)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return sousCategorie;
    }

    @BeforeEach
    public void initTest() {
        sousCategorie = createEntity(em);
    }

    @Test
    @Transactional
    public void createSousCategorie() throws Exception {
        int databaseSizeBeforeCreate = sousCategorieRepository.findAll().size();
        // Create the SousCategorie
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(sousCategorie);
        restSousCategorieMockMvc.perform(post("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isCreated());

        // Validate the SousCategorie in the database
        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeCreate + 1);
        SousCategorie testSousCategorie = sousCategorieList.get(sousCategorieList.size() - 1);
        assertThat(testSousCategorie.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSousCategorie.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSousCategorie.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testSousCategorie.isEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testSousCategorie.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSousCategorie.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testSousCategorie.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testSousCategorie.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testSousCategorie.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testSousCategorie.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the SousCategorie in Elasticsearch
        verify(mockSousCategorieSearchRepository, times(1)).save(testSousCategorie);
    }

    @Test
    @Transactional
    public void createSousCategorieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sousCategorieRepository.findAll().size();

        // Create the SousCategorie with an existing ID
        sousCategorie.setId(1L);
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(sousCategorie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSousCategorieMockMvc.perform(post("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SousCategorie in the database
        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeCreate);

        // Validate the SousCategorie in Elasticsearch
        verify(mockSousCategorieSearchRepository, times(0)).save(sousCategorie);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = sousCategorieRepository.findAll().size();
        // set the field null
        sousCategorie.setNom(null);

        // Create the SousCategorie, which fails.
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(sousCategorie);


        restSousCategorieMockMvc.perform(post("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isBadRequest());

        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sousCategorieRepository.findAll().size();
        // set the field null
        sousCategorie.setDescription(null);

        // Create the SousCategorie, which fails.
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(sousCategorie);


        restSousCategorieMockMvc.perform(post("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isBadRequest());

        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sousCategorieRepository.findAll().size();
        // set the field null
        sousCategorie.setPosition(null);

        // Create the SousCategorie, which fails.
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(sousCategorie);


        restSousCategorieMockMvc.perform(post("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isBadRequest());

        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSousCategories() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList
        restSousCategorieMockMvc.perform(get("/api/sous-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sousCategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getSousCategorie() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get the sousCategorie
        restSousCategorieMockMvc.perform(get("/api/sous-categories/{id}", sousCategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sousCategorie.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.booleanValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }


    @Test
    @Transactional
    public void getSousCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        Long id = sousCategorie.getId();

        defaultSousCategorieShouldBeFound("id.equals=" + id);
        defaultSousCategorieShouldNotBeFound("id.notEquals=" + id);

        defaultSousCategorieShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSousCategorieShouldNotBeFound("id.greaterThan=" + id);

        defaultSousCategorieShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSousCategorieShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSousCategoriesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where nom equals to DEFAULT_NOM
        defaultSousCategorieShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the sousCategorieList where nom equals to UPDATED_NOM
        defaultSousCategorieShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where nom not equals to DEFAULT_NOM
        defaultSousCategorieShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the sousCategorieList where nom not equals to UPDATED_NOM
        defaultSousCategorieShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultSousCategorieShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the sousCategorieList where nom equals to UPDATED_NOM
        defaultSousCategorieShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where nom is not null
        defaultSousCategorieShouldBeFound("nom.specified=true");

        // Get all the sousCategorieList where nom is null
        defaultSousCategorieShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllSousCategoriesByNomContainsSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where nom contains DEFAULT_NOM
        defaultSousCategorieShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the sousCategorieList where nom contains UPDATED_NOM
        defaultSousCategorieShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where nom does not contain DEFAULT_NOM
        defaultSousCategorieShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the sousCategorieList where nom does not contain UPDATED_NOM
        defaultSousCategorieShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllSousCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where description equals to DEFAULT_DESCRIPTION
        defaultSousCategorieShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the sousCategorieList where description equals to UPDATED_DESCRIPTION
        defaultSousCategorieShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where description not equals to DEFAULT_DESCRIPTION
        defaultSousCategorieShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the sousCategorieList where description not equals to UPDATED_DESCRIPTION
        defaultSousCategorieShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSousCategorieShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the sousCategorieList where description equals to UPDATED_DESCRIPTION
        defaultSousCategorieShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where description is not null
        defaultSousCategorieShouldBeFound("description.specified=true");

        // Get all the sousCategorieList where description is null
        defaultSousCategorieShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllSousCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where description contains DEFAULT_DESCRIPTION
        defaultSousCategorieShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the sousCategorieList where description contains UPDATED_DESCRIPTION
        defaultSousCategorieShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where description does not contain DEFAULT_DESCRIPTION
        defaultSousCategorieShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the sousCategorieList where description does not contain UPDATED_DESCRIPTION
        defaultSousCategorieShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSousCategoriesByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where position equals to DEFAULT_POSITION
        defaultSousCategorieShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the sousCategorieList where position equals to UPDATED_POSITION
        defaultSousCategorieShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where position not equals to DEFAULT_POSITION
        defaultSousCategorieShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the sousCategorieList where position not equals to UPDATED_POSITION
        defaultSousCategorieShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultSousCategorieShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the sousCategorieList where position equals to UPDATED_POSITION
        defaultSousCategorieShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where position is not null
        defaultSousCategorieShouldBeFound("position.specified=true");

        // Get all the sousCategorieList where position is null
        defaultSousCategorieShouldNotBeFound("position.specified=false");
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByPositionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where position is greater than or equal to DEFAULT_POSITION
        defaultSousCategorieShouldBeFound("position.greaterThanOrEqual=" + DEFAULT_POSITION);

        // Get all the sousCategorieList where position is greater than or equal to UPDATED_POSITION
        defaultSousCategorieShouldNotBeFound("position.greaterThanOrEqual=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByPositionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where position is less than or equal to DEFAULT_POSITION
        defaultSousCategorieShouldBeFound("position.lessThanOrEqual=" + DEFAULT_POSITION);

        // Get all the sousCategorieList where position is less than or equal to SMALLER_POSITION
        defaultSousCategorieShouldNotBeFound("position.lessThanOrEqual=" + SMALLER_POSITION);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByPositionIsLessThanSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where position is less than DEFAULT_POSITION
        defaultSousCategorieShouldNotBeFound("position.lessThan=" + DEFAULT_POSITION);

        // Get all the sousCategorieList where position is less than UPDATED_POSITION
        defaultSousCategorieShouldBeFound("position.lessThan=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByPositionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where position is greater than DEFAULT_POSITION
        defaultSousCategorieShouldNotBeFound("position.greaterThan=" + DEFAULT_POSITION);

        // Get all the sousCategorieList where position is greater than SMALLER_POSITION
        defaultSousCategorieShouldBeFound("position.greaterThan=" + SMALLER_POSITION);
    }


    @Test
    @Transactional
    public void getAllSousCategoriesByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where etat equals to DEFAULT_ETAT
        defaultSousCategorieShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the sousCategorieList where etat equals to UPDATED_ETAT
        defaultSousCategorieShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByEtatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where etat not equals to DEFAULT_ETAT
        defaultSousCategorieShouldNotBeFound("etat.notEquals=" + DEFAULT_ETAT);

        // Get all the sousCategorieList where etat not equals to UPDATED_ETAT
        defaultSousCategorieShouldBeFound("etat.notEquals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultSousCategorieShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the sousCategorieList where etat equals to UPDATED_ETAT
        defaultSousCategorieShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where etat is not null
        defaultSousCategorieShouldBeFound("etat.specified=true");

        // Get all the sousCategorieList where etat is null
        defaultSousCategorieShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creeLe equals to DEFAULT_CREE_LE
        defaultSousCategorieShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the sousCategorieList where creeLe equals to UPDATED_CREE_LE
        defaultSousCategorieShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creeLe not equals to DEFAULT_CREE_LE
        defaultSousCategorieShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the sousCategorieList where creeLe not equals to UPDATED_CREE_LE
        defaultSousCategorieShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultSousCategorieShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the sousCategorieList where creeLe equals to UPDATED_CREE_LE
        defaultSousCategorieShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creeLe is not null
        defaultSousCategorieShouldBeFound("creeLe.specified=true");

        // Get all the sousCategorieList where creeLe is null
        defaultSousCategorieShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultSousCategorieShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the sousCategorieList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultSousCategorieShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultSousCategorieShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the sousCategorieList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultSousCategorieShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creeLe is less than DEFAULT_CREE_LE
        defaultSousCategorieShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the sousCategorieList where creeLe is less than UPDATED_CREE_LE
        defaultSousCategorieShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creeLe is greater than DEFAULT_CREE_LE
        defaultSousCategorieShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the sousCategorieList where creeLe is greater than SMALLER_CREE_LE
        defaultSousCategorieShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllSousCategoriesByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creePar equals to DEFAULT_CREE_PAR
        defaultSousCategorieShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the sousCategorieList where creePar equals to UPDATED_CREE_PAR
        defaultSousCategorieShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creePar not equals to DEFAULT_CREE_PAR
        defaultSousCategorieShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the sousCategorieList where creePar not equals to UPDATED_CREE_PAR
        defaultSousCategorieShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultSousCategorieShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the sousCategorieList where creePar equals to UPDATED_CREE_PAR
        defaultSousCategorieShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creePar is not null
        defaultSousCategorieShouldBeFound("creePar.specified=true");

        // Get all the sousCategorieList where creePar is null
        defaultSousCategorieShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllSousCategoriesByCreeParContainsSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creePar contains DEFAULT_CREE_PAR
        defaultSousCategorieShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the sousCategorieList where creePar contains UPDATED_CREE_PAR
        defaultSousCategorieShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where creePar does not contain DEFAULT_CREE_PAR
        defaultSousCategorieShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the sousCategorieList where creePar does not contain UPDATED_CREE_PAR
        defaultSousCategorieShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllSousCategoriesByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultSousCategorieShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the sousCategorieList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultSousCategorieShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultSousCategorieShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the sousCategorieList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultSousCategorieShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultSousCategorieShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the sousCategorieList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultSousCategorieShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifieLe is not null
        defaultSousCategorieShouldBeFound("modifieLe.specified=true");

        // Get all the sousCategorieList where modifieLe is null
        defaultSousCategorieShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultSousCategorieShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the sousCategorieList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultSousCategorieShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultSousCategorieShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the sousCategorieList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultSousCategorieShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultSousCategorieShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the sousCategorieList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultSousCategorieShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultSousCategorieShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the sousCategorieList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultSousCategorieShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllSousCategoriesByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultSousCategorieShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the sousCategorieList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultSousCategorieShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultSousCategorieShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the sousCategorieList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultSousCategorieShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultSousCategorieShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the sousCategorieList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultSousCategorieShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifiePar is not null
        defaultSousCategorieShouldBeFound("modifiePar.specified=true");

        // Get all the sousCategorieList where modifiePar is null
        defaultSousCategorieShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllSousCategoriesByModifieParContainsSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultSousCategorieShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the sousCategorieList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultSousCategorieShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllSousCategoriesByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultSousCategorieShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the sousCategorieList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultSousCategorieShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllSousCategoriesByProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);
        Produit produit = ProduitResourceIT.createEntity(em);
        em.persist(produit);
        em.flush();
        sousCategorie.addProduit(produit);
        sousCategorieRepository.saveAndFlush(sousCategorie);
        Long produitId = produit.getId();

        // Get all the sousCategorieList where produit equals to produitId
        defaultSousCategorieShouldBeFound("produitId.equals=" + produitId);

        // Get all the sousCategorieList where produit equals to produitId + 1
        defaultSousCategorieShouldNotBeFound("produitId.equals=" + (produitId + 1));
    }


    @Test
    @Transactional
    public void getAllSousCategoriesByStockIsEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);
        Stock stock = StockResourceIT.createEntity(em);
        em.persist(stock);
        em.flush();
        sousCategorie.addStock(stock);
        sousCategorieRepository.saveAndFlush(sousCategorie);
        Long stockId = stock.getId();

        // Get all the sousCategorieList where stock equals to stockId
        defaultSousCategorieShouldBeFound("stockId.equals=" + stockId);

        // Get all the sousCategorieList where stock equals to stockId + 1
        defaultSousCategorieShouldNotBeFound("stockId.equals=" + (stockId + 1));
    }


    @Test
    @Transactional
    public void getAllSousCategoriesByCategorieIsEqualToSomething() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);
        Categorie categorie = CategorieResourceIT.createEntity(em);
        em.persist(categorie);
        em.flush();
        sousCategorie.setCategorie(categorie);
        sousCategorieRepository.saveAndFlush(sousCategorie);
        Long categorieId = categorie.getId();

        // Get all the sousCategorieList where categorie equals to categorieId
        defaultSousCategorieShouldBeFound("categorieId.equals=" + categorieId);

        // Get all the sousCategorieList where categorie equals to categorieId + 1
        defaultSousCategorieShouldNotBeFound("categorieId.equals=" + (categorieId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSousCategorieShouldBeFound(String filter) throws Exception {
        restSousCategorieMockMvc.perform(get("/api/sous-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sousCategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));

        // Check, that the count call also returns 1
        restSousCategorieMockMvc.perform(get("/api/sous-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSousCategorieShouldNotBeFound(String filter) throws Exception {
        restSousCategorieMockMvc.perform(get("/api/sous-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSousCategorieMockMvc.perform(get("/api/sous-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSousCategorie() throws Exception {
        // Get the sousCategorie
        restSousCategorieMockMvc.perform(get("/api/sous-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSousCategorie() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        int databaseSizeBeforeUpdate = sousCategorieRepository.findAll().size();

        // Update the sousCategorie
        SousCategorie updatedSousCategorie = sousCategorieRepository.findById(sousCategorie.getId()).get();
        // Disconnect from session so that the updates on updatedSousCategorie are not directly saved in db
        em.detach(updatedSousCategorie);
        updatedSousCategorie
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .position(UPDATED_POSITION)
            .etat(UPDATED_ETAT)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(updatedSousCategorie);

        restSousCategorieMockMvc.perform(put("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isOk());

        // Validate the SousCategorie in the database
        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeUpdate);
        SousCategorie testSousCategorie = sousCategorieList.get(sousCategorieList.size() - 1);
        assertThat(testSousCategorie.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSousCategorie.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSousCategorie.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testSousCategorie.isEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testSousCategorie.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSousCategorie.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testSousCategorie.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testSousCategorie.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testSousCategorie.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testSousCategorie.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the SousCategorie in Elasticsearch
        verify(mockSousCategorieSearchRepository, times(1)).save(testSousCategorie);
    }

    @Test
    @Transactional
    public void updateNonExistingSousCategorie() throws Exception {
        int databaseSizeBeforeUpdate = sousCategorieRepository.findAll().size();

        // Create the SousCategorie
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(sousCategorie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSousCategorieMockMvc.perform(put("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SousCategorie in the database
        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SousCategorie in Elasticsearch
        verify(mockSousCategorieSearchRepository, times(0)).save(sousCategorie);
    }

    @Test
    @Transactional
    public void deleteSousCategorie() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        int databaseSizeBeforeDelete = sousCategorieRepository.findAll().size();

        // Delete the sousCategorie
        restSousCategorieMockMvc.perform(delete("/api/sous-categories/{id}", sousCategorie.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SousCategorie in Elasticsearch
        verify(mockSousCategorieSearchRepository, times(1)).deleteById(sousCategorie.getId());
    }

    @Test
    @Transactional
    public void searchSousCategorie() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);
        when(mockSousCategorieSearchRepository.search(queryStringQuery("id:" + sousCategorie.getId())))
            .thenReturn(Collections.singletonList(sousCategorie));

        // Search the sousCategorie
        restSousCategorieMockMvc.perform(get("/api/_search/sous-categories?query=id:" + sousCategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sousCategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
