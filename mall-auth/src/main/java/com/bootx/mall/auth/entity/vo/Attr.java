package com.bootx.mall.auth.entity.vo;

import java.io.Serializable;

/**
 * Attr
 *
 * @author 夏黎
 * @version 1.0
 * @date 2020-07-02 14:54:34
 */
public class Attr implements Serializable {

    private Integer attrId;

    private String attrName;

    private String attrValue;

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}
