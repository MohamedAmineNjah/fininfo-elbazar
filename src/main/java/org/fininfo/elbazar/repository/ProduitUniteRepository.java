package org.fininfo.elbazar.repository;

import java.util.Optional;

import org.fininfo.elbazar.domain.ProduitUnite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProduitUnite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitUniteRepository extends JpaRepository<ProduitUnite, Long>, JpaSpecificationExecutor<ProduitUnite> {

    Optional<ProduitUnite> findOneByCode(String code);

}
