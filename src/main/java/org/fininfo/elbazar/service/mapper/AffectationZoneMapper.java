package org.fininfo.elbazar.service.mapper;


import org.fininfo.elbazar.domain.*;
import org.fininfo.elbazar.service.dto.AffectationZoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AffectationZone} and its DTO {@link AffectationZoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {ZoneMapper.class})
public interface AffectationZoneMapper extends EntityMapper<AffectationZoneDTO, AffectationZone> {

    @Mapping(source = "zone.id", target = "zoneId")
    @Mapping(source = "zone.nom", target = "zoneNom")
    AffectationZoneDTO toDto(AffectationZone affectationZone);

    @Mapping(target = "adresses", ignore = true)
    @Mapping(target = "removeAdresse", ignore = true)
    @Mapping(source = "zoneId", target = "zone")
    AffectationZone toEntity(AffectationZoneDTO affectationZoneDTO);

    default AffectationZone fromId(Long id) {
        if (id == null) {
            return null;
        }
        AffectationZone affectationZone = new AffectationZone();
        affectationZone.setId(id);
        return affectationZone;
    }
}
