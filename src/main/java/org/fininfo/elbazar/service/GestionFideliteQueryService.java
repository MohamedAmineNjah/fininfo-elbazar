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

import org.fininfo.elbazar.domain.GestionFidelite;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.GestionFideliteRepository;
import org.fininfo.elbazar.repository.search.GestionFideliteSearchRepository;
import org.fininfo.elbazar.service.dto.GestionFideliteCriteria;
import org.fininfo.elbazar.service.dto.GestionFideliteDTO;
import org.fininfo.elbazar.service.mapper.GestionFideliteMapper;

/**
 * Service for executing complex queries for {@link GestionFidelite} entities in the database.
 * The main input is a {@link GestionFideliteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GestionFideliteDTO} or a {@link Page} of {@link GestionFideliteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GestionFideliteQueryService extends QueryService<GestionFidelite> {

    private final Logger log = LoggerFactory.getLogger(GestionFideliteQueryService.class);

    private final GestionFideliteRepository gestionFideliteRepository;

    private final GestionFideliteMapper gestionFideliteMapper;

    private final GestionFideliteSearchRepository gestionFideliteSearchRepository;

    public GestionFideliteQueryService(GestionFideliteRepository gestionFideliteRepository, GestionFideliteMapper gestionFideliteMapper, GestionFideliteSearchRepository gestionFideliteSearchRepository) {
        this.gestionFideliteRepository = gestionFideliteRepository;
        this.gestionFideliteMapper = gestionFideliteMapper;
        this.gestionFideliteSearchRepository = gestionFideliteSearchRepository;
    }

    /**
     * Return a {@link List} of {@link GestionFideliteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GestionFideliteDTO> findByCriteria(GestionFideliteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GestionFidelite> specification = createSpecification(criteria);
        return gestionFideliteMapper.toDto(gestionFideliteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GestionFideliteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GestionFideliteDTO> findByCriteria(GestionFideliteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GestionFidelite> specification = createSpecification(criteria);
        return gestionFideliteRepository.findAll(specification, page)
            .map(gestionFideliteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GestionFideliteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GestionFidelite> specification = createSpecification(criteria);
        return gestionFideliteRepository.count(specification);
    }

    /**
     * Function to convert {@link GestionFideliteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GestionFidelite> createSpecification(GestionFideliteCriteria criteria) {
        Specification<GestionFidelite> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GestionFidelite_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), GestionFidelite_.nom));
            }
            if (criteria.getPoints() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPoints(), GestionFidelite_.points));
            }
            if (criteria.getValeur() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValeur(), GestionFidelite_.valeur));
            }
            if (criteria.getSilverMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSilverMin(), GestionFidelite_.silverMin));
            }
            if (criteria.getSilverMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSilverMax(), GestionFidelite_.silverMax));
            }
            if (criteria.getGoldMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGoldMin(), GestionFidelite_.goldMin));
            }
            if (criteria.getGoldMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGoldMax(), GestionFidelite_.goldMax));
            }
            if (criteria.getPlatiniumMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlatiniumMin(), GestionFidelite_.platiniumMin));
            }
            if (criteria.getPlatiniumMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlatiniumMax(), GestionFidelite_.platiniumMax));
            }
            if (criteria.getDevise() != null) {
                specification = specification.and(buildSpecification(criteria.getDevise(), GestionFidelite_.devise));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildSpecification(criteria.getEtat(), GestionFidelite_.etat));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), GestionFidelite_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), GestionFidelite_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), GestionFidelite_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), GestionFidelite_.modifiePar));
            }
        }
        return specification;
    }
}
