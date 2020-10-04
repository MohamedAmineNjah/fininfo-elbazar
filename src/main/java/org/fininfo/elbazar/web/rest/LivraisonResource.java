package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.service.LivraisonService;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.fininfo.elbazar.service.dto.LivraisonDTO;
import org.fininfo.elbazar.service.dto.LivraisonCriteria;
import org.fininfo.elbazar.domain.Zone;
import org.fininfo.elbazar.domain.enumeration.ProfileClient;
import org.fininfo.elbazar.service.LivraisonQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link org.fininfo.elbazar.domain.Livraison}.
 */
@RestController
@RequestMapping("/api")
public class LivraisonResource {

    private final Logger log = LoggerFactory.getLogger(LivraisonResource.class);

    private static final String ENTITY_NAME = "livraison";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LivraisonService livraisonService;

    private final LivraisonQueryService livraisonQueryService;

    public LivraisonResource(LivraisonService livraisonService, LivraisonQueryService livraisonQueryService) {
        this.livraisonService = livraisonService;
        this.livraisonQueryService = livraisonQueryService;
    }

    /**
     * {@code POST  /livraisons} : Create a new livraison.
     *
     * @param livraisonDTO the livraisonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livraisonDTO, or with status {@code 400 (Bad Request)} if the livraison has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/livraisons")
    public ResponseEntity<LivraisonDTO> createLivraison(@Valid @RequestBody LivraisonDTO livraisonDTO) throws URISyntaxException {
        log.debug("REST request to save Livraison : {}", livraisonDTO);
        if (livraisonDTO.getId() != null) {
            throw new BadRequestAlertException("A new livraison cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LivraisonDTO result = livraisonService.save(livraisonDTO);
        return ResponseEntity.created(new URI("/api/livraisons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /livraisons} : Updates an existing livraison.
     *
     * @param livraisonDTO the livraisonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livraisonDTO,
     * or with status {@code 400 (Bad Request)} if the livraisonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the livraisonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/livraisons")
    public ResponseEntity<LivraisonDTO> updateLivraison(@Valid @RequestBody LivraisonDTO livraisonDTO) throws URISyntaxException {
        log.debug("REST request to update Livraison : {}", livraisonDTO);
        if (livraisonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LivraisonDTO result = livraisonService.save(livraisonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livraisonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /livraisons} : get all the livraisons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of livraisons in body.
     */
    @GetMapping("/livraisons")
    public ResponseEntity<List<LivraisonDTO>> getAllLivraisons(LivraisonCriteria criteria) {
        log.debug("REST request to get Livraisons by criteria: {}", criteria);
        List<LivraisonDTO> entityList = livraisonQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /livraisons/count} : count all the livraisons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/livraisons/count")
    public ResponseEntity<Long> countLivraisons(LivraisonCriteria criteria) {
        log.debug("REST request to count Livraisons by criteria: {}", criteria);
        return ResponseEntity.ok().body(livraisonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /livraisons/:id} : get the "id" livraison.
     *
     * @param id the id of the livraisonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the livraisonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/livraisons/{id}")
    public ResponseEntity<LivraisonDTO> getLivraison(@PathVariable Long id) {
        log.debug("REST request to get Livraison : {}", id);
        Optional<LivraisonDTO> livraisonDTO = livraisonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(livraisonDTO);
    }

    /**
     * {@code DELETE  /livraisons/:id} : delete the "id" livraison.
     *
     * @param id the id of the livraisonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/livraisons/{id}")
    public ResponseEntity<Void> deleteLivraison(@PathVariable Long id) {
        log.debug("REST request to delete Livraison : {}", id);

        livraisonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/livraisons?query=:query} : search for the livraison corresponding
     * to the query.
     *
     * @param query the query of the livraison search.
     * @return the result of the search.
     */
    @GetMapping("/_search/livraisons")
    public List<LivraisonDTO> searchLivraisons(@RequestParam String query) {
        log.debug("REST request to search Livraisons for query {}", query);
        return livraisonService.search(query);
    }

    /**************************** FININFO CODE : START ************************************ */
        // Get Livraison "Frais" & "Delais Livraison" : inputs = Valeur commande, id ville and profile client
    //     @GetMapping("/livraisons/formule/{val}/{ville}/{profile}")
    // public Optional<LivraisonDTO> findLivByCmdCriteria(@PathVariable Double val, @PathVariable Integer ville, @PathVariable ProfileClient profile) {
    //     Optional<LivraisonDTO> produits = livraisonService.findLivByCmdCriteria(val, ville, profile);
    //     return produits; 
    // }

    @GetMapping("/livraisons/formule/{val}/{ville}")
    public Optional<LivraisonDTO> findLivByCmdCriteria(@PathVariable Double val, @PathVariable Integer ville) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
        String username = userDetails.getUsername();
        Optional<LivraisonDTO> produits = livraisonService.findLivByCmdCriteria(val, ville, username);
        return produits; 
    }
    /**************************** FININFO CODE : END   ************************************ */
}
