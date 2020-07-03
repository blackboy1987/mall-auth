package com.bootx.mall.product.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Sku
 *
 * @author 夏黎
 * @version 1.0
 * @date 2020-07-02 14:50:57
 */
public class DefaultSkuVo implements Serializable {

    private Long id;

    private BigDecimal price;

    private BigDecimal marketPrice;

    private Long exchangePoint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getExchangePoint() {
        return exchangePoint;
    }

    public void setExchangePoint(Long exchangePoint) {
        this.exchangePoint = exchangePoint;
    }
}
