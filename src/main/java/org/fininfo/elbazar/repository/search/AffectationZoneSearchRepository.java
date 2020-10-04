package org.fininfo.elbazar.repository.search;

import org.fininfo.elbazar.domain.AffectationZone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AffectationZone} entity.
 */
public interface AffectationZoneSearchRepository extends ElasticsearchRepository<AffectationZone, Long> {
}
