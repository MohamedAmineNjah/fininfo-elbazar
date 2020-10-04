package org.fininfo.elbazar.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProduitUniteMapperTest {

    private ProduitUniteMapper produitUniteMapper;

    @BeforeEach
    public void setUp() {
        produitUniteMapper = new ProduitUniteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(produitUniteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(produitUniteMapper.fromId(null)).isNull();
    }
}
