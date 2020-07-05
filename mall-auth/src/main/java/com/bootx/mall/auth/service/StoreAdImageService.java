
package com.bootx.mall.auth.service;

import java.util.List;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.entity.Store;
import com.bootx.mall.auth.entity.StoreAdImage;

/**
 * Service - 店铺广告图片
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface StoreAdImageService extends BaseService<StoreAdImage, Long> {

	/**
	 * 查找店铺广告图片
	 * 
	 * @param storeId
	 *            店铺ID
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 店铺广告图片
	 */
	List<StoreAdImage> findList(Long storeId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

	/**
	 * 查找店铺广告图片分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 店铺广告图片分页
	 */
	Page<StoreAdImage> findPage(Store store, Pageable pageable);

}