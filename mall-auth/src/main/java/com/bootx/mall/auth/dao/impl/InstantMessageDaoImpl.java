
package com.bootx.mall.auth.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.dao.InstantMessageDao;
import com.bootx.mall.auth.entity.InstantMessage;
import com.bootx.mall.auth.entity.Store;
import org.springframework.stereotype.Repository;

/**
 * Dao - 即时通讯
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class InstantMessageDaoImpl extends BaseDaoImpl<InstantMessage, Long> implements InstantMessageDao {

	@Override
	public List<InstantMessage> findList(InstantMessage.Type type, Store store, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InstantMessage> criteriaQuery = criteriaBuilder.createQuery(InstantMessage.class);
		Root<InstantMessage> root = criteriaQuery.from(InstantMessage.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	@Override
	public Page<InstantMessage> findPage(Store store, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InstantMessage> criteriaQuery = criteriaBuilder.createQuery(InstantMessage.class);
		Root<InstantMessage> root = criteriaQuery.from(InstantMessage.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}