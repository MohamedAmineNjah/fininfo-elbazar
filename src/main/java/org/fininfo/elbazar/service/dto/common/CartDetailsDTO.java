package org.fininfo.elbazar.service.dto.common;

import java.util.List;

import org.fininfo.elbazar.service.dto.CommandeDTO;
import org.fininfo.elbazar.service.dto.CommandeLignesDTO;

public class CartDetailsDTO {

    private CommandeDTO commandeDTO;
    private List<CommandeLignesDTO> commandeLignes;

    public CommandeDTO getCommandeDTO() {
        return commandeDTO;
    }

    public void setCommandeDTO(CommandeDTO commandeDTO) {
        this.commandeDTO = commandeDTO;
    }

    public List<CommandeLignesDTO> getCommandeLignes() {
        return commandeLignes;
    }

    public void setCommandeLignes(List<CommandeLignesDTO> commandeLignes) {
        this.commandeLignes = commandeLignes;
    }

}