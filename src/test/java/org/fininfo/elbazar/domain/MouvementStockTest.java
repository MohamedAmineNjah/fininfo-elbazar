package org.fininfo.elbazar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class MouvementStockTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MouvementStock.class);
        MouvementStock mouvementStock1 = new MouvementStock();
        mouvementStock1.setId(1L);
        MouvementStock mouvementStock2 = new MouvementStock();
        mouvementStock2.setId(mouvementStock1.getId());
        assertThat(mouvementStock1).isEqualTo(mouvementStock2);
        mouvementStock2.setId(2L);
        assertThat(mouvementStock1).isNotEqualTo(mouvementStock2);
        mouvementStock1.setId(null);
        assertThat(mouvementStock1).isNotEqualTo(mouvementStock2);
    }
}
