package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.ProduitUnite;
import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.repository.ProduitUniteRepository;
import org.fininfo.elbazar.repository.search.ProduitUniteSearchRepository;
import org.fininfo.elbazar.service.ProduitUniteService;
import org.fininfo.elbazar.service.dto.ProduitUniteDTO;
import org.fininfo.elbazar.service.mapper.ProduitUniteMapper;
import org.fininfo.elbazar.service.dto.ProduitUniteCriteria;
import org.fininfo.elbazar.service.ProduitUniteQueryService;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProduitUniteResource} REST controller.
 */
@SpringBootTest(classes = ElbazarApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProduitUniteResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private ProduitUniteRepository produitUniteRepository;

    @Autowired
    private ProduitUniteMapper produitUniteMapper;

    @Autowired
    private ProduitUniteService produitUniteService;

    /**
     * This repository is mocked in the org.fininfo.elbazar.repository.search test package.
     *
     * @see org.fininfo.elbazar.repository.search.ProduitUniteSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProduitUniteSearchRepository mockProduitUniteSearchRepository;

    @Autowired
    private ProduitUniteQueryService produitUniteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProduitUniteMockMvc;

    private ProduitUnite produitUnite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProduitUnite createEntity(EntityManager em) {
        ProduitUnite produitUnite = new ProduitUnite()
            .code(DEFAULT_CODE)
            .nom(DEFAULT_NOM);
        return produitUnite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProduitUnite createUpdatedEntity(EntityManager em) {
        ProduitUnite produitUnite = new ProduitUnite()
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM);
        return produitUnite;
    }

    @BeforeEach
    public void initTest() {
        produitUnite = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduitUnite() throws Exception {
        int databaseSizeBeforeCreate = produitUniteRepository.findAll().size();
        // Create the ProduitUnite
        ProduitUniteDTO produitUniteDTO = produitUniteMapper.toDto(produitUnite);
        restProduitUniteMockMvc.perform(post("/api/produit-unites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitUniteDTO)))
            .andExpect(status().isCreated());

        // Validate the ProduitUnite in the database
        List<ProduitUnite> produitUniteList = produitUniteRepository.findAll();
        assertThat(produitUniteList).hasSize(databaseSizeBeforeCreate + 1);
        ProduitUnite testProduitUnite = produitUniteList.get(produitUniteList.size() - 1);
        assertThat(testProduitUnite.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProduitUnite.getNom()).isEqualTo(DEFAULT_NOM);

        // Validate the ProduitUnite in Elasticsearch
        verify(mockProduitUniteSearchRepository, times(1)).save(testProduitUnite);
    }

    @Test
    @Transactional
    public void createProduitUniteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produitUniteRepository.findAll().size();

        // Create the ProduitUnite with an existing ID
        produitUnite.setId(1L);
        ProduitUniteDTO produitUniteDTO = produitUniteMapper.toDto(produitUnite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitUniteMockMvc.perform(post("/api/produit-unites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitUniteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProduitUnite in the database
        List<ProduitUnite> produitUniteList = produitUniteRepository.findAll();
        assertThat(produitUniteList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProduitUnite in Elasticsearch
        verify(mockProduitUniteSearchRepository, times(0)).save(produitUnite);
    }


    @Test
    @Transactional
    public void getAllProduitUnites() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList
        restProduitUniteMockMvc.perform(get("/api/produit-unites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produitUnite.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
    
    @Test
    @Transactional
    public void getProduitUnite() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get the produitUnite
        restProduitUniteMockMvc.perform(get("/api/produit-unites/{id}", produitUnite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produitUnite.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }


    @Test
    @Transactional
    public void getProduitUnitesByIdFiltering() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        Long id = produitUnite.getId();

        defaultProduitUniteShouldBeFound("id.equals=" + id);
        defaultProduitUniteShouldNotBeFound("id.notEquals=" + id);

        defaultProduitUniteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProduitUniteShouldNotBeFound("id.greaterThan=" + id);

        defaultProduitUniteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProduitUniteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProduitUnitesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList where code equals to DEFAULT_CODE
        defaultProduitUniteShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the produitUniteList where code equals to UPDATED_CODE
        defaultProduitUniteShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProduitUnitesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList where code not equals to DEFAULT_CODE
        defaultProduitUniteShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the produitUniteList where code not equals to UPDATED_CODE
        defaultProduitUniteShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProduitUnitesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProduitUniteShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the produitUniteList where code equals to UPDATED_CODE
        defaultProduitUniteShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProduitUnitesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList where code is not null
        defaultProduitUniteShouldBeFound("code.specified=true");

        // Get all the produitUniteList where code is null
        defaultProduitUniteShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitUnitesByCodeContainsSomething() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList where code contains DEFAULT_CODE
        defaultProduitUniteShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the produitUniteList where code contains UPDATED_CODE
        defaultProduitUniteShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProduitUnitesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList where code does not contain DEFAULT_CODE
        defaultProduitUniteShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the produitUniteList where code does not contain UPDATED_CODE
        defaultProduitUniteShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllProduitUnitesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList where nom equals to DEFAULT_NOM
        defaultProduitUniteShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the produitUniteList where nom equals to UPDATED_NOM
        defaultProduitUniteShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllProduitUnitesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList where nom not equals to DEFAULT_NOM
        defaultProduitUniteShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the produitUniteList where nom not equals to UPDATED_NOM
        defaultProduitUniteShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllProduitUnitesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultProduitUniteShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the produitUniteList where nom equals to UPDATED_NOM
        defaultProduitUniteShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllProduitUnitesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList where nom is not null
        defaultProduitUniteShouldBeFound("nom.specified=true");

        // Get all the produitUniteList where nom is null
        defaultProduitUniteShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitUnitesByNomContainsSomething() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList where nom contains DEFAULT_NOM
        defaultProduitUniteShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the produitUniteList where nom contains UPDATED_NOM
        defaultProduitUniteShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllProduitUnitesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList where nom does not contain DEFAULT_NOM
        defaultProduitUniteShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the produitUniteList where nom does not contain UPDATED_NOM
        defaultProduitUniteShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllProduitUnitesByProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);
        Produit produit = ProduitResourceIT.createEntity(em);
        em.persist(produit);
        em.flush();
        produitUnite.addProduit(produit);
        produitUniteRepository.saveAndFlush(produitUnite);
        Long produitId = produit.getId();

        // Get all the produitUniteList where produit equals to produitId
        defaultProduitUniteShouldBeFound("produitId.equals=" + produitId);

        // Get all the produitUniteList where produit equals to produitId + 1
        defaultProduitUniteShouldNotBeFound("produitId.equals=" + (produitId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProduitUniteShouldBeFound(String filter) throws Exception {
        restProduitUniteMockMvc.perform(get("/api/produit-unites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produitUnite.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));

        // Check, that the count call also returns 1
        restProduitUniteMockMvc.perform(get("/api/produit-unites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProduitUniteShouldNotBeFound(String filter) throws Exception {
        restProduitUniteMockMvc.perform(get("/api/produit-unites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProduitUniteMockMvc.perform(get("/api/produit-unites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProduitUnite() throws Exception {
        // Get the produitUnite
        restProduitUniteMockMvc.perform(get("/api/produit-unites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduitUnite() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        int databaseSizeBeforeUpdate = produitUniteRepository.findAll().size();

        // Update the produitUnite
        ProduitUnite updatedProduitUnite = produitUniteRepository.findById(produitUnite.getId()).get();
        // Disconnect from session so that the updates on updatedProduitUnite are not directly saved in db
        em.detach(updatedProduitUnite);
        updatedProduitUnite
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM);
        ProduitUniteDTO produitUniteDTO = produitUniteMapper.toDto(updatedProduitUnite);

        restProduitUniteMockMvc.perform(put("/api/produit-unites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitUniteDTO)))
            .andExpect(status().isOk());

        // Validate the ProduitUnite in the database
        List<ProduitUnite> produitUniteList = produitUniteRepository.findAll();
        assertThat(produitUniteList).hasSize(databaseSizeBeforeUpdate);
        ProduitUnite testProduitUnite = produitUniteList.get(produitUniteList.size() - 1);
        assertThat(testProduitUnite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProduitUnite.getNom()).isEqualTo(UPDATED_NOM);

        // Validate the ProduitUnite in Elasticsearch
        verify(mockProduitUniteSearchRepository, times(1)).save(testProduitUnite);
    }

    @Test
    @Transactional
    public void updateNonExistingProduitUnite() throws Exception {
        int databaseSizeBeforeUpdate = produitUniteRepository.findAll().size();

        // Create the ProduitUnite
        ProduitUniteDTO produitUniteDTO = produitUniteMapper.toDto(produitUnite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitUniteMockMvc.perform(put("/api/produit-unites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitUniteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProduitUnite in the database
        List<ProduitUnite> produitUniteList = produitUniteRepository.findAll();
        assertThat(produitUniteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProduitUnite in Elasticsearch
        verify(mockProduitUniteSearchRepository, times(0)).save(produitUnite);
    }

    @Test
    @Transactional
    public void deleteProduitUnite() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        int databaseSizeBeforeDelete = produitUniteRepository.findAll().size();

        // Delete the produitUnite
        restProduitUniteMockMvc.perform(delete("/api/produit-unites/{id}", produitUnite.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProduitUnite> produitUniteList = produitUniteRepository.findAll();
        assertThat(produitUniteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProduitUnite in Elasticsearch
        verify(mockProduitUniteSearchRepository, times(1)).deleteById(produitUnite.getId());
    }

    @Test
    @Transactional
    public void searchProduitUnite() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);
        when(mockProduitUniteSearchRepository.search(queryStringQuery("id:" + produitUnite.getId())))
            .thenReturn(Collections.singletonList(produitUnite));

        // Search the produitUnite
        restProduitUniteMockMvc.perform(get("/api/_search/produit-unites?query=id:" + produitUnite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produitUnite.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
}
