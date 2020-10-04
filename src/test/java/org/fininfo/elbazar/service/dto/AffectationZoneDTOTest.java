package org.fininfo.elbazar.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class AffectationZoneDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AffectationZoneDTO.class);
        AffectationZoneDTO affectationZoneDTO1 = new AffectationZoneDTO();
        affectationZoneDTO1.setId(1L);
        AffectationZoneDTO affectationZoneDTO2 = new AffectationZoneDTO();
        assertThat(affectationZoneDTO1).isNotEqualTo(affectationZoneDTO2);
        affectationZoneDTO2.setId(affectationZoneDTO1.getId());
        assertThat(affectationZoneDTO1).isEqualTo(affectationZoneDTO2);
        affectationZoneDTO2.setId(2L);
        assertThat(affectationZoneDTO1).isNotEqualTo(affectationZoneDTO2);
        affectationZoneDTO1.setId(null);
        assertThat(affectationZoneDTO1).isNotEqualTo(affectationZoneDTO2);
    }
}
