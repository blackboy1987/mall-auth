
package com.bootx.mall.product.dao.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.dao.DistributionCashDao;
import com.bootx.mall.product.entity.DistributionCash;
import com.bootx.mall.product.entity.Distributor;
import org.springframework.stereotype.Repository;

/**
 * Dao - 分销提现
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class DistributionCashDaoImpl extends BaseDaoImpl<DistributionCash, Long> implements DistributionCashDao {

	@Override
	public Page<DistributionCash> findPage(DistributionCash.Status status, String bank, String account, String accountHolder, Distributor distributor, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DistributionCash> criteriaQuery = criteriaBuilder.createQuery(DistributionCash.class);
		Root<DistributionCash> root = criteriaQuery.from(DistributionCash.class);
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
		if (accountHolder != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("accountHolder"), accountHolder));
		}
		if (distributor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("distributor"), distributor));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public BigDecimal cashTotalAmount(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<DistributionCash> root = criteriaQuery.from(DistributionCash.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("amount")));
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), DistributionCash.Status.APPROVED));
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		BigDecimal result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result != null ? result : BigDecimal.ZERO;
	}

	@Override
	public Long count(DistributionCash.Status status, String bank, String account, String accountHolder, Distributor distributor) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DistributionCash> criteriaQuery = criteriaBuilder.createQuery(DistributionCash.class);
		Root<DistributionCash> root = criteriaQuery.from(DistributionCash.class);
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
		if (accountHolder != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("accountHolder"), accountHolder));
		}
		if (distributor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("distributor"), distributor));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

}