package org.fininfo.elbazar.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdresseMapperTest {

    private AdresseMapper adresseMapper;

    @BeforeEach
    public void setUp() {
        adresseMapper = new AdresseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adresseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adresseMapper.fromId(null)).isNull();
    }
}
