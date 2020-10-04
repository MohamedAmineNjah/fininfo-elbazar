package org.fininfo.elbazar.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ProduitUniteSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ProduitUniteSearchRepositoryMockConfiguration {

    @MockBean
    private ProduitUniteSearchRepository mockProduitUniteSearchRepository;

}
