package com.bootx.mall.auth.config;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@Configuration
public class MessageConfig {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setCacheSeconds(0);
        reloadableResourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        reloadableResourceBundleMessageSource.setBasenames("classpath:messages/common/message", "classpath:messages/shop/message", "classpath:messages/member/message", "classpath:messages/business/message", "classpath:messages/admin/message");
        String message = reloadableResourceBundleMessageSource.getMessage("common.delete", null, LocaleUtils.toLocale("zh_CN"));
        System.out.println(message);
        System.out.println("reloadableResourceBundleMessageSource:"+reloadableResourceBundleMessageSource.hashCode());
        return reloadableResourceBundleMessageSource;
    }

    @Bean
    public FixedLocaleResolver localeResolver(){
        FixedLocaleResolver fixedLocaleResolver = new FixedLocaleResolver();
        fixedLocaleResolver.setDefaultLocale(LocaleUtils.toLocale("zh_CN"));
        System.out.println("MessConfig:"+fixedLocaleResolver);
        return fixedLocaleResolver;
    }
}
