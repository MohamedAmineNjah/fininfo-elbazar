package org.fininfo.elbazar.repository.search;

import org.fininfo.elbazar.domain.Stock;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Stock} entity.
 */
public interface StockSearchRepository extends ElasticsearchRepository<Stock, Long> {
}
