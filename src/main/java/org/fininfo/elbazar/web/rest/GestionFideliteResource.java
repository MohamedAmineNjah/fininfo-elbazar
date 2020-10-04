package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.service.GestionFideliteService;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.fininfo.elbazar.service.dto.GestionFideliteDTO;
import org.fininfo.elbazar.service.dto.GestionFideliteCriteria;
import org.fininfo.elbazar.service.GestionFideliteQueryService;

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
 * REST controller for managing {@link org.fininfo.elbazar.domain.GestionFidelite}.
 */
@RestController
@RequestMapping("/api")
public class GestionFideliteResource {

    private final Logger log = LoggerFactory.getLogger(GestionFideliteResource.class);

    private static final String ENTITY_NAME = "gestionFidelite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GestionFideliteService gestionFideliteService;

    private final GestionFideliteQueryService gestionFideliteQueryService;

    public GestionFideliteResource(GestionFideliteService gestionFideliteService, GestionFideliteQueryService gestionFideliteQueryService) {
        this.gestionFideliteService = gestionFideliteService;
        this.gestionFideliteQueryService = gestionFideliteQueryService;
    }

    /**
     * {@code POST  /gestion-fidelites} : Create a new gestionFidelite.
     *
     * @param gestionFideliteDTO the gestionFideliteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gestionFideliteDTO, or with status {@code 400 (Bad Request)} if the gestionFidelite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gestion-fidelites")
    public ResponseEntity<GestionFideliteDTO> createGestionFidelite(@Valid @RequestBody GestionFideliteDTO gestionFideliteDTO) throws URISyntaxException {
        log.debug("REST request to save GestionFidelite : {}", gestionFideliteDTO);
        if (gestionFideliteDTO.getId() != null) {
            throw new BadRequestAlertException("A new gestionFidelite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GestionFideliteDTO result = gestionFideliteService.save(gestionFideliteDTO);
        return ResponseEntity.created(new URI("/api/gestion-fidelites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gestion-fidelites} : Updates an existing gestionFidelite.
     *
     * @param gestionFideliteDTO the gestionFideliteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gestionFideliteDTO,
     * or with status {@code 400 (Bad Request)} if the gestionFideliteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gestionFideliteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gestion-fidelites")
    public ResponseEntity<GestionFideliteDTO> updateGestionFidelite(@Valid @RequestBody GestionFideliteDTO gestionFideliteDTO) throws URISyntaxException {
        log.debug("REST request to update GestionFidelite : {}", gestionFideliteDTO);
        if (gestionFideliteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GestionFideliteDTO result = gestionFideliteService.save(gestionFideliteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gestionFideliteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gestion-fidelites} : get all the gestionFidelites.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gestionFidelites in body.
     */
    @GetMapping("/gestion-fidelites")
    public ResponseEntity<List<GestionFideliteDTO>> getAllGestionFidelites(GestionFideliteCriteria criteria) {
        log.debug("REST request to get GestionFidelites by criteria: {}", criteria);
        List<GestionFideliteDTO> entityList = gestionFideliteQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /gestion-fidelites/count} : count all the gestionFidelites.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/gestion-fidelites/count")
    public ResponseEntity<Long> countGestionFidelites(GestionFideliteCriteria criteria) {
        log.debug("REST request to count GestionFidelites by criteria: {}", criteria);
        return ResponseEntity.ok().body(gestionFideliteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /gestion-fidelites/:id} : get the "id" gestionFidelite.
     *
     * @param id the id of the gestionFideliteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gestionFideliteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gestion-fidelites/{id}")
    public ResponseEntity<GestionFideliteDTO> getGestionFidelite(@PathVariable Long id) {
        log.debug("REST request to get GestionFidelite : {}", id);
        Optional<GestionFideliteDTO> gestionFideliteDTO = gestionFideliteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gestionFideliteDTO);
    }

    /**
     * {@code DELETE  /gestion-fidelites/:id} : delete the "id" gestionFidelite.
     *
     * @param id the id of the gestionFideliteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gestion-fidelites/{id}")
    public ResponseEntity<Void> deleteGestionFidelite(@PathVariable Long id) {
        log.debug("REST request to delete GestionFidelite : {}", id);

        gestionFideliteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/gestion-fidelites?query=:query} : search for the gestionFidelite corresponding
     * to the query.
     *
     * @param query the query of the gestionFidelite search.
     * @return the result of the search.
     */
    @GetMapping("/_search/gestion-fidelites")
    public List<GestionFideliteDTO> searchGestionFidelites(@RequestParam String query) {
        log.debug("REST request to search GestionFidelites for query {}", query);
        return gestionFideliteService.search(query);
    }
}
