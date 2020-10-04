package org.fininfo.elbazar.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.fininfo.elbazar.service.CategorieQueryService;
import org.fininfo.elbazar.service.CategorieService;
import org.fininfo.elbazar.service.dto.CategorieCriteria;
import org.fininfo.elbazar.service.dto.CategorieDTO;
import org.fininfo.elbazar.service.dto.common.CatalogueDTO;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.fininfo.elbazar.domain.Categorie}.
 */
@RestController
@RequestMapping("/api")
public class CategorieResource {

    private final Logger log = LoggerFactory.getLogger(CategorieResource.class);

    private static final String ENTITY_NAME = "categorie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieService categorieService;

    private final CategorieQueryService categorieQueryService;

    public CategorieResource(CategorieService categorieService, CategorieQueryService categorieQueryService) {
        this.categorieService = categorieService;
        this.categorieQueryService = categorieQueryService;
    }

    /**
     * {@code POST  /categories} : Create a new categorie.
     *
     * @param categorieDTO the categorieDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieDTO, or with status {@code 400 (Bad Request)} if the categorie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categories")
    public ResponseEntity<CategorieDTO> createCategorie(@Valid @RequestBody CategorieDTO categorieDTO) throws URISyntaxException {
        log.debug("REST request to save Categorie : {}", categorieDTO);
        if (categorieDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieDTO result = categorieService.save(categorieDTO);
        return ResponseEntity.created(new URI("/api/categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categories} : Updates an existing categorie.
     *
     * @param categorieDTO the categorieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieDTO,
     * or with status {@code 400 (Bad Request)} if the categorieDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categories")
    public ResponseEntity<CategorieDTO> updateCategorie(@Valid @RequestBody CategorieDTO categorieDTO) throws URISyntaxException {
        log.debug("REST request to update Categorie : {}", categorieDTO);
        if (categorieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategorieDTO result = categorieService.save(categorieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorieDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/categories")
    public ResponseEntity<List<CategorieDTO>> getAllCategories(CategorieCriteria criteria) {
        log.debug("REST request to get Categories by criteria: {}", criteria);
        List<CategorieDTO> entityList = categorieQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /categories/count} : count all the categories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/categories/count")
    public ResponseEntity<Long> countCategories(CategorieCriteria criteria) {
        log.debug("REST request to count Categories by criteria: {}", criteria);
        return ResponseEntity.ok().body(categorieQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categories/:id} : get the "id" categorie.
     *
     * @param id the id of the categorieDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategorieDTO> getCategorie(@PathVariable Long id) {
        log.debug("REST request to get Categorie : {}", id);
        Optional<CategorieDTO> categorieDTO = categorieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorieDTO);
    }

    /**
     * {@code DELETE  /categories/:id} : delete the "id" categorie.
     *
     * @param id the id of the categorieDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        log.debug("REST request to delete Categorie : {}", id);

        categorieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/categories?query=:query} : search for the categorie corresponding
     * to the query.
     *
     * @param query the query of the categorie search.
     * @return the result of the search.
     */
    @GetMapping("/_search/categories")
    public List<CategorieDTO> searchCategories(@RequestParam String query) {
        log.debug("REST request to search Categories for query {}", query);
        return categorieService.search(query);
    }


        /**
     * {@code GET  /categories} : get all the categories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/categories_pageable")
    public ResponseEntity<Page<CategorieDTO>> getAllCategoriesPageable(CategorieCriteria criteria, Pageable page) {
        log.debug("REST request to get Categories by criteria: {}", criteria);
        Page<CategorieDTO> entityList = categorieQueryService.findByCriteria(criteria, page);
        return ResponseEntity.ok().body(entityList);
    }

    /********************* FININFO CODE: START ********************* */

    @GetMapping("/catalogue")
    public List<CatalogueDTO> getAll() {
        // This returns a JSON of "Categories" and "Sous Categories"
        List<CatalogueDTO> catalogue = categorieService.findCatAndSousCat();
        return catalogue;
    }
    /********************* FININFO CODE: END   ********************* */

}