package org.fininfo.elbazar.repository;

import org.fininfo.elbazar.domain.MouvementStock;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MouvementStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long>, JpaSpecificationExecutor<MouvementStock> {
}
