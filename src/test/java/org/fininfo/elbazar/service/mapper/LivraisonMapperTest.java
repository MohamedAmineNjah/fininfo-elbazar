package org.fininfo.elbazar.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LivraisonMapperTest {

    private LivraisonMapper livraisonMapper;

    @BeforeEach
    public void setUp() {
        livraisonMapper = new LivraisonMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(livraisonMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(livraisonMapper.fromId(null)).isNull();
    }
}
