
package com.bootx.mall.service.impl;

import javax.inject.Inject;

import com.bootx.mall.dao.OrderPaymentDao;
import com.bootx.mall.dao.SnDao;
import com.bootx.mall.entity.OrderPayment;
import com.bootx.mall.entity.Sn;
import com.bootx.mall.service.OrderPaymentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 订单支付
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class OrderPaymentServiceImpl extends BaseServiceImpl<OrderPayment, Long> implements OrderPaymentService {

	@Inject
	private OrderPaymentDao orderPaymentDao;
	@Inject
	private SnDao snDao;

	@Override
	@Transactional(readOnly = true)
	public OrderPayment findBySn(String sn) {
		return orderPaymentDao.find("sn", StringUtils.lowerCase(sn));
	}

	@Override
	@Transactional
	public OrderPayment save(OrderPayment orderPayment) {
		Assert.notNull(orderPayment, "[Assertion failed] - orderPayment is required; it must not be null");

		orderPayment.setSn(snDao.generate(Sn.Type.ORDER_PAYMENT));

		return super.save(orderPayment);
	}

}