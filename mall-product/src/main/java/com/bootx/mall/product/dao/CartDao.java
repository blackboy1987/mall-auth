
package com.bootx.mall.product.dao;

import com.bootx.mall.product.entity.Cart;

/**
 * Dao - 购物车
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface CartDao extends BaseDao<Cart, Long> {

	/**
	 * 删除过期购物车
	 */
	void deleteExpired();

}