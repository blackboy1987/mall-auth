
package com.bootx.mall.product.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.LockModeType;

import com.bootx.mall.product.common.Setting;
import com.bootx.mall.product.dao.PaymentTransactionDao;
import com.bootx.mall.product.dao.SnDao;
import com.bootx.mall.product.plugin.PaymentPlugin;
import com.bootx.mall.product.service.BusinessService;
import com.bootx.mall.product.service.OrderService;
import com.bootx.mall.product.service.ProductService;
import com.bootx.mall.product.util.SystemUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bootx.mall.product.entity.Business;
import com.bootx.mall.product.entity.BusinessDepositLog;
import com.bootx.mall.product.entity.Member;
import com.bootx.mall.product.entity.MemberDepositLog;
import com.bootx.mall.product.entity.Order;
import com.bootx.mall.product.entity.OrderPayment;
import com.bootx.mall.product.entity.PaymentItem;
import com.bootx.mall.product.entity.PaymentMethod;
import com.bootx.mall.product.entity.PaymentTransaction;
import com.bootx.mall.product.entity.PlatformSvc;
import com.bootx.mall.product.entity.PromotionPluginSvc;
import com.bootx.mall.product.entity.Sn;
import com.bootx.mall.product.entity.Store;
import com.bootx.mall.product.entity.StorePluginStatus;
import com.bootx.mall.product.entity.Svc;
import com.bootx.mall.product.entity.User;
import com.bootx.mall.product.service.MemberService;
import com.bootx.mall.product.service.PaymentTransactionService;
import com.bootx.mall.product.service.StorePluginStatusService;
import com.bootx.mall.product.service.StoreService;
import com.bootx.mall.product.service.SvcService;
import com.bootx.mall.product.service.UserService;

/**
 * Service - 支付事务
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class PaymentTransactionServiceImpl extends BaseServiceImpl<PaymentTransaction, Long> implements PaymentTransactionService {

	@Inject
	private PaymentTransactionDao paymentTransactionDao;
	@Inject
	private SnDao snDao;
	@Inject
	private ProductService productService;
	@Inject
	private OrderService orderService;
	@Inject
	private UserService userService;
	@Inject
	private MemberService memberService;
	@Inject
	private BusinessService businessService;
	@Inject
	private StoreService storeService;
	@Inject
	private SvcService svcService;
	@Inject
	private StorePluginStatusService storePluginStatusService;

	@Override
	@Transactional(readOnly = true)
	public PaymentTransaction findBySn(String sn) {
		return paymentTransactionDao.find("sn", StringUtils.lowerCase(sn));
	}

	@Override
	public PaymentTransaction generate(PaymentTransaction.LineItem lineItem, PaymentPlugin paymentPlugin, String rePayUrl) {
		Assert.notNull(lineItem, "[Assertion failed] - lineItem is required; it must not be null");
		Assert.notNull(paymentPlugin, "[Assertion failed] - paymentPlugin is required; it must not be null");
		Assert.notNull(rePayUrl, "[Assertion failed] - rePayUrl is required; it must not be null");
		Assert.notNull(lineItem.getAmount(), "[Assertion failed] - lineItem amount is required; it must not be null");
		Assert.notNull(lineItem.getType(), "[Assertion failed] - lineItem type is required; it must not be null");
		Assert.notNull(lineItem.getTarget(), "[Assertion failed] - lineItem target is required; it must not be null");

		PaymentTransaction paymentTransaction = paymentTransactionDao.findAvailable(lineItem, paymentPlugin);
		if (paymentTransaction == null) {
			paymentTransaction = new PaymentTransaction();
			paymentTransaction.setSn(snDao.generate(Sn.Type.PAYMENT_TRANSACTION));
			paymentTransaction.setType(lineItem.getType());
			paymentTransaction.setAmount(paymentPlugin.calculateAmount(lineItem.getAmount()));
			paymentTransaction.setFee(paymentPlugin.calculateFee(lineItem.getAmount()));
			paymentTransaction.setIsSuccess(false);
			paymentTransaction.setExpire(DateUtils.addSeconds(new Date(), paymentPlugin.getTimeout()));
			paymentTransaction.setParent(null);
			paymentTransaction.setChildren(null);
			paymentTransaction.setTarget(lineItem.getTarget());
			paymentTransaction.setPaymentPlugin(paymentPlugin);
			paymentTransaction.setRePayUrl(rePayUrl);
			paymentTransactionDao.persist(paymentTransaction);
		}
		return paymentTransaction;
	}

	@Override
	public PaymentTransaction generateParent(Collection<PaymentTransaction.LineItem> lineItems, PaymentPlugin paymentPlugin, String rePayUrl) {
		Assert.notEmpty(lineItems, "[Assertion failed] - lineItems must not be empty: it must contain at least 1 element");
		Assert.notNull(paymentPlugin, "[Assertion failed] - paymentPlugin is required; it must not be null");
		Assert.notNull(rePayUrl, "[Assertion failed] - rePayUrl is required; it must not be null");
		Assert.isTrue(lineItems.size() > 1, "[Assertion failed] - lineItems size must be greater than 1");

		PaymentTransaction parentPaymentTransaction = paymentTransactionDao.findAvailableParent(lineItems, paymentPlugin);
		if (parentPaymentTransaction == null) {
			BigDecimal amount = BigDecimal.ZERO;
			for (PaymentTransaction.LineItem lineItem : lineItems) {
				Assert.notNull(lineItem, "[Assertion failed] - lineItem is required; it must not be null");
				Assert.notNull(lineItem.getAmount(), "[Assertion failed] - lineItem amount is required; it must not be null");
				Assert.notNull(lineItem.getType(), "[Assertion failed] - lineItem type is required; it must not be null");
				Assert.notNull(lineItem.getTarget(), "[Assertion failed] - lineItem target is required; it must not be null");

				amount = amount.add(lineItem.getAmount());
			}

			parentPaymentTransaction = new PaymentTransaction();
			parentPaymentTransaction.setSn(snDao.generate(Sn.Type.PAYMENT_TRANSACTION));
			parentPaymentTransaction.setType(null);
			parentPaymentTransaction.setAmount(paymentPlugin.calculateAmount(amount));
			parentPaymentTransaction.setFee(paymentPlugin.calculateFee(amount));
			parentPaymentTransaction.setIsSuccess(false);
			parentPaymentTransaction.setExpire(DateUtils.addSeconds(new Date(), paymentPlugin.getTimeout()));
			parentPaymentTransaction.setParent(null);
			parentPaymentTransaction.setChildren(null);
			parentPaymentTransaction.setTarget(null);
			parentPaymentTransaction.setPaymentPlugin(paymentPlugin);
			parentPaymentTransaction.setRePayUrl(rePayUrl);
			paymentTransactionDao.persist(parentPaymentTransaction);
			for (PaymentTransaction.LineItem lineItem : lineItems) {
				Assert.notNull(lineItem, "[Assertion failed] - lineItem is required; it must not be null");
				Assert.notNull(lineItem.getAmount(), "[Assertion failed] - lineItem amount is required; it must not be null");
				Assert.notNull(lineItem.getType(), "[Assertion failed] - lineItem type is required; it must not be null");
				Assert.notNull(lineItem.getTarget(), "[Assertion failed] - lineItem target is required; it must not be null");

				PaymentTransaction paymentTransaction = new PaymentTransaction();
				paymentTransaction.setSn(snDao.generate(Sn.Type.PAYMENT_TRANSACTION));
				paymentTransaction.setType(lineItem.getType());
				paymentTransaction.setAmount(paymentPlugin.calculateAmount(lineItem.getAmount()));
				paymentTransaction.setFee(paymentPlugin.calculateFee(lineItem.getAmount()));
				paymentTransaction.setIsSuccess(null);
				paymentTransaction.setExpire(null);
				paymentTransaction.setChildren(null);
				paymentTransaction.setTarget(lineItem.getTarget());
				paymentTransaction.setPaymentPlugin(null);
				paymentTransaction.setParent(parentPaymentTransaction);
				paymentTransactionDao.persist(paymentTransaction);
			}
		}
		return parentPaymentTransaction;
	}

	@Override
	public void handle(PaymentTransaction paymentTransaction) {
		Assert.notNull(paymentTransaction, "[Assertion failed] - paymentTransaction is required; it must not be null");

		if (!LockModeType.PESSIMISTIC_WRITE.equals(paymentTransactionDao.getLockMode(paymentTransaction))) {
			paymentTransactionDao.flush();
			paymentTransactionDao.refresh(paymentTransaction, LockModeType.PESSIMISTIC_WRITE);
		}

		if (BooleanUtils.isNotFalse(paymentTransaction.getIsSuccess())) {
			return;
		}

		Set<PaymentTransaction> paymentTransactions = new HashSet<>();
		Set<PaymentTransaction> childrenList = paymentTransaction.getChildren();
		if (CollectionUtils.isNotEmpty(childrenList)) {
			paymentTransaction.setIsSuccess(true);
			paymentTransactions = childrenList;
		} else {
			paymentTransactions.add(paymentTransaction);
		}

		for (PaymentTransaction transaction : paymentTransactions) {
			Svc svc = transaction.getSvc();
			Store store = transaction.getStore();
			User user = transaction.getUser();
			BigDecimal effectiveAmount = transaction.getEffectiveAmount();

			Assert.notNull(transaction.getType(), "[Assertion failed] - transaction type is required; it must not be null");
			switch (transaction.getType()) {
			case ORDER_PAYMENT:
				Order order = transaction.getOrder();
				if (order != null) {
					OrderPayment orderPayment = new OrderPayment();
					orderPayment.setMethod(OrderPayment.Method.ONLINE);
					orderPayment.setPaymentMethod(transaction.getPaymentPluginName());
					orderPayment.setAmount(transaction.getAmount());
					orderPayment.setFee(transaction.getFee());
					orderPayment.setOrder(order);
					orderService.payment(order, orderPayment);
				}
				break;
			case SVC_PAYMENT:
				if (svc == null || svc.getStore() == null) {
					break;
				}
				store = svc.getStore();

				Integer durationDays = svc.getDurationDays();
				if (svc instanceof PlatformSvc) {
					storeService.addEndDays(store, durationDays);
					if (Store.Status.APPROVED.equals(store.getStatus()) && !store.hasExpired() && store.getBailPayable().compareTo(BigDecimal.ZERO) == 0) {
						store.setStatus(Store.Status.SUCCESS);
					} else {
						productService.refreshActive(store);
					}
				} else if (svc instanceof PromotionPluginSvc) {
					String promotionPluginId = ((PromotionPluginSvc) svc).getPromotionPluginId();
					StorePluginStatus storePluginStatus = storePluginStatusService.find(store, promotionPluginId);
					if (storePluginStatus != null) {
						storePluginStatusService.addPluginEndDays(storePluginStatus, durationDays);
					} else {
						storePluginStatusService.create(store, promotionPluginId, durationDays);
					}
				}
				break;
			case DEPOSIT_RECHARGE:
				if (user instanceof Member) {
					memberService.addBalance((Member) user, effectiveAmount, MemberDepositLog.Type.RECHARGE, null);
				} else if (user instanceof Business) {
					businessService.addBalance((Business) user, effectiveAmount, BusinessDepositLog.Type.RECHARGE, null);
				}
				break;
			case BAIL_PAYMENT:
				if (store == null) {
					break;
				}

				storeService.addBailPaid(store, effectiveAmount);
				if (Store.Status.APPROVED.equals(store.getStatus()) && !store.hasExpired() && store.getBailPayable().compareTo(BigDecimal.ZERO) == 0) {
					store.setStatus(Store.Status.SUCCESS);
				} else {
					productService.refreshActive(store);
				}
				break;
			}
			transaction.setIsSuccess(true);
		}
	}

	@Override
	public PaymentTransaction.LineItem generate(PaymentItem paymentItem) {
		if (paymentItem == null || paymentItem.getType() == null) {
			return null;
		}
		Setting setting = SystemUtils.getSetting();
		User user = userService.getCurrent();
		switch (paymentItem.getType()) {
		case ORDER_PAYMENT:
			Member member = (Member) user;
			if (member == null) {
				return null;
			}
			Order order = orderService.findBySn(paymentItem.getOrderSn());
			if (order == null || !member.equals(order.getMember()) || !orderService.acquireLock(order, member)) {
				return null;
			}
			if (order.getPaymentMethod() == null || !PaymentMethod.Method.ONLINE.equals(order.getPaymentMethod().getMethod())) {
				return null;
			}
			if (order.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0) {
				return null;
			}
			return new PaymentTransaction.OrderLineItem(order);
		case SVC_PAYMENT:
			Svc svc = svcService.findBySn(paymentItem.getSvcSn());
			if (svc == null) {
				return null;
			}
			if (svc.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
				return null;
			}
			return new PaymentTransaction.SvcLineItem(svc);
		case DEPOSIT_RECHARGE:
			if (user == null) {
				return null;
			}
			if (paymentItem.getAmount() == null || paymentItem.getAmount().compareTo(BigDecimal.ZERO) <= 0 || paymentItem.getAmount().precision() > 15 || paymentItem.getAmount().scale() > setting.getPriceScale()) {
				return null;
			}
			if (user instanceof Member || user instanceof Business) {
				return new PaymentTransaction.DepositRechargerLineItem(user, paymentItem.getAmount());
			} else {
				return null;
			}
		case BAIL_PAYMENT:
			Store store = storeService.getCurrent();
			if (store == null) {
				return null;
			}
			if (paymentItem.getAmount() == null || paymentItem.getAmount().compareTo(BigDecimal.ZERO) <= 0 || paymentItem.getAmount().precision() > 15 || paymentItem.getAmount().scale() > setting.getPriceScale()) {
				return null;
			}
			return new PaymentTransaction.BailPaymentLineItem(store, paymentItem.getAmount());
		}
		return null;
	}

	@Override
	@Transactional
	public PaymentTransaction save(PaymentTransaction paymentTransaction) {
		Assert.notNull(paymentTransaction, "[Assertion failed] - paymentTransaction is required; it must not be null");
		paymentTransaction.setSn(snDao.generate(Sn.Type.PAYMENT_TRANSACTION));

		return super.save(paymentTransaction);
	}

}