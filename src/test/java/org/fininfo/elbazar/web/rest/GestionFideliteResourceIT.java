package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.GestionFidelite;
import org.fininfo.elbazar.repository.GestionFideliteRepository;
import org.fininfo.elbazar.repository.search.GestionFideliteSearchRepository;
import org.fininfo.elbazar.service.GestionFideliteService;
import org.fininfo.elbazar.service.dto.GestionFideliteDTO;
import org.fininfo.elbazar.service.mapper.GestionFideliteMapper;
import org.fininfo.elbazar.service.dto.GestionFideliteCriteria;
import org.fininfo.elbazar.service.GestionFideliteQueryService;

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

import org.fininfo.elbazar.domain.enumeration.Devise;
/**
 * Integration tests for the {@link GestionFideliteResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GestionFideliteResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINTS = 1;
    private static final Integer UPDATED_POINTS = 2;
    private static final Integer SMALLER_POINTS = 1 - 1;

    private static final Double DEFAULT_VALEUR = 1D;
    private static final Double UPDATED_VALEUR = 2D;
    private static final Double SMALLER_VALEUR = 1D - 1D;

    private static final Integer DEFAULT_SILVER_MIN = 1;
    private static final Integer UPDATED_SILVER_MIN = 2;
    private static final Integer SMALLER_SILVER_MIN = 1 - 1;

    private static final Integer DEFAULT_SILVER_MAX = 1;
    private static final Integer UPDATED_SILVER_MAX = 2;
    private static final Integer SMALLER_SILVER_MAX = 1 - 1;

    private static final Integer DEFAULT_GOLD_MIN = 1;
    private static final Integer UPDATED_GOLD_MIN = 2;
    private static final Integer SMALLER_GOLD_MIN = 1 - 1;

    private static final Integer DEFAULT_GOLD_MAX = 1;
    private static final Integer UPDATED_GOLD_MAX = 2;
    private static final Integer SMALLER_GOLD_MAX = 1 - 1;

    private static final Integer DEFAULT_PLATINIUM_MIN = 1;
    private static final Integer UPDATED_PLATINIUM_MIN = 2;
    private static final Integer SMALLER_PLATINIUM_MIN = 1 - 1;

    private static final Integer DEFAULT_PLATINIUM_MAX = 1;
    private static final Integer UPDATED_PLATINIUM_MAX = 2;
    private static final Integer SMALLER_PLATINIUM_MAX = 1 - 1;

    private static final Devise DEFAULT_DEVISE = Devise.TND;
    private static final Devise UPDATED_DEVISE = Devise.EUR;

    private static final Boolean DEFAULT_ETAT = false;
    private static final Boolean UPDATED_ETAT = true;

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
    private GestionFideliteRepository gestionFideliteRepository;

    @Autowired
    private GestionFideliteMapper gestionFideliteMapper;

    @Autowired
    private GestionFideliteService gestionFideliteService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.GestionFideliteSearchRepositoryMockConfiguration
     */
    @Autowired
    private GestionFideliteSearchRepository mockGestionFideliteSearchRepository;

    @Autowired
    private GestionFideliteQueryService gestionFideliteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGestionFideliteMockMvc;

    private GestionFidelite gestionFidelite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GestionFidelite createEntity(EntityManager em) {
        GestionFidelite gestionFidelite = new GestionFidelite()
            .nom(DEFAULT_NOM)
            .points(DEFAULT_POINTS)
            .valeur(DEFAULT_VALEUR)
            .silverMin(DEFAULT_SILVER_MIN)
            .silverMax(DEFAULT_SILVER_MAX)
            .goldMin(DEFAULT_GOLD_MIN)
            .goldMax(DEFAULT_GOLD_MAX)
            .platiniumMin(DEFAULT_PLATINIUM_MIN)
            .platiniumMax(DEFAULT_PLATINIUM_MAX)
            .devise(DEFAULT_DEVISE)
            .etat(DEFAULT_ETAT)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return gestionFidelite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GestionFidelite createUpdatedEntity(EntityManager em) {
        GestionFidelite gestionFidelite = new GestionFidelite()
            .nom(UPDATED_NOM)
            .points(UPDATED_POINTS)
            .valeur(UPDATED_VALEUR)
            .silverMin(UPDATED_SILVER_MIN)
            .silverMax(UPDATED_SILVER_MAX)
            .goldMin(UPDATED_GOLD_MIN)
            .goldMax(UPDATED_GOLD_MAX)
            .platiniumMin(UPDATED_PLATINIUM_MIN)
            .platiniumMax(UPDATED_PLATINIUM_MAX)
            .devise(UPDATED_DEVISE)
            .etat(UPDATED_ETAT)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return gestionFidelite;
    }

    @BeforeEach
    public void initTest() {
        gestionFidelite = createEntity(em);
    }

    @Test
    @Transactional
    public void createGestionFidelite() throws Exception {
        int databaseSizeBeforeCreate = gestionFideliteRepository.findAll().size();
        // Create the GestionFidelite
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(gestionFidelite);
        restGestionFideliteMockMvc.perform(post("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isCreated());

        // Validate the GestionFidelite in the database
        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeCreate + 1);
        GestionFidelite testGestionFidelite = gestionFideliteList.get(gestionFideliteList.size() - 1);
        assertThat(testGestionFidelite.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testGestionFidelite.getPoints()).isEqualTo(DEFAULT_POINTS);
        assertThat(testGestionFidelite.getValeur()).isEqualTo(DEFAULT_VALEUR);
        assertThat(testGestionFidelite.getSilverMin()).isEqualTo(DEFAULT_SILVER_MIN);
        assertThat(testGestionFidelite.getSilverMax()).isEqualTo(DEFAULT_SILVER_MAX);
        assertThat(testGestionFidelite.getGoldMin()).isEqualTo(DEFAULT_GOLD_MIN);
        assertThat(testGestionFidelite.getGoldMax()).isEqualTo(DEFAULT_GOLD_MAX);
        assertThat(testGestionFidelite.getPlatiniumMin()).isEqualTo(DEFAULT_PLATINIUM_MIN);
        assertThat(testGestionFidelite.getPlatiniumMax()).isEqualTo(DEFAULT_PLATINIUM_MAX);
        assertThat(testGestionFidelite.getDevise()).isEqualTo(DEFAULT_DEVISE);
        assertThat(testGestionFidelite.isEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testGestionFidelite.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testGestionFidelite.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testGestionFidelite.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testGestionFidelite.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the GestionFidelite in Elasticsearch
        verify(mockGestionFideliteSearchRepository, times(1)).save(testGestionFidelite);
    }

    @Test
    @Transactional
    public void createGestionFideliteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gestionFideliteRepository.findAll().size();

        // Create the GestionFidelite with an existing ID
        gestionFidelite.setId(1L);
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(gestionFidelite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGestionFideliteMockMvc.perform(post("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GestionFidelite in the database
        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeCreate);

        // Validate the GestionFidelite in Elasticsearch
        verify(mockGestionFideliteSearchRepository, times(0)).save(gestionFidelite);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = gestionFideliteRepository.findAll().size();
        // set the field null
        gestionFidelite.setNom(null);

        // Create the GestionFidelite, which fails.
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(gestionFidelite);


        restGestionFideliteMockMvc.perform(post("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isBadRequest());

        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = gestionFideliteRepository.findAll().size();
        // set the field null
        gestionFidelite.setPoints(null);

        // Create the GestionFidelite, which fails.
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(gestionFidelite);


        restGestionFideliteMockMvc.perform(post("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isBadRequest());

        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValeurIsRequired() throws Exception {
        int databaseSizeBeforeTest = gestionFideliteRepository.findAll().size();
        // set the field null
        gestionFidelite.setValeur(null);

        // Create the GestionFidelite, which fails.
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(gestionFidelite);


        restGestionFideliteMockMvc.perform(post("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isBadRequest());

        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSilverMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = gestionFideliteRepository.findAll().size();
        // set the field null
        gestionFidelite.setSilverMin(null);

        // Create the GestionFidelite, which fails.
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(gestionFidelite);


        restGestionFideliteMockMvc.perform(post("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isBadRequest());

        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSilverMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = gestionFideliteRepository.findAll().size();
        // set the field null
        gestionFidelite.setSilverMax(null);

        // Create the GestionFidelite, which fails.
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(gestionFidelite);


        restGestionFideliteMockMvc.perform(post("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isBadRequest());

        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGoldMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = gestionFideliteRepository.findAll().size();
        // set the field null
        gestionFidelite.setGoldMin(null);

        // Create the GestionFidelite, which fails.
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(gestionFidelite);


        restGestionFideliteMockMvc.perform(post("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isBadRequest());

        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGoldMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = gestionFideliteRepository.findAll().size();
        // set the field null
        gestionFidelite.setGoldMax(null);

        // Create the GestionFidelite, which fails.
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(gestionFidelite);


        restGestionFideliteMockMvc.perform(post("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isBadRequest());

        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlatiniumMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = gestionFideliteRepository.findAll().size();
        // set the field null
        gestionFidelite.setPlatiniumMin(null);

        // Create the GestionFidelite, which fails.
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(gestionFidelite);


        restGestionFideliteMockMvc.perform(post("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isBadRequest());

        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlatiniumMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = gestionFideliteRepository.findAll().size();
        // set the field null
        gestionFidelite.setPlatiniumMax(null);

        // Create the GestionFidelite, which fails.
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(gestionFidelite);


        restGestionFideliteMockMvc.perform(post("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isBadRequest());

        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGestionFidelites() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList
        restGestionFideliteMockMvc.perform(get("/api/gestion-fidelites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gestionFidelite.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].silverMin").value(hasItem(DEFAULT_SILVER_MIN)))
            .andExpect(jsonPath("$.[*].silverMax").value(hasItem(DEFAULT_SILVER_MAX)))
            .andExpect(jsonPath("$.[*].goldMin").value(hasItem(DEFAULT_GOLD_MIN)))
            .andExpect(jsonPath("$.[*].goldMax").value(hasItem(DEFAULT_GOLD_MAX)))
            .andExpect(jsonPath("$.[*].platiniumMin").value(hasItem(DEFAULT_PLATINIUM_MIN)))
            .andExpect(jsonPath("$.[*].platiniumMax").value(hasItem(DEFAULT_PLATINIUM_MAX)))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getGestionFidelite() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get the gestionFidelite
        restGestionFideliteMockMvc.perform(get("/api/gestion-fidelites/{id}", gestionFidelite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gestionFidelite.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.doubleValue()))
            .andExpect(jsonPath("$.silverMin").value(DEFAULT_SILVER_MIN))
            .andExpect(jsonPath("$.silverMax").value(DEFAULT_SILVER_MAX))
            .andExpect(jsonPath("$.goldMin").value(DEFAULT_GOLD_MIN))
            .andExpect(jsonPath("$.goldMax").value(DEFAULT_GOLD_MAX))
            .andExpect(jsonPath("$.platiniumMin").value(DEFAULT_PLATINIUM_MIN))
            .andExpect(jsonPath("$.platiniumMax").value(DEFAULT_PLATINIUM_MAX))
            .andExpect(jsonPath("$.devise").value(DEFAULT_DEVISE.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.booleanValue()))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }


    @Test
    @Transactional
    public void getGestionFidelitesByIdFiltering() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        Long id = gestionFidelite.getId();

        defaultGestionFideliteShouldBeFound("id.equals=" + id);
        defaultGestionFideliteShouldNotBeFound("id.notEquals=" + id);

        defaultGestionFideliteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGestionFideliteShouldNotBeFound("id.greaterThan=" + id);

        defaultGestionFideliteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGestionFideliteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where nom equals to DEFAULT_NOM
        defaultGestionFideliteShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the gestionFideliteList where nom equals to UPDATED_NOM
        defaultGestionFideliteShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where nom not equals to DEFAULT_NOM
        defaultGestionFideliteShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the gestionFideliteList where nom not equals to UPDATED_NOM
        defaultGestionFideliteShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultGestionFideliteShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the gestionFideliteList where nom equals to UPDATED_NOM
        defaultGestionFideliteShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where nom is not null
        defaultGestionFideliteShouldBeFound("nom.specified=true");

        // Get all the gestionFideliteList where nom is null
        defaultGestionFideliteShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllGestionFidelitesByNomContainsSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where nom contains DEFAULT_NOM
        defaultGestionFideliteShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the gestionFideliteList where nom contains UPDATED_NOM
        defaultGestionFideliteShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where nom does not contain DEFAULT_NOM
        defaultGestionFideliteShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the gestionFideliteList where nom does not contain UPDATED_NOM
        defaultGestionFideliteShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesByPointsIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where points equals to DEFAULT_POINTS
        defaultGestionFideliteShouldBeFound("points.equals=" + DEFAULT_POINTS);

        // Get all the gestionFideliteList where points equals to UPDATED_POINTS
        defaultGestionFideliteShouldNotBeFound("points.equals=" + UPDATED_POINTS);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPointsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where points not equals to DEFAULT_POINTS
        defaultGestionFideliteShouldNotBeFound("points.notEquals=" + DEFAULT_POINTS);

        // Get all the gestionFideliteList where points not equals to UPDATED_POINTS
        defaultGestionFideliteShouldBeFound("points.notEquals=" + UPDATED_POINTS);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPointsIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where points in DEFAULT_POINTS or UPDATED_POINTS
        defaultGestionFideliteShouldBeFound("points.in=" + DEFAULT_POINTS + "," + UPDATED_POINTS);

        // Get all the gestionFideliteList where points equals to UPDATED_POINTS
        defaultGestionFideliteShouldNotBeFound("points.in=" + UPDATED_POINTS);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPointsIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where points is not null
        defaultGestionFideliteShouldBeFound("points.specified=true");

        // Get all the gestionFideliteList where points is null
        defaultGestionFideliteShouldNotBeFound("points.specified=false");
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPointsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where points is greater than or equal to DEFAULT_POINTS
        defaultGestionFideliteShouldBeFound("points.greaterThanOrEqual=" + DEFAULT_POINTS);

        // Get all the gestionFideliteList where points is greater than or equal to UPDATED_POINTS
        defaultGestionFideliteShouldNotBeFound("points.greaterThanOrEqual=" + UPDATED_POINTS);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPointsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where points is less than or equal to DEFAULT_POINTS
        defaultGestionFideliteShouldBeFound("points.lessThanOrEqual=" + DEFAULT_POINTS);

        // Get all the gestionFideliteList where points is less than or equal to SMALLER_POINTS
        defaultGestionFideliteShouldNotBeFound("points.lessThanOrEqual=" + SMALLER_POINTS);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPointsIsLessThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where points is less than DEFAULT_POINTS
        defaultGestionFideliteShouldNotBeFound("points.lessThan=" + DEFAULT_POINTS);

        // Get all the gestionFideliteList where points is less than UPDATED_POINTS
        defaultGestionFideliteShouldBeFound("points.lessThan=" + UPDATED_POINTS);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPointsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where points is greater than DEFAULT_POINTS
        defaultGestionFideliteShouldNotBeFound("points.greaterThan=" + DEFAULT_POINTS);

        // Get all the gestionFideliteList where points is greater than SMALLER_POINTS
        defaultGestionFideliteShouldBeFound("points.greaterThan=" + SMALLER_POINTS);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesByValeurIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where valeur equals to DEFAULT_VALEUR
        defaultGestionFideliteShouldBeFound("valeur.equals=" + DEFAULT_VALEUR);

        // Get all the gestionFideliteList where valeur equals to UPDATED_VALEUR
        defaultGestionFideliteShouldNotBeFound("valeur.equals=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByValeurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where valeur not equals to DEFAULT_VALEUR
        defaultGestionFideliteShouldNotBeFound("valeur.notEquals=" + DEFAULT_VALEUR);

        // Get all the gestionFideliteList where valeur not equals to UPDATED_VALEUR
        defaultGestionFideliteShouldBeFound("valeur.notEquals=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByValeurIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where valeur in DEFAULT_VALEUR or UPDATED_VALEUR
        defaultGestionFideliteShouldBeFound("valeur.in=" + DEFAULT_VALEUR + "," + UPDATED_VALEUR);

        // Get all the gestionFideliteList where valeur equals to UPDATED_VALEUR
        defaultGestionFideliteShouldNotBeFound("valeur.in=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByValeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where valeur is not null
        defaultGestionFideliteShouldBeFound("valeur.specified=true");

        // Get all the gestionFideliteList where valeur is null
        defaultGestionFideliteShouldNotBeFound("valeur.specified=false");
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByValeurIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where valeur is greater than or equal to DEFAULT_VALEUR
        defaultGestionFideliteShouldBeFound("valeur.greaterThanOrEqual=" + DEFAULT_VALEUR);

        // Get all the gestionFideliteList where valeur is greater than or equal to UPDATED_VALEUR
        defaultGestionFideliteShouldNotBeFound("valeur.greaterThanOrEqual=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByValeurIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where valeur is less than or equal to DEFAULT_VALEUR
        defaultGestionFideliteShouldBeFound("valeur.lessThanOrEqual=" + DEFAULT_VALEUR);

        // Get all the gestionFideliteList where valeur is less than or equal to SMALLER_VALEUR
        defaultGestionFideliteShouldNotBeFound("valeur.lessThanOrEqual=" + SMALLER_VALEUR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByValeurIsLessThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where valeur is less than DEFAULT_VALEUR
        defaultGestionFideliteShouldNotBeFound("valeur.lessThan=" + DEFAULT_VALEUR);

        // Get all the gestionFideliteList where valeur is less than UPDATED_VALEUR
        defaultGestionFideliteShouldBeFound("valeur.lessThan=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByValeurIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where valeur is greater than DEFAULT_VALEUR
        defaultGestionFideliteShouldNotBeFound("valeur.greaterThan=" + DEFAULT_VALEUR);

        // Get all the gestionFideliteList where valeur is greater than SMALLER_VALEUR
        defaultGestionFideliteShouldBeFound("valeur.greaterThan=" + SMALLER_VALEUR);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMinIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMin equals to DEFAULT_SILVER_MIN
        defaultGestionFideliteShouldBeFound("silverMin.equals=" + DEFAULT_SILVER_MIN);

        // Get all the gestionFideliteList where silverMin equals to UPDATED_SILVER_MIN
        defaultGestionFideliteShouldNotBeFound("silverMin.equals=" + UPDATED_SILVER_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMin not equals to DEFAULT_SILVER_MIN
        defaultGestionFideliteShouldNotBeFound("silverMin.notEquals=" + DEFAULT_SILVER_MIN);

        // Get all the gestionFideliteList where silverMin not equals to UPDATED_SILVER_MIN
        defaultGestionFideliteShouldBeFound("silverMin.notEquals=" + UPDATED_SILVER_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMinIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMin in DEFAULT_SILVER_MIN or UPDATED_SILVER_MIN
        defaultGestionFideliteShouldBeFound("silverMin.in=" + DEFAULT_SILVER_MIN + "," + UPDATED_SILVER_MIN);

        // Get all the gestionFideliteList where silverMin equals to UPDATED_SILVER_MIN
        defaultGestionFideliteShouldNotBeFound("silverMin.in=" + UPDATED_SILVER_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMin is not null
        defaultGestionFideliteShouldBeFound("silverMin.specified=true");

        // Get all the gestionFideliteList where silverMin is null
        defaultGestionFideliteShouldNotBeFound("silverMin.specified=false");
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMin is greater than or equal to DEFAULT_SILVER_MIN
        defaultGestionFideliteShouldBeFound("silverMin.greaterThanOrEqual=" + DEFAULT_SILVER_MIN);

        // Get all the gestionFideliteList where silverMin is greater than or equal to UPDATED_SILVER_MIN
        defaultGestionFideliteShouldNotBeFound("silverMin.greaterThanOrEqual=" + UPDATED_SILVER_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMin is less than or equal to DEFAULT_SILVER_MIN
        defaultGestionFideliteShouldBeFound("silverMin.lessThanOrEqual=" + DEFAULT_SILVER_MIN);

        // Get all the gestionFideliteList where silverMin is less than or equal to SMALLER_SILVER_MIN
        defaultGestionFideliteShouldNotBeFound("silverMin.lessThanOrEqual=" + SMALLER_SILVER_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMinIsLessThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMin is less than DEFAULT_SILVER_MIN
        defaultGestionFideliteShouldNotBeFound("silverMin.lessThan=" + DEFAULT_SILVER_MIN);

        // Get all the gestionFideliteList where silverMin is less than UPDATED_SILVER_MIN
        defaultGestionFideliteShouldBeFound("silverMin.lessThan=" + UPDATED_SILVER_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMin is greater than DEFAULT_SILVER_MIN
        defaultGestionFideliteShouldNotBeFound("silverMin.greaterThan=" + DEFAULT_SILVER_MIN);

        // Get all the gestionFideliteList where silverMin is greater than SMALLER_SILVER_MIN
        defaultGestionFideliteShouldBeFound("silverMin.greaterThan=" + SMALLER_SILVER_MIN);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMax equals to DEFAULT_SILVER_MAX
        defaultGestionFideliteShouldBeFound("silverMax.equals=" + DEFAULT_SILVER_MAX);

        // Get all the gestionFideliteList where silverMax equals to UPDATED_SILVER_MAX
        defaultGestionFideliteShouldNotBeFound("silverMax.equals=" + UPDATED_SILVER_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMax not equals to DEFAULT_SILVER_MAX
        defaultGestionFideliteShouldNotBeFound("silverMax.notEquals=" + DEFAULT_SILVER_MAX);

        // Get all the gestionFideliteList where silverMax not equals to UPDATED_SILVER_MAX
        defaultGestionFideliteShouldBeFound("silverMax.notEquals=" + UPDATED_SILVER_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMaxIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMax in DEFAULT_SILVER_MAX or UPDATED_SILVER_MAX
        defaultGestionFideliteShouldBeFound("silverMax.in=" + DEFAULT_SILVER_MAX + "," + UPDATED_SILVER_MAX);

        // Get all the gestionFideliteList where silverMax equals to UPDATED_SILVER_MAX
        defaultGestionFideliteShouldNotBeFound("silverMax.in=" + UPDATED_SILVER_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMax is not null
        defaultGestionFideliteShouldBeFound("silverMax.specified=true");

        // Get all the gestionFideliteList where silverMax is null
        defaultGestionFideliteShouldNotBeFound("silverMax.specified=false");
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMax is greater than or equal to DEFAULT_SILVER_MAX
        defaultGestionFideliteShouldBeFound("silverMax.greaterThanOrEqual=" + DEFAULT_SILVER_MAX);

        // Get all the gestionFideliteList where silverMax is greater than or equal to UPDATED_SILVER_MAX
        defaultGestionFideliteShouldNotBeFound("silverMax.greaterThanOrEqual=" + UPDATED_SILVER_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMax is less than or equal to DEFAULT_SILVER_MAX
        defaultGestionFideliteShouldBeFound("silverMax.lessThanOrEqual=" + DEFAULT_SILVER_MAX);

        // Get all the gestionFideliteList where silverMax is less than or equal to SMALLER_SILVER_MAX
        defaultGestionFideliteShouldNotBeFound("silverMax.lessThanOrEqual=" + SMALLER_SILVER_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMax is less than DEFAULT_SILVER_MAX
        defaultGestionFideliteShouldNotBeFound("silverMax.lessThan=" + DEFAULT_SILVER_MAX);

        // Get all the gestionFideliteList where silverMax is less than UPDATED_SILVER_MAX
        defaultGestionFideliteShouldBeFound("silverMax.lessThan=" + UPDATED_SILVER_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesBySilverMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where silverMax is greater than DEFAULT_SILVER_MAX
        defaultGestionFideliteShouldNotBeFound("silverMax.greaterThan=" + DEFAULT_SILVER_MAX);

        // Get all the gestionFideliteList where silverMax is greater than SMALLER_SILVER_MAX
        defaultGestionFideliteShouldBeFound("silverMax.greaterThan=" + SMALLER_SILVER_MAX);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMinIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMin equals to DEFAULT_GOLD_MIN
        defaultGestionFideliteShouldBeFound("goldMin.equals=" + DEFAULT_GOLD_MIN);

        // Get all the gestionFideliteList where goldMin equals to UPDATED_GOLD_MIN
        defaultGestionFideliteShouldNotBeFound("goldMin.equals=" + UPDATED_GOLD_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMin not equals to DEFAULT_GOLD_MIN
        defaultGestionFideliteShouldNotBeFound("goldMin.notEquals=" + DEFAULT_GOLD_MIN);

        // Get all the gestionFideliteList where goldMin not equals to UPDATED_GOLD_MIN
        defaultGestionFideliteShouldBeFound("goldMin.notEquals=" + UPDATED_GOLD_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMinIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMin in DEFAULT_GOLD_MIN or UPDATED_GOLD_MIN
        defaultGestionFideliteShouldBeFound("goldMin.in=" + DEFAULT_GOLD_MIN + "," + UPDATED_GOLD_MIN);

        // Get all the gestionFideliteList where goldMin equals to UPDATED_GOLD_MIN
        defaultGestionFideliteShouldNotBeFound("goldMin.in=" + UPDATED_GOLD_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMin is not null
        defaultGestionFideliteShouldBeFound("goldMin.specified=true");

        // Get all the gestionFideliteList where goldMin is null
        defaultGestionFideliteShouldNotBeFound("goldMin.specified=false");
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMin is greater than or equal to DEFAULT_GOLD_MIN
        defaultGestionFideliteShouldBeFound("goldMin.greaterThanOrEqual=" + DEFAULT_GOLD_MIN);

        // Get all the gestionFideliteList where goldMin is greater than or equal to UPDATED_GOLD_MIN
        defaultGestionFideliteShouldNotBeFound("goldMin.greaterThanOrEqual=" + UPDATED_GOLD_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMin is less than or equal to DEFAULT_GOLD_MIN
        defaultGestionFideliteShouldBeFound("goldMin.lessThanOrEqual=" + DEFAULT_GOLD_MIN);

        // Get all the gestionFideliteList where goldMin is less than or equal to SMALLER_GOLD_MIN
        defaultGestionFideliteShouldNotBeFound("goldMin.lessThanOrEqual=" + SMALLER_GOLD_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMinIsLessThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMin is less than DEFAULT_GOLD_MIN
        defaultGestionFideliteShouldNotBeFound("goldMin.lessThan=" + DEFAULT_GOLD_MIN);

        // Get all the gestionFideliteList where goldMin is less than UPDATED_GOLD_MIN
        defaultGestionFideliteShouldBeFound("goldMin.lessThan=" + UPDATED_GOLD_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMin is greater than DEFAULT_GOLD_MIN
        defaultGestionFideliteShouldNotBeFound("goldMin.greaterThan=" + DEFAULT_GOLD_MIN);

        // Get all the gestionFideliteList where goldMin is greater than SMALLER_GOLD_MIN
        defaultGestionFideliteShouldBeFound("goldMin.greaterThan=" + SMALLER_GOLD_MIN);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMax equals to DEFAULT_GOLD_MAX
        defaultGestionFideliteShouldBeFound("goldMax.equals=" + DEFAULT_GOLD_MAX);

        // Get all the gestionFideliteList where goldMax equals to UPDATED_GOLD_MAX
        defaultGestionFideliteShouldNotBeFound("goldMax.equals=" + UPDATED_GOLD_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMax not equals to DEFAULT_GOLD_MAX
        defaultGestionFideliteShouldNotBeFound("goldMax.notEquals=" + DEFAULT_GOLD_MAX);

        // Get all the gestionFideliteList where goldMax not equals to UPDATED_GOLD_MAX
        defaultGestionFideliteShouldBeFound("goldMax.notEquals=" + UPDATED_GOLD_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMaxIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMax in DEFAULT_GOLD_MAX or UPDATED_GOLD_MAX
        defaultGestionFideliteShouldBeFound("goldMax.in=" + DEFAULT_GOLD_MAX + "," + UPDATED_GOLD_MAX);

        // Get all the gestionFideliteList where goldMax equals to UPDATED_GOLD_MAX
        defaultGestionFideliteShouldNotBeFound("goldMax.in=" + UPDATED_GOLD_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMax is not null
        defaultGestionFideliteShouldBeFound("goldMax.specified=true");

        // Get all the gestionFideliteList where goldMax is null
        defaultGestionFideliteShouldNotBeFound("goldMax.specified=false");
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMax is greater than or equal to DEFAULT_GOLD_MAX
        defaultGestionFideliteShouldBeFound("goldMax.greaterThanOrEqual=" + DEFAULT_GOLD_MAX);

        // Get all the gestionFideliteList where goldMax is greater than or equal to UPDATED_GOLD_MAX
        defaultGestionFideliteShouldNotBeFound("goldMax.greaterThanOrEqual=" + UPDATED_GOLD_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMax is less than or equal to DEFAULT_GOLD_MAX
        defaultGestionFideliteShouldBeFound("goldMax.lessThanOrEqual=" + DEFAULT_GOLD_MAX);

        // Get all the gestionFideliteList where goldMax is less than or equal to SMALLER_GOLD_MAX
        defaultGestionFideliteShouldNotBeFound("goldMax.lessThanOrEqual=" + SMALLER_GOLD_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMax is less than DEFAULT_GOLD_MAX
        defaultGestionFideliteShouldNotBeFound("goldMax.lessThan=" + DEFAULT_GOLD_MAX);

        // Get all the gestionFideliteList where goldMax is less than UPDATED_GOLD_MAX
        defaultGestionFideliteShouldBeFound("goldMax.lessThan=" + UPDATED_GOLD_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByGoldMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where goldMax is greater than DEFAULT_GOLD_MAX
        defaultGestionFideliteShouldNotBeFound("goldMax.greaterThan=" + DEFAULT_GOLD_MAX);

        // Get all the gestionFideliteList where goldMax is greater than SMALLER_GOLD_MAX
        defaultGestionFideliteShouldBeFound("goldMax.greaterThan=" + SMALLER_GOLD_MAX);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMinIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMin equals to DEFAULT_PLATINIUM_MIN
        defaultGestionFideliteShouldBeFound("platiniumMin.equals=" + DEFAULT_PLATINIUM_MIN);

        // Get all the gestionFideliteList where platiniumMin equals to UPDATED_PLATINIUM_MIN
        defaultGestionFideliteShouldNotBeFound("platiniumMin.equals=" + UPDATED_PLATINIUM_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMin not equals to DEFAULT_PLATINIUM_MIN
        defaultGestionFideliteShouldNotBeFound("platiniumMin.notEquals=" + DEFAULT_PLATINIUM_MIN);

        // Get all the gestionFideliteList where platiniumMin not equals to UPDATED_PLATINIUM_MIN
        defaultGestionFideliteShouldBeFound("platiniumMin.notEquals=" + UPDATED_PLATINIUM_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMinIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMin in DEFAULT_PLATINIUM_MIN or UPDATED_PLATINIUM_MIN
        defaultGestionFideliteShouldBeFound("platiniumMin.in=" + DEFAULT_PLATINIUM_MIN + "," + UPDATED_PLATINIUM_MIN);

        // Get all the gestionFideliteList where platiniumMin equals to UPDATED_PLATINIUM_MIN
        defaultGestionFideliteShouldNotBeFound("platiniumMin.in=" + UPDATED_PLATINIUM_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMin is not null
        defaultGestionFideliteShouldBeFound("platiniumMin.specified=true");

        // Get all the gestionFideliteList where platiniumMin is null
        defaultGestionFideliteShouldNotBeFound("platiniumMin.specified=false");
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMin is greater than or equal to DEFAULT_PLATINIUM_MIN
        defaultGestionFideliteShouldBeFound("platiniumMin.greaterThanOrEqual=" + DEFAULT_PLATINIUM_MIN);

        // Get all the gestionFideliteList where platiniumMin is greater than or equal to UPDATED_PLATINIUM_MIN
        defaultGestionFideliteShouldNotBeFound("platiniumMin.greaterThanOrEqual=" + UPDATED_PLATINIUM_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMin is less than or equal to DEFAULT_PLATINIUM_MIN
        defaultGestionFideliteShouldBeFound("platiniumMin.lessThanOrEqual=" + DEFAULT_PLATINIUM_MIN);

        // Get all the gestionFideliteList where platiniumMin is less than or equal to SMALLER_PLATINIUM_MIN
        defaultGestionFideliteShouldNotBeFound("platiniumMin.lessThanOrEqual=" + SMALLER_PLATINIUM_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMinIsLessThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMin is less than DEFAULT_PLATINIUM_MIN
        defaultGestionFideliteShouldNotBeFound("platiniumMin.lessThan=" + DEFAULT_PLATINIUM_MIN);

        // Get all the gestionFideliteList where platiniumMin is less than UPDATED_PLATINIUM_MIN
        defaultGestionFideliteShouldBeFound("platiniumMin.lessThan=" + UPDATED_PLATINIUM_MIN);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMin is greater than DEFAULT_PLATINIUM_MIN
        defaultGestionFideliteShouldNotBeFound("platiniumMin.greaterThan=" + DEFAULT_PLATINIUM_MIN);

        // Get all the gestionFideliteList where platiniumMin is greater than SMALLER_PLATINIUM_MIN
        defaultGestionFideliteShouldBeFound("platiniumMin.greaterThan=" + SMALLER_PLATINIUM_MIN);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMax equals to DEFAULT_PLATINIUM_MAX
        defaultGestionFideliteShouldBeFound("platiniumMax.equals=" + DEFAULT_PLATINIUM_MAX);

        // Get all the gestionFideliteList where platiniumMax equals to UPDATED_PLATINIUM_MAX
        defaultGestionFideliteShouldNotBeFound("platiniumMax.equals=" + UPDATED_PLATINIUM_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMax not equals to DEFAULT_PLATINIUM_MAX
        defaultGestionFideliteShouldNotBeFound("platiniumMax.notEquals=" + DEFAULT_PLATINIUM_MAX);

        // Get all the gestionFideliteList where platiniumMax not equals to UPDATED_PLATINIUM_MAX
        defaultGestionFideliteShouldBeFound("platiniumMax.notEquals=" + UPDATED_PLATINIUM_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMaxIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMax in DEFAULT_PLATINIUM_MAX or UPDATED_PLATINIUM_MAX
        defaultGestionFideliteShouldBeFound("platiniumMax.in=" + DEFAULT_PLATINIUM_MAX + "," + UPDATED_PLATINIUM_MAX);

        // Get all the gestionFideliteList where platiniumMax equals to UPDATED_PLATINIUM_MAX
        defaultGestionFideliteShouldNotBeFound("platiniumMax.in=" + UPDATED_PLATINIUM_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMax is not null
        defaultGestionFideliteShouldBeFound("platiniumMax.specified=true");

        // Get all the gestionFideliteList where platiniumMax is null
        defaultGestionFideliteShouldNotBeFound("platiniumMax.specified=false");
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMax is greater than or equal to DEFAULT_PLATINIUM_MAX
        defaultGestionFideliteShouldBeFound("platiniumMax.greaterThanOrEqual=" + DEFAULT_PLATINIUM_MAX);

        // Get all the gestionFideliteList where platiniumMax is greater than or equal to UPDATED_PLATINIUM_MAX
        defaultGestionFideliteShouldNotBeFound("platiniumMax.greaterThanOrEqual=" + UPDATED_PLATINIUM_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMax is less than or equal to DEFAULT_PLATINIUM_MAX
        defaultGestionFideliteShouldBeFound("platiniumMax.lessThanOrEqual=" + DEFAULT_PLATINIUM_MAX);

        // Get all the gestionFideliteList where platiniumMax is less than or equal to SMALLER_PLATINIUM_MAX
        defaultGestionFideliteShouldNotBeFound("platiniumMax.lessThanOrEqual=" + SMALLER_PLATINIUM_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMax is less than DEFAULT_PLATINIUM_MAX
        defaultGestionFideliteShouldNotBeFound("platiniumMax.lessThan=" + DEFAULT_PLATINIUM_MAX);

        // Get all the gestionFideliteList where platiniumMax is less than UPDATED_PLATINIUM_MAX
        defaultGestionFideliteShouldBeFound("platiniumMax.lessThan=" + UPDATED_PLATINIUM_MAX);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByPlatiniumMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where platiniumMax is greater than DEFAULT_PLATINIUM_MAX
        defaultGestionFideliteShouldNotBeFound("platiniumMax.greaterThan=" + DEFAULT_PLATINIUM_MAX);

        // Get all the gestionFideliteList where platiniumMax is greater than SMALLER_PLATINIUM_MAX
        defaultGestionFideliteShouldBeFound("platiniumMax.greaterThan=" + SMALLER_PLATINIUM_MAX);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesByDeviseIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where devise equals to DEFAULT_DEVISE
        defaultGestionFideliteShouldBeFound("devise.equals=" + DEFAULT_DEVISE);

        // Get all the gestionFideliteList where devise equals to UPDATED_DEVISE
        defaultGestionFideliteShouldNotBeFound("devise.equals=" + UPDATED_DEVISE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByDeviseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where devise not equals to DEFAULT_DEVISE
        defaultGestionFideliteShouldNotBeFound("devise.notEquals=" + DEFAULT_DEVISE);

        // Get all the gestionFideliteList where devise not equals to UPDATED_DEVISE
        defaultGestionFideliteShouldBeFound("devise.notEquals=" + UPDATED_DEVISE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByDeviseIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where devise in DEFAULT_DEVISE or UPDATED_DEVISE
        defaultGestionFideliteShouldBeFound("devise.in=" + DEFAULT_DEVISE + "," + UPDATED_DEVISE);

        // Get all the gestionFideliteList where devise equals to UPDATED_DEVISE
        defaultGestionFideliteShouldNotBeFound("devise.in=" + UPDATED_DEVISE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByDeviseIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where devise is not null
        defaultGestionFideliteShouldBeFound("devise.specified=true");

        // Get all the gestionFideliteList where devise is null
        defaultGestionFideliteShouldNotBeFound("devise.specified=false");
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where etat equals to DEFAULT_ETAT
        defaultGestionFideliteShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the gestionFideliteList where etat equals to UPDATED_ETAT
        defaultGestionFideliteShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByEtatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where etat not equals to DEFAULT_ETAT
        defaultGestionFideliteShouldNotBeFound("etat.notEquals=" + DEFAULT_ETAT);

        // Get all the gestionFideliteList where etat not equals to UPDATED_ETAT
        defaultGestionFideliteShouldBeFound("etat.notEquals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultGestionFideliteShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the gestionFideliteList where etat equals to UPDATED_ETAT
        defaultGestionFideliteShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where etat is not null
        defaultGestionFideliteShouldBeFound("etat.specified=true");

        // Get all the gestionFideliteList where etat is null
        defaultGestionFideliteShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creeLe equals to DEFAULT_CREE_LE
        defaultGestionFideliteShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the gestionFideliteList where creeLe equals to UPDATED_CREE_LE
        defaultGestionFideliteShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creeLe not equals to DEFAULT_CREE_LE
        defaultGestionFideliteShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the gestionFideliteList where creeLe not equals to UPDATED_CREE_LE
        defaultGestionFideliteShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultGestionFideliteShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the gestionFideliteList where creeLe equals to UPDATED_CREE_LE
        defaultGestionFideliteShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creeLe is not null
        defaultGestionFideliteShouldBeFound("creeLe.specified=true");

        // Get all the gestionFideliteList where creeLe is null
        defaultGestionFideliteShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultGestionFideliteShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the gestionFideliteList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultGestionFideliteShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultGestionFideliteShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the gestionFideliteList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultGestionFideliteShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creeLe is less than DEFAULT_CREE_LE
        defaultGestionFideliteShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the gestionFideliteList where creeLe is less than UPDATED_CREE_LE
        defaultGestionFideliteShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creeLe is greater than DEFAULT_CREE_LE
        defaultGestionFideliteShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the gestionFideliteList where creeLe is greater than SMALLER_CREE_LE
        defaultGestionFideliteShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creePar equals to DEFAULT_CREE_PAR
        defaultGestionFideliteShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the gestionFideliteList where creePar equals to UPDATED_CREE_PAR
        defaultGestionFideliteShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creePar not equals to DEFAULT_CREE_PAR
        defaultGestionFideliteShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the gestionFideliteList where creePar not equals to UPDATED_CREE_PAR
        defaultGestionFideliteShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultGestionFideliteShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the gestionFideliteList where creePar equals to UPDATED_CREE_PAR
        defaultGestionFideliteShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creePar is not null
        defaultGestionFideliteShouldBeFound("creePar.specified=true");

        // Get all the gestionFideliteList where creePar is null
        defaultGestionFideliteShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllGestionFidelitesByCreeParContainsSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creePar contains DEFAULT_CREE_PAR
        defaultGestionFideliteShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the gestionFideliteList where creePar contains UPDATED_CREE_PAR
        defaultGestionFideliteShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where creePar does not contain DEFAULT_CREE_PAR
        defaultGestionFideliteShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the gestionFideliteList where creePar does not contain UPDATED_CREE_PAR
        defaultGestionFideliteShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultGestionFideliteShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the gestionFideliteList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultGestionFideliteShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultGestionFideliteShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the gestionFideliteList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultGestionFideliteShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultGestionFideliteShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the gestionFideliteList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultGestionFideliteShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifieLe is not null
        defaultGestionFideliteShouldBeFound("modifieLe.specified=true");

        // Get all the gestionFideliteList where modifieLe is null
        defaultGestionFideliteShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultGestionFideliteShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the gestionFideliteList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultGestionFideliteShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultGestionFideliteShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the gestionFideliteList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultGestionFideliteShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultGestionFideliteShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the gestionFideliteList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultGestionFideliteShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultGestionFideliteShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the gestionFideliteList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultGestionFideliteShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultGestionFideliteShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the gestionFideliteList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultGestionFideliteShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultGestionFideliteShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the gestionFideliteList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultGestionFideliteShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultGestionFideliteShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the gestionFideliteList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultGestionFideliteShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifiePar is not null
        defaultGestionFideliteShouldBeFound("modifiePar.specified=true");

        // Get all the gestionFideliteList where modifiePar is null
        defaultGestionFideliteShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllGestionFidelitesByModifieParContainsSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultGestionFideliteShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the gestionFideliteList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultGestionFideliteShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllGestionFidelitesByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        // Get all the gestionFideliteList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultGestionFideliteShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the gestionFideliteList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultGestionFideliteShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGestionFideliteShouldBeFound(String filter) throws Exception {
        restGestionFideliteMockMvc.perform(get("/api/gestion-fidelites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gestionFidelite.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].silverMin").value(hasItem(DEFAULT_SILVER_MIN)))
            .andExpect(jsonPath("$.[*].silverMax").value(hasItem(DEFAULT_SILVER_MAX)))
            .andExpect(jsonPath("$.[*].goldMin").value(hasItem(DEFAULT_GOLD_MIN)))
            .andExpect(jsonPath("$.[*].goldMax").value(hasItem(DEFAULT_GOLD_MAX)))
            .andExpect(jsonPath("$.[*].platiniumMin").value(hasItem(DEFAULT_PLATINIUM_MIN)))
            .andExpect(jsonPath("$.[*].platiniumMax").value(hasItem(DEFAULT_PLATINIUM_MAX)))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));

        // Check, that the count call also returns 1
        restGestionFideliteMockMvc.perform(get("/api/gestion-fidelites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGestionFideliteShouldNotBeFound(String filter) throws Exception {
        restGestionFideliteMockMvc.perform(get("/api/gestion-fidelites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGestionFideliteMockMvc.perform(get("/api/gestion-fidelites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGestionFidelite() throws Exception {
        // Get the gestionFidelite
        restGestionFideliteMockMvc.perform(get("/api/gestion-fidelites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGestionFidelite() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        int databaseSizeBeforeUpdate = gestionFideliteRepository.findAll().size();

        // Update the gestionFidelite
        GestionFidelite updatedGestionFidelite = gestionFideliteRepository.findById(gestionFidelite.getId()).get();
        // Disconnect from session so that the updates on updatedGestionFidelite are not directly saved in db
        em.detach(updatedGestionFidelite);
        updatedGestionFidelite
            .nom(UPDATED_NOM)
            .points(UPDATED_POINTS)
            .valeur(UPDATED_VALEUR)
            .silverMin(UPDATED_SILVER_MIN)
            .silverMax(UPDATED_SILVER_MAX)
            .goldMin(UPDATED_GOLD_MIN)
            .goldMax(UPDATED_GOLD_MAX)
            .platiniumMin(UPDATED_PLATINIUM_MIN)
            .platiniumMax(UPDATED_PLATINIUM_MAX)
            .devise(UPDATED_DEVISE)
            .etat(UPDATED_ETAT)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(updatedGestionFidelite);

        restGestionFideliteMockMvc.perform(put("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isOk());

        // Validate the GestionFidelite in the database
        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeUpdate);
        GestionFidelite testGestionFidelite = gestionFideliteList.get(gestionFideliteList.size() - 1);
        assertThat(testGestionFidelite.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testGestionFidelite.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testGestionFidelite.getValeur()).isEqualTo(UPDATED_VALEUR);
        assertThat(testGestionFidelite.getSilverMin()).isEqualTo(UPDATED_SILVER_MIN);
        assertThat(testGestionFidelite.getSilverMax()).isEqualTo(UPDATED_SILVER_MAX);
        assertThat(testGestionFidelite.getGoldMin()).isEqualTo(UPDATED_GOLD_MIN);
        assertThat(testGestionFidelite.getGoldMax()).isEqualTo(UPDATED_GOLD_MAX);
        assertThat(testGestionFidelite.getPlatiniumMin()).isEqualTo(UPDATED_PLATINIUM_MIN);
        assertThat(testGestionFidelite.getPlatiniumMax()).isEqualTo(UPDATED_PLATINIUM_MAX);
        assertThat(testGestionFidelite.getDevise()).isEqualTo(UPDATED_DEVISE);
        assertThat(testGestionFidelite.isEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testGestionFidelite.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testGestionFidelite.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testGestionFidelite.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testGestionFidelite.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the GestionFidelite in Elasticsearch
        verify(mockGestionFideliteSearchRepository, times(1)).save(testGestionFidelite);
    }

    @Test
    @Transactional
    public void updateNonExistingGestionFidelite() throws Exception {
        int databaseSizeBeforeUpdate = gestionFideliteRepository.findAll().size();

        // Create the GestionFidelite
        GestionFideliteDTO gestionFideliteDTO = gestionFideliteMapper.toDto(gestionFidelite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestionFideliteMockMvc.perform(put("/api/gestion-fidelites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gestionFideliteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GestionFidelite in the database
        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GestionFidelite in Elasticsearch
        verify(mockGestionFideliteSearchRepository, times(0)).save(gestionFidelite);
    }

    @Test
    @Transactional
    public void deleteGestionFidelite() throws Exception {
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);

        int databaseSizeBeforeDelete = gestionFideliteRepository.findAll().size();

        // Delete the gestionFidelite
        restGestionFideliteMockMvc.perform(delete("/api/gestion-fidelites/{id}", gestionFidelite.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GestionFidelite> gestionFideliteList = gestionFideliteRepository.findAll();
        assertThat(gestionFideliteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GestionFidelite in Elasticsearch
        verify(mockGestionFideliteSearchRepository, times(1)).deleteById(gestionFidelite.getId());
    }

    @Test
    @Transactional
    public void searchGestionFidelite() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        gestionFideliteRepository.saveAndFlush(gestionFidelite);
        when(mockGestionFideliteSearchRepository.search(queryStringQuery("id:" + gestionFidelite.getId())))
            .thenReturn(Collections.singletonList(gestionFidelite));

        // Search the gestionFidelite
        restGestionFideliteMockMvc.perform(get("/api/_search/gestion-fidelites?query=id:" + gestionFidelite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gestionFidelite.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].silverMin").value(hasItem(DEFAULT_SILVER_MIN)))
            .andExpect(jsonPath("$.[*].silverMax").value(hasItem(DEFAULT_SILVER_MAX)))
            .andExpect(jsonPath("$.[*].goldMin").value(hasItem(DEFAULT_GOLD_MIN)))
            .andExpect(jsonPath("$.[*].goldMax").value(hasItem(DEFAULT_GOLD_MAX)))
            .andExpect(jsonPath("$.[*].platiniumMin").value(hasItem(DEFAULT_PLATINIUM_MIN)))
            .andExpect(jsonPath("$.[*].platiniumMax").value(hasItem(DEFAULT_PLATINIUM_MAX)))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
