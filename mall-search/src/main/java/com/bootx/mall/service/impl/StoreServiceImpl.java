
package com.bootx.mall.service.impl;

import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.dao.ProductDao;
import com.bootx.mall.dao.StoreDao;
import com.bootx.mall.entity.*;
import com.bootx.mall.plugin.MoneyOffPromotionPlugin;
import com.bootx.mall.plugin.PromotionPlugin;
import com.bootx.mall.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Service - 店铺
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class StoreServiceImpl extends BaseServiceImpl<Store, Long> implements StoreService {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private StoreDao storeDao;
	@Inject
	private ProductDao productDao;
	@Inject
	private UserService userService;
	@Inject
	private BusinessService businessService;
	@Inject
	private MailService mailService;
	@Inject
	private SmsService smsService;
	@Inject
	private AftersalesSettingService aftersalesSettingService;
	@Inject
	private StorePluginStatusService storePluginStatusService;

	@Override
	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		return storeDao.exists("name", name, true);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean nameUnique(Long id, String name) {
		return storeDao.unique(id, "name", name, true);
	}

	@Override
	public boolean productCategoryExists(Store store, final ProductCategory productCategory) {
		Assert.notNull(productCategory, "[Assertion failed] - productCategory is required; it must not be null");
		Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");

		return CollectionUtils.exists(store.getProductCategories(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				ProductCategory storeProductCategory = (ProductCategory) object;
				return storeProductCategory != null && storeProductCategory == productCategory;
			}
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Store findByName(String name) {
		return storeDao.find("name", name, true);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Store> findList(Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired, Integer first, Integer count) {
		return storeDao.findList(type, status, isEnabled, hasExpired, first, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findProductCategoryList(Store store, CategoryApplication.Status status) {
		return storeDao.findProductCategoryList(store, status);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Store> findPage(Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired, Pageable pageable) {
		return storeDao.findPage(type, status, isEnabled, hasExpired, pageable);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Store> search(String keyword, Pageable pageable) {
		if (StringUtils.isEmpty(keyword)) {
			return Page.emptyPage(pageable);
		}

		if (pageable == null) {
			pageable = new Pageable();
		}

		return Page.emptyPage(pageable);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Store> search(String keyword) {
		if (StringUtils.isEmpty(keyword)) {
			return Collections.EMPTY_LIST;
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	@Transactional(readOnly = true)
	public Store getCurrent() {
		Business currentUser = userService.getCurrent(Business.class);
		return currentUser != null ? currentUser.getStore() : null;
	}

	@Override
	@CacheEvict(value = "authorization", allEntries = true)
	public void addEndDays(Store store, int amount) {
		Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");

		if (amount == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(storeDao.getLockMode(store))) {
			storeDao.flush();
			storeDao.refresh(store, LockModeType.PESSIMISTIC_WRITE);
		}

		Date now = new Date();
		Date currentEndDate = store.getEndDate();
		if (amount > 0) {
			store.setEndDate(DateUtils.addDays(currentEndDate.after(now) ? currentEndDate : now, amount));
		} else {
			store.setEndDate(DateUtils.addDays(currentEndDate, amount));
		}
		storeDao.flush();
	}

	@Override
	@CacheEvict(value = "authorization", allEntries = true)
	public void addBailPaid(Store store, BigDecimal amount) {
		Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");
		Assert.notNull(amount, "[Assertion failed] - amount is required; it must not be null");

		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(storeDao.getLockMode(store))) {
			storeDao.flush();
			storeDao.refresh(store, LockModeType.PESSIMISTIC_WRITE);
		}

		Assert.notNull(store.getBailPaid(), "[Assertion failed] - store bailPaid is required; it must not be null");
		Assert.state(store.getBailPaid().add(amount).compareTo(BigDecimal.ZERO) >= 0, "[Assertion failed] - store bailPaid must be equal or greater than 0");

		store.setBailPaid(store.getBailPaid().add(amount));
		storeDao.flush();
	}

	@Override
	@CacheEvict(value = "authorization", allEntries = true)
	public void review(Store store, boolean passed, String content) {
		Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");
		Assert.state(Store.Status.PENDING.equals(store.getStatus()), "[Assertion failed] - store status must be PENDING");
		Assert.state(passed || StringUtils.isNotEmpty(content), "[Assertion failed] - passed or content must not be empty");

		if (passed) {
			BigDecimal serviceFee = store.getStoreRank().getServiceFee();
			BigDecimal bail = store.getStoreCategory().getBail();
			if (serviceFee.compareTo(BigDecimal.ZERO) <= 0 && bail.compareTo(BigDecimal.ZERO) <= 0) {
				store.setStatus(Store.Status.SUCCESS);
				store.setEndDate(DateUtils.addYears(new Date(), 1));
			} else {
				store.setStatus(Store.Status.APPROVED);
				store.setEndDate(new Date());
			}
			AftersalesSetting aftersalesSetting = new AftersalesSetting();
			aftersalesSetting.setStore(store);
			aftersalesSettingService.save(aftersalesSetting);

			smsService.sendApprovalStoreSms(store);
			mailService.sendApprovalStoreMail(store);
		} else {
			store.setStatus(Store.Status.FAILED);
			smsService.sendFailStoreSms(store, content);
			mailService.sendFailStoreMail(store, content);
		}
	}

	@Override
	public void buy(Store store, PromotionPlugin promotionPlugin, int months) {
		Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");
		Assert.notNull(promotionPlugin, "[Assertion failed] - promotionPlugin is required; it must not be null");
		Assert.state(promotionPlugin.getIsEnabled(), "[Assertion failed] - promotionPlugin must be enabled");
		Assert.state(months > 0, "[Assertion failed] - months must be greater than 0");

		BigDecimal amount = promotionPlugin.getServiceCharge().multiply(new BigDecimal(months));
		Business business = store.getBusiness();
		Assert.state(business.getBalance() != null && business.getBalance().compareTo(amount) >= 0, "[Assertion failed] - business balance must not be null and be greater than 0");

		int days = months * 30;
		if (promotionPlugin instanceof MoneyOffPromotionPlugin) {
			StorePluginStatus storePluginStatus = storePluginStatusService.find(store, promotionPlugin.getId());
			if (storePluginStatus != null) {
				storePluginStatusService.addPluginEndDays(storePluginStatus, days);
			} else {
				storePluginStatusService.create(store, promotionPlugin.getId(), days);
			}
			businessService.addBalance(business, amount.negate(), BusinessDepositLog.Type.SVC_PAYMENT, null);
		}
	}

	@Override
	@CacheEvict(value = { "authorization", "product", "productCategory" }, allEntries = true)
	public void expiredStoreProcessing() {
		productDao.refreshExpiredStoreProductActive();
	}

	@Override
	@Transactional
	@CacheEvict(value = { "authorization", "product", "productCategory" }, allEntries = true)
	public Store update(Store store) {
		productDao.refreshActive(store);

		return super.update(store);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "authorization", "product", "productCategory" }, allEntries = true)
	public Store update(Store store, String... ignoreProperties) {
		return super.update(store, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "authorization", "product", "productCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "authorization", "product", "productCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "authorization", "product", "productCategory" }, allEntries = true)
	public void delete(Store store) {
		super.delete(store);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal bailPaidTotalAmount() {
		return storeDao.bailPaidTotalAmount();
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired) {
		return storeDao.count(type, status, isEnabled, hasExpired);
	}

}