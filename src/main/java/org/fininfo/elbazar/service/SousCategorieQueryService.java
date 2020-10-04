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

import org.fininfo.elbazar.domain.SousCategorie;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.SousCategorieRepository;
import org.fininfo.elbazar.repository.search.SousCategorieSearchRepository;
import org.fininfo.elbazar.service.dto.SousCategorieCriteria;
import org.fininfo.elbazar.service.dto.SousCategorieDTO;
import org.fininfo.elbazar.service.mapper.SousCategorieMapper;

/**
 * Service for executing complex queries for {@link SousCategorie} entities in the database.
 * The main input is a {@link SousCategorieCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SousCategorieDTO} or a {@link Page} of {@link SousCategorieDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SousCategorieQueryService extends QueryService<SousCategorie> {

    private final Logger log = LoggerFactory.getLogger(SousCategorieQueryService.class);

    private final SousCategorieRepository sousCategorieRepository;

    private final SousCategorieMapper sousCategorieMapper;

    private final SousCategorieSearchRepository sousCategorieSearchRepository;

    public SousCategorieQueryService(SousCategorieRepository sousCategorieRepository, SousCategorieMapper sousCategorieMapper, SousCategorieSearchRepository sousCategorieSearchRepository) {
        this.sousCategorieRepository = sousCategorieRepository;
        this.sousCategorieMapper = sousCategorieMapper;
        this.sousCategorieSearchRepository = sousCategorieSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SousCategorieDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SousCategorieDTO> findByCriteria(SousCategorieCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SousCategorie> specification = createSpecification(criteria);
        return sousCategorieMapper.toDto(sousCategorieRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SousCategorieDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SousCategorieDTO> findByCriteria(SousCategorieCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SousCategorie> specification = createSpecification(criteria);
        return sousCategorieRepository.findAll(specification, page)
            .map(sousCategorieMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SousCategorieCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SousCategorie> specification = createSpecification(criteria);
        return sousCategorieRepository.count(specification);
    }

    /**
     * Function to convert {@link SousCategorieCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SousCategorie> createSpecification(SousCategorieCriteria criteria) {
        Specification<SousCategorie> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SousCategorie_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), SousCategorie_.nom));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SousCategorie_.description));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPosition(), SousCategorie_.position));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildSpecification(criteria.getEtat(), SousCategorie_.etat));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), SousCategorie_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), SousCategorie_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), SousCategorie_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), SousCategorie_.modifiePar));
            }
            if (criteria.getProduitId() != null) {
                specification = specification.and(buildSpecification(criteria.getProduitId(),
                    root -> root.join(SousCategorie_.produits, JoinType.LEFT).get(Produit_.id)));
            }
            if (criteria.getStockId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockId(),
                    root -> root.join(SousCategorie_.stocks, JoinType.LEFT).get(Stock_.id)));
            }
            if (criteria.getCategorieId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategorieId(),
                    root -> root.join(SousCategorie_.categorie, JoinType.LEFT).get(Categorie_.id)));
            }
        }
        return specification;
    }
}
