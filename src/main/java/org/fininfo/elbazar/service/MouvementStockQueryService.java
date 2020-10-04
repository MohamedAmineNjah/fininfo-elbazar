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

import org.fininfo.elbazar.domain.MouvementStock;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.MouvementStockRepository;
import org.fininfo.elbazar.repository.search.MouvementStockSearchRepository;
import org.fininfo.elbazar.service.dto.MouvementStockCriteria;
import org.fininfo.elbazar.service.dto.MouvementStockDTO;
import org.fininfo.elbazar.service.mapper.MouvementStockMapper;

/**
 * Service for executing complex queries for {@link MouvementStock} entities in the database.
 * The main input is a {@link MouvementStockCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MouvementStockDTO} or a {@link Page} of {@link MouvementStockDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MouvementStockQueryService extends QueryService<MouvementStock> {

    private final Logger log = LoggerFactory.getLogger(MouvementStockQueryService.class);

    private final MouvementStockRepository mouvementStockRepository;

    private final MouvementStockMapper mouvementStockMapper;

    private final MouvementStockSearchRepository mouvementStockSearchRepository;

    public MouvementStockQueryService(MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper, MouvementStockSearchRepository mouvementStockSearchRepository) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockMapper = mouvementStockMapper;
        this.mouvementStockSearchRepository = mouvementStockSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MouvementStockDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MouvementStockDTO> findByCriteria(MouvementStockCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MouvementStock> specification = createSpecification(criteria);
        return mouvementStockMapper.toDto(mouvementStockRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MouvementStockDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MouvementStockDTO> findByCriteria(MouvementStockCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MouvementStock> specification = createSpecification(criteria);
        return mouvementStockRepository.findAll(specification, page)
            .map(mouvementStockMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MouvementStockCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MouvementStock> specification = createSpecification(criteria);
        return mouvementStockRepository.count(specification);
    }

    /**
     * Function to convert {@link MouvementStockCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MouvementStock> createSpecification(MouvementStockCriteria criteria) {
        Specification<MouvementStock> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MouvementStock_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), MouvementStock_.type));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), MouvementStock_.date));
            }
            if (criteria.getSens() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSens(), MouvementStock_.sens));
            }
            if (criteria.getQuantite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantite(), MouvementStock_.quantite));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), MouvementStock_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), MouvementStock_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), MouvementStock_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), MouvementStock_.modifiePar));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), MouvementStock_.reference));
            }
            if (criteria.getRefProduitId() != null) {
                specification = specification.and(buildSpecification(criteria.getRefProduitId(),
                    root -> root.join(MouvementStock_.refProduit, JoinType.LEFT).get(Produit_.id)));
            }
            if (criteria.getRefProduitReference() != null) {
                specification = specification.and(buildSpecification(criteria.getRefProduitReference(),
                    root -> root.join(MouvementStock_.refProduit, JoinType.LEFT).get(Produit_.reference)));
            }
            if (criteria.getNomProduit() != null) {
                specification = specification.and(buildSpecification(criteria.getNomProduit(),
                    root -> root.join(MouvementStock_.refProduit, JoinType.LEFT).get(Produit_.nom)));
            }
            if (criteria.getRefCommandeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRefCommandeId(),
                    root -> root.join(MouvementStock_.refCommande, JoinType.LEFT).get(Commande_.id)));
            }
            if (criteria.getRefCommandeReference() != null) {
                specification = specification.and(buildSpecification(criteria.getRefCommandeReference(),
                    root -> root.join(MouvementStock_.refCommande, JoinType.LEFT).get(Commande_.reference)));
            }
            if (criteria.getRefCommandeStatut()!= null) {
                specification = specification.and(buildSpecification(criteria.getRefCommandeStatut(),
                    root -> root.join(MouvementStock_.refCommande, JoinType.LEFT).get(Commande_.statut)));
            }
        }
        return specification;
    }
}
