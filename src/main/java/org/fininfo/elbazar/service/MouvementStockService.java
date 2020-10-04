package org.fininfo.elbazar.service;

import org.fininfo.elbazar.domain.MouvementStock;
import org.fininfo.elbazar.domain.Stock;
import org.fininfo.elbazar.domain.enumeration.TypeMvt;
import org.fininfo.elbazar.repository.MouvementStockRepository;
import org.fininfo.elbazar.repository.StockRepository;
import org.fininfo.elbazar.repository.search.MouvementStockSearchRepository;
import org.fininfo.elbazar.service.dto.MouvementStockDTO;
import org.fininfo.elbazar.service.mapper.MouvementStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MouvementStock}.
 */
@Service
@Transactional
public class MouvementStockService {

	private final Logger log = LoggerFactory.getLogger(MouvementStockService.class);

	private final MouvementStockRepository mouvementStockRepository;

	private final MouvementStockMapper mouvementStockMapper;

	private final MouvementStockSearchRepository mouvementStockSearchRepository;

	private final StockService stockService;

	private final StockRepository stockRepository;


	public MouvementStockService(MouvementStockRepository mouvementStockRepository,
			MouvementStockMapper mouvementStockMapper, MouvementStockSearchRepository mouvementStockSearchRepository,
			StockService stockService, StockRepository stockRepository) {
		this.mouvementStockRepository = mouvementStockRepository;
		this.stockRepository = stockRepository;
		this.mouvementStockMapper = mouvementStockMapper;
		this.mouvementStockSearchRepository = mouvementStockSearchRepository;
		this.stockService = stockService;
	}

	/**
	 * Save a mouvementStock.
	 *
	 * @param mouvementStockDTO the entity to save.
	 * @return the persisted entity.
	 */
	public MouvementStockDTO save(MouvementStockDTO mouvementStockDTO) {
		log.debug("Request to save MouvementStock : {}", mouvementStockDTO);
		MouvementStock mouvementStock = mouvementStockMapper.toEntity(mouvementStockDTO);
		checkStock(mouvementStock);
		mouvementStock = mouvementStockRepository.save(mouvementStock);
		stockService.updateStockFromMvnt(mouvementStock);
		MouvementStockDTO result = mouvementStockMapper.toDto(mouvementStock);
		mouvementStockSearchRepository.save(mouvementStock);
		return result;
	}

	private void checkStock(MouvementStock mouvementStock) {
        Optional<Stock> optionalStock = stockRepository.findOneByrefProduit(mouvementStock.getRefProduit());
        Stock stock = (optionalStock.isPresent() ? optionalStock.get() : null);
		if(mouvementStock.getId() == null && mouvementStock.getType() == TypeMvt.SortieStock ) {
			if ( mouvementStock.getQuantite() > stock.getStockDisponible()) {
				throw new StockDisponibleNegatifException();
			}
		}
	}
	
	/**
	 * Get all the mouvementStocks.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<MouvementStockDTO> findAll(Pageable pageable) {
		log.debug("Request to get all MouvementStocks");
		return mouvementStockRepository.findAll(pageable).map(mouvementStockMapper::toDto);
	}

	/**
	 * Get one mouvementStock by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<MouvementStockDTO> findOne(Long id) {
		log.debug("Request to get MouvementStock : {}", id);
		return mouvementStockRepository.findById(id).map(mouvementStockMapper::toDto);
	}

	/**
	 * Delete the mouvementStock by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete MouvementStock : {}", id);

		mouvementStockRepository.deleteById(id);
		mouvementStockSearchRepository.deleteById(id);
	}

	/**
	 * Search for the mouvementStock corresponding to the query.
	 *
	 * @param query    the query of the search.
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<MouvementStockDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of MouvementStocks for query {}", query);
		return mouvementStockSearchRepository.search(queryStringQuery(query), pageable)
				.map(mouvementStockMapper::toDto);
	}
}
