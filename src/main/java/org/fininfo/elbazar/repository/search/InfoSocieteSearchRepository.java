package org.fininfo.elbazar.repository.search;

import org.fininfo.elbazar.domain.InfoSociete;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link InfoSociete} entity.
 */
public interface InfoSocieteSearchRepository extends ElasticsearchRepository<InfoSociete, Long> {
}
