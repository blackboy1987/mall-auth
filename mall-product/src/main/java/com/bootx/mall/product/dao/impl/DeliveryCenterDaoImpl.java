
package com.bootx.mall.product.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.dao.DeliveryCenterDao;
import com.bootx.mall.product.entity.DeliveryCenter;
import com.bootx.mall.product.entity.Store;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 发货点
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class DeliveryCenterDaoImpl extends BaseDaoImpl<DeliveryCenter, Long> implements DeliveryCenterDao {

	@Override
	public DeliveryCenter findDefault(Store store) {
		try {
			String jpql = "select deliveryCenter from DeliveryCenter deliveryCenter where deliveryCenter.isDefault = true and lower(deliveryCenter.store) = lower(:store)";
			return entityManager.createQuery(jpql, DeliveryCenter.class).setParameter("store", store).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void clearDefault(Store store) {
		String jpql = "update DeliveryCenter deliveryCenter set deliveryCenter.isDefault = false where deliveryCenter.isDefault = true and lower(deliveryCenter.store) = lower(:store)";
		entityManager.createQuery(jpql).setParameter("store", store).executeUpdate();
	}

	@Override
	public void clearDefault(DeliveryCenter exclude) {
		Assert.notNull(exclude, "[Assertion failed] - exclude is required; it must not be null");

		String jpql = "update DeliveryCenter deliveryCenter set deliveryCenter.isDefault = false where deliveryCenter.isDefault = true and deliveryCenter != :exclude and lower(deliveryCenter.store) = lower(:store)";
		entityManager.createQuery(jpql).setParameter("exclude", exclude).setParameter("store", exclude.getStore()).executeUpdate();
	}

	@Override
	public Page<DeliveryCenter> findPage(Store store, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DeliveryCenter> criteriaQuery = criteriaBuilder.createQuery(DeliveryCenter.class);
		Root<DeliveryCenter> root = criteriaQuery.from(DeliveryCenter.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<DeliveryCenter> findAll(Store store) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DeliveryCenter> criteriaQuery = criteriaBuilder.createQuery(DeliveryCenter.class);
		Root<DeliveryCenter> root = criteriaQuery.from(DeliveryCenter.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery);
	}
}