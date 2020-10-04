package org.fininfo.elbazar.repository.search;

import org.fininfo.elbazar.domain.ProduitUnite;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ProduitUnite} entity.
 */
public interface ProduitUniteSearchRepository extends ElasticsearchRepository<ProduitUnite, Long> {
}
