package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.service.MouvementStockService;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.fininfo.elbazar.service.dto.MouvementStockDTO;
import org.fininfo.elbazar.service.dto.MouvementStockCriteria;
import org.fininfo.elbazar.service.MouvementStockQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link org.fininfo.elbazar.domain.MouvementStock}.
 */
@RestController
@RequestMapping("/api")
public class MouvementStockResource {

    private final Logger log = LoggerFactory.getLogger(MouvementStockResource.class);

    private static final String ENTITY_NAME = "mouvementStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MouvementStockService mouvementStockService;

    private final MouvementStockQueryService mouvementStockQueryService;

    public MouvementStockResource(MouvementStockService mouvementStockService, MouvementStockQueryService mouvementStockQueryService) {
        this.mouvementStockService = mouvementStockService;
        this.mouvementStockQueryService = mouvementStockQueryService;
    }

    /**
     * {@code POST  /mouvement-stocks} : Create a new mouvementStock.
     *
     * @param mouvementStockDTO the mouvementStockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mouvementStockDTO, or with status {@code 400 (Bad Request)} if the mouvementStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mouvement-stocks")
    public ResponseEntity<MouvementStockDTO> createMouvementStock(@RequestBody MouvementStockDTO mouvementStockDTO) throws URISyntaxException {
        log.debug("REST request to save MouvementStock : {}", mouvementStockDTO);
        if (mouvementStockDTO.getId() != null) {
            throw new BadRequestAlertException("A new mouvementStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MouvementStockDTO result = mouvementStockService.save(mouvementStockDTO);
        return ResponseEntity.created(new URI("/api/mouvement-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mouvement-stocks} : Updates an existing mouvementStock.
     *
     * @param mouvementStockDTO the mouvementStockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mouvementStockDTO,
     * or with status {@code 400 (Bad Request)} if the mouvementStockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mouvementStockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mouvement-stocks")
    public ResponseEntity<MouvementStockDTO> updateMouvementStock(@RequestBody MouvementStockDTO mouvementStockDTO) throws URISyntaxException {
        log.debug("REST request to update MouvementStock : {}", mouvementStockDTO);
        if (mouvementStockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MouvementStockDTO result = mouvementStockService.save(mouvementStockDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mouvementStockDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mouvement-stocks} : get all the mouvementStocks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mouvementStocks in body.
     */
    @GetMapping("/mouvement-stocks")
    public ResponseEntity<List<MouvementStockDTO>> getAllMouvementStocks(MouvementStockCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MouvementStocks by criteria: {}", criteria);
        Page<MouvementStockDTO> page = mouvementStockQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mouvement-stocks/count} : count all the mouvementStocks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/mouvement-stocks/count")
    public ResponseEntity<Long> countMouvementStocks(MouvementStockCriteria criteria) {
        log.debug("REST request to count MouvementStocks by criteria: {}", criteria);
        return ResponseEntity.ok().body(mouvementStockQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /mouvement-stocks/:id} : get the "id" mouvementStock.
     *
     * @param id the id of the mouvementStockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mouvementStockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mouvement-stocks/{id}")
    public ResponseEntity<MouvementStockDTO> getMouvementStock(@PathVariable Long id) {
        log.debug("REST request to get MouvementStock : {}", id);
        Optional<MouvementStockDTO> mouvementStockDTO = mouvementStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mouvementStockDTO);
    }

    /**
     * {@code DELETE  /mouvement-stocks/:id} : delete the "id" mouvementStock.
     *
     * @param id the id of the mouvementStockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mouvement-stocks/{id}")
    public ResponseEntity<Void> deleteMouvementStock(@PathVariable Long id) {
        log.debug("REST request to delete MouvementStock : {}", id);

        mouvementStockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/mouvement-stocks?query=:query} : search for the mouvementStock corresponding
     * to the query.
     *
     * @param query the query of the mouvementStock search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/mouvement-stocks")
    public ResponseEntity<List<MouvementStockDTO>> searchMouvementStocks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MouvementStocks for query {}", query);
        Page<MouvementStockDTO> page = mouvementStockService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
