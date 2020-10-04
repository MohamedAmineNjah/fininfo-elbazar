package org.fininfo.elbazar.service;

import org.fininfo.elbazar.domain.Categorie;
import org.fininfo.elbazar.domain.SousCategorie;
import org.fininfo.elbazar.repository.CategorieRepository;
import org.fininfo.elbazar.repository.SousCategorieRepository;
import org.fininfo.elbazar.repository.search.CategorieSearchRepository;
import org.fininfo.elbazar.service.dto.CategorieDTO;
import org.fininfo.elbazar.service.dto.SousCategorieDTO;
import org.fininfo.elbazar.service.dto.common.CatalogueDTO;
import org.fininfo.elbazar.service.mapper.CategorieMapper;
import org.fininfo.elbazar.service.mapper.SousCategorieMapper;
import org.fininfo.elbazar.service.SousCategorieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Categorie}.
 */
@Service
@Transactional
public class CategorieService {

    private final Logger log = LoggerFactory.getLogger(CategorieService.class);

    private final CategorieRepository categorieRepository;

    private final SousCategorieRepository sousCategorieRepository;

    private final CategorieMapper categorieMapper;

    private final SousCategorieService sousCategorieService;

    private final SousCategorieMapper sousCategorieMapper;

    private final CategorieSearchRepository categorieSearchRepository;

    public CategorieService(CategorieRepository categorieRepository, CategorieMapper categorieMapper, CategorieSearchRepository categorieSearchRepository,
    SousCategorieRepository sousCategorieRepository, SousCategorieMapper sousCategorieMapper, SousCategorieService sousCategorieService) {
        this.categorieRepository = categorieRepository;
        this.sousCategorieRepository = sousCategorieRepository;
        this.categorieMapper = categorieMapper;
        this.sousCategorieMapper = sousCategorieMapper;
        this.sousCategorieService = sousCategorieService;
        this.categorieSearchRepository = categorieSearchRepository;
    }

    /**
     * Save a categorie.
     *
     * @param categorieDTO the entity to save.
     * @return the persisted entity.
     */
    public CategorieDTO save(CategorieDTO categorieDTO) {
        log.debug("Request to save Categorie : {}", categorieDTO);
        Categorie categorie = categorieMapper.toEntity(categorieDTO);
        // disableall(categorie);
        categorie = categorieRepository.save(categorie);
        CategorieDTO result = categorieMapper.toDto(categorie);
        categorieSearchRepository.save(categorie);
        return result;

    }


    /**
     * Get all the categories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CategorieDTO> findAll() {
        log.debug("Request to get all Categories");
        return categorieRepository.findAll().stream()
            .map(categorieMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one categorie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategorieDTO> findOne(Long id) {
        log.debug("Request to get Categorie : {}", id);
        return categorieRepository.findById(id)
            .map(categorieMapper::toDto);
    }

    /**
     * Delete the categorie by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Categorie : {}", id);

        categorieRepository.deleteById(id);
        categorieSearchRepository.deleteById(id);
    }

    /**
     * Search for the categorie corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CategorieDTO> search(String query) {
        log.debug("Request to search Categories for query {}", query);
        return StreamSupport
            .stream(categorieSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(categorieMapper::toDto)
        .collect(Collectors.toList());
    }

/********************* FININFO CODE: START ********************* */
   
     @Transactional(readOnly = true)
     public List<CatalogueDTO> findCatAndSousCat() {
     return categorieRepository.findCatAndSousCat();
     }

/********************* FININFO CODE: END   ********************* */

}
