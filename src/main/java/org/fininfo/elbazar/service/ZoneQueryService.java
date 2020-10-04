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

import org.fininfo.elbazar.domain.Zone;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.ZoneRepository;
import org.fininfo.elbazar.repository.search.ZoneSearchRepository;
import org.fininfo.elbazar.service.dto.ZoneCriteria;
import org.fininfo.elbazar.service.dto.ZoneDTO;
import org.fininfo.elbazar.service.mapper.ZoneMapper;

/**
 * Service for executing complex queries for {@link Zone} entities in the database.
 * The main input is a {@link ZoneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZoneDTO} or a {@link Page} of {@link ZoneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZoneQueryService extends QueryService<Zone> {

    private final Logger log = LoggerFactory.getLogger(ZoneQueryService.class);

    private final ZoneRepository zoneRepository;

    private final ZoneMapper zoneMapper;

    private final ZoneSearchRepository zoneSearchRepository;

    public ZoneQueryService(ZoneRepository zoneRepository, ZoneMapper zoneMapper, ZoneSearchRepository zoneSearchRepository) {
        this.zoneRepository = zoneRepository;
        this.zoneMapper = zoneMapper;
        this.zoneSearchRepository = zoneSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ZoneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ZoneDTO> findByCriteria(ZoneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Zone> specification = createSpecification(criteria);
        return zoneMapper.toDto(zoneRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ZoneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ZoneDTO> findByCriteria(ZoneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Zone> specification = createSpecification(criteria);
        return zoneRepository.findAll(specification, page)
            .map(zoneMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ZoneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Zone> specification = createSpecification(criteria);
        return zoneRepository.count(specification);
    }

    /**
     * Function to convert {@link ZoneCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Zone> createSpecification(ZoneCriteria criteria) {
        Specification<Zone> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Zone_.id));
            }
            if (criteria.getCodeZone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeZone(), Zone_.codeZone));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Zone_.nom));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), Zone_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), Zone_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), Zone_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), Zone_.modifiePar));
            }
            if (criteria.getAdresseId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdresseId(),
                    root -> root.join(Zone_.adresses, JoinType.LEFT).get(Adresse_.id)));
            }
            if (criteria.getLivraisonId() != null) {
                specification = specification.and(buildSpecification(criteria.getLivraisonId(),
                    root -> root.join(Zone_.livraisons, JoinType.LEFT).get(Livraison_.id)));
            }
            if (criteria.getAffectationZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getAffectationZoneId(),
                    root -> root.join(Zone_.affectationZones, JoinType.LEFT).get(AffectationZone_.id)));
            }
            if (criteria.getCommandeId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommandeId(),
                    root -> root.join(Zone_.commandes, JoinType.LEFT).get(Commande_.id)));
            }
        }
        return specification;
    }
}
