package org.fininfo.elbazar.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.domain.CommandeLignes;
import org.fininfo.elbazar.repository.CommandeRepository;
import org.fininfo.elbazar.service.CommandeLignesService;
import org.fininfo.elbazar.service.CommandeQueryService;
import org.fininfo.elbazar.service.CommandeService;
import org.fininfo.elbazar.service.dto.CommandeCriteria;
import org.fininfo.elbazar.service.dto.CommandeDTO;
import org.fininfo.elbazar.service.dto.common.CommandeClientDTO;
import org.fininfo.elbazar.service.dto.CommandeLignesDTO;
import org.fininfo.elbazar.service.dto.common.CartDetailsDTO;
import org.fininfo.elbazar.service.dto.common.CommandeDetailsDTO;
import org.fininfo.elbazar.service.dto.common.CommandeHeaderDTO;
import org.fininfo.elbazar.service.mapper.CommandeMapper;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.fininfo.elbazar.domain.Commande}.
 */
@RestController
@RequestMapping("/api")
public class CommandeResource {

    private final Logger log = LoggerFactory.getLogger(CommandeResource.class);

    private static final String ENTITY_NAME = "commande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandeService commandeService;



    private final CommandeQueryService commandeQueryService;
    

    public CommandeResource(CommandeService commandeService, CommandeQueryService commandeQueryService, CommandeLignesService commandeLignesService) {
        this.commandeService = commandeService;
    
        this.commandeQueryService = commandeQueryService;
    }

    /**
     * {@code POST  /commandes} : Create a new commande.
     *
     * @param commandeDTO the commandeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandeDTO, or with status {@code 400 (Bad Request)} if the commande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commandes")
    public ResponseEntity<CommandeDTO> createCommande(@Valid @RequestBody CommandeDTO commandeDTO) throws URISyntaxException {
        log.debug("REST request to save Commande : {}", commandeDTO);
        if (commandeDTO.getId() != null) {
            throw new BadRequestAlertException("A new commande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandeDTO result = commandeService.save(commandeDTO);
        return ResponseEntity.created(new URI("/api/commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commandes} : Updates an existing commande.
     *
     * @param commandeDTO the commandeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeDTO,
     * or with status {@code 400 (Bad Request)} if the commandeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commandes")
    public ResponseEntity<CommandeDTO> updateCommande(@Valid @RequestBody CommandeDTO commandeDTO) throws URISyntaxException {
        log.debug("REST request to update Commande : {}", commandeDTO);
        if (commandeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommandeDTO result = commandeService.save(commandeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commandes} : get all the commandes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandes in body.
     */
    @GetMapping("/commandes")
    public ResponseEntity<List<CommandeDTO>> getAllCommandes(CommandeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Commandes by criteria: {}", criteria);
        Page<CommandeDTO> page = commandeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    

    /**
     * {@code GET  /commandes/count} : count all the commandes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/commandes/count")
    public ResponseEntity<Long> countCommandes(CommandeCriteria criteria) {
        log.debug("REST request to count Commandes by criteria: {}", criteria);
        return ResponseEntity.ok().body(commandeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /commandes/:id} : get the "id" commande.
     *
     * @param id the id of the commandeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commandes/{id}")
    public ResponseEntity<CommandeDTO> getCommande(@PathVariable Long id) {
        log.debug("REST request to get Commande : {}", id);
        Optional<CommandeDTO> commandeDTO = commandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commandeDTO);
    }

    /**
     * {@code DELETE  /commandes/:id} : delete the "id" commande.
     *
     * @param id the id of the commandeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commandes/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        log.debug("REST request to delete Commande : {}", id);

        commandeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    
    @GetMapping("/commandes/client/{id}")
    public ResponseEntity<CommandeDetailsDTO> getDetailCommandeByID(@PathVariable Long id) {
        log.debug("REST request to get Client from Commande Id : {}", id);
        Optional<CommandeDetailsDTO> clientDto = commandeService.findClientbyCommande(id);
       return ResponseUtil.wrapOrNotFound(clientDto);
    }
    
    /**
     * {@code SEARCH  /_search/commandes?query=:query} : search for the commande corresponding
     * to the query.
     *
     * @param query the query of the commande search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/commandes")
    public ResponseEntity<List<CommandeDTO>> searchCommandes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Commandes for query {}", query);
        Page<CommandeDTO> page = commandeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

    /********************* FININFO CODE: START ********************* */
    // @GetMapping("/commanndes/client_cmd/{clt_id}")
    // public List<CommandeClientDTO> getCommandeByClient(@PathVariable Long clt_id) {
    //     // This returns a JSON of "Categories" and "Sous Categories"
    //     List<CommandeClientDTO> commande = commandeService.findCommandeByClient(clt_id);
    //     return commande;
    // }

    //     // This returns a list of a Client Commands
    // @PostMapping("/commandes/client_cmd/{clt_id}")
    // @Secured("ROLE_USER")
    // public @ResponseBody List<CommandeClientDTO> getCommandeByClientz(@PathVariable Long clt_id) {
    //     List<CommandeClientDTO> commande = commandeService.findCommandeByClient(clt_id);
    //     return commande;
    // }

    // Get all "Commandes" by User Login
    @GetMapping("/commandes/client")
    public List<CommandeClientDTO> findCommandeByLogin() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername();
        List<CommandeClientDTO> commande = commandeService.findCommandeByLogin(username);
        return commande;
    }

        // Get cart details, and save it into client commands
    @PostMapping("/getcmds")
    public ResponseEntity<CommandeDTO> getcmds(@Valid @RequestBody CartDetailsDTO cartDetailsDTO) throws URISyntaxException  {
         
        log.debug("REST request to save Commande : {}", cartDetailsDTO);
      
        CommandeDTO result = commandeService.creatCart(cartDetailsDTO);

        return ResponseEntity.created(new URI("/api/getcmds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    /**************************** FININFO CODE : END   ************************************ */
}
