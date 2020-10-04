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

import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.ProduitRepository;
import org.fininfo.elbazar.repository.search.ProduitSearchRepository;
import org.fininfo.elbazar.service.dto.ProduitCriteria;
import org.fininfo.elbazar.service.dto.ProduitDTO;
import org.fininfo.elbazar.service.mapper.ProduitMapper;
import org.springframework.data.domain.PageRequest;


/**
 * Service for executing complex queries for {@link Produit} entities in the database.
 * The main input is a {@link ProduitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProduitDTO} or a {@link Page} of {@link ProduitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProduitQueryService extends QueryService<Produit> {

    private final Logger log = LoggerFactory.getLogger(ProduitQueryService.class);

    private final ProduitRepository produitRepository;

    private final ProduitMapper produitMapper;

    private final ProduitSearchRepository produitSearchRepository;

    public ProduitQueryService(ProduitRepository produitRepository, ProduitMapper produitMapper, ProduitSearchRepository produitSearchRepository) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
        this.produitSearchRepository = produitSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ProduitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProduitDTO> findByCriteria(ProduitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitMapper.toDto(produitRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProduitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitDTO> findByCriteria(ProduitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitRepository.findAll(specification, page)
            .map(produitMapper::toDto);
    }

     /**
     * Return a {@link Page} of {@link ProduitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitDTO> findAllByCriteria(ProduitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final PageRequest nopageable = PageRequest.of(0, (int) produitRepository.count());
        final Specification<Produit> specification = createSpecification(criteria);
        return produitRepository.findAll(specification, nopageable)
            .map(produitMapper::toDto);
    }
    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProduitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitRepository.count(specification);
    }

    /**
     * Function to convert {@link ProduitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Produit> createSpecification(ProduitCriteria criteria) {
        Specification<Produit> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Produit_.id));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), Produit_.reference));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Produit_.nom));
            }
            if (criteria.getCodeBarre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeBarre(), Produit_.codeBarre));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Produit_.description));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildSpecification(criteria.getEtat(), Produit_.etat));
            }
            if (criteria.getMarque() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarque(), Produit_.marque));
            }
            if (criteria.getNature() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNature(), Produit_.nature));
            }
            if (criteria.getStockMinimum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockMinimum(), Produit_.stockMinimum));
            }
            if (criteria.getQuantiteVenteMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantiteVenteMax(), Produit_.quantiteVenteMax));
            }
            if (criteria.getHorsStock() != null) {
                specification = specification.and(buildSpecification(criteria.getHorsStock(), Produit_.horsStock));
            }
            if (criteria.getTypeService() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeService(), Produit_.typeService));
            }
            if (criteria.getDatePremption() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePremption(), Produit_.datePremption));
            }
            if (criteria.getPrixHT() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixHT(), Produit_.prixHT));
            }
            if (criteria.getTauxTVA() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTauxTVA(), Produit_.tauxTVA));
            }
            if (criteria.getPrixTTC() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixTTC(), Produit_.prixTTC));
            }
            if (criteria.getDevise() != null) {
                specification = specification.and(buildSpecification(criteria.getDevise(), Produit_.devise));
            }
            if (criteria.getSourceProduit() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceProduit(), Produit_.sourceProduit));
            }
            if (criteria.getRating() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRating(), Produit_.rating));
            }
            if (criteria.getEligibleRemise() != null) {
                specification = specification.and(buildSpecification(criteria.getEligibleRemise(), Produit_.eligibleRemise));
            }
            if (criteria.getRemise() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRemise(), Produit_.remise));
            }
            if (criteria.getDebutPromo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDebutPromo(), Produit_.debutPromo));
            }
            if (criteria.getFinPromo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinPromo(), Produit_.finPromo));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), Produit_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), Produit_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), Produit_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), Produit_.modifiePar));
            }
            if (criteria.getEnVedette() != null) {
                specification = specification.and(buildSpecification(criteria.getEnVedette(), Produit_.enVedette));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), Produit_.imageUrl));
            }
            if (criteria.getStockId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockId(),
                    root -> root.join(Produit_.stocks, JoinType.LEFT).get(Stock_.id)));
            }
            if (criteria.getMouvementStockId() != null) {
                specification = specification.and(buildSpecification(criteria.getMouvementStockId(),
                    root -> root.join(Produit_.mouvementStocks, JoinType.LEFT).get(MouvementStock_.id)));
            }
            if (criteria.getCommandeLignesId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommandeLignesId(),
                    root -> root.join(Produit_.commandeLignes, JoinType.LEFT).get(CommandeLignes_.id)));
            }
            if (criteria.getCategorieId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategorieId(),
                    root -> root.join(Produit_.categorie, JoinType.LEFT).get(Categorie_.id)));
            }
            if (criteria.getCategorieNom() != null) {
                specification = specification.and(buildSpecification(criteria.getCategorieNom(),
                    root -> root.join(Produit_.categorie, JoinType.LEFT).get(Categorie_.NOM)));
            }
            if (criteria.getSousCategorieId() != null) {
                specification = specification.and(buildSpecification(criteria.getSousCategorieId(),
                    root -> root.join(Produit_.sousCategorie, JoinType.LEFT).get(SousCategorie_.id)));
            }
            if (criteria.getSousCategorieNom() != null) {
                specification = specification.and(buildSpecification(criteria.getSousCategorieNom(),
                    root -> root.join(Produit_.sousCategorie, JoinType.LEFT).get(SousCategorie_.NOM)));
            }
            if (criteria.getUniteId() != null) {
                specification = specification.and(buildSpecification(criteria.getUniteId(),
                    root -> root.join(Produit_.unite, JoinType.LEFT).get(ProduitUnite_.id)));
            }
            if (criteria.getUniteCode() != null) {
                specification = specification.and(buildSpecification(criteria.getUniteCode(),
                    root -> root.join(Produit_.unite, JoinType.LEFT).get(ProduitUnite_.CODE)));
            }
        }
        return specification;
    }
}
