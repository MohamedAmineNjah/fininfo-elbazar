package org.fininfo.elbazar.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SlidesSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SlidesSearchRepositoryMockConfiguration {

    @MockBean
    private SlidesSearchRepository mockSlidesSearchRepository;

}
