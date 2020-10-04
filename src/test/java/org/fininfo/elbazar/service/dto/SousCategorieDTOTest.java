package org.fininfo.elbazar.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class SousCategorieDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SousCategorieDTO.class);
        SousCategorieDTO sousCategorieDTO1 = new SousCategorieDTO();
        sousCategorieDTO1.setId(1L);
        SousCategorieDTO sousCategorieDTO2 = new SousCategorieDTO();
        assertThat(sousCategorieDTO1).isNotEqualTo(sousCategorieDTO2);
        sousCategorieDTO2.setId(sousCategorieDTO1.getId());
        assertThat(sousCategorieDTO1).isEqualTo(sousCategorieDTO2);
        sousCategorieDTO2.setId(2L);
        assertThat(sousCategorieDTO1).isNotEqualTo(sousCategorieDTO2);
        sousCategorieDTO1.setId(null);
        assertThat(sousCategorieDTO1).isNotEqualTo(sousCategorieDTO2);
    }
}
