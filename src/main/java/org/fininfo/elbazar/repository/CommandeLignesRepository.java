package org.fininfo.elbazar.repository;

import java.util.List;

import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.domain.CommandeLignes;
import org.fininfo.elbazar.service.dto.common.CommandeLignesHeaderProjection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the CommandeLignes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeLignesRepository extends JpaRepository<CommandeLignes, Long>, JpaSpecificationExecutor<CommandeLignes> {
	
	/************************** FININFO START ************************* */
	
	List<CommandeLignes> findByRefCommande(Commande refCommande);
	
	@Query(value = "SELECT cmdl.id, cmdl.quantite, cmdl.prix_ht, cmdl.remise, cmdl.tva, cmdl.prix_ttc, cmdl.ref_commande_id, cmdl.ref_produit_id, prd.reference, prd.nom FROM public.commande_lignes as cmdl INNER JOIN public.produit as prd ON cmdl.ref_produit_id = prd.id WHERE cmdl.ref_commande_id = ?1", nativeQuery = true)
	List<CommandeLignesHeaderProjection> findAllByrefCommande (Long id);
	
	
	/************************** FININFO END *************************** */
	
}
