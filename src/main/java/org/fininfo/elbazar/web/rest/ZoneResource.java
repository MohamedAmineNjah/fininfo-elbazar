package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.service.ZoneService;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.fininfo.elbazar.service.dto.ZoneDTO;
import org.fininfo.elbazar.service.dto.ZoneCriteria;
import org.fininfo.elbazar.service.ZoneQueryService;

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
 * REST controller for managing {@link org.fininfo.elbazar.domain.Zone}.
 */
@RestController
@RequestMapping("/api")
public class ZoneResource {

    private final Logger log = LoggerFactory.getLogger(ZoneResource.class);

    private static final String ENTITY_NAME = "zone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZoneService zoneService;

    private final ZoneQueryService zoneQueryService;

    public ZoneResource(ZoneService zoneService, ZoneQueryService zoneQueryService) {
        this.zoneService = zoneService;
        this.zoneQueryService = zoneQueryService;
    }

    /**
     * {@code POST  /zones} : Create a new zone.
     *
     * @param zoneDTO the zoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zoneDTO, or with status {@code 400 (Bad Request)} if the zone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/zones")
    public ResponseEntity<ZoneDTO> createZone(@Valid @RequestBody ZoneDTO zoneDTO) throws URISyntaxException {
        log.debug("REST request to save Zone : {}", zoneDTO);
        if (zoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new zone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZoneDTO result = zoneService.save(zoneDTO);
        return ResponseEntity.created(new URI("/api/zones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /zones} : Updates an existing zone.
     *
     * @param zoneDTO the zoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zoneDTO,
     * or with status {@code 400 (Bad Request)} if the zoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/zones")
    public ResponseEntity<ZoneDTO> updateZone(@Valid @RequestBody ZoneDTO zoneDTO) throws URISyntaxException {
        log.debug("REST request to update Zone : {}", zoneDTO);
        if (zoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZoneDTO result = zoneService.save(zoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, zoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /zones} : get all the zones.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zones in body.
     */
    @GetMapping("/zones")
    public ResponseEntity<List<ZoneDTO>> getAllZones(ZoneCriteria criteria) {
        log.debug("REST request to get Zones by criteria: {}", criteria);
        List<ZoneDTO> entityList = zoneQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /zones/count} : count all the zones.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/zones/count")
    public ResponseEntity<Long> countZones(ZoneCriteria criteria) {
        log.debug("REST request to count Zones by criteria: {}", criteria);
        return ResponseEntity.ok().body(zoneQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /zones/:id} : get the "id" zone.
     *
     * @param id the id of the zoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/zones/{id}")
    public ResponseEntity<ZoneDTO> getZone(@PathVariable Long id) {
        log.debug("REST request to get Zone : {}", id);
        Optional<ZoneDTO> zoneDTO = zoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zoneDTO);
    }

    /**
     * {@code DELETE  /zones/:id} : delete the "id" zone.
     *
     * @param id the id of the zoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/zones/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        log.debug("REST request to delete Zone : {}", id);

        zoneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/zones?query=:query} : search for the zone corresponding
     * to the query.
     *
     * @param query the query of the zone search.
     * @return the result of the search.
     */
    @GetMapping("/_search/zones")
    public List<ZoneDTO> searchZones(@RequestParam String query) {
        log.debug("REST request to search Zones for query {}", query);
        return zoneService.search(query);
    }
}
