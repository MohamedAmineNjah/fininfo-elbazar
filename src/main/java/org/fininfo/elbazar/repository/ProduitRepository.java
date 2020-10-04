package org.fininfo.elbazar.repository;

import java.util.List;
import java.util.Optional;

import org.fininfo.elbazar.domain.Produit;

import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.service.dto.ProduitDTO;
import org.fininfo.elbazar.service.dto.common.ProduitBySousCatDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long>, JpaSpecificationExecutor<Produit> {

        /*****************************
         * FININFO CODE : START
         *************************************/

        // Get all "Produits" by "SousCategorie" id
        @Query(value = "SELECT  p.id, p.reference, p.nom, p.code_barre, p.description, p.etat, p.marque, p.nature, p.stock_minimum, p.quantite_vente_max, p.hors_stock, p.prix_ht, \n"
                        + "p.taux_tva, p.prix_ttc, p.devise, p.source_produit, p.rating, p.remise, p.debut_promo, p.fin_promo, u.code as unite, p.image, p.image_url,  p.eligible_remise \n"
                        + "FROM public.produit as p INNER JOIN public.sous_categorie as s ON p.sous_categorie_id = s.id INNER JOIN public.produit_unite as u ON p.unite_id = u.id WHERE p.etat =true AND s.id =?1", nativeQuery = true)
        Page<ProduitBySousCatDTO> findProdBySousCat(Long id, Pageable pageable);

        // Search all "Produits" by "SousCategorie" id
        @Query(value = "SELECT * FROM produit WHERE sous_categorie_id=?1 AND UPPER(nom) LIKE UPPER('%'||?2||'%')", nativeQuery = true)
        Page<ProduitBySousCatDTO> findProdBySousCatSearch(Long id, String scat, Pageable pageable);

        // Get all "Produits" on Promo
        @Query(value = "SELECT * FROM produit WHERE date(now()) BETWEEN debut_promo AND fin_promo", nativeQuery = true)
        Page<ProduitBySousCatDTO> findProdPromo(Pageable pageable);

        // Get TOP 10 new "Produits"
        @Query(value = "SELECT * FROM produit", nativeQuery = true)
        Page<ProduitBySousCatDTO> find10NewProd(Pageable pageable);

        // Get "Produits" en vedette
        @Query(value = "SELECT * FROM produit WHERE en_vedette = true", nativeQuery = true)
        Page<ProduitBySousCatDTO> findProdByVedette(Pageable pageable);

        // Get "Produits" renamed
        @Query(value = "SELECT p.id, p.reference, p.nom, p.code_barre, p.description,p.etat,p.marque,p.nature,p.stock_minimum,p.quantite_vente_max,p.hors_stock,p.type_service,p.date_premption,p.prix_ht, \n"
                        + "p.taux_tva,p.prix_ttc,p.devise,p.source_produit,p.rating,p.eligible_remise ,p.remise,p.debut_promo,p.fin_promo,p.image,p.image_url,p.image_content_type,p.cree_le,p.cree_par,p.modifie_le,\n"
                        + "p.modifie_par,p.en_vedette,p.categorie_id,c.nom as categorie_nom,p.sous_categorie_id,s.nom as sous_categorie_nom,p.unite_id,u.code as code_unite FROM public.produit AS p INNER JOIN public.sous_categorie AS s \n"
                        + "ON p.sous_categorie_id = s.id INNER JOIN public.categorie AS c ON p.categorie_id = c.id INNER JOIN public.produit_unite AS u ON p.unite_id = u.id WHERE p.etat =TRUE AND p.id =?1", nativeQuery = true)
        Optional<ProduitBySousCatDTO> findById_(Long id);

        // amine
        @Query(value = "SELECT * FROM produit WHERE  UPPER(nom) LIKE UPPER('%'||?1||'%')", nativeQuery = true)
        Page<ProduitBySousCatDTO> findProdByName(String nom, Pageable pageable);

        Optional<Produit> findOneByreference(String reference);

        /*****************************
         * FININFO CODE : END
         *************************************/

}
