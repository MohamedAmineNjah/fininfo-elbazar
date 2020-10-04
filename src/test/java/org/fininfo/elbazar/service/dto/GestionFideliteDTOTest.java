package org.fininfo.elbazar.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class GestionFideliteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GestionFideliteDTO.class);
        GestionFideliteDTO gestionFideliteDTO1 = new GestionFideliteDTO();
        gestionFideliteDTO1.setId(1L);
        GestionFideliteDTO gestionFideliteDTO2 = new GestionFideliteDTO();
        assertThat(gestionFideliteDTO1).isNotEqualTo(gestionFideliteDTO2);
        gestionFideliteDTO2.setId(gestionFideliteDTO1.getId());
        assertThat(gestionFideliteDTO1).isEqualTo(gestionFideliteDTO2);
        gestionFideliteDTO2.setId(2L);
        assertThat(gestionFideliteDTO1).isNotEqualTo(gestionFideliteDTO2);
        gestionFideliteDTO1.setId(null);
        assertThat(gestionFideliteDTO1).isNotEqualTo(gestionFideliteDTO2);
    }
}
