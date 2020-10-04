package org.fininfo.elbazar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class InfoSocieteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoSociete.class);
        InfoSociete infoSociete1 = new InfoSociete();
        infoSociete1.setId(1L);
        InfoSociete infoSociete2 = new InfoSociete();
        infoSociete2.setId(infoSociete1.getId());
        assertThat(infoSociete1).isEqualTo(infoSociete2);
        infoSociete2.setId(2L);
        assertThat(infoSociete1).isNotEqualTo(infoSociete2);
        infoSociete1.setId(null);
        assertThat(infoSociete1).isNotEqualTo(infoSociete2);
    }
}
