package org.fininfo.elbazar.repository;

import java.util.List;

import org.fininfo.elbazar.domain.Categorie;
import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.service.dto.CategorieDTO;
import org.fininfo.elbazar.service.dto.common.CatalogueDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Categorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long>, JpaSpecificationExecutor<Categorie> {

/********************* FININFO CODE: START ********************* */
    // Extract "Categories" and "Sous Categories" from database, "Sous Categories" is extract in JSON Format
     
    @Query(value = "SELECT * FROM (SELECT DISTINCT  c.id, c.nom, c.description, c.position, c.etat, (SELECT COALESCE(jsonb_agg(sous_categorie) FILTER (WHERE sous_categorie IS NOT NULL), '[]') FROM categorie ci \n"
    +"LEFT JOIN (SELECT categorie_id, to_json(sub) as sous_categorie    FROM (SELECT id, nom, description, position, etat, categorie_id FROM sous_categorie WHERE etat = true AND id in \n"
    +"(select distinct sous_categorie_id from produit WHERE etat = true)) sub) sc ON sc.categorie_id=ci.id WHERE c.id=ci.id) #>> '{}' as sous_categorie FROM categorie c LEFT JOIN sous_categorie as sc \n"
    +"ON sc.categorie_id=c.id WHERE c.etat=true ) r WHERE sous_categorie <> '[]' ", nativeQuery = true)
    List<CatalogueDTO> findCatAndSousCat();



/********************* FININFO CODE: END   ********************* */

}
