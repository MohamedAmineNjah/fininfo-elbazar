package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.service.InfoSocieteService;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.fininfo.elbazar.service.dto.InfoSocieteDTO;
import org.fininfo.elbazar.service.dto.InfoSocieteCriteria;
import org.fininfo.elbazar.service.InfoSocieteQueryService;

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
 * REST controller for managing {@link org.fininfo.elbazar.domain.InfoSociete}.
 */
@RestController
@RequestMapping("/api")
public class InfoSocieteResource {

    private final Logger log = LoggerFactory.getLogger(InfoSocieteResource.class);

    private static final String ENTITY_NAME = "infoSociete";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfoSocieteService infoSocieteService;

    private final InfoSocieteQueryService infoSocieteQueryService;

    public InfoSocieteResource(InfoSocieteService infoSocieteService, InfoSocieteQueryService infoSocieteQueryService) {
        this.infoSocieteService = infoSocieteService;
        this.infoSocieteQueryService = infoSocieteQueryService;
    }

    /**
     * {@code POST  /info-societes} : Create a new infoSociete.
     *
     * @param infoSocieteDTO the infoSocieteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoSocieteDTO, or with status {@code 400 (Bad Request)} if the infoSociete has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/info-societes")
    public ResponseEntity<InfoSocieteDTO> createInfoSociete(@Valid @RequestBody InfoSocieteDTO infoSocieteDTO) throws URISyntaxException {
        log.debug("REST request to save InfoSociete : {}", infoSocieteDTO);
        if (infoSocieteDTO.getId() != null) {
            throw new BadRequestAlertException("A new infoSociete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfoSocieteDTO result = infoSocieteService.save(infoSocieteDTO);
        return ResponseEntity.created(new URI("/api/info-societes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /info-societes} : Updates an existing infoSociete.
     *
     * @param infoSocieteDTO the infoSocieteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoSocieteDTO,
     * or with status {@code 400 (Bad Request)} if the infoSocieteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoSocieteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/info-societes")
    public ResponseEntity<InfoSocieteDTO> updateInfoSociete(@Valid @RequestBody InfoSocieteDTO infoSocieteDTO) throws URISyntaxException {
        log.debug("REST request to update InfoSociete : {}", infoSocieteDTO);
        if (infoSocieteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InfoSocieteDTO result = infoSocieteService.save(infoSocieteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infoSocieteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /info-societes} : get all the infoSocietes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoSocietes in body.
     */
    @GetMapping("/info-societes")
    public ResponseEntity<List<InfoSocieteDTO>> getAllInfoSocietes(InfoSocieteCriteria criteria) {
        log.debug("REST request to get InfoSocietes by criteria: {}", criteria);
        List<InfoSocieteDTO> entityList = infoSocieteQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /info-societes/count} : count all the infoSocietes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/info-societes/count")
    public ResponseEntity<Long> countInfoSocietes(InfoSocieteCriteria criteria) {
        log.debug("REST request to count InfoSocietes by criteria: {}", criteria);
        return ResponseEntity.ok().body(infoSocieteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /info-societes/:id} : get the "id" infoSociete.
     *
     * @param id the id of the infoSocieteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoSocieteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/info-societes/{id}")
    public ResponseEntity<InfoSocieteDTO> getInfoSociete(@PathVariable Long id) {
        log.debug("REST request to get InfoSociete : {}", id);
        Optional<InfoSocieteDTO> infoSocieteDTO = infoSocieteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(infoSocieteDTO);
    }

    /**
     * {@code DELETE  /info-societes/:id} : delete the "id" infoSociete.
     *
     * @param id the id of the infoSocieteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/info-societes/{id}")
    public ResponseEntity<Void> deleteInfoSociete(@PathVariable Long id) {
        log.debug("REST request to delete InfoSociete : {}", id);

        infoSocieteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/info-societes?query=:query} : search for the infoSociete corresponding
     * to the query.
     *
     * @param query the query of the infoSociete search.
     * @return the result of the search.
     */
    @GetMapping("/_search/info-societes")
    public List<InfoSocieteDTO> searchInfoSocietes(@RequestParam String query) {
        log.debug("REST request to search InfoSocietes for query {}", query);
        return infoSocieteService.search(query);
    }
}
