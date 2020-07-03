
package com.bootx.mall.product.service.impl;

import javax.inject.Inject;

import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.dao.AreaFreightConfigDao;
import com.bootx.mall.product.entity.Area;
import com.bootx.mall.product.entity.AreaFreightConfig;
import com.bootx.mall.product.entity.ShippingMethod;
import com.bootx.mall.product.entity.Store;
import com.bootx.mall.product.service.AreaFreightConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 地区运费配置
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class AreaFreightConfigServiceImpl extends BaseServiceImpl<AreaFreightConfig, Long> implements AreaFreightConfigService {

	@Inject
	private AreaFreightConfigDao areaFreightConfigDao;

	@Override
	@Transactional(readOnly = true)
	public boolean exists(ShippingMethod shippingMethod, Store store, Area area) {
		return areaFreightConfigDao.exists(shippingMethod, store, area);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean unique(Long id, ShippingMethod shippingMethod, Store store, Area area) {
		return areaFreightConfigDao.unique(id, shippingMethod, store, area);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AreaFreightConfig> findPage(ShippingMethod shippingMethod, Store store, Pageable pageable) {
		return areaFreightConfigDao.findPage(shippingMethod, store, pageable);
	}

}