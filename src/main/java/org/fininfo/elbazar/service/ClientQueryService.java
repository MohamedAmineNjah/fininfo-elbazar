package org.fininfo.elbazar.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.ClientRepository;
import org.fininfo.elbazar.repository.search.ClientSearchRepository;
import org.fininfo.elbazar.service.dto.ClientCriteria;
import org.fininfo.elbazar.service.dto.ClientDTO;
import org.fininfo.elbazar.service.mapper.ClientMapper;

/**
 * Service for executing complex queries for {@link Client} entities in the database.
 * The main input is a {@link ClientCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClientDTO} or a {@link Page} of {@link ClientDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientQueryService extends QueryService<Client> {

    private final Logger log = LoggerFactory.getLogger(ClientQueryService.class);

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final ClientSearchRepository clientSearchRepository;

    public ClientQueryService(ClientRepository clientRepository, ClientMapper clientMapper, ClientSearchRepository clientSearchRepository) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.clientSearchRepository = clientSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ClientDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClientDTO> findByCriteria(ClientCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Client> specification = createSpecification(criteria);
        return clientMapper.toDto(clientRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClientDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDTO> findByCriteria(ClientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Client> specification = createSpecification(criteria);
        return clientRepository.findAll(specification, page)
            .map(clientMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClientCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Client> specification = createSpecification(criteria);
        return clientRepository.count(specification);
    }

    /**
     * Function to convert {@link ClientCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Client> createSpecification(ClientCriteria criteria) {
        Specification<Client> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Client_.id));
            }
            if (criteria.getCivilite() != null) {
                specification = specification.and(buildSpecification(criteria.getCivilite(), Client_.civilite));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), Client_.prenom));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Client_.nom));
            }
            if (criteria.getDateDeNaissance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDeNaissance(), Client_.dateDeNaissance));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Client_.email));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMobile(), Client_.mobile));
            }
            if (criteria.getReglement() != null) {
                specification = specification.and(buildSpecification(criteria.getReglement(), Client_.reglement));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildSpecification(criteria.getEtat(), Client_.etat));
            }
            if (criteria.getInscription() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInscription(), Client_.inscription));
            }
            if (criteria.getDerniereVisite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDerniereVisite(), Client_.derniereVisite));
            }
            if (criteria.getTotalAchat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAchat(), Client_.totalAchat));
            }
            if (criteria.getProfile() != null) {
                specification = specification.and(buildSpecification(criteria.getProfile(), Client_.profile));
            }
            if (criteria.getPointsFidelite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPointsFidelite(), Client_.pointsFidelite));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), Client_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), Client_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), Client_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), Client_.modifiePar));
            }
            if (criteria.getAdresseId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdresseId(),
                    root -> root.join(Client_.adresses, JoinType.LEFT).get(Adresse_.id)));
            }
            if (criteria.getCommandeId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommandeId(),
                    root -> root.join(Client_.commandes, JoinType.LEFT).get(Commande_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Client_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
