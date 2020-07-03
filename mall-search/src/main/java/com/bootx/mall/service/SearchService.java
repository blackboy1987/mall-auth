
package com.bootx.mall.service;

import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.entity.Product;
import com.bootx.mall.entity.Store;
import com.bootx.mall.entity.vo.SkuVo;

import java.math.BigDecimal;

/**
 * Service - 搜索
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface SearchService {

	/**
	 * 创建索引
	 *
	 * @param type
	 *            索引类型
	 */
	void index(Class<?> type);

	/**
	 * 创建索引
	 *
	 * @param type
	 *            索引类型
	 * @param purgeAll
	 *            是否清空已存在索引
	 */
	void index(Class<?> type, boolean purgeAll);


	/**
	 * 搜索商品分页
	 *
	 * @param keyword
	 *            关键词
	 * @param type
	 *            类型
	 * @param storeType
	 *            店铺类型
	 * @param store
	 *            店铺
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
	Page<SkuVo> search(String keyword, Product.Type type, Store.Type storeType, Long storeId, Boolean isOutOfStock, Boolean isStockAlert, BigDecimal startPrice, BigDecimal endPrice, Product.OrderType orderType, Pageable pageable);


}