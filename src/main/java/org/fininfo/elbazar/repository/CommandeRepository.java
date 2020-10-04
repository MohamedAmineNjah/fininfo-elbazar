package org.fininfo.elbazar.repository;

import java.util.List;
import java.util.Optional;

import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.service.dto.common.CommandeClientDTO;
import org.fininfo.elbazar.service.dto.common.CommandeHeaderProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Commande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long>, JpaSpecificationExecutor<Commande> {
	
	@Query(value = "SELECT DISTINCT c.id, c.reference, c.date_creation, c.date_annulation, c.statut, c.origine, c.reglement, c.points_fidelite, c.date_livraison, \n"
	+"c.total_ht, c.total_tva, c.total_remise, c.tot_ttc, c.devise, c.id_client_id, (SELECT jsonb_agg(sous_categorie) FROM commande ci LEFT JOIN ( SELECT ref_commande_id, to_json(sub) as sous_categorie \n"
	+"FROM (SELECT cl.id, ref_produit_id, p.nom, p.reference, quantite, cl.prix_ht, cl.remise, cl.tva, cl.prix_ttc, ref_commande_id FROM commande_lignes cl JOIN produit p on cl.ref_produit_id = p.id) sub) sc \n"
	+"ON sc.ref_commande_id = ci.id WHERE c.id = ci.id) #>> '{}' as commande_lignes FROM commande c LEFT JOIN commande_lignes as sc ON sc.ref_commande_id = c.id WHERE id_client_id=?1 ORDER  BY c.date_creation asc", nativeQuery = true)
	List<CommandeClientDTO> findCommandeByClient(Long id_client_id);
	
	@Query(value = "SELECT DISTINCT c.id, c.reference, c.date_creation, c.date_annulation, c.statut, c.origine, c.reglement, c.points_fidelite, c.date_livraison, \n"
	+"c.total_ht, c.total_tva, c.total_remise, c.tot_ttc, c.devise, c.id_client_id, (SELECT jsonb_agg(sous_categorie) FROM commande ci LEFT JOIN ( SELECT ref_commande_id, to_json(sub) as sous_categorie \n"
	+"FROM (SELECT cl.id, ref_produit_id, p.nom, p.reference, quantite, cl.prix_ht, cl.remise, cl.tva, cl.prix_ttc, ref_commande_id FROM commande_lignes cl JOIN produit p on cl.ref_produit_id = p.id) sub) sc \n"
	+"ON sc.ref_commande_id = ci.id WHERE c.id = ci.id) #>> '{}' as commande_lignes FROM commande c LEFT JOIN commande_lignes as sc ON sc.ref_commande_id = c.id \n" 
	+"INNER JOIN jhi_user usr on c.id_client_id = usr.id WHERE usr.login = ?1 ORDER  BY c.date_creation asc", nativeQuery = true)
    List<CommandeClientDTO> findCommandeByLogin(String login);
	
	Optional<Commande> findByIdClient(Client idClient);
}
