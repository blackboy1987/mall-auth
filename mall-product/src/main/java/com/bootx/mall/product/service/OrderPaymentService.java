
package com.bootx.mall.product.service;

import com.bootx.mall.product.entity.OrderPayment;

/**
 * Service - 订单支付
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface OrderPaymentService extends BaseService<OrderPayment, Long> {

	/**
	 * 根据编号查找订单支付
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 订单支付，若不存在则返回null
	 */
	OrderPayment findBySn(String sn);

}