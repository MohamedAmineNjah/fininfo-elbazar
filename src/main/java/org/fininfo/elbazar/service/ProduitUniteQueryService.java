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

import org.fininfo.elbazar.domain.ProduitUnite;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.ProduitUniteRepository;
import org.fininfo.elbazar.repository.search.ProduitUniteSearchRepository;
import org.fininfo.elbazar.service.dto.ProduitUniteCriteria;
import org.fininfo.elbazar.service.dto.ProduitUniteDTO;
import org.fininfo.elbazar.service.mapper.ProduitUniteMapper;

/**
 * Service for executing complex queries for {@link ProduitUnite} entities in the database.
 * The main input is a {@link ProduitUniteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProduitUniteDTO} or a {@link Page} of {@link ProduitUniteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProduitUniteQueryService extends QueryService<ProduitUnite> {

    private final Logger log = LoggerFactory.getLogger(ProduitUniteQueryService.class);

    private final ProduitUniteRepository produitUniteRepository;

    private final ProduitUniteMapper produitUniteMapper;

    private final ProduitUniteSearchRepository produitUniteSearchRepository;

    public ProduitUniteQueryService(ProduitUniteRepository produitUniteRepository, ProduitUniteMapper produitUniteMapper, ProduitUniteSearchRepository produitUniteSearchRepository) {
        this.produitUniteRepository = produitUniteRepository;
        this.produitUniteMapper = produitUniteMapper;
        this.produitUniteSearchRepository = produitUniteSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ProduitUniteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProduitUniteDTO> findByCriteria(ProduitUniteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProduitUnite> specification = createSpecification(criteria);
        return produitUniteMapper.toDto(produitUniteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProduitUniteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitUniteDTO> findByCriteria(ProduitUniteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProduitUnite> specification = createSpecification(criteria);
        return produitUniteRepository.findAll(specification, page)
            .map(produitUniteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProduitUniteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProduitUnite> specification = createSpecification(criteria);
        return produitUniteRepository.count(specification);
    }

    /**
     * Function to convert {@link ProduitUniteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProduitUnite> createSpecification(ProduitUniteCriteria criteria) {
        Specification<ProduitUnite> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProduitUnite_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProduitUnite_.code));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), ProduitUnite_.nom));
            }
            if (criteria.getProduitId() != null) {
                specification = specification.and(buildSpecification(criteria.getProduitId(),
                    root -> root.join(ProduitUnite_.produits, JoinType.LEFT).get(Produit_.id)));
            }
        }
        return specification;
    }
}
