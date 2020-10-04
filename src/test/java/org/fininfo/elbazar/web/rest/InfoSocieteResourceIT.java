package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.InfoSociete;
import org.fininfo.elbazar.repository.InfoSocieteRepository;
import org.fininfo.elbazar.repository.search.InfoSocieteSearchRepository;
import org.fininfo.elbazar.service.InfoSocieteService;
import org.fininfo.elbazar.service.dto.InfoSocieteDTO;
import org.fininfo.elbazar.service.mapper.InfoSocieteMapper;
import org.fininfo.elbazar.service.dto.InfoSocieteCriteria;
import org.fininfo.elbazar.service.InfoSocieteQueryService;

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
 * Integration tests for the {@link InfoSocieteResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InfoSocieteResourceIT {

    private static final String DEFAULT_NOM_SOCIETE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_SOCIETE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TEL_1 = 1;
    private static final Integer UPDATED_TEL_1 = 2;
    private static final Integer SMALLER_TEL_1 = 1 - 1;

    private static final Integer DEFAULT_TEL_2 = 1;
    private static final Integer UPDATED_TEL_2 = 2;
    private static final Integer SMALLER_TEL_2 = 1 - 1;

    private static final Integer DEFAULT_TEL_3 = 1;
    private static final Integer UPDATED_TEL_3 = 2;
    private static final Integer SMALLER_TEL_3 = 1 - 1;

    private static final String DEFAULT_EMAIL_1 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_2 = "BBBBBBBBBB";

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

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_YOUTUBE = "AAAAAAAAAA";
    private static final String UPDATED_YOUTUBE = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER = "BBBBBBBBBB";

    private static final String DEFAULT_TIKTOK = "AAAAAAAAAA";
    private static final String UPDATED_TIKTOK = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULE_FISCAL = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE_FISCAL = "BBBBBBBBBB";

    private static final Double DEFAULT_VALEUR_MIN_PANIER = 1D;
    private static final Double UPDATED_VALEUR_MIN_PANIER = 2D;
    private static final Double SMALLER_VALEUR_MIN_PANIER = 1D - 1D;

    @Autowired
    private InfoSocieteRepository infoSocieteRepository;

    @Autowired
    private InfoSocieteMapper infoSocieteMapper;

    @Autowired
    private InfoSocieteService infoSocieteService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.InfoSocieteSearchRepositoryMockConfiguration
     */
    @Autowired
    private InfoSocieteSearchRepository mockInfoSocieteSearchRepository;

    @Autowired
    private InfoSocieteQueryService infoSocieteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInfoSocieteMockMvc;

    private InfoSociete infoSociete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoSociete createEntity(EntityManager em) {
        InfoSociete infoSociete = new InfoSociete()
            .nomSociete(DEFAULT_NOM_SOCIETE)
            .adresse(DEFAULT_ADRESSE)
            .tel1(DEFAULT_TEL_1)
            .tel2(DEFAULT_TEL_2)
            .tel3(DEFAULT_TEL_3)
            .email1(DEFAULT_EMAIL_1)
            .email2(DEFAULT_EMAIL_2)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR)
            .facebook(DEFAULT_FACEBOOK)
            .youtube(DEFAULT_YOUTUBE)
            .instagram(DEFAULT_INSTAGRAM)
            .twitter(DEFAULT_TWITTER)
            .tiktok(DEFAULT_TIKTOK)
            .matriculeFiscal(DEFAULT_MATRICULE_FISCAL)
            .valeurMinPanier(DEFAULT_VALEUR_MIN_PANIER);
        return infoSociete;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoSociete createUpdatedEntity(EntityManager em) {
        InfoSociete infoSociete = new InfoSociete()
            .nomSociete(UPDATED_NOM_SOCIETE)
            .adresse(UPDATED_ADRESSE)
            .tel1(UPDATED_TEL_1)
            .tel2(UPDATED_TEL_2)
            .tel3(UPDATED_TEL_3)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR)
            .facebook(UPDATED_FACEBOOK)
            .youtube(UPDATED_YOUTUBE)
            .instagram(UPDATED_INSTAGRAM)
            .twitter(UPDATED_TWITTER)
            .tiktok(UPDATED_TIKTOK)
            .matriculeFiscal(UPDATED_MATRICULE_FISCAL)
            .valeurMinPanier(UPDATED_VALEUR_MIN_PANIER);
        return infoSociete;
    }

    @BeforeEach
    public void initTest() {
        infoSociete = createEntity(em);
    }

    @Test
    @Transactional
    public void createInfoSociete() throws Exception {
        int databaseSizeBeforeCreate = infoSocieteRepository.findAll().size();
        // Create the InfoSociete
        InfoSocieteDTO infoSocieteDTO = infoSocieteMapper.toDto(infoSociete);
        restInfoSocieteMockMvc.perform(post("/api/info-societes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoSocieteDTO)))
            .andExpect(status().isCreated());

        // Validate the InfoSociete in the database
        List<InfoSociete> infoSocieteList = infoSocieteRepository.findAll();
        assertThat(infoSocieteList).hasSize(databaseSizeBeforeCreate + 1);
        InfoSociete testInfoSociete = infoSocieteList.get(infoSocieteList.size() - 1);
        assertThat(testInfoSociete.getNomSociete()).isEqualTo(DEFAULT_NOM_SOCIETE);
        assertThat(testInfoSociete.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testInfoSociete.getTel1()).isEqualTo(DEFAULT_TEL_1);
        assertThat(testInfoSociete.getTel2()).isEqualTo(DEFAULT_TEL_2);
        assertThat(testInfoSociete.getTel3()).isEqualTo(DEFAULT_TEL_3);
        assertThat(testInfoSociete.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testInfoSociete.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testInfoSociete.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testInfoSociete.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testInfoSociete.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testInfoSociete.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);
        assertThat(testInfoSociete.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testInfoSociete.getYoutube()).isEqualTo(DEFAULT_YOUTUBE);
        assertThat(testInfoSociete.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testInfoSociete.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testInfoSociete.getTiktok()).isEqualTo(DEFAULT_TIKTOK);
        assertThat(testInfoSociete.getMatriculeFiscal()).isEqualTo(DEFAULT_MATRICULE_FISCAL);
        assertThat(testInfoSociete.getValeurMinPanier()).isEqualTo(DEFAULT_VALEUR_MIN_PANIER);

        // Validate the InfoSociete in Elasticsearch
        verify(mockInfoSocieteSearchRepository, times(1)).save(testInfoSociete);
    }

    @Test
    @Transactional
    public void createInfoSocieteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infoSocieteRepository.findAll().size();

        // Create the InfoSociete with an existing ID
        infoSociete.setId(1L);
        InfoSocieteDTO infoSocieteDTO = infoSocieteMapper.toDto(infoSociete);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoSocieteMockMvc.perform(post("/api/info-societes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoSocieteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InfoSociete in the database
        List<InfoSociete> infoSocieteList = infoSocieteRepository.findAll();
        assertThat(infoSocieteList).hasSize(databaseSizeBeforeCreate);

        // Validate the InfoSociete in Elasticsearch
        verify(mockInfoSocieteSearchRepository, times(0)).save(infoSociete);
    }


    @Test
    @Transactional
    public void checkNomSocieteIsRequired() throws Exception {
        int databaseSizeBeforeTest = infoSocieteRepository.findAll().size();
        // set the field null
        infoSociete.setNomSociete(null);

        // Create the InfoSociete, which fails.
        InfoSocieteDTO infoSocieteDTO = infoSocieteMapper.toDto(infoSociete);


        restInfoSocieteMockMvc.perform(post("/api/info-societes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoSocieteDTO)))
            .andExpect(status().isBadRequest());

        List<InfoSociete> infoSocieteList = infoSocieteRepository.findAll();
        assertThat(infoSocieteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = infoSocieteRepository.findAll().size();
        // set the field null
        infoSociete.setAdresse(null);

        // Create the InfoSociete, which fails.
        InfoSocieteDTO infoSocieteDTO = infoSocieteMapper.toDto(infoSociete);


        restInfoSocieteMockMvc.perform(post("/api/info-societes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoSocieteDTO)))
            .andExpect(status().isBadRequest());

        List<InfoSociete> infoSocieteList = infoSocieteRepository.findAll();
        assertThat(infoSocieteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTel1IsRequired() throws Exception {
        int databaseSizeBeforeTest = infoSocieteRepository.findAll().size();
        // set the field null
        infoSociete.setTel1(null);

        // Create the InfoSociete, which fails.
        InfoSocieteDTO infoSocieteDTO = infoSocieteMapper.toDto(infoSociete);


        restInfoSocieteMockMvc.perform(post("/api/info-societes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoSocieteDTO)))
            .andExpect(status().isBadRequest());

        List<InfoSociete> infoSocieteList = infoSocieteRepository.findAll();
        assertThat(infoSocieteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTel2IsRequired() throws Exception {
        int databaseSizeBeforeTest = infoSocieteRepository.findAll().size();
        // set the field null
        infoSociete.setTel2(null);

        // Create the InfoSociete, which fails.
        InfoSocieteDTO infoSocieteDTO = infoSocieteMapper.toDto(infoSociete);


        restInfoSocieteMockMvc.perform(post("/api/info-societes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoSocieteDTO)))
            .andExpect(status().isBadRequest());

        List<InfoSociete> infoSocieteList = infoSocieteRepository.findAll();
        assertThat(infoSocieteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTel3IsRequired() throws Exception {
        int databaseSizeBeforeTest = infoSocieteRepository.findAll().size();
        // set the field null
        infoSociete.setTel3(null);

        // Create the InfoSociete, which fails.
        InfoSocieteDTO infoSocieteDTO = infoSocieteMapper.toDto(infoSociete);


        restInfoSocieteMockMvc.perform(post("/api/info-societes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoSocieteDTO)))
            .andExpect(status().isBadRequest());

        List<InfoSociete> infoSocieteList = infoSocieteRepository.findAll();
        assertThat(infoSocieteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValeurMinPanierIsRequired() throws Exception {
        int databaseSizeBeforeTest = infoSocieteRepository.findAll().size();
        // set the field null
        infoSociete.setValeurMinPanier(null);

        // Create the InfoSociete, which fails.
        InfoSocieteDTO infoSocieteDTO = infoSocieteMapper.toDto(infoSociete);


        restInfoSocieteMockMvc.perform(post("/api/info-societes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoSocieteDTO)))
            .andExpect(status().isBadRequest());

        List<InfoSociete> infoSocieteList = infoSocieteRepository.findAll();
        assertThat(infoSocieteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInfoSocietes() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList
        restInfoSocieteMockMvc.perform(get("/api/info-societes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoSociete.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomSociete").value(hasItem(DEFAULT_NOM_SOCIETE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].tel1").value(hasItem(DEFAULT_TEL_1)))
            .andExpect(jsonPath("$.[*].tel2").value(hasItem(DEFAULT_TEL_2)))
            .andExpect(jsonPath("$.[*].tel3").value(hasItem(DEFAULT_TEL_3)))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1)))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].youtube").value(hasItem(DEFAULT_YOUTUBE)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)))
            .andExpect(jsonPath("$.[*].tiktok").value(hasItem(DEFAULT_TIKTOK)))
            .andExpect(jsonPath("$.[*].matriculeFiscal").value(hasItem(DEFAULT_MATRICULE_FISCAL)))
            .andExpect(jsonPath("$.[*].valeurMinPanier").value(hasItem(DEFAULT_VALEUR_MIN_PANIER.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getInfoSociete() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get the infoSociete
        restInfoSocieteMockMvc.perform(get("/api/info-societes/{id}", infoSociete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(infoSociete.getId().intValue()))
            .andExpect(jsonPath("$.nomSociete").value(DEFAULT_NOM_SOCIETE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.tel1").value(DEFAULT_TEL_1))
            .andExpect(jsonPath("$.tel2").value(DEFAULT_TEL_2))
            .andExpect(jsonPath("$.tel3").value(DEFAULT_TEL_3))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK))
            .andExpect(jsonPath("$.youtube").value(DEFAULT_YOUTUBE))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER))
            .andExpect(jsonPath("$.tiktok").value(DEFAULT_TIKTOK))
            .andExpect(jsonPath("$.matriculeFiscal").value(DEFAULT_MATRICULE_FISCAL))
            .andExpect(jsonPath("$.valeurMinPanier").value(DEFAULT_VALEUR_MIN_PANIER.doubleValue()));
    }


    @Test
    @Transactional
    public void getInfoSocietesByIdFiltering() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        Long id = infoSociete.getId();

        defaultInfoSocieteShouldBeFound("id.equals=" + id);
        defaultInfoSocieteShouldNotBeFound("id.notEquals=" + id);

        defaultInfoSocieteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInfoSocieteShouldNotBeFound("id.greaterThan=" + id);

        defaultInfoSocieteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInfoSocieteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByNomSocieteIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where nomSociete equals to DEFAULT_NOM_SOCIETE
        defaultInfoSocieteShouldBeFound("nomSociete.equals=" + DEFAULT_NOM_SOCIETE);

        // Get all the infoSocieteList where nomSociete equals to UPDATED_NOM_SOCIETE
        defaultInfoSocieteShouldNotBeFound("nomSociete.equals=" + UPDATED_NOM_SOCIETE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByNomSocieteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where nomSociete not equals to DEFAULT_NOM_SOCIETE
        defaultInfoSocieteShouldNotBeFound("nomSociete.notEquals=" + DEFAULT_NOM_SOCIETE);

        // Get all the infoSocieteList where nomSociete not equals to UPDATED_NOM_SOCIETE
        defaultInfoSocieteShouldBeFound("nomSociete.notEquals=" + UPDATED_NOM_SOCIETE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByNomSocieteIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where nomSociete in DEFAULT_NOM_SOCIETE or UPDATED_NOM_SOCIETE
        defaultInfoSocieteShouldBeFound("nomSociete.in=" + DEFAULT_NOM_SOCIETE + "," + UPDATED_NOM_SOCIETE);

        // Get all the infoSocieteList where nomSociete equals to UPDATED_NOM_SOCIETE
        defaultInfoSocieteShouldNotBeFound("nomSociete.in=" + UPDATED_NOM_SOCIETE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByNomSocieteIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where nomSociete is not null
        defaultInfoSocieteShouldBeFound("nomSociete.specified=true");

        // Get all the infoSocieteList where nomSociete is null
        defaultInfoSocieteShouldNotBeFound("nomSociete.specified=false");
    }
                @Test
    @Transactional
    public void getAllInfoSocietesByNomSocieteContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where nomSociete contains DEFAULT_NOM_SOCIETE
        defaultInfoSocieteShouldBeFound("nomSociete.contains=" + DEFAULT_NOM_SOCIETE);

        // Get all the infoSocieteList where nomSociete contains UPDATED_NOM_SOCIETE
        defaultInfoSocieteShouldNotBeFound("nomSociete.contains=" + UPDATED_NOM_SOCIETE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByNomSocieteNotContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where nomSociete does not contain DEFAULT_NOM_SOCIETE
        defaultInfoSocieteShouldNotBeFound("nomSociete.doesNotContain=" + DEFAULT_NOM_SOCIETE);

        // Get all the infoSocieteList where nomSociete does not contain UPDATED_NOM_SOCIETE
        defaultInfoSocieteShouldBeFound("nomSociete.doesNotContain=" + UPDATED_NOM_SOCIETE);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where adresse equals to DEFAULT_ADRESSE
        defaultInfoSocieteShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the infoSocieteList where adresse equals to UPDATED_ADRESSE
        defaultInfoSocieteShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where adresse not equals to DEFAULT_ADRESSE
        defaultInfoSocieteShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the infoSocieteList where adresse not equals to UPDATED_ADRESSE
        defaultInfoSocieteShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultInfoSocieteShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the infoSocieteList where adresse equals to UPDATED_ADRESSE
        defaultInfoSocieteShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where adresse is not null
        defaultInfoSocieteShouldBeFound("adresse.specified=true");

        // Get all the infoSocieteList where adresse is null
        defaultInfoSocieteShouldNotBeFound("adresse.specified=false");
    }
                @Test
    @Transactional
    public void getAllInfoSocietesByAdresseContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where adresse contains DEFAULT_ADRESSE
        defaultInfoSocieteShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the infoSocieteList where adresse contains UPDATED_ADRESSE
        defaultInfoSocieteShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where adresse does not contain DEFAULT_ADRESSE
        defaultInfoSocieteShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the infoSocieteList where adresse does not contain UPDATED_ADRESSE
        defaultInfoSocieteShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByTel1IsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel1 equals to DEFAULT_TEL_1
        defaultInfoSocieteShouldBeFound("tel1.equals=" + DEFAULT_TEL_1);

        // Get all the infoSocieteList where tel1 equals to UPDATED_TEL_1
        defaultInfoSocieteShouldNotBeFound("tel1.equals=" + UPDATED_TEL_1);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel1 not equals to DEFAULT_TEL_1
        defaultInfoSocieteShouldNotBeFound("tel1.notEquals=" + DEFAULT_TEL_1);

        // Get all the infoSocieteList where tel1 not equals to UPDATED_TEL_1
        defaultInfoSocieteShouldBeFound("tel1.notEquals=" + UPDATED_TEL_1);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel1IsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel1 in DEFAULT_TEL_1 or UPDATED_TEL_1
        defaultInfoSocieteShouldBeFound("tel1.in=" + DEFAULT_TEL_1 + "," + UPDATED_TEL_1);

        // Get all the infoSocieteList where tel1 equals to UPDATED_TEL_1
        defaultInfoSocieteShouldNotBeFound("tel1.in=" + UPDATED_TEL_1);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel1IsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel1 is not null
        defaultInfoSocieteShouldBeFound("tel1.specified=true");

        // Get all the infoSocieteList where tel1 is null
        defaultInfoSocieteShouldNotBeFound("tel1.specified=false");
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel1 is greater than or equal to DEFAULT_TEL_1
        defaultInfoSocieteShouldBeFound("tel1.greaterThanOrEqual=" + DEFAULT_TEL_1);

        // Get all the infoSocieteList where tel1 is greater than or equal to UPDATED_TEL_1
        defaultInfoSocieteShouldNotBeFound("tel1.greaterThanOrEqual=" + UPDATED_TEL_1);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel1 is less than or equal to DEFAULT_TEL_1
        defaultInfoSocieteShouldBeFound("tel1.lessThanOrEqual=" + DEFAULT_TEL_1);

        // Get all the infoSocieteList where tel1 is less than or equal to SMALLER_TEL_1
        defaultInfoSocieteShouldNotBeFound("tel1.lessThanOrEqual=" + SMALLER_TEL_1);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel1IsLessThanSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel1 is less than DEFAULT_TEL_1
        defaultInfoSocieteShouldNotBeFound("tel1.lessThan=" + DEFAULT_TEL_1);

        // Get all the infoSocieteList where tel1 is less than UPDATED_TEL_1
        defaultInfoSocieteShouldBeFound("tel1.lessThan=" + UPDATED_TEL_1);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel1 is greater than DEFAULT_TEL_1
        defaultInfoSocieteShouldNotBeFound("tel1.greaterThan=" + DEFAULT_TEL_1);

        // Get all the infoSocieteList where tel1 is greater than SMALLER_TEL_1
        defaultInfoSocieteShouldBeFound("tel1.greaterThan=" + SMALLER_TEL_1);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByTel2IsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel2 equals to DEFAULT_TEL_2
        defaultInfoSocieteShouldBeFound("tel2.equals=" + DEFAULT_TEL_2);

        // Get all the infoSocieteList where tel2 equals to UPDATED_TEL_2
        defaultInfoSocieteShouldNotBeFound("tel2.equals=" + UPDATED_TEL_2);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel2 not equals to DEFAULT_TEL_2
        defaultInfoSocieteShouldNotBeFound("tel2.notEquals=" + DEFAULT_TEL_2);

        // Get all the infoSocieteList where tel2 not equals to UPDATED_TEL_2
        defaultInfoSocieteShouldBeFound("tel2.notEquals=" + UPDATED_TEL_2);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel2IsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel2 in DEFAULT_TEL_2 or UPDATED_TEL_2
        defaultInfoSocieteShouldBeFound("tel2.in=" + DEFAULT_TEL_2 + "," + UPDATED_TEL_2);

        // Get all the infoSocieteList where tel2 equals to UPDATED_TEL_2
        defaultInfoSocieteShouldNotBeFound("tel2.in=" + UPDATED_TEL_2);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel2IsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel2 is not null
        defaultInfoSocieteShouldBeFound("tel2.specified=true");

        // Get all the infoSocieteList where tel2 is null
        defaultInfoSocieteShouldNotBeFound("tel2.specified=false");
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel2 is greater than or equal to DEFAULT_TEL_2
        defaultInfoSocieteShouldBeFound("tel2.greaterThanOrEqual=" + DEFAULT_TEL_2);

        // Get all the infoSocieteList where tel2 is greater than or equal to UPDATED_TEL_2
        defaultInfoSocieteShouldNotBeFound("tel2.greaterThanOrEqual=" + UPDATED_TEL_2);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel2 is less than or equal to DEFAULT_TEL_2
        defaultInfoSocieteShouldBeFound("tel2.lessThanOrEqual=" + DEFAULT_TEL_2);

        // Get all the infoSocieteList where tel2 is less than or equal to SMALLER_TEL_2
        defaultInfoSocieteShouldNotBeFound("tel2.lessThanOrEqual=" + SMALLER_TEL_2);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel2IsLessThanSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel2 is less than DEFAULT_TEL_2
        defaultInfoSocieteShouldNotBeFound("tel2.lessThan=" + DEFAULT_TEL_2);

        // Get all the infoSocieteList where tel2 is less than UPDATED_TEL_2
        defaultInfoSocieteShouldBeFound("tel2.lessThan=" + UPDATED_TEL_2);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel2 is greater than DEFAULT_TEL_2
        defaultInfoSocieteShouldNotBeFound("tel2.greaterThan=" + DEFAULT_TEL_2);

        // Get all the infoSocieteList where tel2 is greater than SMALLER_TEL_2
        defaultInfoSocieteShouldBeFound("tel2.greaterThan=" + SMALLER_TEL_2);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByTel3IsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel3 equals to DEFAULT_TEL_3
        defaultInfoSocieteShouldBeFound("tel3.equals=" + DEFAULT_TEL_3);

        // Get all the infoSocieteList where tel3 equals to UPDATED_TEL_3
        defaultInfoSocieteShouldNotBeFound("tel3.equals=" + UPDATED_TEL_3);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel3 not equals to DEFAULT_TEL_3
        defaultInfoSocieteShouldNotBeFound("tel3.notEquals=" + DEFAULT_TEL_3);

        // Get all the infoSocieteList where tel3 not equals to UPDATED_TEL_3
        defaultInfoSocieteShouldBeFound("tel3.notEquals=" + UPDATED_TEL_3);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel3IsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel3 in DEFAULT_TEL_3 or UPDATED_TEL_3
        defaultInfoSocieteShouldBeFound("tel3.in=" + DEFAULT_TEL_3 + "," + UPDATED_TEL_3);

        // Get all the infoSocieteList where tel3 equals to UPDATED_TEL_3
        defaultInfoSocieteShouldNotBeFound("tel3.in=" + UPDATED_TEL_3);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel3IsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel3 is not null
        defaultInfoSocieteShouldBeFound("tel3.specified=true");

        // Get all the infoSocieteList where tel3 is null
        defaultInfoSocieteShouldNotBeFound("tel3.specified=false");
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel3 is greater than or equal to DEFAULT_TEL_3
        defaultInfoSocieteShouldBeFound("tel3.greaterThanOrEqual=" + DEFAULT_TEL_3);

        // Get all the infoSocieteList where tel3 is greater than or equal to UPDATED_TEL_3
        defaultInfoSocieteShouldNotBeFound("tel3.greaterThanOrEqual=" + UPDATED_TEL_3);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel3 is less than or equal to DEFAULT_TEL_3
        defaultInfoSocieteShouldBeFound("tel3.lessThanOrEqual=" + DEFAULT_TEL_3);

        // Get all the infoSocieteList where tel3 is less than or equal to SMALLER_TEL_3
        defaultInfoSocieteShouldNotBeFound("tel3.lessThanOrEqual=" + SMALLER_TEL_3);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel3IsLessThanSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel3 is less than DEFAULT_TEL_3
        defaultInfoSocieteShouldNotBeFound("tel3.lessThan=" + DEFAULT_TEL_3);

        // Get all the infoSocieteList where tel3 is less than UPDATED_TEL_3
        defaultInfoSocieteShouldBeFound("tel3.lessThan=" + UPDATED_TEL_3);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTel3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tel3 is greater than DEFAULT_TEL_3
        defaultInfoSocieteShouldNotBeFound("tel3.greaterThan=" + DEFAULT_TEL_3);

        // Get all the infoSocieteList where tel3 is greater than SMALLER_TEL_3
        defaultInfoSocieteShouldBeFound("tel3.greaterThan=" + SMALLER_TEL_3);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByEmail1IsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where email1 equals to DEFAULT_EMAIL_1
        defaultInfoSocieteShouldBeFound("email1.equals=" + DEFAULT_EMAIL_1);

        // Get all the infoSocieteList where email1 equals to UPDATED_EMAIL_1
        defaultInfoSocieteShouldNotBeFound("email1.equals=" + UPDATED_EMAIL_1);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByEmail1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where email1 not equals to DEFAULT_EMAIL_1
        defaultInfoSocieteShouldNotBeFound("email1.notEquals=" + DEFAULT_EMAIL_1);

        // Get all the infoSocieteList where email1 not equals to UPDATED_EMAIL_1
        defaultInfoSocieteShouldBeFound("email1.notEquals=" + UPDATED_EMAIL_1);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByEmail1IsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where email1 in DEFAULT_EMAIL_1 or UPDATED_EMAIL_1
        defaultInfoSocieteShouldBeFound("email1.in=" + DEFAULT_EMAIL_1 + "," + UPDATED_EMAIL_1);

        // Get all the infoSocieteList where email1 equals to UPDATED_EMAIL_1
        defaultInfoSocieteShouldNotBeFound("email1.in=" + UPDATED_EMAIL_1);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByEmail1IsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where email1 is not null
        defaultInfoSocieteShouldBeFound("email1.specified=true");

        // Get all the infoSocieteList where email1 is null
        defaultInfoSocieteShouldNotBeFound("email1.specified=false");
    }
                @Test
    @Transactional
    public void getAllInfoSocietesByEmail1ContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where email1 contains DEFAULT_EMAIL_1
        defaultInfoSocieteShouldBeFound("email1.contains=" + DEFAULT_EMAIL_1);

        // Get all the infoSocieteList where email1 contains UPDATED_EMAIL_1
        defaultInfoSocieteShouldNotBeFound("email1.contains=" + UPDATED_EMAIL_1);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByEmail1NotContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where email1 does not contain DEFAULT_EMAIL_1
        defaultInfoSocieteShouldNotBeFound("email1.doesNotContain=" + DEFAULT_EMAIL_1);

        // Get all the infoSocieteList where email1 does not contain UPDATED_EMAIL_1
        defaultInfoSocieteShouldBeFound("email1.doesNotContain=" + UPDATED_EMAIL_1);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByEmail2IsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where email2 equals to DEFAULT_EMAIL_2
        defaultInfoSocieteShouldBeFound("email2.equals=" + DEFAULT_EMAIL_2);

        // Get all the infoSocieteList where email2 equals to UPDATED_EMAIL_2
        defaultInfoSocieteShouldNotBeFound("email2.equals=" + UPDATED_EMAIL_2);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByEmail2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where email2 not equals to DEFAULT_EMAIL_2
        defaultInfoSocieteShouldNotBeFound("email2.notEquals=" + DEFAULT_EMAIL_2);

        // Get all the infoSocieteList where email2 not equals to UPDATED_EMAIL_2
        defaultInfoSocieteShouldBeFound("email2.notEquals=" + UPDATED_EMAIL_2);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByEmail2IsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where email2 in DEFAULT_EMAIL_2 or UPDATED_EMAIL_2
        defaultInfoSocieteShouldBeFound("email2.in=" + DEFAULT_EMAIL_2 + "," + UPDATED_EMAIL_2);

        // Get all the infoSocieteList where email2 equals to UPDATED_EMAIL_2
        defaultInfoSocieteShouldNotBeFound("email2.in=" + UPDATED_EMAIL_2);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByEmail2IsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where email2 is not null
        defaultInfoSocieteShouldBeFound("email2.specified=true");

        // Get all the infoSocieteList where email2 is null
        defaultInfoSocieteShouldNotBeFound("email2.specified=false");
    }
                @Test
    @Transactional
    public void getAllInfoSocietesByEmail2ContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where email2 contains DEFAULT_EMAIL_2
        defaultInfoSocieteShouldBeFound("email2.contains=" + DEFAULT_EMAIL_2);

        // Get all the infoSocieteList where email2 contains UPDATED_EMAIL_2
        defaultInfoSocieteShouldNotBeFound("email2.contains=" + UPDATED_EMAIL_2);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByEmail2NotContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where email2 does not contain DEFAULT_EMAIL_2
        defaultInfoSocieteShouldNotBeFound("email2.doesNotContain=" + DEFAULT_EMAIL_2);

        // Get all the infoSocieteList where email2 does not contain UPDATED_EMAIL_2
        defaultInfoSocieteShouldBeFound("email2.doesNotContain=" + UPDATED_EMAIL_2);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByCreeLeIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creeLe equals to DEFAULT_CREE_LE
        defaultInfoSocieteShouldBeFound("creeLe.equals=" + DEFAULT_CREE_LE);

        // Get all the infoSocieteList where creeLe equals to UPDATED_CREE_LE
        defaultInfoSocieteShouldNotBeFound("creeLe.equals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByCreeLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creeLe not equals to DEFAULT_CREE_LE
        defaultInfoSocieteShouldNotBeFound("creeLe.notEquals=" + DEFAULT_CREE_LE);

        // Get all the infoSocieteList where creeLe not equals to UPDATED_CREE_LE
        defaultInfoSocieteShouldBeFound("creeLe.notEquals=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByCreeLeIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creeLe in DEFAULT_CREE_LE or UPDATED_CREE_LE
        defaultInfoSocieteShouldBeFound("creeLe.in=" + DEFAULT_CREE_LE + "," + UPDATED_CREE_LE);

        // Get all the infoSocieteList where creeLe equals to UPDATED_CREE_LE
        defaultInfoSocieteShouldNotBeFound("creeLe.in=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByCreeLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creeLe is not null
        defaultInfoSocieteShouldBeFound("creeLe.specified=true");

        // Get all the infoSocieteList where creeLe is null
        defaultInfoSocieteShouldNotBeFound("creeLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByCreeLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creeLe is greater than or equal to DEFAULT_CREE_LE
        defaultInfoSocieteShouldBeFound("creeLe.greaterThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the infoSocieteList where creeLe is greater than or equal to UPDATED_CREE_LE
        defaultInfoSocieteShouldNotBeFound("creeLe.greaterThanOrEqual=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByCreeLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creeLe is less than or equal to DEFAULT_CREE_LE
        defaultInfoSocieteShouldBeFound("creeLe.lessThanOrEqual=" + DEFAULT_CREE_LE);

        // Get all the infoSocieteList where creeLe is less than or equal to SMALLER_CREE_LE
        defaultInfoSocieteShouldNotBeFound("creeLe.lessThanOrEqual=" + SMALLER_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByCreeLeIsLessThanSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creeLe is less than DEFAULT_CREE_LE
        defaultInfoSocieteShouldNotBeFound("creeLe.lessThan=" + DEFAULT_CREE_LE);

        // Get all the infoSocieteList where creeLe is less than UPDATED_CREE_LE
        defaultInfoSocieteShouldBeFound("creeLe.lessThan=" + UPDATED_CREE_LE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByCreeLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creeLe is greater than DEFAULT_CREE_LE
        defaultInfoSocieteShouldNotBeFound("creeLe.greaterThan=" + DEFAULT_CREE_LE);

        // Get all the infoSocieteList where creeLe is greater than SMALLER_CREE_LE
        defaultInfoSocieteShouldBeFound("creeLe.greaterThan=" + SMALLER_CREE_LE);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByCreeParIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creePar equals to DEFAULT_CREE_PAR
        defaultInfoSocieteShouldBeFound("creePar.equals=" + DEFAULT_CREE_PAR);

        // Get all the infoSocieteList where creePar equals to UPDATED_CREE_PAR
        defaultInfoSocieteShouldNotBeFound("creePar.equals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByCreeParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creePar not equals to DEFAULT_CREE_PAR
        defaultInfoSocieteShouldNotBeFound("creePar.notEquals=" + DEFAULT_CREE_PAR);

        // Get all the infoSocieteList where creePar not equals to UPDATED_CREE_PAR
        defaultInfoSocieteShouldBeFound("creePar.notEquals=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByCreeParIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creePar in DEFAULT_CREE_PAR or UPDATED_CREE_PAR
        defaultInfoSocieteShouldBeFound("creePar.in=" + DEFAULT_CREE_PAR + "," + UPDATED_CREE_PAR);

        // Get all the infoSocieteList where creePar equals to UPDATED_CREE_PAR
        defaultInfoSocieteShouldNotBeFound("creePar.in=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByCreeParIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creePar is not null
        defaultInfoSocieteShouldBeFound("creePar.specified=true");

        // Get all the infoSocieteList where creePar is null
        defaultInfoSocieteShouldNotBeFound("creePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllInfoSocietesByCreeParContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creePar contains DEFAULT_CREE_PAR
        defaultInfoSocieteShouldBeFound("creePar.contains=" + DEFAULT_CREE_PAR);

        // Get all the infoSocieteList where creePar contains UPDATED_CREE_PAR
        defaultInfoSocieteShouldNotBeFound("creePar.contains=" + UPDATED_CREE_PAR);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByCreeParNotContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where creePar does not contain DEFAULT_CREE_PAR
        defaultInfoSocieteShouldNotBeFound("creePar.doesNotContain=" + DEFAULT_CREE_PAR);

        // Get all the infoSocieteList where creePar does not contain UPDATED_CREE_PAR
        defaultInfoSocieteShouldBeFound("creePar.doesNotContain=" + UPDATED_CREE_PAR);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByModifieLeIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifieLe equals to DEFAULT_MODIFIE_LE
        defaultInfoSocieteShouldBeFound("modifieLe.equals=" + DEFAULT_MODIFIE_LE);

        // Get all the infoSocieteList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultInfoSocieteShouldNotBeFound("modifieLe.equals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByModifieLeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifieLe not equals to DEFAULT_MODIFIE_LE
        defaultInfoSocieteShouldNotBeFound("modifieLe.notEquals=" + DEFAULT_MODIFIE_LE);

        // Get all the infoSocieteList where modifieLe not equals to UPDATED_MODIFIE_LE
        defaultInfoSocieteShouldBeFound("modifieLe.notEquals=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByModifieLeIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifieLe in DEFAULT_MODIFIE_LE or UPDATED_MODIFIE_LE
        defaultInfoSocieteShouldBeFound("modifieLe.in=" + DEFAULT_MODIFIE_LE + "," + UPDATED_MODIFIE_LE);

        // Get all the infoSocieteList where modifieLe equals to UPDATED_MODIFIE_LE
        defaultInfoSocieteShouldNotBeFound("modifieLe.in=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByModifieLeIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifieLe is not null
        defaultInfoSocieteShouldBeFound("modifieLe.specified=true");

        // Get all the infoSocieteList where modifieLe is null
        defaultInfoSocieteShouldNotBeFound("modifieLe.specified=false");
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByModifieLeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifieLe is greater than or equal to DEFAULT_MODIFIE_LE
        defaultInfoSocieteShouldBeFound("modifieLe.greaterThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the infoSocieteList where modifieLe is greater than or equal to UPDATED_MODIFIE_LE
        defaultInfoSocieteShouldNotBeFound("modifieLe.greaterThanOrEqual=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByModifieLeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifieLe is less than or equal to DEFAULT_MODIFIE_LE
        defaultInfoSocieteShouldBeFound("modifieLe.lessThanOrEqual=" + DEFAULT_MODIFIE_LE);

        // Get all the infoSocieteList where modifieLe is less than or equal to SMALLER_MODIFIE_LE
        defaultInfoSocieteShouldNotBeFound("modifieLe.lessThanOrEqual=" + SMALLER_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByModifieLeIsLessThanSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifieLe is less than DEFAULT_MODIFIE_LE
        defaultInfoSocieteShouldNotBeFound("modifieLe.lessThan=" + DEFAULT_MODIFIE_LE);

        // Get all the infoSocieteList where modifieLe is less than UPDATED_MODIFIE_LE
        defaultInfoSocieteShouldBeFound("modifieLe.lessThan=" + UPDATED_MODIFIE_LE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByModifieLeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifieLe is greater than DEFAULT_MODIFIE_LE
        defaultInfoSocieteShouldNotBeFound("modifieLe.greaterThan=" + DEFAULT_MODIFIE_LE);

        // Get all the infoSocieteList where modifieLe is greater than SMALLER_MODIFIE_LE
        defaultInfoSocieteShouldBeFound("modifieLe.greaterThan=" + SMALLER_MODIFIE_LE);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByModifieParIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifiePar equals to DEFAULT_MODIFIE_PAR
        defaultInfoSocieteShouldBeFound("modifiePar.equals=" + DEFAULT_MODIFIE_PAR);

        // Get all the infoSocieteList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultInfoSocieteShouldNotBeFound("modifiePar.equals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByModifieParIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifiePar not equals to DEFAULT_MODIFIE_PAR
        defaultInfoSocieteShouldNotBeFound("modifiePar.notEquals=" + DEFAULT_MODIFIE_PAR);

        // Get all the infoSocieteList where modifiePar not equals to UPDATED_MODIFIE_PAR
        defaultInfoSocieteShouldBeFound("modifiePar.notEquals=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByModifieParIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifiePar in DEFAULT_MODIFIE_PAR or UPDATED_MODIFIE_PAR
        defaultInfoSocieteShouldBeFound("modifiePar.in=" + DEFAULT_MODIFIE_PAR + "," + UPDATED_MODIFIE_PAR);

        // Get all the infoSocieteList where modifiePar equals to UPDATED_MODIFIE_PAR
        defaultInfoSocieteShouldNotBeFound("modifiePar.in=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByModifieParIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifiePar is not null
        defaultInfoSocieteShouldBeFound("modifiePar.specified=true");

        // Get all the infoSocieteList where modifiePar is null
        defaultInfoSocieteShouldNotBeFound("modifiePar.specified=false");
    }
                @Test
    @Transactional
    public void getAllInfoSocietesByModifieParContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifiePar contains DEFAULT_MODIFIE_PAR
        defaultInfoSocieteShouldBeFound("modifiePar.contains=" + DEFAULT_MODIFIE_PAR);

        // Get all the infoSocieteList where modifiePar contains UPDATED_MODIFIE_PAR
        defaultInfoSocieteShouldNotBeFound("modifiePar.contains=" + UPDATED_MODIFIE_PAR);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByModifieParNotContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where modifiePar does not contain DEFAULT_MODIFIE_PAR
        defaultInfoSocieteShouldNotBeFound("modifiePar.doesNotContain=" + DEFAULT_MODIFIE_PAR);

        // Get all the infoSocieteList where modifiePar does not contain UPDATED_MODIFIE_PAR
        defaultInfoSocieteShouldBeFound("modifiePar.doesNotContain=" + UPDATED_MODIFIE_PAR);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByFacebookIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where facebook equals to DEFAULT_FACEBOOK
        defaultInfoSocieteShouldBeFound("facebook.equals=" + DEFAULT_FACEBOOK);

        // Get all the infoSocieteList where facebook equals to UPDATED_FACEBOOK
        defaultInfoSocieteShouldNotBeFound("facebook.equals=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByFacebookIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where facebook not equals to DEFAULT_FACEBOOK
        defaultInfoSocieteShouldNotBeFound("facebook.notEquals=" + DEFAULT_FACEBOOK);

        // Get all the infoSocieteList where facebook not equals to UPDATED_FACEBOOK
        defaultInfoSocieteShouldBeFound("facebook.notEquals=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByFacebookIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where facebook in DEFAULT_FACEBOOK or UPDATED_FACEBOOK
        defaultInfoSocieteShouldBeFound("facebook.in=" + DEFAULT_FACEBOOK + "," + UPDATED_FACEBOOK);

        // Get all the infoSocieteList where facebook equals to UPDATED_FACEBOOK
        defaultInfoSocieteShouldNotBeFound("facebook.in=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByFacebookIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where facebook is not null
        defaultInfoSocieteShouldBeFound("facebook.specified=true");

        // Get all the infoSocieteList where facebook is null
        defaultInfoSocieteShouldNotBeFound("facebook.specified=false");
    }
                @Test
    @Transactional
    public void getAllInfoSocietesByFacebookContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where facebook contains DEFAULT_FACEBOOK
        defaultInfoSocieteShouldBeFound("facebook.contains=" + DEFAULT_FACEBOOK);

        // Get all the infoSocieteList where facebook contains UPDATED_FACEBOOK
        defaultInfoSocieteShouldNotBeFound("facebook.contains=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByFacebookNotContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where facebook does not contain DEFAULT_FACEBOOK
        defaultInfoSocieteShouldNotBeFound("facebook.doesNotContain=" + DEFAULT_FACEBOOK);

        // Get all the infoSocieteList where facebook does not contain UPDATED_FACEBOOK
        defaultInfoSocieteShouldBeFound("facebook.doesNotContain=" + UPDATED_FACEBOOK);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByYoutubeIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where youtube equals to DEFAULT_YOUTUBE
        defaultInfoSocieteShouldBeFound("youtube.equals=" + DEFAULT_YOUTUBE);

        // Get all the infoSocieteList where youtube equals to UPDATED_YOUTUBE
        defaultInfoSocieteShouldNotBeFound("youtube.equals=" + UPDATED_YOUTUBE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByYoutubeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where youtube not equals to DEFAULT_YOUTUBE
        defaultInfoSocieteShouldNotBeFound("youtube.notEquals=" + DEFAULT_YOUTUBE);

        // Get all the infoSocieteList where youtube not equals to UPDATED_YOUTUBE
        defaultInfoSocieteShouldBeFound("youtube.notEquals=" + UPDATED_YOUTUBE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByYoutubeIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where youtube in DEFAULT_YOUTUBE or UPDATED_YOUTUBE
        defaultInfoSocieteShouldBeFound("youtube.in=" + DEFAULT_YOUTUBE + "," + UPDATED_YOUTUBE);

        // Get all the infoSocieteList where youtube equals to UPDATED_YOUTUBE
        defaultInfoSocieteShouldNotBeFound("youtube.in=" + UPDATED_YOUTUBE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByYoutubeIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where youtube is not null
        defaultInfoSocieteShouldBeFound("youtube.specified=true");

        // Get all the infoSocieteList where youtube is null
        defaultInfoSocieteShouldNotBeFound("youtube.specified=false");
    }
                @Test
    @Transactional
    public void getAllInfoSocietesByYoutubeContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where youtube contains DEFAULT_YOUTUBE
        defaultInfoSocieteShouldBeFound("youtube.contains=" + DEFAULT_YOUTUBE);

        // Get all the infoSocieteList where youtube contains UPDATED_YOUTUBE
        defaultInfoSocieteShouldNotBeFound("youtube.contains=" + UPDATED_YOUTUBE);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByYoutubeNotContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where youtube does not contain DEFAULT_YOUTUBE
        defaultInfoSocieteShouldNotBeFound("youtube.doesNotContain=" + DEFAULT_YOUTUBE);

        // Get all the infoSocieteList where youtube does not contain UPDATED_YOUTUBE
        defaultInfoSocieteShouldBeFound("youtube.doesNotContain=" + UPDATED_YOUTUBE);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByInstagramIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where instagram equals to DEFAULT_INSTAGRAM
        defaultInfoSocieteShouldBeFound("instagram.equals=" + DEFAULT_INSTAGRAM);

        // Get all the infoSocieteList where instagram equals to UPDATED_INSTAGRAM
        defaultInfoSocieteShouldNotBeFound("instagram.equals=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByInstagramIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where instagram not equals to DEFAULT_INSTAGRAM
        defaultInfoSocieteShouldNotBeFound("instagram.notEquals=" + DEFAULT_INSTAGRAM);

        // Get all the infoSocieteList where instagram not equals to UPDATED_INSTAGRAM
        defaultInfoSocieteShouldBeFound("instagram.notEquals=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByInstagramIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where instagram in DEFAULT_INSTAGRAM or UPDATED_INSTAGRAM
        defaultInfoSocieteShouldBeFound("instagram.in=" + DEFAULT_INSTAGRAM + "," + UPDATED_INSTAGRAM);

        // Get all the infoSocieteList where instagram equals to UPDATED_INSTAGRAM
        defaultInfoSocieteShouldNotBeFound("instagram.in=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByInstagramIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where instagram is not null
        defaultInfoSocieteShouldBeFound("instagram.specified=true");

        // Get all the infoSocieteList where instagram is null
        defaultInfoSocieteShouldNotBeFound("instagram.specified=false");
    }
                @Test
    @Transactional
    public void getAllInfoSocietesByInstagramContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where instagram contains DEFAULT_INSTAGRAM
        defaultInfoSocieteShouldBeFound("instagram.contains=" + DEFAULT_INSTAGRAM);

        // Get all the infoSocieteList where instagram contains UPDATED_INSTAGRAM
        defaultInfoSocieteShouldNotBeFound("instagram.contains=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByInstagramNotContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where instagram does not contain DEFAULT_INSTAGRAM
        defaultInfoSocieteShouldNotBeFound("instagram.doesNotContain=" + DEFAULT_INSTAGRAM);

        // Get all the infoSocieteList where instagram does not contain UPDATED_INSTAGRAM
        defaultInfoSocieteShouldBeFound("instagram.doesNotContain=" + UPDATED_INSTAGRAM);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByTwitterIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where twitter equals to DEFAULT_TWITTER
        defaultInfoSocieteShouldBeFound("twitter.equals=" + DEFAULT_TWITTER);

        // Get all the infoSocieteList where twitter equals to UPDATED_TWITTER
        defaultInfoSocieteShouldNotBeFound("twitter.equals=" + UPDATED_TWITTER);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTwitterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where twitter not equals to DEFAULT_TWITTER
        defaultInfoSocieteShouldNotBeFound("twitter.notEquals=" + DEFAULT_TWITTER);

        // Get all the infoSocieteList where twitter not equals to UPDATED_TWITTER
        defaultInfoSocieteShouldBeFound("twitter.notEquals=" + UPDATED_TWITTER);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTwitterIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where twitter in DEFAULT_TWITTER or UPDATED_TWITTER
        defaultInfoSocieteShouldBeFound("twitter.in=" + DEFAULT_TWITTER + "," + UPDATED_TWITTER);

        // Get all the infoSocieteList where twitter equals to UPDATED_TWITTER
        defaultInfoSocieteShouldNotBeFound("twitter.in=" + UPDATED_TWITTER);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTwitterIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where twitter is not null
        defaultInfoSocieteShouldBeFound("twitter.specified=true");

        // Get all the infoSocieteList where twitter is null
        defaultInfoSocieteShouldNotBeFound("twitter.specified=false");
    }
                @Test
    @Transactional
    public void getAllInfoSocietesByTwitterContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where twitter contains DEFAULT_TWITTER
        defaultInfoSocieteShouldBeFound("twitter.contains=" + DEFAULT_TWITTER);

        // Get all the infoSocieteList where twitter contains UPDATED_TWITTER
        defaultInfoSocieteShouldNotBeFound("twitter.contains=" + UPDATED_TWITTER);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTwitterNotContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where twitter does not contain DEFAULT_TWITTER
        defaultInfoSocieteShouldNotBeFound("twitter.doesNotContain=" + DEFAULT_TWITTER);

        // Get all the infoSocieteList where twitter does not contain UPDATED_TWITTER
        defaultInfoSocieteShouldBeFound("twitter.doesNotContain=" + UPDATED_TWITTER);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByTiktokIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tiktok equals to DEFAULT_TIKTOK
        defaultInfoSocieteShouldBeFound("tiktok.equals=" + DEFAULT_TIKTOK);

        // Get all the infoSocieteList where tiktok equals to UPDATED_TIKTOK
        defaultInfoSocieteShouldNotBeFound("tiktok.equals=" + UPDATED_TIKTOK);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTiktokIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tiktok not equals to DEFAULT_TIKTOK
        defaultInfoSocieteShouldNotBeFound("tiktok.notEquals=" + DEFAULT_TIKTOK);

        // Get all the infoSocieteList where tiktok not equals to UPDATED_TIKTOK
        defaultInfoSocieteShouldBeFound("tiktok.notEquals=" + UPDATED_TIKTOK);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTiktokIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tiktok in DEFAULT_TIKTOK or UPDATED_TIKTOK
        defaultInfoSocieteShouldBeFound("tiktok.in=" + DEFAULT_TIKTOK + "," + UPDATED_TIKTOK);

        // Get all the infoSocieteList where tiktok equals to UPDATED_TIKTOK
        defaultInfoSocieteShouldNotBeFound("tiktok.in=" + UPDATED_TIKTOK);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTiktokIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tiktok is not null
        defaultInfoSocieteShouldBeFound("tiktok.specified=true");

        // Get all the infoSocieteList where tiktok is null
        defaultInfoSocieteShouldNotBeFound("tiktok.specified=false");
    }
                @Test
    @Transactional
    public void getAllInfoSocietesByTiktokContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tiktok contains DEFAULT_TIKTOK
        defaultInfoSocieteShouldBeFound("tiktok.contains=" + DEFAULT_TIKTOK);

        // Get all the infoSocieteList where tiktok contains UPDATED_TIKTOK
        defaultInfoSocieteShouldNotBeFound("tiktok.contains=" + UPDATED_TIKTOK);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByTiktokNotContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where tiktok does not contain DEFAULT_TIKTOK
        defaultInfoSocieteShouldNotBeFound("tiktok.doesNotContain=" + DEFAULT_TIKTOK);

        // Get all the infoSocieteList where tiktok does not contain UPDATED_TIKTOK
        defaultInfoSocieteShouldBeFound("tiktok.doesNotContain=" + UPDATED_TIKTOK);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByMatriculeFiscalIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where matriculeFiscal equals to DEFAULT_MATRICULE_FISCAL
        defaultInfoSocieteShouldBeFound("matriculeFiscal.equals=" + DEFAULT_MATRICULE_FISCAL);

        // Get all the infoSocieteList where matriculeFiscal equals to UPDATED_MATRICULE_FISCAL
        defaultInfoSocieteShouldNotBeFound("matriculeFiscal.equals=" + UPDATED_MATRICULE_FISCAL);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByMatriculeFiscalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where matriculeFiscal not equals to DEFAULT_MATRICULE_FISCAL
        defaultInfoSocieteShouldNotBeFound("matriculeFiscal.notEquals=" + DEFAULT_MATRICULE_FISCAL);

        // Get all the infoSocieteList where matriculeFiscal not equals to UPDATED_MATRICULE_FISCAL
        defaultInfoSocieteShouldBeFound("matriculeFiscal.notEquals=" + UPDATED_MATRICULE_FISCAL);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByMatriculeFiscalIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where matriculeFiscal in DEFAULT_MATRICULE_FISCAL or UPDATED_MATRICULE_FISCAL
        defaultInfoSocieteShouldBeFound("matriculeFiscal.in=" + DEFAULT_MATRICULE_FISCAL + "," + UPDATED_MATRICULE_FISCAL);

        // Get all the infoSocieteList where matriculeFiscal equals to UPDATED_MATRICULE_FISCAL
        defaultInfoSocieteShouldNotBeFound("matriculeFiscal.in=" + UPDATED_MATRICULE_FISCAL);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByMatriculeFiscalIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where matriculeFiscal is not null
        defaultInfoSocieteShouldBeFound("matriculeFiscal.specified=true");

        // Get all the infoSocieteList where matriculeFiscal is null
        defaultInfoSocieteShouldNotBeFound("matriculeFiscal.specified=false");
    }
                @Test
    @Transactional
    public void getAllInfoSocietesByMatriculeFiscalContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where matriculeFiscal contains DEFAULT_MATRICULE_FISCAL
        defaultInfoSocieteShouldBeFound("matriculeFiscal.contains=" + DEFAULT_MATRICULE_FISCAL);

        // Get all the infoSocieteList where matriculeFiscal contains UPDATED_MATRICULE_FISCAL
        defaultInfoSocieteShouldNotBeFound("matriculeFiscal.contains=" + UPDATED_MATRICULE_FISCAL);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByMatriculeFiscalNotContainsSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where matriculeFiscal does not contain DEFAULT_MATRICULE_FISCAL
        defaultInfoSocieteShouldNotBeFound("matriculeFiscal.doesNotContain=" + DEFAULT_MATRICULE_FISCAL);

        // Get all the infoSocieteList where matriculeFiscal does not contain UPDATED_MATRICULE_FISCAL
        defaultInfoSocieteShouldBeFound("matriculeFiscal.doesNotContain=" + UPDATED_MATRICULE_FISCAL);
    }


    @Test
    @Transactional
    public void getAllInfoSocietesByValeurMinPanierIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where valeurMinPanier equals to DEFAULT_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldBeFound("valeurMinPanier.equals=" + DEFAULT_VALEUR_MIN_PANIER);

        // Get all the infoSocieteList where valeurMinPanier equals to UPDATED_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldNotBeFound("valeurMinPanier.equals=" + UPDATED_VALEUR_MIN_PANIER);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByValeurMinPanierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where valeurMinPanier not equals to DEFAULT_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldNotBeFound("valeurMinPanier.notEquals=" + DEFAULT_VALEUR_MIN_PANIER);

        // Get all the infoSocieteList where valeurMinPanier not equals to UPDATED_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldBeFound("valeurMinPanier.notEquals=" + UPDATED_VALEUR_MIN_PANIER);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByValeurMinPanierIsInShouldWork() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where valeurMinPanier in DEFAULT_VALEUR_MIN_PANIER or UPDATED_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldBeFound("valeurMinPanier.in=" + DEFAULT_VALEUR_MIN_PANIER + "," + UPDATED_VALEUR_MIN_PANIER);

        // Get all the infoSocieteList where valeurMinPanier equals to UPDATED_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldNotBeFound("valeurMinPanier.in=" + UPDATED_VALEUR_MIN_PANIER);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByValeurMinPanierIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where valeurMinPanier is not null
        defaultInfoSocieteShouldBeFound("valeurMinPanier.specified=true");

        // Get all the infoSocieteList where valeurMinPanier is null
        defaultInfoSocieteShouldNotBeFound("valeurMinPanier.specified=false");
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByValeurMinPanierIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where valeurMinPanier is greater than or equal to DEFAULT_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldBeFound("valeurMinPanier.greaterThanOrEqual=" + DEFAULT_VALEUR_MIN_PANIER);

        // Get all the infoSocieteList where valeurMinPanier is greater than or equal to UPDATED_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldNotBeFound("valeurMinPanier.greaterThanOrEqual=" + UPDATED_VALEUR_MIN_PANIER);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByValeurMinPanierIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where valeurMinPanier is less than or equal to DEFAULT_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldBeFound("valeurMinPanier.lessThanOrEqual=" + DEFAULT_VALEUR_MIN_PANIER);

        // Get all the infoSocieteList where valeurMinPanier is less than or equal to SMALLER_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldNotBeFound("valeurMinPanier.lessThanOrEqual=" + SMALLER_VALEUR_MIN_PANIER);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByValeurMinPanierIsLessThanSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where valeurMinPanier is less than DEFAULT_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldNotBeFound("valeurMinPanier.lessThan=" + DEFAULT_VALEUR_MIN_PANIER);

        // Get all the infoSocieteList where valeurMinPanier is less than UPDATED_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldBeFound("valeurMinPanier.lessThan=" + UPDATED_VALEUR_MIN_PANIER);
    }

    @Test
    @Transactional
    public void getAllInfoSocietesByValeurMinPanierIsGreaterThanSomething() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        // Get all the infoSocieteList where valeurMinPanier is greater than DEFAULT_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldNotBeFound("valeurMinPanier.greaterThan=" + DEFAULT_VALEUR_MIN_PANIER);

        // Get all the infoSocieteList where valeurMinPanier is greater than SMALLER_VALEUR_MIN_PANIER
        defaultInfoSocieteShouldBeFound("valeurMinPanier.greaterThan=" + SMALLER_VALEUR_MIN_PANIER);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInfoSocieteShouldBeFound(String filter) throws Exception {
        restInfoSocieteMockMvc.perform(get("/api/info-societes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoSociete.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomSociete").value(hasItem(DEFAULT_NOM_SOCIETE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].tel1").value(hasItem(DEFAULT_TEL_1)))
            .andExpect(jsonPath("$.[*].tel2").value(hasItem(DEFAULT_TEL_2)))
            .andExpect(jsonPath("$.[*].tel3").value(hasItem(DEFAULT_TEL_3)))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1)))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].youtube").value(hasItem(DEFAULT_YOUTUBE)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)))
            .andExpect(jsonPath("$.[*].tiktok").value(hasItem(DEFAULT_TIKTOK)))
            .andExpect(jsonPath("$.[*].matriculeFiscal").value(hasItem(DEFAULT_MATRICULE_FISCAL)))
            .andExpect(jsonPath("$.[*].valeurMinPanier").value(hasItem(DEFAULT_VALEUR_MIN_PANIER.doubleValue())));

        // Check, that the count call also returns 1
        restInfoSocieteMockMvc.perform(get("/api/info-societes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInfoSocieteShouldNotBeFound(String filter) throws Exception {
        restInfoSocieteMockMvc.perform(get("/api/info-societes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInfoSocieteMockMvc.perform(get("/api/info-societes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInfoSociete() throws Exception {
        // Get the infoSociete
        restInfoSocieteMockMvc.perform(get("/api/info-societes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInfoSociete() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        int databaseSizeBeforeUpdate = infoSocieteRepository.findAll().size();

        // Update the infoSociete
        InfoSociete updatedInfoSociete = infoSocieteRepository.findById(infoSociete.getId()).get();
        // Disconnect from session so that the updates on updatedInfoSociete are not directly saved in db
        em.detach(updatedInfoSociete);
        updatedInfoSociete
            .nomSociete(UPDATED_NOM_SOCIETE)
            .adresse(UPDATED_ADRESSE)
            .tel1(UPDATED_TEL_1)
            .tel2(UPDATED_TEL_2)
            .tel3(UPDATED_TEL_3)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR)
            .facebook(UPDATED_FACEBOOK)
            .youtube(UPDATED_YOUTUBE)
            .instagram(UPDATED_INSTAGRAM)
            .twitter(UPDATED_TWITTER)
            .tiktok(UPDATED_TIKTOK)
            .matriculeFiscal(UPDATED_MATRICULE_FISCAL)
            .valeurMinPanier(UPDATED_VALEUR_MIN_PANIER);
        InfoSocieteDTO infoSocieteDTO = infoSocieteMapper.toDto(updatedInfoSociete);

        restInfoSocieteMockMvc.perform(put("/api/info-societes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoSocieteDTO)))
            .andExpect(status().isOk());

        // Validate the InfoSociete in the database
        List<InfoSociete> infoSocieteList = infoSocieteRepository.findAll();
        assertThat(infoSocieteList).hasSize(databaseSizeBeforeUpdate);
        InfoSociete testInfoSociete = infoSocieteList.get(infoSocieteList.size() - 1);
        assertThat(testInfoSociete.getNomSociete()).isEqualTo(UPDATED_NOM_SOCIETE);
        assertThat(testInfoSociete.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testInfoSociete.getTel1()).isEqualTo(UPDATED_TEL_1);
        assertThat(testInfoSociete.getTel2()).isEqualTo(UPDATED_TEL_2);
        assertThat(testInfoSociete.getTel3()).isEqualTo(UPDATED_TEL_3);
        assertThat(testInfoSociete.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testInfoSociete.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testInfoSociete.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testInfoSociete.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testInfoSociete.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testInfoSociete.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);
        assertThat(testInfoSociete.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testInfoSociete.getYoutube()).isEqualTo(UPDATED_YOUTUBE);
        assertThat(testInfoSociete.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testInfoSociete.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testInfoSociete.getTiktok()).isEqualTo(UPDATED_TIKTOK);
        assertThat(testInfoSociete.getMatriculeFiscal()).isEqualTo(UPDATED_MATRICULE_FISCAL);
        assertThat(testInfoSociete.getValeurMinPanier()).isEqualTo(UPDATED_VALEUR_MIN_PANIER);

        // Validate the InfoSociete in Elasticsearch
        verify(mockInfoSocieteSearchRepository, times(1)).save(testInfoSociete);
    }

    @Test
    @Transactional
    public void updateNonExistingInfoSociete() throws Exception {
        int databaseSizeBeforeUpdate = infoSocieteRepository.findAll().size();

        // Create the InfoSociete
        InfoSocieteDTO infoSocieteDTO = infoSocieteMapper.toDto(infoSociete);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoSocieteMockMvc.perform(put("/api/info-societes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoSocieteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InfoSociete in the database
        List<InfoSociete> infoSocieteList = infoSocieteRepository.findAll();
        assertThat(infoSocieteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InfoSociete in Elasticsearch
        verify(mockInfoSocieteSearchRepository, times(0)).save(infoSociete);
    }

    @Test
    @Transactional
    public void deleteInfoSociete() throws Exception {
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);

        int databaseSizeBeforeDelete = infoSocieteRepository.findAll().size();

        // Delete the infoSociete
        restInfoSocieteMockMvc.perform(delete("/api/info-societes/{id}", infoSociete.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InfoSociete> infoSocieteList = infoSocieteRepository.findAll();
        assertThat(infoSocieteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InfoSociete in Elasticsearch
        verify(mockInfoSocieteSearchRepository, times(1)).deleteById(infoSociete.getId());
    }

    @Test
    @Transactional
    public void searchInfoSociete() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        infoSocieteRepository.saveAndFlush(infoSociete);
        when(mockInfoSocieteSearchRepository.search(queryStringQuery("id:" + infoSociete.getId())))
            .thenReturn(Collections.singletonList(infoSociete));

        // Search the infoSociete
        restInfoSocieteMockMvc.perform(get("/api/_search/info-societes?query=id:" + infoSociete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoSociete.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomSociete").value(hasItem(DEFAULT_NOM_SOCIETE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].tel1").value(hasItem(DEFAULT_TEL_1)))
            .andExpect(jsonPath("$.[*].tel2").value(hasItem(DEFAULT_TEL_2)))
            .andExpect(jsonPath("$.[*].tel3").value(hasItem(DEFAULT_TEL_3)))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1)))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].youtube").value(hasItem(DEFAULT_YOUTUBE)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)))
            .andExpect(jsonPath("$.[*].tiktok").value(hasItem(DEFAULT_TIKTOK)))
            .andExpect(jsonPath("$.[*].matriculeFiscal").value(hasItem(DEFAULT_MATRICULE_FISCAL)))
            .andExpect(jsonPath("$.[*].valeurMinPanier").value(hasItem(DEFAULT_VALEUR_MIN_PANIER.doubleValue())));
    }
}
