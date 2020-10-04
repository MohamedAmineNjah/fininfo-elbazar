package org.fininfo.elbazar.service.mapper;


import org.fininfo.elbazar.domain.*;
import org.fininfo.elbazar.service.dto.AdresseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Adresse} and its DTO {@link AdresseDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class, ZoneMapper.class, AffectationZoneMapper.class})
public interface AdresseMapper extends EntityMapper<AdresseDTO, Adresse> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "zone.id", target = "zoneId")
    @Mapping(source = "zone.nom", target = "zoneNom")
    @Mapping(source = "codePostal.id", target = "codePostalId")
    @Mapping(source = "codePostal.codePostal", target = "codePostalCodePostal")
    AdresseDTO toDto(Adresse adresse);

    @Mapping(target = "commandes", ignore = true)
    @Mapping(target = "removeCommande", ignore = true)
    @Mapping(source = "clientId", target = "client")
    @Mapping(source = "zoneId", target = "zone")
    @Mapping(source = "codePostalId", target = "codePostal")
    Adresse toEntity(AdresseDTO adresseDTO);

    default Adresse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Adresse adresse = new Adresse();
        adresse.setId(id);
        return adresse;
    }
}
