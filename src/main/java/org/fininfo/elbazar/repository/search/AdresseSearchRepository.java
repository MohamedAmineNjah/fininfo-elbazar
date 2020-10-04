package org.fininfo.elbazar.repository.search;

import org.fininfo.elbazar.domain.Adresse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Adresse} entity.
 */
public interface AdresseSearchRepository extends ElasticsearchRepository<Adresse, Long> {
}
