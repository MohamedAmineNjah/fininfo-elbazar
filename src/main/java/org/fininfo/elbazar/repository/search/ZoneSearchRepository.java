package org.fininfo.elbazar.repository.search;

import org.fininfo.elbazar.domain.Zone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Zone} entity.
 */
public interface ZoneSearchRepository extends ElasticsearchRepository<Zone, Long> {
}
