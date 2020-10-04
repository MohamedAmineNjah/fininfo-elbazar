package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.MouvementStock;
import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.repository.MouvementStockRepository;
import org.fininfo.elbazar.repository.search.MouvementStockSearchRepository;
import org.fininfo.elbazar.service.MouvementStockService;
import org.fininfo.elbazar.service.dto.MouvementStockDTO;
import org.fininfo.elbazar.service.mapper.MouvementStockMapper;
import org.fininfo.elbazar.service.dto.MouvementStockCriteria;
import org.fininfo.elbazar.service.MouvementStockQueryService;

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

import org.fininfo.elbazar.domain.enumeration.TypeMvt;
/**
 * Integration tests for the {@link MouvementStockResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MouvementStockResourceIT {

    private static final TypeMvt DEFAULT_TYPE = TypeMvt.EntreeStock;
    private static final TypeMvt UPDATED_TYPE = TypeMvt.SortieStock;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_SENS = 1;
    private static final Integer UPDATED_SENS = 2;
    private static final Integer SMALLER_SENS = 1 - 1;

    private static final Double DEFAULT_QUANTITE = 1D;
    private static final Double UPDATED_QUANTITE = 2D;
    private static final Double SMALLER_QUANTITE = 1D - 1D;

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

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    @Autowired
    private MouvementStockRepository mouvementStockRepository;

    @Autowired
    private MouvementStockMapper mouvementStockMapper;

    @Autowired
    private MouvementStockService mouvementStockService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.MouvementStockSearchRepositoryMockConfiguration
     */
    @Autowired
    private MouvementStockSearchRepository mockMouvementStockSearchRepository;

    @Autowired
    private MouvementStockQueryService mouvementStockQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMouvementStockMockMvc;

    private MouvementStock mouvementStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MouvementStock createEntity(EntityManager em) {
        MouvementStock mouvementStock = new MouvementStock()
            .type(DEFAULT_TYPE)
            .date(DEFAULT_DATE)
            .sens(DEFAULT_SENS)
            .quantite(DEFAULT_QUANTITE)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR)
            .reference(DEFAULT_REFERENCE);
        return mouvementStock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MouvementStock createUpdatedEntity(EntityManager em) {
        MouvementStock mouvementStock = new MouvementStock()
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .sens(UPDATED_SENS)
            .quantite(UPDATED_QUANTITE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR)
            .reference(UPDATED_REFERENCE);
        return mouvementStock;
    }

    @BeforeEach
    public void initTest() {
        mouvementStock = createEntity(em);
    }

    @Test
    @Transactional
    public void createMouvementStock() throws Exception {
        int databaseSizeBeforeCreate = mouvementStockRepository.findAll().size();
        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);
        restMouvementStockMockMvc.perform(post("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isCreated());

        // Validate the MouvementStock in the database
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeCreate + 1);
        MouvementStock testMouvementStock = mouvementStockList.get(mouvementStockList.size() - 1);
        assertThat(testMouvementStock.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMouvementStock.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMouvementStock.getSens()).isEqualTo(DEFAULT_SENS);
        assertThat(testMouvementStock.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testMouvementStock.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testMouvementStock.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testMouvementStock.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testMouvementStock.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);
        assertThat(testMouvementStock.getReference()).isEqualTo(DEFAULT_REFERENCE);

        // Validate the MouvementStock in Elasticsearch
        verify(mockMouvementStockSearchRepository, times(1)).save(testMouvementStock);
    }

    @Test
    @Transactional
    public void createMouvementStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mouvementStockRepository.findAll().size();

        // Create the MouvementStock with an existing ID
        mouvementStock.setId(1L);
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMouvementStockMockMvc.perform(post("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MouvementStock in the database
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeCreate);

        // Validate the MouvementStock in Elasticsearch
        verify(mockMouvementStockSearchRepository, times(0)).save(mouvementStock);
    }


    @Test
    @Transactional
    public void getAllMouvementStocks() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouvementStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].sens").value(hasItem(DEFAULT_SENS)))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)));
    }
    
    @Test
    @Transactional
    public void getMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get the mouvementStock
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks/{id}", mouvementStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mouvementStock.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.sens").value(DEFAULT_SENS))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE));
    }


    @Test
    @Transactional
    public void getMouvementStocksByIdFiltering() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        Long id = mouvementStock.getId();

        defaultMouvementStockShouldBeFound("id.equals=" + id);
        defaultMouvementStockShouldNotBeFound("id.notEquals=" + id);

        defaultMouvementStockShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMouvementStockShouldNotBeFound("id.greaterThan=" + id);

        defaultMouvementStockShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMouvementStockShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMouvementStocksByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where type equals to DEFAULT_TYPE
        defaultMouvementStockShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the mouvementStockList where type equals to UPDATED_TYPE
        defaultMouvementStockShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where type not equals to DEFAULT_TYPE
        defaultMouvementStockShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the mouvementStockList where type not equals to UPDATED_TYPE
        defaultMouvementStockShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultMouvementStockShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the mouvementStockList where type equals to UPDATED_TYPE
        defaultMouvementStockShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where type is not null
        defaultMouvementStockShouldBeFound("type.specified=true");

        // Get all the mouvementStockList where type is null
        defaultMouvementStockShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where date equals to DEFAULT_DATE
        defaultMouvementStockShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the mouvementStockList where date equals to UPDATED_DATE
        defaultMouvementStockShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where date not equals to DEFAULT_DATE
        defaultMouvementStockShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the mouvementStockList where date not equals to UPDATED_DATE
        defaultMouvementStockShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByDateIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where date in DEFAULT_DATE or UPDATED_DATE
        defaultMouvementStockShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the mouvementStockList where date equals to UPDATED_DATE
        defaultMouvementStockShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where date is not null
        defaultMouvementStockShouldBeFound("date.specified=true");

        // Get all the mouvementStockList where date is null
        defaultMouvementStockShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where date is greater than or equal to DEFAULT_DATE
        defaultMouvementStockShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the mouvementStockList where date is greater than or equal to UPDATED_DATE
        defaultMouvementStockShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where date is less than or equal to DEFAULT_DATE
        defaultMouvementStockShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the mouvementStockList where date is less than or equal to SMALLER_DATE
        defaultMouvementStockShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where date is less than DEFAULT_DATE
        defaultMouvementStockShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the mouvementStockList where date is less than UPDATED_DATE
        defaultMouvementStockShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where date is greater than DEFAULT_DATE
        defaultMouvementStockShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the mouvementStockList where date is greater than SMALLER_DATE
        defaultMouvementStockShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllMouvementStocksBySensIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where sens equals to DEFAULT_SENS
        defaultMouvementStockShouldBeFound("sens.equals=" + DEFAULT_SENS);

        // Get all the mouvementStockList where sens equals to UPDATED_SENS
        defaultMouvementStockShouldNotBeFound("sens.equals=" + UPDATED_SENS);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksBySensIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where sens not equals to DEFAULT_SENS
        defaultMouvementStockShouldNotBeFound("sens.notEquals=" + DEFAULT_SENS);

        // Get all the mouvementStockList where sens not equals to UPDATED_SENS
        defaultMouvementStockShouldBeFound("sens.notEquals=" + UPDATED_SENS);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksBySensIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where sens in DEFAULT_SENS or UPDATED_SENS
        defaultMouvementStockShouldBeFound("sens.in=" + DEFAULT_SENS + "," + UPDATED_SENS);

        // Get all the mouvementStockList where sens equals to UPDATED_SENS
        defaultMouvementStockShouldNotBeFound("sens.in=" + UPDATED_SENS);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksBySensIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where sens is not null
        defaultMouvementStockShouldBeFound("sens.specified=true");

        // Get all the mouvementStockList where sens is null
        defaultMouvementStockShouldNotBeFound("sens.specified=false");
    }

    @Test
    @Transactional
    public void getAllMouvementStocksBySensIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where sens is greater than or equal to DEFAULT_SENS
        defaultMouvementStockShouldBeFound("sens.greaterThanOrEqual=" + DEFAULT_SENS);

        // Get all the mouvementStockList where sens is greater than or equal to UPDATED_SENS
        defaultMouvementStockShouldNotBeFound("sens.greaterThanOrEqual=" + UPDATED_SENS);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksBySensIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where sens is less than or equal to DEFAULT_SENS
        defaultMouvementStockShouldBeFound("sens.lessThanOrEqual=" + DEFAULT_SENS);

        // Get all the mouvementStockList where sens is less than or equal to SMALLER_SENS
        defaultMouvementStockShouldNotBeFound("sens.lessThanOrEqual=" + SMALLER_SENS);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksBySensIsLessThanSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where sens is less than DEFAULT_SENS
        defaultMouvementStockShouldNotBeFound("sens.lessThan=" + DEFAULT_SENS);

        // Get all the mouvementStockList where sens is less than UPDATED_SENS
        defaultMouvementStockShouldBeFound("sens.lessThan=" + UPDATED_SENS);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksBySensIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where sens is greater than DEFAULT_SENS
        defaultMouvementStockShouldNotBeFound("sens.greaterThan=" + DEFAULT_SENS);

        // Get all the mouvementStockList where sens is greater than SMALLER_SENS
        defaultMouvementStockShouldBeFound("sens.greaterThan=" + SMALLER_SENS);
    }


    @Test
    @Transactional
    public void getAllMouvementStocksByQuantiteIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite equals to DEFAULT_QUANTITE
        defaultMouvementStockShouldBeFound("quantite.equals=" + DEFAULT_QUANTITE);

        // Get all the mouvementStockList where quantite equals to UPDATED_QUANTITE
        defaultMouvementStockShouldNotBeFound("quantite.equals=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByQuantiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite not equals to DEFAULT_QUANTITE
        defaultMouvementStockShouldNotBeFound("quantite.notEquals=" + DEFAULT_QUANTITE);

        // Get all the mouvementStockList where quantite not equals to UPDATED_QUANTITE
        defaultMouvementStockShouldBeFound("quantite.notEquals=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByQuantiteIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite in DEFAULT_QUANTITE or UPDATED_QUANTITE
        defaultMouvementStockShouldBeFound("quantite.in=" + DEFAULT_QUANTITE + "," + UPDATED_QUANTITE);

        // Get all the mouvementStockList where quantite equals to UPDATED_QUANTITE
        defaultMouvementStockShouldNotBeFound("quantite.in=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByQuantiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite is not null
        defaultMouvementStockShouldBeFound("quantite.specified=true");

        // Get all the mouvementStockList where quantite is null
        defaultMouvementStockShouldNotBeFound("quantite.specified=false");
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByQuantiteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite is greater than or equal to DEFAULT_QUANTITE
        defaultMouvementStockShouldBeFound("quantite.greaterThanOrEqual=" + DEFAULT_QUANTITE);

        // Get all the mouvementStockList where quantite is greater than or equal to UPDATED_QUANTITE
        defaultMouvementStockShouldNotBeFound("quantite.greaterThanOrEqual=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByQuantiteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite is less than or equal to DEFAULT_QUANTITE
        defaultMouvementStockShouldBeFound("quantite.lessThanOrEqual=" + DEFAULT_QUANTITE);

        // Get all the mouvementStockList where quantite is less than or equal to SMALLER_QUANTITE
        defaultMouvementStockShouldNotBeFound("quantite.lessThanOrEqual=" + SMALLER_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByQuantiteIsLessThanSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite is less than DEFAULT_QUANTITE
        defaultMouvementStockShouldNotBeFound("quantite.lessThan=" + DEFAULT_QUANTITE);

        // Get all the mouvementStockList where quantite is less than UPDATED_QUANTITE
        defaultMouvementStockShouldBeFound("quantite.lessThan=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByQuantiteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite is greater than DEFAULT_QUANTITE
        defaultMouvementStockShouldNotBeFound("quantite.greaterThan=" + DEFAULT_QUANTITE);

        // Get all the mouvementStockList where quantite is greater than SMALLER_QUANTITE
        defaultMouvementStockShouldBeFound("quantite.greaterThan=" + SMALLER_QUANTITE);
    }


    @Test
    @Transactional
    public void getAllMouvementStocksByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creeLe equals to DEFAULT_CREE_LE
        defaultMouvementStockShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the mouvementStockList where creeLe equals to UPDATED_CREE_LE
        defaultMouvementStockShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creeLe not equals to DEFAULT_CREE_LE
        defaultMouvementStockShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the mouvementStockList where creeLe not equals to UPDATED_CREE_LE
        defaultMouvementStockShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultMouvementStockShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the mouvementStockList where creeLe equals to UPDATED_CREE_LE
        defaultMouvementStockShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creeLe is not null
        defaultMouvementStockShouldBeFound("creeLe.specified=true");

        // Get all the mouvementStockList where creeLe is null
        defaultMouvementStockShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultMouvementStockShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the mouvementStockList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultMouvementStockShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultMouvementStockShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the mouvementStockList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultMouvementStockShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creeLe is less than DEFAULT_CREE_LE
        defaultMouvementStockShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the mouvementStockList where creeLe is less than UPDATED_CREE_LE
        defaultMouvementStockShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creeLe is greater than DEFAULT_CREE_LE
        defaultMouvementStockShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the mouvementStockList where creeLe is greater than SMALLER_CREE_LE
        defaultMouvementStockShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllMouvementStocksByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creePar equals to DEFAULT_CREE_PAR
        defaultMouvementStockShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the mouvementStockList where creePar equals to UPDATED_CREE_PAR
        defaultMouvementStockShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creePar not equals to DEFAULT_CREE_PAR
        defaultMouvementStockShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the mouvementStockList where creePar not equals to UPDATED_CREE_PAR
        defaultMouvementStockShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultMouvementStockShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the mouvementStockList where creePar equals to UPDATED_CREE_PAR
        defaultMouvementStockShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creePar is not null
        defaultMouvementStockShouldBeFound("creePar.specified=true");

        // Get all the mouvementStockList where creePar is null
        defaultMouvementStockShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllMouvementStocksByCreeParContainsSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creePar contains DEFAULT_CREE_PAR
        defaultMouvementStockShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the mouvementStockList where creePar contains UPDATED_CREE_PAR
        defaultMouvementStockShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where creePar does not contain DEFAULT_CREE_PAR
        defaultMouvementStockShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the mouvementStockList where creePar does not contain UPDATED_CREE_PAR
        defaultMouvementStockShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllMouvementStocksByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultMouvementStockShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the mouvementStockList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultMouvementStockShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultMouvementStockShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the mouvementStockList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultMouvementStockShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultMouvementStockShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the mouvementStockList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultMouvementStockShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifieLe is not null
        defaultMouvementStockShouldBeFound("modifieLe.specified=true");

        // Get all the mouvementStockList where modifieLe is null
        defaultMouvementStockShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultMouvementStockShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the mouvementStockList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultMouvementStockShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultMouvementStockShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the mouvementStockList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultMouvementStockShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultMouvementStockShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the mouvementStockList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultMouvementStockShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultMouvementStockShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the mouvementStockList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultMouvementStockShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllMouvementStocksByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultMouvementStockShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the mouvementStockList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultMouvementStockShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultMouvementStockShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the mouvementStockList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultMouvementStockShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultMouvementStockShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the mouvementStockList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultMouvementStockShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifiePar is not null
        defaultMouvementStockShouldBeFound("modifiePar.specified=true");

        // Get all the mouvementStockList where modifiePar is null
        defaultMouvementStockShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllMouvementStocksByModifieParContainsSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultMouvementStockShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the mouvementStockList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultMouvementStockShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultMouvementStockShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the mouvementStockList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultMouvementStockShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllMouvementStocksByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where reference equals to DEFAULT_REFERENCE
        defaultMouvementStockShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the mouvementStockList where reference equals to UPDATED_REFERENCE
        defaultMouvementStockShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByReferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where reference not equals to DEFAULT_REFERENCE
        defaultMouvementStockShouldNotBeFound("reference.notEquals=" + DEFAULT_REFERENCE);

        // Get all the mouvementStockList where reference not equals to UPDATED_REFERENCE
        defaultMouvementStockShouldBeFound("reference.notEquals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultMouvementStockShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the mouvementStockList where reference equals to UPDATED_REFERENCE
        defaultMouvementStockShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where reference is not null
        defaultMouvementStockShouldBeFound("reference.specified=true");

        // Get all the mouvementStockList where reference is null
        defaultMouvementStockShouldNotBeFound("reference.specified=false");
    }
                @Test
    @Transactional
    public void getAllMouvementStocksByReferenceContainsSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where reference contains DEFAULT_REFERENCE
        defaultMouvementStockShouldBeFound("reference.contains=" + DEFAULT_REFERENCE);

        // Get all the mouvementStockList where reference contains UPDATED_REFERENCE
        defaultMouvementStockShouldNotBeFound("reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllMouvementStocksByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where reference does not contain DEFAULT_REFERENCE
        defaultMouvementStockShouldNotBeFound("reference.doesNotContain=" + DEFAULT_REFERENCE);

        // Get all the mouvementStockList where reference does not contain UPDATED_REFERENCE
        defaultMouvementStockShouldBeFound("reference.doesNotContain=" + UPDATED_REFERENCE);
    }


    @Test
    @Transactional
    public void getAllMouvementStocksByRefProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);
        Produit refProduit = ProduitResourceIT.createEntity(em);
        em.persist(refProduit);
        em.flush();
        mouvementStock.setRefProduit(refProduit);
        mouvementStockRepository.saveAndFlush(mouvementStock);
        Long refProduitId = refProduit.getId();

        // Get all the mouvementStockList where refProduit equals to refProduitId
        defaultMouvementStockShouldBeFound("refProduitId.equals=" + refProduitId);

        // Get all the mouvementStockList where refProduit equals to refProduitId + 1
        defaultMouvementStockShouldNotBeFound("refProduitId.equals=" + (refProduitId + 1));
    }


    @Test
    @Transactional
    public void getAllMouvementStocksByRefCommandeIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);
        Commande refCommande = CommandeResourceIT.createEntity(em);
        em.persist(refCommande);
        em.flush();
        mouvementStock.setRefCommande(refCommande);
        mouvementStockRepository.saveAndFlush(mouvementStock);
        Long refCommandeId = refCommande.getId();

        // Get all the mouvementStockList where refCommande equals to refCommandeId
        defaultMouvementStockShouldBeFound("refCommandeId.equals=" + refCommandeId);

        // Get all the mouvementStockList where refCommande equals to refCommandeId + 1
        defaultMouvementStockShouldNotBeFound("refCommandeId.equals=" + (refCommandeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMouvementStockShouldBeFound(String filter) throws Exception {
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouvementStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].sens").value(hasItem(DEFAULT_SENS)))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)));

        // Check, that the count call also returns 1
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMouvementStockShouldNotBeFound(String filter) throws Exception {
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMouvementStock() throws Exception {
        // Get the mouvementStock
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        int databaseSizeBeforeUpdate = mouvementStockRepository.findAll().size();

        // Update the mouvementStock
        MouvementStock updatedMouvementStock = mouvementStockRepository.findById(mouvementStock.getId()).get();
        // Disconnect from session so that the updates on updatedMouvementStock are not directly saved in db
        em.detach(updatedMouvementStock);
        updatedMouvementStock
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .sens(UPDATED_SENS)
            .quantite(UPDATED_QUANTITE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR)
            .reference(UPDATED_REFERENCE);
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(updatedMouvementStock);

        restMouvementStockMockMvc.perform(put("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isOk());

        // Validate the MouvementStock in the database
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeUpdate);
        MouvementStock testMouvementStock = mouvementStockList.get(mouvementStockList.size() - 1);
        assertThat(testMouvementStock.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMouvementStock.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMouvementStock.getSens()).isEqualTo(UPDATED_SENS);
        assertThat(testMouvementStock.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testMouvementStock.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testMouvementStock.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testMouvementStock.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testMouvementStock.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);
        assertThat(testMouvementStock.getReference()).isEqualTo(UPDATED_REFERENCE);

        // Validate the MouvementStock in Elasticsearch
        verify(mockMouvementStockSearchRepository, times(1)).save(testMouvementStock);
    }

    @Test
    @Transactional
    public void updateNonExistingMouvementStock() throws Exception {
        int databaseSizeBeforeUpdate = mouvementStockRepository.findAll().size();

        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMouvementStockMockMvc.perform(put("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MouvementStock in the database
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MouvementStock in Elasticsearch
        verify(mockMouvementStockSearchRepository, times(0)).save(mouvementStock);
    }

    @Test
    @Transactional
    public void deleteMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        int databaseSizeBeforeDelete = mouvementStockRepository.findAll().size();

        // Delete the mouvementStock
        restMouvementStockMockMvc.perform(delete("/api/mouvement-stocks/{id}", mouvementStock.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MouvementStock in Elasticsearch
        verify(mockMouvementStockSearchRepository, times(1)).deleteById(mouvementStock.getId());
    }

    @Test
    @Transactional
    public void searchMouvementStock() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);
        when(mockMouvementStockSearchRepository.search(queryStringQuery("id:" + mouvementStock.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(mouvementStock), PageRequest.of(0, 1), 1));

        // Search the mouvementStock
        restMouvementStockMockMvc.perform(get("/api/_search/mouvement-stocks?query=id:" + mouvementStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouvementStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].sens").value(hasItem(DEFAULT_SENS)))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)));
    }
}
