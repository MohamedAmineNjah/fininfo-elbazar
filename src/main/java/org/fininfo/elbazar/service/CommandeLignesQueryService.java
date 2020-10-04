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

import org.fininfo.elbazar.domain.CommandeLignes;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.CommandeLignesRepository;
import org.fininfo.elbazar.repository.search.CommandeLignesSearchRepository;
import org.fininfo.elbazar.service.dto.CommandeLignesCriteria;
import org.fininfo.elbazar.service.dto.CommandeLignesDTO;
import org.fininfo.elbazar.service.mapper.CommandeLignesMapper;

/**
 * Service for executing complex queries for {@link CommandeLignes} entities in the database.
 * The main input is a {@link CommandeLignesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommandeLignesDTO} or a {@link Page} of {@link CommandeLignesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommandeLignesQueryService extends QueryService<CommandeLignes> {

    private final Logger log = LoggerFactory.getLogger(CommandeLignesQueryService.class);

    private final CommandeLignesRepository commandeLignesRepository;

    private final CommandeLignesMapper commandeLignesMapper;

    private final CommandeLignesSearchRepository commandeLignesSearchRepository;

    public CommandeLignesQueryService(CommandeLignesRepository commandeLignesRepository, CommandeLignesMapper commandeLignesMapper, CommandeLignesSearchRepository commandeLignesSearchRepository) {
        this.commandeLignesRepository = commandeLignesRepository;
        this.commandeLignesMapper = commandeLignesMapper;
        this.commandeLignesSearchRepository = commandeLignesSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommandeLignesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommandeLignesDTO> findByCriteria(CommandeLignesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommandeLignes> specification = createSpecification(criteria);
        return commandeLignesMapper.toDto(commandeLignesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommandeLignesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommandeLignesDTO> findByCriteria(CommandeLignesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommandeLignes> specification = createSpecification(criteria);
        return commandeLignesRepository.findAll(specification, page)
            .map(commandeLignesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommandeLignesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommandeLignes> specification = createSpecification(criteria);
        return commandeLignesRepository.count(specification);
    }

    /**
     * Function to convert {@link CommandeLignesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CommandeLignes> createSpecification(CommandeLignesCriteria criteria) {
        Specification<CommandeLignes> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CommandeLignes_.id));
            }
            if (criteria.getQuantite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantite(), CommandeLignes_.quantite));
            }
            if (criteria.getPrixHT() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixHT(), CommandeLignes_.prixHT));
            }
            if (criteria.getRemise() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRemise(), CommandeLignes_.remise));
            }
            if (criteria.getTva() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTva(), CommandeLignes_.tva));
            }
            if (criteria.getPrixTTC() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixTTC(), CommandeLignes_.prixTTC));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), CommandeLignes_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), CommandeLignes_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), CommandeLignes_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), CommandeLignes_.modifiePar));
            }
            if (criteria.getRefCommandeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRefCommandeId(),
                    root -> root.join(CommandeLignes_.refCommande, JoinType.LEFT).get(Commande_.id)));
            }
            if (criteria.getRefProduitId() != null) {
                specification = specification.and(buildSpecification(criteria.getRefProduitId(),
                    root -> root.join(CommandeLignes_.refProduit, JoinType.LEFT).get(Produit_.id)));
            }
        }
        return specification;
    }
}
