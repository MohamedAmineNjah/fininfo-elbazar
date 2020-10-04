package org.fininfo.elbazar.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MouvementStockMapperTest {

    private MouvementStockMapper mouvementStockMapper;

    @BeforeEach
    public void setUp() {
        mouvementStockMapper = new MouvementStockMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mouvementStockMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mouvementStockMapper.fromId(null)).isNull();
    }
}
