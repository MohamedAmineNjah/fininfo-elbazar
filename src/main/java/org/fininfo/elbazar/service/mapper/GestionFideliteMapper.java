package org.fininfo.elbazar.service.mapper;


import org.fininfo.elbazar.domain.*;
import org.fininfo.elbazar.service.dto.GestionFideliteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GestionFidelite} and its DTO {@link GestionFideliteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GestionFideliteMapper extends EntityMapper<GestionFideliteDTO, GestionFidelite> {



    default GestionFidelite fromId(Long id) {
        if (id == null) {
            return null;
        }
        GestionFidelite gestionFidelite = new GestionFidelite();
        gestionFidelite.setId(id);
        return gestionFidelite;
    }
}
