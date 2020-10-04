package org.fininfo.elbazar.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class InfoSocieteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoSocieteDTO.class);
        InfoSocieteDTO infoSocieteDTO1 = new InfoSocieteDTO();
        infoSocieteDTO1.setId(1L);
        InfoSocieteDTO infoSocieteDTO2 = new InfoSocieteDTO();
        assertThat(infoSocieteDTO1).isNotEqualTo(infoSocieteDTO2);
        infoSocieteDTO2.setId(infoSocieteDTO1.getId());
        assertThat(infoSocieteDTO1).isEqualTo(infoSocieteDTO2);
        infoSocieteDTO2.setId(2L);
        assertThat(infoSocieteDTO1).isNotEqualTo(infoSocieteDTO2);
        infoSocieteDTO1.setId(null);
        assertThat(infoSocieteDTO1).isNotEqualTo(infoSocieteDTO2);
    }
}
