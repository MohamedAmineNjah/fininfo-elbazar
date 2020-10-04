package org.fininfo.elbazar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class AffectationZoneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AffectationZone.class);
        AffectationZone affectationZone1 = new AffectationZone();
        affectationZone1.setId(1L);
        AffectationZone affectationZone2 = new AffectationZone();
        affectationZone2.setId(affectationZone1.getId());
        assertThat(affectationZone1).isEqualTo(affectationZone2);
        affectationZone2.setId(2L);
        assertThat(affectationZone1).isNotEqualTo(affectationZone2);
        affectationZone1.setId(null);
        assertThat(affectationZone1).isNotEqualTo(affectationZone2);
    }
}
