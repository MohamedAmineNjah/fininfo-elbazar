package org.fininfo.elbazar.repository.search;

import org.fininfo.elbazar.domain.Livraison;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Livraison} entity.
 */
public interface LivraisonSearchRepository extends ElasticsearchRepository<Livraison, Long> {
}
