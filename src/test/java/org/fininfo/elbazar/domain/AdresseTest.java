package org.fininfo.elbazar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class AdresseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adresse.class);
        Adresse adresse1 = new Adresse();
        adresse1.setId(1L);
        Adresse adresse2 = new Adresse();
        adresse2.setId(adresse1.getId());
        assertThat(adresse1).isEqualTo(adresse2);
        adresse2.setId(2L);
        assertThat(adresse1).isNotEqualTo(adresse2);
        adresse1.setId(null);
        assertThat(adresse1).isNotEqualTo(adresse2);
    }
}
