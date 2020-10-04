package org.fininfo.elbazar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Elbazar.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    public final Imageproduit imageproduit = new Imageproduit();

    public Imageproduit getImageproduit() {
        return imageproduit;
    }

    public static class Imageproduit {

        private String path = "";

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

}
