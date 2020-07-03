package com.bootx.mall.product.entity.vo;

import com.bootx.mall.product.entity.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Sku
 *
 * @author 夏黎
 * @version 1.0
 * @date 2020-07-02 14:50:57
 */
public class SkuVo implements Serializable {

    private String sn;

    private Long id;

    private String name;

    private BigDecimal price;

    private String thumbnail;

    private String path;

    private Long sales;

    private Boolean hasStock;

    private Long totalScore;

    private Long brandId;

    private Long categoryId;

    private String brandName;

    private String brandImg;

    private String categoryName;

    private Long storeId;

    private String storeName;

    private StoreVo store;

    private Product.Type type;

    private String caption;


    private Set<Attr> attrs = new HashSet<>();

    private DefaultSkuVo defaultSku;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public StoreVo getStore() {
        return store;
    }

    public void setStore(StoreVo store) {
        this.store = store;
    }

    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public Boolean getHasStock() {
        return hasStock;
    }

    public void setHasStock(Boolean hasStock) {
        this.hasStock = hasStock;
    }

    public Long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandImg() {
        return brandImg;
    }

    public void setBrandImg(String brandImg) {
        this.brandImg = brandImg;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Attr> getAttrs() {
        return attrs;
    }

    public void setAttrs(Set<Attr> attrs) {
        this.attrs = attrs;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Product.Type getType() {
        return type;
    }

    public void setType(Product.Type type) {
        this.type = type;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public DefaultSkuVo getDefaultSku() {
        return defaultSku;
    }

    public void setDefaultSku(DefaultSkuVo defaultSkuVo) {
        this.defaultSku = defaultSkuVo;
    }


}
