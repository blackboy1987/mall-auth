
package com.bootx.mall.auth.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.dao.AreaFreightConfigDao;
import com.bootx.mall.auth.entity.Area;
import com.bootx.mall.auth.entity.AreaFreightConfig;
import com.bootx.mall.auth.entity.ShippingMethod;
import com.bootx.mall.auth.entity.Store;
import org.springframework.stereotype.Repository;

/**
 * Dao - 地区运费配置
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class AreaFreightConfigDaoImpl extends BaseDaoImpl<AreaFreightConfig, Long> implements AreaFreightConfigDao {

	@Override
	public boolean exists(ShippingMethod shippingMethod, Store store, Area area) {
		if (shippingMethod == null || store == null || area == null) {
			return false;
		}
		String jpql = "select count(*) from AreaFreightConfig areaFreightConfig where areaFreightConfig.shippingMethod = :shippingMethod and areaFreightConfig.store = :store and areaFreightConfig.area = :area";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("shippingMethod", shippingMethod).setParameter("store", store).setParameter("area", area).getSingleResult();
		return count > 0;
	}

	@Override
	public boolean unique(Long id, ShippingMethod shippingMethod, Store store, Area area) {
		if (shippingMethod == null || store == null || area == null) {
			return false;
		}
		if (id != null) {
			String jpql = "select count(*) from AreaFreightConfig areaFreightConfig where areaFreightConfig.id != :id and areaFreightConfig.shippingMethod = :shippingMethod and areaFreightConfig.store = :store and areaFreightConfig.area = :area";
			Long count = entityManager.createQuery(jpql, Long.class).setParameter("id", id).setParameter("shippingMethod", shippingMethod).setParameter("store", store).setParameter("area", area).getSingleResult();
			return count <= 0;
		} else {
			String jpql = "select count(*) from AreaFreightConfig areaFreightConfig where areaFreightConfig.shippingMethod = :shippingMethod and areaFreightConfig.store = :store and areaFreightConfig.area = :area";
			Long count = entityManager.createQuery(jpql, Long.class).setParameter("shippingMethod", shippingMethod).setParameter("store", store).setParameter("area", area).getSingleResult();
			return count <= 0;
		}
	}

	@Override
	public Page<AreaFreightConfig> findPage(ShippingMethod shippingMethod, Store store, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AreaFreightConfig> criteriaQuery = criteriaBuilder.createQuery(AreaFreightConfig.class);
		Root<AreaFreightConfig> root = criteriaQuery.from(AreaFreightConfig.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (shippingMethod != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("shippingMethod"), shippingMethod));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
}