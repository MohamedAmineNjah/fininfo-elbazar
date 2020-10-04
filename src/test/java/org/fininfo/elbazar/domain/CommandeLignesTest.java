package org.fininfo.elbazar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class CommandeLignesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeLignes.class);
        CommandeLignes commandeLignes1 = new CommandeLignes();
        commandeLignes1.setId(1L);
        CommandeLignes commandeLignes2 = new CommandeLignes();
        commandeLignes2.setId(commandeLignes1.getId());
        assertThat(commandeLignes1).isEqualTo(commandeLignes2);
        commandeLignes2.setId(2L);
        assertThat(commandeLignes1).isNotEqualTo(commandeLignes2);
        commandeLignes1.setId(null);
        assertThat(commandeLignes1).isNotEqualTo(commandeLignes2);
    }
}
