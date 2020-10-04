package org.fininfo.elbazar.repository;

import org.fininfo.elbazar.domain.InfoSociete;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InfoSociete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoSocieteRepository extends JpaRepository<InfoSociete, Long>, JpaSpecificationExecutor<InfoSociete> {
}
