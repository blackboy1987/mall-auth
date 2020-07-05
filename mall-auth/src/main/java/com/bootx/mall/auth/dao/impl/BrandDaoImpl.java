
package com.bootx.mall.auth.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.dao.BrandDao;
import com.bootx.mall.auth.entity.Brand;
import com.bootx.mall.auth.entity.ProductCategory;
import org.springframework.stereotype.Repository;

/**
 * Dao - 品牌
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class BrandDaoImpl extends BaseDaoImpl<Brand, Long> implements BrandDao {

	@Override
	public List<Brand> findList(ProductCategory productCategory, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
		Root<Brand> root = criteriaQuery.from(Brand.class);
		criteriaQuery.select(root);
		if (productCategory != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.join("productCategories"), productCategory));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

}