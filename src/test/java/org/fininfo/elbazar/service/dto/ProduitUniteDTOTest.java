package org.fininfo.elbazar.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class ProduitUniteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduitUniteDTO.class);
        ProduitUniteDTO produitUniteDTO1 = new ProduitUniteDTO();
        produitUniteDTO1.setId(1L);
        ProduitUniteDTO produitUniteDTO2 = new ProduitUniteDTO();
        assertThat(produitUniteDTO1).isNotEqualTo(produitUniteDTO2);
        produitUniteDTO2.setId(produitUniteDTO1.getId());
        assertThat(produitUniteDTO1).isEqualTo(produitUniteDTO2);
        produitUniteDTO2.setId(2L);
        assertThat(produitUniteDTO1).isNotEqualTo(produitUniteDTO2);
        produitUniteDTO1.setId(null);
        assertThat(produitUniteDTO1).isNotEqualTo(produitUniteDTO2);
    }
}
