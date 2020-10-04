package org.fininfo.elbazar.config;

import io.github.jhipster.config.JHipsterDefaults;
import io.github.jhipster.config.JHipsterProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.CacheControl;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.concurrent.TimeUnit;

import static org.fininfo.elbazar.config.StaticResourcesWebConfiguration.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class StaticResourcesWebConfigurerTest {
    public static final int MAX_AGE_TEST = 5;
    public StaticResourcesWebConfiguration staticResourcesWebConfiguration;
    private ResourceHandlerRegistry resourceHandlerRegistry;
    private MockServletContext servletContext;
    private WebApplicationContext applicationContext;
    private JHipsterProperties props;
    private ApplicationProperties appProps;

    @BeforeEach
    void setUp() {
        servletContext = spy(new MockServletContext());
        applicationContext = mock(WebApplicationContext.class);
        resourceHandlerRegistry = spy(new ResourceHandlerRegistry(applicationContext, servletContext));
        props = new JHipsterProperties();
        staticResourcesWebConfiguration = spy(new StaticResourcesWebConfiguration(props, appProps));
    }

   

 


    @Test
    public void shoudCreateCacheControlBasedOnJhipsterDefaultProperties() {
        CacheControl cacheExpected = CacheControl.maxAge(JHipsterDefaults.Http.Cache.timeToLiveInDays, TimeUnit.DAYS).cachePublic();
        assertThat(staticResourcesWebConfiguration.getCacheControl())
            .extracting(CacheControl::getHeaderValue)
            .isEqualTo(cacheExpected.getHeaderValue());
    }

    @Test
    public void shoudCreateCacheControlWithSpecificConfigurationInProperties() {
        props.getHttp().getCache().setTimeToLiveInDays(MAX_AGE_TEST);
        CacheControl cacheExpected = CacheControl.maxAge(MAX_AGE_TEST, TimeUnit.DAYS).cachePublic();
        assertThat(staticResourcesWebConfiguration.getCacheControl())
            .extracting(CacheControl::getHeaderValue)
            .isEqualTo(cacheExpected.getHeaderValue());
    }
}
