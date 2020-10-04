package org.fininfo.elbazar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class GestionFideliteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GestionFidelite.class);
        GestionFidelite gestionFidelite1 = new GestionFidelite();
        gestionFidelite1.setId(1L);
        GestionFidelite gestionFidelite2 = new GestionFidelite();
        gestionFidelite2.setId(gestionFidelite1.getId());
        assertThat(gestionFidelite1).isEqualTo(gestionFidelite2);
        gestionFidelite2.setId(2L);
        assertThat(gestionFidelite1).isNotEqualTo(gestionFidelite2);
        gestionFidelite1.setId(null);
        assertThat(gestionFidelite1).isNotEqualTo(gestionFidelite2);
    }
}
