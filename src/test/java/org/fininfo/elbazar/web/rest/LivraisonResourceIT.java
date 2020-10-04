package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.Livraison;
import org.fininfo.elbazar.domain.Zone;
import org.fininfo.elbazar.repository.LivraisonRepository;
import org.fininfo.elbazar.repository.search.LivraisonSearchRepository;
import org.fininfo.elbazar.service.LivraisonService;
import org.fininfo.elbazar.service.dto.LivraisonDTO;
import org.fininfo.elbazar.service.mapper.LivraisonMapper;
import org.fininfo.elbazar.service.dto.LivraisonCriteria;
import org.fininfo.elbazar.service.LivraisonQueryService;

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

import org.fininfo.elbazar.domain.enumeration.ProfileClient;
/**
 * Integration tests for the {@link LivraisonResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LivraisonResourceIT {

    private static final ProfileClient DEFAULT_CATEGORIE_CLIENT = ProfileClient.Silver;
    private static final ProfileClient UPDATED_CATEGORIE_CLIENT = ProfileClient.Gold;

    private static final Double DEFAULT_INTERVAL_VALEUR = 1D;
    private static final Double UPDATED_INTERVAL_VALEUR = 2D;
    private static final Double SMALLER_INTERVAL_VALEUR = 1D - 1D;

    private static final Double DEFAULT_FRAIS = 1D;
    private static final Double UPDATED_FRAIS = 2D;
    private static final Double SMALLER_FRAIS = 1D - 1D;

    private static final Integer DEFAULT_DATE = 1;
    private static final Integer UPDATED_DATE = 2;
    private static final Integer SMALLER_DATE = 1 - 1;

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

    private static final Double DEFAULT_VALEUR_MIN = 1D;
    private static final Double UPDATED_VALEUR_MIN = 2D;
    private static final Double SMALLER_VALEUR_MIN = 1D - 1D;

    private static final Double DEFAULT_VALEUR_MAX = 1D;
    private static final Double UPDATED_VALEUR_MAX = 2D;
    private static final Double SMALLER_VALEUR_MAX = 1D - 1D;

    private static final LocalDate DEFAULT_DATE_LIVRAISON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LIVRAISON = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_LIVRAISON = LocalDate.ofEpochDay(-1L);

    @Autowired
    private LivraisonRepository livraisonRepository;

    @Autowired
    private LivraisonMapper livraisonMapper;

    @Autowired
    private LivraisonService livraisonService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.LivraisonSearchRepositoryMockConfiguration
     */
    @Autowired
    private LivraisonSearchRepository mockLivraisonSearchRepository;

    @Autowired
    private LivraisonQueryService livraisonQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLivraisonMockMvc;

    private Livraison livraison;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livraison createEntity(EntityManager em) {
        Livraison livraison = new Livraison()
            .categorieClient(DEFAULT_CATEGORIE_CLIENT)
            .intervalValeur(DEFAULT_INTERVAL_VALEUR)
            .frais(DEFAULT_FRAIS)
            .date(DEFAULT_DATE)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR)
            .valeurMin(DEFAULT_VALEUR_MIN)
            .valeurMax(DEFAULT_VALEUR_MAX)
            .dateLivraison(DEFAULT_DATE_LIVRAISON);
        return livraison;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livraison createUpdatedEntity(EntityManager em) {
        Livraison livraison = new Livraison()
            .categorieClient(UPDATED_CATEGORIE_CLIENT)
            .intervalValeur(UPDATED_INTERVAL_VALEUR)
            .frais(UPDATED_FRAIS)
            .date(UPDATED_DATE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR)
            .valeurMin(UPDATED_VALEUR_MIN)
            .valeurMax(UPDATED_VALEUR_MAX)
            .dateLivraison(UPDATED_DATE_LIVRAISON);
        return livraison;
    }

    @BeforeEach
    public void initTest() {
        livraison = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivraison() throws Exception {
        int databaseSizeBeforeCreate = livraisonRepository.findAll().size();
        // Create the Livraison
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);
        restLivraisonMockMvc.perform(post("/api/livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isCreated());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeCreate + 1);
        Livraison testLivraison = livraisonList.get(livraisonList.size() - 1);
        assertThat(testLivraison.getCategorieClient()).isEqualTo(DEFAULT_CATEGORIE_CLIENT);
        assertThat(testLivraison.getIntervalValeur()).isEqualTo(DEFAULT_INTERVAL_VALEUR);
        assertThat(testLivraison.getFrais()).isEqualTo(DEFAULT_FRAIS);
        assertThat(testLivraison.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testLivraison.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testLivraison.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testLivraison.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testLivraison.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);
        assertThat(testLivraison.getValeurMin()).isEqualTo(DEFAULT_VALEUR_MIN);
        assertThat(testLivraison.getValeurMax()).isEqualTo(DEFAULT_VALEUR_MAX);
        assertThat(testLivraison.getDateLivraison()).isEqualTo(DEFAULT_DATE_LIVRAISON);

        // Validate the Livraison in Elasticsearch
        verify(mockLivraisonSearchRepository, times(1)).save(testLivraison);
    }

    @Test
    @Transactional
    public void createLivraisonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livraisonRepository.findAll().size();

        // Create the Livraison with an existing ID
        livraison.setId(1L);
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivraisonMockMvc.perform(post("/api/livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeCreate);

        // Validate the Livraison in Elasticsearch
        verify(mockLivraisonSearchRepository, times(0)).save(livraison);
    }


    @Test
    @Transactional
    public void checkValeurMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = livraisonRepository.findAll().size();
        // set the field null
        livraison.setValeurMin(null);

        // Create the Livraison, which fails.
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);


        restLivraisonMockMvc.perform(post("/api/livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isBadRequest());

        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValeurMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = livraisonRepository.findAll().size();
        // set the field null
        livraison.setValeurMax(null);

        // Create the Livraison, which fails.
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);


        restLivraisonMockMvc.perform(post("/api/livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isBadRequest());

        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLivraisons() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList
        restLivraisonMockMvc.perform(get("/api/livraisons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].categorieClient").value(hasItem(DEFAULT_CATEGORIE_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].intervalValeur").value(hasItem(DEFAULT_INTERVAL_VALEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].frais").value(hasItem(DEFAULT_FRAIS.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].valeurMin").value(hasItem(DEFAULT_VALEUR_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].valeurMax").value(hasItem(DEFAULT_VALEUR_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].dateLivraison").value(hasItem(DEFAULT_DATE_LIVRAISON.toString())));
    }
    
    @Test
    @Transactional
    public void getLivraison() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get the livraison
        restLivraisonMockMvc.perform(get("/api/livraisons/{id}", livraison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livraison.getId().intValue()))
            .andExpect(jsonPath("$.categorieClient").value(DEFAULT_CATEGORIE_CLIENT.toString()))
            .andExpect(jsonPath("$.intervalValeur").value(DEFAULT_INTERVAL_VALEUR.doubleValue()))
            .andExpect(jsonPath("$.frais").value(DEFAULT_FRAIS.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR))
            .andExpect(jsonPath("$.valeurMin").value(DEFAULT_VALEUR_MIN.doubleValue()))
            .andExpect(jsonPath("$.valeurMax").value(DEFAULT_VALEUR_MAX.doubleValue()))
            .andExpect(jsonPath("$.dateLivraison").value(DEFAULT_DATE_LIVRAISON.toString()));
    }


    @Test
    @Transactional
    public void getLivraisonsByIdFiltering() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        Long id = livraison.getId();

        defaultLivraisonShouldBeFound("id.equals=" + id);
        defaultLivraisonShouldNotBeFound("id.notEquals=" + id);

        defaultLivraisonShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLivraisonShouldNotBeFound("id.greaterThan=" + id);

        defaultLivraisonShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLivraisonShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByCategorieClientIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where categorieClient equals to DEFAULT_CATEGORIE_CLIENT
        defaultLivraisonShouldBeFound("categorieClient.equals=" + DEFAULT_CATEGORIE_CLIENT);

        // Get all the livraisonList where categorieClient equals to UPDATED_CATEGORIE_CLIENT
        defaultLivraisonShouldNotBeFound("categorieClient.equals=" + UPDATED_CATEGORIE_CLIENT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCategorieClientIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where categorieClient not equals to DEFAULT_CATEGORIE_CLIENT
        defaultLivraisonShouldNotBeFound("categorieClient.notEquals=" + DEFAULT_CATEGORIE_CLIENT);

        // Get all the livraisonList where categorieClient not equals to UPDATED_CATEGORIE_CLIENT
        defaultLivraisonShouldBeFound("categorieClient.notEquals=" + UPDATED_CATEGORIE_CLIENT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCategorieClientIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where categorieClient in DEFAULT_CATEGORIE_CLIENT or UPDATED_CATEGORIE_CLIENT
        defaultLivraisonShouldBeFound("categorieClient.in=" + DEFAULT_CATEGORIE_CLIENT + "," + UPDATED_CATEGORIE_CLIENT);

        // Get all the livraisonList where categorieClient equals to UPDATED_CATEGORIE_CLIENT
        defaultLivraisonShouldNotBeFound("categorieClient.in=" + UPDATED_CATEGORIE_CLIENT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCategorieClientIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where categorieClient is not null
        defaultLivraisonShouldBeFound("categorieClient.specified=true");

        // Get all the livraisonList where categorieClient is null
        defaultLivraisonShouldNotBeFound("categorieClient.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByIntervalValeurIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where intervalValeur equals to DEFAULT_INTERVAL_VALEUR
        defaultLivraisonShouldBeFound("intervalValeur.equals=" + DEFAULT_INTERVAL_VALEUR);

        // Get all the livraisonList where intervalValeur equals to UPDATED_INTERVAL_VALEUR
        defaultLivraisonShouldNotBeFound("intervalValeur.equals=" + UPDATED_INTERVAL_VALEUR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByIntervalValeurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where intervalValeur not equals to DEFAULT_INTERVAL_VALEUR
        defaultLivraisonShouldNotBeFound("intervalValeur.notEquals=" + DEFAULT_INTERVAL_VALEUR);

        // Get all the livraisonList where intervalValeur not equals to UPDATED_INTERVAL_VALEUR
        defaultLivraisonShouldBeFound("intervalValeur.notEquals=" + UPDATED_INTERVAL_VALEUR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByIntervalValeurIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where intervalValeur in DEFAULT_INTERVAL_VALEUR or UPDATED_INTERVAL_VALEUR
        defaultLivraisonShouldBeFound("intervalValeur.in=" + DEFAULT_INTERVAL_VALEUR + "," + UPDATED_INTERVAL_VALEUR);

        // Get all the livraisonList where intervalValeur equals to UPDATED_INTERVAL_VALEUR
        defaultLivraisonShouldNotBeFound("intervalValeur.in=" + UPDATED_INTERVAL_VALEUR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByIntervalValeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where intervalValeur is not null
        defaultLivraisonShouldBeFound("intervalValeur.specified=true");

        // Get all the livraisonList where intervalValeur is null
        defaultLivraisonShouldNotBeFound("intervalValeur.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByIntervalValeurIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where intervalValeur is greater than or equal to DEFAULT_INTERVAL_VALEUR
        defaultLivraisonShouldBeFound("intervalValeur.greaterThanOrEqual=" + DEFAULT_INTERVAL_VALEUR);

        // Get all the livraisonList where intervalValeur is greater than or equal to UPDATED_INTERVAL_VALEUR
        defaultLivraisonShouldNotBeFound("intervalValeur.greaterThanOrEqual=" + UPDATED_INTERVAL_VALEUR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByIntervalValeurIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where intervalValeur is less than or equal to DEFAULT_INTERVAL_VALEUR
        defaultLivraisonShouldBeFound("intervalValeur.lessThanOrEqual=" + DEFAULT_INTERVAL_VALEUR);

        // Get all the livraisonList where intervalValeur is less than or equal to SMALLER_INTERVAL_VALEUR
        defaultLivraisonShouldNotBeFound("intervalValeur.lessThanOrEqual=" + SMALLER_INTERVAL_VALEUR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByIntervalValeurIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where intervalValeur is less than DEFAULT_INTERVAL_VALEUR
        defaultLivraisonShouldNotBeFound("intervalValeur.lessThan=" + DEFAULT_INTERVAL_VALEUR);

        // Get all the livraisonList where intervalValeur is less than UPDATED_INTERVAL_VALEUR
        defaultLivraisonShouldBeFound("intervalValeur.lessThan=" + UPDATED_INTERVAL_VALEUR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByIntervalValeurIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where intervalValeur is greater than DEFAULT_INTERVAL_VALEUR
        defaultLivraisonShouldNotBeFound("intervalValeur.greaterThan=" + DEFAULT_INTERVAL_VALEUR);

        // Get all the livraisonList where intervalValeur is greater than SMALLER_INTERVAL_VALEUR
        defaultLivraisonShouldBeFound("intervalValeur.greaterThan=" + SMALLER_INTERVAL_VALEUR);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByFraisIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where frais equals to DEFAULT_FRAIS
        defaultLivraisonShouldBeFound("frais.equals=" + DEFAULT_FRAIS);

        // Get all the livraisonList where frais equals to UPDATED_FRAIS
        defaultLivraisonShouldNotBeFound("frais.equals=" + UPDATED_FRAIS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where frais not equals to DEFAULT_FRAIS
        defaultLivraisonShouldNotBeFound("frais.notEquals=" + DEFAULT_FRAIS);

        // Get all the livraisonList where frais not equals to UPDATED_FRAIS
        defaultLivraisonShouldBeFound("frais.notEquals=" + UPDATED_FRAIS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where frais in DEFAULT_FRAIS or UPDATED_FRAIS
        defaultLivraisonShouldBeFound("frais.in=" + DEFAULT_FRAIS + "," + UPDATED_FRAIS);

        // Get all the livraisonList where frais equals to UPDATED_FRAIS
        defaultLivraisonShouldNotBeFound("frais.in=" + UPDATED_FRAIS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where frais is not null
        defaultLivraisonShouldBeFound("frais.specified=true");

        // Get all the livraisonList where frais is null
        defaultLivraisonShouldNotBeFound("frais.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where frais is greater than or equal to DEFAULT_FRAIS
        defaultLivraisonShouldBeFound("frais.greaterThanOrEqual=" + DEFAULT_FRAIS);

        // Get all the livraisonList where frais is greater than or equal to UPDATED_FRAIS
        defaultLivraisonShouldNotBeFound("frais.greaterThanOrEqual=" + UPDATED_FRAIS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where frais is less than or equal to DEFAULT_FRAIS
        defaultLivraisonShouldBeFound("frais.lessThanOrEqual=" + DEFAULT_FRAIS);

        // Get all the livraisonList where frais is less than or equal to SMALLER_FRAIS
        defaultLivraisonShouldNotBeFound("frais.lessThanOrEqual=" + SMALLER_FRAIS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where frais is less than DEFAULT_FRAIS
        defaultLivraisonShouldNotBeFound("frais.lessThan=" + DEFAULT_FRAIS);

        // Get all the livraisonList where frais is less than UPDATED_FRAIS
        defaultLivraisonShouldBeFound("frais.lessThan=" + UPDATED_FRAIS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where frais is greater than DEFAULT_FRAIS
        defaultLivraisonShouldNotBeFound("frais.greaterThan=" + DEFAULT_FRAIS);

        // Get all the livraisonList where frais is greater than SMALLER_FRAIS
        defaultLivraisonShouldBeFound("frais.greaterThan=" + SMALLER_FRAIS);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where date equals to DEFAULT_DATE
        defaultLivraisonShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the livraisonList where date equals to UPDATED_DATE
        defaultLivraisonShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where date not equals to DEFAULT_DATE
        defaultLivraisonShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the livraisonList where date not equals to UPDATED_DATE
        defaultLivraisonShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where date in DEFAULT_DATE or UPDATED_DATE
        defaultLivraisonShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the livraisonList where date equals to UPDATED_DATE
        defaultLivraisonShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where date is not null
        defaultLivraisonShouldBeFound("date.specified=true");

        // Get all the livraisonList where date is null
        defaultLivraisonShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where date is greater than or equal to DEFAULT_DATE
        defaultLivraisonShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the livraisonList where date is greater than or equal to UPDATED_DATE
        defaultLivraisonShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where date is less than or equal to DEFAULT_DATE
        defaultLivraisonShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the livraisonList where date is less than or equal to SMALLER_DATE
        defaultLivraisonShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where date is less than DEFAULT_DATE
        defaultLivraisonShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the livraisonList where date is less than UPDATED_DATE
        defaultLivraisonShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where date is greater than DEFAULT_DATE
        defaultLivraisonShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the livraisonList where date is greater than SMALLER_DATE
        defaultLivraisonShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creeLe equals to DEFAULT_CREE_LE
        defaultLivraisonShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the livraisonList where creeLe equals to UPDATED_CREE_LE
        defaultLivraisonShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creeLe not equals to DEFAULT_CREE_LE
        defaultLivraisonShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the livraisonList where creeLe not equals to UPDATED_CREE_LE
        defaultLivraisonShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultLivraisonShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the livraisonList where creeLe equals to UPDATED_CREE_LE
        defaultLivraisonShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creeLe is not null
        defaultLivraisonShouldBeFound("creeLe.specified=true");

        // Get all the livraisonList where creeLe is null
        defaultLivraisonShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultLivraisonShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the livraisonList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultLivraisonShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultLivraisonShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the livraisonList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultLivraisonShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creeLe is less than DEFAULT_CREE_LE
        defaultLivraisonShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the livraisonList where creeLe is less than UPDATED_CREE_LE
        defaultLivraisonShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creeLe is greater than DEFAULT_CREE_LE
        defaultLivraisonShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the livraisonList where creeLe is greater than SMALLER_CREE_LE
        defaultLivraisonShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creePar equals to DEFAULT_CREE_PAR
        defaultLivraisonShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the livraisonList where creePar equals to UPDATED_CREE_PAR
        defaultLivraisonShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creePar not equals to DEFAULT_CREE_PAR
        defaultLivraisonShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the livraisonList where creePar not equals to UPDATED_CREE_PAR
        defaultLivraisonShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultLivraisonShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the livraisonList where creePar equals to UPDATED_CREE_PAR
        defaultLivraisonShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creePar is not null
        defaultLivraisonShouldBeFound("creePar.specified=true");

        // Get all the livraisonList where creePar is null
        defaultLivraisonShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllLivraisonsByCreeParContainsSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creePar contains DEFAULT_CREE_PAR
        defaultLivraisonShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the livraisonList where creePar contains UPDATED_CREE_PAR
        defaultLivraisonShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where creePar does not contain DEFAULT_CREE_PAR
        defaultLivraisonShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the livraisonList where creePar does not contain UPDATED_CREE_PAR
        defaultLivraisonShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultLivraisonShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the livraisonList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultLivraisonShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultLivraisonShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the livraisonList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultLivraisonShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultLivraisonShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the livraisonList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultLivraisonShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifieLe is not null
        defaultLivraisonShouldBeFound("modifieLe.specified=true");

        // Get all the livraisonList where modifieLe is null
        defaultLivraisonShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultLivraisonShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the livraisonList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultLivraisonShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultLivraisonShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the livraisonList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultLivraisonShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultLivraisonShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the livraisonList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultLivraisonShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultLivraisonShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the livraisonList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultLivraisonShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultLivraisonShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the livraisonList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultLivraisonShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultLivraisonShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the livraisonList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultLivraisonShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultLivraisonShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the livraisonList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultLivraisonShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifiePar is not null
        defaultLivraisonShouldBeFound("modifiePar.specified=true");

        // Get all the livraisonList where modifiePar is null
        defaultLivraisonShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllLivraisonsByModifieParContainsSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultLivraisonShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the livraisonList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultLivraisonShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultLivraisonShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the livraisonList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultLivraisonShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByValeurMinIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMin equals to DEFAULT_VALEUR_MIN
        defaultLivraisonShouldBeFound("valeurMin.equals=" + DEFAULT_VALEUR_MIN);

        // Get all the livraisonList where valeurMin equals to UPDATED_VALEUR_MIN
        defaultLivraisonShouldNotBeFound("valeurMin.equals=" + UPDATED_VALEUR_MIN);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMin not equals to DEFAULT_VALEUR_MIN
        defaultLivraisonShouldNotBeFound("valeurMin.notEquals=" + DEFAULT_VALEUR_MIN);

        // Get all the livraisonList where valeurMin not equals to UPDATED_VALEUR_MIN
        defaultLivraisonShouldBeFound("valeurMin.notEquals=" + UPDATED_VALEUR_MIN);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMinIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMin in DEFAULT_VALEUR_MIN or UPDATED_VALEUR_MIN
        defaultLivraisonShouldBeFound("valeurMin.in=" + DEFAULT_VALEUR_MIN + "," + UPDATED_VALEUR_MIN);

        // Get all the livraisonList where valeurMin equals to UPDATED_VALEUR_MIN
        defaultLivraisonShouldNotBeFound("valeurMin.in=" + UPDATED_VALEUR_MIN);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMin is not null
        defaultLivraisonShouldBeFound("valeurMin.specified=true");

        // Get all the livraisonList where valeurMin is null
        defaultLivraisonShouldNotBeFound("valeurMin.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMin is greater than or equal to DEFAULT_VALEUR_MIN
        defaultLivraisonShouldBeFound("valeurMin.greaterThanOrEqual=" + DEFAULT_VALEUR_MIN);

        // Get all the livraisonList where valeurMin is greater than or equal to UPDATED_VALEUR_MIN
        defaultLivraisonShouldNotBeFound("valeurMin.greaterThanOrEqual=" + UPDATED_VALEUR_MIN);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMin is less than or equal to DEFAULT_VALEUR_MIN
        defaultLivraisonShouldBeFound("valeurMin.lessThanOrEqual=" + DEFAULT_VALEUR_MIN);

        // Get all the livraisonList where valeurMin is less than or equal to SMALLER_VALEUR_MIN
        defaultLivraisonShouldNotBeFound("valeurMin.lessThanOrEqual=" + SMALLER_VALEUR_MIN);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMinIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMin is less than DEFAULT_VALEUR_MIN
        defaultLivraisonShouldNotBeFound("valeurMin.lessThan=" + DEFAULT_VALEUR_MIN);

        // Get all the livraisonList where valeurMin is less than UPDATED_VALEUR_MIN
        defaultLivraisonShouldBeFound("valeurMin.lessThan=" + UPDATED_VALEUR_MIN);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMin is greater than DEFAULT_VALEUR_MIN
        defaultLivraisonShouldNotBeFound("valeurMin.greaterThan=" + DEFAULT_VALEUR_MIN);

        // Get all the livraisonList where valeurMin is greater than SMALLER_VALEUR_MIN
        defaultLivraisonShouldBeFound("valeurMin.greaterThan=" + SMALLER_VALEUR_MIN);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByValeurMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMax equals to DEFAULT_VALEUR_MAX
        defaultLivraisonShouldBeFound("valeurMax.equals=" + DEFAULT_VALEUR_MAX);

        // Get all the livraisonList where valeurMax equals to UPDATED_VALEUR_MAX
        defaultLivraisonShouldNotBeFound("valeurMax.equals=" + UPDATED_VALEUR_MAX);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMax not equals to DEFAULT_VALEUR_MAX
        defaultLivraisonShouldNotBeFound("valeurMax.notEquals=" + DEFAULT_VALEUR_MAX);

        // Get all the livraisonList where valeurMax not equals to UPDATED_VALEUR_MAX
        defaultLivraisonShouldBeFound("valeurMax.notEquals=" + UPDATED_VALEUR_MAX);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMaxIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMax in DEFAULT_VALEUR_MAX or UPDATED_VALEUR_MAX
        defaultLivraisonShouldBeFound("valeurMax.in=" + DEFAULT_VALEUR_MAX + "," + UPDATED_VALEUR_MAX);

        // Get all the livraisonList where valeurMax equals to UPDATED_VALEUR_MAX
        defaultLivraisonShouldNotBeFound("valeurMax.in=" + UPDATED_VALEUR_MAX);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMax is not null
        defaultLivraisonShouldBeFound("valeurMax.specified=true");

        // Get all the livraisonList where valeurMax is null
        defaultLivraisonShouldNotBeFound("valeurMax.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMax is greater than or equal to DEFAULT_VALEUR_MAX
        defaultLivraisonShouldBeFound("valeurMax.greaterThanOrEqual=" + DEFAULT_VALEUR_MAX);

        // Get all the livraisonList where valeurMax is greater than or equal to UPDATED_VALEUR_MAX
        defaultLivraisonShouldNotBeFound("valeurMax.greaterThanOrEqual=" + UPDATED_VALEUR_MAX);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMax is less than or equal to DEFAULT_VALEUR_MAX
        defaultLivraisonShouldBeFound("valeurMax.lessThanOrEqual=" + DEFAULT_VALEUR_MAX);

        // Get all the livraisonList where valeurMax is less than or equal to SMALLER_VALEUR_MAX
        defaultLivraisonShouldNotBeFound("valeurMax.lessThanOrEqual=" + SMALLER_VALEUR_MAX);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMax is less than DEFAULT_VALEUR_MAX
        defaultLivraisonShouldNotBeFound("valeurMax.lessThan=" + DEFAULT_VALEUR_MAX);

        // Get all the livraisonList where valeurMax is less than UPDATED_VALEUR_MAX
        defaultLivraisonShouldBeFound("valeurMax.lessThan=" + UPDATED_VALEUR_MAX);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByValeurMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where valeurMax is greater than DEFAULT_VALEUR_MAX
        defaultLivraisonShouldNotBeFound("valeurMax.greaterThan=" + DEFAULT_VALEUR_MAX);

        // Get all the livraisonList where valeurMax is greater than SMALLER_VALEUR_MAX
        defaultLivraisonShouldBeFound("valeurMax.greaterThan=" + SMALLER_VALEUR_MAX);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByDateLivraisonIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateLivraison equals to DEFAULT_DATE_LIVRAISON
        defaultLivraisonShouldBeFound("dateLivraison.equals=" + DEFAULT_DATE_LIVRAISON);

        // Get all the livraisonList where dateLivraison equals to UPDATED_DATE_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateLivraison.equals=" + UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateLivraisonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateLivraison not equals to DEFAULT_DATE_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateLivraison.notEquals=" + DEFAULT_DATE_LIVRAISON);

        // Get all the livraisonList where dateLivraison not equals to UPDATED_DATE_LIVRAISON
        defaultLivraisonShouldBeFound("dateLivraison.notEquals=" + UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateLivraisonIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateLivraison in DEFAULT_DATE_LIVRAISON or UPDATED_DATE_LIVRAISON
        defaultLivraisonShouldBeFound("dateLivraison.in=" + DEFAULT_DATE_LIVRAISON + "," + UPDATED_DATE_LIVRAISON);

        // Get all the livraisonList where dateLivraison equals to UPDATED_DATE_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateLivraison.in=" + UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateLivraisonIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateLivraison is not null
        defaultLivraisonShouldBeFound("dateLivraison.specified=true");

        // Get all the livraisonList where dateLivraison is null
        defaultLivraisonShouldNotBeFound("dateLivraison.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateLivraisonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateLivraison is greater than or equal to DEFAULT_DATE_LIVRAISON
        defaultLivraisonShouldBeFound("dateLivraison.greaterThanOrEqual=" + DEFAULT_DATE_LIVRAISON);

        // Get all the livraisonList where dateLivraison is greater than or equal to UPDATED_DATE_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateLivraison.greaterThanOrEqual=" + UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateLivraisonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateLivraison is less than or equal to DEFAULT_DATE_LIVRAISON
        defaultLivraisonShouldBeFound("dateLivraison.lessThanOrEqual=" + DEFAULT_DATE_LIVRAISON);

        // Get all the livraisonList where dateLivraison is less than or equal to SMALLER_DATE_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateLivraison.lessThanOrEqual=" + SMALLER_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateLivraisonIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateLivraison is less than DEFAULT_DATE_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateLivraison.lessThan=" + DEFAULT_DATE_LIVRAISON);

        // Get all the livraisonList where dateLivraison is less than UPDATED_DATE_LIVRAISON
        defaultLivraisonShouldBeFound("dateLivraison.lessThan=" + UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateLivraisonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateLivraison is greater than DEFAULT_DATE_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateLivraison.greaterThan=" + DEFAULT_DATE_LIVRAISON);

        // Get all the livraisonList where dateLivraison is greater than SMALLER_DATE_LIVRAISON
        defaultLivraisonShouldBeFound("dateLivraison.greaterThan=" + SMALLER_DATE_LIVRAISON);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);
        Zone zone = ZoneResourceIT.createEntity(em);
        em.persist(zone);
        em.flush();
        livraison.setZone(zone);
        livraisonRepository.saveAndFlush(livraison);
        Long zoneId = zone.getId();

        // Get all the livraisonList where zone equals to zoneId
        defaultLivraisonShouldBeFound("zoneId.equals=" + zoneId);

        // Get all the livraisonList where zone equals to zoneId + 1
        defaultLivraisonShouldNotBeFound("zoneId.equals=" + (zoneId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLivraisonShouldBeFound(String filter) throws Exception {
        restLivraisonMockMvc.perform(get("/api/livraisons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].categorieClient").value(hasItem(DEFAULT_CATEGORIE_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].intervalValeur").value(hasItem(DEFAULT_INTERVAL_VALEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].frais").value(hasItem(DEFAULT_FRAIS.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].valeurMin").value(hasItem(DEFAULT_VALEUR_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].valeurMax").value(hasItem(DEFAULT_VALEUR_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].dateLivraison").value(hasItem(DEFAULT_DATE_LIVRAISON.toString())));

        // Check, that the count call also returns 1
        restLivraisonMockMvc.perform(get("/api/livraisons/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLivraisonShouldNotBeFound(String filter) throws Exception {
        restLivraisonMockMvc.perform(get("/api/livraisons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLivraisonMockMvc.perform(get("/api/livraisons/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLivraison() throws Exception {
        // Get the livraison
        restLivraisonMockMvc.perform(get("/api/livraisons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivraison() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();

        // Update the livraison
        Livraison updatedLivraison = livraisonRepository.findById(livraison.getId()).get();
        // Disconnect from session so that the updates on updatedLivraison are not directly saved in db
        em.detach(updatedLivraison);
        updatedLivraison
            .categorieClient(UPDATED_CATEGORIE_CLIENT)
            .intervalValeur(UPDATED_INTERVAL_VALEUR)
            .frais(UPDATED_FRAIS)
            .date(UPDATED_DATE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR)
            .valeurMin(UPDATED_VALEUR_MIN)
            .valeurMax(UPDATED_VALEUR_MAX)
            .dateLivraison(UPDATED_DATE_LIVRAISON);
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(updatedLivraison);

        restLivraisonMockMvc.perform(put("/api/livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isOk());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
        Livraison testLivraison = livraisonList.get(livraisonList.size() - 1);
        assertThat(testLivraison.getCategorieClient()).isEqualTo(UPDATED_CATEGORIE_CLIENT);
        assertThat(testLivraison.getIntervalValeur()).isEqualTo(UPDATED_INTERVAL_VALEUR);
        assertThat(testLivraison.getFrais()).isEqualTo(UPDATED_FRAIS);
        assertThat(testLivraison.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testLivraison.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testLivraison.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testLivraison.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testLivraison.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);
        assertThat(testLivraison.getValeurMin()).isEqualTo(UPDATED_VALEUR_MIN);
        assertThat(testLivraison.getValeurMax()).isEqualTo(UPDATED_VALEUR_MAX);
        assertThat(testLivraison.getDateLivraison()).isEqualTo(UPDATED_DATE_LIVRAISON);

        // Validate the Livraison in Elasticsearch
        verify(mockLivraisonSearchRepository, times(1)).save(testLivraison);
    }

    @Test
    @Transactional
    public void updateNonExistingLivraison() throws Exception {
        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();

        // Create the Livraison
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivraisonMockMvc.perform(put("/api/livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Livraison in Elasticsearch
        verify(mockLivraisonSearchRepository, times(0)).save(livraison);
    }

    @Test
    @Transactional
    public void deleteLivraison() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        int databaseSizeBeforeDelete = livraisonRepository.findAll().size();

        // Delete the livraison
        restLivraisonMockMvc.perform(delete("/api/livraisons/{id}", livraison.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Livraison in Elasticsearch
        verify(mockLivraisonSearchRepository, times(1)).deleteById(livraison.getId());
    }

    @Test
    @Transactional
    public void searchLivraison() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);
        when(mockLivraisonSearchRepository.search(queryStringQuery("id:" + livraison.getId())))
            .thenReturn(Collections.singletonList(livraison));

        // Search the livraison
        restLivraisonMockMvc.perform(get("/api/_search/livraisons?query=id:" + livraison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].categorieClient").value(hasItem(DEFAULT_CATEGORIE_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].intervalValeur").value(hasItem(DEFAULT_INTERVAL_VALEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].frais").value(hasItem(DEFAULT_FRAIS.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].valeurMin").value(hasItem(DEFAULT_VALEUR_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].valeurMax").value(hasItem(DEFAULT_VALEUR_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].dateLivraison").value(hasItem(DEFAULT_DATE_LIVRAISON.toString())));
    }
}
