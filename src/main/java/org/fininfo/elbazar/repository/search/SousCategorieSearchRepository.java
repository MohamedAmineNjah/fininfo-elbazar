package org.fininfo.elbazar.repository.search;

import org.fininfo.elbazar.domain.SousCategorie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link SousCategorie} entity.
 */
public interface SousCategorieSearchRepository extends ElasticsearchRepository<SousCategorie, Long> {
}
