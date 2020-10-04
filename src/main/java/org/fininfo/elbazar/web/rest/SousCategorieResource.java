package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.service.SousCategorieService;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.fininfo.elbazar.service.dto.SousCategorieDTO;
import org.fininfo.elbazar.service.dto.SousCategorieCriteria;
import org.fininfo.elbazar.service.SousCategorieQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link org.fininfo.elbazar.domain.SousCategorie}.
 */
@RestController
@RequestMapping("/api")
public class SousCategorieResource {

    private final Logger log = LoggerFactory.getLogger(SousCategorieResource.class);

    private static final String ENTITY_NAME = "sousCategorie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SousCategorieService sousCategorieService;

    private final SousCategorieQueryService sousCategorieQueryService;

    public SousCategorieResource(SousCategorieService sousCategorieService, SousCategorieQueryService sousCategorieQueryService) {
        this.sousCategorieService = sousCategorieService;
        this.sousCategorieQueryService = sousCategorieQueryService;
    }

    /**
     * {@code POST  /sous-categories} : Create a new sousCategorie.
     *
     * @param sousCategorieDTO the sousCategorieDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sousCategorieDTO, or with status {@code 400 (Bad Request)} if the sousCategorie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sous-categories")
    public ResponseEntity<SousCategorieDTO> createSousCategorie(@Valid @RequestBody SousCategorieDTO sousCategorieDTO) throws URISyntaxException {
        log.debug("REST request to save SousCategorie : {}", sousCategorieDTO);
        if (sousCategorieDTO.getId() != null) {
            throw new BadRequestAlertException("A new sousCategorie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SousCategorieDTO result = sousCategorieService.save(sousCategorieDTO);
        return ResponseEntity.created(new URI("/api/sous-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sous-categories} : Updates an existing sousCategorie.
     *
     * @param sousCategorieDTO the sousCategorieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sousCategorieDTO,
     * or with status {@code 400 (Bad Request)} if the sousCategorieDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sousCategorieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sous-categories")
    public ResponseEntity<SousCategorieDTO> updateSousCategorie(@Valid @RequestBody SousCategorieDTO sousCategorieDTO) throws URISyntaxException {
        log.debug("REST request to update SousCategorie : {}", sousCategorieDTO);
        if (sousCategorieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SousCategorieDTO result = sousCategorieService.save(sousCategorieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sousCategorieDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sous-categories} : get all the sousCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sousCategories in body.
     */
    @GetMapping("/sous-categories")
    public ResponseEntity<List<SousCategorieDTO>> getAllSousCategories(SousCategorieCriteria criteria) {
        log.debug("REST request to get SousCategories by criteria: {}", criteria);
        List<SousCategorieDTO> entityList = sousCategorieQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /sous-categories/count} : count all the sousCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sous-categories/count")
    public ResponseEntity<Long> countSousCategories(SousCategorieCriteria criteria) {
        log.debug("REST request to count SousCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(sousCategorieQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sous-categories/:id} : get the "id" sousCategorie.
     *
     * @param id the id of the sousCategorieDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sousCategorieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sous-categories/{id}")
    public ResponseEntity<SousCategorieDTO> getSousCategorie(@PathVariable Long id) {
        log.debug("REST request to get SousCategorie : {}", id);
        Optional<SousCategorieDTO> sousCategorieDTO = sousCategorieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sousCategorieDTO);
    }

    /**
     * {@code DELETE  /sous-categories/:id} : delete the "id" sousCategorie.
     *
     * @param id the id of the sousCategorieDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sous-categories/{id}")
    public ResponseEntity<Void> deleteSousCategorie(@PathVariable Long id) {
        log.debug("REST request to delete SousCategorie : {}", id);

        sousCategorieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/sous-categories?query=:query} : search for the sousCategorie corresponding
     * to the query.
     *
     * @param query the query of the sousCategorie search.
     * @return the result of the search.
     */
    @GetMapping("/_search/sous-categories")
    public List<SousCategorieDTO> searchSousCategories(@RequestParam String query) {
        log.debug("REST request to search SousCategories for query {}", query);
        return sousCategorieService.search(query);
    }
}
