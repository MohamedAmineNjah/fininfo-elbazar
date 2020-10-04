package org.fininfo.elbazar.service.mapper;


import org.fininfo.elbazar.domain.*;
import org.fininfo.elbazar.service.dto.CommandeLignesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommandeLignes} and its DTO {@link CommandeLignesDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommandeMapper.class, ProduitMapper.class})
public interface CommandeLignesMapper extends EntityMapper<CommandeLignesDTO, CommandeLignes> {

    @Mapping(source = "refCommande.id", target = "refCommandeId")
    @Mapping(source = "refCommande.reference", target = "refCommandeReference")
    @Mapping(source = "refProduit.id", target = "refProduitId")
    @Mapping(source = "refProduit.reference", target = "refProduitReference")
    CommandeLignesDTO toDto(CommandeLignes commandeLignes);

    @Mapping(source = "refCommandeId", target = "refCommande")
    @Mapping(source = "refProduitId", target = "refProduit")
    CommandeLignes toEntity(CommandeLignesDTO commandeLignesDTO);

    default CommandeLignes fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommandeLignes commandeLignes = new CommandeLignes();
        commandeLignes.setId(id);
        return commandeLignes;
    }
}
