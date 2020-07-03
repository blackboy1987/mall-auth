package com.bootx.mall.product.config;

import com.bootx.mall.product.template.directive.*;
import com.bootx.mall.product.template.method.AbbreviateMethod;
import com.bootx.mall.product.template.method.CurrencyMethod;
import com.bootx.mall.product.template.method.MessageMethod;
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
    private NavigationListDirective navigationListDirective;
    @Resource
    private ProductCategoryRootListDirective productCategoryRootListDirective;
    @Resource
    private ProductCategoryChildrenListDirective productCategoryChildrenListDirective;
    @Resource
    private MessageMethod messageMethod;
    @Resource
    private PromotionListDirective promotionListDirective;
    @Resource
    private BrandListDirective brandListDirective;
    @Resource
    private ArticleCategoryRootListDirective articleCategoryRootListDirective;
    @Resource
    private ArticleListDirective articleListDirective;
    @Resource
    private ProductListDirective productListDirective;
    @Resource
    private ProductTagListDirective productTagListDirective;
    @Resource
    private FriendLinkListDirective friendLinkListDirective;
    @Resource
    private ProductCategoryParentListDirective productCategoryParentListDirective;
    @Resource
    private InstantMessageListDirective instantMessageListDirective;
    @Resource
    private ConsultationListDirective consultationListDirective;

    @Resource
    private StoreAdImageListDirective storeAdImageListDirective;
    @Resource
    private StoreCountDirective storeCountDirective;
    @Resource
    private StoreFavoriteDirective storeFavoriteDirective;
    @Resource
    private StoreProductCategoryChildrenListDirective storeProductCategoryChildrenListDirective;
    @Resource
    private StoreProductCategoryParentListDirective storeProductCategoryParentListDirective;
    @Resource
    private StoreProductCategoryRootListDirective storeProductCategoryRootListDirective;
    @Resource
    private StoreProductTagDirective storeProductTagDirective;
    @Resource
    private MemberAttributeListDirective memberAttributeListDirective;


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
        freemarkerVariables.put("currency",new CurrencyMethod());
        freemarkerVariables.put("ad_position",adPositionDirective);
        freemarkerVariables.put("article_category_children_list",new ArticleCategoryChildrenListDirective());
        freemarkerVariables.put("article_category_parent_list",new ArticleCategoryParentListDirective());
        freemarkerVariables.put("article_category_root_list",articleCategoryRootListDirective);
        freemarkerVariables.put("article_list",articleListDirective);
        freemarkerVariables.put("article_tag_list",new ArticleTagListDirective());
        freemarkerVariables.put("attribute_list",new AttributeListDirective());
        freemarkerVariables.put("brand_list",brandListDirective);
        freemarkerVariables.put("business_attribute_list",new BusinessAttributeListDirective());
        freemarkerVariables.put("business_cash_count", new BusinessCashCountDirective());
        freemarkerVariables.put("category_application_count",new CategoryApplicationCountDirective());
        freemarkerVariables.put("consultation_list",consultationListDirective);
        freemarkerVariables.put("distribution_cash_count",new DistributionCashCountDirective());
        freemarkerVariables.put("friend_link_list",friendLinkListDirective);
        freemarkerVariables.put("instant_message_list",instantMessageListDirective);
        freemarkerVariables.put("member_attribute_list",memberAttributeListDirective);
        freemarkerVariables.put("navigation_list",navigationListDirective);
        freemarkerVariables.put("order_count",new OrderCountDirective());
        freemarkerVariables.put("pagination",new PaginationDirective());
        freemarkerVariables.put("product_category_children_list",productCategoryChildrenListDirective);
        freemarkerVariables.put("product_category_parent_list",productCategoryParentListDirective);
        freemarkerVariables.put("product_category_root_list",productCategoryRootListDirective);
        freemarkerVariables.put("product_count",new ProductCountDirective());
        freemarkerVariables.put("product_favorite",new ProductFavoriteDirective());
        freemarkerVariables.put("product_list",productListDirective);
        freemarkerVariables.put("product_tag_list",productTagListDirective);
        freemarkerVariables.put("promotion_list",promotionListDirective);
        freemarkerVariables.put("promotion_plugin",new PromotionPluginDirective());
        freemarkerVariables.put("review_count",new ReviewCountDirective());
        freemarkerVariables.put("review_list",new ReviewListDirective());
        freemarkerVariables.put("seo",seoDirective);
        freemarkerVariables.put("store_ad_image_list",storeAdImageListDirective);
        freemarkerVariables.put("store_count",storeCountDirective);
        freemarkerVariables.put("store_favorite",storeFavoriteDirective);
        freemarkerVariables.put("store_product_category_children_list",storeProductCategoryChildrenListDirective);
        freemarkerVariables.put("store_product_category_parent_list",storeProductCategoryParentListDirective);
        freemarkerVariables.put("store_product_category_root_list",storeProductCategoryRootListDirective);
        freemarkerVariables.put("store_product_tag_list",storeProductTagDirective);
        freemarkerVariables.put("has_permission_tag",new HasPermissionTagDirective());
        freemarkerVariables.put("has_any_permissions_tag",new HasAnyPermissionsTagDirective());
         freeMarkerConfigurer.setFreemarkerVariables(freemarkerVariables);


        return freeMarkerConfigurer;
    }
}
