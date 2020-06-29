package com.bootx.mall.config;

import com.bootx.mall.template.directive.*;
import com.bootx.mall.template.method.AbbreviateMethod;
import com.bootx.mall.template.method.MessageMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class FreeMarkerConfig {

    @Resource
    private ServletContext servletContext;

    @Value("${show_powered}")
    private Boolean show_powered;

    @Resource
    private SeoDirective seoDirective;
    @Resource
    private AdPositionDirective adPositionDirective;
    @Resource
    private MessageMethod messageMethod;
    @Resource
    private ArticleCategoryRootListDirective articleCategoryRootListDirective;
    @Resource
    private ArticleListDirective articleListDirective;
    @Resource
    private FriendLinkListDirective friendLinkListDirective;

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(){

        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPaths("classpath:/templates/");
        Properties freemarkerSettings = new Properties();
        freemarkerSettings.setProperty("default_encoding","UTF-8");
        freemarkerSettings.setProperty("url_escaping_charset","UTF-8");
        freemarkerSettings.setProperty("output_format","HTMLOutputFormat");
        freemarkerSettings.setProperty("template_update_delay","0");
        freemarkerSettings.setProperty("tag_syntax","auto_detect");
        freemarkerSettings.setProperty("classic_compatible","true");
        freemarkerSettings.setProperty("number_format","0.######");
        freemarkerSettings.setProperty("boolean_format","true,false");
        freemarkerSettings.setProperty("datetime_format","yyyy-MM-dd");
        freemarkerSettings.setProperty("date_format","yyyy-MM-dd");
        freemarkerSettings.setProperty("time_format","HH:mm:ss");
        freemarkerSettings.setProperty("object_wrapper","freemarker.ext.beans.BeansWrapper");
        freeMarkerConfigurer.setFreemarkerSettings(freemarkerSettings);
        Map<String,Object> freemarkerVariables= new HashMap<>();

        freemarkerVariables.put("base",servletContext.getContextPath());
        freemarkerVariables.put("showPowered",show_powered);
        freemarkerVariables.put("message", messageMethod);
        freemarkerVariables.put("abbreviate",new AbbreviateMethod());
        freemarkerVariables.put("ad_position",adPositionDirective);
        freemarkerVariables.put("article_category_children_list",new ArticleCategoryChildrenListDirective());
        freemarkerVariables.put("article_category_parent_list",new ArticleCategoryParentListDirective());
        freemarkerVariables.put("article_category_root_list",articleCategoryRootListDirective);
        freemarkerVariables.put("article_list",articleListDirective);
        freemarkerVariables.put("article_tag_list",new ArticleTagListDirective());
        freemarkerVariables.put("friend_link_list",friendLinkListDirective);
        freemarkerVariables.put("seo",seoDirective);
         freeMarkerConfigurer.setFreemarkerVariables(freemarkerVariables);


        return freeMarkerConfigurer;
    }
}
