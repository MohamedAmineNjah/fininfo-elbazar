package org.fininfo.elbazar.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class LivraisonDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LivraisonDTO.class);
        LivraisonDTO livraisonDTO1 = new LivraisonDTO();
        livraisonDTO1.setId(1L);
        LivraisonDTO livraisonDTO2 = new LivraisonDTO();
        assertThat(livraisonDTO1).isNotEqualTo(livraisonDTO2);
        livraisonDTO2.setId(livraisonDTO1.getId());
        assertThat(livraisonDTO1).isEqualTo(livraisonDTO2);
        livraisonDTO2.setId(2L);
        assertThat(livraisonDTO1).isNotEqualTo(livraisonDTO2);
        livraisonDTO1.setId(null);
        assertThat(livraisonDTO1).isNotEqualTo(livraisonDTO2);
    }
}