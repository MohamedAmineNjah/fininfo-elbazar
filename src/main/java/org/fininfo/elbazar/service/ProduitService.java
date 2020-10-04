package org.fininfo.elbazar.service;

import org.fininfo.elbazar.config.ApplicationProperties;
import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.domain.Stock;
import org.fininfo.elbazar.domain.enumeration.TypeMvt;
import org.fininfo.elbazar.repository.ProduitRepository;
import org.fininfo.elbazar.repository.StockRepository;
import org.fininfo.elbazar.repository.search.ProduitSearchRepository;
import org.fininfo.elbazar.repository.search.StockSearchRepository;
import org.fininfo.elbazar.service.dto.ProduitDTO;
import org.fininfo.elbazar.service.dto.common.ProduitBySousCatDTO;
import org.fininfo.elbazar.service.mapper.ProduitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static java.net.URLDecoder.decode;

/**
 * Service Implementation for managing {@link Produit}.
 */
@Service
@Transactional
public class ProduitService {

    private final Logger log = LoggerFactory.getLogger(ProduitService.class);

    private final ApplicationProperties applicationProperties;

    private final ProduitRepository produitRepository;

    private final ProduitMapper produitMapper;

    private final ProduitSearchRepository produitSearchRepository;

    private final StockRepository stockRepository;

    private final StockSearchRepository stockSearchRepository;

    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper,
            ProduitSearchRepository produitSearchRepository, StockRepository stockRepository,
            StockSearchRepository stockSearchRepository, ApplicationProperties applicationProperties) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
        this.produitSearchRepository = produitSearchRepository;
        this.stockRepository = stockRepository;
        this.stockSearchRepository = stockSearchRepository;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Save a produit.
     *
     * @param produitDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitDTO save(ProduitDTO produitDTO) {
        log.debug("Request to save Produit : {}", produitDTO);
        Produit produit = produitMapper.toEntity(produitDTO);
        if (produitDTO.getId() == null) {
            produitRepository.findOneByreference(produitDTO.getReference()).ifPresent(existingProduit -> {

                throw new ProduitAlreadyUsedException();

            });
        }

        String folder = applicationProperties.getImageproduit().getPath();
        String ImageName = produit.getNom() + "." + produit.getImageContentType().substring(6);
        Path path = Paths.get(folder + ImageName);
        try {
            Files.write(path, produitDTO.getImage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        produit.setImageUrl(ImageName);

        produit = produitRepository.save(produit);
        addnewlinetoStock(produit);
        produit = produitRepository.save(produit);
        ProduitDTO result = produitMapper.toDto(produit);
        produitSearchRepository.save(produit);
        return result;
    }

    private void addnewlinetoStock(Produit produit) {
        Optional<Stock> optionalStock = stockRepository.findOneByrefProduit(produit);
        Stock stock = (optionalStock.isPresent() ? optionalStock.get() : null);

        if (stock != null) {
            stock.setStockMinimum(produit.getStockMinimum());
            if (stock.getStockPhysique() > produit.getStockMinimum()) {
                stock.setAlerteStock(false);
                produit.setHorsStock(false);
            }
            if (stock.getStockPhysique() <= produit.getStockMinimum()) {
                stock.setAlerteStock(true);
                produit.setHorsStock(true);
            }
            stockRepository.save(stock);
            stockSearchRepository.save(stock);
        } else {
            Stock newStock = new Stock();
            LocalDate nowdate = LocalDate.now();
            newStock.setId(null);
            newStock.setRefProduit(produit);
            newStock.setCreeLe(nowdate);
            newStock.setCreePar("BackOffice");
            newStock.setDerniereEntre(nowdate);
            newStock.setModifieLe(nowdate);
            newStock.setModifiePar("BackOffice");
            newStock.setStockDisponible(0.0);
            newStock.setStockMinimum(produit.getStockMinimum());
            newStock.setStockPhysique(0.0);
            newStock.setStockReserve(0.0);
            newStock.setStockCommande(0.0);
            newStock.setAlerteStock(true);
            newStock.setIdCategorie(produit.getCategorie());
            newStock.setIdSousCategorie(produit.getSousCategorie());
            stockRepository.save(newStock);
            stockSearchRepository.save(newStock);
            produit.setHorsStock(true);
        }
    }

    /**
     * Get all the produits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Produits");
        return produitRepository.findAll(pageable).map(produitMapper::toDto);
    }

    /**
     * Get one produit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProduitDTO> findOne(Long id) {
        log.debug("Request to get Produit : {}", id);
        return produitRepository.findById(id).map(produitMapper::toDto);
    }

    /**
     * Delete the produit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Produit : {}", id);

        produitRepository.deleteById(id);
        produitSearchRepository.deleteById(id);
    }

    /**
     * Search for the produit corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Produits for query {}", query);
        return produitSearchRepository.search(queryStringQuery(query), pageable).map(produitMapper::toDto);
    }

    /****************************
     * FININFO CODE : START ************************************
     */
    // Get All "Produits" by "Sous Categorie"
    @Transactional(readOnly = true)
    public Page<ProduitBySousCatDTO> findProdBySousCat(Long id, Pageable pageable) {
        return produitRepository.findProdBySousCat(id, pageable);
    }

    // Search All "Produits" by Name by "Sous Categorie"
    @Transactional(readOnly = true)
    public Page<ProduitBySousCatDTO> findProdBySousCatSearch(Long id, String scat, Pageable pageable) {
        return produitRepository.findProdBySousCatSearch(id, scat, pageable);
    }

    // Get All "Produits" in Promo
    @Transactional(readOnly = true)
    public Page<ProduitBySousCatDTO> findProdPromo(Pageable pageable) {
        return produitRepository.findProdPromo(pageable);
    }

    // Get TOP 10 New "Produits"
    @Transactional(readOnly = true)
    public Page<ProduitBySousCatDTO> find10NewProd() {
        return produitRepository.find10NewProd(PageRequest.of(0, 10));
    }

    // Get "Produits" en vedette
    @Transactional(readOnly = true)
    public Page<ProduitBySousCatDTO> findProdByVedette(Pageable pageable) {
        return produitRepository.findProdByVedette(pageable);
    }

    // Get "Produits" renamed
    @Transactional(readOnly = true)
    public Optional<ProduitBySousCatDTO> findOne_(Long id) {
        log.debug("Request to get Produit : {}", id);
        return produitRepository.findById_(id);
    }

    // amine
    // Search All "Produits" by Name"
    @Transactional(readOnly = true)
    public Page<ProduitBySousCatDTO> findProdByNameSearch(String nom, Pageable pageable) {
        System.out.println("nommmmm" + nom);
        return produitRepository.findProdByName(nom, pageable);
    }
    /*****************************
     * FININFO CODE : END
     *************************************/

}
