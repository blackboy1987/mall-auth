
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
import com.bootx.mall.auth.dao.StoreProductTagDao;
import com.bootx.mall.auth.entity.Store;
import com.bootx.mall.auth.entity.StoreProductTag;
import org.springframework.stereotype.Repository;

/**
 * Dao - 店铺商品标签
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class StoreProductTagDaoImpl extends BaseDaoImpl<StoreProductTag, Long> implements StoreProductTagDao {

	@Override
	public List<StoreProductTag> findList(Store store, Boolean isEnabled) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StoreProductTag> criteriaQuery = criteriaBuilder.createQuery(StoreProductTag.class);
		Root<StoreProductTag> root = criteriaQuery.from(StoreProductTag.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (isEnabled != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), isEnabled));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<StoreProductTag> findList(Store store, Boolean isEnabled, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StoreProductTag> criteriaQuery = criteriaBuilder.createQuery(StoreProductTag.class);
		Root<StoreProductTag> root = criteriaQuery.from(StoreProductTag.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (isEnabled != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), isEnabled));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	@Override
	public Page<StoreProductTag> findPage(Store store, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StoreProductTag> criteriaQuery = criteriaBuilder.createQuery(StoreProductTag.class);
		Root<StoreProductTag> root = criteriaQuery.from(StoreProductTag.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}