package org.fininfo.elbazar.service;

import org.fininfo.elbazar.domain.Adresse;
import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.repository.AdresseRepository;
import org.fininfo.elbazar.repository.search.AdresseSearchRepository;
import org.fininfo.elbazar.service.dto.AdresseDTO;
import org.fininfo.elbazar.service.mapper.AdresseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Adresse}.
 */
@Service
@Transactional
public class AdresseService {

    private final Logger log = LoggerFactory.getLogger(AdresseService.class);

    private final AdresseRepository adresseRepository;

    private final AdresseMapper adresseMapper;

    private final AdresseSearchRepository adresseSearchRepository;

    public AdresseService(AdresseRepository adresseRepository, AdresseMapper adresseMapper,
            AdresseSearchRepository adresseSearchRepository) {
        this.adresseRepository = adresseRepository;
        this.adresseMapper = adresseMapper;
        this.adresseSearchRepository = adresseSearchRepository;
    }

    /**
     * Save a adresse.
     *
     * @param adresseDTO the entity to save.
     * @return the persisted entity.
     */
    public AdresseDTO save(AdresseDTO adresseDTO) {
        log.debug("Request to save Adresse : {}", adresseDTO);
        Adresse adresse = adresseMapper.toEntity(adresseDTO);
        adressePrincipal(adresseDTO);
        adresse = adresseRepository.save(adresse);
        AdresseDTO result = adresseMapper.toDto(adresse);
        adresseSearchRepository.save(adresse);
        return result;
    }
    
    /**
     * Get all the adresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdresseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Adresses");
        return adresseRepository.findAll(pageable).map(adresseMapper::toDto);
    }

    /**
     * Get one adresse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdresseDTO> findOne(Long id) {
        log.debug("Request to get Adresse : {}", id);
        return adresseRepository.findById(id).map(adresseMapper::toDto);
    }

    /**
     * Delete the adresse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Adresse : {}", id);

        adresseRepository.deleteById(id);
        adresseSearchRepository.deleteById(id);
    }

    /**
     * Search for the adresse corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdresseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Adresses for query {}", query);
        return adresseSearchRepository.search(queryStringQuery(query), pageable).map(adresseMapper::toDto);
    }

    /********************* FININFO CODE: START ********************* */
    // Update "Adresse" principal
    private void adressePrincipal(AdresseDTO adresseDTO) {
        Adresse adresse = adresseMapper.toEntity(adresseDTO);
        if (adresse.isPrincipale().equals(true)) {
        Optional<Adresse> oldOptional = adresseRepository.findByPrincipal(adresse.getClient());
        Adresse oldPrincipale = (oldOptional.isPresent() ? oldOptional.get() : null);
        oldPrincipale.setPrincipale(false);
        }
    }

    // Find "Adresses" by Client
    @Transactional(readOnly = true)
    public List<AdresseDTO> getAdresseByClient(Client client) {
        log.debug("Request to search for a page of Adresses for query {}", client);
        return adresseRepository.findByClient(client).stream().map(adresseMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    // Find "Adresses" by User login
    @Transactional(readOnly = true)
    public List<AdresseDTO> getAdresseByEmail(String login) {
        log.debug("Request to search for a page of Adresses for query {}", login);
        return adresseRepository.findByEmail(login).stream().map(adresseMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
    /********************* FININFO CODE: END ********************* */
}
