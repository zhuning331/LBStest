package com.gorilla.myapp.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.gorilla.myapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.ServiceConfig.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.WorkspaceConfig.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Language.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Workspace.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Workspace.class.getName() + ".configs", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Workspace.class.getName() + ".maps", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Map.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Map.class.getName() + ".layers", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Map.class.getName() + ".webs", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Map.class.getName() + ".regions", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Layer.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.POI.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.POI.class.getName() + ".types", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.POIType.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.POIHistoricalLocation.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Node.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Edge.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Edge.class.getName() + ".types", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Region.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Region.class.getName() + ".polygonRegions", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Region.class.getName() + ".regularRegions", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Region.class.getName() + ".types", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.RegionType.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.PolygonRegion.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.PolygonRegion.class.getName() + ".nodes", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.RegularRegion.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Web.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Web.class.getName() + ".edges", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.Web.class.getName() + ".types", jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.WebType.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.EdgeType.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.BindingPOI.class.getName(), jcacheConfiguration);
            cm.createCache(com.gorilla.myapp.domain.LocationUpdate.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
