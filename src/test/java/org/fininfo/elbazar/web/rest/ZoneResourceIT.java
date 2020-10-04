package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.Zone;
import org.fininfo.elbazar.domain.Adresse;
import org.fininfo.elbazar.domain.Livraison;
import org.fininfo.elbazar.domain.AffectationZone;
import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.repository.ZoneRepository;
import org.fininfo.elbazar.repository.search.ZoneSearchRepository;
import org.fininfo.elbazar.service.ZoneService;
import org.fininfo.elbazar.service.dto.ZoneDTO;
import org.fininfo.elbazar.service.mapper.ZoneMapper;
import org.fininfo.elbazar.service.dto.ZoneCriteria;
import org.fininfo.elbazar.service.ZoneQueryService;

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

/**
 * Integration tests for the {@link ZoneResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ZoneResourceIT {

    private static final String DEFAULT_CODE_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ZONE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

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
    private ZoneRepository zoneRepository;

    @Autowired
    private ZoneMapper zoneMapper;

    @Autowired
    private ZoneService zoneService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.ZoneSearchRepositoryMockConfiguration
     */
    @Autowired
    private ZoneSearchRepository mockZoneSearchRepository;

    @Autowired
    private ZoneQueryService zoneQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZoneMockMvc;

    private Zone zone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zone createEntity(EntityManager em) {
        Zone zone = new Zone()
            .codeZone(DEFAULT_CODE_ZONE)
            .nom(DEFAULT_NOM)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return zone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zone createUpdatedEntity(EntityManager em) {
        Zone zone = new Zone()
            .codeZone(UPDATED_CODE_ZONE)
            .nom(UPDATED_NOM)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return zone;
    }

    @BeforeEach
    public void initTest() {
        zone = createEntity(em);
    }

    @Test
    @Transactional
    public void createZone() throws Exception {
        int databaseSizeBeforeCreate = zoneRepository.findAll().size();
        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);
        restZoneMockMvc.perform(post("/api/zones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isCreated());

        // Validate the Zone in the database
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeCreate + 1);
        Zone testZone = zoneList.get(zoneList.size() - 1);
        assertThat(testZone.getCodeZone()).isEqualTo(DEFAULT_CODE_ZONE);
        assertThat(testZone.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testZone.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testZone.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testZone.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testZone.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the Zone in Elasticsearch
        verify(mockZoneSearchRepository, times(1)).save(testZone);
    }

    @Test
    @Transactional
    public void createZoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zoneRepository.findAll().size();

        // Create the Zone with an existing ID
        zone.setId(1L);
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZoneMockMvc.perform(post("/api/zones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Zone in the database
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeCreate);

        // Validate the Zone in Elasticsearch
        verify(mockZoneSearchRepository, times(0)).save(zone);
    }


    @Test
    @Transactional
    public void getAllZones() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList
        restZoneMockMvc.perform(get("/api/zones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zone.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeZone").value(hasItem(DEFAULT_CODE_ZONE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getZone() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get the zone
        restZoneMockMvc.perform(get("/api/zones/{id}", zone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zone.getId().intValue()))
            .andExpect(jsonPath("$.codeZone").value(DEFAULT_CODE_ZONE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }


    @Test
    @Transactional
    public void getZonesByIdFiltering() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        Long id = zone.getId();

        defaultZoneShouldBeFound("id.equals=" + id);
        defaultZoneShouldNotBeFound("id.notEquals=" + id);

        defaultZoneShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultZoneShouldNotBeFound("id.greaterThan=" + id);

        defaultZoneShouldBeFound("id.lessThanOrEqual=" + id);
        defaultZoneShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllZonesByCodeZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where codeZone equals to DEFAULT_CODE_ZONE
        defaultZoneShouldBeFound("codeZone.equals=" + DEFAULT_CODE_ZONE);

        // Get all the zoneList where codeZone equals to UPDATED_CODE_ZONE
        defaultZoneShouldNotBeFound("codeZone.equals=" + UPDATED_CODE_ZONE);
    }

    @Test
    @Transactional
    public void getAllZonesByCodeZoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where codeZone not equals to DEFAULT_CODE_ZONE
        defaultZoneShouldNotBeFound("codeZone.notEquals=" + DEFAULT_CODE_ZONE);

        // Get all the zoneList where codeZone not equals to UPDATED_CODE_ZONE
        defaultZoneShouldBeFound("codeZone.notEquals=" + UPDATED_CODE_ZONE);
    }

    @Test
    @Transactional
    public void getAllZonesByCodeZoneIsInShouldWork() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where codeZone in DEFAULT_CODE_ZONE or UPDATED_CODE_ZONE
        defaultZoneShouldBeFound("codeZone.in=" + DEFAULT_CODE_ZONE + "," + UPDATED_CODE_ZONE);

        // Get all the zoneList where codeZone equals to UPDATED_CODE_ZONE
        defaultZoneShouldNotBeFound("codeZone.in=" + UPDATED_CODE_ZONE);
    }

    @Test
    @Transactional
    public void getAllZonesByCodeZoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where codeZone is not null
        defaultZoneShouldBeFound("codeZone.specified=true");

        // Get all the zoneList where codeZone is null
        defaultZoneShouldNotBeFound("codeZone.specified=false");
    }
                @Test
    @Transactional
    public void getAllZonesByCodeZoneContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where codeZone contains DEFAULT_CODE_ZONE
        defaultZoneShouldBeFound("codeZone.contains=" + DEFAULT_CODE_ZONE);

        // Get all the zoneList where codeZone contains UPDATED_CODE_ZONE
        defaultZoneShouldNotBeFound("codeZone.contains=" + UPDATED_CODE_ZONE);
    }

    @Test
    @Transactional
    public void getAllZonesByCodeZoneNotContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where codeZone does not contain DEFAULT_CODE_ZONE
        defaultZoneShouldNotBeFound("codeZone.doesNotContain=" + DEFAULT_CODE_ZONE);

        // Get all the zoneList where codeZone does not contain UPDATED_CODE_ZONE
        defaultZoneShouldBeFound("codeZone.doesNotContain=" + UPDATED_CODE_ZONE);
    }


    @Test
    @Transactional
    public void getAllZonesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where nom equals to DEFAULT_NOM
        defaultZoneShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the zoneList where nom equals to UPDATED_NOM
        defaultZoneShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllZonesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where nom not equals to DEFAULT_NOM
        defaultZoneShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the zoneList where nom not equals to UPDATED_NOM
        defaultZoneShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllZonesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultZoneShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the zoneList where nom equals to UPDATED_NOM
        defaultZoneShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllZonesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where nom is not null
        defaultZoneShouldBeFound("nom.specified=true");

        // Get all the zoneList where nom is null
        defaultZoneShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllZonesByNomContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where nom contains DEFAULT_NOM
        defaultZoneShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the zoneList where nom contains UPDATED_NOM
        defaultZoneShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllZonesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where nom does not contain DEFAULT_NOM
        defaultZoneShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the zoneList where nom does not contain UPDATED_NOM
        defaultZoneShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllZonesByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creeLe equals to DEFAULT_CREE_LE
        defaultZoneShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the zoneList where creeLe equals to UPDATED_CREE_LE
        defaultZoneShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllZonesByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creeLe not equals to DEFAULT_CREE_LE
        defaultZoneShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the zoneList where creeLe not equals to UPDATED_CREE_LE
        defaultZoneShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllZonesByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultZoneShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the zoneList where creeLe equals to UPDATED_CREE_LE
        defaultZoneShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllZonesByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creeLe is not null
        defaultZoneShouldBeFound("creeLe.specified=true");

        // Get all the zoneList where creeLe is null
        defaultZoneShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllZonesByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultZoneShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the zoneList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultZoneShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllZonesByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultZoneShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the zoneList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultZoneShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllZonesByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creeLe is less than DEFAULT_CREE_LE
        defaultZoneShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the zoneList where creeLe is less than UPDATED_CREE_LE
        defaultZoneShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllZonesByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creeLe is greater than DEFAULT_CREE_LE
        defaultZoneShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the zoneList where creeLe is greater than SMALLER_CREE_LE
        defaultZoneShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllZonesByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creePar equals to DEFAULT_CREE_PAR
        defaultZoneShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the zoneList where creePar equals to UPDATED_CREE_PAR
        defaultZoneShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllZonesByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creePar not equals to DEFAULT_CREE_PAR
        defaultZoneShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the zoneList where creePar not equals to UPDATED_CREE_PAR
        defaultZoneShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllZonesByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultZoneShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the zoneList where creePar equals to UPDATED_CREE_PAR
        defaultZoneShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllZonesByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creePar is not null
        defaultZoneShouldBeFound("creePar.specified=true");

        // Get all the zoneList where creePar is null
        defaultZoneShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllZonesByCreeParContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creePar contains DEFAULT_CREE_PAR
        defaultZoneShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the zoneList where creePar contains UPDATED_CREE_PAR
        defaultZoneShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllZonesByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where creePar does not contain DEFAULT_CREE_PAR
        defaultZoneShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the zoneList where creePar does not contain UPDATED_CREE_PAR
        defaultZoneShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllZonesByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultZoneShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the zoneList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultZoneShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllZonesByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultZoneShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the zoneList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultZoneShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllZonesByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultZoneShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the zoneList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultZoneShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllZonesByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifieLe is not null
        defaultZoneShouldBeFound("modifieLe.specified=true");

        // Get all the zoneList where modifieLe is null
        defaultZoneShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllZonesByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultZoneShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the zoneList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultZoneShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllZonesByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultZoneShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the zoneList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultZoneShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllZonesByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultZoneShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the zoneList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultZoneShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllZonesByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultZoneShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the zoneList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultZoneShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllZonesByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultZoneShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the zoneList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultZoneShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllZonesByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultZoneShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the zoneList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultZoneShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllZonesByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultZoneShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the zoneList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultZoneShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllZonesByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifiePar is not null
        defaultZoneShouldBeFound("modifiePar.specified=true");

        // Get all the zoneList where modifiePar is null
        defaultZoneShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllZonesByModifieParContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultZoneShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the zoneList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultZoneShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllZonesByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultZoneShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the zoneList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultZoneShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllZonesByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);
        Adresse adresse = AdresseResourceIT.createEntity(em);
        em.persist(adresse);
        em.flush();
        zone.addAdresse(adresse);
        zoneRepository.saveAndFlush(zone);
        Long adresseId = adresse.getId();

        // Get all the zoneList where adresse equals to adresseId
        defaultZoneShouldBeFound("adresseId.equals=" + adresseId);

        // Get all the zoneList where adresse equals to adresseId + 1
        defaultZoneShouldNotBeFound("adresseId.equals=" + (adresseId + 1));
    }


    @Test
    @Transactional
    public void getAllZonesByLivraisonIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);
        Livraison livraison = LivraisonResourceIT.createEntity(em);
        em.persist(livraison);
        em.flush();
        zone.addLivraison(livraison);
        zoneRepository.saveAndFlush(zone);
        Long livraisonId = livraison.getId();

        // Get all the zoneList where livraison equals to livraisonId
        defaultZoneShouldBeFound("livraisonId.equals=" + livraisonId);

        // Get all the zoneList where livraison equals to livraisonId + 1
        defaultZoneShouldNotBeFound("livraisonId.equals=" + (livraisonId + 1));
    }


    @Test
    @Transactional
    public void getAllZonesByAffectationZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);
        AffectationZone affectationZone = AffectationZoneResourceIT.createEntity(em);
        em.persist(affectationZone);
        em.flush();
        zone.addAffectationZone(affectationZone);
        zoneRepository.saveAndFlush(zone);
        Long affectationZoneId = affectationZone.getId();

        // Get all the zoneList where affectationZone equals to affectationZoneId
        defaultZoneShouldBeFound("affectationZoneId.equals=" + affectationZoneId);

        // Get all the zoneList where affectationZone equals to affectationZoneId + 1
        defaultZoneShouldNotBeFound("affectationZoneId.equals=" + (affectationZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllZonesByCommandeIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);
        Commande commande = CommandeResourceIT.createEntity(em);
        em.persist(commande);
        em.flush();
        zone.addCommande(commande);
        zoneRepository.saveAndFlush(zone);
        Long commandeId = commande.getId();

        // Get all the zoneList where commande equals to commandeId
        defaultZoneShouldBeFound("commandeId.equals=" + commandeId);

        // Get all the zoneList where commande equals to commandeId + 1
        defaultZoneShouldNotBeFound("commandeId.equals=" + (commandeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultZoneShouldBeFound(String filter) throws Exception {
        restZoneMockMvc.perform(get("/api/zones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zone.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeZone").value(hasItem(DEFAULT_CODE_ZONE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));

        // Check, that the count call also returns 1
        restZoneMockMvc.perform(get("/api/zones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultZoneShouldNotBeFound(String filter) throws Exception {
        restZoneMockMvc.perform(get("/api/zones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restZoneMockMvc.perform(get("/api/zones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingZone() throws Exception {
        // Get the zone
        restZoneMockMvc.perform(get("/api/zones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZone() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        int databaseSizeBeforeUpdate = zoneRepository.findAll().size();

        // Update the zone
        Zone updatedZone = zoneRepository.findById(zone.getId()).get();
        // Disconnect from session so that the updates on updatedZone are not directly saved in db
        em.detach(updatedZone);
        updatedZone
            .codeZone(UPDATED_CODE_ZONE)
            .nom(UPDATED_NOM)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        ZoneDTO zoneDTO = zoneMapper.toDto(updatedZone);

        restZoneMockMvc.perform(put("/api/zones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isOk());

        // Validate the Zone in the database
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeUpdate);
        Zone testZone = zoneList.get(zoneList.size() - 1);
        assertThat(testZone.getCodeZone()).isEqualTo(UPDATED_CODE_ZONE);
        assertThat(testZone.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testZone.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testZone.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testZone.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testZone.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the Zone in Elasticsearch
        verify(mockZoneSearchRepository, times(1)).save(testZone);
    }

    @Test
    @Transactional
    public void updateNonExistingZone() throws Exception {
        int databaseSizeBeforeUpdate = zoneRepository.findAll().size();

        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZoneMockMvc.perform(put("/api/zones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Zone in the database
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Zone in Elasticsearch
        verify(mockZoneSearchRepository, times(0)).save(zone);
    }

    @Test
    @Transactional
    public void deleteZone() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        int databaseSizeBeforeDelete = zoneRepository.findAll().size();

        // Delete the zone
        restZoneMockMvc.perform(delete("/api/zones/{id}", zone.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Zone in Elasticsearch
        verify(mockZoneSearchRepository, times(1)).deleteById(zone.getId());
    }

    @Test
    @Transactional
    public void searchZone() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        zoneRepository.saveAndFlush(zone);
        when(mockZoneSearchRepository.search(queryStringQuery("id:" + zone.getId())))
            .thenReturn(Collections.singletonList(zone));

        // Search the zone
        restZoneMockMvc.perform(get("/api/_search/zones?query=id:" + zone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zone.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeZone").value(hasItem(DEFAULT_CODE_ZONE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
