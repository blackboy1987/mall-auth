
package com.bootx.mall.service.impl;

import javax.inject.Inject;

import com.bootx.mall.dao.SnDao;
import com.bootx.mall.entity.OrderReturns;
import com.bootx.mall.entity.Sn;
import com.bootx.mall.service.OrderReturnsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 订单退货
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class OrderReturnsServiceImpl extends BaseServiceImpl<OrderReturns, Long> implements OrderReturnsService {

	@Inject
	private SnDao snDao;

	@Override
	@Transactional
	public OrderReturns save(OrderReturns orderReturns) {
		Assert.notNull(orderReturns, "[Assertion failed] - orderReturns is required; it must not be null");

		orderReturns.setSn(snDao.generate(Sn.Type.ORDER_RETURNS));

		return super.save(orderReturns);
	}

}