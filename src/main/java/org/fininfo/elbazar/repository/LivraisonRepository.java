package org.fininfo.elbazar.repository;

import java.util.Optional;

import org.fininfo.elbazar.domain.Livraison;
import org.fininfo.elbazar.domain.Zone;
import org.fininfo.elbazar.domain.enumeration.ProfileClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Livraison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long>, JpaSpecificationExecutor<Livraison> {

    /**************************** FININFO CODE : START ************************************ */
        // Get Livraison "Frais" & "Delais Livraison" : inputs = Valeur commande, id ville and profile client
    @Query(value = "SELECT DISTINCT l FROM Livraison l JOIN AffectationZone az ON l.zone = az.zone WHERE l.valeurMin < ?1 AND l.valeurMax > ?1 AND az.idVille =?2 AND l.categorieClient =?3")
    Optional<Livraison> findLivByCmdCriteria(Double val, Integer ville, ProfileClient profile);

    /**************************** FININFO CODE : END   ************************************ */
}
