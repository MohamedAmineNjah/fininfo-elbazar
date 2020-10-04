package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.service.AdresseService;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.fininfo.elbazar.service.dto.AdresseDTO;
import org.fininfo.elbazar.service.dto.AdresseCriteria;
import org.fininfo.elbazar.domain.Adresse;
import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.service.AdresseQueryService;

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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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
 * REST controller for managing {@link org.fininfo.elbazar.domain.Adresse}.
 */
@RestController
@RequestMapping("/api")
public class AdresseResource {

    private final Logger log = LoggerFactory.getLogger(AdresseResource.class);

    private static final String ENTITY_NAME = "adresse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdresseService adresseService;

    private final AdresseQueryService adresseQueryService;

    public AdresseResource(AdresseService adresseService, AdresseQueryService adresseQueryService) {
        this.adresseService = adresseService;
        this.adresseQueryService = adresseQueryService;
    }

    /**
     * {@code POST  /adresses} : Create a new adresse.
     *
     * @param adresseDTO the adresseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new adresseDTO, or with status {@code 400 (Bad Request)} if
     *         the adresse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adresses")
    public ResponseEntity<AdresseDTO> createAdresse(@Valid @RequestBody AdresseDTO adresseDTO)
            throws URISyntaxException {
        log.debug("REST request to save Adresse : {}", adresseDTO);
        if (adresseDTO.getId() != null) {
            throw new BadRequestAlertException("A new adresse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdresseDTO result = adresseService.save(adresseDTO);
        return ResponseEntity
                .created(new URI("/api/adresses/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /adresses} : Updates an existing adresse.
     *
     * @param adresseDTO the adresseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated adresseDTO, or with status {@code 400 (Bad Request)} if
     *         the adresseDTO is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the adresseDTO couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adresses")
    public ResponseEntity<AdresseDTO> updateAdresse(@Valid @RequestBody AdresseDTO adresseDTO)
            throws URISyntaxException {
        log.debug("REST request to update Adresse : {}", adresseDTO);
        if (adresseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdresseDTO result = adresseService.save(adresseDTO);
        return ResponseEntity.ok().headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adresseDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /adresses} : get all the adresses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of adresses in body.
     */
    @GetMapping("/adresses")
    public ResponseEntity<List<AdresseDTO>> getAllAdresses(AdresseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Adresses by criteria: {}", criteria);
        Page<AdresseDTO> page = adresseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /adresses/count} : count all the adresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/adresses/count")
    public ResponseEntity<Long> countAdresses(AdresseCriteria criteria) {
        log.debug("REST request to count Adresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(adresseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /adresses/:id} : get the "id" adresse.
     *
     * @param id the id of the adresseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the adresseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adresses/{id}")
    public ResponseEntity<AdresseDTO> getAdresse(@PathVariable Long id) {
        log.debug("REST request to get Adresse : {}", id);
        Optional<AdresseDTO> adresseDTO = adresseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adresseDTO);
    }

    /**
     * {@code DELETE  /adresses/:id} : delete the "id" adresse.
     *
     * @param id the id of the adresseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adresses/{id}")
    public ResponseEntity<Void> deleteAdresse(@PathVariable Long id) {
        log.debug("REST request to delete Adresse : {}", id);

        adresseService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

    /**
     * {@code SEARCH  /_search/adresses?query=:query} : search for the adresse
     * corresponding to the query.
     *
     * @param query    the query of the adresse search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/adresses")
    public ResponseEntity<List<AdresseDTO>> searchAdresses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Adresses for query {}", query);
        Page<AdresseDTO> page = adresseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /********************* FININFO CODE: START ********************* */
    // Get all "Adresses" by "Client " id
    @GetMapping("/adresses/client/{client}")
    public List<AdresseDTO> getAdresse(@PathVariable Client client) {
        log.debug("REST request to get Adresse : {}", client);
        List<AdresseDTO> adresseDTO = adresseService.getAdresseByClient(client);
        return adresseDTO;
    }

    // // This returns a list of a Client Commands
    // @PostMapping("/adresses/client/")
    // @Secured("ROLE_USER")
    // public @ResponseBody List<AdresseDTO> getAdresses(Client client) {
    // List<AdresseDTO> adresseDTO = adresseService.getAdresseByClient(client);
    // return adresseDTO;
    // }

    // Get all "Adresses" by "Client " id
    @GetMapping("/adresses/client")
    public List<AdresseDTO> getAdressee() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        List<AdresseDTO> adresseDTO = adresseService.getAdresseByEmail(username);
        return adresseDTO;
    }
    /********************* FININFO CODE: END ********************* */

}
