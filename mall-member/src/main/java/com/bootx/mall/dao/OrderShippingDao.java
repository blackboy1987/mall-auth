
package com.bootx.mall.dao;

import com.bootx.mall.entity.Order;
import com.bootx.mall.entity.OrderShipping;

/**
 * Dao - 订单发货
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface OrderShippingDao extends BaseDao<OrderShipping, Long> {

	/**
	 * 查找最后一条订单发货
	 * 
	 * @param order
	 *            订单
	 * @return 订单发货，若不存在则返回null
	 */
	OrderShipping findLast(Order order);

}