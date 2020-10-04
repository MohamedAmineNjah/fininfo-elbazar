package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.domain.MouvementStock;
import org.fininfo.elbazar.domain.CommandeLignes;
import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.domain.Adresse;
import org.fininfo.elbazar.domain.Zone;
import org.fininfo.elbazar.repository.CommandeRepository;
import org.fininfo.elbazar.repository.search.CommandeSearchRepository;
import org.fininfo.elbazar.service.CommandeService;
import org.fininfo.elbazar.service.dto.CommandeDTO;
import org.fininfo.elbazar.service.mapper.CommandeMapper;
import org.fininfo.elbazar.service.dto.CommandeCriteria;
import org.fininfo.elbazar.service.CommandeQueryService;

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

import org.fininfo.elbazar.domain.enumeration.StatCmd;
import org.fininfo.elbazar.domain.enumeration.Origine;
import org.fininfo.elbazar.domain.enumeration.Devise;
import org.fininfo.elbazar.domain.enumeration.RegMod;
/**
 * Integration tests for the {@link CommandeResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommandeResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final StatCmd DEFAULT_STATUT = StatCmd.Reservee;
    private static final StatCmd UPDATED_STATUT = StatCmd.Commandee;

    private static final Origine DEFAULT_ORIGINE = Origine.Client;
    private static final Origine UPDATED_ORIGINE = Origine.Admin;

    private static final Double DEFAULT_TOTAL_HT = 1D;
    private static final Double UPDATED_TOTAL_HT = 2D;
    private static final Double SMALLER_TOTAL_HT = 1D - 1D;

    private static final Double DEFAULT_TOTAL_TVA = 1D;
    private static final Double UPDATED_TOTAL_TVA = 2D;
    private static final Double SMALLER_TOTAL_TVA = 1D - 1D;

    private static final Double DEFAULT_TOTAL_REMISE = 1D;
    private static final Double UPDATED_TOTAL_REMISE = 2D;
    private static final Double SMALLER_TOTAL_REMISE = 1D - 1D;

    private static final Double DEFAULT_TOT_TTC = 1D;
    private static final Double UPDATED_TOT_TTC = 2D;
    private static final Double SMALLER_TOT_TTC = 1D - 1D;

    private static final Devise DEFAULT_DEVISE = Devise.TND;
    private static final Devise UPDATED_DEVISE = Devise.EUR;

    private static final Integer DEFAULT_POINTS_FIDELITE = 1;
    private static final Integer UPDATED_POINTS_FIDELITE = 2;
    private static final Integer SMALLER_POINTS_FIDELITE = 1 - 1;

    private static final RegMod DEFAULT_REGLEMENT = RegMod.CarteBancaire;
    private static final RegMod UPDATED_REGLEMENT = RegMod.Cash;

    private static final LocalDate DEFAULT_DATE_LIVRAISON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LIVRAISON = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_LIVRAISON = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_CREATION = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_ANNULATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ANNULATION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_ANNULATION = LocalDate.ofEpochDay(-1L);

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

    private static final Integer DEFAULT_CODE_POSTAL = 1;
    private static final Integer UPDATED_CODE_POSTAL = 2;
    private static final Integer SMALLER_CODE_POSTAL = 1 - 1;

    private static final String DEFAULT_INDICATION = "AAAAAAAAAA";
    private static final String UPDATED_INDICATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEPHONE = 1;
    private static final Integer UPDATED_TELEPHONE = 2;
    private static final Integer SMALLER_TELEPHONE = 1 - 1;

    private static final Integer DEFAULT_MOBILE = 1;
    private static final Integer UPDATED_MOBILE = 2;
    private static final Integer SMALLER_MOBILE = 1 - 1;

    private static final Double DEFAULT_FRAIS_LIVRAISON = 1D;
    private static final Double UPDATED_FRAIS_LIVRAISON = 2D;
    private static final Double SMALLER_FRAIS_LIVRAISON = 1D - 1D;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private CommandeMapper commandeMapper;

    @Autowired
    private CommandeService commandeService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.CommandeSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommandeSearchRepository mockCommandeSearchRepository;

    @Autowired
    private CommandeQueryService commandeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandeMockMvc;

    private Commande commande;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commande createEntity(EntityManager em) {
        Commande commande = new Commande()
            .reference(DEFAULT_REFERENCE)
            .date(DEFAULT_DATE)
            .statut(DEFAULT_STATUT)
            .origine(DEFAULT_ORIGINE)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totalRemise(DEFAULT_TOTAL_REMISE)
            .totTTC(DEFAULT_TOT_TTC)
            .devise(DEFAULT_DEVISE)
            .pointsFidelite(DEFAULT_POINTS_FIDELITE)
            .reglement(DEFAULT_REGLEMENT)
            .dateLivraison(DEFAULT_DATE_LIVRAISON)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateAnnulation(DEFAULT_DATE_ANNULATION)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR)
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .gouvernorat(DEFAULT_GOUVERNORAT)
            .ville(DEFAULT_VILLE)
            .localite(DEFAULT_LOCALITE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .indication(DEFAULT_INDICATION)
            .telephone(DEFAULT_TELEPHONE)
            .mobile(DEFAULT_MOBILE)
            .fraisLivraison(DEFAULT_FRAIS_LIVRAISON);
        return commande;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commande createUpdatedEntity(EntityManager em) {
        Commande commande = new Commande()
            .reference(UPDATED_REFERENCE)
            .date(UPDATED_DATE)
            .statut(UPDATED_STATUT)
            .origine(UPDATED_ORIGINE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totalRemise(UPDATED_TOTAL_REMISE)
            .totTTC(UPDATED_TOT_TTC)
            .devise(UPDATED_DEVISE)
            .pointsFidelite(UPDATED_POINTS_FIDELITE)
            .reglement(UPDATED_REGLEMENT)
            .dateLivraison(UPDATED_DATE_LIVRAISON)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateAnnulation(UPDATED_DATE_ANNULATION)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .gouvernorat(UPDATED_GOUVERNORAT)
            .ville(UPDATED_VILLE)
            .localite(UPDATED_LOCALITE)
            .codePostal(UPDATED_CODE_POSTAL)
            .indication(UPDATED_INDICATION)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .fraisLivraison(UPDATED_FRAIS_LIVRAISON);
        return commande;
    }

    @BeforeEach
    public void initTest() {
        commande = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommande() throws Exception {
        int databaseSizeBeforeCreate = commandeRepository.findAll().size();
        // Create the Commande
        CommandeDTO commandeDTO = commandeMapper.toDto(commande);
        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeDTO)))
            .andExpect(status().isCreated());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeCreate + 1);
        Commande testCommande = commandeList.get(commandeList.size() - 1);
        assertThat(testCommande.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testCommande.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCommande.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testCommande.getOrigine()).isEqualTo(DEFAULT_ORIGINE);
        assertThat(testCommande.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testCommande.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testCommande.getTotalRemise()).isEqualTo(DEFAULT_TOTAL_REMISE);
        assertThat(testCommande.getTotTTC()).isEqualTo(DEFAULT_TOT_TTC);
        assertThat(testCommande.getDevise()).isEqualTo(DEFAULT_DEVISE);
        assertThat(testCommande.getPointsFidelite()).isEqualTo(DEFAULT_POINTS_FIDELITE);
        assertThat(testCommande.getReglement()).isEqualTo(DEFAULT_REGLEMENT);
        assertThat(testCommande.getDateLivraison()).isEqualTo(DEFAULT_DATE_LIVRAISON);
        assertThat(testCommande.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testCommande.getDateAnnulation()).isEqualTo(DEFAULT_DATE_ANNULATION);
        assertThat(testCommande.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testCommande.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testCommande.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testCommande.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);
        assertThat(testCommande.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testCommande.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCommande.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testCommande.getGouvernorat()).isEqualTo(DEFAULT_GOUVERNORAT);
        assertThat(testCommande.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testCommande.getLocalite()).isEqualTo(DEFAULT_LOCALITE);
        assertThat(testCommande.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testCommande.getIndication()).isEqualTo(DEFAULT_INDICATION);
        assertThat(testCommande.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testCommande.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testCommande.getFraisLivraison()).isEqualTo(DEFAULT_FRAIS_LIVRAISON);

        // Validate the Commande in Elasticsearch
        verify(mockCommandeSearchRepository, times(1)).save(testCommande);
    }

    @Test
    @Transactional
    public void createCommandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeRepository.findAll().size();

        // Create the Commande with an existing ID
        commande.setId(1L);
        CommandeDTO commandeDTO = commandeMapper.toDto(commande);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Commande in Elasticsearch
        verify(mockCommandeSearchRepository, times(0)).save(commande);
    }


    @Test
    @Transactional
    public void getAllCommandes() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList
        restCommandeMockMvc.perform(get("/api/commandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commande.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE.toString())))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].totalRemise").value(hasItem(DEFAULT_TOTAL_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].totTTC").value(hasItem(DEFAULT_TOT_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())))
            .andExpect(jsonPath("$.[*].pointsFidelite").value(hasItem(DEFAULT_POINTS_FIDELITE)))
            .andExpect(jsonPath("$.[*].reglement").value(hasItem(DEFAULT_REGLEMENT.toString())))
            .andExpect(jsonPath("$.[*].dateLivraison").value(hasItem(DEFAULT_DATE_LIVRAISON.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateAnnulation").value(hasItem(DEFAULT_DATE_ANNULATION.toString())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].fraisLivraison").value(hasItem(DEFAULT_FRAIS_LIVRAISON.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCommande() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get the commande
        restCommandeMockMvc.perform(get("/api/commandes/{id}", commande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commande.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.origine").value(DEFAULT_ORIGINE.toString()))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.doubleValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.doubleValue()))
            .andExpect(jsonPath("$.totalRemise").value(DEFAULT_TOTAL_REMISE.doubleValue()))
            .andExpect(jsonPath("$.totTTC").value(DEFAULT_TOT_TTC.doubleValue()))
            .andExpect(jsonPath("$.devise").value(DEFAULT_DEVISE.toString()))
            .andExpect(jsonPath("$.pointsFidelite").value(DEFAULT_POINTS_FIDELITE))
            .andExpect(jsonPath("$.reglement").value(DEFAULT_REGLEMENT.toString()))
            .andExpect(jsonPath("$.dateLivraison").value(DEFAULT_DATE_LIVRAISON.toString()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateAnnulation").value(DEFAULT_DATE_ANNULATION.toString()))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.gouvernorat").value(DEFAULT_GOUVERNORAT))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.localite").value(DEFAULT_LOCALITE))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.indication").value(DEFAULT_INDICATION))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.fraisLivraison").value(DEFAULT_FRAIS_LIVRAISON.doubleValue()));
    }


    @Test
    @Transactional
    public void getCommandesByIdFiltering() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        Long id = commande.getId();

        defaultCommandeShouldBeFound("id.equals=" + id);
        defaultCommandeShouldNotBeFound("id.notEquals=" + id);

        defaultCommandeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommandeShouldNotBeFound("id.greaterThan=" + id);

        defaultCommandeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommandeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommandesByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where reference equals to DEFAULT_REFERENCE
        defaultCommandeShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the commandeList where reference equals to UPDATED_REFERENCE
        defaultCommandeShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllCommandesByReferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where reference not equals to DEFAULT_REFERENCE
        defaultCommandeShouldNotBeFound("reference.notEquals=" + DEFAULT_REFERENCE);

        // Get all the commandeList where reference not equals to UPDATED_REFERENCE
        defaultCommandeShouldBeFound("reference.notEquals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllCommandesByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultCommandeShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the commandeList where reference equals to UPDATED_REFERENCE
        defaultCommandeShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllCommandesByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where reference is not null
        defaultCommandeShouldBeFound("reference.specified=true");

        // Get all the commandeList where reference is null
        defaultCommandeShouldNotBeFound("reference.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandesByReferenceContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where reference contains DEFAULT_REFERENCE
        defaultCommandeShouldBeFound("reference.contains=" + DEFAULT_REFERENCE);

        // Get all the commandeList where reference contains UPDATED_REFERENCE
        defaultCommandeShouldNotBeFound("reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllCommandesByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where reference does not contain DEFAULT_REFERENCE
        defaultCommandeShouldNotBeFound("reference.doesNotContain=" + DEFAULT_REFERENCE);

        // Get all the commandeList where reference does not contain UPDATED_REFERENCE
        defaultCommandeShouldBeFound("reference.doesNotContain=" + UPDATED_REFERENCE);
    }


    @Test
    @Transactional
    public void getAllCommandesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where date equals to DEFAULT_DATE
        defaultCommandeShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the commandeList where date equals to UPDATED_DATE
        defaultCommandeShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where date not equals to DEFAULT_DATE
        defaultCommandeShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the commandeList where date not equals to UPDATED_DATE
        defaultCommandeShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where date in DEFAULT_DATE or UPDATED_DATE
        defaultCommandeShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the commandeList where date equals to UPDATED_DATE
        defaultCommandeShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where date is not null
        defaultCommandeShouldBeFound("date.specified=true");

        // Get all the commandeList where date is null
        defaultCommandeShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where date is greater than or equal to DEFAULT_DATE
        defaultCommandeShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the commandeList where date is greater than or equal to UPDATED_DATE
        defaultCommandeShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where date is less than or equal to DEFAULT_DATE
        defaultCommandeShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the commandeList where date is less than or equal to SMALLER_DATE
        defaultCommandeShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where date is less than DEFAULT_DATE
        defaultCommandeShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the commandeList where date is less than UPDATED_DATE
        defaultCommandeShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where date is greater than DEFAULT_DATE
        defaultCommandeShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the commandeList where date is greater than SMALLER_DATE
        defaultCommandeShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllCommandesByStatutIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where statut equals to DEFAULT_STATUT
        defaultCommandeShouldBeFound("statut.equals=" + DEFAULT_STATUT);

        // Get all the commandeList where statut equals to UPDATED_STATUT
        defaultCommandeShouldNotBeFound("statut.equals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllCommandesByStatutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where statut not equals to DEFAULT_STATUT
        defaultCommandeShouldNotBeFound("statut.notEquals=" + DEFAULT_STATUT);

        // Get all the commandeList where statut not equals to UPDATED_STATUT
        defaultCommandeShouldBeFound("statut.notEquals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllCommandesByStatutIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where statut in DEFAULT_STATUT or UPDATED_STATUT
        defaultCommandeShouldBeFound("statut.in=" + DEFAULT_STATUT + "," + UPDATED_STATUT);

        // Get all the commandeList where statut equals to UPDATED_STATUT
        defaultCommandeShouldNotBeFound("statut.in=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllCommandesByStatutIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where statut is not null
        defaultCommandeShouldBeFound("statut.specified=true");

        // Get all the commandeList where statut is null
        defaultCommandeShouldNotBeFound("statut.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByOrigineIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where origine equals to DEFAULT_ORIGINE
        defaultCommandeShouldBeFound("origine.equals=" + DEFAULT_ORIGINE);

        // Get all the commandeList where origine equals to UPDATED_ORIGINE
        defaultCommandeShouldNotBeFound("origine.equals=" + UPDATED_ORIGINE);
    }

    @Test
    @Transactional
    public void getAllCommandesByOrigineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where origine not equals to DEFAULT_ORIGINE
        defaultCommandeShouldNotBeFound("origine.notEquals=" + DEFAULT_ORIGINE);

        // Get all the commandeList where origine not equals to UPDATED_ORIGINE
        defaultCommandeShouldBeFound("origine.notEquals=" + UPDATED_ORIGINE);
    }

    @Test
    @Transactional
    public void getAllCommandesByOrigineIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where origine in DEFAULT_ORIGINE or UPDATED_ORIGINE
        defaultCommandeShouldBeFound("origine.in=" + DEFAULT_ORIGINE + "," + UPDATED_ORIGINE);

        // Get all the commandeList where origine equals to UPDATED_ORIGINE
        defaultCommandeShouldNotBeFound("origine.in=" + UPDATED_ORIGINE);
    }

    @Test
    @Transactional
    public void getAllCommandesByOrigineIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where origine is not null
        defaultCommandeShouldBeFound("origine.specified=true");

        // Get all the commandeList where origine is null
        defaultCommandeShouldNotBeFound("origine.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalHTIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalHT equals to DEFAULT_TOTAL_HT
        defaultCommandeShouldBeFound("totalHT.equals=" + DEFAULT_TOTAL_HT);

        // Get all the commandeList where totalHT equals to UPDATED_TOTAL_HT
        defaultCommandeShouldNotBeFound("totalHT.equals=" + UPDATED_TOTAL_HT);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalHTIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalHT not equals to DEFAULT_TOTAL_HT
        defaultCommandeShouldNotBeFound("totalHT.notEquals=" + DEFAULT_TOTAL_HT);

        // Get all the commandeList where totalHT not equals to UPDATED_TOTAL_HT
        defaultCommandeShouldBeFound("totalHT.notEquals=" + UPDATED_TOTAL_HT);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalHTIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalHT in DEFAULT_TOTAL_HT or UPDATED_TOTAL_HT
        defaultCommandeShouldBeFound("totalHT.in=" + DEFAULT_TOTAL_HT + "," + UPDATED_TOTAL_HT);

        // Get all the commandeList where totalHT equals to UPDATED_TOTAL_HT
        defaultCommandeShouldNotBeFound("totalHT.in=" + UPDATED_TOTAL_HT);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalHTIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalHT is not null
        defaultCommandeShouldBeFound("totalHT.specified=true");

        // Get all the commandeList where totalHT is null
        defaultCommandeShouldNotBeFound("totalHT.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalHTIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalHT is greater than or equal to DEFAULT_TOTAL_HT
        defaultCommandeShouldBeFound("totalHT.greaterThanOrEqual=" + DEFAULT_TOTAL_HT);

        // Get all the commandeList where totalHT is greater than or equal to UPDATED_TOTAL_HT
        defaultCommandeShouldNotBeFound("totalHT.greaterThanOrEqual=" + UPDATED_TOTAL_HT);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalHTIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalHT is less than or equal to DEFAULT_TOTAL_HT
        defaultCommandeShouldBeFound("totalHT.lessThanOrEqual=" + DEFAULT_TOTAL_HT);

        // Get all the commandeList where totalHT is less than or equal to SMALLER_TOTAL_HT
        defaultCommandeShouldNotBeFound("totalHT.lessThanOrEqual=" + SMALLER_TOTAL_HT);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalHTIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalHT is less than DEFAULT_TOTAL_HT
        defaultCommandeShouldNotBeFound("totalHT.lessThan=" + DEFAULT_TOTAL_HT);

        // Get all the commandeList where totalHT is less than UPDATED_TOTAL_HT
        defaultCommandeShouldBeFound("totalHT.lessThan=" + UPDATED_TOTAL_HT);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalHTIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalHT is greater than DEFAULT_TOTAL_HT
        defaultCommandeShouldNotBeFound("totalHT.greaterThan=" + DEFAULT_TOTAL_HT);

        // Get all the commandeList where totalHT is greater than SMALLER_TOTAL_HT
        defaultCommandeShouldBeFound("totalHT.greaterThan=" + SMALLER_TOTAL_HT);
    }


    @Test
    @Transactional
    public void getAllCommandesByTotalTVAIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalTVA equals to DEFAULT_TOTAL_TVA
        defaultCommandeShouldBeFound("totalTVA.equals=" + DEFAULT_TOTAL_TVA);

        // Get all the commandeList where totalTVA equals to UPDATED_TOTAL_TVA
        defaultCommandeShouldNotBeFound("totalTVA.equals=" + UPDATED_TOTAL_TVA);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalTVAIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalTVA not equals to DEFAULT_TOTAL_TVA
        defaultCommandeShouldNotBeFound("totalTVA.notEquals=" + DEFAULT_TOTAL_TVA);

        // Get all the commandeList where totalTVA not equals to UPDATED_TOTAL_TVA
        defaultCommandeShouldBeFound("totalTVA.notEquals=" + UPDATED_TOTAL_TVA);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalTVAIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalTVA in DEFAULT_TOTAL_TVA or UPDATED_TOTAL_TVA
        defaultCommandeShouldBeFound("totalTVA.in=" + DEFAULT_TOTAL_TVA + "," + UPDATED_TOTAL_TVA);

        // Get all the commandeList where totalTVA equals to UPDATED_TOTAL_TVA
        defaultCommandeShouldNotBeFound("totalTVA.in=" + UPDATED_TOTAL_TVA);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalTVAIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalTVA is not null
        defaultCommandeShouldBeFound("totalTVA.specified=true");

        // Get all the commandeList where totalTVA is null
        defaultCommandeShouldNotBeFound("totalTVA.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalTVAIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalTVA is greater than or equal to DEFAULT_TOTAL_TVA
        defaultCommandeShouldBeFound("totalTVA.greaterThanOrEqual=" + DEFAULT_TOTAL_TVA);

        // Get all the commandeList where totalTVA is greater than or equal to UPDATED_TOTAL_TVA
        defaultCommandeShouldNotBeFound("totalTVA.greaterThanOrEqual=" + UPDATED_TOTAL_TVA);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalTVAIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalTVA is less than or equal to DEFAULT_TOTAL_TVA
        defaultCommandeShouldBeFound("totalTVA.lessThanOrEqual=" + DEFAULT_TOTAL_TVA);

        // Get all the commandeList where totalTVA is less than or equal to SMALLER_TOTAL_TVA
        defaultCommandeShouldNotBeFound("totalTVA.lessThanOrEqual=" + SMALLER_TOTAL_TVA);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalTVAIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalTVA is less than DEFAULT_TOTAL_TVA
        defaultCommandeShouldNotBeFound("totalTVA.lessThan=" + DEFAULT_TOTAL_TVA);

        // Get all the commandeList where totalTVA is less than UPDATED_TOTAL_TVA
        defaultCommandeShouldBeFound("totalTVA.lessThan=" + UPDATED_TOTAL_TVA);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalTVAIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalTVA is greater than DEFAULT_TOTAL_TVA
        defaultCommandeShouldNotBeFound("totalTVA.greaterThan=" + DEFAULT_TOTAL_TVA);

        // Get all the commandeList where totalTVA is greater than SMALLER_TOTAL_TVA
        defaultCommandeShouldBeFound("totalTVA.greaterThan=" + SMALLER_TOTAL_TVA);
    }


    @Test
    @Transactional
    public void getAllCommandesByTotalRemiseIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalRemise equals to DEFAULT_TOTAL_REMISE
        defaultCommandeShouldBeFound("totalRemise.equals=" + DEFAULT_TOTAL_REMISE);

        // Get all the commandeList where totalRemise equals to UPDATED_TOTAL_REMISE
        defaultCommandeShouldNotBeFound("totalRemise.equals=" + UPDATED_TOTAL_REMISE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalRemiseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalRemise not equals to DEFAULT_TOTAL_REMISE
        defaultCommandeShouldNotBeFound("totalRemise.notEquals=" + DEFAULT_TOTAL_REMISE);

        // Get all the commandeList where totalRemise not equals to UPDATED_TOTAL_REMISE
        defaultCommandeShouldBeFound("totalRemise.notEquals=" + UPDATED_TOTAL_REMISE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalRemiseIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalRemise in DEFAULT_TOTAL_REMISE or UPDATED_TOTAL_REMISE
        defaultCommandeShouldBeFound("totalRemise.in=" + DEFAULT_TOTAL_REMISE + "," + UPDATED_TOTAL_REMISE);

        // Get all the commandeList where totalRemise equals to UPDATED_TOTAL_REMISE
        defaultCommandeShouldNotBeFound("totalRemise.in=" + UPDATED_TOTAL_REMISE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalRemiseIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalRemise is not null
        defaultCommandeShouldBeFound("totalRemise.specified=true");

        // Get all the commandeList where totalRemise is null
        defaultCommandeShouldNotBeFound("totalRemise.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalRemiseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalRemise is greater than or equal to DEFAULT_TOTAL_REMISE
        defaultCommandeShouldBeFound("totalRemise.greaterThanOrEqual=" + DEFAULT_TOTAL_REMISE);

        // Get all the commandeList where totalRemise is greater than or equal to UPDATED_TOTAL_REMISE
        defaultCommandeShouldNotBeFound("totalRemise.greaterThanOrEqual=" + UPDATED_TOTAL_REMISE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalRemiseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalRemise is less than or equal to DEFAULT_TOTAL_REMISE
        defaultCommandeShouldBeFound("totalRemise.lessThanOrEqual=" + DEFAULT_TOTAL_REMISE);

        // Get all the commandeList where totalRemise is less than or equal to SMALLER_TOTAL_REMISE
        defaultCommandeShouldNotBeFound("totalRemise.lessThanOrEqual=" + SMALLER_TOTAL_REMISE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalRemiseIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalRemise is less than DEFAULT_TOTAL_REMISE
        defaultCommandeShouldNotBeFound("totalRemise.lessThan=" + DEFAULT_TOTAL_REMISE);

        // Get all the commandeList where totalRemise is less than UPDATED_TOTAL_REMISE
        defaultCommandeShouldBeFound("totalRemise.lessThan=" + UPDATED_TOTAL_REMISE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotalRemiseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totalRemise is greater than DEFAULT_TOTAL_REMISE
        defaultCommandeShouldNotBeFound("totalRemise.greaterThan=" + DEFAULT_TOTAL_REMISE);

        // Get all the commandeList where totalRemise is greater than SMALLER_TOTAL_REMISE
        defaultCommandeShouldBeFound("totalRemise.greaterThan=" + SMALLER_TOTAL_REMISE);
    }


    @Test
    @Transactional
    public void getAllCommandesByTotTTCIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totTTC equals to DEFAULT_TOT_TTC
        defaultCommandeShouldBeFound("totTTC.equals=" + DEFAULT_TOT_TTC);

        // Get all the commandeList where totTTC equals to UPDATED_TOT_TTC
        defaultCommandeShouldNotBeFound("totTTC.equals=" + UPDATED_TOT_TTC);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotTTCIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totTTC not equals to DEFAULT_TOT_TTC
        defaultCommandeShouldNotBeFound("totTTC.notEquals=" + DEFAULT_TOT_TTC);

        // Get all the commandeList where totTTC not equals to UPDATED_TOT_TTC
        defaultCommandeShouldBeFound("totTTC.notEquals=" + UPDATED_TOT_TTC);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotTTCIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totTTC in DEFAULT_TOT_TTC or UPDATED_TOT_TTC
        defaultCommandeShouldBeFound("totTTC.in=" + DEFAULT_TOT_TTC + "," + UPDATED_TOT_TTC);

        // Get all the commandeList where totTTC equals to UPDATED_TOT_TTC
        defaultCommandeShouldNotBeFound("totTTC.in=" + UPDATED_TOT_TTC);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotTTCIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totTTC is not null
        defaultCommandeShouldBeFound("totTTC.specified=true");

        // Get all the commandeList where totTTC is null
        defaultCommandeShouldNotBeFound("totTTC.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByTotTTCIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totTTC is greater than or equal to DEFAULT_TOT_TTC
        defaultCommandeShouldBeFound("totTTC.greaterThanOrEqual=" + DEFAULT_TOT_TTC);

        // Get all the commandeList where totTTC is greater than or equal to UPDATED_TOT_TTC
        defaultCommandeShouldNotBeFound("totTTC.greaterThanOrEqual=" + UPDATED_TOT_TTC);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotTTCIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totTTC is less than or equal to DEFAULT_TOT_TTC
        defaultCommandeShouldBeFound("totTTC.lessThanOrEqual=" + DEFAULT_TOT_TTC);

        // Get all the commandeList where totTTC is less than or equal to SMALLER_TOT_TTC
        defaultCommandeShouldNotBeFound("totTTC.lessThanOrEqual=" + SMALLER_TOT_TTC);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotTTCIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totTTC is less than DEFAULT_TOT_TTC
        defaultCommandeShouldNotBeFound("totTTC.lessThan=" + DEFAULT_TOT_TTC);

        // Get all the commandeList where totTTC is less than UPDATED_TOT_TTC
        defaultCommandeShouldBeFound("totTTC.lessThan=" + UPDATED_TOT_TTC);
    }

    @Test
    @Transactional
    public void getAllCommandesByTotTTCIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where totTTC is greater than DEFAULT_TOT_TTC
        defaultCommandeShouldNotBeFound("totTTC.greaterThan=" + DEFAULT_TOT_TTC);

        // Get all the commandeList where totTTC is greater than SMALLER_TOT_TTC
        defaultCommandeShouldBeFound("totTTC.greaterThan=" + SMALLER_TOT_TTC);
    }


    @Test
    @Transactional
    public void getAllCommandesByDeviseIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where devise equals to DEFAULT_DEVISE
        defaultCommandeShouldBeFound("devise.equals=" + DEFAULT_DEVISE);

        // Get all the commandeList where devise equals to UPDATED_DEVISE
        defaultCommandeShouldNotBeFound("devise.equals=" + UPDATED_DEVISE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDeviseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where devise not equals to DEFAULT_DEVISE
        defaultCommandeShouldNotBeFound("devise.notEquals=" + DEFAULT_DEVISE);

        // Get all the commandeList where devise not equals to UPDATED_DEVISE
        defaultCommandeShouldBeFound("devise.notEquals=" + UPDATED_DEVISE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDeviseIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where devise in DEFAULT_DEVISE or UPDATED_DEVISE
        defaultCommandeShouldBeFound("devise.in=" + DEFAULT_DEVISE + "," + UPDATED_DEVISE);

        // Get all the commandeList where devise equals to UPDATED_DEVISE
        defaultCommandeShouldNotBeFound("devise.in=" + UPDATED_DEVISE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDeviseIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where devise is not null
        defaultCommandeShouldBeFound("devise.specified=true");

        // Get all the commandeList where devise is null
        defaultCommandeShouldNotBeFound("devise.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByPointsFideliteIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where pointsFidelite equals to DEFAULT_POINTS_FIDELITE
        defaultCommandeShouldBeFound("pointsFidelite.equals=" + DEFAULT_POINTS_FIDELITE);

        // Get all the commandeList where pointsFidelite equals to UPDATED_POINTS_FIDELITE
        defaultCommandeShouldNotBeFound("pointsFidelite.equals=" + UPDATED_POINTS_FIDELITE);
    }

    @Test
    @Transactional
    public void getAllCommandesByPointsFideliteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where pointsFidelite not equals to DEFAULT_POINTS_FIDELITE
        defaultCommandeShouldNotBeFound("pointsFidelite.notEquals=" + DEFAULT_POINTS_FIDELITE);

        // Get all the commandeList where pointsFidelite not equals to UPDATED_POINTS_FIDELITE
        defaultCommandeShouldBeFound("pointsFidelite.notEquals=" + UPDATED_POINTS_FIDELITE);
    }

    @Test
    @Transactional
    public void getAllCommandesByPointsFideliteIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where pointsFidelite in DEFAULT_POINTS_FIDELITE or UPDATED_POINTS_FIDELITE
        defaultCommandeShouldBeFound("pointsFidelite.in=" + DEFAULT_POINTS_FIDELITE + "," + UPDATED_POINTS_FIDELITE);

        // Get all the commandeList where pointsFidelite equals to UPDATED_POINTS_FIDELITE
        defaultCommandeShouldNotBeFound("pointsFidelite.in=" + UPDATED_POINTS_FIDELITE);
    }

    @Test
    @Transactional
    public void getAllCommandesByPointsFideliteIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where pointsFidelite is not null
        defaultCommandeShouldBeFound("pointsFidelite.specified=true");

        // Get all the commandeList where pointsFidelite is null
        defaultCommandeShouldNotBeFound("pointsFidelite.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByPointsFideliteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where pointsFidelite is greater than or equal to DEFAULT_POINTS_FIDELITE
        defaultCommandeShouldBeFound("pointsFidelite.greaterThanOrEqual=" + DEFAULT_POINTS_FIDELITE);

        // Get all the commandeList where pointsFidelite is greater than or equal to UPDATED_POINTS_FIDELITE
        defaultCommandeShouldNotBeFound("pointsFidelite.greaterThanOrEqual=" + UPDATED_POINTS_FIDELITE);
    }

    @Test
    @Transactional
    public void getAllCommandesByPointsFideliteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where pointsFidelite is less than or equal to DEFAULT_POINTS_FIDELITE
        defaultCommandeShouldBeFound("pointsFidelite.lessThanOrEqual=" + DEFAULT_POINTS_FIDELITE);

        // Get all the commandeList where pointsFidelite is less than or equal to SMALLER_POINTS_FIDELITE
        defaultCommandeShouldNotBeFound("pointsFidelite.lessThanOrEqual=" + SMALLER_POINTS_FIDELITE);
    }

    @Test
    @Transactional
    public void getAllCommandesByPointsFideliteIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where pointsFidelite is less than DEFAULT_POINTS_FIDELITE
        defaultCommandeShouldNotBeFound("pointsFidelite.lessThan=" + DEFAULT_POINTS_FIDELITE);

        // Get all the commandeList where pointsFidelite is less than UPDATED_POINTS_FIDELITE
        defaultCommandeShouldBeFound("pointsFidelite.lessThan=" + UPDATED_POINTS_FIDELITE);
    }

    @Test
    @Transactional
    public void getAllCommandesByPointsFideliteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where pointsFidelite is greater than DEFAULT_POINTS_FIDELITE
        defaultCommandeShouldNotBeFound("pointsFidelite.greaterThan=" + DEFAULT_POINTS_FIDELITE);

        // Get all the commandeList where pointsFidelite is greater than SMALLER_POINTS_FIDELITE
        defaultCommandeShouldBeFound("pointsFidelite.greaterThan=" + SMALLER_POINTS_FIDELITE);
    }


    @Test
    @Transactional
    public void getAllCommandesByReglementIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where reglement equals to DEFAULT_REGLEMENT
        defaultCommandeShouldBeFound("reglement.equals=" + DEFAULT_REGLEMENT);

        // Get all the commandeList where reglement equals to UPDATED_REGLEMENT
        defaultCommandeShouldNotBeFound("reglement.equals=" + UPDATED_REGLEMENT);
    }

    @Test
    @Transactional
    public void getAllCommandesByReglementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where reglement not equals to DEFAULT_REGLEMENT
        defaultCommandeShouldNotBeFound("reglement.notEquals=" + DEFAULT_REGLEMENT);

        // Get all the commandeList where reglement not equals to UPDATED_REGLEMENT
        defaultCommandeShouldBeFound("reglement.notEquals=" + UPDATED_REGLEMENT);
    }

    @Test
    @Transactional
    public void getAllCommandesByReglementIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where reglement in DEFAULT_REGLEMENT or UPDATED_REGLEMENT
        defaultCommandeShouldBeFound("reglement.in=" + DEFAULT_REGLEMENT + "," + UPDATED_REGLEMENT);

        // Get all the commandeList where reglement equals to UPDATED_REGLEMENT
        defaultCommandeShouldNotBeFound("reglement.in=" + UPDATED_REGLEMENT);
    }

    @Test
    @Transactional
    public void getAllCommandesByReglementIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where reglement is not null
        defaultCommandeShouldBeFound("reglement.specified=true");

        // Get all the commandeList where reglement is null
        defaultCommandeShouldNotBeFound("reglement.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByDateLivraisonIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateLivraison equals to DEFAULT_DATE_LIVRAISON
        defaultCommandeShouldBeFound("dateLivraison.equals=" + DEFAULT_DATE_LIVRAISON);

        // Get all the commandeList where dateLivraison equals to UPDATED_DATE_LIVRAISON
        defaultCommandeShouldNotBeFound("dateLivraison.equals=" + UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateLivraisonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateLivraison not equals to DEFAULT_DATE_LIVRAISON
        defaultCommandeShouldNotBeFound("dateLivraison.notEquals=" + DEFAULT_DATE_LIVRAISON);

        // Get all the commandeList where dateLivraison not equals to UPDATED_DATE_LIVRAISON
        defaultCommandeShouldBeFound("dateLivraison.notEquals=" + UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateLivraisonIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateLivraison in DEFAULT_DATE_LIVRAISON or UPDATED_DATE_LIVRAISON
        defaultCommandeShouldBeFound("dateLivraison.in=" + DEFAULT_DATE_LIVRAISON + "," + UPDATED_DATE_LIVRAISON);

        // Get all the commandeList where dateLivraison equals to UPDATED_DATE_LIVRAISON
        defaultCommandeShouldNotBeFound("dateLivraison.in=" + UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateLivraisonIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateLivraison is not null
        defaultCommandeShouldBeFound("dateLivraison.specified=true");

        // Get all the commandeList where dateLivraison is null
        defaultCommandeShouldNotBeFound("dateLivraison.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByDateLivraisonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateLivraison is greater than or equal to DEFAULT_DATE_LIVRAISON
        defaultCommandeShouldBeFound("dateLivraison.greaterThanOrEqual=" + DEFAULT_DATE_LIVRAISON);

        // Get all the commandeList where dateLivraison is greater than or equal to UPDATED_DATE_LIVRAISON
        defaultCommandeShouldNotBeFound("dateLivraison.greaterThanOrEqual=" + UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateLivraisonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateLivraison is less than or equal to DEFAULT_DATE_LIVRAISON
        defaultCommandeShouldBeFound("dateLivraison.lessThanOrEqual=" + DEFAULT_DATE_LIVRAISON);

        // Get all the commandeList where dateLivraison is less than or equal to SMALLER_DATE_LIVRAISON
        defaultCommandeShouldNotBeFound("dateLivraison.lessThanOrEqual=" + SMALLER_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateLivraisonIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateLivraison is less than DEFAULT_DATE_LIVRAISON
        defaultCommandeShouldNotBeFound("dateLivraison.lessThan=" + DEFAULT_DATE_LIVRAISON);

        // Get all the commandeList where dateLivraison is less than UPDATED_DATE_LIVRAISON
        defaultCommandeShouldBeFound("dateLivraison.lessThan=" + UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateLivraisonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateLivraison is greater than DEFAULT_DATE_LIVRAISON
        defaultCommandeShouldNotBeFound("dateLivraison.greaterThan=" + DEFAULT_DATE_LIVRAISON);

        // Get all the commandeList where dateLivraison is greater than SMALLER_DATE_LIVRAISON
        defaultCommandeShouldBeFound("dateLivraison.greaterThan=" + SMALLER_DATE_LIVRAISON);
    }


    @Test
    @Transactional
    public void getAllCommandesByDateCreationIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateCreation equals to DEFAULT_DATE_CREATION
        defaultCommandeShouldBeFound("dateCreation.equals=" + DEFAULT_DATE_CREATION);

        // Get all the commandeList where dateCreation equals to UPDATED_DATE_CREATION
        defaultCommandeShouldNotBeFound("dateCreation.equals=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateCreationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateCreation not equals to DEFAULT_DATE_CREATION
        defaultCommandeShouldNotBeFound("dateCreation.notEquals=" + DEFAULT_DATE_CREATION);

        // Get all the commandeList where dateCreation not equals to UPDATED_DATE_CREATION
        defaultCommandeShouldBeFound("dateCreation.notEquals=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateCreationIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateCreation in DEFAULT_DATE_CREATION or UPDATED_DATE_CREATION
        defaultCommandeShouldBeFound("dateCreation.in=" + DEFAULT_DATE_CREATION + "," + UPDATED_DATE_CREATION);

        // Get all the commandeList where dateCreation equals to UPDATED_DATE_CREATION
        defaultCommandeShouldNotBeFound("dateCreation.in=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateCreationIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateCreation is not null
        defaultCommandeShouldBeFound("dateCreation.specified=true");

        // Get all the commandeList where dateCreation is null
        defaultCommandeShouldNotBeFound("dateCreation.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByDateCreationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateCreation is greater than or equal to DEFAULT_DATE_CREATION
        defaultCommandeShouldBeFound("dateCreation.greaterThanOrEqual=" + DEFAULT_DATE_CREATION);

        // Get all the commandeList where dateCreation is greater than or equal to UPDATED_DATE_CREATION
        defaultCommandeShouldNotBeFound("dateCreation.greaterThanOrEqual=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateCreationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateCreation is less than or equal to DEFAULT_DATE_CREATION
        defaultCommandeShouldBeFound("dateCreation.lessThanOrEqual=" + DEFAULT_DATE_CREATION);

        // Get all the commandeList where dateCreation is less than or equal to SMALLER_DATE_CREATION
        defaultCommandeShouldNotBeFound("dateCreation.lessThanOrEqual=" + SMALLER_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateCreationIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateCreation is less than DEFAULT_DATE_CREATION
        defaultCommandeShouldNotBeFound("dateCreation.lessThan=" + DEFAULT_DATE_CREATION);

        // Get all the commandeList where dateCreation is less than UPDATED_DATE_CREATION
        defaultCommandeShouldBeFound("dateCreation.lessThan=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateCreationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateCreation is greater than DEFAULT_DATE_CREATION
        defaultCommandeShouldNotBeFound("dateCreation.greaterThan=" + DEFAULT_DATE_CREATION);

        // Get all the commandeList where dateCreation is greater than SMALLER_DATE_CREATION
        defaultCommandeShouldBeFound("dateCreation.greaterThan=" + SMALLER_DATE_CREATION);
    }


    @Test
    @Transactional
    public void getAllCommandesByDateAnnulationIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateAnnulation equals to DEFAULT_DATE_ANNULATION
        defaultCommandeShouldBeFound("dateAnnulation.equals=" + DEFAULT_DATE_ANNULATION);

        // Get all the commandeList where dateAnnulation equals to UPDATED_DATE_ANNULATION
        defaultCommandeShouldNotBeFound("dateAnnulation.equals=" + UPDATED_DATE_ANNULATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateAnnulationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateAnnulation not equals to DEFAULT_DATE_ANNULATION
        defaultCommandeShouldNotBeFound("dateAnnulation.notEquals=" + DEFAULT_DATE_ANNULATION);

        // Get all the commandeList where dateAnnulation not equals to UPDATED_DATE_ANNULATION
        defaultCommandeShouldBeFound("dateAnnulation.notEquals=" + UPDATED_DATE_ANNULATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateAnnulationIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateAnnulation in DEFAULT_DATE_ANNULATION or UPDATED_DATE_ANNULATION
        defaultCommandeShouldBeFound("dateAnnulation.in=" + DEFAULT_DATE_ANNULATION + "," + UPDATED_DATE_ANNULATION);

        // Get all the commandeList where dateAnnulation equals to UPDATED_DATE_ANNULATION
        defaultCommandeShouldNotBeFound("dateAnnulation.in=" + UPDATED_DATE_ANNULATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateAnnulationIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateAnnulation is not null
        defaultCommandeShouldBeFound("dateAnnulation.specified=true");

        // Get all the commandeList where dateAnnulation is null
        defaultCommandeShouldNotBeFound("dateAnnulation.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByDateAnnulationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateAnnulation is greater than or equal to DEFAULT_DATE_ANNULATION
        defaultCommandeShouldBeFound("dateAnnulation.greaterThanOrEqual=" + DEFAULT_DATE_ANNULATION);

        // Get all the commandeList where dateAnnulation is greater than or equal to UPDATED_DATE_ANNULATION
        defaultCommandeShouldNotBeFound("dateAnnulation.greaterThanOrEqual=" + UPDATED_DATE_ANNULATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateAnnulationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateAnnulation is less than or equal to DEFAULT_DATE_ANNULATION
        defaultCommandeShouldBeFound("dateAnnulation.lessThanOrEqual=" + DEFAULT_DATE_ANNULATION);

        // Get all the commandeList where dateAnnulation is less than or equal to SMALLER_DATE_ANNULATION
        defaultCommandeShouldNotBeFound("dateAnnulation.lessThanOrEqual=" + SMALLER_DATE_ANNULATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateAnnulationIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateAnnulation is less than DEFAULT_DATE_ANNULATION
        defaultCommandeShouldNotBeFound("dateAnnulation.lessThan=" + DEFAULT_DATE_ANNULATION);

        // Get all the commandeList where dateAnnulation is less than UPDATED_DATE_ANNULATION
        defaultCommandeShouldBeFound("dateAnnulation.lessThan=" + UPDATED_DATE_ANNULATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateAnnulationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where dateAnnulation is greater than DEFAULT_DATE_ANNULATION
        defaultCommandeShouldNotBeFound("dateAnnulation.greaterThan=" + DEFAULT_DATE_ANNULATION);

        // Get all the commandeList where dateAnnulation is greater than SMALLER_DATE_ANNULATION
        defaultCommandeShouldBeFound("dateAnnulation.greaterThan=" + SMALLER_DATE_ANNULATION);
    }


    @Test
    @Transactional
    public void getAllCommandesByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creeLe equals to DEFAULT_CREE_LE
        defaultCommandeShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the commandeList where creeLe equals to UPDATED_CREE_LE
        defaultCommandeShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandesByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creeLe not equals to DEFAULT_CREE_LE
        defaultCommandeShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the commandeList where creeLe not equals to UPDATED_CREE_LE
        defaultCommandeShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandesByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultCommandeShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the commandeList where creeLe equals to UPDATED_CREE_LE
        defaultCommandeShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandesByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creeLe is not null
        defaultCommandeShouldBeFound("creeLe.specified=true");

        // Get all the commandeList where creeLe is null
        defaultCommandeShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultCommandeShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the commandeList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultCommandeShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandesByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultCommandeShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the commandeList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultCommandeShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandesByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creeLe is less than DEFAULT_CREE_LE
        defaultCommandeShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the commandeList where creeLe is less than UPDATED_CREE_LE
        defaultCommandeShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandesByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creeLe is greater than DEFAULT_CREE_LE
        defaultCommandeShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the commandeList where creeLe is greater than SMALLER_CREE_LE
        defaultCommandeShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllCommandesByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creePar equals to DEFAULT_CREE_PAR
        defaultCommandeShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the commandeList where creePar equals to UPDATED_CREE_PAR
        defaultCommandeShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandesByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creePar not equals to DEFAULT_CREE_PAR
        defaultCommandeShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the commandeList where creePar not equals to UPDATED_CREE_PAR
        defaultCommandeShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandesByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultCommandeShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the commandeList where creePar equals to UPDATED_CREE_PAR
        defaultCommandeShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandesByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creePar is not null
        defaultCommandeShouldBeFound("creePar.specified=true");

        // Get all the commandeList where creePar is null
        defaultCommandeShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandesByCreeParContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creePar contains DEFAULT_CREE_PAR
        defaultCommandeShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the commandeList where creePar contains UPDATED_CREE_PAR
        defaultCommandeShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandesByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where creePar does not contain DEFAULT_CREE_PAR
        defaultCommandeShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the commandeList where creePar does not contain UPDATED_CREE_PAR
        defaultCommandeShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllCommandesByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultCommandeShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the commandeList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultCommandeShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandesByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultCommandeShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the commandeList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultCommandeShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandesByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultCommandeShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the commandeList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultCommandeShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandesByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifieLe is not null
        defaultCommandeShouldBeFound("modifieLe.specified=true");

        // Get all the commandeList where modifieLe is null
        defaultCommandeShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultCommandeShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the commandeList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultCommandeShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandesByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultCommandeShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the commandeList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultCommandeShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandesByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultCommandeShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the commandeList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultCommandeShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandesByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultCommandeShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the commandeList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultCommandeShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllCommandesByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultCommandeShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the commandeList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultCommandeShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandesByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultCommandeShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the commandeList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultCommandeShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandesByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultCommandeShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the commandeList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultCommandeShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandesByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifiePar is not null
        defaultCommandeShouldBeFound("modifiePar.specified=true");

        // Get all the commandeList where modifiePar is null
        defaultCommandeShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandesByModifieParContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultCommandeShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the commandeList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultCommandeShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandesByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultCommandeShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the commandeList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultCommandeShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllCommandesByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where prenom equals to DEFAULT_PRENOM
        defaultCommandeShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the commandeList where prenom equals to UPDATED_PRENOM
        defaultCommandeShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllCommandesByPrenomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where prenom not equals to DEFAULT_PRENOM
        defaultCommandeShouldNotBeFound("prenom.notEquals=" + DEFAULT_PRENOM);

        // Get all the commandeList where prenom not equals to UPDATED_PRENOM
        defaultCommandeShouldBeFound("prenom.notEquals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllCommandesByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultCommandeShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the commandeList where prenom equals to UPDATED_PRENOM
        defaultCommandeShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllCommandesByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where prenom is not null
        defaultCommandeShouldBeFound("prenom.specified=true");

        // Get all the commandeList where prenom is null
        defaultCommandeShouldNotBeFound("prenom.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandesByPrenomContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where prenom contains DEFAULT_PRENOM
        defaultCommandeShouldBeFound("prenom.contains=" + DEFAULT_PRENOM);

        // Get all the commandeList where prenom contains UPDATED_PRENOM
        defaultCommandeShouldNotBeFound("prenom.contains=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllCommandesByPrenomNotContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where prenom does not contain DEFAULT_PRENOM
        defaultCommandeShouldNotBeFound("prenom.doesNotContain=" + DEFAULT_PRENOM);

        // Get all the commandeList where prenom does not contain UPDATED_PRENOM
        defaultCommandeShouldBeFound("prenom.doesNotContain=" + UPDATED_PRENOM);
    }


    @Test
    @Transactional
    public void getAllCommandesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where nom equals to DEFAULT_NOM
        defaultCommandeShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the commandeList where nom equals to UPDATED_NOM
        defaultCommandeShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCommandesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where nom not equals to DEFAULT_NOM
        defaultCommandeShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the commandeList where nom not equals to UPDATED_NOM
        defaultCommandeShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCommandesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultCommandeShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the commandeList where nom equals to UPDATED_NOM
        defaultCommandeShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCommandesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where nom is not null
        defaultCommandeShouldBeFound("nom.specified=true");

        // Get all the commandeList where nom is null
        defaultCommandeShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandesByNomContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where nom contains DEFAULT_NOM
        defaultCommandeShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the commandeList where nom contains UPDATED_NOM
        defaultCommandeShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCommandesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where nom does not contain DEFAULT_NOM
        defaultCommandeShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the commandeList where nom does not contain UPDATED_NOM
        defaultCommandeShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllCommandesByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where adresse equals to DEFAULT_ADRESSE
        defaultCommandeShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the commandeList where adresse equals to UPDATED_ADRESSE
        defaultCommandeShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllCommandesByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where adresse not equals to DEFAULT_ADRESSE
        defaultCommandeShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the commandeList where adresse not equals to UPDATED_ADRESSE
        defaultCommandeShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllCommandesByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultCommandeShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the commandeList where adresse equals to UPDATED_ADRESSE
        defaultCommandeShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllCommandesByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where adresse is not null
        defaultCommandeShouldBeFound("adresse.specified=true");

        // Get all the commandeList where adresse is null
        defaultCommandeShouldNotBeFound("adresse.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandesByAdresseContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where adresse contains DEFAULT_ADRESSE
        defaultCommandeShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the commandeList where adresse contains UPDATED_ADRESSE
        defaultCommandeShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllCommandesByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where adresse does not contain DEFAULT_ADRESSE
        defaultCommandeShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the commandeList where adresse does not contain UPDATED_ADRESSE
        defaultCommandeShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }


    @Test
    @Transactional
    public void getAllCommandesByGouvernoratIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where gouvernorat equals to DEFAULT_GOUVERNORAT
        defaultCommandeShouldBeFound("gouvernorat.equals=" + DEFAULT_GOUVERNORAT);

        // Get all the commandeList where gouvernorat equals to UPDATED_GOUVERNORAT
        defaultCommandeShouldNotBeFound("gouvernorat.equals=" + UPDATED_GOUVERNORAT);
    }

    @Test
    @Transactional
    public void getAllCommandesByGouvernoratIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where gouvernorat not equals to DEFAULT_GOUVERNORAT
        defaultCommandeShouldNotBeFound("gouvernorat.notEquals=" + DEFAULT_GOUVERNORAT);

        // Get all the commandeList where gouvernorat not equals to UPDATED_GOUVERNORAT
        defaultCommandeShouldBeFound("gouvernorat.notEquals=" + UPDATED_GOUVERNORAT);
    }

    @Test
    @Transactional
    public void getAllCommandesByGouvernoratIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where gouvernorat in DEFAULT_GOUVERNORAT or UPDATED_GOUVERNORAT
        defaultCommandeShouldBeFound("gouvernorat.in=" + DEFAULT_GOUVERNORAT + "," + UPDATED_GOUVERNORAT);

        // Get all the commandeList where gouvernorat equals to UPDATED_GOUVERNORAT
        defaultCommandeShouldNotBeFound("gouvernorat.in=" + UPDATED_GOUVERNORAT);
    }

    @Test
    @Transactional
    public void getAllCommandesByGouvernoratIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where gouvernorat is not null
        defaultCommandeShouldBeFound("gouvernorat.specified=true");

        // Get all the commandeList where gouvernorat is null
        defaultCommandeShouldNotBeFound("gouvernorat.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandesByGouvernoratContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where gouvernorat contains DEFAULT_GOUVERNORAT
        defaultCommandeShouldBeFound("gouvernorat.contains=" + DEFAULT_GOUVERNORAT);

        // Get all the commandeList where gouvernorat contains UPDATED_GOUVERNORAT
        defaultCommandeShouldNotBeFound("gouvernorat.contains=" + UPDATED_GOUVERNORAT);
    }

    @Test
    @Transactional
    public void getAllCommandesByGouvernoratNotContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where gouvernorat does not contain DEFAULT_GOUVERNORAT
        defaultCommandeShouldNotBeFound("gouvernorat.doesNotContain=" + DEFAULT_GOUVERNORAT);

        // Get all the commandeList where gouvernorat does not contain UPDATED_GOUVERNORAT
        defaultCommandeShouldBeFound("gouvernorat.doesNotContain=" + UPDATED_GOUVERNORAT);
    }


    @Test
    @Transactional
    public void getAllCommandesByVilleIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where ville equals to DEFAULT_VILLE
        defaultCommandeShouldBeFound("ville.equals=" + DEFAULT_VILLE);

        // Get all the commandeList where ville equals to UPDATED_VILLE
        defaultCommandeShouldNotBeFound("ville.equals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllCommandesByVilleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where ville not equals to DEFAULT_VILLE
        defaultCommandeShouldNotBeFound("ville.notEquals=" + DEFAULT_VILLE);

        // Get all the commandeList where ville not equals to UPDATED_VILLE
        defaultCommandeShouldBeFound("ville.notEquals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllCommandesByVilleIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where ville in DEFAULT_VILLE or UPDATED_VILLE
        defaultCommandeShouldBeFound("ville.in=" + DEFAULT_VILLE + "," + UPDATED_VILLE);

        // Get all the commandeList where ville equals to UPDATED_VILLE
        defaultCommandeShouldNotBeFound("ville.in=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllCommandesByVilleIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where ville is not null
        defaultCommandeShouldBeFound("ville.specified=true");

        // Get all the commandeList where ville is null
        defaultCommandeShouldNotBeFound("ville.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandesByVilleContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where ville contains DEFAULT_VILLE
        defaultCommandeShouldBeFound("ville.contains=" + DEFAULT_VILLE);

        // Get all the commandeList where ville contains UPDATED_VILLE
        defaultCommandeShouldNotBeFound("ville.contains=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllCommandesByVilleNotContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where ville does not contain DEFAULT_VILLE
        defaultCommandeShouldNotBeFound("ville.doesNotContain=" + DEFAULT_VILLE);

        // Get all the commandeList where ville does not contain UPDATED_VILLE
        defaultCommandeShouldBeFound("ville.doesNotContain=" + UPDATED_VILLE);
    }


    @Test
    @Transactional
    public void getAllCommandesByLocaliteIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where localite equals to DEFAULT_LOCALITE
        defaultCommandeShouldBeFound("localite.equals=" + DEFAULT_LOCALITE);

        // Get all the commandeList where localite equals to UPDATED_LOCALITE
        defaultCommandeShouldNotBeFound("localite.equals=" + UPDATED_LOCALITE);
    }

    @Test
    @Transactional
    public void getAllCommandesByLocaliteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where localite not equals to DEFAULT_LOCALITE
        defaultCommandeShouldNotBeFound("localite.notEquals=" + DEFAULT_LOCALITE);

        // Get all the commandeList where localite not equals to UPDATED_LOCALITE
        defaultCommandeShouldBeFound("localite.notEquals=" + UPDATED_LOCALITE);
    }

    @Test
    @Transactional
    public void getAllCommandesByLocaliteIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where localite in DEFAULT_LOCALITE or UPDATED_LOCALITE
        defaultCommandeShouldBeFound("localite.in=" + DEFAULT_LOCALITE + "," + UPDATED_LOCALITE);

        // Get all the commandeList where localite equals to UPDATED_LOCALITE
        defaultCommandeShouldNotBeFound("localite.in=" + UPDATED_LOCALITE);
    }

    @Test
    @Transactional
    public void getAllCommandesByLocaliteIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where localite is not null
        defaultCommandeShouldBeFound("localite.specified=true");

        // Get all the commandeList where localite is null
        defaultCommandeShouldNotBeFound("localite.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandesByLocaliteContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where localite contains DEFAULT_LOCALITE
        defaultCommandeShouldBeFound("localite.contains=" + DEFAULT_LOCALITE);

        // Get all the commandeList where localite contains UPDATED_LOCALITE
        defaultCommandeShouldNotBeFound("localite.contains=" + UPDATED_LOCALITE);
    }

    @Test
    @Transactional
    public void getAllCommandesByLocaliteNotContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where localite does not contain DEFAULT_LOCALITE
        defaultCommandeShouldNotBeFound("localite.doesNotContain=" + DEFAULT_LOCALITE);

        // Get all the commandeList where localite does not contain UPDATED_LOCALITE
        defaultCommandeShouldBeFound("localite.doesNotContain=" + UPDATED_LOCALITE);
    }


    @Test
    @Transactional
    public void getAllCommandesByCodePostalIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where codePostal equals to DEFAULT_CODE_POSTAL
        defaultCommandeShouldBeFound("codePostal.equals=" + DEFAULT_CODE_POSTAL);

        // Get all the commandeList where codePostal equals to UPDATED_CODE_POSTAL
        defaultCommandeShouldNotBeFound("codePostal.equals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllCommandesByCodePostalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where codePostal not equals to DEFAULT_CODE_POSTAL
        defaultCommandeShouldNotBeFound("codePostal.notEquals=" + DEFAULT_CODE_POSTAL);

        // Get all the commandeList where codePostal not equals to UPDATED_CODE_POSTAL
        defaultCommandeShouldBeFound("codePostal.notEquals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllCommandesByCodePostalIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where codePostal in DEFAULT_CODE_POSTAL or UPDATED_CODE_POSTAL
        defaultCommandeShouldBeFound("codePostal.in=" + DEFAULT_CODE_POSTAL + "," + UPDATED_CODE_POSTAL);

        // Get all the commandeList where codePostal equals to UPDATED_CODE_POSTAL
        defaultCommandeShouldNotBeFound("codePostal.in=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllCommandesByCodePostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where codePostal is not null
        defaultCommandeShouldBeFound("codePostal.specified=true");

        // Get all the commandeList where codePostal is null
        defaultCommandeShouldNotBeFound("codePostal.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByCodePostalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where codePostal is greater than or equal to DEFAULT_CODE_POSTAL
        defaultCommandeShouldBeFound("codePostal.greaterThanOrEqual=" + DEFAULT_CODE_POSTAL);

        // Get all the commandeList where codePostal is greater than or equal to UPDATED_CODE_POSTAL
        defaultCommandeShouldNotBeFound("codePostal.greaterThanOrEqual=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllCommandesByCodePostalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where codePostal is less than or equal to DEFAULT_CODE_POSTAL
        defaultCommandeShouldBeFound("codePostal.lessThanOrEqual=" + DEFAULT_CODE_POSTAL);

        // Get all the commandeList where codePostal is less than or equal to SMALLER_CODE_POSTAL
        defaultCommandeShouldNotBeFound("codePostal.lessThanOrEqual=" + SMALLER_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllCommandesByCodePostalIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where codePostal is less than DEFAULT_CODE_POSTAL
        defaultCommandeShouldNotBeFound("codePostal.lessThan=" + DEFAULT_CODE_POSTAL);

        // Get all the commandeList where codePostal is less than UPDATED_CODE_POSTAL
        defaultCommandeShouldBeFound("codePostal.lessThan=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllCommandesByCodePostalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where codePostal is greater than DEFAULT_CODE_POSTAL
        defaultCommandeShouldNotBeFound("codePostal.greaterThan=" + DEFAULT_CODE_POSTAL);

        // Get all the commandeList where codePostal is greater than SMALLER_CODE_POSTAL
        defaultCommandeShouldBeFound("codePostal.greaterThan=" + SMALLER_CODE_POSTAL);
    }


    @Test
    @Transactional
    public void getAllCommandesByIndicationIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where indication equals to DEFAULT_INDICATION
        defaultCommandeShouldBeFound("indication.equals=" + DEFAULT_INDICATION);

        // Get all the commandeList where indication equals to UPDATED_INDICATION
        defaultCommandeShouldNotBeFound("indication.equals=" + UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByIndicationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where indication not equals to DEFAULT_INDICATION
        defaultCommandeShouldNotBeFound("indication.notEquals=" + DEFAULT_INDICATION);

        // Get all the commandeList where indication not equals to UPDATED_INDICATION
        defaultCommandeShouldBeFound("indication.notEquals=" + UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByIndicationIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where indication in DEFAULT_INDICATION or UPDATED_INDICATION
        defaultCommandeShouldBeFound("indication.in=" + DEFAULT_INDICATION + "," + UPDATED_INDICATION);

        // Get all the commandeList where indication equals to UPDATED_INDICATION
        defaultCommandeShouldNotBeFound("indication.in=" + UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByIndicationIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where indication is not null
        defaultCommandeShouldBeFound("indication.specified=true");

        // Get all the commandeList where indication is null
        defaultCommandeShouldNotBeFound("indication.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandesByIndicationContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where indication contains DEFAULT_INDICATION
        defaultCommandeShouldBeFound("indication.contains=" + DEFAULT_INDICATION);

        // Get all the commandeList where indication contains UPDATED_INDICATION
        defaultCommandeShouldNotBeFound("indication.contains=" + UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void getAllCommandesByIndicationNotContainsSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where indication does not contain DEFAULT_INDICATION
        defaultCommandeShouldNotBeFound("indication.doesNotContain=" + DEFAULT_INDICATION);

        // Get all the commandeList where indication does not contain UPDATED_INDICATION
        defaultCommandeShouldBeFound("indication.doesNotContain=" + UPDATED_INDICATION);
    }


    @Test
    @Transactional
    public void getAllCommandesByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where telephone equals to DEFAULT_TELEPHONE
        defaultCommandeShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the commandeList where telephone equals to UPDATED_TELEPHONE
        defaultCommandeShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where telephone not equals to DEFAULT_TELEPHONE
        defaultCommandeShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the commandeList where telephone not equals to UPDATED_TELEPHONE
        defaultCommandeShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultCommandeShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the commandeList where telephone equals to UPDATED_TELEPHONE
        defaultCommandeShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where telephone is not null
        defaultCommandeShouldBeFound("telephone.specified=true");

        // Get all the commandeList where telephone is null
        defaultCommandeShouldNotBeFound("telephone.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByTelephoneIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where telephone is greater than or equal to DEFAULT_TELEPHONE
        defaultCommandeShouldBeFound("telephone.greaterThanOrEqual=" + DEFAULT_TELEPHONE);

        // Get all the commandeList where telephone is greater than or equal to UPDATED_TELEPHONE
        defaultCommandeShouldNotBeFound("telephone.greaterThanOrEqual=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTelephoneIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where telephone is less than or equal to DEFAULT_TELEPHONE
        defaultCommandeShouldBeFound("telephone.lessThanOrEqual=" + DEFAULT_TELEPHONE);

        // Get all the commandeList where telephone is less than or equal to SMALLER_TELEPHONE
        defaultCommandeShouldNotBeFound("telephone.lessThanOrEqual=" + SMALLER_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTelephoneIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where telephone is less than DEFAULT_TELEPHONE
        defaultCommandeShouldNotBeFound("telephone.lessThan=" + DEFAULT_TELEPHONE);

        // Get all the commandeList where telephone is less than UPDATED_TELEPHONE
        defaultCommandeShouldBeFound("telephone.lessThan=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTelephoneIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where telephone is greater than DEFAULT_TELEPHONE
        defaultCommandeShouldNotBeFound("telephone.greaterThan=" + DEFAULT_TELEPHONE);

        // Get all the commandeList where telephone is greater than SMALLER_TELEPHONE
        defaultCommandeShouldBeFound("telephone.greaterThan=" + SMALLER_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllCommandesByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where mobile equals to DEFAULT_MOBILE
        defaultCommandeShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the commandeList where mobile equals to UPDATED_MOBILE
        defaultCommandeShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCommandesByMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where mobile not equals to DEFAULT_MOBILE
        defaultCommandeShouldNotBeFound("mobile.notEquals=" + DEFAULT_MOBILE);

        // Get all the commandeList where mobile not equals to UPDATED_MOBILE
        defaultCommandeShouldBeFound("mobile.notEquals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCommandesByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultCommandeShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the commandeList where mobile equals to UPDATED_MOBILE
        defaultCommandeShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCommandesByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where mobile is not null
        defaultCommandeShouldBeFound("mobile.specified=true");

        // Get all the commandeList where mobile is null
        defaultCommandeShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByMobileIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where mobile is greater than or equal to DEFAULT_MOBILE
        defaultCommandeShouldBeFound("mobile.greaterThanOrEqual=" + DEFAULT_MOBILE);

        // Get all the commandeList where mobile is greater than or equal to UPDATED_MOBILE
        defaultCommandeShouldNotBeFound("mobile.greaterThanOrEqual=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCommandesByMobileIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where mobile is less than or equal to DEFAULT_MOBILE
        defaultCommandeShouldBeFound("mobile.lessThanOrEqual=" + DEFAULT_MOBILE);

        // Get all the commandeList where mobile is less than or equal to SMALLER_MOBILE
        defaultCommandeShouldNotBeFound("mobile.lessThanOrEqual=" + SMALLER_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCommandesByMobileIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where mobile is less than DEFAULT_MOBILE
        defaultCommandeShouldNotBeFound("mobile.lessThan=" + DEFAULT_MOBILE);

        // Get all the commandeList where mobile is less than UPDATED_MOBILE
        defaultCommandeShouldBeFound("mobile.lessThan=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCommandesByMobileIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where mobile is greater than DEFAULT_MOBILE
        defaultCommandeShouldNotBeFound("mobile.greaterThan=" + DEFAULT_MOBILE);

        // Get all the commandeList where mobile is greater than SMALLER_MOBILE
        defaultCommandeShouldBeFound("mobile.greaterThan=" + SMALLER_MOBILE);
    }


    @Test
    @Transactional
    public void getAllCommandesByFraisLivraisonIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where fraisLivraison equals to DEFAULT_FRAIS_LIVRAISON
        defaultCommandeShouldBeFound("fraisLivraison.equals=" + DEFAULT_FRAIS_LIVRAISON);

        // Get all the commandeList where fraisLivraison equals to UPDATED_FRAIS_LIVRAISON
        defaultCommandeShouldNotBeFound("fraisLivraison.equals=" + UPDATED_FRAIS_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllCommandesByFraisLivraisonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where fraisLivraison not equals to DEFAULT_FRAIS_LIVRAISON
        defaultCommandeShouldNotBeFound("fraisLivraison.notEquals=" + DEFAULT_FRAIS_LIVRAISON);

        // Get all the commandeList where fraisLivraison not equals to UPDATED_FRAIS_LIVRAISON
        defaultCommandeShouldBeFound("fraisLivraison.notEquals=" + UPDATED_FRAIS_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllCommandesByFraisLivraisonIsInShouldWork() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where fraisLivraison in DEFAULT_FRAIS_LIVRAISON or UPDATED_FRAIS_LIVRAISON
        defaultCommandeShouldBeFound("fraisLivraison.in=" + DEFAULT_FRAIS_LIVRAISON + "," + UPDATED_FRAIS_LIVRAISON);

        // Get all the commandeList where fraisLivraison equals to UPDATED_FRAIS_LIVRAISON
        defaultCommandeShouldNotBeFound("fraisLivraison.in=" + UPDATED_FRAIS_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllCommandesByFraisLivraisonIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where fraisLivraison is not null
        defaultCommandeShouldBeFound("fraisLivraison.specified=true");

        // Get all the commandeList where fraisLivraison is null
        defaultCommandeShouldNotBeFound("fraisLivraison.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByFraisLivraisonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where fraisLivraison is greater than or equal to DEFAULT_FRAIS_LIVRAISON
        defaultCommandeShouldBeFound("fraisLivraison.greaterThanOrEqual=" + DEFAULT_FRAIS_LIVRAISON);

        // Get all the commandeList where fraisLivraison is greater than or equal to UPDATED_FRAIS_LIVRAISON
        defaultCommandeShouldNotBeFound("fraisLivraison.greaterThanOrEqual=" + UPDATED_FRAIS_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllCommandesByFraisLivraisonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where fraisLivraison is less than or equal to DEFAULT_FRAIS_LIVRAISON
        defaultCommandeShouldBeFound("fraisLivraison.lessThanOrEqual=" + DEFAULT_FRAIS_LIVRAISON);

        // Get all the commandeList where fraisLivraison is less than or equal to SMALLER_FRAIS_LIVRAISON
        defaultCommandeShouldNotBeFound("fraisLivraison.lessThanOrEqual=" + SMALLER_FRAIS_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllCommandesByFraisLivraisonIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where fraisLivraison is less than DEFAULT_FRAIS_LIVRAISON
        defaultCommandeShouldNotBeFound("fraisLivraison.lessThan=" + DEFAULT_FRAIS_LIVRAISON);

        // Get all the commandeList where fraisLivraison is less than UPDATED_FRAIS_LIVRAISON
        defaultCommandeShouldBeFound("fraisLivraison.lessThan=" + UPDATED_FRAIS_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllCommandesByFraisLivraisonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList where fraisLivraison is greater than DEFAULT_FRAIS_LIVRAISON
        defaultCommandeShouldNotBeFound("fraisLivraison.greaterThan=" + DEFAULT_FRAIS_LIVRAISON);

        // Get all the commandeList where fraisLivraison is greater than SMALLER_FRAIS_LIVRAISON
        defaultCommandeShouldBeFound("fraisLivraison.greaterThan=" + SMALLER_FRAIS_LIVRAISON);
    }


    @Test
    @Transactional
    public void getAllCommandesByMouvementStockIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);
        MouvementStock mouvementStock = MouvementStockResourceIT.createEntity(em);
        em.persist(mouvementStock);
        em.flush();
        commande.addMouvementStock(mouvementStock);
        commandeRepository.saveAndFlush(commande);
        Long mouvementStockId = mouvementStock.getId();

        // Get all the commandeList where mouvementStock equals to mouvementStockId
        defaultCommandeShouldBeFound("mouvementStockId.equals=" + mouvementStockId);

        // Get all the commandeList where mouvementStock equals to mouvementStockId + 1
        defaultCommandeShouldNotBeFound("mouvementStockId.equals=" + (mouvementStockId + 1));
    }


    @Test
    @Transactional
    public void getAllCommandesByCommandeLignesIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);
        CommandeLignes commandeLignes = CommandeLignesResourceIT.createEntity(em);
        em.persist(commandeLignes);
        em.flush();
        commande.addCommandeLignes(commandeLignes);
        commandeRepository.saveAndFlush(commande);
        Long commandeLignesId = commandeLignes.getId();

        // Get all the commandeList where commandeLignes equals to commandeLignesId
        defaultCommandeShouldBeFound("commandeLignesId.equals=" + commandeLignesId);

        // Get all the commandeList where commandeLignes equals to commandeLignesId + 1
        defaultCommandeShouldNotBeFound("commandeLignesId.equals=" + (commandeLignesId + 1));
    }


    @Test
    @Transactional
    public void getAllCommandesByIdClientIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);
        Client idClient = ClientResourceIT.createEntity(em);
        em.persist(idClient);
        em.flush();
        commande.setIdClient(idClient);
        commandeRepository.saveAndFlush(commande);
        Long idClientId = idClient.getId();

        // Get all the commandeList where idClient equals to idClientId
        defaultCommandeShouldBeFound("idClientId.equals=" + idClientId);

        // Get all the commandeList where idClient equals to idClientId + 1
        defaultCommandeShouldNotBeFound("idClientId.equals=" + (idClientId + 1));
    }


    @Test
    @Transactional
    public void getAllCommandesByIdAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);
        Adresse idAdresse = AdresseResourceIT.createEntity(em);
        em.persist(idAdresse);
        em.flush();
        commande.setIdAdresse(idAdresse);
        commandeRepository.saveAndFlush(commande);
        Long idAdresseId = idAdresse.getId();

        // Get all the commandeList where idAdresse equals to idAdresseId
        defaultCommandeShouldBeFound("idAdresseId.equals=" + idAdresseId);

        // Get all the commandeList where idAdresse equals to idAdresseId + 1
        defaultCommandeShouldNotBeFound("idAdresseId.equals=" + (idAdresseId + 1));
    }


    @Test
    @Transactional
    public void getAllCommandesByZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);
        Zone zone = ZoneResourceIT.createEntity(em);
        em.persist(zone);
        em.flush();
        commande.setZone(zone);
        commandeRepository.saveAndFlush(commande);
        Long zoneId = zone.getId();

        // Get all the commandeList where zone equals to zoneId
        defaultCommandeShouldBeFound("zoneId.equals=" + zoneId);

        // Get all the commandeList where zone equals to zoneId + 1
        defaultCommandeShouldNotBeFound("zoneId.equals=" + (zoneId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommandeShouldBeFound(String filter) throws Exception {
        restCommandeMockMvc.perform(get("/api/commandes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commande.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE.toString())))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].totalRemise").value(hasItem(DEFAULT_TOTAL_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].totTTC").value(hasItem(DEFAULT_TOT_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())))
            .andExpect(jsonPath("$.[*].pointsFidelite").value(hasItem(DEFAULT_POINTS_FIDELITE)))
            .andExpect(jsonPath("$.[*].reglement").value(hasItem(DEFAULT_REGLEMENT.toString())))
            .andExpect(jsonPath("$.[*].dateLivraison").value(hasItem(DEFAULT_DATE_LIVRAISON.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateAnnulation").value(hasItem(DEFAULT_DATE_ANNULATION.toString())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].fraisLivraison").value(hasItem(DEFAULT_FRAIS_LIVRAISON.doubleValue())));

        // Check, that the count call also returns 1
        restCommandeMockMvc.perform(get("/api/commandes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommandeShouldNotBeFound(String filter) throws Exception {
        restCommandeMockMvc.perform(get("/api/commandes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommandeMockMvc.perform(get("/api/commandes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCommande() throws Exception {
        // Get the commande
        restCommandeMockMvc.perform(get("/api/commandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommande() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        int databaseSizeBeforeUpdate = commandeRepository.findAll().size();

        // Update the commande
        Commande updatedCommande = commandeRepository.findById(commande.getId()).get();
        // Disconnect from session so that the updates on updatedCommande are not directly saved in db
        em.detach(updatedCommande);
        updatedCommande
            .reference(UPDATED_REFERENCE)
            .date(UPDATED_DATE)
            .statut(UPDATED_STATUT)
            .origine(UPDATED_ORIGINE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totalRemise(UPDATED_TOTAL_REMISE)
            .totTTC(UPDATED_TOT_TTC)
            .devise(UPDATED_DEVISE)
            .pointsFidelite(UPDATED_POINTS_FIDELITE)
            .reglement(UPDATED_REGLEMENT)
            .dateLivraison(UPDATED_DATE_LIVRAISON)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateAnnulation(UPDATED_DATE_ANNULATION)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .gouvernorat(UPDATED_GOUVERNORAT)
            .ville(UPDATED_VILLE)
            .localite(UPDATED_LOCALITE)
            .codePostal(UPDATED_CODE_POSTAL)
            .indication(UPDATED_INDICATION)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .fraisLivraison(UPDATED_FRAIS_LIVRAISON);
        CommandeDTO commandeDTO = commandeMapper.toDto(updatedCommande);

        restCommandeMockMvc.perform(put("/api/commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeDTO)))
            .andExpect(status().isOk());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeUpdate);
        Commande testCommande = commandeList.get(commandeList.size() - 1);
        assertThat(testCommande.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testCommande.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCommande.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testCommande.getOrigine()).isEqualTo(UPDATED_ORIGINE);
        assertThat(testCommande.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testCommande.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testCommande.getTotalRemise()).isEqualTo(UPDATED_TOTAL_REMISE);
        assertThat(testCommande.getTotTTC()).isEqualTo(UPDATED_TOT_TTC);
        assertThat(testCommande.getDevise()).isEqualTo(UPDATED_DEVISE);
        assertThat(testCommande.getPointsFidelite()).isEqualTo(UPDATED_POINTS_FIDELITE);
        assertThat(testCommande.getReglement()).isEqualTo(UPDATED_REGLEMENT);
        assertThat(testCommande.getDateLivraison()).isEqualTo(UPDATED_DATE_LIVRAISON);
        assertThat(testCommande.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testCommande.getDateAnnulation()).isEqualTo(UPDATED_DATE_ANNULATION);
        assertThat(testCommande.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testCommande.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testCommande.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testCommande.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);
        assertThat(testCommande.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testCommande.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCommande.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testCommande.getGouvernorat()).isEqualTo(UPDATED_GOUVERNORAT);
        assertThat(testCommande.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testCommande.getLocalite()).isEqualTo(UPDATED_LOCALITE);
        assertThat(testCommande.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testCommande.getIndication()).isEqualTo(UPDATED_INDICATION);
        assertThat(testCommande.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testCommande.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCommande.getFraisLivraison()).isEqualTo(UPDATED_FRAIS_LIVRAISON);

        // Validate the Commande in Elasticsearch
        verify(mockCommandeSearchRepository, times(1)).save(testCommande);
    }

    @Test
    @Transactional
    public void updateNonExistingCommande() throws Exception {
        int databaseSizeBeforeUpdate = commandeRepository.findAll().size();

        // Create the Commande
        CommandeDTO commandeDTO = commandeMapper.toDto(commande);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeMockMvc.perform(put("/api/commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Commande in Elasticsearch
        verify(mockCommandeSearchRepository, times(0)).save(commande);
    }

    @Test
    @Transactional
    public void deleteCommande() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        int databaseSizeBeforeDelete = commandeRepository.findAll().size();

        // Delete the commande
        restCommandeMockMvc.perform(delete("/api/commandes/{id}", commande.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Commande in Elasticsearch
        verify(mockCommandeSearchRepository, times(1)).deleteById(commande.getId());
    }

    @Test
    @Transactional
    public void searchCommande() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        commandeRepository.saveAndFlush(commande);
        when(mockCommandeSearchRepository.search(queryStringQuery("id:" + commande.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commande), PageRequest.of(0, 1), 1));

        // Search the commande
        restCommandeMockMvc.perform(get("/api/_search/commandes?query=id:" + commande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commande.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE.toString())))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].totalRemise").value(hasItem(DEFAULT_TOTAL_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].totTTC").value(hasItem(DEFAULT_TOT_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())))
            .andExpect(jsonPath("$.[*].pointsFidelite").value(hasItem(DEFAULT_POINTS_FIDELITE)))
            .andExpect(jsonPath("$.[*].reglement").value(hasItem(DEFAULT_REGLEMENT.toString())))
            .andExpect(jsonPath("$.[*].dateLivraison").value(hasItem(DEFAULT_DATE_LIVRAISON.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateAnnulation").value(hasItem(DEFAULT_DATE_ANNULATION.toString())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].fraisLivraison").value(hasItem(DEFAULT_FRAIS_LIVRAISON.doubleValue())));
    }
}
