package com.bootx.mall.feign;

import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.entity.Product;
import com.bootx.mall.entity.Store;
import com.bootx.mall.entity.vo.SkuVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * 远程搜索
 *
 * @author 夏黎
 * @version 1.0
 * @date 2020-07-03 11:11:08
 */
@FeignClient(value = "mall-search")
public interface SearchService {

    /**
     * 搜索商品分页
     *
     * @param keyword
     *            关键词
     * @param type
     *            类型
     * @param storeType
     *            店铺类型
     * @param storeId
     *            店铺ID
     * @param isOutOfStock
     *            是否缺货
     * @param isStockAlert
     *            是否库存警告
     * @param startPrice
     *            最低价格
     * @param endPrice
     *            最高价格
     * @param orderType
     *            排序类型
     * @param pageable
     *            分页信息
     * @return 商品分页
     */
    @GetMapping("/search/product")
    Page<SkuVo> search(@RequestParam("keyword") String keyword,
                       @RequestParam("type") Product.Type type,
                       @RequestParam("storeType")  Store.Type storeType,
                       @RequestParam("storeId")  Long storeId,
                       @RequestParam("isOutOfStock")   Boolean isOutOfStock,
                       @RequestParam("isStockAlert")  Boolean isStockAlert,
                       @RequestParam("startPrice") BigDecimal startPrice,
                       @RequestParam("endPrice")  BigDecimal endPrice,
                       @RequestParam("orderType") Product.OrderType orderType,
                       @RequestParam("pageable")  Pageable pageable);


}
