package org.fininfo.elbazar.service.mapper;


import org.fininfo.elbazar.domain.*;
import org.fininfo.elbazar.service.dto.ProduitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produit} and its DTO {@link ProduitDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategorieMapper.class, SousCategorieMapper.class, ProduitUniteMapper.class})
public interface ProduitMapper extends EntityMapper<ProduitDTO, Produit> {

    @Mapping(source = "categorie.id", target = "categorieId")
    @Mapping(source = "categorie.nom", target = "categorieNom")
    @Mapping(source = "sousCategorie.id", target = "sousCategorieId")
    @Mapping(source = "sousCategorie.nom", target = "sousCategorieNom")
    @Mapping(source = "unite.id", target = "uniteId")
    @Mapping(source = "unite.code", target = "uniteCode")
    ProduitDTO toDto(Produit produit);

    @Mapping(target = "stocks", ignore = true)
    @Mapping(target = "removeStock", ignore = true)
    @Mapping(target = "mouvementStocks", ignore = true)
    @Mapping(target = "removeMouvementStock", ignore = true)
    @Mapping(target = "commandeLignes", ignore = true)
    @Mapping(target = "removeCommandeLignes", ignore = true)
    @Mapping(source = "categorieId", target = "categorie")
    @Mapping(source = "sousCategorieId", target = "sousCategorie")
    @Mapping(source = "uniteId", target = "unite")
    @Mapping(target = "image", ignore = true)
    Produit toEntity(ProduitDTO produitDTO);

    default Produit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produit produit = new Produit();
        produit.setId(id);
        return produit;
    }
}
