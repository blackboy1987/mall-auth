package com.bootx.mall.controller.common;

import com.bootx.mall.config.ElasticSearchConfig;
import com.bootx.mall.entity.*;
import com.bootx.mall.entity.vo.Attr;
import com.bootx.mall.entity.vo.SkuVo;
import com.bootx.mall.service.SkuService;
import com.bootx.mall.service.SpecificationService;
import com.bootx.mall.util.JsonUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * aa
 *
 * @author 夏黎
 * @version 1.0
 * @date 2020-07-02 13:33:43
 */
@RestController
public class IndexController {

    @Inject
    private SkuService skuService;

    @Inject
    private SpecificationService specificationService;

    @Inject
    private RestHighLevelClient restHighLevelClient;

    public Integer index() throws Exception{
        List<Sku> skus = skuService.findAll();
        // 循环组装数据。放到es里面
        for (Sku sku:skus){
            SkuVo skuVo = setSkuVo(sku);
            IndexRequest indexRequest = new IndexRequest("product");
            indexRequest.id(skuVo.getSn());
            indexRequest.source(JsonUtils.toJson(skuVo), XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
            System.out.println(indexResponse);
        }
        return skus.size();
    }

    private SkuVo setSkuVo(Sku sku) {
        SkuVo skuVo = new SkuVo();
        Product product = sku.getProduct();
        Brand brand = product.getBrand();
        ProductCategory productCategory = product.getProductCategory();
        Store store = product.getStore();
        skuVo.setSn(sku.getSn());
        skuVo.setProductId(product.getId());
        skuVo.setImg(product.getThumbnail());
        skuVo.setName(product.getName());
        skuVo.setPrice(sku.getPrice());
        skuVo.setHasStock(!sku.getIsOutOfStock());
        skuVo.setSales(product.getSales());
        skuVo.setTotalScore(product.getTotalScore());
        skuVo.setAttrs(setAttrs(sku));
        if(brand!=null){
            skuVo.setBrandId(brand.getId());
            skuVo.setBrandName(brand.getName());
            skuVo.setBrandImg(brand.getLogo());
        }
        if(productCategory!=null){
            skuVo.setCategoryId(productCategory.getId());
            skuVo.setCategoryName(productCategory.getName());
        }
        if(store!=null){
            skuVo.setStoreId(store.getId());
            skuVo.setStoreName(store.getName());
        }
        return skuVo;
    }

    private Set<Attr> setAttrs(Sku sku) {
        List<SpecificationValue> specificationValues = sku.getSpecificationValues();
        Set<Attr> attrs = new HashSet<>();
        for (SpecificationValue specificationValue:specificationValues) {
            Attr attr = new Attr();
            attr.setAttrId(specificationValue.getId());
            attr.setAttrValue(specificationValue.getValue());
            attr.setAttrName(specificationService.find((long) specificationValue.getId()).getName());
            attrs.add(attr);
        }
        return attrs;
    }
}
