package org.fininfo.elbazar.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, org.fininfo.elbazar.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, org.fininfo.elbazar.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, org.fininfo.elbazar.domain.User.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.Authority.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.User.class.getName() + ".authorities");
            createCache(cm, org.fininfo.elbazar.domain.Categorie.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.Categorie.class.getName() + ".sousCategories");
            createCache(cm, org.fininfo.elbazar.domain.Categorie.class.getName() + ".produits");
            createCache(cm, org.fininfo.elbazar.domain.Categorie.class.getName() + ".stocks");
            createCache(cm, org.fininfo.elbazar.domain.SousCategorie.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.SousCategorie.class.getName() + ".produits");
            createCache(cm, org.fininfo.elbazar.domain.SousCategorie.class.getName() + ".stocks");
            createCache(cm, org.fininfo.elbazar.domain.Produit.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.Produit.class.getName() + ".stocks");
            createCache(cm, org.fininfo.elbazar.domain.Produit.class.getName() + ".mouvementStocks");
            createCache(cm, org.fininfo.elbazar.domain.Produit.class.getName() + ".commandeLignes");
            createCache(cm, org.fininfo.elbazar.domain.ProduitUnite.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.ProduitUnite.class.getName() + ".produits");
            createCache(cm, org.fininfo.elbazar.domain.Stock.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.MouvementStock.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.Client.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.Client.class.getName() + ".adresses");
            createCache(cm, org.fininfo.elbazar.domain.Client.class.getName() + ".commandes");
            createCache(cm, org.fininfo.elbazar.domain.Adresse.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.Adresse.class.getName() + ".commandes");
            createCache(cm, org.fininfo.elbazar.domain.GestionFidelite.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.Commande.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.Commande.class.getName() + ".mouvementStocks");
            createCache(cm, org.fininfo.elbazar.domain.Commande.class.getName() + ".commandeLignes");
            createCache(cm, org.fininfo.elbazar.domain.CommandeLignes.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.Zone.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.Zone.class.getName() + ".adresses");
            createCache(cm, org.fininfo.elbazar.domain.Zone.class.getName() + ".livraisons");
            createCache(cm, org.fininfo.elbazar.domain.Zone.class.getName() + ".affectationZones");
            createCache(cm, org.fininfo.elbazar.domain.Zone.class.getName() + ".commandes");
            createCache(cm, org.fininfo.elbazar.domain.Livraison.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.AffectationZone.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.AffectationZone.class.getName() + ".adresses");
            createCache(cm, org.fininfo.elbazar.domain.InfoSociete.class.getName());
            createCache(cm, org.fininfo.elbazar.domain.Slides.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
