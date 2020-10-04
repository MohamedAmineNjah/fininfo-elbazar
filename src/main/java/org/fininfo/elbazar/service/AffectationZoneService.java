package org.fininfo.elbazar.service;

import org.fininfo.elbazar.domain.AffectationZone;
import org.fininfo.elbazar.repository.AffectationZoneRepository;
import org.fininfo.elbazar.repository.search.AffectationZoneSearchRepository;
import org.fininfo.elbazar.service.dto.AffectationZoneDTO;
import org.fininfo.elbazar.service.mapper.AffectationZoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AffectationZone}.
 */
@Service
@Transactional
public class AffectationZoneService {

    private final Logger log = LoggerFactory.getLogger(AffectationZoneService.class);

    private final AffectationZoneRepository affectationZoneRepository;

    private final AffectationZoneMapper affectationZoneMapper;

    private final AffectationZoneSearchRepository affectationZoneSearchRepository;

    public AffectationZoneService(AffectationZoneRepository affectationZoneRepository, AffectationZoneMapper affectationZoneMapper, AffectationZoneSearchRepository affectationZoneSearchRepository) {
        this.affectationZoneRepository = affectationZoneRepository;
        this.affectationZoneMapper = affectationZoneMapper;
        this.affectationZoneSearchRepository = affectationZoneSearchRepository;
    }

    /**
     * Save a affectationZone.
     *
     * @param affectationZoneDTO the entity to save.
     * @return the persisted entity.
     */
    public AffectationZoneDTO save(AffectationZoneDTO affectationZoneDTO) {
        log.debug("Request to save AffectationZone : {}", affectationZoneDTO);
        AffectationZone affectationZone = affectationZoneMapper.toEntity(affectationZoneDTO);
        List<AffectationZone> affectationZoneList = affectationZoneRepository.findByGouvernoratAndVille(affectationZoneDTO.getGouvernorat(), affectationZoneDTO.getVille());
        for (AffectationZone affectationZoneItem : affectationZoneList) {
            affectationZoneItem.setZone(affectationZone.getZone());

        }
        affectationZone = affectationZoneRepository.save(affectationZone);
        AffectationZoneDTO result = affectationZoneMapper.toDto(affectationZone);
        affectationZoneSearchRepository.save(affectationZone);
        return result;
    }

    /**
     * Get all the affectationZones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AffectationZoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AffectationZones");
        return affectationZoneRepository.findAll(pageable)
            .map(affectationZoneMapper::toDto);
    }


    /**
     * Get one affectationZone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AffectationZoneDTO> findOne(Long id) {
        log.debug("Request to get AffectationZone : {}", id);
        return affectationZoneRepository.findById(id)
            .map(affectationZoneMapper::toDto);
    }

    /**
     * Delete the affectationZone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AffectationZone : {}", id);

        affectationZoneRepository.deleteById(id);
        affectationZoneSearchRepository.deleteById(id);
    }

    /**
     * Search for the affectationZone corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AffectationZoneDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AffectationZones for query {}", query);
        return affectationZoneSearchRepository.search(queryStringQuery(query), pageable)
            .map(affectationZoneMapper::toDto);
    }
}
