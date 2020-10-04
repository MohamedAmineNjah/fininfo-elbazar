package org.fininfo.elbazar.service.mapper;


import org.fininfo.elbazar.domain.*;
import org.fininfo.elbazar.service.dto.CommandeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commande} and its DTO {@link CommandeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class, AdresseMapper.class, ZoneMapper.class})
public interface CommandeMapper extends EntityMapper<CommandeDTO, Commande> {

    @Mapping(source = "idClient.id", target = "idClientId")
    @Mapping(source = "idClient.nom", target = "nomClient")
    @Mapping(source = "idClient.prenom", target = "prenomClient")
    @Mapping(source = "idAdresse.id", target = "idAdresseId")
    @Mapping(source = "zone.id", target = "zoneId")
    @Mapping(source = "zone.nom", target = "zoneNom")
    CommandeDTO toDto(Commande commande);

    @Mapping(target = "mouvementStocks", ignore = true)
    @Mapping(target = "removeMouvementStock", ignore = true)
    @Mapping(target = "commandeLignes", ignore = true)
    @Mapping(target = "removeCommandeLignes", ignore = true)
    @Mapping(source = "idClientId", target = "idClient")
    @Mapping(source = "idAdresseId", target = "idAdresse")
    @Mapping(source = "zoneId", target = "zone")
    Commande toEntity(CommandeDTO commandeDTO);

    default Commande fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commande commande = new Commande();
        commande.setId(id);
        return commande;
    }
}
