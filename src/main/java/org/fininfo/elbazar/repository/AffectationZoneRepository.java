package org.fininfo.elbazar.repository;

import java.util.List;

import org.fininfo.elbazar.domain.AffectationZone;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AffectationZone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffectationZoneRepository extends JpaRepository<AffectationZone, Long>, JpaSpecificationExecutor<AffectationZone> {

    List<AffectationZone> findByGouvernoratAndVille( String gouvernorat,  String ville);

}
