
package com.bootx.mall.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.bootx.mall.common.Filter;
import com.bootx.mall.common.Order;
import com.bootx.mall.dao.StoreRankDao;
import com.bootx.mall.entity.StoreRank;
import org.springframework.stereotype.Repository;

/**
 * Dao - 店铺等级
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class StoreRankDaoImpl extends BaseDaoImpl<StoreRank, Long> implements StoreRankDao {

	@Override
	public List<StoreRank> findList(Boolean isAllowRegister, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StoreRank> criteriaQuery = criteriaBuilder.createQuery(StoreRank.class);
		Root<StoreRank> root = criteriaQuery.from(StoreRank.class);
		criteriaQuery.select(root);
		if (isAllowRegister != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("isAllowRegister"), isAllowRegister));
		}
		return super.findList(criteriaQuery, null, null, filters, orders);
	}

}