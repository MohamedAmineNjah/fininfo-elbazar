package org.fininfo.elbazar.service;

import org.fininfo.elbazar.domain.CommandeLignes;
import org.fininfo.elbazar.domain.MouvementStock;
import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.domain.Stock;
import org.fininfo.elbazar.domain.enumeration.StatCmd;
import org.fininfo.elbazar.domain.enumeration.TypeMvt;
import org.fininfo.elbazar.repository.ProduitRepository;
import org.fininfo.elbazar.repository.StockRepository;
import org.fininfo.elbazar.repository.search.ProduitSearchRepository;
import org.fininfo.elbazar.repository.search.StockSearchRepository;
import org.fininfo.elbazar.service.dto.StockDTO;
import org.fininfo.elbazar.service.mapper.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Stock}.
 */
@Service
@Transactional
public class StockService {

	private final Logger log = LoggerFactory.getLogger(StockService.class);

	private final StockRepository stockRepository;

	private final StockMapper stockMapper;

	private final StockSearchRepository stockSearchRepository;

	private final ProduitRepository produitRepository;

	private final ProduitSearchRepository produitSearchRepository;

	public StockService(StockRepository stockRepository, StockMapper stockMapper,
			StockSearchRepository stockSearchRepository, ProduitRepository produitRepository,
			ProduitSearchRepository produitSearchRepository) {
		this.stockRepository = stockRepository;
		this.stockMapper = stockMapper;
		this.stockSearchRepository = stockSearchRepository;
		this.produitRepository = produitRepository;
		this.produitSearchRepository = produitSearchRepository;
	}

	/**
	 * Save a stock.
	 *
	 * @param stockDTO the entity to save.
	 * @return the persisted entity.
	 */
	public StockDTO save(StockDTO stockDTO) {
		log.debug("Request to save Stock : {}", stockDTO);
		Stock stock = stockMapper.toEntity(stockDTO);
		stock = stockRepository.save(stock);
		StockDTO result = stockMapper.toDto(stock);
		stockSearchRepository.save(stock);
		return result;
	}

	/**
	 * Get all the stocks.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<StockDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Stocks");
		return stockRepository.findAll(pageable).map(stockMapper::toDto);
	}

	/**
	 * Get one stock by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<StockDTO> findOne(Long id) {
		log.debug("Request to get Stock : {}", id);
		return stockRepository.findById(id).map(stockMapper::toDto);
	}

	/**
	 * Delete the stock by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Stock : {}", id);

		stockRepository.deleteById(id);
		stockSearchRepository.deleteById(id);
	}

	/**
	 * Search for the stock corresponding to the query.
	 *
	 * @param query    the query of the search.
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<StockDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Stocks for query {}", query);
		return stockSearchRepository.search(queryStringQuery(query), pageable).map(stockMapper::toDto);
	}

	public void updateStockFromMvnt(MouvementStock mouvementStock) {
		Optional<Stock> optionalStock = stockRepository.findOneByrefProduit(mouvementStock.getRefProduit());
		Stock stock = (optionalStock.isPresent() ? optionalStock.get() : null);

		Optional<Produit> optionalProduit = produitRepository.findById(mouvementStock.getRefProduit().getId());
		Produit produit = (optionalProduit.isPresent() ? optionalProduit.get() : null);
		if (stock != null) {

		if (mouvementStock.getType() == TypeMvt.EntreeStock) {
			stock.setStockPhysique(stock.getStockPhysique() + mouvementStock.getQuantite());

		}
		if (mouvementStock.getType() == TypeMvt.SortieStock) {
			stock.setStockPhysique(stock.getStockPhysique() - mouvementStock.getQuantite());
		}
		if (mouvementStock.getType() == TypeMvt.Commande) {
			stock.setStockReserve(stock.getStockReserve() + mouvementStock.getQuantite());
		}

		if (stock.getStockPhysique() > stock.getStockMinimum()) {
			stock.setAlerteStock(false); 
			if (produit != null) {
				produit.setHorsStock(false);
				produitRepository.save(produit);
				produitSearchRepository.save(produit);
			}
		}

		if (stock.getStockPhysique() <= stock.getStockMinimum()) {
			stock.setAlerteStock(true); 
			if (produit != null) {
				produit.setHorsStock(true);
				produitRepository.save(produit);
				produitSearchRepository.save(produit);
			}
		}

			stock.setStockDisponible(stock.getStockPhysique() - (stock.getStockCommande() + stock.getStockReserve()));
			stockRepository.save(stock);
			stockSearchRepository.save(stock);
		}
	}

	public void updateStockFromCmdLignes(CommandeLignes commandeLignes, StatCmd statCmd, StatCmd statCmdBefore) {
		Optional<Stock> optionalStock = stockRepository.findOneByrefProduit(commandeLignes.getRefProduit());
		Stock stock = (optionalStock.isPresent() ? optionalStock.get() : null);

		Optional<Produit> optionalProduit = produitRepository.findById(commandeLignes.getRefProduit().getId());
		Produit produit = (optionalProduit.isPresent() ? optionalProduit.get() : null);

		if (stock != null) {
		
		if (statCmd.equals(StatCmd.Commandee) && statCmdBefore.equals(StatCmd.Reservee)) {
			stock.setStockCommande(stock.getStockCommande() + commandeLignes.getQuantite());
			stock.setStockReserve(stock.getStockReserve() - commandeLignes.getQuantite());

		}
		if (statCmd.equals(StatCmd.Commandee) && statCmdBefore.equals(StatCmd.Livraison)) {
			stock.setStockCommande(stock.getStockCommande() + commandeLignes.getQuantite());
			stock.setStockPhysique(stock.getStockPhysique() + commandeLignes.getQuantite());

		}
		if (statCmd.equals(StatCmd.Livraison) && statCmdBefore.equals(StatCmd.Commandee)) {
			stock.setStockCommande(stock.getStockCommande() - commandeLignes.getQuantite());
			stock.setStockPhysique(stock.getStockPhysique() - commandeLignes.getQuantite());

		}
		if (statCmd.equals(StatCmd.Annulee) && statCmdBefore.equals(StatCmd.Reservee)) {
			stock.setStockReserve(stock.getStockReserve() - commandeLignes.getQuantite());

		}
		if (statCmd.equals(StatCmd.Annulee) && statCmdBefore.equals(StatCmd.Commandee)) {
			stock.setStockCommande(stock.getStockCommande() - commandeLignes.getQuantite());

		}

		if (stock.getStockPhysique() > stock.getStockMinimum()) {
			stock.setAlerteStock(false); 
			if (produit != null) {
				produit.setHorsStock(false);
				produitRepository.save(produit);
				produitSearchRepository.save(produit);
		} }

		if (stock.getStockPhysique() <= stock.getStockMinimum()) {
			stock.setAlerteStock(true); 
			if (produit != null) {
				produit.setHorsStock(true);
				produitRepository.save(produit);
				produitSearchRepository.save(produit);
		} }	
			stock.setStockDisponible(stock.getStockPhysique() - (stock.getStockCommande() + stock.getStockReserve()));
			
			stockRepository.save(stock);
			stockSearchRepository.save(stock);
		}

	}
}
