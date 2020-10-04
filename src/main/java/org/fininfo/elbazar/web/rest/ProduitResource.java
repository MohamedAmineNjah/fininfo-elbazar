package org.fininfo.elbazar.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.fininfo.elbazar.service.ProduitQueryService;
import org.fininfo.elbazar.service.ProduitService;
import org.fininfo.elbazar.service.dto.ProduitCriteria;
import org.fininfo.elbazar.service.dto.ProduitDTO;
import org.fininfo.elbazar.service.dto.common.ProduitBySousCatDTO;
import org.fininfo.elbazar.service.dto.common.SearchDTO;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.fininfo.elbazar.domain.Produit}.
 */
@RestController
@RequestMapping("/api")
public class ProduitResource {

    private final Logger log = LoggerFactory.getLogger(ProduitResource.class);

    private static final String ENTITY_NAME = "produit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduitService produitService;

    private final ProduitQueryService produitQueryService;

    public ProduitResource(ProduitService produitService, ProduitQueryService produitQueryService) {
        this.produitService = produitService;
        this.produitQueryService = produitQueryService;
    }

    /**
     * {@code POST  /produits} : Create a new produit.
     *
     * @param produitDTO the produitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new produitDTO, or with status {@code 400 (Bad Request)} if
     *         the produit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/produits")
    public ResponseEntity<ProduitDTO> createProduit(@Valid @RequestBody ProduitDTO produitDTO)
            throws URISyntaxException {
        log.debug("REST request to save Produit : {}", produitDTO);
        if (produitDTO.getId() != null) {
            throw new BadRequestAlertException("A new produit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProduitDTO result = produitService.save(produitDTO);
        return ResponseEntity
                .created(new URI("/api/produits/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /produits} : Updates an existing produit.
     *
     * @param produitDTO the produitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated produitDTO, or with status {@code 400 (Bad Request)} if
     *         the produitDTO is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the produitDTO couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/produits")
    public ResponseEntity<ProduitDTO> updateProduit(@Valid @RequestBody ProduitDTO produitDTO)
            throws URISyntaxException {
        log.debug("REST request to update Produit : {}", produitDTO);
        if (produitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProduitDTO result = produitService.save(produitDTO);
        return ResponseEntity.ok().headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /produits} : get all the produits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of produits in body.
     */
    @GetMapping("/produits")
    public ResponseEntity<List<ProduitDTO>> getAllProduits(ProduitCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Produits by criteria: {}", criteria);
        Page<ProduitDTO> page = produitQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /produits} : get all the produits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of produits in body.
     */
    @GetMapping("/allproduits")
    public ResponseEntity<List<ProduitDTO>> getAllNoPageProduits(ProduitCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Produits by criteria: {}", criteria);
        Page<ProduitDTO> page = produitQueryService.findAllByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /produits/count} : count all the produits.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/produits/count")
    public ResponseEntity<Long> countProduits(ProduitCriteria criteria) {
        log.debug("REST request to count Produits by criteria: {}", criteria);
        return ResponseEntity.ok().body(produitQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /produits/:id} : get the "id" produit.
     *
     * @param id the id of the produitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the produitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produits/{id}")
    public ResponseEntity<ProduitDTO> getProduit(@PathVariable Long id) {
        log.debug("REST request to get Produit : {}", id);
        Optional<ProduitDTO> produitDTO = produitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produitDTO);
    }

    /**
     * {@code DELETE  /produits/:id} : delete the "id" produit.
     *
     * @param id the id of the produitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/produits/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        log.debug("REST request to delete Produit : {}", id);

        produitService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

    /**
     * {@code SEARCH  /_search/produits?query=:query} : search for the produit
     * corresponding to the query.
     *
     * @param query    the query of the produit search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/produits")
    public ResponseEntity<List<ProduitDTO>> searchProduits(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Produits for query {}", query);
        Page<ProduitDTO> page = produitService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /****************************
     * FININFO CODE : START ************************************
     */
    // Get All "Produits" by "Sous Categorie"
    @GetMapping("/produits/souscategorie/{id}")
    public Page<ProduitBySousCatDTO> getAllprod(@PathVariable Long id, Pageable pageable) {
        Page<ProduitBySousCatDTO> produits = produitService.findProdBySousCat(id, pageable);
        return produits;
    }

    // Search All "Produits" by "Sous Categorie"
    @GetMapping("/produits/souscategorie/{id}/{scat}")
    public Page<ProduitBySousCatDTO> SearchAllprod(@PathVariable Long id, @PathVariable String scat,
            Pageable pageable) {
        Page<ProduitBySousCatDTO> produits = produitService.findProdBySousCatSearch(id, scat, pageable);
        return produits;
    }

    // Get All "Produits" in Promo
    @GetMapping("/produits/promo")
    public Page<ProduitBySousCatDTO> getProdPromo(Pageable pageable) {
        Page<ProduitBySousCatDTO> produits = produitService.findProdPromo(pageable);
        return produits;
    }

    // Get TOP 10 New "Produits"
    @GetMapping("/produits/newprod")
    public Page<ProduitBySousCatDTO> find10NewProd() {
        Page<ProduitBySousCatDTO> produits = produitService.find10NewProd();
        return produits;
    }

    // Get "Produits" en vedette
    @GetMapping("/produits/vedette")
    public Page<ProduitBySousCatDTO> findProdByVedette(Pageable pageable) {
        Page<ProduitBySousCatDTO> produits = produitService.findProdByVedette(pageable);
        return produits;
    }

    // Get "Produits" renamed
    @GetMapping("/produit_/{id}")
    public ResponseEntity<ProduitBySousCatDTO> getProduit_(@PathVariable Long id) {
        log.debug("REST request to get Produit : {}", id);
        Optional<ProduitBySousCatDTO> produitDTO = produitService.findOne_(id);
        return ResponseUtil.wrapOrNotFound(produitDTO);
    }

    // amine
    // Search All "Produits" by Name"
    @GetMapping("/search/produitnom/{nom}")
    public Page<ProduitBySousCatDTO> SearchProductbyName(@PathVariable String nom, Pageable pageable) {
        Page<ProduitBySousCatDTO> produits = produitService.findProdByNameSearch(nom, pageable);

        return produits;
    }

    // Search All "Produits" by Name"
    @PostMapping("/search/produitnom")
    public Page<ProduitBySousCatDTO> GetProductbyName(@Valid @RequestBody SearchDTO searchDTO, Pageable pageable) {
        System.out.println("THIIIIIIIIIIS" + searchDTO.getName());
        Page<ProduitBySousCatDTO> produits = produitService.findProdByNameSearch(searchDTO.getName(), pageable);
        return produits;
    }
    /****************************
     * FININFO CODE : END ************************************
     */
}