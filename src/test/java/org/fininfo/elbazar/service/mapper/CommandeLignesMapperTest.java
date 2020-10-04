package org.fininfo.elbazar.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommandeLignesMapperTest {

    private CommandeLignesMapper commandeLignesMapper;

    @BeforeEach
    public void setUp() {
        commandeLignesMapper = new CommandeLignesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commandeLignesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commandeLignesMapper.fromId(null)).isNull();
    }
}
