package org.fininfo.elbazar.config;

import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.JHipsterProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@Profile({JHipsterConstants.SPRING_PROFILE_PRODUCTION})
public class StaticResourcesWebConfiguration implements WebMvcConfigurer {

    protected static final String[] RESOURCE_LOCATIONS = new String[]{"classpath:/static/app/", "classpath:/static/content/", "classpath:/static/i18n/"};
    protected static final String[] IMAGE_PRODUCT_RESOURCE_LOCATIONS = new String[]{""};
    protected static final String[] RESOURCE_PATHS = new String[]{"/app/*", "/content/*", "/i18n/*", "/media/*"};

    private final JHipsterProperties jhipsterProperties;

    private final ApplicationProperties applicationProperties;

    public StaticResourcesWebConfiguration(JHipsterProperties jHipsterProperties, ApplicationProperties applicationProperties) {
        this.jhipsterProperties = jHipsterProperties;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration resourceHandlerRegistration = appendResourceHandler(registry);
        initializeResourceHandler(resourceHandlerRegistration);
    }

    protected ResourceHandlerRegistration appendResourceHandler(ResourceHandlerRegistry registry) {
        return registry.addResourceHandler(RESOURCE_PATHS);
    }

    protected void initializeResourceHandler(ResourceHandlerRegistration resourceHandlerRegistration) {
        IMAGE_PRODUCT_RESOURCE_LOCATIONS[0] = "file:////"+applicationProperties.getImageproduit().getPath();
        resourceHandlerRegistration.addResourceLocations(RESOURCE_LOCATIONS).setCacheControl(getCacheControl());
        resourceHandlerRegistration.addResourceLocations(IMAGE_PRODUCT_RESOURCE_LOCATIONS);
    }

    protected CacheControl getCacheControl() {
        return CacheControl.maxAge(getJHipsterHttpCacheProperty(), TimeUnit.DAYS).cachePublic();
    }

    private int getJHipsterHttpCacheProperty() {
        return jhipsterProperties.getHttp().getCache().getTimeToLiveInDays();
    }

}
