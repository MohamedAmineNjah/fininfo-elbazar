package org.fininfo.elbazar.service.mapper;


import org.fininfo.elbazar.domain.*;
import org.fininfo.elbazar.service.dto.CategorieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categorie} and its DTO {@link CategorieDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategorieMapper extends EntityMapper<CategorieDTO, Categorie> {


    @Mapping(target = "sousCategories", ignore = true)
    @Mapping(target = "removeSousCategorie", ignore = true)
    @Mapping(target = "produits", ignore = true)
    @Mapping(target = "removeProduit", ignore = true)
    @Mapping(target = "stocks", ignore = true)
    @Mapping(target = "removeStock", ignore = true)
    Categorie toEntity(CategorieDTO categorieDTO);

    default Categorie fromId(Long id) {
        if (id == null) {
            return null;
        }
        Categorie categorie = new Categorie();
        categorie.setId(id);
        return categorie;
    }
}
