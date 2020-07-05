package com.bootx.mall.auth.entity.vo;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * ProductCategoryTreeVo
 *
 * @author 夏黎
 * @version 1.0
 * @date 2020-07-04 09:12:52
 */
public class ProductCategoryTreeVo implements Serializable,Comparable<ProductCategoryTreeVo> {

    private Long id;

    private String name;

    private Integer order;

    private Set<ProductCategoryTreeVo> children = new HashSet<>();


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

    public Set<ProductCategoryTreeVo> getChildren() {
        return children;
    }

    public void setChildren(Set<ProductCategoryTreeVo> children) {
        this.children = children;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public int compareTo(ProductCategoryTreeVo productCategoryTreeVo) {
        return new CompareToBuilder().append(getOrder(), productCategoryTreeVo.getOrder()).append(getId(), productCategoryTreeVo.getId()).toComparison();
    }
}
