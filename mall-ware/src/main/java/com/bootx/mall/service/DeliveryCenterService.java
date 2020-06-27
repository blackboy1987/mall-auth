
package com.bootx.mall.service;

import java.util.List;

import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.entity.DeliveryCenter;
import com.bootx.mall.entity.Store;

/**
 * Service - 发货点
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface DeliveryCenterService extends BaseService<DeliveryCenter, Long> {

	/**
	 * 查找默认发货点
	 * 
	 * @param store
	 *            店铺
	 * @return 默认发货点，若不存在则返回null
	 */
	DeliveryCenter findDefault(Store store);

	/**
	 * 查找发货点分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 发货点分页
	 */
	Page<DeliveryCenter> findPage(Store store, Pageable pageable);

	/**
	 * 查找发货点
	 * 
	 * @param store
	 *            店铺
	 * @return 发货点
	 */
	List<DeliveryCenter> findAll(Store store);

}