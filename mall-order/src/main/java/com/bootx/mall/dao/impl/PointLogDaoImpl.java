
package com.bootx.mall.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.dao.PointLogDao;
import com.bootx.mall.entity.Member;
import com.bootx.mall.entity.PointLog;
import org.springframework.stereotype.Repository;

/**
 * Dao - 积分记录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class PointLogDaoImpl extends BaseDaoImpl<PointLog, Long> implements PointLogDao {

	@Override
	public Page<PointLog> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return Page.emptyPage(pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PointLog> criteriaQuery = criteriaBuilder.createQuery(PointLog.class);
		Root<PointLog> root = criteriaQuery.from(PointLog.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findPage(criteriaQuery, pageable);
	}

}