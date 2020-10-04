package org.fininfo.elbazar.service.mapper;


import org.fininfo.elbazar.domain.*;
import org.fininfo.elbazar.service.dto.MouvementStockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MouvementStock} and its DTO {@link MouvementStockDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProduitMapper.class, CommandeMapper.class})
public interface MouvementStockMapper extends EntityMapper<MouvementStockDTO, MouvementStock> {

    @Mapping(source = "refProduit.id", target = "refProduitId")
    @Mapping(source = "refProduit.reference", target = "refProduitReference")
    @Mapping(source = "refProduit.nom", target = "nomProduit")
    @Mapping(source = "refCommande.id", target = "refCommandeId")
    @Mapping(source = "refCommande.reference", target = "refCommandeReference")
    @Mapping(source = "refCommande.statut", target = "refCommandeStatut")
    MouvementStockDTO toDto(MouvementStock mouvementStock);

    @Mapping(source = "refProduitId", target = "refProduit")
    @Mapping(source = "refCommandeId", target = "refCommande")
    MouvementStock toEntity(MouvementStockDTO mouvementStockDTO);

    default MouvementStock fromId(Long id) {
        if (id == null) {
            return null;
        }
        MouvementStock mouvementStock = new MouvementStock();
        mouvementStock.setId(id);
        return mouvementStock;
    }
}
