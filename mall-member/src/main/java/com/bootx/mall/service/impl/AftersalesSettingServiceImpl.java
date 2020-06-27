
package com.bootx.mall.service.impl;

import javax.inject.Inject;

import com.bootx.mall.entity.AftersalesSetting;
import com.bootx.mall.entity.Store;
import com.bootx.mall.dao.AftersalesSettingDao;
import com.bootx.mall.service.AftersalesSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 售后设置
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class AftersalesSettingServiceImpl extends BaseServiceImpl<AftersalesSetting, Long> implements AftersalesSettingService {

	@Inject
	private AftersalesSettingDao aftersalesSettingDao;

	@Override
	@Transactional(readOnly = true)
	public AftersalesSetting findByStore(Store store) {
		Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");

		return aftersalesSettingDao.find("store", store);
	}

}