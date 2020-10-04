package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.service.CommandeLignesService;
import org.fininfo.elbazar.domain.CommandeLignes;
import org.fininfo.elbazar.repository.CommandeLignesRepository;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.fininfo.elbazar.service.dto.CommandeLignesDTO;
import org.fininfo.elbazar.service.mapper.CommandeLignesMapper;
import org.fininfo.elbazar.service.dto.CommandeLignesCriteria;
import org.fininfo.elbazar.service.CommandeLignesQueryService;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link org.fininfo.elbazar.domain.CommandeLignes}.
 */
@RestController
@RequestMapping("/api")
public class CommandeLignesResource {

    private final CommandeLignesRepository commandeLignesRepository;

    private final Logger log = LoggerFactory.getLogger(CommandeLignesResource.class);

    private static final String ENTITY_NAME = "commandeLignes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandeLignesService commandeLignesService;

    private final CommandeLignesQueryService commandeLignesQueryService;

    public CommandeLignesResource(CommandeLignesService commandeLignesService, CommandeLignesQueryService commandeLignesQueryService, CommandeLignesRepository commandeLignesRepository) {
        this.commandeLignesService = commandeLignesService;
        this.commandeLignesQueryService = commandeLignesQueryService;
        this.commandeLignesRepository = commandeLignesRepository;
    }

    /**
     * {@code POST  /commande-lignes} : Create a new commandeLignes.
     *
     * @param commandeLignesDTO the commandeLignesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandeLignesDTO, or with status {@code 400 (Bad Request)} if the commandeLignes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commande-lignes")
    public ResponseEntity<CommandeLignesDTO> createCommandeLignes(@RequestBody CommandeLignesDTO commandeLignesDTO) throws URISyntaxException {
        log.debug("REST request to save CommandeLignes : {}", commandeLignesDTO);
        if (commandeLignesDTO.getId() != null) {
            throw new BadRequestAlertException("A new commandeLignes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandeLignesDTO result = commandeLignesService.save(commandeLignesDTO);
        return ResponseEntity.created(new URI("/api/commande-lignes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commande-lignes} : Updates an existing commandeLignes.
     *
     * @param commandeLignesDTO the commandeLignesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeLignesDTO,
     * or with status {@code 400 (Bad Request)} if the commandeLignesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandeLignesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commande-lignes")
    public ResponseEntity<CommandeLignesDTO> updateCommandeLignes(@RequestBody CommandeLignesDTO commandeLignesDTO) throws URISyntaxException {
        log.debug("REST request to update CommandeLignes : {}", commandeLignesDTO);
        if (commandeLignesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommandeLignesDTO result = commandeLignesService.save(commandeLignesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandeLignesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commande-lignes} : get all the commandeLignes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandeLignes in body.
     */
    @GetMapping("/commande-lignes")
    public ResponseEntity<List<CommandeLignesDTO>> getAllCommandeLignes(CommandeLignesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommandeLignes by criteria: {}", criteria);
        Page<CommandeLignesDTO> page = commandeLignesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commande-lignes/count} : count all the commandeLignes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/commande-lignes/count")
    public ResponseEntity<Long> countCommandeLignes(CommandeLignesCriteria criteria) {
        log.debug("REST request to count CommandeLignes by criteria: {}", criteria);
        return ResponseEntity.ok().body(commandeLignesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /commande-lignes/:id} : get the "id" commandeLignes.
     *
     * @param id the id of the commandeLignesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandeLignesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commande-lignes/{id}")
    public ResponseEntity<CommandeLignesDTO> getCommandeLignes(@PathVariable Long id) {
        log.debug("REST request to get CommandeLignes : {}", id);
        Optional<CommandeLignesDTO> commandeLignesDTO = commandeLignesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commandeLignesDTO);
    }

    /**
     * {@code DELETE  /commande-lignes/:id} : delete the "id" commandeLignes.
     *
     * @param id the id of the commandeLignesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commande-lignes/{id}")
    public ResponseEntity<Void> deleteCommandeLignes(@PathVariable Long id) {
        log.debug("REST request to delete CommandeLignes : {}", id);

        commandeLignesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/commande-lignes?query=:query} : search for the commandeLignes corresponding
     * to the query.
     *
     * @param query the query of the commandeLignes search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/commande-lignes")
    public ResponseEntity<List<CommandeLignesDTO>> searchCommandeLignes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommandeLignes for query {}", query);
        Page<CommandeLignesDTO> page = commandeLignesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
    }
    /************************** FININFO START ************************* */

 
