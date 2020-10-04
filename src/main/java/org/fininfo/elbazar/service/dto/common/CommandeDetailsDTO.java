package org.fininfo.elbazar.service.dto.common;

import java.util.List;

import org.fininfo.elbazar.service.dto.CommandeDTO;
import org.fininfo.elbazar.service.dto.CommandeLignesDTO;

public class CommandeDetailsDTO {

	private CommandeDTO commandeDTO;

	private List<CommandeLignesHeaderDTO> commandeLignesHeaderDTO;	
	
	
	public List<CommandeLignesHeaderDTO> getCommandeLignesDTO() {
		return commandeLignesHeaderDTO;
	}

	public void setCommandeLignesDTO(List<CommandeLignesHeaderDTO> commandeLignesHeaderDTO) {
		this.commandeLignesHeaderDTO = commandeLignesHeaderDTO;
	}

	public CommandeDTO getCommandeDTO() {
		return commandeDTO;
	}

	public void setCommandeDTO(CommandeDTO commandeDTO) {
		this.commandeDTO = commandeDTO;
	}
		
}
