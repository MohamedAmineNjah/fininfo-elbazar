package org.fininfo.elbazar.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AffectationZoneMapperTest {

    private AffectationZoneMapper affectationZoneMapper;

    @BeforeEach
    public void setUp() {
        affectationZoneMapper = new AffectationZoneMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(affectationZoneMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(affectationZoneMapper.fromId(null)).isNull();
    }
}
