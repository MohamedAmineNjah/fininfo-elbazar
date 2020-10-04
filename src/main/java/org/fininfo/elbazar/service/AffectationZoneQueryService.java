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

import org.fininfo.elbazar.domain.AffectationZone;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.AffectationZoneRepository;
import org.fininfo.elbazar.repository.search.AffectationZoneSearchRepository;
import org.fininfo.elbazar.service.dto.AffectationZoneCriteria;
import org.fininfo.elbazar.service.dto.AffectationZoneDTO;
import org.fininfo.elbazar.service.mapper.AffectationZoneMapper;

/**
 * Service for executing complex queries for {@link AffectationZone} entities in
 * the database. The main input is a {@link AffectationZoneCriteria} which gets
 * converted to {@link Specification}, in a way that all the filters must apply.
 * It returns a {@link List} of {@link AffectationZoneDTO} or a {@link Page} of
 * {@link AffectationZoneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AffectationZoneQueryService extends QueryService<AffectationZone> {

    private final Logger log = LoggerFactory.getLogger(AffectationZoneQueryService.class);

    private final AffectationZoneRepository affectationZoneRepository;

    private final AffectationZoneMapper affectationZoneMapper;

    private final AffectationZoneSearchRepository affectationZoneSearchRepository;

    public AffectationZoneQueryService(AffectationZoneRepository affectationZoneRepository,
            AffectationZoneMapper affectationZoneMapper,
            AffectationZoneSearchRepository affectationZoneSearchRepository) {
        this.affectationZoneRepository = affectationZoneRepository;
        this.affectationZoneMapper = affectationZoneMapper;
        this.affectationZoneSearchRepository = affectationZoneSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AffectationZoneDTO} which matches the
     * criteria from the database.
     * 
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AffectationZoneDTO> findByCriteria(AffectationZoneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AffectationZone> specification = createSpecification(criteria);
        return affectationZoneMapper.toDto(affectationZoneRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AffectationZoneDTO} which matches the
     * criteria from the database.
     * 
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AffectationZoneDTO> findByCriteria(AffectationZoneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AffectationZone> specification = createSpecification(criteria);
        return affectationZoneRepository.findAll(specification, page).map(affectationZoneMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * 
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AffectationZoneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AffectationZone> specification = createSpecification(criteria);
        return affectationZoneRepository.count(specification);
    }

    /**
     * Function to convert {@link AffectationZoneCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AffectationZone> createSpecification(AffectationZoneCriteria criteria) {
        Specification<AffectationZone> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AffectationZone_.id));
            }
            if (criteria.getGouvernorat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGouvernorat(), AffectationZone_.gouvernorat));
            }
            if (criteria.getVille() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVille(), AffectationZone_.ville));
            }
            if (criteria.getLocalite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalite(), AffectationZone_.localite));
            }
            if (criteria.getCodePostal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodePostal(), AffectationZone_.codePostal));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), AffectationZone_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), AffectationZone_.modifiePar));
            }
            if (criteria.getIdVille() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdVille(), AffectationZone_.idVille));
            }
            if (criteria.getAdresseId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdresseId(),
                    root -> root.join(AffectationZone_.adresses, JoinType.LEFT).get(Adresse_.id)));
            }
            if (criteria.getZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getZoneId(),
                    root -> root.join(AffectationZone_.zone, JoinType.LEFT).get(Zone_.id)));
            }
            if (criteria.getZoneNom() != null) {
                specification = specification.and(buildSpecification(criteria.getZoneNom(),
                    root -> root.join(AffectationZone_.zone, JoinType.LEFT).get(Zone_.nom)));
            }
        }
        return specification;
    }
}
