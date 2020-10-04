package org.fininfo.elbazar.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SousCategorieMapperTest {

    private SousCategorieMapper sousCategorieMapper;

    @BeforeEach
    public void setUp() {
        sousCategorieMapper = new SousCategorieMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sousCategorieMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sousCategorieMapper.fromId(null)).isNull();
    }
}
