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

import org.fininfo.elbazar.domain.Categorie;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.CategorieRepository;
import org.fininfo.elbazar.repository.search.CategorieSearchRepository;
import org.fininfo.elbazar.service.dto.CategorieCriteria;
import org.fininfo.elbazar.service.dto.CategorieDTO;
import org.fininfo.elbazar.service.mapper.CategorieMapper;

/**
 * Service for executing complex queries for {@link Categorie} entities in the database.
 * The main input is a {@link CategorieCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategorieDTO} or a {@link Page} of {@link CategorieDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategorieQueryService extends QueryService<Categorie> {

    private final Logger log = LoggerFactory.getLogger(CategorieQueryService.class);

    private final CategorieRepository categorieRepository;

    private final CategorieMapper categorieMapper;

    private final CategorieSearchRepository categorieSearchRepository;

    public CategorieQueryService(CategorieRepository categorieRepository, CategorieMapper categorieMapper, CategorieSearchRepository categorieSearchRepository) {
        this.categorieRepository = categorieRepository;
        this.categorieMapper = categorieMapper;
        this.categorieSearchRepository = categorieSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CategorieDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategorieDTO> findByCriteria(CategorieCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Categorie> specification = createSpecification(criteria);
        return categorieMapper.toDto(categorieRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategorieDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategorieDTO> findByCriteria(CategorieCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Categorie> specification = createSpecification(criteria);
        return categorieRepository.findAll(specification, page)
            .map(categorieMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategorieCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Categorie> specification = createSpecification(criteria);
        return categorieRepository.count(specification);
    }

    /**
     * Function to convert {@link CategorieCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Categorie> createSpecification(CategorieCriteria criteria) {
        Specification<Categorie> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Categorie_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Categorie_.nom));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Categorie_.description));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPosition(), Categorie_.position));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildSpecification(criteria.getEtat(), Categorie_.etat));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), Categorie_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), Categorie_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), Categorie_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), Categorie_.modifiePar));
            }
            if (criteria.getSousCategorieId() != null) {
                specification = specification.and(buildSpecification(criteria.getSousCategorieId(),
                    root -> root.join(Categorie_.sousCategories, JoinType.LEFT).get(SousCategorie_.id)));
            }
            if (criteria.getProduitId() != null) {
                specification = specification.and(buildSpecification(criteria.getProduitId(),
                    root -> root.join(Categorie_.produits, JoinType.LEFT).get(Produit_.id)));
            }
            if (criteria.getStockId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockId(),
                    root -> root.join(Categorie_.stocks, JoinType.LEFT).get(Stock_.id)));
            }
        }
        return specification;
    }
}
