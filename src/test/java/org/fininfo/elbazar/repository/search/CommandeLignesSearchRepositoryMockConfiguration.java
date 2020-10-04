package org.fininfo.elbazar.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CommandeLignesSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CommandeLignesSearchRepositoryMockConfiguration {

    @MockBean
    private CommandeLignesSearchRepository mockCommandeLignesSearchRepository;

}
