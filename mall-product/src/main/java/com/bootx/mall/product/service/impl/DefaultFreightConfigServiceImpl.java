
package com.bootx.mall.product.service.impl;

import javax.inject.Inject;

import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.dao.DefaultFreightConfigDao;
import com.bootx.mall.product.entity.Area;
import com.bootx.mall.product.entity.DefaultFreightConfig;
import com.bootx.mall.product.entity.ShippingMethod;
import com.bootx.mall.product.entity.Store;
import com.bootx.mall.product.service.DefaultFreightConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 默认运费配置
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class DefaultFreightConfigServiceImpl extends BaseServiceImpl<DefaultFreightConfig, Long> implements DefaultFreightConfigService {

	@Inject
	private DefaultFreightConfigDao defaultFreightConfigDao;

	@Override
	@Transactional(readOnly = true)
	public boolean exists(ShippingMethod shippingMethod, Area area) {
		return defaultFreightConfigDao.exists(shippingMethod, area);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean unique(ShippingMethod shippingMethod, Area previousArea, Area currentArea) {
		return (previousArea != null && previousArea.equals(currentArea)) || !defaultFreightConfigDao.exists(shippingMethod, currentArea);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<DefaultFreightConfig> findPage(Store store, Pageable pageable) {
		return defaultFreightConfigDao.findPage(store, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public DefaultFreightConfig find(ShippingMethod shippingMethod, Store store) {
		return defaultFreightConfigDao.find(shippingMethod, store);
	}

	@Override
	public void update(DefaultFreightConfig defaultFreightConfig, Store store, ShippingMethod shippingMethod) {
		Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");
		Assert.notNull(shippingMethod, "[Assertion failed] - shippingMethod is required; it must not be null");
		if (!defaultFreightConfig.isNew()) {
			super.update(defaultFreightConfig);
		} else {
			defaultFreightConfig.setStore(store);
			defaultFreightConfig.setShippingMethod(shippingMethod);
			super.save(defaultFreightConfig);
		}
	}

}