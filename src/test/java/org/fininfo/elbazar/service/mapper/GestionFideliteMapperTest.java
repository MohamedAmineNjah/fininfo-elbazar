package org.fininfo.elbazar.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GestionFideliteMapperTest {

    private GestionFideliteMapper gestionFideliteMapper;

    @BeforeEach
    public void setUp() {
        gestionFideliteMapper = new GestionFideliteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(gestionFideliteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(gestionFideliteMapper.fromId(null)).isNull();
    }
}
