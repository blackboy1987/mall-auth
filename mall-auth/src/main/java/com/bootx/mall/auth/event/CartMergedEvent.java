
package com.bootx.mall.auth.event;

import com.bootx.mall.auth.entity.Cart;

/**
 * Event - 合并购物车
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public class CartMergedEvent extends CartEvent {

	private static final long serialVersionUID = -320699877093325080L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param cart
	 *            购物车
	 */
	public CartMergedEvent(Object source, Cart cart) {
		super(source, cart);
	}

}