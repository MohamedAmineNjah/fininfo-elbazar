package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.service.AffectationZoneService;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.fininfo.elbazar.service.dto.AffectationZoneDTO;
import org.fininfo.elbazar.service.dto.AffectationZoneCriteria;
import org.fininfo.elbazar.service.AffectationZoneQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
 * REST controller for managing {@link org.fininfo.elbazar.domain.AffectationZone}.
 */
@RestController
@RequestMapping("/api")
public class AffectationZoneResource {

    private final Logger log = LoggerFactory.getLogger(AffectationZoneResource.class);

    private static final String ENTITY_NAME = "affectationZone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AffectationZoneService affectationZoneService;

    private final AffectationZoneQueryService affectationZoneQueryService;

    public AffectationZoneResource(AffectationZoneService affectationZoneService, AffectationZoneQueryService affectationZoneQueryService) {
        this.affectationZoneService = affectationZoneService;
        this.affectationZoneQueryService = affectationZoneQueryService;
    }

    /**
     * {@code POST  /affectation-zones} : Create a new affectationZone.
     *
     * @param affectationZoneDTO the affectationZoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new affectationZoneDTO, or with status {@code 400 (Bad Request)} if the affectationZone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/affectation-zones")
    public ResponseEntity<AffectationZoneDTO> createAffectationZone(@Valid @RequestBody AffectationZoneDTO affectationZoneDTO) throws URISyntaxException {
        log.debug("REST request to save AffectationZone : {}", affectationZoneDTO);
        if (affectationZoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new affectationZone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AffectationZoneDTO result = affectationZoneService.save(affectationZoneDTO);
        return ResponseEntity.created(new URI("/api/affectation-zones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /affectation-zones} : Updates an existing affectationZone.
     *
     * @param affectationZoneDTO the affectationZoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affectationZoneDTO,
     * or with status {@code 400 (Bad Request)} if the affectationZoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the affectationZoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/affectation-zones")
    public ResponseEntity<AffectationZoneDTO> updateAffectationZone(@Valid @RequestBody AffectationZoneDTO affectationZoneDTO) throws URISyntaxException {
        log.debug("REST request to update AffectationZone : {}", affectationZoneDTO);
        if (affectationZoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AffectationZoneDTO result = affectationZoneService.save(affectationZoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affectationZoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /affectation-zones} : get all the affectationZones.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of affectationZones in body.
     */
    @GetMapping("/affectation-zones")
    public ResponseEntity<List<AffectationZoneDTO>> getAllAffectationZones(AffectationZoneCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AffectationZones by criteria: {}", criteria);
        Page<AffectationZoneDTO> page = affectationZoneQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /affectation-zones/count} : count all the affectationZones.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/affectation-zones/count")
    public ResponseEntity<Long> countAffectationZones(AffectationZoneCriteria criteria) {
        log.debug("REST request to count AffectationZones by criteria: {}", criteria);
        return ResponseEntity.ok().body(affectationZoneQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /affectation-zones/:id} : get the "id" affectationZone.
     *
     * @param id the id of the affectationZoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the affectationZoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/affectation-zones/{id}")
    public ResponseEntity<AffectationZoneDTO> getAffectationZone(@PathVariable Long id) {
        log.debug("REST request to get AffectationZone : {}", id);
        Optional<AffectationZoneDTO> affectationZoneDTO = affectationZoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(affectationZoneDTO);
    }

    /**
     * {@code DELETE  /affectation-zones/:id} : delete the "id" affectationZone.
     *
     * @param id the id of the affectationZoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/affectation-zones/{id}")
    public ResponseEntity<Void> deleteAffectationZone(@PathVariable Long id) {
        log.debug("REST request to delete AffectationZone : {}", id);

        affectationZoneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/affectation-zones?query=:query} : search for the affectationZone corresponding
     * to the query.
     *
     * @param query the query of the affectationZone search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/affectation-zones")
    public ResponseEntity<List<AffectationZoneDTO>> searchAffectationZones(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AffectationZones for query {}", query);
        Page<AffectationZoneDTO> page = affectationZoneService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
