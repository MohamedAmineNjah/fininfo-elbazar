package org.fininfo.elbazar.service;

import org.fininfo.elbazar.domain.Zone;
import org.fininfo.elbazar.repository.ZoneRepository;
import org.fininfo.elbazar.repository.search.ZoneSearchRepository;
import org.fininfo.elbazar.service.dto.ZoneDTO;
import org.fininfo.elbazar.service.mapper.ZoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Zone}.
 */
@Service
@Transactional
public class ZoneService {

	private final Logger log = LoggerFactory.getLogger(ZoneService.class);

	private final ZoneRepository zoneRepository;

	private final ZoneMapper zoneMapper;

	private final ZoneSearchRepository zoneSearchRepository;

	public ZoneService(ZoneRepository zoneRepository, ZoneMapper zoneMapper,
			ZoneSearchRepository zoneSearchRepository) {
		this.zoneRepository = zoneRepository;
		this.zoneMapper = zoneMapper;
		this.zoneSearchRepository = zoneSearchRepository;
	}

	/**
	 * Save a zone.
	 *
	 * @param zoneDTO the entity to save.
	 * @return the persisted entity.
	 */
	public ZoneDTO save(ZoneDTO zoneDTO) {
		log.debug("Request to save Zone : {}", zoneDTO);
		Zone zone = zoneMapper.toEntity(zoneDTO);
		if (zoneDTO.getId() == null)
			zoneRepository.findOneByCodeZone(zoneDTO.getCodeZone()).ifPresent(existingZone -> {
				throw new ZoneAlreadyUsedException();
			});
		zone = zoneRepository.save(zone);
		ZoneDTO result = zoneMapper.toDto(zone);
		zoneSearchRepository.save(zone);
		return result;
	}

	/**
	 * Get all the zones.
	 *
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public List<ZoneDTO> findAll() {
		log.debug("Request to get all Zones");
		return zoneRepository.findAll().stream().map(zoneMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one zone by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<ZoneDTO> findOne(Long id) {
		log.debug("Request to get Zone : {}", id);
		return zoneRepository.findById(id).map(zoneMapper::toDto);
	}

	/**
	 * Delete the zone by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Zone : {}", id);

		zoneRepository.deleteById(id);
		zoneSearchRepository.deleteById(id);
	}

	/**
	 * Search for the zone corresponding to the query.
	 *
	 * @param query the query of the search.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public List<ZoneDTO> search(String query) {
		log.debug("Request to search Zones for query {}", query);
		return StreamSupport.stream(zoneSearchRepository.search(queryStringQuery(query)).spliterator(), false)
				.map(zoneMapper::toDto).collect(Collectors.toList());
	}
}
