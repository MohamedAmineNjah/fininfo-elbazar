package org.fininfo.elbazar.service.mapper;


import org.fininfo.elbazar.domain.*;
import org.fininfo.elbazar.service.dto.LivraisonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Livraison} and its DTO {@link LivraisonDTO}.
 */
@Mapper(componentModel = "spring", uses = {ZoneMapper.class})
public interface LivraisonMapper extends EntityMapper<LivraisonDTO, Livraison> {

    @Mapping(source = "zone.id", target = "zoneId")
    @Mapping(source = "zone.nom", target = "zoneNom")
    LivraisonDTO toDto(Livraison livraison);

    @Mapping(source = "zoneId", target = "zone")
    Livraison toEntity(LivraisonDTO livraisonDTO);

    default Livraison fromId(Long id) {
        if (id == null) {
            return null;
        }
        Livraison livraison = new Livraison();
        livraison.setId(id);
        return livraison;
    }
}
