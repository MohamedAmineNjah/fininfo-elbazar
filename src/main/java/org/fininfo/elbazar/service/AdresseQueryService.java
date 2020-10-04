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

import org.fininfo.elbazar.domain.Adresse;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.AdresseRepository;
import org.fininfo.elbazar.repository.search.AdresseSearchRepository;
import org.fininfo.elbazar.service.dto.AdresseCriteria;
import org.fininfo.elbazar.service.dto.AdresseDTO;
import org.fininfo.elbazar.service.mapper.AdresseMapper;

/**
 * Service for executing complex queries for {@link Adresse} entities in the database.
 * The main input is a {@link AdresseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdresseDTO} or a {@link Page} of {@link AdresseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdresseQueryService extends QueryService<Adresse> {

    private final Logger log = LoggerFactory.getLogger(AdresseQueryService.class);

    private final AdresseRepository adresseRepository;

    private final AdresseMapper adresseMapper;

    private final AdresseSearchRepository adresseSearchRepository;

    public AdresseQueryService(AdresseRepository adresseRepository, AdresseMapper adresseMapper, AdresseSearchRepository adresseSearchRepository) {
        this.adresseRepository = adresseRepository;
        this.adresseMapper = adresseMapper;
        this.adresseSearchRepository = adresseSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AdresseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdresseDTO> findByCriteria(AdresseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Adresse> specification = createSpecification(criteria);
        return adresseMapper.toDto(adresseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdresseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdresseDTO> findByCriteria(AdresseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Adresse> specification = createSpecification(criteria);
        return adresseRepository.findAll(specification, page)
            .map(adresseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdresseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Adresse> specification = createSpecification(criteria);
        return adresseRepository.count(specification);
    }

    /**
     * Function to convert {@link AdresseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Adresse> createSpecification(AdresseCriteria criteria) {
        Specification<Adresse> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Adresse_.id));
            }
            if (criteria.getPrincipale() != null) {
                specification = specification.and(buildSpecification(criteria.getPrincipale(), Adresse_.principale));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), Adresse_.prenom));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Adresse_.nom));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), Adresse_.adresse));
            }
            if (criteria.getGouvernorat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGouvernorat(), Adresse_.gouvernorat));
            }
            if (criteria.getVille() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVille(), Adresse_.ville));
            }
            if (criteria.getLocalite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalite(), Adresse_.localite));
            }
            if (criteria.getIndication() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndication(), Adresse_.indication));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTelephone(), Adresse_.telephone));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMobile(), Adresse_.mobile));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), Adresse_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), Adresse_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), Adresse_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), Adresse_.modifiePar));
            }
            if (criteria.getCommandeId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommandeId(),
                    root -> root.join(Adresse_.commandes, JoinType.LEFT).get(Commande_.id)));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildSpecification(criteria.getClientId(),
                    root -> root.join(Adresse_.client, JoinType.LEFT).get(Client_.id)));
            }
            if (criteria.getZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getZoneId(),
                    root -> root.join(Adresse_.zone, JoinType.LEFT).get(Zone_.id)));
            }
            if (criteria.getCodePostalId() != null) {
                specification = specification.and(buildSpecification(criteria.getCodePostalId(),
                    root -> root.join(Adresse_.codePostal, JoinType.LEFT).get(AffectationZone_.id)));
            }
        }
        return specification;
    }
}
