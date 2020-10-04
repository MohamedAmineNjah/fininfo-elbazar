package org.fininfo.elbazar.repository.search;

import org.fininfo.elbazar.domain.Slides;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Slides} entity.
 */
public interface SlidesSearchRepository extends ElasticsearchRepository<Slides, Long> {
}
