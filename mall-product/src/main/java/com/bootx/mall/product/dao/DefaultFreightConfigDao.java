
package com.bootx.mall.product.dao;

import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.entity.Area;
import com.bootx.mall.product.entity.DefaultFreightConfig;
import com.bootx.mall.product.entity.ShippingMethod;
import com.bootx.mall.product.entity.Store;

/**
 * Dao - 默认运费配置
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface DefaultFreightConfigDao extends BaseDao<DefaultFreightConfig, Long> {

	/**
	 * 判断运费配置是否存在
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param area
	 *            地区
	 * @return 运费配置是否存在
	 */
	boolean exists(ShippingMethod shippingMethod, Area area);

	/**
	 * 查找默认运费配置分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 运费配置分页
	 */
	Page<DefaultFreightConfig> findPage(Store store, Pageable pageable);

	/**
	 * 查找默认运费配置
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param store
	 *            店铺
	 * @return 默认运费配置
	 */
	DefaultFreightConfig find(ShippingMethod shippingMethod, Store store);

}