package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.Stock;
import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.domain.Categorie;
import org.fininfo.elbazar.domain.SousCategorie;
import org.fininfo.elbazar.repository.StockRepository;
import org.fininfo.elbazar.repository.search.StockSearchRepository;
import org.fininfo.elbazar.service.StockService;
import org.fininfo.elbazar.service.dto.StockDTO;
import org.fininfo.elbazar.service.mapper.StockMapper;
import org.fininfo.elbazar.service.dto.StockCriteria;
import org.fininfo.elbazar.service.StockQueryService;

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
 * Integration tests for the {@link StockResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class StockResourceIT {

    private static final Double DEFAULT_STOCK_RESERVE = 1D;
    private static final Double UPDATED_STOCK_RESERVE = 2D;
    private static final Double SMALLER_STOCK_RESERVE = 1D - 1D;

    private static final Double DEFAULT_STOCK_COMMANDE = 1D;
    private static final Double UPDATED_STOCK_COMMANDE = 2D;
    private static final Double SMALLER_STOCK_COMMANDE = 1D - 1D;

    private static final Double DEFAULT_STOCK_PHYSIQUE = 1D;
    private static final Double UPDATED_STOCK_PHYSIQUE = 2D;
    private static final Double SMALLER_STOCK_PHYSIQUE = 1D - 1D;

    private static final Double DEFAULT_STOCK_DISPONIBLE = 1D;
    private static final Double UPDATED_STOCK_DISPONIBLE = 2D;
    private static final Double SMALLER_STOCK_DISPONIBLE = 1D - 1D;

    private static final Double DEFAULT_STOCK_MINIMUM = 1D;
    private static final Double UPDATED_STOCK_MINIMUM = 2D;
    private static final Double SMALLER_STOCK_MINIMUM = 1D - 1D;

    private static final LocalDate DEFAULT_DERNIERE_ENTRE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DERNIERE_ENTRE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DERNIERE_ENTRE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DERNIERE_SORTIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DERNIERE_SORTIE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DERNIERE_SORTIE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_ALERTE_STOCK = false;
    private static final Boolean UPDATED_ALERTE_STOCK = true;

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
    private StockRepository stockRepository;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockService stockService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.StockSearchRepositoryMockConfiguration
     */
    @Autowired
    private StockSearchRepository mockStockSearchRepository;

    @Autowired
    private StockQueryService stockQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockMockMvc;

    private Stock stock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stock createEntity(EntityManager em) {
        Stock stock = new Stock()
            .stockReserve(DEFAULT_STOCK_RESERVE)
            .stockCommande(DEFAULT_STOCK_COMMANDE)
            .stockPhysique(DEFAULT_STOCK_PHYSIQUE)
            .stockDisponible(DEFAULT_STOCK_DISPONIBLE)
            .stockMinimum(DEFAULT_STOCK_MINIMUM)
            .derniereEntre(DEFAULT_DERNIERE_ENTRE)
            .derniereSortie(DEFAULT_DERNIERE_SORTIE)
            .alerteStock(DEFAULT_ALERTE_STOCK)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return stock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stock createUpdatedEntity(EntityManager em) {
        Stock stock = new Stock()
            .stockReserve(UPDATED_STOCK_RESERVE)
            .stockCommande(UPDATED_STOCK_COMMANDE)
            .stockPhysique(UPDATED_STOCK_PHYSIQUE)
            .stockDisponible(UPDATED_STOCK_DISPONIBLE)
            .stockMinimum(UPDATED_STOCK_MINIMUM)
            .derniereEntre(UPDATED_DERNIERE_ENTRE)
            .derniereSortie(UPDATED_DERNIERE_SORTIE)
            .alerteStock(UPDATED_ALERTE_STOCK)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return stock;
    }

    @BeforeEach
    public void initTest() {
        stock = createEntity(em);
    }

    @Test
    @Transactional
    public void createStock() throws Exception {
        int databaseSizeBeforeCreate = stockRepository.findAll().size();
        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);
        restStockMockMvc.perform(post("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isCreated());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeCreate + 1);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getStockReserve()).isEqualTo(DEFAULT_STOCK_RESERVE);
        assertThat(testStock.getStockCommande()).isEqualTo(DEFAULT_STOCK_COMMANDE);
        assertThat(testStock.getStockPhysique()).isEqualTo(DEFAULT_STOCK_PHYSIQUE);
        assertThat(testStock.getStockDisponible()).isEqualTo(DEFAULT_STOCK_DISPONIBLE);
        assertThat(testStock.getStockMinimum()).isEqualTo(DEFAULT_STOCK_MINIMUM);
        assertThat(testStock.getDerniereEntre()).isEqualTo(DEFAULT_DERNIERE_ENTRE);
        assertThat(testStock.getDerniereSortie()).isEqualTo(DEFAULT_DERNIERE_SORTIE);
        assertThat(testStock.isAlerteStock()).isEqualTo(DEFAULT_ALERTE_STOCK);
        assertThat(testStock.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testStock.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testStock.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testStock.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the Stock in Elasticsearch
        verify(mockStockSearchRepository, times(1)).save(testStock);
    }

    @Test
    @Transactional
    public void createStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockRepository.findAll().size();

        // Create the Stock with an existing ID
        stock.setId(1L);
        StockDTO stockDTO = stockMapper.toDto(stock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockMockMvc.perform(post("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeCreate);

        // Validate the Stock in Elasticsearch
        verify(mockStockSearchRepository, times(0)).save(stock);
    }


    @Test
    @Transactional
    public void getAllStocks() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList
        restStockMockMvc.perform(get("/api/stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stock.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockReserve").value(hasItem(DEFAULT_STOCK_RESERVE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockCommande").value(hasItem(DEFAULT_STOCK_COMMANDE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockPhysique").value(hasItem(DEFAULT_STOCK_PHYSIQUE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockDisponible").value(hasItem(DEFAULT_STOCK_DISPONIBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockMinimum").value(hasItem(DEFAULT_STOCK_MINIMUM.doubleValue())))
            .andExpect(jsonPath("$.[*].derniereEntre").value(hasItem(DEFAULT_DERNIERE_ENTRE.toString())))
            .andExpect(jsonPath("$.[*].derniereSortie").value(hasItem(DEFAULT_DERNIERE_SORTIE.toString())))
            .andExpect(jsonPath("$.[*].alerteStock").value(hasItem(DEFAULT_ALERTE_STOCK.booleanValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get the stock
        restStockMockMvc.perform(get("/api/stocks/{id}", stock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stock.getId().intValue()))
            .andExpect(jsonPath("$.stockReserve").value(DEFAULT_STOCK_RESERVE.doubleValue()))
            .andExpect(jsonPath("$.stockCommande").value(DEFAULT_STOCK_COMMANDE.doubleValue()))
            .andExpect(jsonPath("$.stockPhysique").value(DEFAULT_STOCK_PHYSIQUE.doubleValue()))
            .andExpect(jsonPath("$.stockDisponible").value(DEFAULT_STOCK_DISPONIBLE.doubleValue()))
            .andExpect(jsonPath("$.stockMinimum").value(DEFAULT_STOCK_MINIMUM.doubleValue()))
            .andExpect(jsonPath("$.derniereEntre").value(DEFAULT_DERNIERE_ENTRE.toString()))
            .andExpect(jsonPath("$.derniereSortie").value(DEFAULT_DERNIERE_SORTIE.toString()))
            .andExpect(jsonPath("$.alerteStock").value(DEFAULT_ALERTE_STOCK.booleanValue()))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }


    @Test
    @Transactional
    public void getStocksByIdFiltering() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        Long id = stock.getId();

        defaultStockShouldBeFound("id.equals=" + id);
        defaultStockShouldNotBeFound("id.notEquals=" + id);

        defaultStockShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockShouldNotBeFound("id.greaterThan=" + id);

        defaultStockShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStocksByStockReserveIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockReserve equals to DEFAULT_STOCK_RESERVE
        defaultStockShouldBeFound("stockReserve.equals=" + DEFAULT_STOCK_RESERVE);

        // Get all the stockList where stockReserve equals to UPDATED_STOCK_RESERVE
        defaultStockShouldNotBeFound("stockReserve.equals=" + UPDATED_STOCK_RESERVE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockReserveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockReserve not equals to DEFAULT_STOCK_RESERVE
        defaultStockShouldNotBeFound("stockReserve.notEquals=" + DEFAULT_STOCK_RESERVE);

        // Get all the stockList where stockReserve not equals to UPDATED_STOCK_RESERVE
        defaultStockShouldBeFound("stockReserve.notEquals=" + UPDATED_STOCK_RESERVE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockReserveIsInShouldWork() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockReserve in DEFAULT_STOCK_RESERVE or UPDATED_STOCK_RESERVE
        defaultStockShouldBeFound("stockReserve.in=" + DEFAULT_STOCK_RESERVE + "," + UPDATED_STOCK_RESERVE);

        // Get all the stockList where stockReserve equals to UPDATED_STOCK_RESERVE
        defaultStockShouldNotBeFound("stockReserve.in=" + UPDATED_STOCK_RESERVE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockReserveIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockReserve is not null
        defaultStockShouldBeFound("stockReserve.specified=true");

        // Get all the stockList where stockReserve is null
        defaultStockShouldNotBeFound("stockReserve.specified=false");
    }

    @Test
    @Transactional
    public void getAllStocksByStockReserveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockReserve is greater than or equal to DEFAULT_STOCK_RESERVE
        defaultStockShouldBeFound("stockReserve.greaterThanOrEqual=" + DEFAULT_STOCK_RESERVE);

        // Get all the stockList where stockReserve is greater than or equal to UPDATED_STOCK_RESERVE
        defaultStockShouldNotBeFound("stockReserve.greaterThanOrEqual=" + UPDATED_STOCK_RESERVE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockReserveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockReserve is less than or equal to DEFAULT_STOCK_RESERVE
        defaultStockShouldBeFound("stockReserve.lessThanOrEqual=" + DEFAULT_STOCK_RESERVE);

        // Get all the stockList where stockReserve is less than or equal to SMALLER_STOCK_RESERVE
        defaultStockShouldNotBeFound("stockReserve.lessThanOrEqual=" + SMALLER_STOCK_RESERVE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockReserveIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockReserve is less than DEFAULT_STOCK_RESERVE
        defaultStockShouldNotBeFound("stockReserve.lessThan=" + DEFAULT_STOCK_RESERVE);

        // Get all the stockList where stockReserve is less than UPDATED_STOCK_RESERVE
        defaultStockShouldBeFound("stockReserve.lessThan=" + UPDATED_STOCK_RESERVE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockReserveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockReserve is greater than DEFAULT_STOCK_RESERVE
        defaultStockShouldNotBeFound("stockReserve.greaterThan=" + DEFAULT_STOCK_RESERVE);

        // Get all the stockList where stockReserve is greater than SMALLER_STOCK_RESERVE
        defaultStockShouldBeFound("stockReserve.greaterThan=" + SMALLER_STOCK_RESERVE);
    }


    @Test
    @Transactional
    public void getAllStocksByStockCommandeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockCommande equals to DEFAULT_STOCK_COMMANDE
        defaultStockShouldBeFound("stockCommande.equals=" + DEFAULT_STOCK_COMMANDE);

        // Get all the stockList where stockCommande equals to UPDATED_STOCK_COMMANDE
        defaultStockShouldNotBeFound("stockCommande.equals=" + UPDATED_STOCK_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockCommandeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockCommande not equals to DEFAULT_STOCK_COMMANDE
        defaultStockShouldNotBeFound("stockCommande.notEquals=" + DEFAULT_STOCK_COMMANDE);

        // Get all the stockList where stockCommande not equals to UPDATED_STOCK_COMMANDE
        defaultStockShouldBeFound("stockCommande.notEquals=" + UPDATED_STOCK_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockCommandeIsInShouldWork() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockCommande in DEFAULT_STOCK_COMMANDE or UPDATED_STOCK_COMMANDE
        defaultStockShouldBeFound("stockCommande.in=" + DEFAULT_STOCK_COMMANDE + "," + UPDATED_STOCK_COMMANDE);

        // Get all the stockList where stockCommande equals to UPDATED_STOCK_COMMANDE
        defaultStockShouldNotBeFound("stockCommande.in=" + UPDATED_STOCK_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockCommandeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockCommande is not null
        defaultStockShouldBeFound("stockCommande.specified=true");

        // Get all the stockList where stockCommande is null
        defaultStockShouldNotBeFound("stockCommande.specified=false");
    }

    @Test
    @Transactional
    public void getAllStocksByStockCommandeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockCommande is greater than or equal to DEFAULT_STOCK_COMMANDE
        defaultStockShouldBeFound("stockCommande.greaterThanOrEqual=" + DEFAULT_STOCK_COMMANDE);

        // Get all the stockList where stockCommande is greater than or equal to UPDATED_STOCK_COMMANDE
        defaultStockShouldNotBeFound("stockCommande.greaterThanOrEqual=" + UPDATED_STOCK_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockCommandeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockCommande is less than or equal to DEFAULT_STOCK_COMMANDE
        defaultStockShouldBeFound("stockCommande.lessThanOrEqual=" + DEFAULT_STOCK_COMMANDE);

        // Get all the stockList where stockCommande is less than or equal to SMALLER_STOCK_COMMANDE
        defaultStockShouldNotBeFound("stockCommande.lessThanOrEqual=" + SMALLER_STOCK_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockCommandeIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockCommande is less than DEFAULT_STOCK_COMMANDE
        defaultStockShouldNotBeFound("stockCommande.lessThan=" + DEFAULT_STOCK_COMMANDE);

        // Get all the stockList where stockCommande is less than UPDATED_STOCK_COMMANDE
        defaultStockShouldBeFound("stockCommande.lessThan=" + UPDATED_STOCK_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockCommandeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockCommande is greater than DEFAULT_STOCK_COMMANDE
        defaultStockShouldNotBeFound("stockCommande.greaterThan=" + DEFAULT_STOCK_COMMANDE);

        // Get all the stockList where stockCommande is greater than SMALLER_STOCK_COMMANDE
        defaultStockShouldBeFound("stockCommande.greaterThan=" + SMALLER_STOCK_COMMANDE);
    }


    @Test
    @Transactional
    public void getAllStocksByStockPhysiqueIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockPhysique equals to DEFAULT_STOCK_PHYSIQUE
        defaultStockShouldBeFound("stockPhysique.equals=" + DEFAULT_STOCK_PHYSIQUE);

        // Get all the stockList where stockPhysique equals to UPDATED_STOCK_PHYSIQUE
        defaultStockShouldNotBeFound("stockPhysique.equals=" + UPDATED_STOCK_PHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockPhysiqueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockPhysique not equals to DEFAULT_STOCK_PHYSIQUE
        defaultStockShouldNotBeFound("stockPhysique.notEquals=" + DEFAULT_STOCK_PHYSIQUE);

        // Get all the stockList where stockPhysique not equals to UPDATED_STOCK_PHYSIQUE
        defaultStockShouldBeFound("stockPhysique.notEquals=" + UPDATED_STOCK_PHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockPhysiqueIsInShouldWork() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockPhysique in DEFAULT_STOCK_PHYSIQUE or UPDATED_STOCK_PHYSIQUE
        defaultStockShouldBeFound("stockPhysique.in=" + DEFAULT_STOCK_PHYSIQUE + "," + UPDATED_STOCK_PHYSIQUE);

        // Get all the stockList where stockPhysique equals to UPDATED_STOCK_PHYSIQUE
        defaultStockShouldNotBeFound("stockPhysique.in=" + UPDATED_STOCK_PHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockPhysiqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockPhysique is not null
        defaultStockShouldBeFound("stockPhysique.specified=true");

        // Get all the stockList where stockPhysique is null
        defaultStockShouldNotBeFound("stockPhysique.specified=false");
    }

    @Test
    @Transactional
    public void getAllStocksByStockPhysiqueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockPhysique is greater than or equal to DEFAULT_STOCK_PHYSIQUE
        defaultStockShouldBeFound("stockPhysique.greaterThanOrEqual=" + DEFAULT_STOCK_PHYSIQUE);

        // Get all the stockList where stockPhysique is greater than or equal to UPDATED_STOCK_PHYSIQUE
        defaultStockShouldNotBeFound("stockPhysique.greaterThanOrEqual=" + UPDATED_STOCK_PHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockPhysiqueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockPhysique is less than or equal to DEFAULT_STOCK_PHYSIQUE
        defaultStockShouldBeFound("stockPhysique.lessThanOrEqual=" + DEFAULT_STOCK_PHYSIQUE);

        // Get all the stockList where stockPhysique is less than or equal to SMALLER_STOCK_PHYSIQUE
        defaultStockShouldNotBeFound("stockPhysique.lessThanOrEqual=" + SMALLER_STOCK_PHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockPhysiqueIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockPhysique is less than DEFAULT_STOCK_PHYSIQUE
        defaultStockShouldNotBeFound("stockPhysique.lessThan=" + DEFAULT_STOCK_PHYSIQUE);

        // Get all the stockList where stockPhysique is less than UPDATED_STOCK_PHYSIQUE
        defaultStockShouldBeFound("stockPhysique.lessThan=" + UPDATED_STOCK_PHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockPhysiqueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockPhysique is greater than DEFAULT_STOCK_PHYSIQUE
        defaultStockShouldNotBeFound("stockPhysique.greaterThan=" + DEFAULT_STOCK_PHYSIQUE);

        // Get all the stockList where stockPhysique is greater than SMALLER_STOCK_PHYSIQUE
        defaultStockShouldBeFound("stockPhysique.greaterThan=" + SMALLER_STOCK_PHYSIQUE);
    }


    @Test
    @Transactional
    public void getAllStocksByStockDisponibleIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockDisponible equals to DEFAULT_STOCK_DISPONIBLE
        defaultStockShouldBeFound("stockDisponible.equals=" + DEFAULT_STOCK_DISPONIBLE);

        // Get all the stockList where stockDisponible equals to UPDATED_STOCK_DISPONIBLE
        defaultStockShouldNotBeFound("stockDisponible.equals=" + UPDATED_STOCK_DISPONIBLE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockDisponibleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockDisponible not equals to DEFAULT_STOCK_DISPONIBLE
        defaultStockShouldNotBeFound("stockDisponible.notEquals=" + DEFAULT_STOCK_DISPONIBLE);

        // Get all the stockList where stockDisponible not equals to UPDATED_STOCK_DISPONIBLE
        defaultStockShouldBeFound("stockDisponible.notEquals=" + UPDATED_STOCK_DISPONIBLE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockDisponibleIsInShouldWork() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockDisponible in DEFAULT_STOCK_DISPONIBLE or UPDATED_STOCK_DISPONIBLE
        defaultStockShouldBeFound("stockDisponible.in=" + DEFAULT_STOCK_DISPONIBLE + "," + UPDATED_STOCK_DISPONIBLE);

        // Get all the stockList where stockDisponible equals to UPDATED_STOCK_DISPONIBLE
        defaultStockShouldNotBeFound("stockDisponible.in=" + UPDATED_STOCK_DISPONIBLE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockDisponibleIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockDisponible is not null
        defaultStockShouldBeFound("stockDisponible.specified=true");

        // Get all the stockList where stockDisponible is null
        defaultStockShouldNotBeFound("stockDisponible.specified=false");
    }

    @Test
    @Transactional
    public void getAllStocksByStockDisponibleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockDisponible is greater than or equal to DEFAULT_STOCK_DISPONIBLE
        defaultStockShouldBeFound("stockDisponible.greaterThanOrEqual=" + DEFAULT_STOCK_DISPONIBLE);

        // Get all the stockList where stockDisponible is greater than or equal to UPDATED_STOCK_DISPONIBLE
        defaultStockShouldNotBeFound("stockDisponible.greaterThanOrEqual=" + UPDATED_STOCK_DISPONIBLE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockDisponibleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockDisponible is less than or equal to DEFAULT_STOCK_DISPONIBLE
        defaultStockShouldBeFound("stockDisponible.lessThanOrEqual=" + DEFAULT_STOCK_DISPONIBLE);

        // Get all the stockList where stockDisponible is less than or equal to SMALLER_STOCK_DISPONIBLE
        defaultStockShouldNotBeFound("stockDisponible.lessThanOrEqual=" + SMALLER_STOCK_DISPONIBLE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockDisponibleIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockDisponible is less than DEFAULT_STOCK_DISPONIBLE
        defaultStockShouldNotBeFound("stockDisponible.lessThan=" + DEFAULT_STOCK_DISPONIBLE);

        // Get all the stockList where stockDisponible is less than UPDATED_STOCK_DISPONIBLE
        defaultStockShouldBeFound("stockDisponible.lessThan=" + UPDATED_STOCK_DISPONIBLE);
    }

    @Test
    @Transactional
    public void getAllStocksByStockDisponibleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockDisponible is greater than DEFAULT_STOCK_DISPONIBLE
        defaultStockShouldNotBeFound("stockDisponible.greaterThan=" + DEFAULT_STOCK_DISPONIBLE);

        // Get all the stockList where stockDisponible is greater than SMALLER_STOCK_DISPONIBLE
        defaultStockShouldBeFound("stockDisponible.greaterThan=" + SMALLER_STOCK_DISPONIBLE);
    }


    @Test
    @Transactional
    public void getAllStocksByStockMinimumIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockMinimum equals to DEFAULT_STOCK_MINIMUM
        defaultStockShouldBeFound("stockMinimum.equals=" + DEFAULT_STOCK_MINIMUM);

        // Get all the stockList where stockMinimum equals to UPDATED_STOCK_MINIMUM
        defaultStockShouldNotBeFound("stockMinimum.equals=" + UPDATED_STOCK_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllStocksByStockMinimumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockMinimum not equals to DEFAULT_STOCK_MINIMUM
        defaultStockShouldNotBeFound("stockMinimum.notEquals=" + DEFAULT_STOCK_MINIMUM);

        // Get all the stockList where stockMinimum not equals to UPDATED_STOCK_MINIMUM
        defaultStockShouldBeFound("stockMinimum.notEquals=" + UPDATED_STOCK_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllStocksByStockMinimumIsInShouldWork() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockMinimum in DEFAULT_STOCK_MINIMUM or UPDATED_STOCK_MINIMUM
        defaultStockShouldBeFound("stockMinimum.in=" + DEFAULT_STOCK_MINIMUM + "," + UPDATED_STOCK_MINIMUM);

        // Get all the stockList where stockMinimum equals to UPDATED_STOCK_MINIMUM
        defaultStockShouldNotBeFound("stockMinimum.in=" + UPDATED_STOCK_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllStocksByStockMinimumIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockMinimum is not null
        defaultStockShouldBeFound("stockMinimum.specified=true");

        // Get all the stockList where stockMinimum is null
        defaultStockShouldNotBeFound("stockMinimum.specified=false");
    }

    @Test
    @Transactional
    public void getAllStocksByStockMinimumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockMinimum is greater than or equal to DEFAULT_STOCK_MINIMUM
        defaultStockShouldBeFound("stockMinimum.greaterThanOrEqual=" + DEFAULT_STOCK_MINIMUM);

        // Get all the stockList where stockMinimum is greater than or equal to UPDATED_STOCK_MINIMUM
        defaultStockShouldNotBeFound("stockMinimum.greaterThanOrEqual=" + UPDATED_STOCK_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllStocksByStockMinimumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockMinimum is less than or equal to DEFAULT_STOCK_MINIMUM
        defaultStockShouldBeFound("stockMinimum.lessThanOrEqual=" + DEFAULT_STOCK_MINIMUM);

        // Get all the stockList where stockMinimum is less than or equal to SMALLER_STOCK_MINIMUM
        defaultStockShouldNotBeFound("stockMinimum.lessThanOrEqual=" + SMALLER_STOCK_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllStocksByStockMinimumIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockMinimum is less than DEFAULT_STOCK_MINIMUM
        defaultStockShouldNotBeFound("stockMinimum.lessThan=" + DEFAULT_STOCK_MINIMUM);

        // Get all the stockList where stockMinimum is less than UPDATED_STOCK_MINIMUM
        defaultStockShouldBeFound("stockMinimum.lessThan=" + UPDATED_STOCK_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllStocksByStockMinimumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where stockMinimum is greater than DEFAULT_STOCK_MINIMUM
        defaultStockShouldNotBeFound("stockMinimum.greaterThan=" + DEFAULT_STOCK_MINIMUM);

        // Get all the stockList where stockMinimum is greater than SMALLER_STOCK_MINIMUM
        defaultStockShouldBeFound("stockMinimum.greaterThan=" + SMALLER_STOCK_MINIMUM);
    }


    @Test
    @Transactional
    public void getAllStocksByDerniereEntreIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereEntre equals to DEFAULT_DERNIERE_ENTRE
        defaultStockShouldBeFound("derniereEntre.equals=" + DEFAULT_DERNIERE_ENTRE);

        // Get all the stockList where derniereEntre equals to UPDATED_DERNIERE_ENTRE
        defaultStockShouldNotBeFound("derniereEntre.equals=" + UPDATED_DERNIERE_ENTRE);
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereEntreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereEntre not equals to DEFAULT_DERNIERE_ENTRE
        defaultStockShouldNotBeFound("derniereEntre.notEquals=" + DEFAULT_DERNIERE_ENTRE);

        // Get all the stockList where derniereEntre not equals to UPDATED_DERNIERE_ENTRE
        defaultStockShouldBeFound("derniereEntre.notEquals=" + UPDATED_DERNIERE_ENTRE);
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereEntreIsInShouldWork() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereEntre in DEFAULT_DERNIERE_ENTRE or UPDATED_DERNIERE_ENTRE
        defaultStockShouldBeFound("derniereEntre.in=" + DEFAULT_DERNIERE_ENTRE + "," + UPDATED_DERNIERE_ENTRE);

        // Get all the stockList where derniereEntre equals to UPDATED_DERNIERE_ENTRE
        defaultStockShouldNotBeFound("derniereEntre.in=" + UPDATED_DERNIERE_ENTRE);
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereEntreIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereEntre is not null
        defaultStockShouldBeFound("derniereEntre.specified=true");

        // Get all the stockList where derniereEntre is null
        defaultStockShouldNotBeFound("derniereEntre.specified=false");
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereEntreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereEntre is greater than or equal to DEFAULT_DERNIERE_ENTRE
        defaultStockShouldBeFound("derniereEntre.greaterThanOrEqual=" + DEFAULT_DERNIERE_ENTRE);

        // Get all the stockList where derniereEntre is greater than or equal to UPDATED_DERNIERE_ENTRE
        defaultStockShouldNotBeFound("derniereEntre.greaterThanOrEqual=" + UPDATED_DERNIERE_ENTRE);
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereEntreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereEntre is less than or equal to DEFAULT_DERNIERE_ENTRE
        defaultStockShouldBeFound("derniereEntre.lessThanOrEqual=" + DEFAULT_DERNIERE_ENTRE);

        // Get all the stockList where derniereEntre is less than or equal to SMALLER_DERNIERE_ENTRE
        defaultStockShouldNotBeFound("derniereEntre.lessThanOrEqual=" + SMALLER_DERNIERE_ENTRE);
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereEntreIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereEntre is less than DEFAULT_DERNIERE_ENTRE
        defaultStockShouldNotBeFound("derniereEntre.lessThan=" + DEFAULT_DERNIERE_ENTRE);

        // Get all the stockList where derniereEntre is less than UPDATED_DERNIERE_ENTRE
        defaultStockShouldBeFound("derniereEntre.lessThan=" + UPDATED_DERNIERE_ENTRE);
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereEntreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereEntre is greater than DEFAULT_DERNIERE_ENTRE
        defaultStockShouldNotBeFound("derniereEntre.greaterThan=" + DEFAULT_DERNIERE_ENTRE);

        // Get all the stockList where derniereEntre is greater than SMALLER_DERNIERE_ENTRE
        defaultStockShouldBeFound("derniereEntre.greaterThan=" + SMALLER_DERNIERE_ENTRE);
    }


    @Test
    @Transactional
    public void getAllStocksByDerniereSortieIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereSortie equals to DEFAULT_DERNIERE_SORTIE
        defaultStockShouldBeFound("derniereSortie.equals=" + DEFAULT_DERNIERE_SORTIE);

        // Get all the stockList where derniereSortie equals to UPDATED_DERNIERE_SORTIE
        defaultStockShouldNotBeFound("derniereSortie.equals=" + UPDATED_DERNIERE_SORTIE);
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereSortieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereSortie not equals to DEFAULT_DERNIERE_SORTIE
        defaultStockShouldNotBeFound("derniereSortie.notEquals=" + DEFAULT_DERNIERE_SORTIE);

        // Get all the stockList where derniereSortie not equals to UPDATED_DERNIERE_SORTIE
        defaultStockShouldBeFound("derniereSortie.notEquals=" + UPDATED_DERNIERE_SORTIE);
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereSortieIsInShouldWork() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereSortie in DEFAULT_DERNIERE_SORTIE or UPDATED_DERNIERE_SORTIE
        defaultStockShouldBeFound("derniereSortie.in=" + DEFAULT_DERNIERE_SORTIE + "," + UPDATED_DERNIERE_SORTIE);

        // Get all the stockList where derniereSortie equals to UPDATED_DERNIERE_SORTIE
        defaultStockShouldNotBeFound("derniereSortie.in=" + UPDATED_DERNIERE_SORTIE);
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereSortieIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereSortie is not null
        defaultStockShouldBeFound("derniereSortie.specified=true");

        // Get all the stockList where derniereSortie is null
        defaultStockShouldNotBeFound("derniereSortie.specified=false");
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereSortieIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereSortie is greater than or equal to DEFAULT_DERNIERE_SORTIE
        defaultStockShouldBeFound("derniereSortie.greaterThanOrEqual=" + DEFAULT_DERNIERE_SORTIE);

        // Get all the stockList where derniereSortie is greater than or equal to UPDATED_DERNIERE_SORTIE
        defaultStockShouldNotBeFound("derniereSortie.greaterThanOrEqual=" + UPDATED_DERNIERE_SORTIE);
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereSortieIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereSortie is less than or equal to DEFAULT_DERNIERE_SORTIE
        defaultStockShouldBeFound("derniereSortie.lessThanOrEqual=" + DEFAULT_DERNIERE_SORTIE);

        // Get all the stockList where derniereSortie is less than or equal to SMALLER_DERNIERE_SORTIE
        defaultStockShouldNotBeFound("derniereSortie.lessThanOrEqual=" + SMALLER_DERNIERE_SORTIE);
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereSortieIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereSortie is less than DEFAULT_DERNIERE_SORTIE
        defaultStockShouldNotBeFound("derniereSortie.lessThan=" + DEFAULT_DERNIERE_SORTIE);

        // Get all the stockList where derniereSortie is less than UPDATED_DERNIERE_SORTIE
        defaultStockShouldBeFound("derniereSortie.lessThan=" + UPDATED_DERNIERE_SORTIE);
    }

    @Test
    @Transactional
    public void getAllStocksByDerniereSortieIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where derniereSortie is greater than DEFAULT_DERNIERE_SORTIE
        defaultStockShouldNotBeFound("derniereSortie.greaterThan=" + DEFAULT_DERNIERE_SORTIE);

        // Get all the stockList where derniereSortie is greater than SMALLER_DERNIERE_SORTIE
        defaultStockShouldBeFound("derniereSortie.greaterThan=" + SMALLER_DERNIERE_SORTIE);
    }


    @Test
    @Transactional
    public void getAllStocksByAlerteStockIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where alerteStock equals to DEFAULT_ALERTE_STOCK
        defaultStockShouldBeFound("alerteStock.equals=" + DEFAULT_ALERTE_STOCK);

        // Get all the stockList where alerteStock equals to UPDATED_ALERTE_STOCK
        defaultStockShouldNotBeFound("alerteStock.equals=" + UPDATED_ALERTE_STOCK);
    }

    @Test
    @Transactional
    public void getAllStocksByAlerteStockIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where alerteStock not equals to DEFAULT_ALERTE_STOCK
        defaultStockShouldNotBeFound("alerteStock.notEquals=" + DEFAULT_ALERTE_STOCK);

        // Get all the stockList where alerteStock not equals to UPDATED_ALERTE_STOCK
        defaultStockShouldBeFound("alerteStock.notEquals=" + UPDATED_ALERTE_STOCK);
    }

    @Test
    @Transactional
    public void getAllStocksByAlerteStockIsInShouldWork() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where alerteStock in DEFAULT_ALERTE_STOCK or UPDATED_ALERTE_STOCK
        defaultStockShouldBeFound("alerteStock.in=" + DEFAULT_ALERTE_STOCK + "," + UPDATED_ALERTE_STOCK);

        // Get all the stockList where alerteStock equals to UPDATED_ALERTE_STOCK
        defaultStockShouldNotBeFound("alerteStock.in=" + UPDATED_ALERTE_STOCK);
    }

    @Test
    @Transactional
    public void getAllStocksByAlerteStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where alerteStock is not null
        defaultStockShouldBeFound("alerteStock.specified=true");

        // Get all the stockList where alerteStock is null
        defaultStockShouldNotBeFound("alerteStock.specified=false");
    }

    @Test
    @Transactional
    public void getAllStocksByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creeLe equals to DEFAULT_CREE_LE
        defaultStockShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the stockList where creeLe equals to UPDATED_CREE_LE
        defaultStockShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllStocksByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creeLe not equals to DEFAULT_CREE_LE
        defaultStockShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the stockList where creeLe not equals to UPDATED_CREE_LE
        defaultStockShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllStocksByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultStockShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the stockList where creeLe equals to UPDATED_CREE_LE
        defaultStockShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllStocksByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creeLe is not null
        defaultStockShouldBeFound("creeLe.specified=true");

        // Get all the stockList where creeLe is null
        defaultStockShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllStocksByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultStockShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the stockList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultStockShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllStocksByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultStockShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the stockList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultStockShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllStocksByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creeLe is less than DEFAULT_CREE_LE
        defaultStockShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the stockList where creeLe is less than UPDATED_CREE_LE
        defaultStockShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllStocksByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creeLe is greater than DEFAULT_CREE_LE
        defaultStockShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the stockList where creeLe is greater than SMALLER_CREE_LE
        defaultStockShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllStocksByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creePar equals to DEFAULT_CREE_PAR
        defaultStockShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the stockList where creePar equals to UPDATED_CREE_PAR
        defaultStockShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllStocksByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creePar not equals to DEFAULT_CREE_PAR
        defaultStockShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the stockList where creePar not equals to UPDATED_CREE_PAR
        defaultStockShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllStocksByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultStockShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the stockList where creePar equals to UPDATED_CREE_PAR
        defaultStockShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllStocksByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creePar is not null
        defaultStockShouldBeFound("creePar.specified=true");

        // Get all the stockList where creePar is null
        defaultStockShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllStocksByCreeParContainsSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creePar contains DEFAULT_CREE_PAR
        defaultStockShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the stockList where creePar contains UPDATED_CREE_PAR
        defaultStockShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllStocksByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where creePar does not contain DEFAULT_CREE_PAR
        defaultStockShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the stockList where creePar does not contain UPDATED_CREE_PAR
        defaultStockShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllStocksByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultStockShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the stockList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultStockShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllStocksByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultStockShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the stockList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultStockShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllStocksByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultStockShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the stockList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultStockShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllStocksByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifieLe is not null
        defaultStockShouldBeFound("modifieLe.specified=true");

        // Get all the stockList where modifieLe is null
        defaultStockShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllStocksByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultStockShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the stockList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultStockShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllStocksByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultStockShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the stockList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultStockShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllStocksByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultStockShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the stockList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultStockShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllStocksByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultStockShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the stockList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultStockShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllStocksByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultStockShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the stockList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultStockShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllStocksByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultStockShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the stockList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultStockShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllStocksByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultStockShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the stockList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultStockShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllStocksByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifiePar is not null
        defaultStockShouldBeFound("modifiePar.specified=true");

        // Get all the stockList where modifiePar is null
        defaultStockShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllStocksByModifieParContainsSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultStockShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the stockList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultStockShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllStocksByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultStockShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the stockList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultStockShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllStocksByRefProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);
        Produit refProduit = ProduitResourceIT.createEntity(em);
        em.persist(refProduit);
        em.flush();
        stock.setRefProduit(refProduit);
        stockRepository.saveAndFlush(stock);
        Long refProduitId = refProduit.getId();

        // Get all the stockList where refProduit equals to refProduitId
        defaultStockShouldBeFound("refProduitId.equals=" + refProduitId);

        // Get all the stockList where refProduit equals to refProduitId + 1
        defaultStockShouldNotBeFound("refProduitId.equals=" + (refProduitId + 1));
    }


    @Test
    @Transactional
    public void getAllStocksByIdCategorieIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);
        Categorie idCategorie = CategorieResourceIT.createEntity(em);
        em.persist(idCategorie);
        em.flush();
        stock.setIdCategorie(idCategorie);
        stockRepository.saveAndFlush(stock);
        Long idCategorieId = idCategorie.getId();

        // Get all the stockList where idCategorie equals to idCategorieId
        defaultStockShouldBeFound("idCategorieId.equals=" + idCategorieId);

        // Get all the stockList where idCategorie equals to idCategorieId + 1
        defaultStockShouldNotBeFound("idCategorieId.equals=" + (idCategorieId + 1));
    }


    @Test
    @Transactional
    public void getAllStocksByIdSousCategorieIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);
        SousCategorie idSousCategorie = SousCategorieResourceIT.createEntity(em);
        em.persist(idSousCategorie);
        em.flush();
        stock.setIdSousCategorie(idSousCategorie);
        stockRepository.saveAndFlush(stock);
        Long idSousCategorieId = idSousCategorie.getId();

        // Get all the stockList where idSousCategorie equals to idSousCategorieId
        defaultStockShouldBeFound("idSousCategorieId.equals=" + idSousCategorieId);

        // Get all the stockList where idSousCategorie equals to idSousCategorieId + 1
        defaultStockShouldNotBeFound("idSousCategorieId.equals=" + (idSousCategorieId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockShouldBeFound(String filter) throws Exception {
        restStockMockMvc.perform(get("/api/stocks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stock.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockReserve").value(hasItem(DEFAULT_STOCK_RESERVE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockCommande").value(hasItem(DEFAULT_STOCK_COMMANDE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockPhysique").value(hasItem(DEFAULT_STOCK_PHYSIQUE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockDisponible").value(hasItem(DEFAULT_STOCK_DISPONIBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockMinimum").value(hasItem(DEFAULT_STOCK_MINIMUM.doubleValue())))
            .andExpect(jsonPath("$.[*].derniereEntre").value(hasItem(DEFAULT_DERNIERE_ENTRE.toString())))
            .andExpect(jsonPath("$.[*].derniereSortie").value(hasItem(DEFAULT_DERNIERE_SORTIE.toString())))
            .andExpect(jsonPath("$.[*].alerteStock").value(hasItem(DEFAULT_ALERTE_STOCK.booleanValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));

        // Check, that the count call also returns 1
        restStockMockMvc.perform(get("/api/stocks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockShouldNotBeFound(String filter) throws Exception {
        restStockMockMvc.perform(get("/api/stocks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockMockMvc.perform(get("/api/stocks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStock() throws Exception {
        // Get the stock
        restStockMockMvc.perform(get("/api/stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Update the stock
        Stock updatedStock = stockRepository.findById(stock.getId()).get();
        // Disconnect from session so that the updates on updatedStock are not directly saved in db
        em.detach(updatedStock);
        updatedStock
            .stockReserve(UPDATED_STOCK_RESERVE)
            .stockCommande(UPDATED_STOCK_COMMANDE)
            .stockPhysique(UPDATED_STOCK_PHYSIQUE)
            .stockDisponible(UPDATED_STOCK_DISPONIBLE)
            .stockMinimum(UPDATED_STOCK_MINIMUM)
            .derniereEntre(UPDATED_DERNIERE_ENTRE)
            .derniereSortie(UPDATED_DERNIERE_SORTIE)
            .alerteStock(UPDATED_ALERTE_STOCK)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        StockDTO stockDTO = stockMapper.toDto(updatedStock);

        restStockMockMvc.perform(put("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isOk());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getStockReserve()).isEqualTo(UPDATED_STOCK_RESERVE);
        assertThat(testStock.getStockCommande()).isEqualTo(UPDATED_STOCK_COMMANDE);
        assertThat(testStock.getStockPhysique()).isEqualTo(UPDATED_STOCK_PHYSIQUE);
        assertThat(testStock.getStockDisponible()).isEqualTo(UPDATED_STOCK_DISPONIBLE);
        assertThat(testStock.getStockMinimum()).isEqualTo(UPDATED_STOCK_MINIMUM);
        assertThat(testStock.getDerniereEntre()).isEqualTo(UPDATED_DERNIERE_ENTRE);
        assertThat(testStock.getDerniereSortie()).isEqualTo(UPDATED_DERNIERE_SORTIE);
        assertThat(testStock.isAlerteStock()).isEqualTo(UPDATED_ALERTE_STOCK);
        assertThat(testStock.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testStock.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testStock.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testStock.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the Stock in Elasticsearch
        verify(mockStockSearchRepository, times(1)).save(testStock);
    }

    @Test
    @Transactional
    public void updateNonExistingStock() throws Exception {
        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockMockMvc.perform(put("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Stock in Elasticsearch
        verify(mockStockSearchRepository, times(0)).save(stock);
    }

    @Test
    @Transactional
    public void deleteStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        int databaseSizeBeforeDelete = stockRepository.findAll().size();

        // Delete the stock
        restStockMockMvc.perform(delete("/api/stocks/{id}", stock.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Stock in Elasticsearch
        verify(mockStockSearchRepository, times(1)).deleteById(stock.getId());
    }

    @Test
    @Transactional
    public void searchStock() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        stockRepository.saveAndFlush(stock);
        when(mockStockSearchRepository.search(queryStringQuery("id:" + stock.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(stock), PageRequest.of(0, 1), 1));

        // Search the stock
        restStockMockMvc.perform(get("/api/_search/stocks?query=id:" + stock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stock.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockReserve").value(hasItem(DEFAULT_STOCK_RESERVE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockCommande").value(hasItem(DEFAULT_STOCK_COMMANDE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockPhysique").value(hasItem(DEFAULT_STOCK_PHYSIQUE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockDisponible").value(hasItem(DEFAULT_STOCK_DISPONIBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockMinimum").value(hasItem(DEFAULT_STOCK_MINIMUM.doubleValue())))
            .andExpect(jsonPath("$.[*].derniereEntre").value(hasItem(DEFAULT_DERNIERE_ENTRE.toString())))
            .andExpect(jsonPath("$.[*].derniereSortie").value(hasItem(DEFAULT_DERNIERE_SORTIE.toString())))
            .andExpect(jsonPath("$.[*].alerteStock").value(hasItem(DEFAULT_ALERTE_STOCK.booleanValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
