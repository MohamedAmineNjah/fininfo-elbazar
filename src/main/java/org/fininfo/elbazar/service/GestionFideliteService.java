package org.fininfo.elbazar.service;

import org.fininfo.elbazar.domain.GestionFidelite;
import org.fininfo.elbazar.repository.GestionFideliteRepository;
import org.fininfo.elbazar.repository.search.GestionFideliteSearchRepository;
import org.fininfo.elbazar.service.dto.GestionFideliteDTO;
import org.fininfo.elbazar.service.mapper.GestionFideliteMapper;
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
 * Service Implementation for managing {@link GestionFidelite}.
 */
@Service
@Transactional
public class GestionFideliteService {

    private final Logger log = LoggerFactory.getLogger(GestionFideliteService.class);

    private final GestionFideliteRepository gestionFideliteRepository;

    private final GestionFideliteMapper gestionFideliteMapper;

    private final GestionFideliteSearchRepository gestionFideliteSearchRepository;

    public GestionFideliteService(GestionFideliteRepository gestionFideliteRepository,
            GestionFideliteMapper gestionFideliteMapper,
            GestionFideliteSearchRepository gestionFideliteSearchRepository) {
        this.gestionFideliteRepository = gestionFideliteRepository;
        this.gestionFideliteMapper = gestionFideliteMapper;
        this.gestionFideliteSearchRepository = gestionFideliteSearchRepository;
    }

    /**
     * Save a gestionFidelite.
     *
     * @param gestionFideliteDTO the entity to save.
     * @return the persisted entity.
     */
    public GestionFideliteDTO save(GestionFideliteDTO gestionFideliteDTO) {
        log.debug("Request to save GestionFidelite : {}", gestionFideliteDTO);
        GestionFidelite gestionFidelite = gestionFideliteMapper.toEntity(gestionFideliteDTO);
        gestionFidelite = gestionFideliteRepository.save(gestionFidelite);
        GestionFideliteDTO result = gestionFideliteMapper.toDto(gestionFidelite);
        gestionFideliteSearchRepository.save(gestionFidelite);
        return result;
    }

    /**
     * Get all the gestionFidelites.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GestionFideliteDTO> findAll() {
        log.debug("Request to get all GestionFidelites");
        return gestionFideliteRepository.findAll().stream().map(gestionFideliteMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /*********************************************************************************************************************
     * *******************************************************************************************************************/
    @Transactional(readOnly = true)
    public Optional<GestionFideliteDTO> findActive() {
        log.debug("Request to get True GestionFidelite : {}");
        return gestionFideliteRepository.findByetatTrue().map(gestionFideliteMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<GestionFideliteDTO> findInactive() {
        log.debug("Request to get False GestionFidelite : {}");
        return gestionFideliteRepository.findByetatFalse().stream().map(gestionFideliteMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /*********************************************************************************************************************
     * *******************************************************************************************************************/

    /**
     * Get one gestionFidelite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GestionFideliteDTO> findOne(Long id) {
        log.debug("Request to get GestionFidelite : {}", id);
        return gestionFideliteRepository.findById(id).map(gestionFideliteMapper::toDto);
    }

    /**
     * Delete the gestionFidelite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GestionFidelite : {}", id);

        gestionFideliteRepository.deleteById(id);
        gestionFideliteSearchRepository.deleteById(id);
    }

    /**
     * Search for the gestionFidelite corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GestionFideliteDTO> search(String query) {
        log.debug("Request to search GestionFidelites for query {}", query);
        return StreamSupport
                .stream(gestionFideliteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .map(gestionFideliteMapper::toDto).collect(Collectors.toList());
    }
}
