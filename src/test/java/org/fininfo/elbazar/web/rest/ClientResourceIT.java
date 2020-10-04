package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.domain.Adresse;
import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.domain.User;
import org.fininfo.elbazar.repository.ClientRepository;
import org.fininfo.elbazar.repository.search.ClientSearchRepository;
import org.fininfo.elbazar.service.ClientService;
import org.fininfo.elbazar.service.dto.ClientDTO;
import org.fininfo.elbazar.service.mapper.ClientMapper;
import org.fininfo.elbazar.service.dto.ClientCriteria;
import org.fininfo.elbazar.service.ClientQueryService;

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

import org.fininfo.elbazar.domain.enumeration.Civilite;
import org.fininfo.elbazar.domain.enumeration.RegMod;
import org.fininfo.elbazar.domain.enumeration.ProfileClient;
/**
 * Integration tests for the {@link ClientResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClientResourceIT {

    private static final Civilite DEFAULT_CIVILITE = Civilite.M;
    private static final Civilite UPDATED_CIVILITE = Civilite.Mme;

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_DE_NAISSANCE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_EMAIL = "CLj1@8.iOuIv";
    private static final String UPDATED_EMAIL = "llN@NmaY5.NiZNK";

    private static final Integer DEFAULT_MOBILE = 1;
    private static final Integer UPDATED_MOBILE = 2;
    private static final Integer SMALLER_MOBILE = 1 - 1;

    private static final RegMod DEFAULT_REGLEMENT = RegMod.CarteBancaire;
    private static final RegMod UPDATED_REGLEMENT = RegMod.Cash;

    private static final Boolean DEFAULT_ETAT = false;
    private static final Boolean UPDATED_ETAT = true;

    private static final LocalDate DEFAULT_INSCRIPTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INSCRIPTION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_INSCRIPTION = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DERNIERE_VISITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DERNIERE_VISITE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DERNIERE_VISITE = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_TOTAL_ACHAT = 1D;
    private static final Double UPDATED_TOTAL_ACHAT = 2D;
    private static final Double SMALLER_TOTAL_ACHAT = 1D - 1D;

    private static final ProfileClient DEFAULT_PROFILE = ProfileClient.Silver;
    private static final ProfileClient UPDATED_PROFILE = ProfileClient.Gold;

    private static final Integer DEFAULT_POINTS_FIDELITE = 1;
    private static final Integer UPDATED_POINTS_FIDELITE = 2;
    private static final Integer SMALLER_POINTS_FIDELITE = 1 - 1;

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
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ClientService clientService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.ClientSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClientSearchRepository mockClientSearchRepository;

    @Autowired
    private ClientQueryService clientQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .civilite(DEFAULT_CIVILITE)
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE)
            .email(DEFAULT_EMAIL)
            .mobile(DEFAULT_MOBILE)
            .reglement(DEFAULT_REGLEMENT)
            .etat(DEFAULT_ETAT)
            .inscription(DEFAULT_INSCRIPTION)
            .derniereVisite(DEFAULT_DERNIERE_VISITE)
            .totalAchat(DEFAULT_TOTAL_ACHAT)
            .profile(DEFAULT_PROFILE)
            .pointsFidelite(DEFAULT_POINTS_FIDELITE)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        client.setUser(user);
        return client;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .civilite(UPDATED_CIVILITE)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .email(UPDATED_EMAIL)
            .mobile(UPDATED_MOBILE)
            .reglement(UPDATED_REGLEMENT)
            .etat(UPDATED_ETAT)
            .inscription(UPDATED_INSCRIPTION)
            .derniereVisite(UPDATED_DERNIERE_VISITE)
            .totalAchat(UPDATED_TOTAL_ACHAT)
            .profile(UPDATED_PROFILE)
            .pointsFidelite(UPDATED_POINTS_FIDELITE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        client.setUser(user);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);
        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getCivilite()).isEqualTo(DEFAULT_CIVILITE);
        assertThat(testClient.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testClient.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testClient.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testClient.getReglement()).isEqualTo(DEFAULT_REGLEMENT);
        assertThat(testClient.isEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testClient.getInscription()).isEqualTo(DEFAULT_INSCRIPTION);
        assertThat(testClient.getDerniereVisite()).isEqualTo(DEFAULT_DERNIERE_VISITE);
        assertThat(testClient.getTotalAchat()).isEqualTo(DEFAULT_TOTAL_ACHAT);
        assertThat(testClient.getProfile()).isEqualTo(DEFAULT_PROFILE);
        assertThat(testClient.getPointsFidelite()).isEqualTo(DEFAULT_POINTS_FIDELITE);
        assertThat(testClient.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testClient.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testClient.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testClient.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the id for MapsId, the ids must be same
        assertThat(testClient.getId()).isEqualTo(testClient.getUser().getId());

        // Validate the Client in Elasticsearch
        verify(mockClientSearchRepository, times(1)).save(testClient);
    }

    @Test
    @Transactional
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId(1L);
        ClientDTO clientDTO = clientMapper.toDto(client);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);

        // Validate the Client in Elasticsearch
        verify(mockClientSearchRepository, times(0)).save(client);
    }

    @Test
    @Transactional
    public void updateClientMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        int databaseSizeBeforeCreate = clientRepository.findAll().size();


        // Load the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);

        // Update the User with new association value
//        updatedClient.setUser();
        ClientDTO updatedClientDTO = clientMapper.toDto(updatedClient);

        // Update the entity
        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientDTO)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
        Client testClient = clientList.get(clientList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testClient.getId()).isEqualTo(testClient.getUser().getId());

        // Validate the Client in Elasticsearch
        verify(mockClientSearchRepository, times(1)).save(client);
    }

    @Test
    @Transactional
    public void checkCiviliteIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setCivilite(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setPrenom(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setNom(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDeNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setDateDeNaissance(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setEmail(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setMobile(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReglementIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setReglement(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProfileIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setProfile(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].civilite").value(hasItem(DEFAULT_CIVILITE.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].reglement").value(hasItem(DEFAULT_REGLEMENT.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].inscription").value(hasItem(DEFAULT_INSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].derniereVisite").value(hasItem(DEFAULT_DERNIERE_VISITE.toString())))
            .andExpect(jsonPath("$.[*].totalAchat").value(hasItem(DEFAULT_TOTAL_ACHAT.doubleValue())))
            .andExpect(jsonPath("$.[*].profile").value(hasItem(DEFAULT_PROFILE.toString())))
            .andExpect(jsonPath("$.[*].pointsFidelite").value(hasItem(DEFAULT_POINTS_FIDELITE)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.civilite").value(DEFAULT_CIVILITE.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.reglement").value(DEFAULT_REGLEMENT.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.booleanValue()))
            .andExpect(jsonPath("$.inscription").value(DEFAULT_INSCRIPTION.toString()))
            .andExpect(jsonPath("$.derniereVisite").value(DEFAULT_DERNIERE_VISITE.toString()))
            .andExpect(jsonPath("$.totalAchat").value(DEFAULT_TOTAL_ACHAT.doubleValue()))
            .andExpect(jsonPath("$.profile").value(DEFAULT_PROFILE.toString()))
            .andExpect(jsonPath("$.pointsFidelite").value(DEFAULT_POINTS_FIDELITE))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }


    @Test
    @Transactional
    public void getClientsByIdFiltering() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        Long id = client.getId();

        defaultClientShouldBeFound("id.equals=" + id);
        defaultClientShouldNotBeFound("id.notEquals=" + id);

        defaultClientShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClientShouldNotBeFound("id.greaterThan=" + id);

        defaultClientShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClientShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClientsByCiviliteIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where civilite equals to DEFAULT_CIVILITE
        defaultClientShouldBeFound("civilite.equals=" + DEFAULT_CIVILITE);

        // Get all the clientList where civilite equals to UPDATED_CIVILITE
        defaultClientShouldNotBeFound("civilite.equals=" + UPDATED_CIVILITE);
    }

    @Test
    @Transactional
    public void getAllClientsByCiviliteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where civilite not equals to DEFAULT_CIVILITE
        defaultClientShouldNotBeFound("civilite.notEquals=" + DEFAULT_CIVILITE);

        // Get all the clientList where civilite not equals to UPDATED_CIVILITE
        defaultClientShouldBeFound("civilite.notEquals=" + UPDATED_CIVILITE);
    }

    @Test
    @Transactional
    public void getAllClientsByCiviliteIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where civilite in DEFAULT_CIVILITE or UPDATED_CIVILITE
        defaultClientShouldBeFound("civilite.in=" + DEFAULT_CIVILITE + "," + UPDATED_CIVILITE);

        // Get all the clientList where civilite equals to UPDATED_CIVILITE
        defaultClientShouldNotBeFound("civilite.in=" + UPDATED_CIVILITE);
    }

    @Test
    @Transactional
    public void getAllClientsByCiviliteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where civilite is not null
        defaultClientShouldBeFound("civilite.specified=true");

        // Get all the clientList where civilite is null
        defaultClientShouldNotBeFound("civilite.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where prenom equals to DEFAULT_PRENOM
        defaultClientShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the clientList where prenom equals to UPDATED_PRENOM
        defaultClientShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllClientsByPrenomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where prenom not equals to DEFAULT_PRENOM
        defaultClientShouldNotBeFound("prenom.notEquals=" + DEFAULT_PRENOM);

        // Get all the clientList where prenom not equals to UPDATED_PRENOM
        defaultClientShouldBeFound("prenom.notEquals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllClientsByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultClientShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the clientList where prenom equals to UPDATED_PRENOM
        defaultClientShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllClientsByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where prenom is not null
        defaultClientShouldBeFound("prenom.specified=true");

        // Get all the clientList where prenom is null
        defaultClientShouldNotBeFound("prenom.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByPrenomContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where prenom contains DEFAULT_PRENOM
        defaultClientShouldBeFound("prenom.contains=" + DEFAULT_PRENOM);

        // Get all the clientList where prenom contains UPDATED_PRENOM
        defaultClientShouldNotBeFound("prenom.contains=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllClientsByPrenomNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where prenom does not contain DEFAULT_PRENOM
        defaultClientShouldNotBeFound("prenom.doesNotContain=" + DEFAULT_PRENOM);

        // Get all the clientList where prenom does not contain UPDATED_PRENOM
        defaultClientShouldBeFound("prenom.doesNotContain=" + UPDATED_PRENOM);
    }


    @Test
    @Transactional
    public void getAllClientsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nom equals to DEFAULT_NOM
        defaultClientShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the clientList where nom equals to UPDATED_NOM
        defaultClientShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClientsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nom not equals to DEFAULT_NOM
        defaultClientShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the clientList where nom not equals to UPDATED_NOM
        defaultClientShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClientsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultClientShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the clientList where nom equals to UPDATED_NOM
        defaultClientShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClientsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nom is not null
        defaultClientShouldBeFound("nom.specified=true");

        // Get all the clientList where nom is null
        defaultClientShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByNomContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nom contains DEFAULT_NOM
        defaultClientShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the clientList where nom contains UPDATED_NOM
        defaultClientShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClientsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nom does not contain DEFAULT_NOM
        defaultClientShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the clientList where nom does not contain UPDATED_NOM
        defaultClientShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllClientsByDateDeNaissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateDeNaissance equals to DEFAULT_DATE_DE_NAISSANCE
        defaultClientShouldBeFound("dateDeNaissance.equals=" + DEFAULT_DATE_DE_NAISSANCE);

        // Get all the clientList where dateDeNaissance equals to UPDATED_DATE_DE_NAISSANCE
        defaultClientShouldNotBeFound("dateDeNaissance.equals=" + UPDATED_DATE_DE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllClientsByDateDeNaissanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateDeNaissance not equals to DEFAULT_DATE_DE_NAISSANCE
        defaultClientShouldNotBeFound("dateDeNaissance.notEquals=" + DEFAULT_DATE_DE_NAISSANCE);

        // Get all the clientList where dateDeNaissance not equals to UPDATED_DATE_DE_NAISSANCE
        defaultClientShouldBeFound("dateDeNaissance.notEquals=" + UPDATED_DATE_DE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllClientsByDateDeNaissanceIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateDeNaissance in DEFAULT_DATE_DE_NAISSANCE or UPDATED_DATE_DE_NAISSANCE
        defaultClientShouldBeFound("dateDeNaissance.in=" + DEFAULT_DATE_DE_NAISSANCE + "," + UPDATED_DATE_DE_NAISSANCE);

        // Get all the clientList where dateDeNaissance equals to UPDATED_DATE_DE_NAISSANCE
        defaultClientShouldNotBeFound("dateDeNaissance.in=" + UPDATED_DATE_DE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllClientsByDateDeNaissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateDeNaissance is not null
        defaultClientShouldBeFound("dateDeNaissance.specified=true");

        // Get all the clientList where dateDeNaissance is null
        defaultClientShouldNotBeFound("dateDeNaissance.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByDateDeNaissanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateDeNaissance is greater than or equal to DEFAULT_DATE_DE_NAISSANCE
        defaultClientShouldBeFound("dateDeNaissance.greaterThanOrEqual=" + DEFAULT_DATE_DE_NAISSANCE);

        // Get all the clientList where dateDeNaissance is greater than or equal to UPDATED_DATE_DE_NAISSANCE
        defaultClientShouldNotBeFound("dateDeNaissance.greaterThanOrEqual=" + UPDATED_DATE_DE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllClientsByDateDeNaissanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateDeNaissance is less than or equal to DEFAULT_DATE_DE_NAISSANCE
        defaultClientShouldBeFound("dateDeNaissance.lessThanOrEqual=" + DEFAULT_DATE_DE_NAISSANCE);

        // Get all the clientList where dateDeNaissance is less than or equal to SMALLER_DATE_DE_NAISSANCE
        defaultClientShouldNotBeFound("dateDeNaissance.lessThanOrEqual=" + SMALLER_DATE_DE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllClientsByDateDeNaissanceIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateDeNaissance is less than DEFAULT_DATE_DE_NAISSANCE
        defaultClientShouldNotBeFound("dateDeNaissance.lessThan=" + DEFAULT_DATE_DE_NAISSANCE);

        // Get all the clientList where dateDeNaissance is less than UPDATED_DATE_DE_NAISSANCE
        defaultClientShouldBeFound("dateDeNaissance.lessThan=" + UPDATED_DATE_DE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllClientsByDateDeNaissanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateDeNaissance is greater than DEFAULT_DATE_DE_NAISSANCE
        defaultClientShouldNotBeFound("dateDeNaissance.greaterThan=" + DEFAULT_DATE_DE_NAISSANCE);

        // Get all the clientList where dateDeNaissance is greater than SMALLER_DATE_DE_NAISSANCE
        defaultClientShouldBeFound("dateDeNaissance.greaterThan=" + SMALLER_DATE_DE_NAISSANCE);
    }


    @Test
    @Transactional
    public void getAllClientsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where email equals to DEFAULT_EMAIL
        defaultClientShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the clientList where email equals to UPDATED_EMAIL
        defaultClientShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where email not equals to DEFAULT_EMAIL
        defaultClientShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the clientList where email not equals to UPDATED_EMAIL
        defaultClientShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultClientShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the clientList where email equals to UPDATED_EMAIL
        defaultClientShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where email is not null
        defaultClientShouldBeFound("email.specified=true");

        // Get all the clientList where email is null
        defaultClientShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByEmailContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where email contains DEFAULT_EMAIL
        defaultClientShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the clientList where email contains UPDATED_EMAIL
        defaultClientShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where email does not contain DEFAULT_EMAIL
        defaultClientShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the clientList where email does not contain UPDATED_EMAIL
        defaultClientShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllClientsByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mobile equals to DEFAULT_MOBILE
        defaultClientShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the clientList where mobile equals to UPDATED_MOBILE
        defaultClientShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllClientsByMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mobile not equals to DEFAULT_MOBILE
        defaultClientShouldNotBeFound("mobile.notEquals=" + DEFAULT_MOBILE);

        // Get all the clientList where mobile not equals to UPDATED_MOBILE
        defaultClientShouldBeFound("mobile.notEquals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllClientsByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultClientShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the clientList where mobile equals to UPDATED_MOBILE
        defaultClientShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllClientsByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mobile is not null
        defaultClientShouldBeFound("mobile.specified=true");

        // Get all the clientList where mobile is null
        defaultClientShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByMobileIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mobile is greater than or equal to DEFAULT_MOBILE
        defaultClientShouldBeFound("mobile.greaterThanOrEqual=" + DEFAULT_MOBILE);

        // Get all the clientList where mobile is greater than or equal to UPDATED_MOBILE
        defaultClientShouldNotBeFound("mobile.greaterThanOrEqual=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllClientsByMobileIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mobile is less than or equal to DEFAULT_MOBILE
        defaultClientShouldBeFound("mobile.lessThanOrEqual=" + DEFAULT_MOBILE);

        // Get all the clientList where mobile is less than or equal to SMALLER_MOBILE
        defaultClientShouldNotBeFound("mobile.lessThanOrEqual=" + SMALLER_MOBILE);
    }

    @Test
    @Transactional
    public void getAllClientsByMobileIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mobile is less than DEFAULT_MOBILE
        defaultClientShouldNotBeFound("mobile.lessThan=" + DEFAULT_MOBILE);

        // Get all the clientList where mobile is less than UPDATED_MOBILE
        defaultClientShouldBeFound("mobile.lessThan=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllClientsByMobileIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mobile is greater than DEFAULT_MOBILE
        defaultClientShouldNotBeFound("mobile.greaterThan=" + DEFAULT_MOBILE);

        // Get all the clientList where mobile is greater than SMALLER_MOBILE
        defaultClientShouldBeFound("mobile.greaterThan=" + SMALLER_MOBILE);
    }


    @Test
    @Transactional
    public void getAllClientsByReglementIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where reglement equals to DEFAULT_REGLEMENT
        defaultClientShouldBeFound("reglement.equals=" + DEFAULT_REGLEMENT);

        // Get all the clientList where reglement equals to UPDATED_REGLEMENT
        defaultClientShouldNotBeFound("reglement.equals=" + UPDATED_REGLEMENT);
    }

    @Test
    @Transactional
    public void getAllClientsByReglementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where reglement not equals to DEFAULT_REGLEMENT
        defaultClientShouldNotBeFound("reglement.notEquals=" + DEFAULT_REGLEMENT);

        // Get all the clientList where reglement not equals to UPDATED_REGLEMENT
        defaultClientShouldBeFound("reglement.notEquals=" + UPDATED_REGLEMENT);
    }

    @Test
    @Transactional
    public void getAllClientsByReglementIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where reglement in DEFAULT_REGLEMENT or UPDATED_REGLEMENT
        defaultClientShouldBeFound("reglement.in=" + DEFAULT_REGLEMENT + "," + UPDATED_REGLEMENT);

        // Get all the clientList where reglement equals to UPDATED_REGLEMENT
        defaultClientShouldNotBeFound("reglement.in=" + UPDATED_REGLEMENT);
    }

    @Test
    @Transactional
    public void getAllClientsByReglementIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where reglement is not null
        defaultClientShouldBeFound("reglement.specified=true");

        // Get all the clientList where reglement is null
        defaultClientShouldNotBeFound("reglement.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where etat equals to DEFAULT_ETAT
        defaultClientShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the clientList where etat equals to UPDATED_ETAT
        defaultClientShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllClientsByEtatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where etat not equals to DEFAULT_ETAT
        defaultClientShouldNotBeFound("etat.notEquals=" + DEFAULT_ETAT);

        // Get all the clientList where etat not equals to UPDATED_ETAT
        defaultClientShouldBeFound("etat.notEquals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllClientsByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultClientShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the clientList where etat equals to UPDATED_ETAT
        defaultClientShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllClientsByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where etat is not null
        defaultClientShouldBeFound("etat.specified=true");

        // Get all the clientList where etat is null
        defaultClientShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByInscriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where inscription equals to DEFAULT_INSCRIPTION
        defaultClientShouldBeFound("inscription.equals=" + DEFAULT_INSCRIPTION);

        // Get all the clientList where inscription equals to UPDATED_INSCRIPTION
        defaultClientShouldNotBeFound("inscription.equals=" + UPDATED_INSCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClientsByInscriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where inscription not equals to DEFAULT_INSCRIPTION
        defaultClientShouldNotBeFound("inscription.notEquals=" + DEFAULT_INSCRIPTION);

        // Get all the clientList where inscription not equals to UPDATED_INSCRIPTION
        defaultClientShouldBeFound("inscription.notEquals=" + UPDATED_INSCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClientsByInscriptionIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where inscription in DEFAULT_INSCRIPTION or UPDATED_INSCRIPTION
        defaultClientShouldBeFound("inscription.in=" + DEFAULT_INSCRIPTION + "," + UPDATED_INSCRIPTION);

        // Get all the clientList where inscription equals to UPDATED_INSCRIPTION
        defaultClientShouldNotBeFound("inscription.in=" + UPDATED_INSCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClientsByInscriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where inscription is not null
        defaultClientShouldBeFound("inscription.specified=true");

        // Get all the clientList where inscription is null
        defaultClientShouldNotBeFound("inscription.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByInscriptionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where inscription is greater than or equal to DEFAULT_INSCRIPTION
        defaultClientShouldBeFound("inscription.greaterThanOrEqual=" + DEFAULT_INSCRIPTION);

        // Get all the clientList where inscription is greater than or equal to UPDATED_INSCRIPTION
        defaultClientShouldNotBeFound("inscription.greaterThanOrEqual=" + UPDATED_INSCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClientsByInscriptionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where inscription is less than or equal to DEFAULT_INSCRIPTION
        defaultClientShouldBeFound("inscription.lessThanOrEqual=" + DEFAULT_INSCRIPTION);

        // Get all the clientList where inscription is less than or equal to SMALLER_INSCRIPTION
        defaultClientShouldNotBeFound("inscription.lessThanOrEqual=" + SMALLER_INSCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClientsByInscriptionIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where inscription is less than DEFAULT_INSCRIPTION
        defaultClientShouldNotBeFound("inscription.lessThan=" + DEFAULT_INSCRIPTION);

        // Get all the clientList where inscription is less than UPDATED_INSCRIPTION
        defaultClientShouldBeFound("inscription.lessThan=" + UPDATED_INSCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClientsByInscriptionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where inscription is greater than DEFAULT_INSCRIPTION
        defaultClientShouldNotBeFound("inscription.greaterThan=" + DEFAULT_INSCRIPTION);

        // Get all the clientList where inscription is greater than SMALLER_INSCRIPTION
        defaultClientShouldBeFound("inscription.greaterThan=" + SMALLER_INSCRIPTION);
    }


    @Test
    @Transactional
    public void getAllClientsByDerniereVisiteIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where derniereVisite equals to DEFAULT_DERNIERE_VISITE
        defaultClientShouldBeFound("derniereVisite.equals=" + DEFAULT_DERNIERE_VISITE);

        // Get all the clientList where derniereVisite equals to UPDATED_DERNIERE_VISITE
        defaultClientShouldNotBeFound("derniereVisite.equals=" + UPDATED_DERNIERE_VISITE);
    }

    @Test
    @Transactional
    public void getAllClientsByDerniereVisiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where derniereVisite not equals to DEFAULT_DERNIERE_VISITE
        defaultClientShouldNotBeFound("derniereVisite.notEquals=" + DEFAULT_DERNIERE_VISITE);

        // Get all the clientList where derniereVisite not equals to UPDATED_DERNIERE_VISITE
        defaultClientShouldBeFound("derniereVisite.notEquals=" + UPDATED_DERNIERE_VISITE);
    }

    @Test
    @Transactional
    public void getAllClientsByDerniereVisiteIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where derniereVisite in DEFAULT_DERNIERE_VISITE or UPDATED_DERNIERE_VISITE
        defaultClientShouldBeFound("derniereVisite.in=" + DEFAULT_DERNIERE_VISITE + "," + UPDATED_DERNIERE_VISITE);

        // Get all the clientList where derniereVisite equals to UPDATED_DERNIERE_VISITE
        defaultClientShouldNotBeFound("derniereVisite.in=" + UPDATED_DERNIERE_VISITE);
    }

    @Test
    @Transactional
    public void getAllClientsByDerniereVisiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where derniereVisite is not null
        defaultClientShouldBeFound("derniereVisite.specified=true");

        // Get all the clientList where derniereVisite is null
        defaultClientShouldNotBeFound("derniereVisite.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByDerniereVisiteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where derniereVisite is greater than or equal to DEFAULT_DERNIERE_VISITE
        defaultClientShouldBeFound("derniereVisite.greaterThanOrEqual=" + DEFAULT_DERNIERE_VISITE);

        // Get all the clientList where derniereVisite is greater than or equal to UPDATED_DERNIERE_VISITE
        defaultClientShouldNotBeFound("derniereVisite.greaterThanOrEqual=" + UPDATED_DERNIERE_VISITE);
    }

    @Test
    @Transactional
    public void getAllClientsByDerniereVisiteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where derniereVisite is less than or equal to DEFAULT_DERNIERE_VISITE
        defaultClientShouldBeFound("derniereVisite.lessThanOrEqual=" + DEFAULT_DERNIERE_VISITE);

        // Get all the clientList where derniereVisite is less than or equal to SMALLER_DERNIERE_VISITE
        defaultClientShouldNotBeFound("derniereVisite.lessThanOrEqual=" + SMALLER_DERNIERE_VISITE);
    }

    @Test
    @Transactional
    public void getAllClientsByDerniereVisiteIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where derniereVisite is less than DEFAULT_DERNIERE_VISITE
        defaultClientShouldNotBeFound("derniereVisite.lessThan=" + DEFAULT_DERNIERE_VISITE);

        // Get all the clientList where derniereVisite is less than UPDATED_DERNIERE_VISITE
        defaultClientShouldBeFound("derniereVisite.lessThan=" + UPDATED_DERNIERE_VISITE);
    }

    @Test
    @Transactional
    public void getAllClientsByDerniereVisiteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where derniereVisite is greater than DEFAULT_DERNIERE_VISITE
        defaultClientShouldNotBeFound("derniereVisite.greaterThan=" + DEFAULT_DERNIERE_VISITE);

        // Get all the clientList where derniereVisite is greater than SMALLER_DERNIERE_VISITE
        defaultClientShouldBeFound("derniereVisite.greaterThan=" + SMALLER_DERNIERE_VISITE);
    }


    @Test
    @Transactional
    public void getAllClientsByTotalAchatIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where totalAchat equals to DEFAULT_TOTAL_ACHAT
        defaultClientShouldBeFound("totalAchat.equals=" + DEFAULT_TOTAL_ACHAT);

        // Get all the clientList where totalAchat equals to UPDATED_TOTAL_ACHAT
        defaultClientShouldNotBeFound("totalAchat.equals=" + UPDATED_TOTAL_ACHAT);
    }

    @Test
    @Transactional
    public void getAllClientsByTotalAchatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where totalAchat not equals to DEFAULT_TOTAL_ACHAT
        defaultClientShouldNotBeFound("totalAchat.notEquals=" + DEFAULT_TOTAL_ACHAT);

        // Get all the clientList where totalAchat not equals to UPDATED_TOTAL_ACHAT
        defaultClientShouldBeFound("totalAchat.notEquals=" + UPDATED_TOTAL_ACHAT);
    }

    @Test
    @Transactional
    public void getAllClientsByTotalAchatIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where totalAchat in DEFAULT_TOTAL_ACHAT or UPDATED_TOTAL_ACHAT
        defaultClientShouldBeFound("totalAchat.in=" + DEFAULT_TOTAL_ACHAT + "," + UPDATED_TOTAL_ACHAT);

        // Get all the clientList where totalAchat equals to UPDATED_TOTAL_ACHAT
        defaultClientShouldNotBeFound("totalAchat.in=" + UPDATED_TOTAL_ACHAT);
    }

    @Test
    @Transactional
    public void getAllClientsByTotalAchatIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where totalAchat is not null
        defaultClientShouldBeFound("totalAchat.specified=true");

        // Get all the clientList where totalAchat is null
        defaultClientShouldNotBeFound("totalAchat.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByTotalAchatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where totalAchat is greater than or equal to DEFAULT_TOTAL_ACHAT
        defaultClientShouldBeFound("totalAchat.greaterThanOrEqual=" + DEFAULT_TOTAL_ACHAT);

        // Get all the clientList where totalAchat is greater than or equal to UPDATED_TOTAL_ACHAT
        defaultClientShouldNotBeFound("totalAchat.greaterThanOrEqual=" + UPDATED_TOTAL_ACHAT);
    }

    @Test
    @Transactional
    public void getAllClientsByTotalAchatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where totalAchat is less than or equal to DEFAULT_TOTAL_ACHAT
        defaultClientShouldBeFound("totalAchat.lessThanOrEqual=" + DEFAULT_TOTAL_ACHAT);

        // Get all the clientList where totalAchat is less than or equal to SMALLER_TOTAL_ACHAT
        defaultClientShouldNotBeFound("totalAchat.lessThanOrEqual=" + SMALLER_TOTAL_ACHAT);
    }

    @Test
    @Transactional
    public void getAllClientsByTotalAchatIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where totalAchat is less than DEFAULT_TOTAL_ACHAT
        defaultClientShouldNotBeFound("totalAchat.lessThan=" + DEFAULT_TOTAL_ACHAT);

        // Get all the clientList where totalAchat is less than UPDATED_TOTAL_ACHAT
        defaultClientShouldBeFound("totalAchat.lessThan=" + UPDATED_TOTAL_ACHAT);
    }

    @Test
    @Transactional
    public void getAllClientsByTotalAchatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where totalAchat is greater than DEFAULT_TOTAL_ACHAT
        defaultClientShouldNotBeFound("totalAchat.greaterThan=" + DEFAULT_TOTAL_ACHAT);

        // Get all the clientList where totalAchat is greater than SMALLER_TOTAL_ACHAT
        defaultClientShouldBeFound("totalAchat.greaterThan=" + SMALLER_TOTAL_ACHAT);
    }


    @Test
    @Transactional
    public void getAllClientsByProfileIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where profile equals to DEFAULT_PROFILE
        defaultClientShouldBeFound("profile.equals=" + DEFAULT_PROFILE);

        // Get all the clientList where profile equals to UPDATED_PROFILE
        defaultClientShouldNotBeFound("profile.equals=" + UPDATED_PROFILE);
    }

    @Test
    @Transactional
    public void getAllClientsByProfileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where profile not equals to DEFAULT_PROFILE
        defaultClientShouldNotBeFound("profile.notEquals=" + DEFAULT_PROFILE);

        // Get all the clientList where profile not equals to UPDATED_PROFILE
        defaultClientShouldBeFound("profile.notEquals=" + UPDATED_PROFILE);
    }

    @Test
    @Transactional
    public void getAllClientsByProfileIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where profile in DEFAULT_PROFILE or UPDATED_PROFILE
        defaultClientShouldBeFound("profile.in=" + DEFAULT_PROFILE + "," + UPDATED_PROFILE);

        // Get all the clientList where profile equals to UPDATED_PROFILE
        defaultClientShouldNotBeFound("profile.in=" + UPDATED_PROFILE);
    }

    @Test
    @Transactional
    public void getAllClientsByProfileIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where profile is not null
        defaultClientShouldBeFound("profile.specified=true");

        // Get all the clientList where profile is null
        defaultClientShouldNotBeFound("profile.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByPointsFideliteIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pointsFidelite equals to DEFAULT_POINTS_FIDELITE
        defaultClientShouldBeFound("pointsFidelite.equals=" + DEFAULT_POINTS_FIDELITE);

        // Get all the clientList where pointsFidelite equals to UPDATED_POINTS_FIDELITE
        defaultClientShouldNotBeFound("pointsFidelite.equals=" + UPDATED_POINTS_FIDELITE);
    }

    @Test
    @Transactional
    public void getAllClientsByPointsFideliteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pointsFidelite not equals to DEFAULT_POINTS_FIDELITE
        defaultClientShouldNotBeFound("pointsFidelite.notEquals=" + DEFAULT_POINTS_FIDELITE);

        // Get all the clientList where pointsFidelite not equals to UPDATED_POINTS_FIDELITE
        defaultClientShouldBeFound("pointsFidelite.notEquals=" + UPDATED_POINTS_FIDELITE);
    }

    @Test
    @Transactional
    public void getAllClientsByPointsFideliteIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pointsFidelite in DEFAULT_POINTS_FIDELITE or UPDATED_POINTS_FIDELITE
        defaultClientShouldBeFound("pointsFidelite.in=" + DEFAULT_POINTS_FIDELITE + "," + UPDATED_POINTS_FIDELITE);

        // Get all the clientList where pointsFidelite equals to UPDATED_POINTS_FIDELITE
        defaultClientShouldNotBeFound("pointsFidelite.in=" + UPDATED_POINTS_FIDELITE);
    }

    @Test
    @Transactional
    public void getAllClientsByPointsFideliteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pointsFidelite is not null
        defaultClientShouldBeFound("pointsFidelite.specified=true");

        // Get all the clientList where pointsFidelite is null
        defaultClientShouldNotBeFound("pointsFidelite.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByPointsFideliteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pointsFidelite is greater than or equal to DEFAULT_POINTS_FIDELITE
        defaultClientShouldBeFound("pointsFidelite.greaterThanOrEqual=" + DEFAULT_POINTS_FIDELITE);

        // Get all the clientList where pointsFidelite is greater than or equal to UPDATED_POINTS_FIDELITE
        defaultClientShouldNotBeFound("pointsFidelite.greaterThanOrEqual=" + UPDATED_POINTS_FIDELITE);
    }

    @Test
    @Transactional
    public void getAllClientsByPointsFideliteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pointsFidelite is less than or equal to DEFAULT_POINTS_FIDELITE
        defaultClientShouldBeFound("pointsFidelite.lessThanOrEqual=" + DEFAULT_POINTS_FIDELITE);

        // Get all the clientList where pointsFidelite is less than or equal to SMALLER_POINTS_FIDELITE
        defaultClientShouldNotBeFound("pointsFidelite.lessThanOrEqual=" + SMALLER_POINTS_FIDELITE);
    }

    @Test
    @Transactional
    public void getAllClientsByPointsFideliteIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pointsFidelite is less than DEFAULT_POINTS_FIDELITE
        defaultClientShouldNotBeFound("pointsFidelite.lessThan=" + DEFAULT_POINTS_FIDELITE);

        // Get all the clientList where pointsFidelite is less than UPDATED_POINTS_FIDELITE
        defaultClientShouldBeFound("pointsFidelite.lessThan=" + UPDATED_POINTS_FIDELITE);
    }

    @Test
    @Transactional
    public void getAllClientsByPointsFideliteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pointsFidelite is greater than DEFAULT_POINTS_FIDELITE
        defaultClientShouldNotBeFound("pointsFidelite.greaterThan=" + DEFAULT_POINTS_FIDELITE);

        // Get all the clientList where pointsFidelite is greater than SMALLER_POINTS_FIDELITE
        defaultClientShouldBeFound("pointsFidelite.greaterThan=" + SMALLER_POINTS_FIDELITE);
    }


    @Test
    @Transactional
    public void getAllClientsByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creeLe equals to DEFAULT_CREE_LE
        defaultClientShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the clientList where creeLe equals to UPDATED_CREE_LE
        defaultClientShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllClientsByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creeLe not equals to DEFAULT_CREE_LE
        defaultClientShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the clientList where creeLe not equals to UPDATED_CREE_LE
        defaultClientShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllClientsByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultClientShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the clientList where creeLe equals to UPDATED_CREE_LE
        defaultClientShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllClientsByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creeLe is not null
        defaultClientShouldBeFound("creeLe.specified=true");

        // Get all the clientList where creeLe is null
        defaultClientShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultClientShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the clientList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultClientShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllClientsByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultClientShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the clientList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultClientShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllClientsByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creeLe is less than DEFAULT_CREE_LE
        defaultClientShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the clientList where creeLe is less than UPDATED_CREE_LE
        defaultClientShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllClientsByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creeLe is greater than DEFAULT_CREE_LE
        defaultClientShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the clientList where creeLe is greater than SMALLER_CREE_LE
        defaultClientShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllClientsByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creePar equals to DEFAULT_CREE_PAR
        defaultClientShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the clientList where creePar equals to UPDATED_CREE_PAR
        defaultClientShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllClientsByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creePar not equals to DEFAULT_CREE_PAR
        defaultClientShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the clientList where creePar not equals to UPDATED_CREE_PAR
        defaultClientShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllClientsByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultClientShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the clientList where creePar equals to UPDATED_CREE_PAR
        defaultClientShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllClientsByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creePar is not null
        defaultClientShouldBeFound("creePar.specified=true");

        // Get all the clientList where creePar is null
        defaultClientShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByCreeParContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creePar contains DEFAULT_CREE_PAR
        defaultClientShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the clientList where creePar contains UPDATED_CREE_PAR
        defaultClientShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllClientsByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where creePar does not contain DEFAULT_CREE_PAR
        defaultClientShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the clientList where creePar does not contain UPDATED_CREE_PAR
        defaultClientShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllClientsByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultClientShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the clientList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultClientShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllClientsByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultClientShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the clientList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultClientShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllClientsByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultClientShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the clientList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultClientShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllClientsByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifieLe is not null
        defaultClientShouldBeFound("modifieLe.specified=true");

        // Get all the clientList where modifieLe is null
        defaultClientShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultClientShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the clientList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultClientShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllClientsByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultClientShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the clientList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultClientShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllClientsByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultClientShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the clientList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultClientShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllClientsByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultClientShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the clientList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultClientShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllClientsByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultClientShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the clientList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultClientShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllClientsByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultClientShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the clientList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultClientShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllClientsByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultClientShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the clientList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultClientShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllClientsByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifiePar is not null
        defaultClientShouldBeFound("modifiePar.specified=true");

        // Get all the clientList where modifiePar is null
        defaultClientShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByModifieParContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultClientShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the clientList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultClientShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllClientsByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultClientShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the clientList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultClientShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllClientsByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        Adresse adresse = AdresseResourceIT.createEntity(em);
        em.persist(adresse);
        em.flush();
        client.addAdresse(adresse);
        clientRepository.saveAndFlush(client);
        Long adresseId = adresse.getId();

        // Get all the clientList where adresse equals to adresseId
        defaultClientShouldBeFound("adresseId.equals=" + adresseId);

        // Get all the clientList where adresse equals to adresseId + 1
        defaultClientShouldNotBeFound("adresseId.equals=" + (adresseId + 1));
    }


    @Test
    @Transactional
    public void getAllClientsByCommandeIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        Commande commande = CommandeResourceIT.createEntity(em);
        em.persist(commande);
        em.flush();
        client.addCommande(commande);
        clientRepository.saveAndFlush(client);
        Long commandeId = commande.getId();

        // Get all the clientList where commande equals to commandeId
        defaultClientShouldBeFound("commandeId.equals=" + commandeId);

        // Get all the clientList where commande equals to commandeId + 1
        defaultClientShouldNotBeFound("commandeId.equals=" + (commandeId + 1));
    }


    @Test
    @Transactional
    public void getAllClientsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = client.getUser();
        clientRepository.saveAndFlush(client);
        Long userId = user.getId();

        // Get all the clientList where user equals to userId
        defaultClientShouldBeFound("userId.equals=" + userId);

        // Get all the clientList where user equals to userId + 1
        defaultClientShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClientShouldBeFound(String filter) throws Exception {
        restClientMockMvc.perform(get("/api/clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].civilite").value(hasItem(DEFAULT_CIVILITE.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].reglement").value(hasItem(DEFAULT_REGLEMENT.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].inscription").value(hasItem(DEFAULT_INSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].derniereVisite").value(hasItem(DEFAULT_DERNIERE_VISITE.toString())))
            .andExpect(jsonPath("$.[*].totalAchat").value(hasItem(DEFAULT_TOTAL_ACHAT.doubleValue())))
            .andExpect(jsonPath("$.[*].profile").value(hasItem(DEFAULT_PROFILE.toString())))
            .andExpect(jsonPath("$.[*].pointsFidelite").value(hasItem(DEFAULT_POINTS_FIDELITE)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));

        // Check, that the count call also returns 1
        restClientMockMvc.perform(get("/api/clients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClientShouldNotBeFound(String filter) throws Exception {
        restClientMockMvc.perform(get("/api/clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientMockMvc.perform(get("/api/clients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .civilite(UPDATED_CIVILITE)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .email(UPDATED_EMAIL)
            .mobile(UPDATED_MOBILE)
            .reglement(UPDATED_REGLEMENT)
            .etat(UPDATED_ETAT)
            .inscription(UPDATED_INSCRIPTION)
            .derniereVisite(UPDATED_DERNIERE_VISITE)
            .totalAchat(UPDATED_TOTAL_ACHAT)
            .profile(UPDATED_PROFILE)
            .pointsFidelite(UPDATED_POINTS_FIDELITE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        ClientDTO clientDTO = clientMapper.toDto(updatedClient);

        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testClient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testClient.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testClient.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testClient.getReglement()).isEqualTo(UPDATED_REGLEMENT);
        assertThat(testClient.isEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testClient.getInscription()).isEqualTo(UPDATED_INSCRIPTION);
        assertThat(testClient.getDerniereVisite()).isEqualTo(UPDATED_DERNIERE_VISITE);
        assertThat(testClient.getTotalAchat()).isEqualTo(UPDATED_TOTAL_ACHAT);
        assertThat(testClient.getProfile()).isEqualTo(UPDATED_PROFILE);
        assertThat(testClient.getPointsFidelite()).isEqualTo(UPDATED_POINTS_FIDELITE);
        assertThat(testClient.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testClient.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testClient.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testClient.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the Client in Elasticsearch
        verify(mockClientSearchRepository, times(1)).save(testClient);
    }

    @Test
    @Transactional
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Client in Elasticsearch
        verify(mockClientSearchRepository, times(0)).save(client);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Client in Elasticsearch
        verify(mockClientSearchRepository, times(1)).deleteById(client.getId());
    }

    @Test
    @Transactional
    public void searchClient() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        clientRepository.saveAndFlush(client);
        when(mockClientSearchRepository.search(queryStringQuery("id:" + client.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(client), PageRequest.of(0, 1), 1));

        // Search the client
        restClientMockMvc.perform(get("/api/_search/clients?query=id:" + client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].civilite").value(hasItem(DEFAULT_CIVILITE.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].reglement").value(hasItem(DEFAULT_REGLEMENT.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].inscription").value(hasItem(DEFAULT_INSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].derniereVisite").value(hasItem(DEFAULT_DERNIERE_VISITE.toString())))
            .andExpect(jsonPath("$.[*].totalAchat").value(hasItem(DEFAULT_TOTAL_ACHAT.doubleValue())))
            .andExpect(jsonPath("$.[*].profile").value(hasItem(DEFAULT_PROFILE.toString())))
            .andExpect(jsonPath("$.[*].pointsFidelite").value(hasItem(DEFAULT_POINTS_FIDELITE)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
