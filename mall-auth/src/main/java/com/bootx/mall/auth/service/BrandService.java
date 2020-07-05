
package com.bootx.mall.auth.service;

import java.util.List;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.entity.Brand;
import com.bootx.mall.auth.entity.ProductCategory;

/**
 * Service - 品牌
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface BrandService extends BaseService<Brand, Long> {

	/**
	 * 查找品牌
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 品牌
	 */
	List<Brand> findList(ProductCategory productCategory, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找品牌
	 * 
	 * @param productCategoryId
	 *            商品分类ID
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 品牌
	 */
	List<Brand> findList(Long productCategoryId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

}