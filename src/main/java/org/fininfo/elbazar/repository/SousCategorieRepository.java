package org.fininfo.elbazar.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.fininfo.elbazar.domain.Categorie;
import org.fininfo.elbazar.domain.SousCategorie;
import org.fininfo.elbazar.service.dto.SousCategorieDTO;
import org.fininfo.elbazar.service.dto.common.SousCatUnicityDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SousCategorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SousCategorieRepository extends JpaRepository<SousCategorie, Long>, JpaSpecificationExecutor<SousCategorie> {


    @Query(value = "SELECT s.id, s.nom as SousCatNom, s.description, s.position, s.etat, s.categorie_id, c.nom as categorie_nom  FROM sous_categorie s \n"
    +"JOIN categorie c ON s.categorie_id = c.id WHERE s.nom = ?1 AND c.nom = ?2", nativeQuery = true)
    Optional<SousCatUnicityDTO> findOneByNom(String cat, String scat);

    @Query(value = "SELECT s.id, s.nom as SousCatNom, s.description, s.position, s.etat, s.categorie_id, c.nom as categorie_nom  FROM sous_categorie s \n"
    +"JOIN categorie c ON s.categorie_id = c.id WHERE s.\"position\" = ?1 AND c.nom = ?2", nativeQuery = true)
    Optional<SousCatUnicityDTO> findOneByPosition(Integer pos, String nom);
    
    @Query(value = "select * from sous_categorie where categorie_id = ?1",  nativeQuery = true)
    List<SousCategorie>  findAllByCategorieID (Long categorie);
}