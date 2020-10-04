package org.fininfo.elbazar.service.mapper;


import org.fininfo.elbazar.domain.*;
import org.fininfo.elbazar.service.dto.StockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Stock} and its DTO {@link StockDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProduitMapper.class, CategorieMapper.class, SousCategorieMapper.class})
public interface StockMapper extends EntityMapper<StockDTO, Stock> {

    @Mapping(source = "refProduit.id", target = "refProduitId")
    @Mapping(source = "refProduit.reference", target = "refProduitReference")
    @Mapping(source = "refProduit.nom", target = "nomProduit")
    @Mapping(source = "idCategorie.id", target = "idCategorieId")
    @Mapping(source = "idCategorie.nom", target = "idCategorieNom")
    @Mapping(source = "idSousCategorie.id", target = "idSousCategorieId")
    @Mapping(source = "idSousCategorie.nom", target = "idSousCategorieNom")
    StockDTO toDto(Stock stock);

    @Mapping(source = "refProduitId", target = "refProduit")
    @Mapping(source = "idCategorieId", target = "idCategorie")
    @Mapping(source = "idSousCategorieId", target = "idSousCategorie")
    Stock toEntity(StockDTO stockDTO);

    default Stock fromId(Long id) {
        if (id == null) {
            return null;
        }
        Stock stock = new Stock();
        stock.setId(id);
        return stock;
    }
}
