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

import org.fininfo.elbazar.domain.Stock;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.StockRepository;
import org.fininfo.elbazar.repository.search.StockSearchRepository;
import org.fininfo.elbazar.service.dto.StockCriteria;
import org.fininfo.elbazar.service.dto.StockDTO;
import org.fininfo.elbazar.service.mapper.StockMapper;

/**
 * Service for executing complex queries for {@link Stock} entities in the database.
 * The main input is a {@link StockCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockDTO} or a {@link Page} of {@link StockDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockQueryService extends QueryService<Stock> {

    private final Logger log = LoggerFactory.getLogger(StockQueryService.class);

    private final StockRepository stockRepository;

    private final StockMapper stockMapper;

    private final StockSearchRepository stockSearchRepository;

    public StockQueryService(StockRepository stockRepository, StockMapper stockMapper, StockSearchRepository stockSearchRepository) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
        this.stockSearchRepository = stockSearchRepository;
    }

    /**
     * Return a {@link List} of {@link StockDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockDTO> findByCriteria(StockCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Stock> specification = createSpecification(criteria);
        return stockMapper.toDto(stockRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StockDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockDTO> findByCriteria(StockCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Stock> specification = createSpecification(criteria);
        return stockRepository.findAll(specification, page)
            .map(stockMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Stock> specification = createSpecification(criteria);
        return stockRepository.count(specification);
    }

    /**
     * Function to convert {@link StockCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Stock> createSpecification(StockCriteria criteria) {
        Specification<Stock> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Stock_.id));
            }
            if (criteria.getStockReserve() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockReserve(), Stock_.stockReserve));
            }
            if (criteria.getStockCommande() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockCommande(), Stock_.stockCommande));
            }
            if (criteria.getStockPhysique() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockPhysique(), Stock_.stockPhysique));
            }
            if (criteria.getStockDisponible() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockDisponible(), Stock_.stockDisponible));
            }
            if (criteria.getStockMinimum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockMinimum(), Stock_.stockMinimum));
            }
            if (criteria.getDerniereEntre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDerniereEntre(), Stock_.derniereEntre));
            }
            if (criteria.getDerniereSortie() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDerniereSortie(), Stock_.derniereSortie));
            }
            if (criteria.getAlerteStock() != null) {
                specification = specification.and(buildSpecification(criteria.getAlerteStock(), Stock_.alerteStock));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), Stock_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), Stock_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), Stock_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), Stock_.modifiePar));
            }
            if (criteria.getRefProduitId() != null) {
                specification = specification.and(buildSpecification(criteria.getRefProduitId(),
                    root -> root.join(Stock_.refProduit, JoinType.LEFT).get(Produit_.id)));
            }
            if (criteria.getNomProduit() != null) {
                specification = specification.and(buildSpecification(criteria.getNomProduit(),
                    root -> root.join(Stock_.refProduit, JoinType.LEFT).get(Produit_.nom)));
            }
            if (criteria.getRefProduitReference() != null) {
                specification = specification.and(buildSpecification(criteria.getRefProduitReference(),
                    root -> root.join(Stock_.refProduit, JoinType.LEFT).get(Produit_.reference)));
            }
            if (criteria.getIdCategorieId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdCategorieId(),
                    root -> root.join(Stock_.idCategorie, JoinType.LEFT).get(Categorie_.id)));
            }
            if (criteria.getIdSousCategorieId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdSousCategorieId(),
                    root -> root.join(Stock_.idSousCategorie, JoinType.LEFT).get(SousCategorie_.id)));
            }
        }
        return specification;
    }
}
