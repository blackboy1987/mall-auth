package com.bootx.mall.config;

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
        reloadableResourceBundleMessageSource.setBasenames("messages/common/message", "messages/shop/message", "messages/member/message", "messages/business/message", "messages/admin/message");
        String message = reloadableResourceBundleMessageSource.getMessage("shop.mainHeader.memberLogin", null, LocaleUtils.toLocale("zh_CN"));
        System.out.println(message);
        return reloadableResourceBundleMessageSource;
    }

    @Bean
    public FixedLocaleResolver localeResolver(){
        FixedLocaleResolver fixedLocaleResolver = new FixedLocaleResolver();
        fixedLocaleResolver.setDefaultLocale(LocaleUtils.toLocale("zh_CN"));
        return fixedLocaleResolver;
    }
}
