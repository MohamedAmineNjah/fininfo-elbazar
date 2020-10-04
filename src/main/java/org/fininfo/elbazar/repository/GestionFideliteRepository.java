package org.fininfo.elbazar.repository;

import org.fininfo.elbazar.domain.GestionFidelite;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the GestionFidelite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GestionFideliteRepository
        extends JpaRepository<GestionFidelite, Long>, JpaSpecificationExecutor<GestionFidelite> {
    Optional<GestionFidelite> findByetatTrue();

    List<GestionFidelite> findByetatFalse();
}
