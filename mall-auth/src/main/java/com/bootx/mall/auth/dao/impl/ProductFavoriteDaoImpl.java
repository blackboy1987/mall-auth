
package com.bootx.mall.auth.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.dao.ProductFavoriteDao;
import com.bootx.mall.auth.entity.Member;
import com.bootx.mall.auth.entity.Product;
import com.bootx.mall.auth.entity.ProductFavorite;
import org.springframework.stereotype.Repository;

/**
 * Dao - 商品收藏
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class ProductFavoriteDaoImpl extends BaseDaoImpl<ProductFavorite, Long> implements ProductFavoriteDao {

	@Override
	public boolean exists(Member member, Product product) {
		String jpql = "select count(*) from ProductFavorite productFavorite where productFavorite.member = :member and productFavorite.product = :product";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("member", member).setParameter("product", product).getSingleResult();
		return count > 0;
	}

	@Override
	public List<ProductFavorite> findList(Member member, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductFavorite> criteriaQuery = criteriaBuilder.createQuery(ProductFavorite.class);
		Root<ProductFavorite> root = criteriaQuery.from(ProductFavorite.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	@Override
	public Page<ProductFavorite> findPage(Member member, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductFavorite> criteriaQuery = criteriaBuilder.createQuery(ProductFavorite.class);
		Root<ProductFavorite> root = criteriaQuery.from(ProductFavorite.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Long count(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductFavorite> criteriaQuery = criteriaBuilder.createQuery(ProductFavorite.class);
		Root<ProductFavorite> root = criteriaQuery.from(ProductFavorite.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery);
	}

}