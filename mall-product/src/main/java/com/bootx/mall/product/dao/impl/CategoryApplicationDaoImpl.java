
package com.bootx.mall.product.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.dao.CategoryApplicationDao;
import com.bootx.mall.product.entity.CategoryApplication;
import com.bootx.mall.product.entity.ProductCategory;
import com.bootx.mall.product.entity.Store;
import org.springframework.stereotype.Repository;

/**
 * Dao - 经营分类申请
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class CategoryApplicationDaoImpl extends BaseDaoImpl<CategoryApplication, Long> implements CategoryApplicationDao {

	@Override
	public List<CategoryApplication> findList(Store store, ProductCategory productCategory, CategoryApplication.Status status) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CategoryApplication> criteriaQuery = criteriaBuilder.createQuery(CategoryApplication.class);
		Root<CategoryApplication> root = criteriaQuery.from(CategoryApplication.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productCategory"), productCategory));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery);
	}

	@Override
	public Page<CategoryApplication> findPage(CategoryApplication.Status status, Store store, ProductCategory productCategory, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CategoryApplication> criteriaQuery = criteriaBuilder.createQuery(CategoryApplication.class);
		Root<CategoryApplication> root = criteriaQuery.from(CategoryApplication.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productCategory"), productCategory));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Long count(CategoryApplication.Status status, Store store, ProductCategory productCategory) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CategoryApplication> criteriaQuery = criteriaBuilder.createQuery(CategoryApplication.class);
		Root<CategoryApplication> root = criteriaQuery.from(CategoryApplication.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productCategory"), productCategory));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

}