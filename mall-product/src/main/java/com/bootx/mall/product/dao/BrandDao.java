
package com.bootx.mall.product.dao;

import java.util.List;

import com.bootx.mall.product.common.Filter;
import com.bootx.mall.product.common.Order;
import com.bootx.mall.product.entity.Brand;
import com.bootx.mall.product.entity.ProductCategory;

/**
 * Dao - 品牌
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface BrandDao extends BaseDao<Brand, Long> {

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

}