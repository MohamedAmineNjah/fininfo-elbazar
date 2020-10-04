package org.fininfo.elbazar.service;

import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.domain.Commande;
import org.fininfo.elbazar.repository.ClientRepository;
import org.fininfo.elbazar.repository.CommandeRepository;
import org.fininfo.elbazar.repository.GestionFideliteRepository;
import org.fininfo.elbazar.domain.GestionFidelite;
import org.fininfo.elbazar.domain.User;
import org.fininfo.elbazar.domain.enumeration.ProfileClient;

import org.fininfo.elbazar.repository.UserRepository;
import org.fininfo.elbazar.repository.search.ClientSearchRepository;
import org.fininfo.elbazar.service.dto.ClientDTO;
import org.fininfo.elbazar.service.dto.CommandeDTO;
import org.fininfo.elbazar.service.mapper.ClientMapper;
import org.fininfo.elbazar.service.mapper.CommandeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final CommandeMapper commandeMapper;

    private final CommandeRepository commandeRepository;

    private final ClientSearchRepository clientSearchRepository;

    private final GestionFideliteRepository gestionFideliteRepository;

    private final UserRepository userRepository;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper, CommandeMapper commandeMapper, CommandeRepository commandeRepository,
            ClientSearchRepository clientSearchRepository, GestionFideliteRepository gestionFideliteRepository , UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.commandeMapper = commandeMapper;
        this.clientSearchRepository = clientSearchRepository;
        this.commandeRepository = commandeRepository;
        this.gestionFideliteRepository = gestionFideliteRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a client.
     *
     * @param clientDTO the entity to save.
     * @return the persisted entity.
     */
    public ClientDTO save(ClientDTO clientDTO) {
        log.debug("Request to save Client : {}", clientDTO);
        clientDTO.setCreeLe(((clientDTO.getCreeLe() == null)) ? LocalDate.now() : clientDTO.getCreeLe());
        clientDTO.setModifieLe(LocalDate.now()); // si modification
        Client client = clientMapper.toEntity(clientDTO);
        Long userId = clientDTO.getUserId();
        client = clientRepository.save(client);
        Optional<User> userOpt = userRepository.findById(userId);
		User user = (userOpt.isPresent() ? userOpt.get() : null);
        if (user != null) {
            user.setActivated(client.isEtat());
            userRepository.save(user);
        }
        ClientDTO result = clientMapper.toDto(client);
        clientSearchRepository.save(client);
        return result;
    }

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable).map(clientMapper::toDto);
    }



    /**
     * Get one client by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientDTO> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id).map(clientMapper::toDto);
    }

    /**
     * Delete the client by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);

        clientRepository.deleteById(id);
        clientSearchRepository.deleteById(id);
    }

    /**
     * Search for the client corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Clients for query {}", query);
        return clientSearchRepository.search(queryStringQuery(query), pageable).map(clientMapper::toDto);
    }
   
    /************************** FININFO START ************************* */
        // Update Point fidelité Client after Commande
    public Client updatepointfidelite(Commande commande, Client client) {
        Optional<GestionFidelite> activeF = gestionFideliteRepository.findByetatTrue();
        GestionFidelite formule = (activeF.isPresent() ? activeF.get() : null);
        if (formule == null) {
            throw new FormuleFideliteNotFoundException();
        }
        double valeur = formule.getValeur();
        int pfinitial = client.getPointsFidelite();
        int pointsC = (int) (commande.getTotTTC() / valeur);
        client.setPointsFidelite(pointsC + pfinitial);
        
        commande.setPointsFidelite(pointsC);
        commandeRepository.save(commande);

        client = categorieclient(client, formule);
        return client ;
    }
            
        // Update Catégorie client after Commande
    public Client categorieclient(Client client, GestionFidelite formule) {
        if (client.getPointsFidelite() > formule.getPlatiniumMin()) {
            client.setProfile(ProfileClient.Platinium);
        } else if (client.getPointsFidelite() > formule.getGoldMin()) {
            client.setProfile(ProfileClient.Gold);
        } else {
            client.setProfile(ProfileClient.Silver);
        }
        return client; 
    }
        // Update Total Achat, Point de fidelite & Catégorie Client
    public void updateTotalAchat(Commande commande) {
        Optional <Client> clientOpt = clientRepository.findById(commande.getIdClient().getId());
        Client client = (clientOpt.isPresent() ? clientOpt.get() : null);
        if (client != null){
           client.setTotalAchat(client.getTotalAchat() + commande.getTotTTC());
           client = updatepointfidelite(commande, client);
        clientRepository.save(client) ;
        }
    }
	/************************** FININFO END   ************************* */
}