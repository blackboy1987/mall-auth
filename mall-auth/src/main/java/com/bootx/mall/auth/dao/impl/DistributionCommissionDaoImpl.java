
package com.bootx.mall.auth.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.dao.DistributionCommissionDao;
import com.bootx.mall.auth.entity.DistributionCommission;
import com.bootx.mall.auth.entity.Distributor;
import org.springframework.stereotype.Repository;

/**
 * Dao - 分销佣金
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class DistributionCommissionDaoImpl extends BaseDaoImpl<DistributionCommission, Long> implements DistributionCommissionDao {

	@Override
	public Page<DistributionCommission> findPage(Distributor distributor, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DistributionCommission> criteriaQuery = criteriaBuilder.createQuery(DistributionCommission.class);
		Root<DistributionCommission> root = criteriaQuery.from(DistributionCommission.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (distributor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("distributor"), distributor));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}