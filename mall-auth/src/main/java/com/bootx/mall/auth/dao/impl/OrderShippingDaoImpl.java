
package com.bootx.mall.auth.dao.impl;

import javax.persistence.NoResultException;

import com.bootx.mall.auth.dao.OrderShippingDao;
import com.bootx.mall.auth.entity.Order;
import com.bootx.mall.auth.entity.OrderShipping;
import org.springframework.stereotype.Repository;

/**
 * Dao - 订单发货
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class OrderShippingDaoImpl extends BaseDaoImpl<OrderShipping, Long> implements OrderShippingDao {

	@Override
	public OrderShipping findLast(Order order) {
		if (order == null) {
			return null;
		}
		String jpql = "select orderShipping from OrderShipping shipping where lower(orderShipping.order) = lower(:order) order by orderShipping.createdDate desc";
		try {
			return entityManager.createQuery(jpql, OrderShipping.class).setParameter("order", order.getId()).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}