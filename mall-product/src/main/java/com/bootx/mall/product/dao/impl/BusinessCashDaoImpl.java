
package com.bootx.mall.product.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.dao.BusinessCashDao;
import com.bootx.mall.product.entity.Business;
import com.bootx.mall.product.entity.BusinessCash;
import org.springframework.stereotype.Repository;

/**
 * Dao - 商家提现
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class BusinessCashDaoImpl extends BaseDaoImpl<BusinessCash, Long> implements BusinessCashDao {

	@Override
	public Page<BusinessCash> findPage(BusinessCash.Status status, String bank, String account, Business business, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BusinessCash> criteriaQuery = criteriaBuilder.createQuery(BusinessCash.class);
		Root<BusinessCash> root = criteriaQuery.from(BusinessCash.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (bank != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("bank"), bank));
		}
		if (account != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("account"), account));
		}
		if (business != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("business"), business));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Long count(BusinessCash.Status status, String bank, String account, Business business) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BusinessCash> criteriaQuery = criteriaBuilder.createQuery(BusinessCash.class);
		Root<BusinessCash> root = criteriaQuery.from(BusinessCash.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (bank != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("bank"), bank));
		}
		if (account != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("account"), account));
		}
		if (business != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("business"), business));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

}