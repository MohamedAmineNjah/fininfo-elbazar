package org.fininfo.elbazar.service;

import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.domain.CommandeLignes;
import org.fininfo.elbazar.domain.MouvementStock;
import org.fininfo.elbazar.domain.Stock;
import org.fininfo.elbazar.domain.enumeration.StatCmd;
import org.fininfo.elbazar.domain.enumeration.TypeMvt;
import org.fininfo.elbazar.repository.CommandeLignesRepository;
import org.fininfo.elbazar.repository.MouvementStockRepository;
import org.fininfo.elbazar.repository.StockRepository;
import org.fininfo.elbazar.repository.search.CommandeLignesSearchRepository;
import org.fininfo.elbazar.service.dto.CommandeDTO;
import org.fininfo.elbazar.service.dto.CommandeLignesDTO;
import org.fininfo.elbazar.service.dto.MouvementStockDTO;
import org.fininfo.elbazar.service.mapper.CommandeLignesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CommandeLignes}.
 */
@Service
@Transactional
public class CommandeLignesService {

    private final Logger log = LoggerFactory.getLogger(CommandeLignesService.class);

    private final CommandeLignesMapper commandeLignesMapper;

    private final CommandeLignesSearchRepository commandeLignesSearchRepository;
    
    private final StockRepository stockRepository;
    
    private final MouvementStockService mouvementStockService;

    private final CommandeLignesRepository commandeLignesRepository;

    public CommandeLignesService(CommandeLignesRepository commandeLignesRepository, CommandeLignesMapper commandeLignesMapper, 
    		CommandeLignesSearchRepository commandeLignesSearchRepository, StockRepository stockRepository,
    		MouvementStockService mouvementStockService) {
        this.commandeLignesRepository = commandeLignesRepository;
        this.commandeLignesMapper = commandeLignesMapper;
        this.commandeLignesSearchRepository = commandeLignesSearchRepository;
        this.stockRepository = stockRepository;
        this.mouvementStockService = mouvementStockService;
    }

    /**
     * Save a commandeLignes.
     *
     * @param commandeLignesDTO the entity to save.
     * @return the persisted entity.
     */
    public CommandeLignesDTO save(CommandeLignesDTO commandeLignesDTO) {
        log.debug("Request to save CommandeLignes : {}", commandeLignesDTO);
        CommandeLignes commandeLignes = commandeLignesMapper.toEntity(commandeLignesDTO);
        commandeLignes = commandeLignesRepository.save(commandeLignes);
        createMvntStock(commandeLignes);
        CommandeLignesDTO result = commandeLignesMapper.toDto(commandeLignes);
        commandeLignesSearchRepository.save(commandeLignes);
        return result;
    }

    private void createMvntStock(CommandeLignes commandeLignes) {
		MouvementStockDTO mvntStockDto = new MouvementStockDTO();
		mvntStockDto.setId(null);
		mvntStockDto.setType(TypeMvt.Commande);
		mvntStockDto.setDate(LocalDate.now());
		mvntStockDto.setSens(-1);
		mvntStockDto.setQuantite(commandeLignes.getQuantite());
		mvntStockDto.setRefProduitId(commandeLignes.getRefProduit().getId());
		mvntStockDto.setRefProduitReference(commandeLignes.getRefProduit().getReference());
		mvntStockDto.setRefCommandeId((commandeLignes.getRefCommande() != null) ? commandeLignes.getRefCommande().getId() : null);
		mvntStockDto.setRefCommandeReference((commandeLignes.getRefCommande() != null)  ? commandeLignes.getRefCommande().getReference() : null);
		mvntStockDto.setCreeLe(LocalDate.now());
		mvntStockDto.setCreePar("Front Office");
		mvntStockDto.setModifieLe(LocalDate.now());
		mvntStockDto.setModifiePar("Front Office");
		mouvementStockService.save(mvntStockDto);
	}

	/**
     * Get all the commandeLignes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommandeLignesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommandeLignes");
        return commandeLignesRepository.findAll(pageable)
            .map(commandeLignesMapper::toDto);
    }


    /**
     * Get one commandeLignes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommandeLignesDTO> findOne(Long id) {
        log.debug("Request to get CommandeLignes : {}", id);
        return commandeLignesRepository.findById(id)
            .map(commandeLignesMapper::toDto);
    }

    /**
     * Delete the commandeLignes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommandeLignes : {}", id);

        commandeLignesRepository.deleteById(id);
        commandeLignesSearchRepository.deleteById(id);
    }

    /**
     * Search for the commandeLignes corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommandeLignesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommandeLignes for query {}", query);
        return commandeLignesSearchRepository.search(queryStringQuery(query), pageable)
            .map(commandeLignesMapper::toDto);
    }

    /************************** FININFO START ************************* */
    public Boolean saveCmdLignesList(List<CommandeLignesDTO> cmdLigneDTO, CommandeDTO commandeDTO) {
        log.debug("Request to save CommandeLignes : {}");
    	for (CommandeLignesDTO commandeLigneDTO : cmdLigneDTO) {
            commandeLigneDTO.setRefCommandeId(commandeDTO.getId()); 
             commandeLigneDTO.setRefCommandeReference(commandeDTO.getReference());
            commandeLigneDTO = this.save(commandeLigneDTO);
        }
        return true;
    }
	/************************** FININFO END   ************************* */


}
