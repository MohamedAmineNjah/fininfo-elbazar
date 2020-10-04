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

import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.CommandeRepository;
import org.fininfo.elbazar.repository.search.CommandeSearchRepository;
import org.fininfo.elbazar.service.dto.CommandeCriteria;
import org.fininfo.elbazar.service.dto.CommandeDTO;
import org.fininfo.elbazar.service.mapper.CommandeMapper;

/**
 * Service for executing complex queries for {@link Commande} entities in the database.
 * The main input is a {@link CommandeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommandeDTO} or a {@link Page} of {@link CommandeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommandeQueryService extends QueryService<Commande> {

    private final Logger log = LoggerFactory.getLogger(CommandeQueryService.class);

    private final CommandeRepository commandeRepository;

    private final CommandeMapper commandeMapper;

    private final CommandeSearchRepository commandeSearchRepository;

    public CommandeQueryService(CommandeRepository commandeRepository, CommandeMapper commandeMapper, CommandeSearchRepository commandeSearchRepository) {
        this.commandeRepository = commandeRepository;
        this.commandeMapper = commandeMapper;
        this.commandeSearchRepository = commandeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommandeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommandeDTO> findByCriteria(CommandeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Commande> specification = createSpecification(criteria);
        return commandeMapper.toDto(commandeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommandeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommandeDTO> findByCriteria(CommandeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Commande> specification = createSpecification(criteria);
        return commandeRepository.findAll(specification, page)
            .map(commandeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommandeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Commande> specification = createSpecification(criteria);
        return commandeRepository.count(specification);
    }

    /**
     * Function to convert {@link CommandeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Commande> createSpecification(CommandeCriteria criteria) {
        Specification<Commande> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Commande_.id));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), Commande_.reference));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Commande_.date));
            }
            if (criteria.getStatut() != null) {
                specification = specification.and(buildSpecification(criteria.getStatut(), Commande_.statut));
            }
            if (criteria.getOrigine() != null) {
                specification = specification.and(buildSpecification(criteria.getOrigine(), Commande_.origine));
            }
            if (criteria.getTotalHT() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalHT(), Commande_.totalHT));
            }
            if (criteria.getTotalTVA() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalTVA(), Commande_.totalTVA));
            }
            if (criteria.getTotalRemise() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalRemise(), Commande_.totalRemise));
            }
            if (criteria.getTotTTC() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotTTC(), Commande_.totTTC));
            }
            if (criteria.getDevise() != null) {
                specification = specification.and(buildSpecification(criteria.getDevise(), Commande_.devise));
            }
            if (criteria.getPointsFidelite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPointsFidelite(), Commande_.pointsFidelite));
            }
            if (criteria.getReglement() != null) {
                specification = specification.and(buildSpecification(criteria.getReglement(), Commande_.reglement));
            }
            if (criteria.getDateLivraison() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateLivraison(), Commande_.dateLivraison));
            }
            if (criteria.getDateCreation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateCreation(), Commande_.dateCreation));
            }
            if (criteria.getDateAnnulation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateAnnulation(), Commande_.dateAnnulation));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), Commande_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), Commande_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), Commande_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), Commande_.modifiePar));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), Commande_.prenom));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Commande_.nom));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), Commande_.adresse));
            }
            if (criteria.getGouvernorat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGouvernorat(), Commande_.gouvernorat));
            }
            if (criteria.getVille() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVille(), Commande_.ville));
            }
            if (criteria.getLocalite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalite(), Commande_.localite));
            }
            if (criteria.getCodePostal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodePostal(), Commande_.codePostal));
            }
            if (criteria.getIndication() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndication(), Commande_.indication));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTelephone(), Commande_.telephone));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMobile(), Commande_.mobile));
            }
            if (criteria.getFraisLivraison() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFraisLivraison(), Commande_.fraisLivraison));
            }
            if (criteria.getMouvementStockId() != null) {
                specification = specification.and(buildSpecification(criteria.getMouvementStockId(),
                    root -> root.join(Commande_.mouvementStocks, JoinType.LEFT).get(MouvementStock_.id)));
            }
            if (criteria.getCommandeLignesId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommandeLignesId(),
                    root -> root.join(Commande_.commandeLignes, JoinType.LEFT).get(CommandeLignes_.id)));
            }
            if (criteria.getIdClientId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdClientId(),
                    root -> root.join(Commande_.idClient, JoinType.LEFT).get(Client_.id)));
            }
            if (criteria.getIdAdresseId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdAdresseId(),
                    root -> root.join(Commande_.idAdresse, JoinType.LEFT).get(Adresse_.id)));
            }
            if (criteria.getZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getZoneId(),
                    root -> root.join(Commande_.zone, JoinType.LEFT).get(Zone_.id)));
            }
            if (criteria.getZoneNom() != null) {
                specification = specification.and(buildSpecification(criteria.getZoneNom(),
                    root -> root.join(Commande_.zone, JoinType.LEFT).get(Zone_.nom)));
            }
            if (criteria.getNomClient() != null) {
                specification = specification.and(buildSpecification(criteria.getNomClient(),
                    root -> root.join(Commande_.idClient, JoinType.LEFT).get(Client_.nom)));
            }
        }
        return specification;
    }
}
