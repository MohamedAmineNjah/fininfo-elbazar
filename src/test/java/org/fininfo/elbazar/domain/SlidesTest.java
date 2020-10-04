package org.fininfo.elbazar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.elbazar.web.rest.TestUtil;

public class SlidesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Slides.class);
        Slides slides1 = new Slides();
        slides1.setId(1L);
        Slides slides2 = new Slides();
        slides2.setId(slides1.getId());
        assertThat(slides1).isEqualTo(slides2);
        slides2.setId(2L);
        assertThat(slides1).isNotEqualTo(slides2);
        slides1.setId(null);
        assertThat(slides1).isNotEqualTo(slides2);
    }
}
