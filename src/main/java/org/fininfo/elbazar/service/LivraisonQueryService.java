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

import org.fininfo.elbazar.domain.Livraison;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.LivraisonRepository;
import org.fininfo.elbazar.repository.search.LivraisonSearchRepository;
import org.fininfo.elbazar.service.dto.LivraisonCriteria;
import org.fininfo.elbazar.service.dto.LivraisonDTO;
import org.fininfo.elbazar.service.mapper.LivraisonMapper;

/**
 * Service for executing complex queries for {@link Livraison} entities in the database.
 * The main input is a {@link LivraisonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LivraisonDTO} or a {@link Page} of {@link LivraisonDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LivraisonQueryService extends QueryService<Livraison> {

    private final Logger log = LoggerFactory.getLogger(LivraisonQueryService.class);

    private final LivraisonRepository livraisonRepository;

    private final LivraisonMapper livraisonMapper;

    private final LivraisonSearchRepository livraisonSearchRepository;

    public LivraisonQueryService(LivraisonRepository livraisonRepository, LivraisonMapper livraisonMapper, LivraisonSearchRepository livraisonSearchRepository) {
        this.livraisonRepository = livraisonRepository;
        this.livraisonMapper = livraisonMapper;
        this.livraisonSearchRepository = livraisonSearchRepository;
    }

    /**
     * Return a {@link List} of {@link LivraisonDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LivraisonDTO> findByCriteria(LivraisonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Livraison> specification = createSpecification(criteria);
        return livraisonMapper.toDto(livraisonRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LivraisonDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LivraisonDTO> findByCriteria(LivraisonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Livraison> specification = createSpecification(criteria);
        return livraisonRepository.findAll(specification, page)
            .map(livraisonMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LivraisonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Livraison> specification = createSpecification(criteria);
        return livraisonRepository.count(specification);
    }

    /**
     * Function to convert {@link LivraisonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Livraison> createSpecification(LivraisonCriteria criteria) {
        Specification<Livraison> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Livraison_.id));
            }
            if (criteria.getCategorieClient() != null) {
                specification = specification.and(buildSpecification(criteria.getCategorieClient(), Livraison_.categorieClient));
            }
            if (criteria.getIntervalValeur() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIntervalValeur(), Livraison_.intervalValeur));
            }
            if (criteria.getFrais() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFrais(), Livraison_.frais));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Livraison_.date));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), Livraison_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), Livraison_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), Livraison_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), Livraison_.modifiePar));
            }
            if (criteria.getValeurMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValeurMin(), Livraison_.valeurMin));
            }
            if (criteria.getValeurMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValeurMax(), Livraison_.valeurMax));
            }
            if (criteria.getDateLivraison() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateLivraison(), Livraison_.dateLivraison));
            }
            if (criteria.getZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getZoneId(),
                    root -> root.join(Livraison_.zone, JoinType.LEFT).get(Zone_.id)));
            }
        }
        return specification;
    }
}
