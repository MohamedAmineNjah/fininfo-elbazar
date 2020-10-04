package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.Categorie;
import org.fininfo.elbazar.domain.SousCategorie;
import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.domain.Stock;
import org.fininfo.elbazar.repository.CategorieRepository;
import org.fininfo.elbazar.repository.search.CategorieSearchRepository;
import org.fininfo.elbazar.service.CategorieService;
import org.fininfo.elbazar.service.dto.CategorieDTO;
import org.fininfo.elbazar.service.mapper.CategorieMapper;
import org.fininfo.elbazar.service.dto.CategorieCriteria;
import org.fininfo.elbazar.service.CategorieQueryService;

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
 * Integration tests for the {@link CategorieResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CategorieResourceIT {

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
    private CategorieRepository categorieRepository;

    @Autowired
    private CategorieMapper categorieMapper;

    @Autowired
    private CategorieService categorieService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.CategorieSearchRepositoryMockConfiguration
     */
    @Autowired
    private CategorieSearchRepository mockCategorieSearchRepository;

    @Autowired
    private CategorieQueryService categorieQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieMockMvc;

    private Categorie categorie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorie createEntity(EntityManager em) {
        Categorie categorie = new Categorie()
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
        return categorie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorie createUpdatedEntity(EntityManager em) {
        Categorie categorie = new Categorie()
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
        return categorie;
    }

    @BeforeEach
    public void initTest() {
        categorie = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorie() throws Exception {
        int databaseSizeBeforeCreate = categorieRepository.findAll().size();
        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);
        restCategorieMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
            .andExpect(status().isCreated());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeCreate + 1);
        Categorie testCategorie = categorieList.get(categorieList.size() - 1);
        assertThat(testCategorie.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCategorie.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCategorie.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testCategorie.isEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testCategorie.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCategorie.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testCategorie.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testCategorie.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testCategorie.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testCategorie.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the Categorie in Elasticsearch
        verify(mockCategorieSearchRepository, times(1)).save(testCategorie);
    }

    @Test
    @Transactional
    public void createCategorieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categorieRepository.findAll().size();

        // Create the Categorie with an existing ID
        categorie.setId(1L);
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeCreate);

        // Validate the Categorie in Elasticsearch
        verify(mockCategorieSearchRepository, times(0)).save(categorie);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieRepository.findAll().size();
        // set the field null
        categorie.setNom(null);

        // Create the Categorie, which fails.
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);


        restCategorieMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
            .andExpect(status().isBadRequest());

        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieRepository.findAll().size();
        // set the field null
        categorie.setDescription(null);

        // Create the Categorie, which fails.
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);


        restCategorieMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
            .andExpect(status().isBadRequest());

        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieRepository.findAll().size();
        // set the field null
        categorie.setPosition(null);

        // Create the Categorie, which fails.
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);


        restCategorieMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
            .andExpect(status().isBadRequest());

        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategories() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList
        restCategorieMockMvc.perform(get("/api/categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorie.getId().intValue())))
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
    public void getCategorie() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get the categorie
        restCategorieMockMvc.perform(get("/api/categories/{id}", categorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorie.getId().intValue()))
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
    public void getCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        Long id = categorie.getId();

        defaultCategorieShouldBeFound("id.equals=" + id);
        defaultCategorieShouldNotBeFound("id.notEquals=" + id);

        defaultCategorieShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategorieShouldNotBeFound("id.greaterThan=" + id);

        defaultCategorieShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategorieShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCategoriesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where nom equals to DEFAULT_NOM
        defaultCategorieShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the categorieList where nom equals to UPDATED_NOM
        defaultCategorieShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where nom not equals to DEFAULT_NOM
        defaultCategorieShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the categorieList where nom not equals to UPDATED_NOM
        defaultCategorieShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultCategorieShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the categorieList where nom equals to UPDATED_NOM
        defaultCategorieShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where nom is not null
        defaultCategorieShouldBeFound("nom.specified=true");

        // Get all the categorieList where nom is null
        defaultCategorieShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByNomContainsSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where nom contains DEFAULT_NOM
        defaultCategorieShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the categorieList where nom contains UPDATED_NOM
        defaultCategorieShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where nom does not contain DEFAULT_NOM
        defaultCategorieShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the categorieList where nom does not contain UPDATED_NOM
        defaultCategorieShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where description equals to DEFAULT_DESCRIPTION
        defaultCategorieShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the categorieList where description equals to UPDATED_DESCRIPTION
        defaultCategorieShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where description not equals to DEFAULT_DESCRIPTION
        defaultCategorieShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the categorieList where description not equals to UPDATED_DESCRIPTION
        defaultCategorieShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCategorieShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the categorieList where description equals to UPDATED_DESCRIPTION
        defaultCategorieShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where description is not null
        defaultCategorieShouldBeFound("description.specified=true");

        // Get all the categorieList where description is null
        defaultCategorieShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where description contains DEFAULT_DESCRIPTION
        defaultCategorieShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the categorieList where description contains UPDATED_DESCRIPTION
        defaultCategorieShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where description does not contain DEFAULT_DESCRIPTION
        defaultCategorieShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the categorieList where description does not contain UPDATED_DESCRIPTION
        defaultCategorieShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCategoriesByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where position equals to DEFAULT_POSITION
        defaultCategorieShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the categorieList where position equals to UPDATED_POSITION
        defaultCategorieShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where position not equals to DEFAULT_POSITION
        defaultCategorieShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the categorieList where position not equals to UPDATED_POSITION
        defaultCategorieShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultCategorieShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the categorieList where position equals to UPDATED_POSITION
        defaultCategorieShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where position is not null
        defaultCategorieShouldBeFound("position.specified=true");

        // Get all the categorieList where position is null
        defaultCategorieShouldNotBeFound("position.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where position is greater than or equal to DEFAULT_POSITION
        defaultCategorieShouldBeFound("position.greaterThanOrEqual=" + DEFAULT_POSITION);

        // Get all the categorieList where position is greater than or equal to UPDATED_POSITION
        defaultCategorieShouldNotBeFound("position.greaterThanOrEqual=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where position is less than or equal to DEFAULT_POSITION
        defaultCategorieShouldBeFound("position.lessThanOrEqual=" + DEFAULT_POSITION);

        // Get all the categorieList where position is less than or equal to SMALLER_POSITION
        defaultCategorieShouldNotBeFound("position.lessThanOrEqual=" + SMALLER_POSITION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsLessThanSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where position is less than DEFAULT_POSITION
        defaultCategorieShouldNotBeFound("position.lessThan=" + DEFAULT_POSITION);

        // Get all the categorieList where position is less than UPDATED_POSITION
        defaultCategorieShouldBeFound("position.lessThan=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where position is greater than DEFAULT_POSITION
        defaultCategorieShouldNotBeFound("position.greaterThan=" + DEFAULT_POSITION);

        // Get all the categorieList where position is greater than SMALLER_POSITION
        defaultCategorieShouldBeFound("position.greaterThan=" + SMALLER_POSITION);
    }


    @Test
    @Transactional
    public void getAllCategoriesByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where etat equals to DEFAULT_ETAT
        defaultCategorieShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the categorieList where etat equals to UPDATED_ETAT
        defaultCategorieShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByEtatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where etat not equals to DEFAULT_ETAT
        defaultCategorieShouldNotBeFound("etat.notEquals=" + DEFAULT_ETAT);

        // Get all the categorieList where etat not equals to UPDATED_ETAT
        defaultCategorieShouldBeFound("etat.notEquals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultCategorieShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the categorieList where etat equals to UPDATED_ETAT
        defaultCategorieShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where etat is not null
        defaultCategorieShouldBeFound("etat.specified=true");

        // Get all the categorieList where etat is null
        defaultCategorieShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creeLe equals to DEFAULT_CREE_LE
        defaultCategorieShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the categorieList where creeLe equals to UPDATED_CREE_LE
        defaultCategorieShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creeLe not equals to DEFAULT_CREE_LE
        defaultCategorieShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the categorieList where creeLe not equals to UPDATED_CREE_LE
        defaultCategorieShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultCategorieShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the categorieList where creeLe equals to UPDATED_CREE_LE
        defaultCategorieShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creeLe is not null
        defaultCategorieShouldBeFound("creeLe.specified=true");

        // Get all the categorieList where creeLe is null
        defaultCategorieShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultCategorieShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the categorieList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultCategorieShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultCategorieShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the categorieList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultCategorieShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creeLe is less than DEFAULT_CREE_LE
        defaultCategorieShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the categorieList where creeLe is less than UPDATED_CREE_LE
        defaultCategorieShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creeLe is greater than DEFAULT_CREE_LE
        defaultCategorieShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the categorieList where creeLe is greater than SMALLER_CREE_LE
        defaultCategorieShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllCategoriesByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creePar equals to DEFAULT_CREE_PAR
        defaultCategorieShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the categorieList where creePar equals to UPDATED_CREE_PAR
        defaultCategorieShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creePar not equals to DEFAULT_CREE_PAR
        defaultCategorieShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the categorieList where creePar not equals to UPDATED_CREE_PAR
        defaultCategorieShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultCategorieShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the categorieList where creePar equals to UPDATED_CREE_PAR
        defaultCategorieShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creePar is not null
        defaultCategorieShouldBeFound("creePar.specified=true");

        // Get all the categorieList where creePar is null
        defaultCategorieShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByCreeParContainsSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creePar contains DEFAULT_CREE_PAR
        defaultCategorieShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the categorieList where creePar contains UPDATED_CREE_PAR
        defaultCategorieShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where creePar does not contain DEFAULT_CREE_PAR
        defaultCategorieShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the categorieList where creePar does not contain UPDATED_CREE_PAR
        defaultCategorieShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllCategoriesByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultCategorieShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the categorieList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultCategorieShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultCategorieShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the categorieList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultCategorieShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultCategorieShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the categorieList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultCategorieShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifieLe is not null
        defaultCategorieShouldBeFound("modifieLe.specified=true");

        // Get all the categorieList where modifieLe is null
        defaultCategorieShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultCategorieShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the categorieList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultCategorieShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultCategorieShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the categorieList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultCategorieShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultCategorieShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the categorieList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultCategorieShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultCategorieShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the categorieList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultCategorieShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllCategoriesByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultCategorieShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the categorieList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultCategorieShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultCategorieShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the categorieList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultCategorieShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultCategorieShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the categorieList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultCategorieShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifiePar is not null
        defaultCategorieShouldBeFound("modifiePar.specified=true");

        // Get all the categorieList where modifiePar is null
        defaultCategorieShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByModifieParContainsSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultCategorieShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the categorieList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultCategorieShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultCategorieShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the categorieList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultCategorieShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllCategoriesBySousCategorieIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);
        SousCategorie sousCategorie = SousCategorieResourceIT.createEntity(em);
        em.persist(sousCategorie);
        em.flush();
        categorie.addSousCategorie(sousCategorie);
        categorieRepository.saveAndFlush(categorie);
        Long sousCategorieId = sousCategorie.getId();

        // Get all the categorieList where sousCategorie equals to sousCategorieId
        defaultCategorieShouldBeFound("sousCategorieId.equals=" + sousCategorieId);

        // Get all the categorieList where sousCategorie equals to sousCategorieId + 1
        defaultCategorieShouldNotBeFound("sousCategorieId.equals=" + (sousCategorieId + 1));
    }


    @Test
    @Transactional
    public void getAllCategoriesByProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);
        Produit produit = ProduitResourceIT.createEntity(em);
        em.persist(produit);
        em.flush();
        categorie.addProduit(produit);
        categorieRepository.saveAndFlush(categorie);
        Long produitId = produit.getId();

        // Get all the categorieList where produit equals to produitId
        defaultCategorieShouldBeFound("produitId.equals=" + produitId);

        // Get all the categorieList where produit equals to produitId + 1
        defaultCategorieShouldNotBeFound("produitId.equals=" + (produitId + 1));
    }


    @Test
    @Transactional
    public void getAllCategoriesByStockIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);
        Stock stock = StockResourceIT.createEntity(em);
        em.persist(stock);
        em.flush();
        categorie.addStock(stock);
        categorieRepository.saveAndFlush(categorie);
        Long stockId = stock.getId();

        // Get all the categorieList where stock equals to stockId
        defaultCategorieShouldBeFound("stockId.equals=" + stockId);

        // Get all the categorieList where stock equals to stockId + 1
        defaultCategorieShouldNotBeFound("stockId.equals=" + (stockId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategorieShouldBeFound(String filter) throws Exception {
        restCategorieMockMvc.perform(get("/api/categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorie.getId().intValue())))
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
        restCategorieMockMvc.perform(get("/api/categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategorieShouldNotBeFound(String filter) throws Exception {
        restCategorieMockMvc.perform(get("/api/categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategorieMockMvc.perform(get("/api/categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCategorie() throws Exception {
        // Get the categorie
        restCategorieMockMvc.perform(get("/api/categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorie() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();

        // Update the categorie
        Categorie updatedCategorie = categorieRepository.findById(categorie.getId()).get();
        // Disconnect from session so that the updates on updatedCategorie are not directly saved in db
        em.detach(updatedCategorie);
        updatedCategorie
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
        CategorieDTO categorieDTO = categorieMapper.toDto(updatedCategorie);

        restCategorieMockMvc.perform(put("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
            .andExpect(status().isOk());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
        Categorie testCategorie = categorieList.get(categorieList.size() - 1);
        assertThat(testCategorie.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCategorie.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategorie.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testCategorie.isEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testCategorie.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCategorie.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testCategorie.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testCategorie.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testCategorie.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testCategorie.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the Categorie in Elasticsearch
        verify(mockCategorieSearchRepository, times(1)).save(testCategorie);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorie() throws Exception {
        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieMockMvc.perform(put("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Categorie in Elasticsearch
        verify(mockCategorieSearchRepository, times(0)).save(categorie);
    }

    @Test
    @Transactional
    public void deleteCategorie() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        int databaseSizeBeforeDelete = categorieRepository.findAll().size();

        // Delete the categorie
        restCategorieMockMvc.perform(delete("/api/categories/{id}", categorie.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Categorie in Elasticsearch
        verify(mockCategorieSearchRepository, times(1)).deleteById(categorie.getId());
    }

    @Test
    @Transactional
    public void searchCategorie() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);
        when(mockCategorieSearchRepository.search(queryStringQuery("id:" + categorie.getId())))
            .thenReturn(Collections.singletonList(categorie));

        // Search the categorie
        restCategorieMockMvc.perform(get("/api/_search/categories?query=id:" + categorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorie.getId().intValue())))
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
