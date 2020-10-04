package org.fininfo.elbazar.repository.search;

import org.fininfo.elbazar.domain.MouvementStock;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link MouvementStock} entity.
 */
public interface MouvementStockSearchRepository extends ElasticsearchRepository<MouvementStock, Long> {
}
