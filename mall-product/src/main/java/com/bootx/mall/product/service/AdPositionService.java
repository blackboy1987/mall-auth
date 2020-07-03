
package com.bootx.mall.product.service;

import com.bootx.mall.product.entity.AdPosition;

/**
 * Service - 广告位
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface AdPositionService extends BaseService<AdPosition, Long> {

	/**
	 * 查找广告位
	 * 
	 * @param id
	 *            ID
	 * @param useCache
	 *            是否使用缓存
	 * @return 广告位
	 */
	AdPosition find(Long id, boolean useCache);

}