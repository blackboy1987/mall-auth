package com.bootx.mall.config;

import com.bootx.mall.listener.CacheEventListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Configuration
public class EhCacheConfig {

    @Inject
    private CacheEventListener cacheEventListener;

    @Bean
    public EhCacheCacheManager cacheManager(@Qualifier("ehCacheManager") EhCacheManagerFactoryBean ehCacheManager){
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(ehCacheManager.getObject());

        return ehCacheCacheManager;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManager(){
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }

    @Bean
    public EhCacheFactoryBean productHitsCacheBean(){
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(Objects.requireNonNull(cacheManager(ehCacheManager()).getCacheManager()));
        ehCacheFactoryBean.setCacheName("productHits");
        Set<net.sf.ehcache.event.CacheEventListener> cacheEventListeners = new HashSet<>();
        cacheEventListeners.add(cacheEventListener);
        ehCacheFactoryBean.setCacheEventListeners(cacheEventListeners);
        return ehCacheFactoryBean;
    }

    @Bean
    public EhCacheFactoryBean articleHitsCacheBean(){
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(Objects.requireNonNull(cacheManager(ehCacheManager()).getCacheManager()));
        ehCacheFactoryBean.setCacheName("articleHits");
        Set<net.sf.ehcache.event.CacheEventListener> cacheEventListeners = new HashSet<>();
        cacheEventListeners.add(cacheEventListener);
        ehCacheFactoryBean.setCacheEventListeners(cacheEventListeners);
        return ehCacheFactoryBean;
    }
}
