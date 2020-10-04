package org.fininfo.elbazar.service;

import org.fininfo.elbazar.domain.ProduitUnite;
import org.fininfo.elbazar.repository.ProduitUniteRepository;
import org.fininfo.elbazar.repository.search.ProduitUniteSearchRepository;
import org.fininfo.elbazar.service.dto.ProduitUniteDTO;
import org.fininfo.elbazar.service.mapper.ProduitUniteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ProduitUnite}.
 */
@Service
@Transactional
public class ProduitUniteService {

    private final Logger log = LoggerFactory.getLogger(ProduitUniteService.class);

    private final ProduitUniteRepository produitUniteRepository;

    private final ProduitUniteMapper produitUniteMapper;

    private final ProduitUniteSearchRepository produitUniteSearchRepository;

    public ProduitUniteService(ProduitUniteRepository produitUniteRepository, ProduitUniteMapper produitUniteMapper,
            ProduitUniteSearchRepository produitUniteSearchRepository) {
        this.produitUniteRepository = produitUniteRepository;
        this.produitUniteMapper = produitUniteMapper;
        this.produitUniteSearchRepository = produitUniteSearchRepository;
    }

    /**
     * Save a produitUnite.
     *
     * @param produitUniteDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitUniteDTO save(ProduitUniteDTO produitUniteDTO) {
        log.debug("Request to save ProduitUnite : {}", produitUniteDTO);
        ProduitUnite produitUnite = produitUniteMapper.toEntity(produitUniteDTO);
        if (produitUniteDTO.getId() == null)
            produitUniteRepository.findOneByCode(produitUniteDTO.getCode()).ifPresent(existingProduit -> {
                throw new ProduitUniteAlreadyUsedException();
            });
        produitUnite = produitUniteRepository.save(produitUnite);
        ProduitUniteDTO result = produitUniteMapper.toDto(produitUnite);
        produitUniteSearchRepository.save(produitUnite);
        return result;
    }

    /**
     * Get all the produitUnites.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProduitUniteDTO> findAll() {
        log.debug("Request to get all ProduitUnites");
        return produitUniteRepository.findAll().stream().map(produitUniteMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one produitUnite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProduitUniteDTO> findOne(Long id) {
        log.debug("Request to get ProduitUnite : {}", id);
        return produitUniteRepository.findById(id).map(produitUniteMapper::toDto);
    }

    /**
     * Delete the produitUnite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProduitUnite : {}", id);

        produitUniteRepository.deleteById(id);
        produitUniteSearchRepository.deleteById(id);
    }

    /**
     * Search for the produitUnite corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProduitUniteDTO> search(String query) {
        log.debug("Request to search ProduitUnites for query {}", query);
        return StreamSupport.stream(produitUniteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .map(produitUniteMapper::toDto).collect(Collectors.toList());
    }
}
