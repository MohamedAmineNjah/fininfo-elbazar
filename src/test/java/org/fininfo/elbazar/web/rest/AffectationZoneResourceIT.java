package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.AffectationZone;
import org.fininfo.elbazar.domain.Adresse;
import org.fininfo.elbazar.domain.Zone;
import org.fininfo.elbazar.repository.AffectationZoneRepository;
import org.fininfo.elbazar.repository.search.AffectationZoneSearchRepository;
import org.fininfo.elbazar.service.AffectationZoneService;
import org.fininfo.elbazar.service.dto.AffectationZoneDTO;
import org.fininfo.elbazar.service.mapper.AffectationZoneMapper;
import org.fininfo.elbazar.service.dto.AffectationZoneCriteria;
import org.fininfo.elbazar.service.AffectationZoneQueryService;

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
 * Integration tests for the {@link AffectationZoneResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AffectationZoneResourceIT {

    private static final String DEFAULT_GOUVERNORAT = "AAAAAAAAAA";
    private static final String UPDATED_GOUVERNORAT = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALITE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALITE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE_POSTAL = 1;
    private static final Integer UPDATED_CODE_POSTAL = 2;
    private static final Integer SMALLER_CODE_POSTAL = 1 - 1;

    private static final LocalDate DEFAULT_MODIFIE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIE_LE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_MODIFIE_LE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_MODIFIE_PAR = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIE_PAR = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_VILLE = 1;
    private static final Integer UPDATED_ID_VILLE = 2;
    private static final Integer SMALLER_ID_VILLE = 1 - 1;

    @Autowired
    private AffectationZoneRepository affectationZoneRepository;

    @Autowired
    private AffectationZoneMapper affectationZoneMapper;

    @Autowired
    private AffectationZoneService affectationZoneService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.AffectationZoneSearchRepositoryMockConfiguration
     */
    @Autowired
    private AffectationZoneSearchRepository mockAffectationZoneSearchRepository;

    @Autowired
    private AffectationZoneQueryService affectationZoneQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAffectationZoneMockMvc;

    private AffectationZone affectationZone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AffectationZone createEntity(EntityManager em) {
        AffectationZone affectationZone = new AffectationZone()
            .gouvernorat(DEFAULT_GOUVERNORAT)
            .ville(DEFAULT_VILLE)
            .localite(DEFAULT_LOCALITE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR)
            .idVille(DEFAULT_ID_VILLE);
        return affectationZone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AffectationZone createUpdatedEntity(EntityManager em) {
        AffectationZone affectationZone = new AffectationZone()
            .gouvernorat(UPDATED_GOUVERNORAT)
            .ville(UPDATED_VILLE)
            .localite(UPDATED_LOCALITE)
            .codePostal(UPDATED_CODE_POSTAL)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR)
            .idVille(UPDATED_ID_VILLE);
        return affectationZone;
    }

    @BeforeEach
    public void initTest() {
        affectationZone = createEntity(em);
    }

    @Test
    @Transactional
    public void createAffectationZone() throws Exception {
        int databaseSizeBeforeCreate = affectationZoneRepository.findAll().size();
        // Create the AffectationZone
        AffectationZoneDTO affectationZoneDTO = affectationZoneMapper.toDto(affectationZone);
        restAffectationZoneMockMvc.perform(post("/api/affectation-zones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(affectationZoneDTO)))
            .andExpect(status().isCreated());

        // Validate the AffectationZone in the database
        List<AffectationZone> affectationZoneList = affectationZoneRepository.findAll();
        assertThat(affectationZoneList).hasSize(databaseSizeBeforeCreate + 1);
        AffectationZone testAffectationZone = affectationZoneList.get(affectationZoneList.size() - 1);
        assertThat(testAffectationZone.getGouvernorat()).isEqualTo(DEFAULT_GOUVERNORAT);
        assertThat(testAffectationZone.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAffectationZone.getLocalite()).isEqualTo(DEFAULT_LOCALITE);
        assertThat(testAffectationZone.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testAffectationZone.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testAffectationZone.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);
        assertThat(testAffectationZone.getIdVille()).isEqualTo(DEFAULT_ID_VILLE);

        // Validate the AffectationZone in Elasticsearch
        verify(mockAffectationZoneSearchRepository, times(1)).save(testAffectationZone);
    }

    @Test
    @Transactional
    public void createAffectationZoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = affectationZoneRepository.findAll().size();

        // Create the AffectationZone with an existing ID
        affectationZone.setId(1L);
        AffectationZoneDTO affectationZoneDTO = affectationZoneMapper.toDto(affectationZone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffectationZoneMockMvc.perform(post("/api/affectation-zones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(affectationZoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AffectationZone in the database
        List<AffectationZone> affectationZoneList = affectationZoneRepository.findAll();
        assertThat(affectationZoneList).hasSize(databaseSizeBeforeCreate);

        // Validate the AffectationZone in Elasticsearch
        verify(mockAffectationZoneSearchRepository, times(0)).save(affectationZone);
    }


    @Test
    @Transactional
    public void checkGouvernoratIsRequired() throws Exception {
        int databaseSizeBeforeTest = affectationZoneRepository.findAll().size();
        // set the field null
        affectationZone.setGouvernorat(null);

        // Create the AffectationZone, which fails.
        AffectationZoneDTO affectationZoneDTO = affectationZoneMapper.toDto(affectationZone);


        restAffectationZoneMockMvc.perform(post("/api/affectation-zones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(affectationZoneDTO)))
            .andExpect(status().isBadRequest());

        List<AffectationZone> affectationZoneList = affectationZoneRepository.findAll();
        assertThat(affectationZoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = affectationZoneRepository.findAll().size();
        // set the field null
        affectationZone.setVille(null);

        // Create the AffectationZone, which fails.
        AffectationZoneDTO affectationZoneDTO = affectationZoneMapper.toDto(affectationZone);


        restAffectationZoneMockMvc.perform(post("/api/affectation-zones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(affectationZoneDTO)))
            .andExpect(status().isBadRequest());

        List<AffectationZone> affectationZoneList = affectationZoneRepository.findAll();
        assertThat(affectationZoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocaliteIsRequired() throws Exception {
        int databaseSizeBeforeTest = affectationZoneRepository.findAll().size();
        // set the field null
        affectationZone.setLocalite(null);

        // Create the AffectationZone, which fails.
        AffectationZoneDTO affectationZoneDTO = affectationZoneMapper.toDto(affectationZone);


        restAffectationZoneMockMvc.perform(post("/api/affectation-zones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(affectationZoneDTO)))
            .andExpect(status().isBadRequest());

        List<AffectationZone> affectationZoneList = affectationZoneRepository.findAll();
        assertThat(affectationZoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodePostalIsRequired() throws Exception {
        int databaseSizeBeforeTest = affectationZoneRepository.findAll().size();
        // set the field null
        affectationZone.setCodePostal(null);

        // Create the AffectationZone, which fails.
        AffectationZoneDTO affectationZoneDTO = affectationZoneMapper.toDto(affectationZone);


        restAffectationZoneMockMvc.perform(post("/api/affectation-zones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(affectationZoneDTO)))
            .andExpect(status().isBadRequest());

        List<AffectationZone> affectationZoneList = affectationZoneRepository.findAll();
        assertThat(affectationZoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAffectationZones() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList
        restAffectationZoneMockMvc.perform(get("/api/affectation-zones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affectationZone.getId().intValue())))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].idVille").value(hasItem(DEFAULT_ID_VILLE)));
    }
    
    @Test
    @Transactional
    public void getAffectationZone() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get the affectationZone
        restAffectationZoneMockMvc.perform(get("/api/affectation-zones/{id}", affectationZone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(affectationZone.getId().intValue()))
            .andExpect(jsonPath("$.gouvernorat").value(DEFAULT_GOUVERNORAT))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.localite").value(DEFAULT_LOCALITE))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR))
            .andExpect(jsonPath("$.idVille").value(DEFAULT_ID_VILLE));
    }


    @Test
    @Transactional
    public void getAffectationZonesByIdFiltering() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        Long id = affectationZone.getId();

        defaultAffectationZoneShouldBeFound("id.equals=" + id);
        defaultAffectationZoneShouldNotBeFound("id.notEquals=" + id);

        defaultAffectationZoneShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAffectationZoneShouldNotBeFound("id.greaterThan=" + id);

        defaultAffectationZoneShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAffectationZoneShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAffectationZonesByGouvernoratIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where gouvernorat equals to DEFAULT_GOUVERNORAT
        defaultAffectationZoneShouldBeFound("gouvernorat.equals=" + DEFAULT_GOUVERNORAT);

        // Get all the affectationZoneList where gouvernorat equals to UPDATED_GOUVERNORAT
        defaultAffectationZoneShouldNotBeFound("gouvernorat.equals=" + UPDATED_GOUVERNORAT);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByGouvernoratIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where gouvernorat not equals to DEFAULT_GOUVERNORAT
        defaultAffectationZoneShouldNotBeFound("gouvernorat.notEquals=" + DEFAULT_GOUVERNORAT);

        // Get all the affectationZoneList where gouvernorat not equals to UPDATED_GOUVERNORAT
        defaultAffectationZoneShouldBeFound("gouvernorat.notEquals=" + UPDATED_GOUVERNORAT);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByGouvernoratIsInShouldWork() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where gouvernorat in DEFAULT_GOUVERNORAT or UPDATED_GOUVERNORAT
        defaultAffectationZoneShouldBeFound("gouvernorat.in=" + DEFAULT_GOUVERNORAT + "," + UPDATED_GOUVERNORAT);

        // Get all the affectationZoneList where gouvernorat equals to UPDATED_GOUVERNORAT
        defaultAffectationZoneShouldNotBeFound("gouvernorat.in=" + UPDATED_GOUVERNORAT);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByGouvernoratIsNullOrNotNull() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where gouvernorat is not null
        defaultAffectationZoneShouldBeFound("gouvernorat.specified=true");

        // Get all the affectationZoneList where gouvernorat is null
        defaultAffectationZoneShouldNotBeFound("gouvernorat.specified=false");
    }
                @Test
    @Transactional
    public void getAllAffectationZonesByGouvernoratContainsSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where gouvernorat contains DEFAULT_GOUVERNORAT
        defaultAffectationZoneShouldBeFound("gouvernorat.contains=" + DEFAULT_GOUVERNORAT);

        // Get all the affectationZoneList where gouvernorat contains UPDATED_GOUVERNORAT
        defaultAffectationZoneShouldNotBeFound("gouvernorat.contains=" + UPDATED_GOUVERNORAT);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByGouvernoratNotContainsSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where gouvernorat does not contain DEFAULT_GOUVERNORAT
        defaultAffectationZoneShouldNotBeFound("gouvernorat.doesNotContain=" + DEFAULT_GOUVERNORAT);

        // Get all the affectationZoneList where gouvernorat does not contain UPDATED_GOUVERNORAT
        defaultAffectationZoneShouldBeFound("gouvernorat.doesNotContain=" + UPDATED_GOUVERNORAT);
    }


    @Test
    @Transactional
    public void getAllAffectationZonesByVilleIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where ville equals to DEFAULT_VILLE
        defaultAffectationZoneShouldBeFound("ville.equals=" + DEFAULT_VILLE);

        // Get all the affectationZoneList where ville equals to UPDATED_VILLE
        defaultAffectationZoneShouldNotBeFound("ville.equals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByVilleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where ville not equals to DEFAULT_VILLE
        defaultAffectationZoneShouldNotBeFound("ville.notEquals=" + DEFAULT_VILLE);

        // Get all the affectationZoneList where ville not equals to UPDATED_VILLE
        defaultAffectationZoneShouldBeFound("ville.notEquals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByVilleIsInShouldWork() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where ville in DEFAULT_VILLE or UPDATED_VILLE
        defaultAffectationZoneShouldBeFound("ville.in=" + DEFAULT_VILLE + "," + UPDATED_VILLE);

        // Get all the affectationZoneList where ville equals to UPDATED_VILLE
        defaultAffectationZoneShouldNotBeFound("ville.in=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByVilleIsNullOrNotNull() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where ville is not null
        defaultAffectationZoneShouldBeFound("ville.specified=true");

        // Get all the affectationZoneList where ville is null
        defaultAffectationZoneShouldNotBeFound("ville.specified=false");
    }
                @Test
    @Transactional
    public void getAllAffectationZonesByVilleContainsSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where ville contains DEFAULT_VILLE
        defaultAffectationZoneShouldBeFound("ville.contains=" + DEFAULT_VILLE);

        // Get all the affectationZoneList where ville contains UPDATED_VILLE
        defaultAffectationZoneShouldNotBeFound("ville.contains=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByVilleNotContainsSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where ville does not contain DEFAULT_VILLE
        defaultAffectationZoneShouldNotBeFound("ville.doesNotContain=" + DEFAULT_VILLE);

        // Get all the affectationZoneList where ville does not contain UPDATED_VILLE
        defaultAffectationZoneShouldBeFound("ville.doesNotContain=" + UPDATED_VILLE);
    }


    @Test
    @Transactional
    public void getAllAffectationZonesByLocaliteIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where localite equals to DEFAULT_LOCALITE
        defaultAffectationZoneShouldBeFound("localite.equals=" + DEFAULT_LOCALITE);

        // Get all the affectationZoneList where localite equals to UPDATED_LOCALITE
        defaultAffectationZoneShouldNotBeFound("localite.equals=" + UPDATED_LOCALITE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByLocaliteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where localite not equals to DEFAULT_LOCALITE
        defaultAffectationZoneShouldNotBeFound("localite.notEquals=" + DEFAULT_LOCALITE);

        // Get all the affectationZoneList where localite not equals to UPDATED_LOCALITE
        defaultAffectationZoneShouldBeFound("localite.notEquals=" + UPDATED_LOCALITE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByLocaliteIsInShouldWork() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where localite in DEFAULT_LOCALITE or UPDATED_LOCALITE
        defaultAffectationZoneShouldBeFound("localite.in=" + DEFAULT_LOCALITE + "," + UPDATED_LOCALITE);

        // Get all the affectationZoneList where localite equals to UPDATED_LOCALITE
        defaultAffectationZoneShouldNotBeFound("localite.in=" + UPDATED_LOCALITE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByLocaliteIsNullOrNotNull() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where localite is not null
        defaultAffectationZoneShouldBeFound("localite.specified=true");

        // Get all the affectationZoneList where localite is null
        defaultAffectationZoneShouldNotBeFound("localite.specified=false");
    }
                @Test
    @Transactional
    public void getAllAffectationZonesByLocaliteContainsSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where localite contains DEFAULT_LOCALITE
        defaultAffectationZoneShouldBeFound("localite.contains=" + DEFAULT_LOCALITE);

        // Get all the affectationZoneList where localite contains UPDATED_LOCALITE
        defaultAffectationZoneShouldNotBeFound("localite.contains=" + UPDATED_LOCALITE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByLocaliteNotContainsSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where localite does not contain DEFAULT_LOCALITE
        defaultAffectationZoneShouldNotBeFound("localite.doesNotContain=" + DEFAULT_LOCALITE);

        // Get all the affectationZoneList where localite does not contain UPDATED_LOCALITE
        defaultAffectationZoneShouldBeFound("localite.doesNotContain=" + UPDATED_LOCALITE);
    }


    @Test
    @Transactional
    public void getAllAffectationZonesByCodePostalIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where codePostal equals to DEFAULT_CODE_POSTAL
        defaultAffectationZoneShouldBeFound("codePostal.equals=" + DEFAULT_CODE_POSTAL);

        // Get all the affectationZoneList where codePostal equals to UPDATED_CODE_POSTAL
        defaultAffectationZoneShouldNotBeFound("codePostal.equals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByCodePostalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where codePostal not equals to DEFAULT_CODE_POSTAL
        defaultAffectationZoneShouldNotBeFound("codePostal.notEquals=" + DEFAULT_CODE_POSTAL);

        // Get all the affectationZoneList where codePostal not equals to UPDATED_CODE_POSTAL
        defaultAffectationZoneShouldBeFound("codePostal.notEquals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByCodePostalIsInShouldWork() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where codePostal in DEFAULT_CODE_POSTAL or UPDATED_CODE_POSTAL
        defaultAffectationZoneShouldBeFound("codePostal.in=" + DEFAULT_CODE_POSTAL + "," + UPDATED_CODE_POSTAL);

        // Get all the affectationZoneList where codePostal equals to UPDATED_CODE_POSTAL
        defaultAffectationZoneShouldNotBeFound("codePostal.in=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByCodePostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where codePostal is not null
        defaultAffectationZoneShouldBeFound("codePostal.specified=true");

        // Get all the affectationZoneList where codePostal is null
        defaultAffectationZoneShouldNotBeFound("codePostal.specified=false");
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByCodePostalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where codePostal is greater than or equal to DEFAULT_CODE_POSTAL
        defaultAffectationZoneShouldBeFound("codePostal.greaterThanOrEqual=" + DEFAULT_CODE_POSTAL);

        // Get all the affectationZoneList where codePostal is greater than or equal to UPDATED_CODE_POSTAL
        defaultAffectationZoneShouldNotBeFound("codePostal.greaterThanOrEqual=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByCodePostalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where codePostal is less than or equal to DEFAULT_CODE_POSTAL
        defaultAffectationZoneShouldBeFound("codePostal.lessThanOrEqual=" + DEFAULT_CODE_POSTAL);

        // Get all the affectationZoneList where codePostal is less than or equal to SMALLER_CODE_POSTAL
        defaultAffectationZoneShouldNotBeFound("codePostal.lessThanOrEqual=" + SMALLER_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByCodePostalIsLessThanSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where codePostal is less than DEFAULT_CODE_POSTAL
        defaultAffectationZoneShouldNotBeFound("codePostal.lessThan=" + DEFAULT_CODE_POSTAL);

        // Get all the affectationZoneList where codePostal is less than UPDATED_CODE_POSTAL
        defaultAffectationZoneShouldBeFound("codePostal.lessThan=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByCodePostalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where codePostal is greater than DEFAULT_CODE_POSTAL
        defaultAffectationZoneShouldNotBeFound("codePostal.greaterThan=" + DEFAULT_CODE_POSTAL);

        // Get all the affectationZoneList where codePostal is greater than SMALLER_CODE_POSTAL
        defaultAffectationZoneShouldBeFound("codePostal.greaterThan=" + SMALLER_CODE_POSTAL);
    }


    @Test
    @Transactional
    public void getAllAffectationZonesByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultAffectationZoneShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the affectationZoneList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultAffectationZoneShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultAffectationZoneShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the affectationZoneList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultAffectationZoneShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultAffectationZoneShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the affectationZoneList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultAffectationZoneShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifieLe is not null
        defaultAffectationZoneShouldBeFound("modifieLe.specified=true");

        // Get all the affectationZoneList where modifieLe is null
        defaultAffectationZoneShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultAffectationZoneShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the affectationZoneList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultAffectationZoneShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultAffectationZoneShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the affectationZoneList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultAffectationZoneShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultAffectationZoneShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the affectationZoneList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultAffectationZoneShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultAffectationZoneShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the affectationZoneList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultAffectationZoneShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllAffectationZonesByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultAffectationZoneShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the affectationZoneList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultAffectationZoneShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultAffectationZoneShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the affectationZoneList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultAffectationZoneShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultAffectationZoneShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the affectationZoneList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultAffectationZoneShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifiePar is not null
        defaultAffectationZoneShouldBeFound("modifiePar.specified=true");

        // Get all the affectationZoneList where modifiePar is null
        defaultAffectationZoneShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllAffectationZonesByModifieParContainsSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultAffectationZoneShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the affectationZoneList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultAffectationZoneShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultAffectationZoneShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the affectationZoneList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultAffectationZoneShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllAffectationZonesByIdVilleIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where idVille equals to DEFAULT_ID_VILLE
        defaultAffectationZoneShouldBeFound("idVille.equals=" + DEFAULT_ID_VILLE);

        // Get all the affectationZoneList where idVille equals to UPDATED_ID_VILLE
        defaultAffectationZoneShouldNotBeFound("idVille.equals=" + UPDATED_ID_VILLE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByIdVilleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where idVille not equals to DEFAULT_ID_VILLE
        defaultAffectationZoneShouldNotBeFound("idVille.notEquals=" + DEFAULT_ID_VILLE);

        // Get all the affectationZoneList where idVille not equals to UPDATED_ID_VILLE
        defaultAffectationZoneShouldBeFound("idVille.notEquals=" + UPDATED_ID_VILLE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByIdVilleIsInShouldWork() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where idVille in DEFAULT_ID_VILLE or UPDATED_ID_VILLE
        defaultAffectationZoneShouldBeFound("idVille.in=" + DEFAULT_ID_VILLE + "," + UPDATED_ID_VILLE);

        // Get all the affectationZoneList where idVille equals to UPDATED_ID_VILLE
        defaultAffectationZoneShouldNotBeFound("idVille.in=" + UPDATED_ID_VILLE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByIdVilleIsNullOrNotNull() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where idVille is not null
        defaultAffectationZoneShouldBeFound("idVille.specified=true");

        // Get all the affectationZoneList where idVille is null
        defaultAffectationZoneShouldNotBeFound("idVille.specified=false");
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByIdVilleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where idVille is greater than or equal to DEFAULT_ID_VILLE
        defaultAffectationZoneShouldBeFound("idVille.greaterThanOrEqual=" + DEFAULT_ID_VILLE);

        // Get all the affectationZoneList where idVille is greater than or equal to UPDATED_ID_VILLE
        defaultAffectationZoneShouldNotBeFound("idVille.greaterThanOrEqual=" + UPDATED_ID_VILLE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByIdVilleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where idVille is less than or equal to DEFAULT_ID_VILLE
        defaultAffectationZoneShouldBeFound("idVille.lessThanOrEqual=" + DEFAULT_ID_VILLE);

        // Get all the affectationZoneList where idVille is less than or equal to SMALLER_ID_VILLE
        defaultAffectationZoneShouldNotBeFound("idVille.lessThanOrEqual=" + SMALLER_ID_VILLE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByIdVilleIsLessThanSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where idVille is less than DEFAULT_ID_VILLE
        defaultAffectationZoneShouldNotBeFound("idVille.lessThan=" + DEFAULT_ID_VILLE);

        // Get all the affectationZoneList where idVille is less than UPDATED_ID_VILLE
        defaultAffectationZoneShouldBeFound("idVille.lessThan=" + UPDATED_ID_VILLE);
    }

    @Test
    @Transactional
    public void getAllAffectationZonesByIdVilleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        // Get all the affectationZoneList where idVille is greater than DEFAULT_ID_VILLE
        defaultAffectationZoneShouldNotBeFound("idVille.greaterThan=" + DEFAULT_ID_VILLE);

        // Get all the affectationZoneList where idVille is greater than SMALLER_ID_VILLE
        defaultAffectationZoneShouldBeFound("idVille.greaterThan=" + SMALLER_ID_VILLE);
    }


    @Test
    @Transactional
    public void getAllAffectationZonesByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);
        Adresse adresse = AdresseResourceIT.createEntity(em);
        em.persist(adresse);
        em.flush();
        affectationZone.addAdresse(adresse);
        affectationZoneRepository.saveAndFlush(affectationZone);
        Long adresseId = adresse.getId();

        // Get all the affectationZoneList where adresse equals to adresseId
        defaultAffectationZoneShouldBeFound("adresseId.equals=" + adresseId);

        // Get all the affectationZoneList where adresse equals to adresseId + 1
        defaultAffectationZoneShouldNotBeFound("adresseId.equals=" + (adresseId + 1));
    }


    @Test
    @Transactional
    public void getAllAffectationZonesByZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);
        Zone zone = ZoneResourceIT.createEntity(em);
        em.persist(zone);
        em.flush();
        affectationZone.setZone(zone);
        affectationZoneRepository.saveAndFlush(affectationZone);
        Long zoneId = zone.getId();

        // Get all the affectationZoneList where zone equals to zoneId
        defaultAffectationZoneShouldBeFound("zoneId.equals=" + zoneId);

        // Get all the affectationZoneList where zone equals to zoneId + 1
        defaultAffectationZoneShouldNotBeFound("zoneId.equals=" + (zoneId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAffectationZoneShouldBeFound(String filter) throws Exception {
        restAffectationZoneMockMvc.perform(get("/api/affectation-zones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affectationZone.getId().intValue())))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].idVille").value(hasItem(DEFAULT_ID_VILLE)));

        // Check, that the count call also returns 1
        restAffectationZoneMockMvc.perform(get("/api/affectation-zones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAffectationZoneShouldNotBeFound(String filter) throws Exception {
        restAffectationZoneMockMvc.perform(get("/api/affectation-zones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAffectationZoneMockMvc.perform(get("/api/affectation-zones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAffectationZone() throws Exception {
        // Get the affectationZone
        restAffectationZoneMockMvc.perform(get("/api/affectation-zones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAffectationZone() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        int databaseSizeBeforeUpdate = affectationZoneRepository.findAll().size();

        // Update the affectationZone
        AffectationZone updatedAffectationZone = affectationZoneRepository.findById(affectationZone.getId()).get();
        // Disconnect from session so that the updates on updatedAffectationZone are not directly saved in db
        em.detach(updatedAffectationZone);
        updatedAffectationZone
            .gouvernorat(UPDATED_GOUVERNORAT)
            .ville(UPDATED_VILLE)
            .localite(UPDATED_LOCALITE)
            .codePostal(UPDATED_CODE_POSTAL)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR)
            .idVille(UPDATED_ID_VILLE);
        AffectationZoneDTO affectationZoneDTO = affectationZoneMapper.toDto(updatedAffectationZone);

        restAffectationZoneMockMvc.perform(put("/api/affectation-zones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(affectationZoneDTO)))
            .andExpect(status().isOk());

        // Validate the AffectationZone in the database
        List<AffectationZone> affectationZoneList = affectationZoneRepository.findAll();
        assertThat(affectationZoneList).hasSize(databaseSizeBeforeUpdate);
        AffectationZone testAffectationZone = affectationZoneList.get(affectationZoneList.size() - 1);
        assertThat(testAffectationZone.getGouvernorat()).isEqualTo(UPDATED_GOUVERNORAT);
        assertThat(testAffectationZone.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAffectationZone.getLocalite()).isEqualTo(UPDATED_LOCALITE);
        assertThat(testAffectationZone.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAffectationZone.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testAffectationZone.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);
        assertThat(testAffectationZone.getIdVille()).isEqualTo(UPDATED_ID_VILLE);

        // Validate the AffectationZone in Elasticsearch
        verify(mockAffectationZoneSearchRepository, times(1)).save(testAffectationZone);
    }

    @Test
    @Transactional
    public void updateNonExistingAffectationZone() throws Exception {
        int databaseSizeBeforeUpdate = affectationZoneRepository.findAll().size();

        // Create the AffectationZone
        AffectationZoneDTO affectationZoneDTO = affectationZoneMapper.toDto(affectationZone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffectationZoneMockMvc.perform(put("/api/affectation-zones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(affectationZoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AffectationZone in the database
        List<AffectationZone> affectationZoneList = affectationZoneRepository.findAll();
        assertThat(affectationZoneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AffectationZone in Elasticsearch
        verify(mockAffectationZoneSearchRepository, times(0)).save(affectationZone);
    }

    @Test
    @Transactional
    public void deleteAffectationZone() throws Exception {
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);

        int databaseSizeBeforeDelete = affectationZoneRepository.findAll().size();

        // Delete the affectationZone
        restAffectationZoneMockMvc.perform(delete("/api/affectation-zones/{id}", affectationZone.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AffectationZone> affectationZoneList = affectationZoneRepository.findAll();
        assertThat(affectationZoneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AffectationZone in Elasticsearch
        verify(mockAffectationZoneSearchRepository, times(1)).deleteById(affectationZone.getId());
    }

    @Test
    @Transactional
    public void searchAffectationZone() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        affectationZoneRepository.saveAndFlush(affectationZone);
        when(mockAffectationZoneSearchRepository.search(queryStringQuery("id:" + affectationZone.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(affectationZone), PageRequest.of(0, 1), 1));

        // Search the affectationZone
        restAffectationZoneMockMvc.perform(get("/api/_search/affectation-zones?query=id:" + affectationZone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affectationZone.getId().intValue())))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].idVille").value(hasItem(DEFAULT_ID_VILLE)));
    }
}
