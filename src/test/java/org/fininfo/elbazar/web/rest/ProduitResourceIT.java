package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.domain.Stock;
import org.fininfo.elbazar.domain.MouvementStock;
import org.fininfo.elbazar.domain.CommandeLignes;
import org.fininfo.elbazar.domain.Categorie;
import org.fininfo.elbazar.domain.SousCategorie;
import org.fininfo.elbazar.domain.ProduitUnite;
import org.fininfo.elbazar.repository.ProduitRepository;
import org.fininfo.elbazar.repository.search.ProduitSearchRepository;
import org.fininfo.elbazar.service.ProduitService;
import org.fininfo.elbazar.service.dto.ProduitDTO;
import org.fininfo.elbazar.service.mapper.ProduitMapper;
import org.fininfo.elbazar.service.dto.ProduitCriteria;
import org.fininfo.elbazar.service.ProduitQueryService;

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

import org.fininfo.elbazar.domain.enumeration.Devise;
import org.fininfo.elbazar.domain.enumeration.SourcePrd;
/**
 * Integration tests for the {@link ProduitResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProduitResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_BARRE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_BARRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ETAT = false;
    private static final Boolean UPDATED_ETAT = true;

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final String DEFAULT_NATURE = "AAAAAAAAAA";
    private static final String UPDATED_NATURE = "BBBBBBBBBB";

    private static final Double DEFAULT_STOCK_MINIMUM = 1D;
    private static final Double UPDATED_STOCK_MINIMUM = 2D;
    private static final Double SMALLER_STOCK_MINIMUM = 1D - 1D;

    private static final Double DEFAULT_QUANTITE_VENTE_MAX = 1D;
    private static final Double UPDATED_QUANTITE_VENTE_MAX = 2D;
    private static final Double SMALLER_QUANTITE_VENTE_MAX = 1D - 1D;

    private static final Boolean DEFAULT_HORS_STOCK = false;
    private static final Boolean UPDATED_HORS_STOCK = true;

    private static final Boolean DEFAULT_TYPE_SERVICE = false;
    private static final Boolean UPDATED_TYPE_SERVICE = true;

    private static final LocalDate DEFAULT_DATE_PREMPTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PREMPTION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_PREMPTION = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_PRIX_HT = 1D;
    private static final Double UPDATED_PRIX_HT = 2D;
    private static final Double SMALLER_PRIX_HT = 1D - 1D;

    private static final Double DEFAULT_TAUX_TVA = 1D;
    private static final Double UPDATED_TAUX_TVA = 2D;
    private static final Double SMALLER_TAUX_TVA = 1D - 1D;

    private static final Double DEFAULT_PRIX_TTC = 1D;
    private static final Double UPDATED_PRIX_TTC = 2D;
    private static final Double SMALLER_PRIX_TTC = 1D - 1D;

    private static final Devise DEFAULT_DEVISE = Devise.TND;
    private static final Devise UPDATED_DEVISE = Devise.EUR;

    private static final SourcePrd DEFAULT_SOURCE_PRODUIT = SourcePrd.Locale;
    private static final SourcePrd UPDATED_SOURCE_PRODUIT = SourcePrd.Externe;

    private static final String DEFAULT_RATING = "1";
    private static final String UPDATED_RATING = "5";

    private static final Boolean DEFAULT_ELIGIBLE_REMISE = false;
    private static final Boolean UPDATED_ELIGIBLE_REMISE = true;

    private static final Double DEFAULT_REMISE = 1D;
    private static final Double UPDATED_REMISE = 2D;
    private static final Double SMALLER_REMISE = 1D - 1D;

    private static final LocalDate DEFAULT_DEBUT_PROMO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEBUT_PROMO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DEBUT_PROMO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FIN_PROMO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIN_PROMO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FIN_PROMO = LocalDate.ofEpochDay(-1L);

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

    private static final Boolean DEFAULT_EN_VEDETTE = false;
    private static final Boolean UPDATED_EN_VEDETTE = true;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitMapper produitMapper;

    @Autowired
    private ProduitService produitService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.ProduitSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProduitSearchRepository mockProduitSearchRepository;

    @Autowired
    private ProduitQueryService produitQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProduitMockMvc;

    private Produit produit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createEntity(EntityManager em) {
        Produit produit = new Produit()
            .reference(DEFAULT_REFERENCE)
            .nom(DEFAULT_NOM)
            .codeBarre(DEFAULT_CODE_BARRE)
            .description(DEFAULT_DESCRIPTION)
            .etat(DEFAULT_ETAT)
            .marque(DEFAULT_MARQUE)
            .nature(DEFAULT_NATURE)
            .stockMinimum(DEFAULT_STOCK_MINIMUM)
            .quantiteVenteMax(DEFAULT_QUANTITE_VENTE_MAX)
            .horsStock(DEFAULT_HORS_STOCK)
            .typeService(DEFAULT_TYPE_SERVICE)
            .datePremption(DEFAULT_DATE_PREMPTION)
            .prixHT(DEFAULT_PRIX_HT)
            .tauxTVA(DEFAULT_TAUX_TVA)
            .prixTTC(DEFAULT_PRIX_TTC)
            .devise(DEFAULT_DEVISE)
            .sourceProduit(DEFAULT_SOURCE_PRODUIT)
            .rating(DEFAULT_RATING)
            .eligibleRemise(DEFAULT_ELIGIBLE_REMISE)
            .remise(DEFAULT_REMISE)
            .debutPromo(DEFAULT_DEBUT_PROMO)
            .finPromo(DEFAULT_FIN_PROMO)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR)
            .enVedette(DEFAULT_EN_VEDETTE)
            .imageUrl(DEFAULT_IMAGE_URL);
        return produit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createUpdatedEntity(EntityManager em) {
        Produit produit = new Produit()
            .reference(UPDATED_REFERENCE)
            .nom(UPDATED_NOM)
            .codeBarre(UPDATED_CODE_BARRE)
            .description(UPDATED_DESCRIPTION)
            .etat(UPDATED_ETAT)
            .marque(UPDATED_MARQUE)
            .nature(UPDATED_NATURE)
            .stockMinimum(UPDATED_STOCK_MINIMUM)
            .quantiteVenteMax(UPDATED_QUANTITE_VENTE_MAX)
            .horsStock(UPDATED_HORS_STOCK)
            .typeService(UPDATED_TYPE_SERVICE)
            .datePremption(UPDATED_DATE_PREMPTION)
            .prixHT(UPDATED_PRIX_HT)
            .tauxTVA(UPDATED_TAUX_TVA)
            .prixTTC(UPDATED_PRIX_TTC)
            .devise(UPDATED_DEVISE)
            .sourceProduit(UPDATED_SOURCE_PRODUIT)
            .rating(UPDATED_RATING)
            .eligibleRemise(UPDATED_ELIGIBLE_REMISE)
            .remise(UPDATED_REMISE)
            .debutPromo(UPDATED_DEBUT_PROMO)
            .finPromo(UPDATED_FIN_PROMO)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR)
            .enVedette(UPDATED_EN_VEDETTE)
            .imageUrl(UPDATED_IMAGE_URL);
        return produit;
    }

    @BeforeEach
    public void initTest() {
        produit = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduit() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();
        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);
        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isCreated());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate + 1);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testProduit.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProduit.getCodeBarre()).isEqualTo(DEFAULT_CODE_BARRE);
        assertThat(testProduit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduit.isEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testProduit.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testProduit.getNature()).isEqualTo(DEFAULT_NATURE);
        assertThat(testProduit.getStockMinimum()).isEqualTo(DEFAULT_STOCK_MINIMUM);
        assertThat(testProduit.getQuantiteVenteMax()).isEqualTo(DEFAULT_QUANTITE_VENTE_MAX);
        assertThat(testProduit.isHorsStock()).isEqualTo(DEFAULT_HORS_STOCK);
        assertThat(testProduit.isTypeService()).isEqualTo(DEFAULT_TYPE_SERVICE);
        assertThat(testProduit.getDatePremption()).isEqualTo(DEFAULT_DATE_PREMPTION);
        assertThat(testProduit.getPrixHT()).isEqualTo(DEFAULT_PRIX_HT);
        assertThat(testProduit.getTauxTVA()).isEqualTo(DEFAULT_TAUX_TVA);
        assertThat(testProduit.getPrixTTC()).isEqualTo(DEFAULT_PRIX_TTC);
        assertThat(testProduit.getDevise()).isEqualTo(DEFAULT_DEVISE);
        assertThat(testProduit.getSourceProduit()).isEqualTo(DEFAULT_SOURCE_PRODUIT);
        assertThat(testProduit.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testProduit.isEligibleRemise()).isEqualTo(DEFAULT_ELIGIBLE_REMISE);
        assertThat(testProduit.getRemise()).isEqualTo(DEFAULT_REMISE);
        assertThat(testProduit.getDebutPromo()).isEqualTo(DEFAULT_DEBUT_PROMO);
        assertThat(testProduit.getFinPromo()).isEqualTo(DEFAULT_FIN_PROMO);
        assertThat(testProduit.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProduit.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testProduit.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testProduit.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testProduit.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testProduit.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);
        assertThat(testProduit.isEnVedette()).isEqualTo(DEFAULT_EN_VEDETTE);
        assertThat(testProduit.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);

        // Validate the Produit in Elasticsearch
        verify(mockProduitSearchRepository, times(1)).save(testProduit);
    }

    @Test
    @Transactional
    public void createProduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // Create the Produit with an existing ID
        produit.setId(1L);
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate);

        // Validate the Produit in Elasticsearch
        verify(mockProduitSearchRepository, times(0)).save(produit);
    }


    @Test
    @Transactional
    public void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setReference(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);


        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setNom(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);


        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setDescription(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);


        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setEtat(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);


        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMarqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setMarque(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);


        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockMinimumIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setStockMinimum(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);


        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantiteVenteMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setQuantiteVenteMax(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);


        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixHTIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setPrixHT(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);


        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTauxTVAIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setTauxTVA(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);


        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setPrixTTC(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);


        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSourceProduitIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setSourceProduit(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);


        restProduitMockMvc.perform(post("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProduits() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList
        restProduitMockMvc.perform(get("/api/produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].codeBarre").value(hasItem(DEFAULT_CODE_BARRE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE)))
            .andExpect(jsonPath("$.[*].stockMinimum").value(hasItem(DEFAULT_STOCK_MINIMUM.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteVenteMax").value(hasItem(DEFAULT_QUANTITE_VENTE_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].horsStock").value(hasItem(DEFAULT_HORS_STOCK.booleanValue())))
            .andExpect(jsonPath("$.[*].typeService").value(hasItem(DEFAULT_TYPE_SERVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].datePremption").value(hasItem(DEFAULT_DATE_PREMPTION.toString())))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].tauxTVA").value(hasItem(DEFAULT_TAUX_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())))
            .andExpect(jsonPath("$.[*].sourceProduit").value(hasItem(DEFAULT_SOURCE_PRODUIT.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].eligibleRemise").value(hasItem(DEFAULT_ELIGIBLE_REMISE.booleanValue())))
            .andExpect(jsonPath("$.[*].remise").value(hasItem(DEFAULT_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].debutPromo").value(hasItem(DEFAULT_DEBUT_PROMO.toString())))
            .andExpect(jsonPath("$.[*].finPromo").value(hasItem(DEFAULT_FIN_PROMO.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].enVedette").value(hasItem(DEFAULT_EN_VEDETTE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }
    
    @Test
    @Transactional
    public void getProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get the produit
        restProduitMockMvc.perform(get("/api/produits/{id}", produit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produit.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.codeBarre").value(DEFAULT_CODE_BARRE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.booleanValue()))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.nature").value(DEFAULT_NATURE))
            .andExpect(jsonPath("$.stockMinimum").value(DEFAULT_STOCK_MINIMUM.doubleValue()))
            .andExpect(jsonPath("$.quantiteVenteMax").value(DEFAULT_QUANTITE_VENTE_MAX.doubleValue()))
            .andExpect(jsonPath("$.horsStock").value(DEFAULT_HORS_STOCK.booleanValue()))
            .andExpect(jsonPath("$.typeService").value(DEFAULT_TYPE_SERVICE.booleanValue()))
            .andExpect(jsonPath("$.datePremption").value(DEFAULT_DATE_PREMPTION.toString()))
            .andExpect(jsonPath("$.prixHT").value(DEFAULT_PRIX_HT.doubleValue()))
            .andExpect(jsonPath("$.tauxTVA").value(DEFAULT_TAUX_TVA.doubleValue()))
            .andExpect(jsonPath("$.prixTTC").value(DEFAULT_PRIX_TTC.doubleValue()))
            .andExpect(jsonPath("$.devise").value(DEFAULT_DEVISE.toString()))
            .andExpect(jsonPath("$.sourceProduit").value(DEFAULT_SOURCE_PRODUIT.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.eligibleRemise").value(DEFAULT_ELIGIBLE_REMISE.booleanValue()))
            .andExpect(jsonPath("$.remise").value(DEFAULT_REMISE.doubleValue()))
            .andExpect(jsonPath("$.debutPromo").value(DEFAULT_DEBUT_PROMO.toString()))
            .andExpect(jsonPath("$.finPromo").value(DEFAULT_FIN_PROMO.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR))
            .andExpect(jsonPath("$.enVedette").value(DEFAULT_EN_VEDETTE.booleanValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL));
    }


    @Test
    @Transactional
    public void getProduitsByIdFiltering() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        Long id = produit.getId();

        defaultProduitShouldBeFound("id.equals=" + id);
        defaultProduitShouldNotBeFound("id.notEquals=" + id);

        defaultProduitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProduitShouldNotBeFound("id.greaterThan=" + id);

        defaultProduitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProduitShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProduitsByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where reference equals to DEFAULT_REFERENCE
        defaultProduitShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the produitList where reference equals to UPDATED_REFERENCE
        defaultProduitShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllProduitsByReferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where reference not equals to DEFAULT_REFERENCE
        defaultProduitShouldNotBeFound("reference.notEquals=" + DEFAULT_REFERENCE);

        // Get all the produitList where reference not equals to UPDATED_REFERENCE
        defaultProduitShouldBeFound("reference.notEquals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllProduitsByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultProduitShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the produitList where reference equals to UPDATED_REFERENCE
        defaultProduitShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllProduitsByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where reference is not null
        defaultProduitShouldBeFound("reference.specified=true");

        // Get all the produitList where reference is null
        defaultProduitShouldNotBeFound("reference.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByReferenceContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where reference contains DEFAULT_REFERENCE
        defaultProduitShouldBeFound("reference.contains=" + DEFAULT_REFERENCE);

        // Get all the produitList where reference contains UPDATED_REFERENCE
        defaultProduitShouldNotBeFound("reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllProduitsByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where reference does not contain DEFAULT_REFERENCE
        defaultProduitShouldNotBeFound("reference.doesNotContain=" + DEFAULT_REFERENCE);

        // Get all the produitList where reference does not contain UPDATED_REFERENCE
        defaultProduitShouldBeFound("reference.doesNotContain=" + UPDATED_REFERENCE);
    }


    @Test
    @Transactional
    public void getAllProduitsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nom equals to DEFAULT_NOM
        defaultProduitShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the produitList where nom equals to UPDATED_NOM
        defaultProduitShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllProduitsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nom not equals to DEFAULT_NOM
        defaultProduitShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the produitList where nom not equals to UPDATED_NOM
        defaultProduitShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllProduitsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultProduitShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the produitList where nom equals to UPDATED_NOM
        defaultProduitShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllProduitsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nom is not null
        defaultProduitShouldBeFound("nom.specified=true");

        // Get all the produitList where nom is null
        defaultProduitShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByNomContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nom contains DEFAULT_NOM
        defaultProduitShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the produitList where nom contains UPDATED_NOM
        defaultProduitShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllProduitsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nom does not contain DEFAULT_NOM
        defaultProduitShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the produitList where nom does not contain UPDATED_NOM
        defaultProduitShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllProduitsByCodeBarreIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where codeBarre equals to DEFAULT_CODE_BARRE
        defaultProduitShouldBeFound("codeBarre.equals=" + DEFAULT_CODE_BARRE);

        // Get all the produitList where codeBarre equals to UPDATED_CODE_BARRE
        defaultProduitShouldNotBeFound("codeBarre.equals=" + UPDATED_CODE_BARRE);
    }

    @Test
    @Transactional
    public void getAllProduitsByCodeBarreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where codeBarre not equals to DEFAULT_CODE_BARRE
        defaultProduitShouldNotBeFound("codeBarre.notEquals=" + DEFAULT_CODE_BARRE);

        // Get all the produitList where codeBarre not equals to UPDATED_CODE_BARRE
        defaultProduitShouldBeFound("codeBarre.notEquals=" + UPDATED_CODE_BARRE);
    }

    @Test
    @Transactional
    public void getAllProduitsByCodeBarreIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where codeBarre in DEFAULT_CODE_BARRE or UPDATED_CODE_BARRE
        defaultProduitShouldBeFound("codeBarre.in=" + DEFAULT_CODE_BARRE + "," + UPDATED_CODE_BARRE);

        // Get all the produitList where codeBarre equals to UPDATED_CODE_BARRE
        defaultProduitShouldNotBeFound("codeBarre.in=" + UPDATED_CODE_BARRE);
    }

    @Test
    @Transactional
    public void getAllProduitsByCodeBarreIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where codeBarre is not null
        defaultProduitShouldBeFound("codeBarre.specified=true");

        // Get all the produitList where codeBarre is null
        defaultProduitShouldNotBeFound("codeBarre.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByCodeBarreContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where codeBarre contains DEFAULT_CODE_BARRE
        defaultProduitShouldBeFound("codeBarre.contains=" + DEFAULT_CODE_BARRE);

        // Get all the produitList where codeBarre contains UPDATED_CODE_BARRE
        defaultProduitShouldNotBeFound("codeBarre.contains=" + UPDATED_CODE_BARRE);
    }

    @Test
    @Transactional
    public void getAllProduitsByCodeBarreNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where codeBarre does not contain DEFAULT_CODE_BARRE
        defaultProduitShouldNotBeFound("codeBarre.doesNotContain=" + DEFAULT_CODE_BARRE);

        // Get all the produitList where codeBarre does not contain UPDATED_CODE_BARRE
        defaultProduitShouldBeFound("codeBarre.doesNotContain=" + UPDATED_CODE_BARRE);
    }


    @Test
    @Transactional
    public void getAllProduitsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where description equals to DEFAULT_DESCRIPTION
        defaultProduitShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the produitList where description equals to UPDATED_DESCRIPTION
        defaultProduitShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where description not equals to DEFAULT_DESCRIPTION
        defaultProduitShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the produitList where description not equals to UPDATED_DESCRIPTION
        defaultProduitShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProduitShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the produitList where description equals to UPDATED_DESCRIPTION
        defaultProduitShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where description is not null
        defaultProduitShouldBeFound("description.specified=true");

        // Get all the produitList where description is null
        defaultProduitShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where description contains DEFAULT_DESCRIPTION
        defaultProduitShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the produitList where description contains UPDATED_DESCRIPTION
        defaultProduitShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where description does not contain DEFAULT_DESCRIPTION
        defaultProduitShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the produitList where description does not contain UPDATED_DESCRIPTION
        defaultProduitShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllProduitsByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where etat equals to DEFAULT_ETAT
        defaultProduitShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the produitList where etat equals to UPDATED_ETAT
        defaultProduitShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllProduitsByEtatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where etat not equals to DEFAULT_ETAT
        defaultProduitShouldNotBeFound("etat.notEquals=" + DEFAULT_ETAT);

        // Get all the produitList where etat not equals to UPDATED_ETAT
        defaultProduitShouldBeFound("etat.notEquals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllProduitsByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultProduitShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the produitList where etat equals to UPDATED_ETAT
        defaultProduitShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllProduitsByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where etat is not null
        defaultProduitShouldBeFound("etat.specified=true");

        // Get all the produitList where etat is null
        defaultProduitShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByMarqueIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where marque equals to DEFAULT_MARQUE
        defaultProduitShouldBeFound("marque.equals=" + DEFAULT_MARQUE);

        // Get all the produitList where marque equals to UPDATED_MARQUE
        defaultProduitShouldNotBeFound("marque.equals=" + UPDATED_MARQUE);
    }

    @Test
    @Transactional
    public void getAllProduitsByMarqueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where marque not equals to DEFAULT_MARQUE
        defaultProduitShouldNotBeFound("marque.notEquals=" + DEFAULT_MARQUE);

        // Get all the produitList where marque not equals to UPDATED_MARQUE
        defaultProduitShouldBeFound("marque.notEquals=" + UPDATED_MARQUE);
    }

    @Test
    @Transactional
    public void getAllProduitsByMarqueIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where marque in DEFAULT_MARQUE or UPDATED_MARQUE
        defaultProduitShouldBeFound("marque.in=" + DEFAULT_MARQUE + "," + UPDATED_MARQUE);

        // Get all the produitList where marque equals to UPDATED_MARQUE
        defaultProduitShouldNotBeFound("marque.in=" + UPDATED_MARQUE);
    }

    @Test
    @Transactional
    public void getAllProduitsByMarqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where marque is not null
        defaultProduitShouldBeFound("marque.specified=true");

        // Get all the produitList where marque is null
        defaultProduitShouldNotBeFound("marque.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByMarqueContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where marque contains DEFAULT_MARQUE
        defaultProduitShouldBeFound("marque.contains=" + DEFAULT_MARQUE);

        // Get all the produitList where marque contains UPDATED_MARQUE
        defaultProduitShouldNotBeFound("marque.contains=" + UPDATED_MARQUE);
    }

    @Test
    @Transactional
    public void getAllProduitsByMarqueNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where marque does not contain DEFAULT_MARQUE
        defaultProduitShouldNotBeFound("marque.doesNotContain=" + DEFAULT_MARQUE);

        // Get all the produitList where marque does not contain UPDATED_MARQUE
        defaultProduitShouldBeFound("marque.doesNotContain=" + UPDATED_MARQUE);
    }


    @Test
    @Transactional
    public void getAllProduitsByNatureIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nature equals to DEFAULT_NATURE
        defaultProduitShouldBeFound("nature.equals=" + DEFAULT_NATURE);

        // Get all the produitList where nature equals to UPDATED_NATURE
        defaultProduitShouldNotBeFound("nature.equals=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    public void getAllProduitsByNatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nature not equals to DEFAULT_NATURE
        defaultProduitShouldNotBeFound("nature.notEquals=" + DEFAULT_NATURE);

        // Get all the produitList where nature not equals to UPDATED_NATURE
        defaultProduitShouldBeFound("nature.notEquals=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    public void getAllProduitsByNatureIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nature in DEFAULT_NATURE or UPDATED_NATURE
        defaultProduitShouldBeFound("nature.in=" + DEFAULT_NATURE + "," + UPDATED_NATURE);

        // Get all the produitList where nature equals to UPDATED_NATURE
        defaultProduitShouldNotBeFound("nature.in=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    public void getAllProduitsByNatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nature is not null
        defaultProduitShouldBeFound("nature.specified=true");

        // Get all the produitList where nature is null
        defaultProduitShouldNotBeFound("nature.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByNatureContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nature contains DEFAULT_NATURE
        defaultProduitShouldBeFound("nature.contains=" + DEFAULT_NATURE);

        // Get all the produitList where nature contains UPDATED_NATURE
        defaultProduitShouldNotBeFound("nature.contains=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    public void getAllProduitsByNatureNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nature does not contain DEFAULT_NATURE
        defaultProduitShouldNotBeFound("nature.doesNotContain=" + DEFAULT_NATURE);

        // Get all the produitList where nature does not contain UPDATED_NATURE
        defaultProduitShouldBeFound("nature.doesNotContain=" + UPDATED_NATURE);
    }


    @Test
    @Transactional
    public void getAllProduitsByStockMinimumIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where stockMinimum equals to DEFAULT_STOCK_MINIMUM
        defaultProduitShouldBeFound("stockMinimum.equals=" + DEFAULT_STOCK_MINIMUM);

        // Get all the produitList where stockMinimum equals to UPDATED_STOCK_MINIMUM
        defaultProduitShouldNotBeFound("stockMinimum.equals=" + UPDATED_STOCK_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllProduitsByStockMinimumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where stockMinimum not equals to DEFAULT_STOCK_MINIMUM
        defaultProduitShouldNotBeFound("stockMinimum.notEquals=" + DEFAULT_STOCK_MINIMUM);

        // Get all the produitList where stockMinimum not equals to UPDATED_STOCK_MINIMUM
        defaultProduitShouldBeFound("stockMinimum.notEquals=" + UPDATED_STOCK_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllProduitsByStockMinimumIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where stockMinimum in DEFAULT_STOCK_MINIMUM or UPDATED_STOCK_MINIMUM
        defaultProduitShouldBeFound("stockMinimum.in=" + DEFAULT_STOCK_MINIMUM + "," + UPDATED_STOCK_MINIMUM);

        // Get all the produitList where stockMinimum equals to UPDATED_STOCK_MINIMUM
        defaultProduitShouldNotBeFound("stockMinimum.in=" + UPDATED_STOCK_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllProduitsByStockMinimumIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where stockMinimum is not null
        defaultProduitShouldBeFound("stockMinimum.specified=true");

        // Get all the produitList where stockMinimum is null
        defaultProduitShouldNotBeFound("stockMinimum.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByStockMinimumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where stockMinimum is greater than or equal to DEFAULT_STOCK_MINIMUM
        defaultProduitShouldBeFound("stockMinimum.greaterThanOrEqual=" + DEFAULT_STOCK_MINIMUM);

        // Get all the produitList where stockMinimum is greater than or equal to UPDATED_STOCK_MINIMUM
        defaultProduitShouldNotBeFound("stockMinimum.greaterThanOrEqual=" + UPDATED_STOCK_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllProduitsByStockMinimumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where stockMinimum is less than or equal to DEFAULT_STOCK_MINIMUM
        defaultProduitShouldBeFound("stockMinimum.lessThanOrEqual=" + DEFAULT_STOCK_MINIMUM);

        // Get all the produitList where stockMinimum is less than or equal to SMALLER_STOCK_MINIMUM
        defaultProduitShouldNotBeFound("stockMinimum.lessThanOrEqual=" + SMALLER_STOCK_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllProduitsByStockMinimumIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where stockMinimum is less than DEFAULT_STOCK_MINIMUM
        defaultProduitShouldNotBeFound("stockMinimum.lessThan=" + DEFAULT_STOCK_MINIMUM);

        // Get all the produitList where stockMinimum is less than UPDATED_STOCK_MINIMUM
        defaultProduitShouldBeFound("stockMinimum.lessThan=" + UPDATED_STOCK_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllProduitsByStockMinimumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where stockMinimum is greater than DEFAULT_STOCK_MINIMUM
        defaultProduitShouldNotBeFound("stockMinimum.greaterThan=" + DEFAULT_STOCK_MINIMUM);

        // Get all the produitList where stockMinimum is greater than SMALLER_STOCK_MINIMUM
        defaultProduitShouldBeFound("stockMinimum.greaterThan=" + SMALLER_STOCK_MINIMUM);
    }


    @Test
    @Transactional
    public void getAllProduitsByQuantiteVenteMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where quantiteVenteMax equals to DEFAULT_QUANTITE_VENTE_MAX
        defaultProduitShouldBeFound("quantiteVenteMax.equals=" + DEFAULT_QUANTITE_VENTE_MAX);

        // Get all the produitList where quantiteVenteMax equals to UPDATED_QUANTITE_VENTE_MAX
        defaultProduitShouldNotBeFound("quantiteVenteMax.equals=" + UPDATED_QUANTITE_VENTE_MAX);
    }

    @Test
    @Transactional
    public void getAllProduitsByQuantiteVenteMaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where quantiteVenteMax not equals to DEFAULT_QUANTITE_VENTE_MAX
        defaultProduitShouldNotBeFound("quantiteVenteMax.notEquals=" + DEFAULT_QUANTITE_VENTE_MAX);

        // Get all the produitList where quantiteVenteMax not equals to UPDATED_QUANTITE_VENTE_MAX
        defaultProduitShouldBeFound("quantiteVenteMax.notEquals=" + UPDATED_QUANTITE_VENTE_MAX);
    }

    @Test
    @Transactional
    public void getAllProduitsByQuantiteVenteMaxIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where quantiteVenteMax in DEFAULT_QUANTITE_VENTE_MAX or UPDATED_QUANTITE_VENTE_MAX
        defaultProduitShouldBeFound("quantiteVenteMax.in=" + DEFAULT_QUANTITE_VENTE_MAX + "," + UPDATED_QUANTITE_VENTE_MAX);

        // Get all the produitList where quantiteVenteMax equals to UPDATED_QUANTITE_VENTE_MAX
        defaultProduitShouldNotBeFound("quantiteVenteMax.in=" + UPDATED_QUANTITE_VENTE_MAX);
    }

    @Test
    @Transactional
    public void getAllProduitsByQuantiteVenteMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where quantiteVenteMax is not null
        defaultProduitShouldBeFound("quantiteVenteMax.specified=true");

        // Get all the produitList where quantiteVenteMax is null
        defaultProduitShouldNotBeFound("quantiteVenteMax.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByQuantiteVenteMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where quantiteVenteMax is greater than or equal to DEFAULT_QUANTITE_VENTE_MAX
        defaultProduitShouldBeFound("quantiteVenteMax.greaterThanOrEqual=" + DEFAULT_QUANTITE_VENTE_MAX);

        // Get all the produitList where quantiteVenteMax is greater than or equal to UPDATED_QUANTITE_VENTE_MAX
        defaultProduitShouldNotBeFound("quantiteVenteMax.greaterThanOrEqual=" + UPDATED_QUANTITE_VENTE_MAX);
    }

    @Test
    @Transactional
    public void getAllProduitsByQuantiteVenteMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where quantiteVenteMax is less than or equal to DEFAULT_QUANTITE_VENTE_MAX
        defaultProduitShouldBeFound("quantiteVenteMax.lessThanOrEqual=" + DEFAULT_QUANTITE_VENTE_MAX);

        // Get all the produitList where quantiteVenteMax is less than or equal to SMALLER_QUANTITE_VENTE_MAX
        defaultProduitShouldNotBeFound("quantiteVenteMax.lessThanOrEqual=" + SMALLER_QUANTITE_VENTE_MAX);
    }

    @Test
    @Transactional
    public void getAllProduitsByQuantiteVenteMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where quantiteVenteMax is less than DEFAULT_QUANTITE_VENTE_MAX
        defaultProduitShouldNotBeFound("quantiteVenteMax.lessThan=" + DEFAULT_QUANTITE_VENTE_MAX);

        // Get all the produitList where quantiteVenteMax is less than UPDATED_QUANTITE_VENTE_MAX
        defaultProduitShouldBeFound("quantiteVenteMax.lessThan=" + UPDATED_QUANTITE_VENTE_MAX);
    }

    @Test
    @Transactional
    public void getAllProduitsByQuantiteVenteMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where quantiteVenteMax is greater than DEFAULT_QUANTITE_VENTE_MAX
        defaultProduitShouldNotBeFound("quantiteVenteMax.greaterThan=" + DEFAULT_QUANTITE_VENTE_MAX);

        // Get all the produitList where quantiteVenteMax is greater than SMALLER_QUANTITE_VENTE_MAX
        defaultProduitShouldBeFound("quantiteVenteMax.greaterThan=" + SMALLER_QUANTITE_VENTE_MAX);
    }


    @Test
    @Transactional
    public void getAllProduitsByHorsStockIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where horsStock equals to DEFAULT_HORS_STOCK
        defaultProduitShouldBeFound("horsStock.equals=" + DEFAULT_HORS_STOCK);

        // Get all the produitList where horsStock equals to UPDATED_HORS_STOCK
        defaultProduitShouldNotBeFound("horsStock.equals=" + UPDATED_HORS_STOCK);
    }

    @Test
    @Transactional
    public void getAllProduitsByHorsStockIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where horsStock not equals to DEFAULT_HORS_STOCK
        defaultProduitShouldNotBeFound("horsStock.notEquals=" + DEFAULT_HORS_STOCK);

        // Get all the produitList where horsStock not equals to UPDATED_HORS_STOCK
        defaultProduitShouldBeFound("horsStock.notEquals=" + UPDATED_HORS_STOCK);
    }

    @Test
    @Transactional
    public void getAllProduitsByHorsStockIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where horsStock in DEFAULT_HORS_STOCK or UPDATED_HORS_STOCK
        defaultProduitShouldBeFound("horsStock.in=" + DEFAULT_HORS_STOCK + "," + UPDATED_HORS_STOCK);

        // Get all the produitList where horsStock equals to UPDATED_HORS_STOCK
        defaultProduitShouldNotBeFound("horsStock.in=" + UPDATED_HORS_STOCK);
    }

    @Test
    @Transactional
    public void getAllProduitsByHorsStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where horsStock is not null
        defaultProduitShouldBeFound("horsStock.specified=true");

        // Get all the produitList where horsStock is null
        defaultProduitShouldNotBeFound("horsStock.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByTypeServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where typeService equals to DEFAULT_TYPE_SERVICE
        defaultProduitShouldBeFound("typeService.equals=" + DEFAULT_TYPE_SERVICE);

        // Get all the produitList where typeService equals to UPDATED_TYPE_SERVICE
        defaultProduitShouldNotBeFound("typeService.equals=" + UPDATED_TYPE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllProduitsByTypeServiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where typeService not equals to DEFAULT_TYPE_SERVICE
        defaultProduitShouldNotBeFound("typeService.notEquals=" + DEFAULT_TYPE_SERVICE);

        // Get all the produitList where typeService not equals to UPDATED_TYPE_SERVICE
        defaultProduitShouldBeFound("typeService.notEquals=" + UPDATED_TYPE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllProduitsByTypeServiceIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where typeService in DEFAULT_TYPE_SERVICE or UPDATED_TYPE_SERVICE
        defaultProduitShouldBeFound("typeService.in=" + DEFAULT_TYPE_SERVICE + "," + UPDATED_TYPE_SERVICE);

        // Get all the produitList where typeService equals to UPDATED_TYPE_SERVICE
        defaultProduitShouldNotBeFound("typeService.in=" + UPDATED_TYPE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllProduitsByTypeServiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where typeService is not null
        defaultProduitShouldBeFound("typeService.specified=true");

        // Get all the produitList where typeService is null
        defaultProduitShouldNotBeFound("typeService.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByDatePremptionIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where datePremption equals to DEFAULT_DATE_PREMPTION
        defaultProduitShouldBeFound("datePremption.equals=" + DEFAULT_DATE_PREMPTION);

        // Get all the produitList where datePremption equals to UPDATED_DATE_PREMPTION
        defaultProduitShouldNotBeFound("datePremption.equals=" + UPDATED_DATE_PREMPTION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDatePremptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where datePremption not equals to DEFAULT_DATE_PREMPTION
        defaultProduitShouldNotBeFound("datePremption.notEquals=" + DEFAULT_DATE_PREMPTION);

        // Get all the produitList where datePremption not equals to UPDATED_DATE_PREMPTION
        defaultProduitShouldBeFound("datePremption.notEquals=" + UPDATED_DATE_PREMPTION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDatePremptionIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where datePremption in DEFAULT_DATE_PREMPTION or UPDATED_DATE_PREMPTION
        defaultProduitShouldBeFound("datePremption.in=" + DEFAULT_DATE_PREMPTION + "," + UPDATED_DATE_PREMPTION);

        // Get all the produitList where datePremption equals to UPDATED_DATE_PREMPTION
        defaultProduitShouldNotBeFound("datePremption.in=" + UPDATED_DATE_PREMPTION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDatePremptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where datePremption is not null
        defaultProduitShouldBeFound("datePremption.specified=true");

        // Get all the produitList where datePremption is null
        defaultProduitShouldNotBeFound("datePremption.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByDatePremptionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where datePremption is greater than or equal to DEFAULT_DATE_PREMPTION
        defaultProduitShouldBeFound("datePremption.greaterThanOrEqual=" + DEFAULT_DATE_PREMPTION);

        // Get all the produitList where datePremption is greater than or equal to UPDATED_DATE_PREMPTION
        defaultProduitShouldNotBeFound("datePremption.greaterThanOrEqual=" + UPDATED_DATE_PREMPTION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDatePremptionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where datePremption is less than or equal to DEFAULT_DATE_PREMPTION
        defaultProduitShouldBeFound("datePremption.lessThanOrEqual=" + DEFAULT_DATE_PREMPTION);

        // Get all the produitList where datePremption is less than or equal to SMALLER_DATE_PREMPTION
        defaultProduitShouldNotBeFound("datePremption.lessThanOrEqual=" + SMALLER_DATE_PREMPTION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDatePremptionIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where datePremption is less than DEFAULT_DATE_PREMPTION
        defaultProduitShouldNotBeFound("datePremption.lessThan=" + DEFAULT_DATE_PREMPTION);

        // Get all the produitList where datePremption is less than UPDATED_DATE_PREMPTION
        defaultProduitShouldBeFound("datePremption.lessThan=" + UPDATED_DATE_PREMPTION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDatePremptionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where datePremption is greater than DEFAULT_DATE_PREMPTION
        defaultProduitShouldNotBeFound("datePremption.greaterThan=" + DEFAULT_DATE_PREMPTION);

        // Get all the produitList where datePremption is greater than SMALLER_DATE_PREMPTION
        defaultProduitShouldBeFound("datePremption.greaterThan=" + SMALLER_DATE_PREMPTION);
    }


    @Test
    @Transactional
    public void getAllProduitsByPrixHTIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixHT equals to DEFAULT_PRIX_HT
        defaultProduitShouldBeFound("prixHT.equals=" + DEFAULT_PRIX_HT);

        // Get all the produitList where prixHT equals to UPDATED_PRIX_HT
        defaultProduitShouldNotBeFound("prixHT.equals=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixHTIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixHT not equals to DEFAULT_PRIX_HT
        defaultProduitShouldNotBeFound("prixHT.notEquals=" + DEFAULT_PRIX_HT);

        // Get all the produitList where prixHT not equals to UPDATED_PRIX_HT
        defaultProduitShouldBeFound("prixHT.notEquals=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixHTIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixHT in DEFAULT_PRIX_HT or UPDATED_PRIX_HT
        defaultProduitShouldBeFound("prixHT.in=" + DEFAULT_PRIX_HT + "," + UPDATED_PRIX_HT);

        // Get all the produitList where prixHT equals to UPDATED_PRIX_HT
        defaultProduitShouldNotBeFound("prixHT.in=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixHTIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixHT is not null
        defaultProduitShouldBeFound("prixHT.specified=true");

        // Get all the produitList where prixHT is null
        defaultProduitShouldNotBeFound("prixHT.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixHTIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixHT is greater than or equal to DEFAULT_PRIX_HT
        defaultProduitShouldBeFound("prixHT.greaterThanOrEqual=" + DEFAULT_PRIX_HT);

        // Get all the produitList where prixHT is greater than or equal to UPDATED_PRIX_HT
        defaultProduitShouldNotBeFound("prixHT.greaterThanOrEqual=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixHTIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixHT is less than or equal to DEFAULT_PRIX_HT
        defaultProduitShouldBeFound("prixHT.lessThanOrEqual=" + DEFAULT_PRIX_HT);

        // Get all the produitList where prixHT is less than or equal to SMALLER_PRIX_HT
        defaultProduitShouldNotBeFound("prixHT.lessThanOrEqual=" + SMALLER_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixHTIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixHT is less than DEFAULT_PRIX_HT
        defaultProduitShouldNotBeFound("prixHT.lessThan=" + DEFAULT_PRIX_HT);

        // Get all the produitList where prixHT is less than UPDATED_PRIX_HT
        defaultProduitShouldBeFound("prixHT.lessThan=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixHTIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixHT is greater than DEFAULT_PRIX_HT
        defaultProduitShouldNotBeFound("prixHT.greaterThan=" + DEFAULT_PRIX_HT);

        // Get all the produitList where prixHT is greater than SMALLER_PRIX_HT
        defaultProduitShouldBeFound("prixHT.greaterThan=" + SMALLER_PRIX_HT);
    }


    @Test
    @Transactional
    public void getAllProduitsByTauxTVAIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where tauxTVA equals to DEFAULT_TAUX_TVA
        defaultProduitShouldBeFound("tauxTVA.equals=" + DEFAULT_TAUX_TVA);

        // Get all the produitList where tauxTVA equals to UPDATED_TAUX_TVA
        defaultProduitShouldNotBeFound("tauxTVA.equals=" + UPDATED_TAUX_TVA);
    }

    @Test
    @Transactional
    public void getAllProduitsByTauxTVAIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where tauxTVA not equals to DEFAULT_TAUX_TVA
        defaultProduitShouldNotBeFound("tauxTVA.notEquals=" + DEFAULT_TAUX_TVA);

        // Get all the produitList where tauxTVA not equals to UPDATED_TAUX_TVA
        defaultProduitShouldBeFound("tauxTVA.notEquals=" + UPDATED_TAUX_TVA);
    }

    @Test
    @Transactional
    public void getAllProduitsByTauxTVAIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where tauxTVA in DEFAULT_TAUX_TVA or UPDATED_TAUX_TVA
        defaultProduitShouldBeFound("tauxTVA.in=" + DEFAULT_TAUX_TVA + "," + UPDATED_TAUX_TVA);

        // Get all the produitList where tauxTVA equals to UPDATED_TAUX_TVA
        defaultProduitShouldNotBeFound("tauxTVA.in=" + UPDATED_TAUX_TVA);
    }

    @Test
    @Transactional
    public void getAllProduitsByTauxTVAIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where tauxTVA is not null
        defaultProduitShouldBeFound("tauxTVA.specified=true");

        // Get all the produitList where tauxTVA is null
        defaultProduitShouldNotBeFound("tauxTVA.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByTauxTVAIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where tauxTVA is greater than or equal to DEFAULT_TAUX_TVA
        defaultProduitShouldBeFound("tauxTVA.greaterThanOrEqual=" + DEFAULT_TAUX_TVA);

        // Get all the produitList where tauxTVA is greater than or equal to UPDATED_TAUX_TVA
        defaultProduitShouldNotBeFound("tauxTVA.greaterThanOrEqual=" + UPDATED_TAUX_TVA);
    }

    @Test
    @Transactional
    public void getAllProduitsByTauxTVAIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where tauxTVA is less than or equal to DEFAULT_TAUX_TVA
        defaultProduitShouldBeFound("tauxTVA.lessThanOrEqual=" + DEFAULT_TAUX_TVA);

        // Get all the produitList where tauxTVA is less than or equal to SMALLER_TAUX_TVA
        defaultProduitShouldNotBeFound("tauxTVA.lessThanOrEqual=" + SMALLER_TAUX_TVA);
    }

    @Test
    @Transactional
    public void getAllProduitsByTauxTVAIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where tauxTVA is less than DEFAULT_TAUX_TVA
        defaultProduitShouldNotBeFound("tauxTVA.lessThan=" + DEFAULT_TAUX_TVA);

        // Get all the produitList where tauxTVA is less than UPDATED_TAUX_TVA
        defaultProduitShouldBeFound("tauxTVA.lessThan=" + UPDATED_TAUX_TVA);
    }

    @Test
    @Transactional
    public void getAllProduitsByTauxTVAIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where tauxTVA is greater than DEFAULT_TAUX_TVA
        defaultProduitShouldNotBeFound("tauxTVA.greaterThan=" + DEFAULT_TAUX_TVA);

        // Get all the produitList where tauxTVA is greater than SMALLER_TAUX_TVA
        defaultProduitShouldBeFound("tauxTVA.greaterThan=" + SMALLER_TAUX_TVA);
    }


    @Test
    @Transactional
    public void getAllProduitsByPrixTTCIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixTTC equals to DEFAULT_PRIX_TTC
        defaultProduitShouldBeFound("prixTTC.equals=" + DEFAULT_PRIX_TTC);

        // Get all the produitList where prixTTC equals to UPDATED_PRIX_TTC
        defaultProduitShouldNotBeFound("prixTTC.equals=" + UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixTTCIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixTTC not equals to DEFAULT_PRIX_TTC
        defaultProduitShouldNotBeFound("prixTTC.notEquals=" + DEFAULT_PRIX_TTC);

        // Get all the produitList where prixTTC not equals to UPDATED_PRIX_TTC
        defaultProduitShouldBeFound("prixTTC.notEquals=" + UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixTTCIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixTTC in DEFAULT_PRIX_TTC or UPDATED_PRIX_TTC
        defaultProduitShouldBeFound("prixTTC.in=" + DEFAULT_PRIX_TTC + "," + UPDATED_PRIX_TTC);

        // Get all the produitList where prixTTC equals to UPDATED_PRIX_TTC
        defaultProduitShouldNotBeFound("prixTTC.in=" + UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixTTCIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixTTC is not null
        defaultProduitShouldBeFound("prixTTC.specified=true");

        // Get all the produitList where prixTTC is null
        defaultProduitShouldNotBeFound("prixTTC.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixTTCIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixTTC is greater than or equal to DEFAULT_PRIX_TTC
        defaultProduitShouldBeFound("prixTTC.greaterThanOrEqual=" + DEFAULT_PRIX_TTC);

        // Get all the produitList where prixTTC is greater than or equal to UPDATED_PRIX_TTC
        defaultProduitShouldNotBeFound("prixTTC.greaterThanOrEqual=" + UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixTTCIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixTTC is less than or equal to DEFAULT_PRIX_TTC
        defaultProduitShouldBeFound("prixTTC.lessThanOrEqual=" + DEFAULT_PRIX_TTC);

        // Get all the produitList where prixTTC is less than or equal to SMALLER_PRIX_TTC
        defaultProduitShouldNotBeFound("prixTTC.lessThanOrEqual=" + SMALLER_PRIX_TTC);
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixTTCIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixTTC is less than DEFAULT_PRIX_TTC
        defaultProduitShouldNotBeFound("prixTTC.lessThan=" + DEFAULT_PRIX_TTC);

        // Get all the produitList where prixTTC is less than UPDATED_PRIX_TTC
        defaultProduitShouldBeFound("prixTTC.lessThan=" + UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void getAllProduitsByPrixTTCIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixTTC is greater than DEFAULT_PRIX_TTC
        defaultProduitShouldNotBeFound("prixTTC.greaterThan=" + DEFAULT_PRIX_TTC);

        // Get all the produitList where prixTTC is greater than SMALLER_PRIX_TTC
        defaultProduitShouldBeFound("prixTTC.greaterThan=" + SMALLER_PRIX_TTC);
    }


    @Test
    @Transactional
    public void getAllProduitsByDeviseIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where devise equals to DEFAULT_DEVISE
        defaultProduitShouldBeFound("devise.equals=" + DEFAULT_DEVISE);

        // Get all the produitList where devise equals to UPDATED_DEVISE
        defaultProduitShouldNotBeFound("devise.equals=" + UPDATED_DEVISE);
    }

    @Test
    @Transactional
    public void getAllProduitsByDeviseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where devise not equals to DEFAULT_DEVISE
        defaultProduitShouldNotBeFound("devise.notEquals=" + DEFAULT_DEVISE);

        // Get all the produitList where devise not equals to UPDATED_DEVISE
        defaultProduitShouldBeFound("devise.notEquals=" + UPDATED_DEVISE);
    }

    @Test
    @Transactional
    public void getAllProduitsByDeviseIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where devise in DEFAULT_DEVISE or UPDATED_DEVISE
        defaultProduitShouldBeFound("devise.in=" + DEFAULT_DEVISE + "," + UPDATED_DEVISE);

        // Get all the produitList where devise equals to UPDATED_DEVISE
        defaultProduitShouldNotBeFound("devise.in=" + UPDATED_DEVISE);
    }

    @Test
    @Transactional
    public void getAllProduitsByDeviseIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where devise is not null
        defaultProduitShouldBeFound("devise.specified=true");

        // Get all the produitList where devise is null
        defaultProduitShouldNotBeFound("devise.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsBySourceProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where sourceProduit equals to DEFAULT_SOURCE_PRODUIT
        defaultProduitShouldBeFound("sourceProduit.equals=" + DEFAULT_SOURCE_PRODUIT);

        // Get all the produitList where sourceProduit equals to UPDATED_SOURCE_PRODUIT
        defaultProduitShouldNotBeFound("sourceProduit.equals=" + UPDATED_SOURCE_PRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsBySourceProduitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where sourceProduit not equals to DEFAULT_SOURCE_PRODUIT
        defaultProduitShouldNotBeFound("sourceProduit.notEquals=" + DEFAULT_SOURCE_PRODUIT);

        // Get all the produitList where sourceProduit not equals to UPDATED_SOURCE_PRODUIT
        defaultProduitShouldBeFound("sourceProduit.notEquals=" + UPDATED_SOURCE_PRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsBySourceProduitIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where sourceProduit in DEFAULT_SOURCE_PRODUIT or UPDATED_SOURCE_PRODUIT
        defaultProduitShouldBeFound("sourceProduit.in=" + DEFAULT_SOURCE_PRODUIT + "," + UPDATED_SOURCE_PRODUIT);

        // Get all the produitList where sourceProduit equals to UPDATED_SOURCE_PRODUIT
        defaultProduitShouldNotBeFound("sourceProduit.in=" + UPDATED_SOURCE_PRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsBySourceProduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where sourceProduit is not null
        defaultProduitShouldBeFound("sourceProduit.specified=true");

        // Get all the produitList where sourceProduit is null
        defaultProduitShouldNotBeFound("sourceProduit.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where rating equals to DEFAULT_RATING
        defaultProduitShouldBeFound("rating.equals=" + DEFAULT_RATING);

        // Get all the produitList where rating equals to UPDATED_RATING
        defaultProduitShouldNotBeFound("rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllProduitsByRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where rating not equals to DEFAULT_RATING
        defaultProduitShouldNotBeFound("rating.notEquals=" + DEFAULT_RATING);

        // Get all the produitList where rating not equals to UPDATED_RATING
        defaultProduitShouldBeFound("rating.notEquals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllProduitsByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where rating in DEFAULT_RATING or UPDATED_RATING
        defaultProduitShouldBeFound("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING);

        // Get all the produitList where rating equals to UPDATED_RATING
        defaultProduitShouldNotBeFound("rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllProduitsByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where rating is not null
        defaultProduitShouldBeFound("rating.specified=true");

        // Get all the produitList where rating is null
        defaultProduitShouldNotBeFound("rating.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByRatingContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where rating contains DEFAULT_RATING
        defaultProduitShouldBeFound("rating.contains=" + DEFAULT_RATING);

        // Get all the produitList where rating contains UPDATED_RATING
        defaultProduitShouldNotBeFound("rating.contains=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllProduitsByRatingNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where rating does not contain DEFAULT_RATING
        defaultProduitShouldNotBeFound("rating.doesNotContain=" + DEFAULT_RATING);

        // Get all the produitList where rating does not contain UPDATED_RATING
        defaultProduitShouldBeFound("rating.doesNotContain=" + UPDATED_RATING);
    }


    @Test
    @Transactional
    public void getAllProduitsByEligibleRemiseIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where eligibleRemise equals to DEFAULT_ELIGIBLE_REMISE
        defaultProduitShouldBeFound("eligibleRemise.equals=" + DEFAULT_ELIGIBLE_REMISE);

        // Get all the produitList where eligibleRemise equals to UPDATED_ELIGIBLE_REMISE
        defaultProduitShouldNotBeFound("eligibleRemise.equals=" + UPDATED_ELIGIBLE_REMISE);
    }

    @Test
    @Transactional
    public void getAllProduitsByEligibleRemiseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where eligibleRemise not equals to DEFAULT_ELIGIBLE_REMISE
        defaultProduitShouldNotBeFound("eligibleRemise.notEquals=" + DEFAULT_ELIGIBLE_REMISE);

        // Get all the produitList where eligibleRemise not equals to UPDATED_ELIGIBLE_REMISE
        defaultProduitShouldBeFound("eligibleRemise.notEquals=" + UPDATED_ELIGIBLE_REMISE);
    }

    @Test
    @Transactional
    public void getAllProduitsByEligibleRemiseIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where eligibleRemise in DEFAULT_ELIGIBLE_REMISE or UPDATED_ELIGIBLE_REMISE
        defaultProduitShouldBeFound("eligibleRemise.in=" + DEFAULT_ELIGIBLE_REMISE + "," + UPDATED_ELIGIBLE_REMISE);

        // Get all the produitList where eligibleRemise equals to UPDATED_ELIGIBLE_REMISE
        defaultProduitShouldNotBeFound("eligibleRemise.in=" + UPDATED_ELIGIBLE_REMISE);
    }

    @Test
    @Transactional
    public void getAllProduitsByEligibleRemiseIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where eligibleRemise is not null
        defaultProduitShouldBeFound("eligibleRemise.specified=true");

        // Get all the produitList where eligibleRemise is null
        defaultProduitShouldNotBeFound("eligibleRemise.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByRemiseIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where remise equals to DEFAULT_REMISE
        defaultProduitShouldBeFound("remise.equals=" + DEFAULT_REMISE);

        // Get all the produitList where remise equals to UPDATED_REMISE
        defaultProduitShouldNotBeFound("remise.equals=" + UPDATED_REMISE);
    }

    @Test
    @Transactional
    public void getAllProduitsByRemiseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where remise not equals to DEFAULT_REMISE
        defaultProduitShouldNotBeFound("remise.notEquals=" + DEFAULT_REMISE);

        // Get all the produitList where remise not equals to UPDATED_REMISE
        defaultProduitShouldBeFound("remise.notEquals=" + UPDATED_REMISE);
    }

    @Test
    @Transactional
    public void getAllProduitsByRemiseIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where remise in DEFAULT_REMISE or UPDATED_REMISE
        defaultProduitShouldBeFound("remise.in=" + DEFAULT_REMISE + "," + UPDATED_REMISE);

        // Get all the produitList where remise equals to UPDATED_REMISE
        defaultProduitShouldNotBeFound("remise.in=" + UPDATED_REMISE);
    }

    @Test
    @Transactional
    public void getAllProduitsByRemiseIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where remise is not null
        defaultProduitShouldBeFound("remise.specified=true");

        // Get all the produitList where remise is null
        defaultProduitShouldNotBeFound("remise.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByRemiseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where remise is greater than or equal to DEFAULT_REMISE
        defaultProduitShouldBeFound("remise.greaterThanOrEqual=" + DEFAULT_REMISE);

        // Get all the produitList where remise is greater than or equal to UPDATED_REMISE
        defaultProduitShouldNotBeFound("remise.greaterThanOrEqual=" + UPDATED_REMISE);
    }

    @Test
    @Transactional
    public void getAllProduitsByRemiseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where remise is less than or equal to DEFAULT_REMISE
        defaultProduitShouldBeFound("remise.lessThanOrEqual=" + DEFAULT_REMISE);

        // Get all the produitList where remise is less than or equal to SMALLER_REMISE
        defaultProduitShouldNotBeFound("remise.lessThanOrEqual=" + SMALLER_REMISE);
    }

    @Test
    @Transactional
    public void getAllProduitsByRemiseIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where remise is less than DEFAULT_REMISE
        defaultProduitShouldNotBeFound("remise.lessThan=" + DEFAULT_REMISE);

        // Get all the produitList where remise is less than UPDATED_REMISE
        defaultProduitShouldBeFound("remise.lessThan=" + UPDATED_REMISE);
    }

    @Test
    @Transactional
    public void getAllProduitsByRemiseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where remise is greater than DEFAULT_REMISE
        defaultProduitShouldNotBeFound("remise.greaterThan=" + DEFAULT_REMISE);

        // Get all the produitList where remise is greater than SMALLER_REMISE
        defaultProduitShouldBeFound("remise.greaterThan=" + SMALLER_REMISE);
    }


    @Test
    @Transactional
    public void getAllProduitsByDebutPromoIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where debutPromo equals to DEFAULT_DEBUT_PROMO
        defaultProduitShouldBeFound("debutPromo.equals=" + DEFAULT_DEBUT_PROMO);

        // Get all the produitList where debutPromo equals to UPDATED_DEBUT_PROMO
        defaultProduitShouldNotBeFound("debutPromo.equals=" + UPDATED_DEBUT_PROMO);
    }

    @Test
    @Transactional
    public void getAllProduitsByDebutPromoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where debutPromo not equals to DEFAULT_DEBUT_PROMO
        defaultProduitShouldNotBeFound("debutPromo.notEquals=" + DEFAULT_DEBUT_PROMO);

        // Get all the produitList where debutPromo not equals to UPDATED_DEBUT_PROMO
        defaultProduitShouldBeFound("debutPromo.notEquals=" + UPDATED_DEBUT_PROMO);
    }

    @Test
    @Transactional
    public void getAllProduitsByDebutPromoIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where debutPromo in DEFAULT_DEBUT_PROMO or UPDATED_DEBUT_PROMO
        defaultProduitShouldBeFound("debutPromo.in=" + DEFAULT_DEBUT_PROMO + "," + UPDATED_DEBUT_PROMO);

        // Get all the produitList where debutPromo equals to UPDATED_DEBUT_PROMO
        defaultProduitShouldNotBeFound("debutPromo.in=" + UPDATED_DEBUT_PROMO);
    }

    @Test
    @Transactional
    public void getAllProduitsByDebutPromoIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where debutPromo is not null
        defaultProduitShouldBeFound("debutPromo.specified=true");

        // Get all the produitList where debutPromo is null
        defaultProduitShouldNotBeFound("debutPromo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByDebutPromoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where debutPromo is greater than or equal to DEFAULT_DEBUT_PROMO
        defaultProduitShouldBeFound("debutPromo.greaterThanOrEqual=" + DEFAULT_DEBUT_PROMO);

        // Get all the produitList where debutPromo is greater than or equal to UPDATED_DEBUT_PROMO
        defaultProduitShouldNotBeFound("debutPromo.greaterThanOrEqual=" + UPDATED_DEBUT_PROMO);
    }

    @Test
    @Transactional
    public void getAllProduitsByDebutPromoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where debutPromo is less than or equal to DEFAULT_DEBUT_PROMO
        defaultProduitShouldBeFound("debutPromo.lessThanOrEqual=" + DEFAULT_DEBUT_PROMO);

        // Get all the produitList where debutPromo is less than or equal to SMALLER_DEBUT_PROMO
        defaultProduitShouldNotBeFound("debutPromo.lessThanOrEqual=" + SMALLER_DEBUT_PROMO);
    }

    @Test
    @Transactional
    public void getAllProduitsByDebutPromoIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where debutPromo is less than DEFAULT_DEBUT_PROMO
        defaultProduitShouldNotBeFound("debutPromo.lessThan=" + DEFAULT_DEBUT_PROMO);

        // Get all the produitList where debutPromo is less than UPDATED_DEBUT_PROMO
        defaultProduitShouldBeFound("debutPromo.lessThan=" + UPDATED_DEBUT_PROMO);
    }

    @Test
    @Transactional
    public void getAllProduitsByDebutPromoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where debutPromo is greater than DEFAULT_DEBUT_PROMO
        defaultProduitShouldNotBeFound("debutPromo.greaterThan=" + DEFAULT_DEBUT_PROMO);

        // Get all the produitList where debutPromo is greater than SMALLER_DEBUT_PROMO
        defaultProduitShouldBeFound("debutPromo.greaterThan=" + SMALLER_DEBUT_PROMO);
    }


    @Test
    @Transactional
    public void getAllProduitsByFinPromoIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where finPromo equals to DEFAULT_FIN_PROMO
        defaultProduitShouldBeFound("finPromo.equals=" + DEFAULT_FIN_PROMO);

        // Get all the produitList where finPromo equals to UPDATED_FIN_PROMO
        defaultProduitShouldNotBeFound("finPromo.equals=" + UPDATED_FIN_PROMO);
    }

    @Test
    @Transactional
    public void getAllProduitsByFinPromoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where finPromo not equals to DEFAULT_FIN_PROMO
        defaultProduitShouldNotBeFound("finPromo.notEquals=" + DEFAULT_FIN_PROMO);

        // Get all the produitList where finPromo not equals to UPDATED_FIN_PROMO
        defaultProduitShouldBeFound("finPromo.notEquals=" + UPDATED_FIN_PROMO);
    }

    @Test
    @Transactional
    public void getAllProduitsByFinPromoIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where finPromo in DEFAULT_FIN_PROMO or UPDATED_FIN_PROMO
        defaultProduitShouldBeFound("finPromo.in=" + DEFAULT_FIN_PROMO + "," + UPDATED_FIN_PROMO);

        // Get all the produitList where finPromo equals to UPDATED_FIN_PROMO
        defaultProduitShouldNotBeFound("finPromo.in=" + UPDATED_FIN_PROMO);
    }

    @Test
    @Transactional
    public void getAllProduitsByFinPromoIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where finPromo is not null
        defaultProduitShouldBeFound("finPromo.specified=true");

        // Get all the produitList where finPromo is null
        defaultProduitShouldNotBeFound("finPromo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByFinPromoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where finPromo is greater than or equal to DEFAULT_FIN_PROMO
        defaultProduitShouldBeFound("finPromo.greaterThanOrEqual=" + DEFAULT_FIN_PROMO);

        // Get all the produitList where finPromo is greater than or equal to UPDATED_FIN_PROMO
        defaultProduitShouldNotBeFound("finPromo.greaterThanOrEqual=" + UPDATED_FIN_PROMO);
    }

    @Test
    @Transactional
    public void getAllProduitsByFinPromoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where finPromo is less than or equal to DEFAULT_FIN_PROMO
        defaultProduitShouldBeFound("finPromo.lessThanOrEqual=" + DEFAULT_FIN_PROMO);

        // Get all the produitList where finPromo is less than or equal to SMALLER_FIN_PROMO
        defaultProduitShouldNotBeFound("finPromo.lessThanOrEqual=" + SMALLER_FIN_PROMO);
    }

    @Test
    @Transactional
    public void getAllProduitsByFinPromoIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where finPromo is less than DEFAULT_FIN_PROMO
        defaultProduitShouldNotBeFound("finPromo.lessThan=" + DEFAULT_FIN_PROMO);

        // Get all the produitList where finPromo is less than UPDATED_FIN_PROMO
        defaultProduitShouldBeFound("finPromo.lessThan=" + UPDATED_FIN_PROMO);
    }

    @Test
    @Transactional
    public void getAllProduitsByFinPromoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where finPromo is greater than DEFAULT_FIN_PROMO
        defaultProduitShouldNotBeFound("finPromo.greaterThan=" + DEFAULT_FIN_PROMO);

        // Get all the produitList where finPromo is greater than SMALLER_FIN_PROMO
        defaultProduitShouldBeFound("finPromo.greaterThan=" + SMALLER_FIN_PROMO);
    }


    @Test
    @Transactional
    public void getAllProduitsByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creeLe equals to DEFAULT_CREE_LE
        defaultProduitShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the produitList where creeLe equals to UPDATED_CREE_LE
        defaultProduitShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllProduitsByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creeLe not equals to DEFAULT_CREE_LE
        defaultProduitShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the produitList where creeLe not equals to UPDATED_CREE_LE
        defaultProduitShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllProduitsByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultProduitShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the produitList where creeLe equals to UPDATED_CREE_LE
        defaultProduitShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllProduitsByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creeLe is not null
        defaultProduitShouldBeFound("creeLe.specified=true");

        // Get all the produitList where creeLe is null
        defaultProduitShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultProduitShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the produitList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultProduitShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllProduitsByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultProduitShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the produitList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultProduitShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllProduitsByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creeLe is less than DEFAULT_CREE_LE
        defaultProduitShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the produitList where creeLe is less than UPDATED_CREE_LE
        defaultProduitShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllProduitsByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creeLe is greater than DEFAULT_CREE_LE
        defaultProduitShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the produitList where creeLe is greater than SMALLER_CREE_LE
        defaultProduitShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllProduitsByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creePar equals to DEFAULT_CREE_PAR
        defaultProduitShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the produitList where creePar equals to UPDATED_CREE_PAR
        defaultProduitShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllProduitsByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creePar not equals to DEFAULT_CREE_PAR
        defaultProduitShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the produitList where creePar not equals to UPDATED_CREE_PAR
        defaultProduitShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllProduitsByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultProduitShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the produitList where creePar equals to UPDATED_CREE_PAR
        defaultProduitShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllProduitsByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creePar is not null
        defaultProduitShouldBeFound("creePar.specified=true");

        // Get all the produitList where creePar is null
        defaultProduitShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByCreeParContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creePar contains DEFAULT_CREE_PAR
        defaultProduitShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the produitList where creePar contains UPDATED_CREE_PAR
        defaultProduitShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllProduitsByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where creePar does not contain DEFAULT_CREE_PAR
        defaultProduitShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the produitList where creePar does not contain UPDATED_CREE_PAR
        defaultProduitShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllProduitsByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultProduitShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the produitList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultProduitShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllProduitsByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultProduitShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the produitList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultProduitShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllProduitsByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultProduitShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the produitList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultProduitShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllProduitsByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifieLe is not null
        defaultProduitShouldBeFound("modifieLe.specified=true");

        // Get all the produitList where modifieLe is null
        defaultProduitShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultProduitShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the produitList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultProduitShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllProduitsByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultProduitShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the produitList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultProduitShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllProduitsByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultProduitShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the produitList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultProduitShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllProduitsByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultProduitShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the produitList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultProduitShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllProduitsByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultProduitShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the produitList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultProduitShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllProduitsByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultProduitShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the produitList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultProduitShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllProduitsByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultProduitShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the produitList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultProduitShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllProduitsByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifiePar is not null
        defaultProduitShouldBeFound("modifiePar.specified=true");

        // Get all the produitList where modifiePar is null
        defaultProduitShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByModifieParContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultProduitShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the produitList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultProduitShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllProduitsByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultProduitShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the produitList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultProduitShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllProduitsByEnVedetteIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enVedette equals to DEFAULT_EN_VEDETTE
        defaultProduitShouldBeFound("enVedette.equals=" + DEFAULT_EN_VEDETTE);

        // Get all the produitList where enVedette equals to UPDATED_EN_VEDETTE
        defaultProduitShouldNotBeFound("enVedette.equals=" + UPDATED_EN_VEDETTE);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnVedetteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enVedette not equals to DEFAULT_EN_VEDETTE
        defaultProduitShouldNotBeFound("enVedette.notEquals=" + DEFAULT_EN_VEDETTE);

        // Get all the produitList where enVedette not equals to UPDATED_EN_VEDETTE
        defaultProduitShouldBeFound("enVedette.notEquals=" + UPDATED_EN_VEDETTE);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnVedetteIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enVedette in DEFAULT_EN_VEDETTE or UPDATED_EN_VEDETTE
        defaultProduitShouldBeFound("enVedette.in=" + DEFAULT_EN_VEDETTE + "," + UPDATED_EN_VEDETTE);

        // Get all the produitList where enVedette equals to UPDATED_EN_VEDETTE
        defaultProduitShouldNotBeFound("enVedette.in=" + UPDATED_EN_VEDETTE);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnVedetteIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enVedette is not null
        defaultProduitShouldBeFound("enVedette.specified=true");

        // Get all the produitList where enVedette is null
        defaultProduitShouldNotBeFound("enVedette.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultProduitShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the produitList where imageUrl equals to UPDATED_IMAGE_URL
        defaultProduitShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProduitsByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultProduitShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the produitList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultProduitShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProduitsByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultProduitShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the produitList where imageUrl equals to UPDATED_IMAGE_URL
        defaultProduitShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProduitsByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where imageUrl is not null
        defaultProduitShouldBeFound("imageUrl.specified=true");

        // Get all the produitList where imageUrl is null
        defaultProduitShouldNotBeFound("imageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where imageUrl contains DEFAULT_IMAGE_URL
        defaultProduitShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the produitList where imageUrl contains UPDATED_IMAGE_URL
        defaultProduitShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProduitsByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultProduitShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the produitList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultProduitShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllProduitsByStockIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);
        Stock stock = StockResourceIT.createEntity(em);
        em.persist(stock);
        em.flush();
        produit.addStock(stock);
        produitRepository.saveAndFlush(produit);
        Long stockId = stock.getId();

        // Get all the produitList where stock equals to stockId
        defaultProduitShouldBeFound("stockId.equals=" + stockId);

        // Get all the produitList where stock equals to stockId + 1
        defaultProduitShouldNotBeFound("stockId.equals=" + (stockId + 1));
    }


    @Test
    @Transactional
    public void getAllProduitsByMouvementStockIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);
        MouvementStock mouvementStock = MouvementStockResourceIT.createEntity(em);
        em.persist(mouvementStock);
        em.flush();
        produit.addMouvementStock(mouvementStock);
        produitRepository.saveAndFlush(produit);
        Long mouvementStockId = mouvementStock.getId();

        // Get all the produitList where mouvementStock equals to mouvementStockId
        defaultProduitShouldBeFound("mouvementStockId.equals=" + mouvementStockId);

        // Get all the produitList where mouvementStock equals to mouvementStockId + 1
        defaultProduitShouldNotBeFound("mouvementStockId.equals=" + (mouvementStockId + 1));
    }


    @Test
    @Transactional
    public void getAllProduitsByCommandeLignesIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);
        CommandeLignes commandeLignes = CommandeLignesResourceIT.createEntity(em);
        em.persist(commandeLignes);
        em.flush();
        produit.addCommandeLignes(commandeLignes);
        produitRepository.saveAndFlush(produit);
        Long commandeLignesId = commandeLignes.getId();

        // Get all the produitList where commandeLignes equals to commandeLignesId
        defaultProduitShouldBeFound("commandeLignesId.equals=" + commandeLignesId);

        // Get all the produitList where commandeLignes equals to commandeLignesId + 1
        defaultProduitShouldNotBeFound("commandeLignesId.equals=" + (commandeLignesId + 1));
    }


    @Test
    @Transactional
    public void getAllProduitsByCategorieIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);
        Categorie categorie = CategorieResourceIT.createEntity(em);
        em.persist(categorie);
        em.flush();
        produit.setCategorie(categorie);
        produitRepository.saveAndFlush(produit);
        Long categorieId = categorie.getId();

        // Get all the produitList where categorie equals to categorieId
        defaultProduitShouldBeFound("categorieId.equals=" + categorieId);

        // Get all the produitList where categorie equals to categorieId + 1
        defaultProduitShouldNotBeFound("categorieId.equals=" + (categorieId + 1));
    }


    @Test
    @Transactional
    public void getAllProduitsBySousCategorieIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);
        SousCategorie sousCategorie = SousCategorieResourceIT.createEntity(em);
        em.persist(sousCategorie);
        em.flush();
        produit.setSousCategorie(sousCategorie);
        produitRepository.saveAndFlush(produit);
        Long sousCategorieId = sousCategorie.getId();

        // Get all the produitList where sousCategorie equals to sousCategorieId
        defaultProduitShouldBeFound("sousCategorieId.equals=" + sousCategorieId);

        // Get all the produitList where sousCategorie equals to sousCategorieId + 1
        defaultProduitShouldNotBeFound("sousCategorieId.equals=" + (sousCategorieId + 1));
    }


    @Test
    @Transactional
    public void getAllProduitsByUniteIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);
        ProduitUnite unite = ProduitUniteResourceIT.createEntity(em);
        em.persist(unite);
        em.flush();
        produit.setUnite(unite);
        produitRepository.saveAndFlush(produit);
        Long uniteId = unite.getId();

        // Get all the produitList where unite equals to uniteId
        defaultProduitShouldBeFound("uniteId.equals=" + uniteId);

        // Get all the produitList where unite equals to uniteId + 1
        defaultProduitShouldNotBeFound("uniteId.equals=" + (uniteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProduitShouldBeFound(String filter) throws Exception {
        restProduitMockMvc.perform(get("/api/produits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].codeBarre").value(hasItem(DEFAULT_CODE_BARRE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE)))
            .andExpect(jsonPath("$.[*].stockMinimum").value(hasItem(DEFAULT_STOCK_MINIMUM.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteVenteMax").value(hasItem(DEFAULT_QUANTITE_VENTE_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].horsStock").value(hasItem(DEFAULT_HORS_STOCK.booleanValue())))
            .andExpect(jsonPath("$.[*].typeService").value(hasItem(DEFAULT_TYPE_SERVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].datePremption").value(hasItem(DEFAULT_DATE_PREMPTION.toString())))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].tauxTVA").value(hasItem(DEFAULT_TAUX_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())))
            .andExpect(jsonPath("$.[*].sourceProduit").value(hasItem(DEFAULT_SOURCE_PRODUIT.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].eligibleRemise").value(hasItem(DEFAULT_ELIGIBLE_REMISE.booleanValue())))
            .andExpect(jsonPath("$.[*].remise").value(hasItem(DEFAULT_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].debutPromo").value(hasItem(DEFAULT_DEBUT_PROMO.toString())))
            .andExpect(jsonPath("$.[*].finPromo").value(hasItem(DEFAULT_FIN_PROMO.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].enVedette").value(hasItem(DEFAULT_EN_VEDETTE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));

        // Check, that the count call also returns 1
        restProduitMockMvc.perform(get("/api/produits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProduitShouldNotBeFound(String filter) throws Exception {
        restProduitMockMvc.perform(get("/api/produits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProduitMockMvc.perform(get("/api/produits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProduit() throws Exception {
        // Get the produit
        restProduitMockMvc.perform(get("/api/produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit
        Produit updatedProduit = produitRepository.findById(produit.getId()).get();
        // Disconnect from session so that the updates on updatedProduit are not directly saved in db
        em.detach(updatedProduit);
        updatedProduit
            .reference(UPDATED_REFERENCE)
            .nom(UPDATED_NOM)
            .codeBarre(UPDATED_CODE_BARRE)
            .description(UPDATED_DESCRIPTION)
            .etat(UPDATED_ETAT)
            .marque(UPDATED_MARQUE)
            .nature(UPDATED_NATURE)
            .stockMinimum(UPDATED_STOCK_MINIMUM)
            .quantiteVenteMax(UPDATED_QUANTITE_VENTE_MAX)
            .horsStock(UPDATED_HORS_STOCK)
            .typeService(UPDATED_TYPE_SERVICE)
            .datePremption(UPDATED_DATE_PREMPTION)
            .prixHT(UPDATED_PRIX_HT)
            .tauxTVA(UPDATED_TAUX_TVA)
            .prixTTC(UPDATED_PRIX_TTC)
            .devise(UPDATED_DEVISE)
            .sourceProduit(UPDATED_SOURCE_PRODUIT)
            .rating(UPDATED_RATING)
            .eligibleRemise(UPDATED_ELIGIBLE_REMISE)
            .remise(UPDATED_REMISE)
            .debutPromo(UPDATED_DEBUT_PROMO)
            .finPromo(UPDATED_FIN_PROMO)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR)
            .enVedette(UPDATED_EN_VEDETTE)
            .imageUrl(UPDATED_IMAGE_URL);
        ProduitDTO produitDTO = produitMapper.toDto(updatedProduit);

        restProduitMockMvc.perform(put("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testProduit.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProduit.getCodeBarre()).isEqualTo(UPDATED_CODE_BARRE);
        assertThat(testProduit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduit.isEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testProduit.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testProduit.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testProduit.getStockMinimum()).isEqualTo(UPDATED_STOCK_MINIMUM);
        assertThat(testProduit.getQuantiteVenteMax()).isEqualTo(UPDATED_QUANTITE_VENTE_MAX);
        assertThat(testProduit.isHorsStock()).isEqualTo(UPDATED_HORS_STOCK);
        assertThat(testProduit.isTypeService()).isEqualTo(UPDATED_TYPE_SERVICE);
        assertThat(testProduit.getDatePremption()).isEqualTo(UPDATED_DATE_PREMPTION);
        assertThat(testProduit.getPrixHT()).isEqualTo(UPDATED_PRIX_HT);
        assertThat(testProduit.getTauxTVA()).isEqualTo(UPDATED_TAUX_TVA);
        assertThat(testProduit.getPrixTTC()).isEqualTo(UPDATED_PRIX_TTC);
        assertThat(testProduit.getDevise()).isEqualTo(UPDATED_DEVISE);
        assertThat(testProduit.getSourceProduit()).isEqualTo(UPDATED_SOURCE_PRODUIT);
        assertThat(testProduit.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testProduit.isEligibleRemise()).isEqualTo(UPDATED_ELIGIBLE_REMISE);
        assertThat(testProduit.getRemise()).isEqualTo(UPDATED_REMISE);
        assertThat(testProduit.getDebutPromo()).isEqualTo(UPDATED_DEBUT_PROMO);
        assertThat(testProduit.getFinPromo()).isEqualTo(UPDATED_FIN_PROMO);
        assertThat(testProduit.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProduit.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testProduit.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testProduit.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testProduit.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testProduit.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);
        assertThat(testProduit.isEnVedette()).isEqualTo(UPDATED_EN_VEDETTE);
        assertThat(testProduit.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);

        // Validate the Produit in Elasticsearch
        verify(mockProduitSearchRepository, times(1)).save(testProduit);
    }

    @Test
    @Transactional
    public void updateNonExistingProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc.perform(put("/api/produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Produit in Elasticsearch
        verify(mockProduitSearchRepository, times(0)).save(produit);
    }

    @Test
    @Transactional
    public void deleteProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeDelete = produitRepository.findAll().size();

        // Delete the produit
        restProduitMockMvc.perform(delete("/api/produits/{id}", produit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Produit in Elasticsearch
        verify(mockProduitSearchRepository, times(1)).deleteById(produit.getId());
    }

    @Test
    @Transactional
    public void searchProduit() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        produitRepository.saveAndFlush(produit);
        when(mockProduitSearchRepository.search(queryStringQuery("id:" + produit.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(produit), PageRequest.of(0, 1), 1));

        // Search the produit
        restProduitMockMvc.perform(get("/api/_search/produits?query=id:" + produit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].codeBarre").value(hasItem(DEFAULT_CODE_BARRE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE)))
            .andExpect(jsonPath("$.[*].stockMinimum").value(hasItem(DEFAULT_STOCK_MINIMUM.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteVenteMax").value(hasItem(DEFAULT_QUANTITE_VENTE_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].horsStock").value(hasItem(DEFAULT_HORS_STOCK.booleanValue())))
            .andExpect(jsonPath("$.[*].typeService").value(hasItem(DEFAULT_TYPE_SERVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].datePremption").value(hasItem(DEFAULT_DATE_PREMPTION.toString())))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].tauxTVA").value(hasItem(DEFAULT_TAUX_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())))
            .andExpect(jsonPath("$.[*].sourceProduit").value(hasItem(DEFAULT_SOURCE_PRODUIT.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].eligibleRemise").value(hasItem(DEFAULT_ELIGIBLE_REMISE.booleanValue())))
            .andExpect(jsonPath("$.[*].remise").value(hasItem(DEFAULT_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].debutPromo").value(hasItem(DEFAULT_DEBUT_PROMO.toString())))
            .andExpect(jsonPath("$.[*].finPromo").value(hasItem(DEFAULT_FIN_PROMO.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].enVedette").value(hasItem(DEFAULT_EN_VEDETTE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }
}
