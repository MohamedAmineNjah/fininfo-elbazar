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

import org.fininfo.elbazar.domain.InfoSociete;
import org.fininfo.elbazar.domain.*; // for static metamodels
import org.fininfo.elbazar.repository.InfoSocieteRepository;
import org.fininfo.elbazar.repository.search.InfoSocieteSearchRepository;
import org.fininfo.elbazar.service.dto.InfoSocieteCriteria;
import org.fininfo.elbazar.service.dto.InfoSocieteDTO;
import org.fininfo.elbazar.service.mapper.InfoSocieteMapper;

/**
 * Service for executing complex queries for {@link InfoSociete} entities in the database.
 * The main input is a {@link InfoSocieteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InfoSocieteDTO} or a {@link Page} of {@link InfoSocieteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InfoSocieteQueryService extends QueryService<InfoSociete> {

    private final Logger log = LoggerFactory.getLogger(InfoSocieteQueryService.class);

    private final InfoSocieteRepository infoSocieteRepository;

    private final InfoSocieteMapper infoSocieteMapper;

    private final InfoSocieteSearchRepository infoSocieteSearchRepository;

    public InfoSocieteQueryService(InfoSocieteRepository infoSocieteRepository, InfoSocieteMapper infoSocieteMapper, InfoSocieteSearchRepository infoSocieteSearchRepository) {
        this.infoSocieteRepository = infoSocieteRepository;
        this.infoSocieteMapper = infoSocieteMapper;
        this.infoSocieteSearchRepository = infoSocieteSearchRepository;
    }

    /**
     * Return a {@link List} of {@link InfoSocieteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InfoSocieteDTO> findByCriteria(InfoSocieteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InfoSociete> specification = createSpecification(criteria);
        return infoSocieteMapper.toDto(infoSocieteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InfoSocieteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InfoSocieteDTO> findByCriteria(InfoSocieteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InfoSociete> specification = createSpecification(criteria);
        return infoSocieteRepository.findAll(specification, page)
            .map(infoSocieteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InfoSocieteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InfoSociete> specification = createSpecification(criteria);
        return infoSocieteRepository.count(specification);
    }

    /**
     * Function to convert {@link InfoSocieteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InfoSociete> createSpecification(InfoSocieteCriteria criteria) {
        Specification<InfoSociete> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InfoSociete_.id));
            }
            if (criteria.getNomSociete() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomSociete(), InfoSociete_.nomSociete));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), InfoSociete_.adresse));
            }
            if (criteria.getTel1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTel1(), InfoSociete_.tel1));
            }
            if (criteria.getTel2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTel2(), InfoSociete_.tel2));
            }
            if (criteria.getTel3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTel3(), InfoSociete_.tel3));
            }
            if (criteria.getEmail1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail1(), InfoSociete_.email1));
            }
            if (criteria.getEmail2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail2(), InfoSociete_.email2));
            }
            if (criteria.getCreeLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreeLe(), InfoSociete_.creeLe));
            }
            if (criteria.getCreePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreePar(), InfoSociete_.creePar));
            }
            if (criteria.getModifieLe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifieLe(), InfoSociete_.modifieLe));
            }
            if (criteria.getModifiePar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiePar(), InfoSociete_.modifiePar));
            }
            if (criteria.getFacebook() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFacebook(), InfoSociete_.facebook));
            }
            if (criteria.getYoutube() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYoutube(), InfoSociete_.youtube));
            }
            if (criteria.getInstagram() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstagram(), InfoSociete_.instagram));
            }
            if (criteria.getTwitter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTwitter(), InfoSociete_.twitter));
            }
            if (criteria.getTiktok() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTiktok(), InfoSociete_.tiktok));
            }
            if (criteria.getMatriculeFiscal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatriculeFiscal(), InfoSociete_.matriculeFiscal));
            }
            if (criteria.getValeurMinPanier() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValeurMinPanier(), InfoSociete_.valeurMinPanier));
            }
        }
        return specification;
    }
}
