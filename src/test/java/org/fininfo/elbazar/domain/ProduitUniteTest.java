package org.fininfo.elbazar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class ProduitUniteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduitUnite.class);
        ProduitUnite produitUnite1 = new ProduitUnite();
        produitUnite1.setId(1L);
        ProduitUnite produitUnite2 = new ProduitUnite();
        produitUnite2.setId(produitUnite1.getId());
        assertThat(produitUnite1).isEqualTo(produitUnite2);
        produitUnite2.setId(2L);
        assertThat(produitUnite1).isNotEqualTo(produitUnite2);
        produitUnite1.setId(null);
        assertThat(produitUnite1).isNotEqualTo(produitUnite2);
    }
}
