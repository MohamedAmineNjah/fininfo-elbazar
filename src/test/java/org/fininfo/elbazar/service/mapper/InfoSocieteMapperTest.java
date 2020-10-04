package org.fininfo.elbazar.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InfoSocieteMapperTest {

    private InfoSocieteMapper infoSocieteMapper;

    @BeforeEach
    public void setUp() {
        infoSocieteMapper = new InfoSocieteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(infoSocieteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(infoSocieteMapper.fromId(null)).isNull();
    }
}
