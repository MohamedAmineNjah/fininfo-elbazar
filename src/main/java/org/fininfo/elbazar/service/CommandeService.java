package org.fininfo.elbazar.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.domain.CommandeLignes;
import org.fininfo.elbazar.domain.Stock;
import org.fininfo.elbazar.domain.enumeration.StatCmd;
import org.fininfo.elbazar.repository.ClientRepository;
import org.fininfo.elbazar.repository.CommandeLignesRepository;
import org.fininfo.elbazar.repository.CommandeRepository;
import org.fininfo.elbazar.repository.StockRepository;
import org.fininfo.elbazar.repository.UserRepository;
import org.fininfo.elbazar.repository.search.CommandeLignesSearchRepository;
import org.fininfo.elbazar.repository.search.CommandeSearchRepository;
import org.fininfo.elbazar.service.dto.ClientDTO;
import org.fininfo.elbazar.service.dto.CommandeDTO;
import org.fininfo.elbazar.service.dto.common.CommandeClientDTO;
import org.fininfo.elbazar.service.dto.CommandeLignesDTO;
import org.fininfo.elbazar.service.dto.common.CartDetailsDTO;
import org.fininfo.elbazar.service.dto.common.CommandeDetailsDTO;
import org.fininfo.elbazar.service.dto.common.CommandeHeaderDTO;
import org.fininfo.elbazar.service.dto.common.CommandeHeaderProjection;
import org.fininfo.elbazar.service.dto.common.CommandeLignesHeaderDTO;
import org.fininfo.elbazar.service.dto.common.CommandeLignesHeaderProjection;
import org.fininfo.elbazar.service.mapper.CommandeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import liquibase.pro.packaged.c;

import java.util.Optional;
import static org.elasticsearch.index.query.QueryBuilders.*;
/**
 * Service Implementation for managing {@link Commande}.
 */
@Service
@Transactional
public class CommandeService {

	private final Logger log = LoggerFactory.getLogger(CommandeService.class);

	private final CommandeRepository commandeRepository;

	private final CommandeMapper commandeMapper;

	private final CommandeSearchRepository commandeSearchRepository;

	private final CommandeLignesRepository commandeLignesRepository;

	private final ClientRepository clientRepository;

	private final StockRepository stockRepository;
	
	private final CommandeLignesSearchRepository commandeLignesSearchRepository;

	private final CommandeLignesService commandeLignesService;

	private final ClientService clientService;

	private final StockService stockService;

	private final MailService mailService;

	private final UserRepository userRepository;


	public CommandeService(CommandeRepository commandeRepository, CommandeMapper commandeMapper,
			CommandeSearchRepository commandeSearchRepository, CommandeLignesRepository commandeLignesRepository,
			StockService stockService, ClientRepository clientRepository, CommandeLignesSearchRepository commandeLignesSearchRepository, 
			CommandeLignesService commandeLignesService, ClientService clientService, StockRepository stockRepository,
			 MailService mailService, UserRepository userRepository) {
		this.commandeRepository = commandeRepository;
		this.commandeMapper = commandeMapper;
		this.commandeSearchRepository = commandeSearchRepository;
		this.commandeLignesRepository = commandeLignesRepository;
		this.commandeLignesSearchRepository = commandeLignesSearchRepository;
		this.stockService = stockService;
		this.clientRepository = clientRepository;
		this.stockRepository = stockRepository;
		this.clientService = clientService;
		this.commandeLignesService = commandeLignesService;
		this.mailService = mailService;
		this.userRepository = userRepository;
	}

	/**
	 * Save a commande.
	 *
	 * @param commandeDTO the entity to save.
	 * @return the persisted entity.
	 */
	public CommandeDTO save(CommandeDTO commandeDTO) {
		log.debug("Request to save Commande : {}", commandeDTO);
		Commande commande = commandeMapper.toEntity(commandeDTO);
		Optional<Commande> commandeBeforeSaveOpt = (commandeDTO.getId() != null
				? commandeRepository.findById(commandeDTO.getId())
				: Optional.empty());
		Commande commandeBeforeSave = (commandeBeforeSaveOpt.isPresent() ? commandeBeforeSaveOpt.get() : null);
		StatCmd cmdStatusBefore = (commandeBeforeSave != null ? commandeBeforeSave.getStatut() : StatCmd.Reservee);
		if (commande.getStatut() == StatCmd.Livree) {
			clientService.updateTotalAchat(commande);
		}
		commande = commandeRepository.save(commande);
		manageStockUpdate(commande, cmdStatusBefore);
		CommandeDTO result = commandeMapper.toDto(commande);
		commandeSearchRepository.save(commande);
		return result;
	}

	private void manageStockUpdate(Commande cmd, StatCmd cmdStatusBefore) {
		StatCmd cmdStatus = cmd.getStatut();
		if (cmd.getStatut().equals(StatCmd.Commandee) || cmd.getStatut().equals(StatCmd.Livraison) || cmd.getStatut().equals(StatCmd.Annulee)) {
			List<CommandeLignes> commandeLignes = commandeLignesRepository.findByRefCommande(cmd);
			for (CommandeLignes cmlignes : commandeLignes) {
				stockService.updateStockFromCmdLignes(cmlignes, cmdStatus, cmdStatusBefore);
			}
		}
	}

	// private void manageStockCheck(Commande cmd, StatCmd cmdStatusBefore) {
	// 	StatCmd cmdStatus = cmd.getStatut();
	// 	if (cmdStatus.equals(StatCmd.Commandee)) {
	// 		List<CommandeLignes> commandeLignes = commandeLignesRepository.findByRefCommande(cmd);
	// 		Integer flag = 0;
	// 		for (CommandeLignes cmlignes : commandeLignes) {
	// 			Optional <Stock> optionalStock = stockRepository.findOneByrefProduit(cmlignes.getRefProduit());
	// 			Stock stock = (optionalStock.isPresent() ? optionalStock.get() : null);
	// 			if(stock != null && stock.getStockDisponible() )
	// 			stockService.updateStockFromCmdLignes(cmlignes, cmdStatus, cmdStatusBefore);
	// 		}
	// 	}
	// }

	/**
	 * Get all the commandes.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<CommandeDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Commandes");
		return commandeRepository.findAll(pageable).map(commandeMapper::toDto);
	}

	/**
	 * Get one commande by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<CommandeDTO> findOne(Long id) {
		log.debug("Request to get Commande : {}", id);
		return commandeRepository.findById(id).map(commandeMapper::toDto);
	}

	/**
	 * Delete the commande by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Commande : {}", id);

		commandeRepository.deleteById(id);
		commandeSearchRepository.deleteById(id);
	}

	/************************** FININFO START ************************* */

	@Transactional(readOnly = true)
	public Optional<CommandeDetailsDTO> findClientbyCommande(Long id) {
		log.debug("Request to get Client by Commande Id : {}", id);
		List<CommandeLignesHeaderDTO> commandeLignesHeaderDTOs = new ArrayList<>();
		CommandeDetailsDTO commandeDetailsDTO = new CommandeDetailsDTO();
		
		Optional<Commande> commandeOpt  = commandeRepository.findById(id);
		Commande commande = (commandeOpt.isPresent() ? commandeOpt.get() : null);
		CommandeDTO result = commandeMapper.toDto(commande);

		List<CommandeLignesHeaderProjection> listCommandeLignes = commandeLignesRepository.findAllByrefCommande(id);

		for (CommandeLignesHeaderProjection comLignesHead : listCommandeLignes) {
			CommandeLignesHeaderDTO commandeLigneHeaderDTO = new CommandeLignesHeaderDTO();
			commandeLigneHeaderDTO.setId(comLignesHead.getId());
			commandeLigneHeaderDTO.setNomProduit(comLignesHead.getnom());
			commandeLigneHeaderDTO.setPrixHT(comLignesHead.getPrix_HT());
			commandeLigneHeaderDTO.setPrixTTC(comLignesHead.getPrix_TTC());
			commandeLigneHeaderDTO.setQuantite(comLignesHead.getQuantite());
			commandeLigneHeaderDTO.setRefCommandeId(comLignesHead.getRef_Commande_Id());
			commandeLigneHeaderDTO.setRefProduitId(comLignesHead.getRef_Produit_Id());
			commandeLigneHeaderDTO.setRefProduitReference(comLignesHead.getReference());
			commandeLigneHeaderDTO.setRemise(comLignesHead.getRemise());
			commandeLigneHeaderDTO.setTva(comLignesHead.getTva());
			commandeLignesHeaderDTOs.add(commandeLigneHeaderDTO);
		}

		commandeDetailsDTO.setCommandeDTO(result);
		commandeDetailsDTO.setCommandeLignesDTO(commandeLignesHeaderDTOs);

		return Optional.of(commandeDetailsDTO);

	}

	/************************** FININFO END *************************** */

	/**
	 * Search for the commande corresponding to the query.
	 *
	 * @param query    the query of the search.
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<CommandeDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Commandes for query {}", query);
		return commandeSearchRepository.search(queryStringQuery(query), pageable).map(commandeMapper::toDto);
	}

	/********************* FININFO CODE: START ********************* */
		// Get all client commandes by Client ID
	@Transactional(readOnly = true)
	public List<CommandeClientDTO> findCommandeByClient(Long clt_id) {
		return commandeRepository.findCommandeByClient(clt_id);
	}

		// Get all client commandes by User_Login
	@Transactional(readOnly = true)
	public List<CommandeClientDTO> findCommandeByLogin(String login) {
		return commandeRepository.findCommandeByLogin(login);
	}
	
		// Create Cart details : Creat commandes, commandes-Lignes, update client categorie and pointFidelite
	 public CommandeDTO creatCart(CartDetailsDTO cartDetailsDTO) {

		CommandeDTO commandeDTO = cartDetailsDTO.getCommandeDTO();
		List<CommandeLignesDTO> commandeLignes = cartDetailsDTO.getCommandeLignes();
		Commande commande = commandeMapper.toEntity(commandeDTO);
		CommandeDTO rst = this.save(commandeDTO);

		// TO IMPROVE : GENERATE COMMANDE REF
		Long id = rst.getId();
		Integer year = LocalDate.now().getYear();
		String ref = Integer.toString(year) + Long.toString(id);
		rst.setReference("CMD" + ref);
		CommandeDTO referencedCommande = this.save(rst);
		// TO IMPROVE : GENERATE COMMANDE REF

		// clientService.updateTotalAchat(commande);
		commandeLignesService.saveCmdLignesList(commandeLignes, referencedCommande);
		CommandeDTO commandeResult = rst;
		// clientDTO.setTotalAchat(commande.getTotTTC());
		Optional<ClientDTO> commandeClientOpt = clientService.findOne(commandeResult.getIdClientId());
		ClientDTO commandClient = (commandeClientOpt.isPresent() ? commandeClientOpt.get() : null);

		userRepository.findOneByLogin(commandClient.getUserLogin().toLowerCase()).ifPresent(existingUser -> {
			mailService.sendCommandeConfirmation(existingUser, commandeResult, commandeLignes);
		});
	return commandeResult;
	 }
	/************************** FININFO END *************************** */
}
