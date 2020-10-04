package org.fininfo.elbazar.service;

import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.domain.Livraison;
import org.fininfo.elbazar.domain.Zone;
import org.fininfo.elbazar.domain.enumeration.ProfileClient;
import org.fininfo.elbazar.repository.ClientRepository;
import org.fininfo.elbazar.repository.LivraisonRepository;
import org.fininfo.elbazar.repository.search.LivraisonSearchRepository;
import org.fininfo.elbazar.service.dto.ClientDTO;
import org.fininfo.elbazar.service.dto.LivraisonDTO;
import org.fininfo.elbazar.service.mapper.LivraisonMapper;
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
 * Service Implementation for managing {@link Livraison}.
 */
@Service
@Transactional
public class LivraisonService {

    private final Logger log = LoggerFactory.getLogger(LivraisonService.class);

    private final LivraisonRepository livraisonRepository;

    private final ClientRepository clientRepository;

    private final LivraisonMapper livraisonMapper;

    private final LivraisonSearchRepository livraisonSearchRepository;

    public LivraisonService(LivraisonRepository livraisonRepository, LivraisonMapper livraisonMapper, LivraisonSearchRepository livraisonSearchRepository,
    ClientRepository clientRepository) {
        this.livraisonRepository = livraisonRepository;
        this.livraisonMapper = livraisonMapper;
        this.livraisonSearchRepository = livraisonSearchRepository;
        this.clientRepository = clientRepository;

    }

    /**
     * Save a livraison.
     *
     * @param livraisonDTO the entity to save.
     * @return the persisted entity.
     */
    public LivraisonDTO save(LivraisonDTO livraisonDTO) {
        log.debug("Request to save Livraison : {}", livraisonDTO);
        Livraison livraison = livraisonMapper.toEntity(livraisonDTO);
        livraison = livraisonRepository.save(livraison);
        LivraisonDTO result = livraisonMapper.toDto(livraison);
        livraisonSearchRepository.save(livraison);
        return result;
    }

    /**
     * Get all the livraisons.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LivraisonDTO> findAll() {
        log.debug("Request to get all Livraisons");
        return livraisonRepository.findAll().stream()
            .map(livraisonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one livraison by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LivraisonDTO> findOne(Long id) {
        log.debug("Request to get Livraison : {}", id);
        return livraisonRepository.findById(id)
            .map(livraisonMapper::toDto);
    }

    /**
     * Delete the livraison by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Livraison : {}", id);

        livraisonRepository.deleteById(id);
        livraisonSearchRepository.deleteById(id);
    }

    /**
     * Search for the livraison corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LivraisonDTO> search(String query) {
        log.debug("Request to search Livraisons for query {}", query);
        return StreamSupport
            .stream(livraisonSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(livraisonMapper::toDto)
        .collect(Collectors.toList());
    }

    /**************************** FININFO CODE : START ************************************ */
        // Get Livraison "Frais" & "Delais Livraison" : inputs = Valeur commande, id ville and profile client
    // @Transactional(readOnly = true)
    // public Optional<LivraisonDTO> findLivByCmdCriteria(Double val, Integer ville, ProfileClient profile) {
    //     return livraisonRepository.findLivByCmdCriteria(val, ville, profile).map(livraisonMapper::toDto);
    // }

    @Transactional(readOnly = true)
    public Optional<LivraisonDTO> findLivByCmdCriteria(Double val, Integer ville, String idClient) {
		Optional<Client> clientOpt = clientRepository.findClientByLogin(idClient);
        Client Client = (clientOpt.isPresent() ? clientOpt.get() : null);
        return livraisonRepository.findLivByCmdCriteria(val, ville, Client.getProfile()).map(livraisonMapper::toDto);
    }
    /**************************** FININFO CODE : END  ************************************ */
}
