package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.service.ClientService;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.fininfo.elbazar.service.dto.ClientDTO;
import org.fininfo.elbazar.service.dto.ClientCriteria;
import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.repository.ClientRepository;
import org.fininfo.elbazar.service.ClientQueryService;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link org.fininfo.elbazar.domain.Client}.
 */
@RestController
@RequestMapping("/api")
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private static final String ENTITY_NAME = "client";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientService clientService;

    private final ClientQueryService clientQueryService;

    private final ClientRepository clientRepository;

    public ClientResource(ClientService clientService, ClientQueryService clientQueryService, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientQueryService = clientQueryService;
        this.clientRepository = clientRepository;
    }

    /**
     * {@code POST  /clients} : Create a new client.
     *
     * @param clientDTO the clientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new clientDTO, or with status {@code 400 (Bad Request)} if
     *         the client has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clients")
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) throws URISyntaxException {
        log.debug("REST request to save Client : {}", clientDTO);
        if (clientDTO.getId() != null) {
            throw new BadRequestAlertException("A new client cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(clientDTO.getUserId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        ClientDTO result = clientService.save(clientDTO);
        return ResponseEntity
                .created(new URI("/api/clients/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /clients} : Updates an existing client.
     *
     * @param clientDTO the clientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated clientDTO, or with status {@code 400 (Bad Request)} if
     *         the clientDTO is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the clientDTO couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clients")
    public ResponseEntity<ClientDTO> updateClient(@Valid @RequestBody ClientDTO clientDTO) throws URISyntaxException {
        log.debug("REST request to update Client : {}", clientDTO);
        if (clientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientDTO result = clientService.save(clientDTO);
        return ResponseEntity.ok().headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /clients} : get all the clients.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of clients in body.
     */
    @GetMapping("/clients")
    public ResponseEntity<List<ClientDTO>> getAllClients(ClientCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Clients by criteria: {}", criteria);
        Page<ClientDTO> page = clientQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clients/count} : count all the clients.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/clients/count")
    public ResponseEntity<Long> countClients(ClientCriteria criteria) {
        log.debug("REST request to count Clients by criteria: {}", criteria);
        return ResponseEntity.ok().body(clientQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /clients/:id} : get the "id" client.
     *
     * @param id the id of the clientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the clientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        log.debug("REST request to get Client : {}", id);
        Optional<ClientDTO> clientDTO = clientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientDTO);
    }

    /**
     * {@code DELETE  /clients/:id} : delete the "id" client.
     *
     * @param id the id of the clientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.debug("REST request to delete Client : {}", id);

        clientService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

    /**
     * {@code SEARCH  /_search/clients?query=:query} : search for the client
     * corresponding to the query.
     *
     * @param query    the query of the client search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/clients")
    public ResponseEntity<List<ClientDTO>> searchClients(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Clients for query {}", query);
        Page<ClientDTO> page = clientService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**************************** FININFO CODE : START ************************************ */
        // Get Client infos for Profil Client
    @GetMapping("/client")
    public ResponseEntity<ClientDTO> getClientInfo() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
        String username = userDetails.getUsername();
        Optional<Client> clientOpt = clientRepository.findClientByLogin(username);
        Client Client = (clientOpt.isPresent() ? clientOpt.get() : null);
        Optional<ClientDTO> clientDTO = clientService.findOne(Client.getId());
        return ResponseUtil.wrapOrNotFound(clientDTO);
    }

        // Get Points Fidélité of a Client
    @GetMapping("/client/points-fid")
    @Secured("ROLE_USER")
    public Optional<Client> getPointFidelite() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
        String username = userDetails.getUsername();
        Optional<Client> pointsFidOpt = clientRepository.findPointFidByLogin(username);
        return pointsFidOpt;
    }
    /**************************** FININFO CODE : END   ************************************ */

}
