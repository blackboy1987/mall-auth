package com.bootx.mall.auth.config;

import com.bootx.mall.auth.entity.Admin;
import com.bootx.mall.auth.security.AuthenticationFilter;
import com.bootx.mall.auth.security.AuthorizingRealm;
import com.bootx.mall.auth.security.LogoutFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ShiroConfig
 *
 * @author 夏黎
 * @version 1.0
 * @date 2020-07-05 19:23:20
 */
@Configuration
public class ShiroConfig {

    @Inject
    private EhCacheManagerFactoryBean ehCacheManager;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setUnauthorizedUrl("/common/error/unauthorized");

        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/admin","anon");
        filterChainDefinitionMap.put("/admin/","anon");
        filterChainDefinitionMap.put("/admin/currentUser","anon");
        filterChainDefinitionMap.put("/admin/login","adminAuthc");
        filterChainDefinitionMap.put("/admin/logout","logout");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String, Filter> filters = new HashMap<>();

        filters.put("adminAuthc",adminAuthc());

        filters.put("logout",new LogoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(authorizingRealm());
        defaultWebSecurityManager.setCacheManager(shiroCacheManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public AuthorizingRealm authorizingRealm(){
        AuthorizingRealm authorizingRealm = new AuthorizingRealm();
        authorizingRealm.setAuthenticationCacheName("authorization");
        return authorizingRealm;
    }

    @Bean
    public EhCacheManager shiroCacheManager(){
        EhCacheManager shiroCacheManager = new EhCacheManager();
        shiroCacheManager.setCacheManager(ehCacheManager.getObject());
        return shiroCacheManager;
    }

    @Bean
    public AuthenticationFilter adminAuthc(){
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setUserClass(Admin.class);
        authenticationFilter.setLoginUrl("/admin/login");
        authenticationFilter.setSuccessUrl("/admin/index");
        return authenticationFilter;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean (){
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(securityManager());

        return methodInvokingFactoryBean;
    }
}