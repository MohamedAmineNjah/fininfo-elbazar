package org.fininfo.elbazar.repository.search;

import org.fininfo.elbazar.domain.Categorie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Categorie} entity.
 */
public interface CategorieSearchRepository extends ElasticsearchRepository<Categorie, Long> {
}
