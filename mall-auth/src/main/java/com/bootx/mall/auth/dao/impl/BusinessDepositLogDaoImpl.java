
package com.bootx.mall.auth.dao.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.dao.BusinessDepositLogDao;
import com.bootx.mall.auth.entity.Business;
import com.bootx.mall.auth.entity.BusinessDepositLog;
import org.springframework.stereotype.Repository;

/**
 * Dao - 商家预存款记录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class BusinessDepositLogDaoImpl extends BaseDaoImpl<BusinessDepositLog, Long> implements BusinessDepositLogDao {

	@Override
	public Page<BusinessDepositLog> findPage(Business business, Pageable pageable) {
		if (business == null) {
			return Page.emptyPage(pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BusinessDepositLog> criteriaQuery = criteriaBuilder.createQuery(BusinessDepositLog.class);
		Root<BusinessDepositLog> root = criteriaQuery.from(BusinessDepositLog.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("business"), business));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public BigDecimal cashTotalAmount(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<BusinessDepositLog> root = criteriaQuery.from(BusinessDepositLog.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("debit")));
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), BusinessDepositLog.Type.CASH));
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
}