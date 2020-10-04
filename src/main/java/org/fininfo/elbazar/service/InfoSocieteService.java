package org.fininfo.elbazar.service;

import org.fininfo.elbazar.domain.InfoSociete;
import org.fininfo.elbazar.repository.InfoSocieteRepository;
import org.fininfo.elbazar.repository.search.InfoSocieteSearchRepository;
import org.fininfo.elbazar.service.dto.InfoSocieteDTO;
import org.fininfo.elbazar.service.mapper.InfoSocieteMapper;
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
 * Service Implementation for managing {@link InfoSociete}.
 */
@Service
@Transactional
public class InfoSocieteService {

    private final Logger log = LoggerFactory.getLogger(InfoSocieteService.class);

    private final InfoSocieteRepository infoSocieteRepository;

    private final InfoSocieteMapper infoSocieteMapper;

    private final InfoSocieteSearchRepository infoSocieteSearchRepository;

    public InfoSocieteService(InfoSocieteRepository infoSocieteRepository, InfoSocieteMapper infoSocieteMapper, InfoSocieteSearchRepository infoSocieteSearchRepository) {
        this.infoSocieteRepository = infoSocieteRepository;
        this.infoSocieteMapper = infoSocieteMapper;
        this.infoSocieteSearchRepository = infoSocieteSearchRepository;
    }

    /**
     * Save a infoSociete.
     *
     * @param infoSocieteDTO the entity to save.
     * @return the persisted entity.
     */
    public InfoSocieteDTO save(InfoSocieteDTO infoSocieteDTO) {
        log.debug("Request to save InfoSociete : {}", infoSocieteDTO);
        InfoSociete infoSociete = infoSocieteMapper.toEntity(infoSocieteDTO);
        infoSociete = infoSocieteRepository.save(infoSociete);
        InfoSocieteDTO result = infoSocieteMapper.toDto(infoSociete);
        infoSocieteSearchRepository.save(infoSociete);
        return result;
    }

    /**
     * Get all the infoSocietes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<InfoSocieteDTO> findAll() {
        log.debug("Request to get all InfoSocietes");
        return infoSocieteRepository.findAll().stream()
            .map(infoSocieteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one infoSociete by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InfoSocieteDTO> findOne(Long id) {
        log.debug("Request to get InfoSociete : {}", id);
        return infoSocieteRepository.findById(id)
            .map(infoSocieteMapper::toDto);
    }

    /**
     * Delete the infoSociete by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InfoSociete : {}", id);

        infoSocieteRepository.deleteById(id);
        infoSocieteSearchRepository.deleteById(id);
    }

    /**
     * Search for the infoSociete corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<InfoSocieteDTO> search(String query) {
        log.debug("Request to search InfoSocietes for query {}", query);
        return StreamSupport
            .stream(infoSocieteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(infoSocieteMapper::toDto)
        .collect(Collectors.toList());
    }
}
