package org.fininfo.elbazar.service.mapper;


import org.fininfo.elbazar.domain.*;
import org.fininfo.elbazar.service.dto.InfoSocieteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InfoSociete} and its DTO {@link InfoSocieteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InfoSocieteMapper extends EntityMapper<InfoSocieteDTO, InfoSociete> {



    default InfoSociete fromId(Long id) {
        if (id == null) {
            return null;
        }
        InfoSociete infoSociete = new InfoSociete();
        infoSociete.setId(id);
        return infoSociete;
    }
}
