
package com.bootx.mall.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.dao.MemberDepositLogDao;
import com.bootx.mall.entity.Member;
import com.bootx.mall.entity.MemberDepositLog;
import org.springframework.stereotype.Repository;

/**
 * Dao - 会员预存款记录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class MemberDepositLogDaoImpl extends BaseDaoImpl<MemberDepositLog, Long> implements MemberDepositLogDao {

	@Override
	public Page<MemberDepositLog> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return Page.emptyPage(pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MemberDepositLog> criteriaQuery = criteriaBuilder.createQuery(MemberDepositLog.class);
		Root<MemberDepositLog> root = criteriaQuery.from(MemberDepositLog.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findPage(criteriaQuery, pageable);
	}

}