package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.Adresse;
import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.domain.Zone;
import org.fininfo.elbazar.domain.AffectationZone;
import org.fininfo.elbazar.repository.AdresseRepository;
import org.fininfo.elbazar.repository.search.AdresseSearchRepository;
import org.fininfo.elbazar.service.AdresseService;
import org.fininfo.elbazar.service.dto.AdresseDTO;
import org.fininfo.elbazar.service.mapper.AdresseMapper;
import org.fininfo.elbazar.service.dto.AdresseCriteria;
import org.fininfo.elbazar.service.AdresseQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

/**
 * Integration tests for the {@link AdresseResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdresseResourceIT {

    private static final Boolean DEFAULT_PRINCIPALE = false;
    private static final Boolean UPDATED_PRINCIPALE = true;

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_GOUVERNORAT = "AAAAAAAAAA";
    private static final String UPDATED_GOUVERNORAT = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALITE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALITE = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATION = "AAAAAAAAAA";
    private static final String UPDATED_INDICATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEPHONE = 1;
    private static final Integer UPDATED_TELEPHONE = 2;
    private static final Integer SMALLER_TELEPHONE = 1 - 1;

    private static final Integer DEFAULT_MOBILE = 1;
    private static final Integer UPDATED_MOBILE = 2;
    private static final Integer SMALLER_MOBILE = 1 - 1;

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
    private AdresseRepository adresseRepository;

    @Autowired
    private AdresseMapper adresseMapper;

    @Autowired
    private AdresseService adresseService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.AdresseSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdresseSearchRepository mockAdresseSearchRepository;

    @Autowired
    private AdresseQueryService adresseQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdresseMockMvc;

    private Adresse adresse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresse createEntity(EntityManager em) {
        Adresse adresse = new Adresse()
            .principale(DEFAULT_PRINCIPALE)
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .gouvernorat(DEFAULT_GOUVERNORAT)
            .ville(DEFAULT_VILLE)
            .localite(DEFAULT_LOCALITE)
            .indication(DEFAULT_INDICATION)
            .telephone(DEFAULT_TELEPHONE)
            .mobile(DEFAULT_MOBILE)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return adresse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresse createUpdatedEntity(EntityManager em) {
        Adresse adresse = new Adresse()
            .principale(UPDATED_PRINCIPALE)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .gouvernorat(UPDATED_GOUVERNORAT)
            .ville(UPDATED_VILLE)
            .localite(UPDATED_LOCALITE)
            .indication(UPDATED_INDICATION)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return adresse;
    }

    @BeforeEach
    public void initTest() {
        adresse = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdresse() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();
        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);
        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isCreated());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeCreate + 1);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.isPrincipale()).isEqualTo(DEFAULT_PRINCIPALE);
        assertThat(testAdresse.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAdresse.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAdresse.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testAdresse.getGouvernorat()).isEqualTo(DEFAULT_GOUVERNORAT);
        assertThat(testAdresse.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdresse.getLocalite()).isEqualTo(DEFAULT_LOCALITE);
        assertThat(testAdresse.getIndication()).isEqualTo(DEFAULT_INDICATION);
        assertThat(testAdresse.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testAdresse.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testAdresse.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testAdresse.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testAdresse.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testAdresse.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the Adresse in Elasticsearch
        verify(mockAdresseSearchRepository, times(1)).save(testAdresse);
    }

    @Test
    @Transactional
    public void createAdresseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // Create the Adresse with an existing ID
        adresse.setId(1L);
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeCreate);

        // Validate the Adresse in Elasticsearch
        verify(mockAdresseSearchRepository, times(0)).save(adresse);
    }


    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setPrenom(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);


        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setNom(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);


        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setAdresse(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);


        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGouvernoratIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setGouvernorat(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);


        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setVille(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);


        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocaliteIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setLocalite(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);


        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setMobile(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);


        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdresses() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList
        restAdresseMockMvc.perform(get("/api/adresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId().intValue())))
            .andExpect(jsonPath("$.[*].principale").value(hasItem(DEFAULT_PRINCIPALE.booleanValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE)))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", adresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adresse.getId().intValue()))
            .andExpect(jsonPath("$.principale").value(DEFAULT_PRINCIPALE.booleanValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.gouvernorat").value(DEFAULT_GOUVERNORAT))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.localite").value(DEFAULT_LOCALITE))
            .andExpect(jsonPath("$.indication").value(DEFAULT_INDICATION))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }


    @Test
    @Transactional
    public void getAdressesByIdFiltering() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        Long id = adresse.getId();

        defaultAdresseShouldBeFound("id.equals=" + id);
        defaultAdresseShouldNotBeFound("id.notEquals=" + id);

        defaultAdresseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdresseShouldNotBeFound("id.greaterThan=" + id);

        defaultAdresseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdresseShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdressesByPrincipaleIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where principale equals to DEFAULT_PRINCIPALE
        defaultAdresseShouldBeFound("principale.equals=" + DEFAULT_PRINCIPALE);

        // Get all the adresseList where principale equals to UPDATED_PRINCIPALE
        defaultAdresseShouldNotBeFound("principale.equals=" + UPDATED_PRINCIPALE);
    }

    @Test
    @Transactional
    public void getAllAdressesByPrincipaleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where principale not equals to DEFAULT_PRINCIPALE
        defaultAdresseShouldNotBeFound("principale.notEquals=" + DEFAULT_PRINCIPALE);

        // Get all the adresseList where principale not equals to UPDATED_PRINCIPALE
        defaultAdresseShouldBeFound("principale.notEquals=" + UPDATED_PRINCIPALE);
    }

    @Test
    @Transactional
    public void getAllAdressesByPrincipaleIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where principale in DEFAULT_PRINCIPALE or UPDATED_PRINCIPALE
        defaultAdresseShouldBeFound("principale.in=" + DEFAULT_PRINCIPALE + "," + UPDATED_PRINCIPALE);

        // Get all the adresseList where principale equals to UPDATED_PRINCIPALE
        defaultAdresseShouldNotBeFound("principale.in=" + UPDATED_PRINCIPALE);
    }

    @Test
    @Transactional
    public void getAllAdressesByPrincipaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where principale is not null
        defaultAdresseShouldBeFound("principale.specified=true");

        // Get all the adresseList where principale is null
        defaultAdresseShouldNotBeFound("principale.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdressesByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where prenom equals to DEFAULT_PRENOM
        defaultAdresseShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the adresseList where prenom equals to UPDATED_PRENOM
        defaultAdresseShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllAdressesByPrenomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where prenom not equals to DEFAULT_PRENOM
        defaultAdresseShouldNotBeFound("prenom.notEquals=" + DEFAULT_PRENOM);

        // Get all the adresseList where prenom not equals to UPDATED_PRENOM
        defaultAdresseShouldBeFound("prenom.notEquals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllAdressesByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultAdresseShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the adresseList where prenom equals to UPDATED_PRENOM
        defaultAdresseShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllAdressesByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where prenom is not null
        defaultAdresseShouldBeFound("prenom.specified=true");

        // Get all the adresseList where prenom is null
        defaultAdresseShouldNotBeFound("prenom.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdressesByPrenomContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where prenom contains DEFAULT_PRENOM
        defaultAdresseShouldBeFound("prenom.contains=" + DEFAULT_PRENOM);

        // Get all the adresseList where prenom contains UPDATED_PRENOM
        defaultAdresseShouldNotBeFound("prenom.contains=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllAdressesByPrenomNotContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where prenom does not contain DEFAULT_PRENOM
        defaultAdresseShouldNotBeFound("prenom.doesNotContain=" + DEFAULT_PRENOM);

        // Get all the adresseList where prenom does not contain UPDATED_PRENOM
        defaultAdresseShouldBeFound("prenom.doesNotContain=" + UPDATED_PRENOM);
    }


    @Test
    @Transactional
    public void getAllAdressesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where nom equals to DEFAULT_NOM
        defaultAdresseShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the adresseList where nom equals to UPDATED_NOM
        defaultAdresseShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllAdressesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where nom not equals to DEFAULT_NOM
        defaultAdresseShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the adresseList where nom not equals to UPDATED_NOM
        defaultAdresseShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllAdressesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultAdresseShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the adresseList where nom equals to UPDATED_NOM
        defaultAdresseShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllAdressesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where nom is not null
        defaultAdresseShouldBeFound("nom.specified=true");

        // Get all the adresseList where nom is null
        defaultAdresseShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdressesByNomContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where nom contains DEFAULT_NOM
        defaultAdresseShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the adresseList where nom contains UPDATED_NOM
        defaultAdresseShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllAdressesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where nom does not contain DEFAULT_NOM
        defaultAdresseShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the adresseList where nom does not contain UPDATED_NOM
        defaultAdresseShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllAdressesByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where adresse equals to DEFAULT_ADRESSE
        defaultAdresseShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the adresseList where adresse equals to UPDATED_ADRESSE
        defaultAdresseShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllAdressesByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where adresse not equals to DEFAULT_ADRESSE
        defaultAdresseShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the adresseList where adresse not equals to UPDATED_ADRESSE
        defaultAdresseShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllAdressesByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultAdresseShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the adresseList where adresse equals to UPDATED_ADRESSE
        defaultAdresseShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllAdressesByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where adresse is not null
        defaultAdresseShouldBeFound("adresse.specified=true");

        // Get all the adresseList where adresse is null
        defaultAdresseShouldNotBeFound("adresse.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdressesByAdresseContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where adresse contains DEFAULT_ADRESSE
        defaultAdresseShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the adresseList where adresse contains UPDATED_ADRESSE
        defaultAdresseShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllAdressesByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where adresse does not contain DEFAULT_ADRESSE
        defaultAdresseShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the adresseList where adresse does not contain UPDATED_ADRESSE
        defaultAdresseShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }


    @Test
    @Transactional
    public void getAllAdressesByGouvernoratIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where gouvernorat equals to DEFAULT_GOUVERNORAT
        defaultAdresseShouldBeFound("gouvernorat.equals=" + DEFAULT_GOUVERNORAT);

        // Get all the adresseList where gouvernorat equals to UPDATED_GOUVERNORAT
        defaultAdresseShouldNotBeFound("gouvernorat.equals=" + UPDATED_GOUVERNORAT);
    }

    @Test
    @Transactional
    public void getAllAdressesByGouvernoratIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where gouvernorat not equals to DEFAULT_GOUVERNORAT
        defaultAdresseShouldNotBeFound("gouvernorat.notEquals=" + DEFAULT_GOUVERNORAT);

        // Get all the adresseList where gouvernorat not equals to UPDATED_GOUVERNORAT
        defaultAdresseShouldBeFound("gouvernorat.notEquals=" + UPDATED_GOUVERNORAT);
    }

    @Test
    @Transactional
    public void getAllAdressesByGouvernoratIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where gouvernorat in DEFAULT_GOUVERNORAT or UPDATED_GOUVERNORAT
        defaultAdresseShouldBeFound("gouvernorat.in=" + DEFAULT_GOUVERNORAT + "," + UPDATED_GOUVERNORAT);

        // Get all the adresseList where gouvernorat equals to UPDATED_GOUVERNORAT
        defaultAdresseShouldNotBeFound("gouvernorat.in=" + UPDATED_GOUVERNORAT);
    }

    @Test
    @Transactional
    public void getAllAdressesByGouvernoratIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where gouvernorat is not null
        defaultAdresseShouldBeFound("gouvernorat.specified=true");

        // Get all the adresseList where gouvernorat is null
        defaultAdresseShouldNotBeFound("gouvernorat.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdressesByGouvernoratContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where gouvernorat contains DEFAULT_GOUVERNORAT
        defaultAdresseShouldBeFound("gouvernorat.contains=" + DEFAULT_GOUVERNORAT);

        // Get all the adresseList where gouvernorat contains UPDATED_GOUVERNORAT
        defaultAdresseShouldNotBeFound("gouvernorat.contains=" + UPDATED_GOUVERNORAT);
    }

    @Test
    @Transactional
    public void getAllAdressesByGouvernoratNotContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where gouvernorat does not contain DEFAULT_GOUVERNORAT
        defaultAdresseShouldNotBeFound("gouvernorat.doesNotContain=" + DEFAULT_GOUVERNORAT);

        // Get all the adresseList where gouvernorat does not contain UPDATED_GOUVERNORAT
        defaultAdresseShouldBeFound("gouvernorat.doesNotContain=" + UPDATED_GOUVERNORAT);
    }


    @Test
    @Transactional
    public void getAllAdressesByVilleIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where ville equals to DEFAULT_VILLE
        defaultAdresseShouldBeFound("ville.equals=" + DEFAULT_VILLE);

        // Get all the adresseList where ville equals to UPDATED_VILLE
        defaultAdresseShouldNotBeFound("ville.equals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllAdressesByVilleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where ville not equals to DEFAULT_VILLE
        defaultAdresseShouldNotBeFound("ville.notEquals=" + DEFAULT_VILLE);

        // Get all the adresseList where ville not equals to UPDATED_VILLE
        defaultAdresseShouldBeFound("ville.notEquals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllAdressesByVilleIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where ville in DEFAULT_VILLE or UPDATED_VILLE
        defaultAdresseShouldBeFound("ville.in=" + DEFAULT_VILLE + "," + UPDATED_VILLE);

        // Get all the adresseList where ville equals to UPDATED_VILLE
        defaultAdresseShouldNotBeFound("ville.in=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllAdressesByVilleIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where ville is not null
        defaultAdresseShouldBeFound("ville.specified=true");

        // Get all the adresseList where ville is null
        defaultAdresseShouldNotBeFound("ville.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdressesByVilleContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where ville contains DEFAULT_VILLE
        defaultAdresseShouldBeFound("ville.contains=" + DEFAULT_VILLE);

        // Get all the adresseList where ville contains UPDATED_VILLE
        defaultAdresseShouldNotBeFound("ville.contains=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllAdressesByVilleNotContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where ville does not contain DEFAULT_VILLE
        defaultAdresseShouldNotBeFound("ville.doesNotContain=" + DEFAULT_VILLE);

        // Get all the adresseList where ville does not contain UPDATED_VILLE
        defaultAdresseShouldBeFound("ville.doesNotContain=" + UPDATED_VILLE);
    }


    @Test
    @Transactional
    public void getAllAdressesByLocaliteIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where localite equals to DEFAULT_LOCALITE
        defaultAdresseShouldBeFound("localite.equals=" + DEFAULT_LOCALITE);

        // Get all the adresseList where localite equals to UPDATED_LOCALITE
        defaultAdresseShouldNotBeFound("localite.equals=" + UPDATED_LOCALITE);
    }

    @Test
    @Transactional
    public void getAllAdressesByLocaliteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where localite not equals to DEFAULT_LOCALITE
        defaultAdresseShouldNotBeFound("localite.notEquals=" + DEFAULT_LOCALITE);

        // Get all the adresseList where localite not equals to UPDATED_LOCALITE
        defaultAdresseShouldBeFound("localite.notEquals=" + UPDATED_LOCALITE);
    }

    @Test
    @Transactional
    public void getAllAdressesByLocaliteIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where localite in DEFAULT_LOCALITE or UPDATED_LOCALITE
        defaultAdresseShouldBeFound("localite.in=" + DEFAULT_LOCALITE + "," + UPDATED_LOCALITE);

        // Get all the adresseList where localite equals to UPDATED_LOCALITE
        defaultAdresseShouldNotBeFound("localite.in=" + UPDATED_LOCALITE);
    }

    @Test
    @Transactional
    public void getAllAdressesByLocaliteIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where localite is not null
        defaultAdresseShouldBeFound("localite.specified=true");

        // Get all the adresseList where localite is null
        defaultAdresseShouldNotBeFound("localite.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdressesByLocaliteContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where localite contains DEFAULT_LOCALITE
        defaultAdresseShouldBeFound("localite.contains=" + DEFAULT_LOCALITE);

        // Get all the adresseList where localite contains UPDATED_LOCALITE
        defaultAdresseShouldNotBeFound("localite.contains=" + UPDATED_LOCALITE);
    }

    @Test
    @Transactional
    public void getAllAdressesByLocaliteNotContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where localite does not contain DEFAULT_LOCALITE
        defaultAdresseShouldNotBeFound("localite.doesNotContain=" + DEFAULT_LOCALITE);

        // Get all the adresseList where localite does not contain UPDATED_LOCALITE
        defaultAdresseShouldBeFound("localite.doesNotContain=" + UPDATED_LOCALITE);
    }


    @Test
    @Transactional
    public void getAllAdressesByIndicationIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where indication equals to DEFAULT_INDICATION
        defaultAdresseShouldBeFound("indication.equals=" + DEFAULT_INDICATION);

        // Get all the adresseList where indication equals to UPDATED_INDICATION
        defaultAdresseShouldNotBeFound("indication.equals=" + UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void getAllAdressesByIndicationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where indication not equals to DEFAULT_INDICATION
        defaultAdresseShouldNotBeFound("indication.notEquals=" + DEFAULT_INDICATION);

        // Get all the adresseList where indication not equals to UPDATED_INDICATION
        defaultAdresseShouldBeFound("indication.notEquals=" + UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void getAllAdressesByIndicationIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where indication in DEFAULT_INDICATION or UPDATED_INDICATION
        defaultAdresseShouldBeFound("indication.in=" + DEFAULT_INDICATION + "," + UPDATED_INDICATION);

        // Get all the adresseList where indication equals to UPDATED_INDICATION
        defaultAdresseShouldNotBeFound("indication.in=" + UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void getAllAdressesByIndicationIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where indication is not null
        defaultAdresseShouldBeFound("indication.specified=true");

        // Get all the adresseList where indication is null
        defaultAdresseShouldNotBeFound("indication.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdressesByIndicationContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where indication contains DEFAULT_INDICATION
        defaultAdresseShouldBeFound("indication.contains=" + DEFAULT_INDICATION);

        // Get all the adresseList where indication contains UPDATED_INDICATION
        defaultAdresseShouldNotBeFound("indication.contains=" + UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void getAllAdressesByIndicationNotContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where indication does not contain DEFAULT_INDICATION
        defaultAdresseShouldNotBeFound("indication.doesNotContain=" + DEFAULT_INDICATION);

        // Get all the adresseList where indication does not contain UPDATED_INDICATION
        defaultAdresseShouldBeFound("indication.doesNotContain=" + UPDATED_INDICATION);
    }


    @Test
    @Transactional
    public void getAllAdressesByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where telephone equals to DEFAULT_TELEPHONE
        defaultAdresseShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the adresseList where telephone equals to UPDATED_TELEPHONE
        defaultAdresseShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllAdressesByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where telephone not equals to DEFAULT_TELEPHONE
        defaultAdresseShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the adresseList where telephone not equals to UPDATED_TELEPHONE
        defaultAdresseShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllAdressesByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultAdresseShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the adresseList where telephone equals to UPDATED_TELEPHONE
        defaultAdresseShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllAdressesByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where telephone is not null
        defaultAdresseShouldBeFound("telephone.specified=true");

        // Get all the adresseList where telephone is null
        defaultAdresseShouldNotBeFound("telephone.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdressesByTelephoneIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where telephone is greater than or equal to DEFAULT_TELEPHONE
        defaultAdresseShouldBeFound("telephone.greaterThanOrEqual=" + DEFAULT_TELEPHONE);

        // Get all the adresseList where telephone is greater than or equal to UPDATED_TELEPHONE
        defaultAdresseShouldNotBeFound("telephone.greaterThanOrEqual=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllAdressesByTelephoneIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where telephone is less than or equal to DEFAULT_TELEPHONE
        defaultAdresseShouldBeFound("telephone.lessThanOrEqual=" + DEFAULT_TELEPHONE);

        // Get all the adresseList where telephone is less than or equal to SMALLER_TELEPHONE
        defaultAdresseShouldNotBeFound("telephone.lessThanOrEqual=" + SMALLER_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllAdressesByTelephoneIsLessThanSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where telephone is less than DEFAULT_TELEPHONE
        defaultAdresseShouldNotBeFound("telephone.lessThan=" + DEFAULT_TELEPHONE);

        // Get all the adresseList where telephone is less than UPDATED_TELEPHONE
        defaultAdresseShouldBeFound("telephone.lessThan=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllAdressesByTelephoneIsGreaterThanSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where telephone is greater than DEFAULT_TELEPHONE
        defaultAdresseShouldNotBeFound("telephone.greaterThan=" + DEFAULT_TELEPHONE);

        // Get all the adresseList where telephone is greater than SMALLER_TELEPHONE
        defaultAdresseShouldBeFound("telephone.greaterThan=" + SMALLER_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllAdressesByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where mobile equals to DEFAULT_MOBILE
        defaultAdresseShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the adresseList where mobile equals to UPDATED_MOBILE
        defaultAdresseShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAdressesByMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where mobile not equals to DEFAULT_MOBILE
        defaultAdresseShouldNotBeFound("mobile.notEquals=" + DEFAULT_MOBILE);

        // Get all the adresseList where mobile not equals to UPDATED_MOBILE
        defaultAdresseShouldBeFound("mobile.notEquals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAdressesByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultAdresseShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the adresseList where mobile equals to UPDATED_MOBILE
        defaultAdresseShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAdressesByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where mobile is not null
        defaultAdresseShouldBeFound("mobile.specified=true");

        // Get all the adresseList where mobile is null
        defaultAdresseShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdressesByMobileIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where mobile is greater than or equal to DEFAULT_MOBILE
        defaultAdresseShouldBeFound("mobile.greaterThanOrEqual=" + DEFAULT_MOBILE);

        // Get all the adresseList where mobile is greater than or equal to UPDATED_MOBILE
        defaultAdresseShouldNotBeFound("mobile.greaterThanOrEqual=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAdressesByMobileIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where mobile is less than or equal to DEFAULT_MOBILE
        defaultAdresseShouldBeFound("mobile.lessThanOrEqual=" + DEFAULT_MOBILE);

        // Get all the adresseList where mobile is less than or equal to SMALLER_MOBILE
        defaultAdresseShouldNotBeFound("mobile.lessThanOrEqual=" + SMALLER_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAdressesByMobileIsLessThanSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where mobile is less than DEFAULT_MOBILE
        defaultAdresseShouldNotBeFound("mobile.lessThan=" + DEFAULT_MOBILE);

        // Get all the adresseList where mobile is less than UPDATED_MOBILE
        defaultAdresseShouldBeFound("mobile.lessThan=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAdressesByMobileIsGreaterThanSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where mobile is greater than DEFAULT_MOBILE
        defaultAdresseShouldNotBeFound("mobile.greaterThan=" + DEFAULT_MOBILE);

        // Get all the adresseList where mobile is greater than SMALLER_MOBILE
        defaultAdresseShouldBeFound("mobile.greaterThan=" + SMALLER_MOBILE);
    }


    @Test
    @Transactional
    public void getAllAdressesByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creeLe equals to DEFAULT_CREE_LE
        defaultAdresseShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the adresseList where creeLe equals to UPDATED_CREE_LE
        defaultAdresseShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllAdressesByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creeLe not equals to DEFAULT_CREE_LE
        defaultAdresseShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the adresseList where creeLe not equals to UPDATED_CREE_LE
        defaultAdresseShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllAdressesByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultAdresseShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the adresseList where creeLe equals to UPDATED_CREE_LE
        defaultAdresseShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllAdressesByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creeLe is not null
        defaultAdresseShouldBeFound("creeLe.specified=true");

        // Get all the adresseList where creeLe is null
        defaultAdresseShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdressesByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultAdresseShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the adresseList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultAdresseShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllAdressesByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultAdresseShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the adresseList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultAdresseShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllAdressesByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creeLe is less than DEFAULT_CREE_LE
        defaultAdresseShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the adresseList where creeLe is less than UPDATED_CREE_LE
        defaultAdresseShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllAdressesByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creeLe is greater than DEFAULT_CREE_LE
        defaultAdresseShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the adresseList where creeLe is greater than SMALLER_CREE_LE
        defaultAdresseShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllAdressesByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creePar equals to DEFAULT_CREE_PAR
        defaultAdresseShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the adresseList where creePar equals to UPDATED_CREE_PAR
        defaultAdresseShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllAdressesByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creePar not equals to DEFAULT_CREE_PAR
        defaultAdresseShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the adresseList where creePar not equals to UPDATED_CREE_PAR
        defaultAdresseShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllAdressesByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultAdresseShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the adresseList where creePar equals to UPDATED_CREE_PAR
        defaultAdresseShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllAdressesByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creePar is not null
        defaultAdresseShouldBeFound("creePar.specified=true");

        // Get all the adresseList where creePar is null
        defaultAdresseShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdressesByCreeParContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creePar contains DEFAULT_CREE_PAR
        defaultAdresseShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the adresseList where creePar contains UPDATED_CREE_PAR
        defaultAdresseShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllAdressesByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where creePar does not contain DEFAULT_CREE_PAR
        defaultAdresseShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the adresseList where creePar does not contain UPDATED_CREE_PAR
        defaultAdresseShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllAdressesByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultAdresseShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the adresseList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultAdresseShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllAdressesByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultAdresseShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the adresseList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultAdresseShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllAdressesByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultAdresseShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the adresseList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultAdresseShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllAdressesByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifieLe is not null
        defaultAdresseShouldBeFound("modifieLe.specified=true");

        // Get all the adresseList where modifieLe is null
        defaultAdresseShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdressesByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultAdresseShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the adresseList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultAdresseShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllAdressesByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultAdresseShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the adresseList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultAdresseShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllAdressesByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultAdresseShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the adresseList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultAdresseShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllAdressesByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultAdresseShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the adresseList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultAdresseShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllAdressesByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultAdresseShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the adresseList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultAdresseShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllAdressesByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultAdresseShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the adresseList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultAdresseShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllAdressesByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultAdresseShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the adresseList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultAdresseShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllAdressesByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifiePar is not null
        defaultAdresseShouldBeFound("modifiePar.specified=true");

        // Get all the adresseList where modifiePar is null
        defaultAdresseShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdressesByModifieParContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultAdresseShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the adresseList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultAdresseShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllAdressesByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultAdresseShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the adresseList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultAdresseShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllAdressesByCommandeIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);
        Commande commande = CommandeResourceIT.createEntity(em);
        em.persist(commande);
        em.flush();
        adresse.addCommande(commande);
        adresseRepository.saveAndFlush(adresse);
        Long commandeId = commande.getId();

        // Get all the adresseList where commande equals to commandeId
        defaultAdresseShouldBeFound("commandeId.equals=" + commandeId);

        // Get all the adresseList where commande equals to commandeId + 1
        defaultAdresseShouldNotBeFound("commandeId.equals=" + (commandeId + 1));
    }


    @Test
    @Transactional
    public void getAllAdressesByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);
        Client client = ClientResourceIT.createEntity(em);
        em.persist(client);
        em.flush();
        adresse.setClient(client);
        adresseRepository.saveAndFlush(adresse);
        Long clientId = client.getId();

        // Get all the adresseList where client equals to clientId
        defaultAdresseShouldBeFound("clientId.equals=" + clientId);

        // Get all the adresseList where client equals to clientId + 1
        defaultAdresseShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }


    @Test
    @Transactional
    public void getAllAdressesByZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);
        Zone zone = ZoneResourceIT.createEntity(em);
        em.persist(zone);
        em.flush();
        adresse.setZone(zone);
        adresseRepository.saveAndFlush(adresse);
        Long zoneId = zone.getId();

        // Get all the adresseList where zone equals to zoneId
        defaultAdresseShouldBeFound("zoneId.equals=" + zoneId);

        // Get all the adresseList where zone equals to zoneId + 1
        defaultAdresseShouldNotBeFound("zoneId.equals=" + (zoneId + 1));
    }


    @Test
    @Transactional
    public void getAllAdressesByCodePostalIsEqualToSomething() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);
        AffectationZone codePostal = AffectationZoneResourceIT.createEntity(em);
        em.persist(codePostal);
        em.flush();
        adresse.setCodePostal(codePostal);
        adresseRepository.saveAndFlush(adresse);
        Long codePostalId = codePostal.getId();

        // Get all the adresseList where codePostal equals to codePostalId
        defaultAdresseShouldBeFound("codePostalId.equals=" + codePostalId);

        // Get all the adresseList where codePostal equals to codePostalId + 1
        defaultAdresseShouldNotBeFound("codePostalId.equals=" + (codePostalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdresseShouldBeFound(String filter) throws Exception {
        restAdresseMockMvc.perform(get("/api/adresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId().intValue())))
            .andExpect(jsonPath("$.[*].principale").value(hasItem(DEFAULT_PRINCIPALE.booleanValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE)))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));

        // Check, that the count call also returns 1
        restAdresseMockMvc.perform(get("/api/adresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdresseShouldNotBeFound(String filter) throws Exception {
        restAdresseMockMvc.perform(get("/api/adresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdresseMockMvc.perform(get("/api/adresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAdresse() throws Exception {
        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Update the adresse
        Adresse updatedAdresse = adresseRepository.findById(adresse.getId()).get();
        // Disconnect from session so that the updates on updatedAdresse are not directly saved in db
        em.detach(updatedAdresse);
        updatedAdresse
            .principale(UPDATED_PRINCIPALE)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .gouvernorat(UPDATED_GOUVERNORAT)
            .ville(UPDATED_VILLE)
            .localite(UPDATED_LOCALITE)
            .indication(UPDATED_INDICATION)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        AdresseDTO adresseDTO = adresseMapper.toDto(updatedAdresse);

        restAdresseMockMvc.perform(put("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isOk());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.isPrincipale()).isEqualTo(UPDATED_PRINCIPALE);
        assertThat(testAdresse.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAdresse.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAdresse.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testAdresse.getGouvernorat()).isEqualTo(UPDATED_GOUVERNORAT);
        assertThat(testAdresse.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdresse.getLocalite()).isEqualTo(UPDATED_LOCALITE);
        assertThat(testAdresse.getIndication()).isEqualTo(UPDATED_INDICATION);
        assertThat(testAdresse.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAdresse.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testAdresse.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testAdresse.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testAdresse.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testAdresse.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the Adresse in Elasticsearch
        verify(mockAdresseSearchRepository, times(1)).save(testAdresse);
    }

    @Test
    @Transactional
    public void updateNonExistingAdresse() throws Exception {
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseMockMvc.perform(put("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Adresse in Elasticsearch
        verify(mockAdresseSearchRepository, times(0)).save(adresse);
    }

    @Test
    @Transactional
    public void deleteAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        int databaseSizeBeforeDelete = adresseRepository.findAll().size();

        // Delete the adresse
        restAdresseMockMvc.perform(delete("/api/adresses/{id}", adresse.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Adresse in Elasticsearch
        verify(mockAdresseSearchRepository, times(1)).deleteById(adresse.getId());
    }

    @Test
    @Transactional
    public void searchAdresse() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);
        when(mockAdresseSearchRepository.search(queryStringQuery("id:" + adresse.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adresse), PageRequest.of(0, 1), 1));

        // Search the adresse
        restAdresseMockMvc.perform(get("/api/_search/adresses?query=id:" + adresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId().intValue())))
            .andExpect(jsonPath("$.[*].principale").value(hasItem(DEFAULT_PRINCIPALE.booleanValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE)))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
