package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.CommandeLignes;
import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.repository.CommandeLignesRepository;
import org.fininfo.elbazar.repository.search.CommandeLignesSearchRepository;
import org.fininfo.elbazar.service.CommandeLignesService;
import org.fininfo.elbazar.service.dto.CommandeLignesDTO;
import org.fininfo.elbazar.service.mapper.CommandeLignesMapper;
import org.fininfo.elbazar.service.dto.CommandeLignesCriteria;
import org.fininfo.elbazar.service.CommandeLignesQueryService;

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
 * Integration tests for the {@link CommandeLignesResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommandeLignesResourceIT {

    private static final Double DEFAULT_QUANTITE = 1D;
    private static final Double UPDATED_QUANTITE = 2D;
    private static final Double SMALLER_QUANTITE = 1D - 1D;

    private static final Double DEFAULT_PRIX_HT = 1D;
    private static final Double UPDATED_PRIX_HT = 2D;
    private static final Double SMALLER_PRIX_HT = 1D - 1D;

    private static final Double DEFAULT_REMISE = 1D;
    private static final Double UPDATED_REMISE = 2D;
    private static final Double SMALLER_REMISE = 1D - 1D;

    private static final Double DEFAULT_TVA = 1D;
    private static final Double UPDATED_TVA = 2D;
    private static final Double SMALLER_TVA = 1D - 1D;

    private static final Double DEFAULT_PRIX_TTC = 1D;
    private static final Double UPDATED_PRIX_TTC = 2D;
    private static final Double SMALLER_PRIX_TTC = 1D - 1D;

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
    private CommandeLignesRepository commandeLignesRepository;

    @Autowired
    private CommandeLignesMapper commandeLignesMapper;

    @Autowired
    private CommandeLignesService commandeLignesService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.CommandeLignesSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommandeLignesSearchRepository mockCommandeLignesSearchRepository;

    @Autowired
    private CommandeLignesQueryService commandeLignesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandeLignesMockMvc;

    private CommandeLignes commandeLignes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeLignes createEntity(EntityManager em) {
        CommandeLignes commandeLignes = new CommandeLignes()
            .quantite(DEFAULT_QUANTITE)
            .prixHT(DEFAULT_PRIX_HT)
            .remise(DEFAULT_REMISE)
            .tva(DEFAULT_TVA)
            .prixTTC(DEFAULT_PRIX_TTC)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return commandeLignes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeLignes createUpdatedEntity(EntityManager em) {
        CommandeLignes commandeLignes = new CommandeLignes()
            .quantite(UPDATED_QUANTITE)
            .prixHT(UPDATED_PRIX_HT)
            .remise(UPDATED_REMISE)
            .tva(UPDATED_TVA)
            .prixTTC(UPDATED_PRIX_TTC)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return commandeLignes;
    }

    @BeforeEach
    public void initTest() {
        commandeLignes = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandeLignes() throws Exception {
        int databaseSizeBeforeCreate = commandeLignesRepository.findAll().size();
        // Create the CommandeLignes
        CommandeLignesDTO commandeLignesDTO = commandeLignesMapper.toDto(commandeLignes);
        restCommandeLignesMockMvc.perform(post("/api/commande-lignes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLignesDTO)))
            .andExpect(status().isCreated());

        // Validate the CommandeLignes in the database
        List<CommandeLignes> commandeLignesList = commandeLignesRepository.findAll();
        assertThat(commandeLignesList).hasSize(databaseSizeBeforeCreate + 1);
        CommandeLignes testCommandeLignes = commandeLignesList.get(commandeLignesList.size() - 1);
        assertThat(testCommandeLignes.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testCommandeLignes.getPrixHT()).isEqualTo(DEFAULT_PRIX_HT);
        assertThat(testCommandeLignes.getRemise()).isEqualTo(DEFAULT_REMISE);
        assertThat(testCommandeLignes.getTva()).isEqualTo(DEFAULT_TVA);
        assertThat(testCommandeLignes.getPrixTTC()).isEqualTo(DEFAULT_PRIX_TTC);
        assertThat(testCommandeLignes.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testCommandeLignes.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testCommandeLignes.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testCommandeLignes.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the CommandeLignes in Elasticsearch
        verify(mockCommandeLignesSearchRepository, times(1)).save(testCommandeLignes);
    }

    @Test
    @Transactional
    public void createCommandeLignesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeLignesRepository.findAll().size();

        // Create the CommandeLignes with an existing ID
        commandeLignes.setId(1L);
        CommandeLignesDTO commandeLignesDTO = commandeLignesMapper.toDto(commandeLignes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeLignesMockMvc.perform(post("/api/commande-lignes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLignesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeLignes in the database
        List<CommandeLignes> commandeLignesList = commandeLignesRepository.findAll();
        assertThat(commandeLignesList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommandeLignes in Elasticsearch
        verify(mockCommandeLignesSearchRepository, times(0)).save(commandeLignes);
    }


    @Test
    @Transactional
    public void getAllCommandeLignes() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList
        restCommandeLignesMockMvc.perform(get("/api/commande-lignes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeLignes.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].remise").value(hasItem(DEFAULT_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getCommandeLignes() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get the commandeLignes
        restCommandeLignesMockMvc.perform(get("/api/commande-lignes/{id}", commandeLignes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandeLignes.getId().intValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()))
            .andExpect(jsonPath("$.prixHT").value(DEFAULT_PRIX_HT.doubleValue()))
            .andExpect(jsonPath("$.remise").value(DEFAULT_REMISE.doubleValue()))
            .andExpect(jsonPath("$.tva").value(DEFAULT_TVA.doubleValue()))
            .andExpect(jsonPath("$.prixTTC").value(DEFAULT_PRIX_TTC.doubleValue()))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }


    @Test
    @Transactional
    public void getCommandeLignesByIdFiltering() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        Long id = commandeLignes.getId();

        defaultCommandeLignesShouldBeFound("id.equals=" + id);
        defaultCommandeLignesShouldNotBeFound("id.notEquals=" + id);

        defaultCommandeLignesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommandeLignesShouldNotBeFound("id.greaterThan=" + id);

        defaultCommandeLignesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommandeLignesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommandeLignesByQuantiteIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where quantite equals to DEFAULT_QUANTITE
        defaultCommandeLignesShouldBeFound("quantite.equals=" + DEFAULT_QUANTITE);

        // Get all the commandeLignesList where quantite equals to UPDATED_QUANTITE
        defaultCommandeLignesShouldNotBeFound("quantite.equals=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByQuantiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where quantite not equals to DEFAULT_QUANTITE
        defaultCommandeLignesShouldNotBeFound("quantite.notEquals=" + DEFAULT_QUANTITE);

        // Get all the commandeLignesList where quantite not equals to UPDATED_QUANTITE
        defaultCommandeLignesShouldBeFound("quantite.notEquals=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByQuantiteIsInShouldWork() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where quantite in DEFAULT_QUANTITE or UPDATED_QUANTITE
        defaultCommandeLignesShouldBeFound("quantite.in=" + DEFAULT_QUANTITE + "," + UPDATED_QUANTITE);

        // Get all the commandeLignesList where quantite equals to UPDATED_QUANTITE
        defaultCommandeLignesShouldNotBeFound("quantite.in=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByQuantiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where quantite is not null
        defaultCommandeLignesShouldBeFound("quantite.specified=true");

        // Get all the commandeLignesList where quantite is null
        defaultCommandeLignesShouldNotBeFound("quantite.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByQuantiteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where quantite is greater than or equal to DEFAULT_QUANTITE
        defaultCommandeLignesShouldBeFound("quantite.greaterThanOrEqual=" + DEFAULT_QUANTITE);

        // Get all the commandeLignesList where quantite is greater than or equal to UPDATED_QUANTITE
        defaultCommandeLignesShouldNotBeFound("quantite.greaterThanOrEqual=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByQuantiteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where quantite is less than or equal to DEFAULT_QUANTITE
        defaultCommandeLignesShouldBeFound("quantite.lessThanOrEqual=" + DEFAULT_QUANTITE);

        // Get all the commandeLignesList where quantite is less than or equal to SMALLER_QUANTITE
        defaultCommandeLignesShouldNotBeFound("quantite.lessThanOrEqual=" + SMALLER_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByQuantiteIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where quantite is less than DEFAULT_QUANTITE
        defaultCommandeLignesShouldNotBeFound("quantite.lessThan=" + DEFAULT_QUANTITE);

        // Get all the commandeLignesList where quantite is less than UPDATED_QUANTITE
        defaultCommandeLignesShouldBeFound("quantite.lessThan=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByQuantiteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where quantite is greater than DEFAULT_QUANTITE
        defaultCommandeLignesShouldNotBeFound("quantite.greaterThan=" + DEFAULT_QUANTITE);

        // Get all the commandeLignesList where quantite is greater than SMALLER_QUANTITE
        defaultCommandeLignesShouldBeFound("quantite.greaterThan=" + SMALLER_QUANTITE);
    }


    @Test
    @Transactional
    public void getAllCommandeLignesByPrixHTIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixHT equals to DEFAULT_PRIX_HT
        defaultCommandeLignesShouldBeFound("prixHT.equals=" + DEFAULT_PRIX_HT);

        // Get all the commandeLignesList where prixHT equals to UPDATED_PRIX_HT
        defaultCommandeLignesShouldNotBeFound("prixHT.equals=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixHTIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixHT not equals to DEFAULT_PRIX_HT
        defaultCommandeLignesShouldNotBeFound("prixHT.notEquals=" + DEFAULT_PRIX_HT);

        // Get all the commandeLignesList where prixHT not equals to UPDATED_PRIX_HT
        defaultCommandeLignesShouldBeFound("prixHT.notEquals=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixHTIsInShouldWork() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixHT in DEFAULT_PRIX_HT or UPDATED_PRIX_HT
        defaultCommandeLignesShouldBeFound("prixHT.in=" + DEFAULT_PRIX_HT + "," + UPDATED_PRIX_HT);

        // Get all the commandeLignesList where prixHT equals to UPDATED_PRIX_HT
        defaultCommandeLignesShouldNotBeFound("prixHT.in=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixHTIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixHT is not null
        defaultCommandeLignesShouldBeFound("prixHT.specified=true");

        // Get all the commandeLignesList where prixHT is null
        defaultCommandeLignesShouldNotBeFound("prixHT.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixHTIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixHT is greater than or equal to DEFAULT_PRIX_HT
        defaultCommandeLignesShouldBeFound("prixHT.greaterThanOrEqual=" + DEFAULT_PRIX_HT);

        // Get all the commandeLignesList where prixHT is greater than or equal to UPDATED_PRIX_HT
        defaultCommandeLignesShouldNotBeFound("prixHT.greaterThanOrEqual=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixHTIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixHT is less than or equal to DEFAULT_PRIX_HT
        defaultCommandeLignesShouldBeFound("prixHT.lessThanOrEqual=" + DEFAULT_PRIX_HT);

        // Get all the commandeLignesList where prixHT is less than or equal to SMALLER_PRIX_HT
        defaultCommandeLignesShouldNotBeFound("prixHT.lessThanOrEqual=" + SMALLER_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixHTIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixHT is less than DEFAULT_PRIX_HT
        defaultCommandeLignesShouldNotBeFound("prixHT.lessThan=" + DEFAULT_PRIX_HT);

        // Get all the commandeLignesList where prixHT is less than UPDATED_PRIX_HT
        defaultCommandeLignesShouldBeFound("prixHT.lessThan=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixHTIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixHT is greater than DEFAULT_PRIX_HT
        defaultCommandeLignesShouldNotBeFound("prixHT.greaterThan=" + DEFAULT_PRIX_HT);

        // Get all the commandeLignesList where prixHT is greater than SMALLER_PRIX_HT
        defaultCommandeLignesShouldBeFound("prixHT.greaterThan=" + SMALLER_PRIX_HT);
    }


    @Test
    @Transactional
    public void getAllCommandeLignesByRemiseIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where remise equals to DEFAULT_REMISE
        defaultCommandeLignesShouldBeFound("remise.equals=" + DEFAULT_REMISE);

        // Get all the commandeLignesList where remise equals to UPDATED_REMISE
        defaultCommandeLignesShouldNotBeFound("remise.equals=" + UPDATED_REMISE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByRemiseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where remise not equals to DEFAULT_REMISE
        defaultCommandeLignesShouldNotBeFound("remise.notEquals=" + DEFAULT_REMISE);

        // Get all the commandeLignesList where remise not equals to UPDATED_REMISE
        defaultCommandeLignesShouldBeFound("remise.notEquals=" + UPDATED_REMISE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByRemiseIsInShouldWork() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where remise in DEFAULT_REMISE or UPDATED_REMISE
        defaultCommandeLignesShouldBeFound("remise.in=" + DEFAULT_REMISE + "," + UPDATED_REMISE);

        // Get all the commandeLignesList where remise equals to UPDATED_REMISE
        defaultCommandeLignesShouldNotBeFound("remise.in=" + UPDATED_REMISE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByRemiseIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where remise is not null
        defaultCommandeLignesShouldBeFound("remise.specified=true");

        // Get all the commandeLignesList where remise is null
        defaultCommandeLignesShouldNotBeFound("remise.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByRemiseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where remise is greater than or equal to DEFAULT_REMISE
        defaultCommandeLignesShouldBeFound("remise.greaterThanOrEqual=" + DEFAULT_REMISE);

        // Get all the commandeLignesList where remise is greater than or equal to UPDATED_REMISE
        defaultCommandeLignesShouldNotBeFound("remise.greaterThanOrEqual=" + UPDATED_REMISE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByRemiseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where remise is less than or equal to DEFAULT_REMISE
        defaultCommandeLignesShouldBeFound("remise.lessThanOrEqual=" + DEFAULT_REMISE);

        // Get all the commandeLignesList where remise is less than or equal to SMALLER_REMISE
        defaultCommandeLignesShouldNotBeFound("remise.lessThanOrEqual=" + SMALLER_REMISE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByRemiseIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where remise is less than DEFAULT_REMISE
        defaultCommandeLignesShouldNotBeFound("remise.lessThan=" + DEFAULT_REMISE);

        // Get all the commandeLignesList where remise is less than UPDATED_REMISE
        defaultCommandeLignesShouldBeFound("remise.lessThan=" + UPDATED_REMISE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByRemiseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where remise is greater than DEFAULT_REMISE
        defaultCommandeLignesShouldNotBeFound("remise.greaterThan=" + DEFAULT_REMISE);

        // Get all the commandeLignesList where remise is greater than SMALLER_REMISE
        defaultCommandeLignesShouldBeFound("remise.greaterThan=" + SMALLER_REMISE);
    }


    @Test
    @Transactional
    public void getAllCommandeLignesByTvaIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where tva equals to DEFAULT_TVA
        defaultCommandeLignesShouldBeFound("tva.equals=" + DEFAULT_TVA);

        // Get all the commandeLignesList where tva equals to UPDATED_TVA
        defaultCommandeLignesShouldNotBeFound("tva.equals=" + UPDATED_TVA);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByTvaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where tva not equals to DEFAULT_TVA
        defaultCommandeLignesShouldNotBeFound("tva.notEquals=" + DEFAULT_TVA);

        // Get all the commandeLignesList where tva not equals to UPDATED_TVA
        defaultCommandeLignesShouldBeFound("tva.notEquals=" + UPDATED_TVA);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByTvaIsInShouldWork() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where tva in DEFAULT_TVA or UPDATED_TVA
        defaultCommandeLignesShouldBeFound("tva.in=" + DEFAULT_TVA + "," + UPDATED_TVA);

        // Get all the commandeLignesList where tva equals to UPDATED_TVA
        defaultCommandeLignesShouldNotBeFound("tva.in=" + UPDATED_TVA);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByTvaIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where tva is not null
        defaultCommandeLignesShouldBeFound("tva.specified=true");

        // Get all the commandeLignesList where tva is null
        defaultCommandeLignesShouldNotBeFound("tva.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByTvaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where tva is greater than or equal to DEFAULT_TVA
        defaultCommandeLignesShouldBeFound("tva.greaterThanOrEqual=" + DEFAULT_TVA);

        // Get all the commandeLignesList where tva is greater than or equal to UPDATED_TVA
        defaultCommandeLignesShouldNotBeFound("tva.greaterThanOrEqual=" + UPDATED_TVA);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByTvaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where tva is less than or equal to DEFAULT_TVA
        defaultCommandeLignesShouldBeFound("tva.lessThanOrEqual=" + DEFAULT_TVA);

        // Get all the commandeLignesList where tva is less than or equal to SMALLER_TVA
        defaultCommandeLignesShouldNotBeFound("tva.lessThanOrEqual=" + SMALLER_TVA);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByTvaIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where tva is less than DEFAULT_TVA
        defaultCommandeLignesShouldNotBeFound("tva.lessThan=" + DEFAULT_TVA);

        // Get all the commandeLignesList where tva is less than UPDATED_TVA
        defaultCommandeLignesShouldBeFound("tva.lessThan=" + UPDATED_TVA);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByTvaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where tva is greater than DEFAULT_TVA
        defaultCommandeLignesShouldNotBeFound("tva.greaterThan=" + DEFAULT_TVA);

        // Get all the commandeLignesList where tva is greater than SMALLER_TVA
        defaultCommandeLignesShouldBeFound("tva.greaterThan=" + SMALLER_TVA);
    }


    @Test
    @Transactional
    public void getAllCommandeLignesByPrixTTCIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixTTC equals to DEFAULT_PRIX_TTC
        defaultCommandeLignesShouldBeFound("prixTTC.equals=" + DEFAULT_PRIX_TTC);

        // Get all the commandeLignesList where prixTTC equals to UPDATED_PRIX_TTC
        defaultCommandeLignesShouldNotBeFound("prixTTC.equals=" + UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixTTCIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixTTC not equals to DEFAULT_PRIX_TTC
        defaultCommandeLignesShouldNotBeFound("prixTTC.notEquals=" + DEFAULT_PRIX_TTC);

        // Get all the commandeLignesList where prixTTC not equals to UPDATED_PRIX_TTC
        defaultCommandeLignesShouldBeFound("prixTTC.notEquals=" + UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixTTCIsInShouldWork() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixTTC in DEFAULT_PRIX_TTC or UPDATED_PRIX_TTC
        defaultCommandeLignesShouldBeFound("prixTTC.in=" + DEFAULT_PRIX_TTC + "," + UPDATED_PRIX_TTC);

        // Get all the commandeLignesList where prixTTC equals to UPDATED_PRIX_TTC
        defaultCommandeLignesShouldNotBeFound("prixTTC.in=" + UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixTTCIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixTTC is not null
        defaultCommandeLignesShouldBeFound("prixTTC.specified=true");

        // Get all the commandeLignesList where prixTTC is null
        defaultCommandeLignesShouldNotBeFound("prixTTC.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixTTCIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixTTC is greater than or equal to DEFAULT_PRIX_TTC
        defaultCommandeLignesShouldBeFound("prixTTC.greaterThanOrEqual=" + DEFAULT_PRIX_TTC);

        // Get all the commandeLignesList where prixTTC is greater than or equal to UPDATED_PRIX_TTC
        defaultCommandeLignesShouldNotBeFound("prixTTC.greaterThanOrEqual=" + UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixTTCIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixTTC is less than or equal to DEFAULT_PRIX_TTC
        defaultCommandeLignesShouldBeFound("prixTTC.lessThanOrEqual=" + DEFAULT_PRIX_TTC);

        // Get all the commandeLignesList where prixTTC is less than or equal to SMALLER_PRIX_TTC
        defaultCommandeLignesShouldNotBeFound("prixTTC.lessThanOrEqual=" + SMALLER_PRIX_TTC);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixTTCIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixTTC is less than DEFAULT_PRIX_TTC
        defaultCommandeLignesShouldNotBeFound("prixTTC.lessThan=" + DEFAULT_PRIX_TTC);

        // Get all the commandeLignesList where prixTTC is less than UPDATED_PRIX_TTC
        defaultCommandeLignesShouldBeFound("prixTTC.lessThan=" + UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByPrixTTCIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where prixTTC is greater than DEFAULT_PRIX_TTC
        defaultCommandeLignesShouldNotBeFound("prixTTC.greaterThan=" + DEFAULT_PRIX_TTC);

        // Get all the commandeLignesList where prixTTC is greater than SMALLER_PRIX_TTC
        defaultCommandeLignesShouldBeFound("prixTTC.greaterThan=" + SMALLER_PRIX_TTC);
    }


    @Test
    @Transactional
    public void getAllCommandeLignesByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creeLe equals to DEFAULT_CREE_LE
        defaultCommandeLignesShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the commandeLignesList where creeLe equals to UPDATED_CREE_LE
        defaultCommandeLignesShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creeLe not equals to DEFAULT_CREE_LE
        defaultCommandeLignesShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the commandeLignesList where creeLe not equals to UPDATED_CREE_LE
        defaultCommandeLignesShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultCommandeLignesShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the commandeLignesList where creeLe equals to UPDATED_CREE_LE
        defaultCommandeLignesShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creeLe is not null
        defaultCommandeLignesShouldBeFound("creeLe.specified=true");

        // Get all the commandeLignesList where creeLe is null
        defaultCommandeLignesShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultCommandeLignesShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the commandeLignesList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultCommandeLignesShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultCommandeLignesShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the commandeLignesList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultCommandeLignesShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creeLe is less than DEFAULT_CREE_LE
        defaultCommandeLignesShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the commandeLignesList where creeLe is less than UPDATED_CREE_LE
        defaultCommandeLignesShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creeLe is greater than DEFAULT_CREE_LE
        defaultCommandeLignesShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the commandeLignesList where creeLe is greater than SMALLER_CREE_LE
        defaultCommandeLignesShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllCommandeLignesByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creePar equals to DEFAULT_CREE_PAR
        defaultCommandeLignesShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the commandeLignesList where creePar equals to UPDATED_CREE_PAR
        defaultCommandeLignesShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creePar not equals to DEFAULT_CREE_PAR
        defaultCommandeLignesShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the commandeLignesList where creePar not equals to UPDATED_CREE_PAR
        defaultCommandeLignesShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultCommandeLignesShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the commandeLignesList where creePar equals to UPDATED_CREE_PAR
        defaultCommandeLignesShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creePar is not null
        defaultCommandeLignesShouldBeFound("creePar.specified=true");

        // Get all the commandeLignesList where creePar is null
        defaultCommandeLignesShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandeLignesByCreeParContainsSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creePar contains DEFAULT_CREE_PAR
        defaultCommandeLignesShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the commandeLignesList where creePar contains UPDATED_CREE_PAR
        defaultCommandeLignesShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where creePar does not contain DEFAULT_CREE_PAR
        defaultCommandeLignesShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the commandeLignesList where creePar does not contain UPDATED_CREE_PAR
        defaultCommandeLignesShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllCommandeLignesByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultCommandeLignesShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the commandeLignesList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultCommandeLignesShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultCommandeLignesShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the commandeLignesList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultCommandeLignesShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultCommandeLignesShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the commandeLignesList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultCommandeLignesShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifieLe is not null
        defaultCommandeLignesShouldBeFound("modifieLe.specified=true");

        // Get all the commandeLignesList where modifieLe is null
        defaultCommandeLignesShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultCommandeLignesShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the commandeLignesList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultCommandeLignesShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultCommandeLignesShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the commandeLignesList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultCommandeLignesShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultCommandeLignesShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the commandeLignesList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultCommandeLignesShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultCommandeLignesShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the commandeLignesList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultCommandeLignesShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllCommandeLignesByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultCommandeLignesShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the commandeLignesList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultCommandeLignesShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultCommandeLignesShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the commandeLignesList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultCommandeLignesShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultCommandeLignesShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the commandeLignesList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultCommandeLignesShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifiePar is not null
        defaultCommandeLignesShouldBeFound("modifiePar.specified=true");

        // Get all the commandeLignesList where modifiePar is null
        defaultCommandeLignesShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandeLignesByModifieParContainsSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultCommandeLignesShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the commandeLignesList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultCommandeLignesShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllCommandeLignesByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultCommandeLignesShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the commandeLignesList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultCommandeLignesShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllCommandeLignesByRefCommandeIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);
        Commande refCommande = CommandeResourceIT.createEntity(em);
        em.persist(refCommande);
        em.flush();
        commandeLignes.setRefCommande(refCommande);
        commandeLignesRepository.saveAndFlush(commandeLignes);
        Long refCommandeId = refCommande.getId();

        // Get all the commandeLignesList where refCommande equals to refCommandeId
        defaultCommandeLignesShouldBeFound("refCommandeId.equals=" + refCommandeId);

        // Get all the commandeLignesList where refCommande equals to refCommandeId + 1
        defaultCommandeLignesShouldNotBeFound("refCommandeId.equals=" + (refCommandeId + 1));
    }


    @Test
    @Transactional
    public void getAllCommandeLignesByRefProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);
        Produit refProduit = ProduitResourceIT.createEntity(em);
        em.persist(refProduit);
        em.flush();
        commandeLignes.setRefProduit(refProduit);
        commandeLignesRepository.saveAndFlush(commandeLignes);
        Long refProduitId = refProduit.getId();

        // Get all the commandeLignesList where refProduit equals to refProduitId
        defaultCommandeLignesShouldBeFound("refProduitId.equals=" + refProduitId);

        // Get all the commandeLignesList where refProduit equals to refProduitId + 1
        defaultCommandeLignesShouldNotBeFound("refProduitId.equals=" + (refProduitId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommandeLignesShouldBeFound(String filter) throws Exception {
        restCommandeLignesMockMvc.perform(get("/api/commande-lignes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeLignes.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].remise").value(hasItem(DEFAULT_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));

        // Check, that the count call also returns 1
        restCommandeLignesMockMvc.perform(get("/api/commande-lignes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommandeLignesShouldNotBeFound(String filter) throws Exception {
        restCommandeLignesMockMvc.perform(get("/api/commande-lignes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommandeLignesMockMvc.perform(get("/api/commande-lignes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCommandeLignes() throws Exception {
        // Get the commandeLignes
        restCommandeLignesMockMvc.perform(get("/api/commande-lignes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandeLignes() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        int databaseSizeBeforeUpdate = commandeLignesRepository.findAll().size();

        // Update the commandeLignes
        CommandeLignes updatedCommandeLignes = commandeLignesRepository.findById(commandeLignes.getId()).get();
        // Disconnect from session so that the updates on updatedCommandeLignes are not directly saved in db
        em.detach(updatedCommandeLignes);
        updatedCommandeLignes
            .quantite(UPDATED_QUANTITE)
            .prixHT(UPDATED_PRIX_HT)
            .remise(UPDATED_REMISE)
            .tva(UPDATED_TVA)
            .prixTTC(UPDATED_PRIX_TTC)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        CommandeLignesDTO commandeLignesDTO = commandeLignesMapper.toDto(updatedCommandeLignes);

        restCommandeLignesMockMvc.perform(put("/api/commande-lignes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLignesDTO)))
            .andExpect(status().isOk());

        // Validate the CommandeLignes in the database
        List<CommandeLignes> commandeLignesList = commandeLignesRepository.findAll();
        assertThat(commandeLignesList).hasSize(databaseSizeBeforeUpdate);
        CommandeLignes testCommandeLignes = commandeLignesList.get(commandeLignesList.size() - 1);
        assertThat(testCommandeLignes.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testCommandeLignes.getPrixHT()).isEqualTo(UPDATED_PRIX_HT);
        assertThat(testCommandeLignes.getRemise()).isEqualTo(UPDATED_REMISE);
        assertThat(testCommandeLignes.getTva()).isEqualTo(UPDATED_TVA);
        assertThat(testCommandeLignes.getPrixTTC()).isEqualTo(UPDATED_PRIX_TTC);
        assertThat(testCommandeLignes.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testCommandeLignes.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testCommandeLignes.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testCommandeLignes.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the CommandeLignes in Elasticsearch
        verify(mockCommandeLignesSearchRepository, times(1)).save(testCommandeLignes);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandeLignes() throws Exception {
        int databaseSizeBeforeUpdate = commandeLignesRepository.findAll().size();

        // Create the CommandeLignes
        CommandeLignesDTO commandeLignesDTO = commandeLignesMapper.toDto(commandeLignes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeLignesMockMvc.perform(put("/api/commande-lignes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLignesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeLignes in the database
        List<CommandeLignes> commandeLignesList = commandeLignesRepository.findAll();
        assertThat(commandeLignesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommandeLignes in Elasticsearch
        verify(mockCommandeLignesSearchRepository, times(0)).save(commandeLignes);
    }

    @Test
    @Transactional
    public void deleteCommandeLignes() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        int databaseSizeBeforeDelete = commandeLignesRepository.findAll().size();

        // Delete the commandeLignes
        restCommandeLignesMockMvc.perform(delete("/api/commande-lignes/{id}", commandeLignes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommandeLignes> commandeLignesList = commandeLignesRepository.findAll();
        assertThat(commandeLignesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommandeLignes in Elasticsearch
        verify(mockCommandeLignesSearchRepository, times(1)).deleteById(commandeLignes.getId());
    }

    @Test
    @Transactional
    public void searchCommandeLignes() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);
        when(mockCommandeLignesSearchRepository.search(queryStringQuery("id:" + commandeLignes.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commandeLignes), PageRequest.of(0, 1), 1));

        // Search the commandeLignes
        restCommandeLignesMockMvc.perform(get("/api/_search/commande-lignes?query=id:" + commandeLignes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeLignes.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].remise").value(hasItem(DEFAULT_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
