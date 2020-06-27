
package com.bootx.mall.dao.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.bootx.mall.common.Filter;
import com.bootx.mall.common.Order;
import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.dao.OrderDao;
import org.springframework.stereotype.Repository;

import com.bootx.mall.entity.Member;
import com.bootx.mall.entity.OrderItem;
import com.bootx.mall.entity.PaymentMethod;
import com.bootx.mall.entity.Product;
import com.bootx.mall.entity.Sku;
import com.bootx.mall.entity.Store;

/**
 * Dao - 订单
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class OrderDaoImpl extends BaseDaoImpl<com.bootx.mall.entity.Order, Long> implements OrderDao {

	@Override
	public List<com.bootx.mall.entity.Order> findList(com.bootx.mall.entity.Order.Type type, com.bootx.mall.entity.Order.Status status, Store store, Member member, Product product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, Integer count, List<Filter> filters,
                                                      List<Order> orders) {
		return findList(type, status, store, member, product, isPendingReceive, isPendingRefunds, isUseCouponCode, isExchangePoint, isAllocatedStock, hasExpired, null, count, filters, orders);
	}

	@Override
	public List<com.bootx.mall.entity.Order> findList(com.bootx.mall.entity.Order.Type type, com.bootx.mall.entity.Order.Status status, Store store, Member member, Product product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, Integer first, Integer count,
                                                      List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<com.bootx.mall.entity.Order> criteriaQuery = criteriaBuilder.createQuery(com.bootx.mall.entity.Order.class);
		Root<com.bootx.mall.entity.Order> root = criteriaQuery.from(com.bootx.mall.entity.Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (product != null) {
			Subquery<Sku> skuSubquery = criteriaQuery.subquery(Sku.class);
			Root<Sku> skuSubqueryRoot = skuSubquery.from(Sku.class);
			skuSubquery.select(skuSubqueryRoot);
			skuSubquery.where(criteriaBuilder.equal(skuSubqueryRoot.get("product"), product));

			Subquery<OrderItem> orderItemSubquery = criteriaQuery.subquery(OrderItem.class);
			Root<OrderItem> orderItemSubqueryRoot = orderItemSubquery.from(OrderItem.class);
			orderItemSubquery.select(orderItemSubqueryRoot);
			orderItemSubquery.where(criteriaBuilder.equal(orderItemSubqueryRoot.get("order"), root), criteriaBuilder.in(orderItemSubqueryRoot.get("sku")).value(skuSubquery));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(orderItemSubquery));
		}
		if (isPendingReceive != null) {
			Predicate predicate = criteriaBuilder.and(criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("paymentMethodType"), PaymentMethod.Type.CASH_ON_DELIVERY),
					criteriaBuilder.notEqual(root.get("status"), com.bootx.mall.entity.Order.Status.COMPLETED), criteriaBuilder.notEqual(root.get("status"), com.bootx.mall.entity.Order.Status.FAILED), criteriaBuilder.notEqual(root.get("status"), com.bootx.mall.entity.Order.Status.CANCELED), criteriaBuilder.notEqual(root.get("status"), com.bootx.mall.entity.Order.Status.DENIED),
					criteriaBuilder.lessThan(root.<BigDecimal>get("amountPaid"), root.<BigDecimal>get("amount")));
			if (isPendingReceive) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isPendingRefunds != null) {
			Predicate predicate = criteriaBuilder.or(
					criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.and(root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("status"), com.bootx.mall.entity.Order.Status.FAILED),
							criteriaBuilder.equal(root.get("status"), com.bootx.mall.entity.Order.Status.CANCELED), criteriaBuilder.equal(root.get("status"), com.bootx.mall.entity.Order.Status.DENIED)), criteriaBuilder.greaterThan(root.<BigDecimal>get("amountPaid"), BigDecimal.ZERO)),
					criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), com.bootx.mall.entity.Order.Status.COMPLETED), criteriaBuilder.greaterThan(root.<BigDecimal>get("amountPaid"), root.<BigDecimal>get("amount"))));
			if (isPendingRefunds) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isUseCouponCode != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isUseCouponCode"), isUseCouponCode));
		}
		if (isExchangePoint != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isExchangePoint"), isExchangePoint));
		}
		if (isAllocatedStock != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isAllocatedStock"), isAllocatedStock));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, first, count, filters, orders);
	}

	@Override
	public Page<com.bootx.mall.entity.Order> findPage(com.bootx.mall.entity.Order.Type type, com.bootx.mall.entity.Order.Status status, Store store, Member member, Product product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<com.bootx.mall.entity.Order> criteriaQuery = criteriaBuilder.createQuery(com.bootx.mall.entity.Order.class);
		Root<com.bootx.mall.entity.Order> root = criteriaQuery.from(com.bootx.mall.entity.Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (product != null) {
			Subquery<Sku> skuSubquery = criteriaQuery.subquery(Sku.class);
			Root<Sku> skuSubqueryRoot = skuSubquery.from(Sku.class);
			skuSubquery.select(skuSubqueryRoot);
			skuSubquery.where(criteriaBuilder.equal(skuSubqueryRoot.get("product"), product));

			Subquery<OrderItem> orderItemSubquery = criteriaQuery.subquery(OrderItem.class);
			Root<OrderItem> orderItemSubqueryRoot = orderItemSubquery.from(OrderItem.class);
			orderItemSubquery.select(orderItemSubqueryRoot);
			orderItemSubquery.where(criteriaBuilder.equal(orderItemSubqueryRoot.get("order"), root), criteriaBuilder.in(orderItemSubqueryRoot.get("sku")).value(skuSubquery));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(orderItemSubquery));
		}
		if (isPendingReceive != null) {
			Predicate predicate = criteriaBuilder.and(criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("paymentMethodType"), PaymentMethod.Type.CASH_ON_DELIVERY),
					criteriaBuilder.notEqual(root.get("status"), com.bootx.mall.entity.Order.Status.COMPLETED), criteriaBuilder.notEqual(root.get("status"), com.bootx.mall.entity.Order.Status.FAILED), criteriaBuilder.notEqual(root.get("status"), com.bootx.mall.entity.Order.Status.CANCELED), criteriaBuilder.notEqual(root.get("status"), com.bootx.mall.entity.Order.Status.DENIED),
					criteriaBuilder.lessThan(root.<BigDecimal>get("amountPaid"), root.<BigDecimal>get("amount")));
			if (isPendingReceive) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isPendingRefunds != null) {
			Predicate predicate = criteriaBuilder.or(
					criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.and(root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("status"), com.bootx.mall.entity.Order.Status.FAILED),
							criteriaBuilder.equal(root.get("status"), com.bootx.mall.entity.Order.Status.CANCELED), criteriaBuilder.equal(root.get("status"), com.bootx.mall.entity.Order.Status.DENIED)), criteriaBuilder.greaterThan(root.<BigDecimal>get("amountPaid"), BigDecimal.ZERO)),
					criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), com.bootx.mall.entity.Order.Status.COMPLETED), criteriaBuilder.greaterThan(root.<BigDecimal>get("amountPaid"), root.<BigDecimal>get("amount"))));
			if (isPendingRefunds) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isUseCouponCode != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isUseCouponCode"), isUseCouponCode));
		}
		if (isExchangePoint != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isExchangePoint"), isExchangePoint));
		}
		if (isAllocatedStock != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isAllocatedStock"), isAllocatedStock));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Long count(com.bootx.mall.entity.Order.Type type, com.bootx.mall.entity.Order.Status status, Store store, Member member, Product product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<com.bootx.mall.entity.Order> criteriaQuery = criteriaBuilder.createQuery(com.bootx.mall.entity.Order.class);
		Root<com.bootx.mall.entity.Order> root = criteriaQuery.from(com.bootx.mall.entity.Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (product != null) {
			Subquery<Sku> skuSubquery = criteriaQuery.subquery(Sku.class);
			Root<Sku> skuSubqueryRoot = skuSubquery.from(Sku.class);
			skuSubquery.select(skuSubqueryRoot);
			skuSubquery.where(criteriaBuilder.equal(skuSubqueryRoot.get("product"), product));

			Subquery<OrderItem> orderItemSubquery = criteriaQuery.subquery(OrderItem.class);
			Root<OrderItem> orderItemSubqueryRoot = orderItemSubquery.from(OrderItem.class);
			orderItemSubquery.select(orderItemSubqueryRoot);
			orderItemSubquery.where(criteriaBuilder.equal(orderItemSubqueryRoot.get("order"), root), criteriaBuilder.in(orderItemSubqueryRoot.get("sku")).value(skuSubquery));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(orderItemSubquery));
		}
		if (isPendingReceive != null) {
			Predicate predicate = criteriaBuilder.and(criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("paymentMethodType"), PaymentMethod.Type.CASH_ON_DELIVERY),
					criteriaBuilder.notEqual(root.get("status"), com.bootx.mall.entity.Order.Status.COMPLETED), criteriaBuilder.notEqual(root.get("status"), com.bootx.mall.entity.Order.Status.FAILED), criteriaBuilder.notEqual(root.get("status"), com.bootx.mall.entity.Order.Status.CANCELED), criteriaBuilder.notEqual(root.get("status"), com.bootx.mall.entity.Order.Status.DENIED),
					criteriaBuilder.lessThan(root.<BigDecimal>get("amountPaid"), root.<BigDecimal>get("amount")));
			if (isPendingReceive) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isPendingRefunds != null) {
			Predicate predicate = criteriaBuilder.or(
					criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.and(root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("status"), com.bootx.mall.entity.Order.Status.FAILED),
							criteriaBuilder.equal(root.get("status"), com.bootx.mall.entity.Order.Status.CANCELED), criteriaBuilder.equal(root.get("status"), com.bootx.mall.entity.Order.Status.DENIED)), criteriaBuilder.greaterThan(root.<BigDecimal>get("amountPaid"), BigDecimal.ZERO)),
					criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), com.bootx.mall.entity.Order.Status.COMPLETED), criteriaBuilder.greaterThan(root.<BigDecimal>get("amountPaid"), root.<BigDecimal>get("amount"))));
			if (isPendingRefunds) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isUseCouponCode != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isUseCouponCode"), isUseCouponCode));
		}
		if (isExchangePoint != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isExchangePoint"), isExchangePoint));
		}
		if (isAllocatedStock != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isAllocatedStock"), isAllocatedStock));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	@Override
	public Long createOrderCount(Store store, Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<com.bootx.mall.entity.Order> criteriaQuery = criteriaBuilder.createQuery(com.bootx.mall.entity.Order.class);
		Root<com.bootx.mall.entity.Order> root = criteriaQuery.from(com.bootx.mall.entity.Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	@Override
	public Long completeOrderCount(Store store, Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<com.bootx.mall.entity.Order> criteriaQuery = criteriaBuilder.createQuery(com.bootx.mall.entity.Order.class);
		Root<com.bootx.mall.entity.Order> root = criteriaQuery.from(com.bootx.mall.entity.Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("completeDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("completeDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	@Override
	public BigDecimal createOrderAmount(Store store, Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<com.bootx.mall.entity.Order> root = criteriaQuery.from(com.bootx.mall.entity.Order.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("amount")));
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
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
	public BigDecimal completeOrderAmount(Store store, Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<com.bootx.mall.entity.Order> root = criteriaQuery.from(com.bootx.mall.entity.Order.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("amount")));
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("completeDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("completeDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		BigDecimal result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result != null ? result : BigDecimal.ZERO;
	}

	@Override
	public BigDecimal grantedCommissionTotalAmount(Store store, com.bootx.mall.entity.Order.CommissionType commissionType, Date beginDate, Date endDate, com.bootx.mall.entity.Order.Status... statuses) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
		switch (commissionType) {
		case PLATFORM:
			criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("platformCommissionTotals")));
			break;
		case DISTRIBUTION:
			criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("distributionCommissionTotals")));
			break;
		}
		Predicate restrictions = criteriaBuilder.conjunction();

		Subquery<com.bootx.mall.entity.Order> orderSubquery = criteriaQuery.subquery(com.bootx.mall.entity.Order.class);
		Root<com.bootx.mall.entity.Order> orderSubqueryRoot = orderSubquery.from(com.bootx.mall.entity.Order.class);
		orderSubquery.select(orderSubqueryRoot);
		Predicate orderSubqueryRestrictions = criteriaBuilder.conjunction();
		if (store != null) {
			orderSubqueryRestrictions = criteriaBuilder.and(orderSubqueryRestrictions, criteriaBuilder.equal(orderSubqueryRoot.get("store"), store));
		}
		if (beginDate != null) {
			orderSubqueryRestrictions = criteriaBuilder.and(orderSubqueryRestrictions, criteriaBuilder.greaterThanOrEqualTo(orderSubqueryRoot.<Date>get("completeDate"), beginDate));
		}
		if (endDate != null) {
			orderSubqueryRestrictions = criteriaBuilder.and(orderSubqueryRestrictions, criteriaBuilder.lessThanOrEqualTo(orderSubqueryRoot.<Date>get("completeDate"), endDate));
		}
		if (statuses != null) {
			orderSubqueryRestrictions = criteriaBuilder.and(orderSubqueryRestrictions, criteriaBuilder.in(orderSubqueryRoot.get("status")).value(Arrays.asList(statuses)));
		}
		orderSubquery.where(orderSubqueryRestrictions);

		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("order")).value(orderSubquery));
		criteriaQuery.where(restrictions);
		BigDecimal result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result != null ? result : BigDecimal.ZERO;
	}

}