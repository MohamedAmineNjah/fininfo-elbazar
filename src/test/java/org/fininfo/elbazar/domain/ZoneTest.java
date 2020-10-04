package org.fininfo.elbazar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class ZoneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zone.class);
        Zone zone1 = new Zone();
        zone1.setId(1L);
        Zone zone2 = new Zone();
        zone2.setId(zone1.getId());
        assertThat(zone1).isEqualTo(zone2);
        zone2.setId(2L);
        assertThat(zone1).isNotEqualTo(zone2);
        zone1.setId(null);
        assertThat(zone1).isNotEqualTo(zone2);
    }
}
