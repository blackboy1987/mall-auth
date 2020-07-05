
package com.bootx.mall.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.dao.SnDao;
import com.bootx.mall.auth.dao.SvcDao;
import com.bootx.mall.auth.entity.Sn;
import com.bootx.mall.auth.entity.Store;
import com.bootx.mall.auth.entity.StoreRank;
import com.bootx.mall.auth.entity.Svc;
import com.bootx.mall.auth.service.SvcService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 服务
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class SvcServiceImpl extends BaseServiceImpl<Svc, Long> implements SvcService {

	@Inject
	private SvcDao svcDao;
	@Inject
	private SnDao snDao;

	@Override
	@Transactional(readOnly = true)
	public Svc findBySn(String sn) {
		return svcDao.find("sn", StringUtils.lowerCase(sn));
	}

	@Override
	@Transactional(readOnly = true)
	public Svc findTheLatest(Store store, String promotionPluginId, StoreRank storeRank) {

		List<Order> orderList = new ArrayList<>();
		orderList.add(new Order("createdDate", Order.Direction.DESC));
		List<Svc> serviceOrders = svcDao.find(store, promotionPluginId, storeRank, orderList);

		return CollectionUtils.isNotEmpty(serviceOrders) ? serviceOrders.get(0) : null;
	}

	@Override
	@Transactional
	public Svc save(Svc svc) {
		Assert.notNull(svc, "[Assertion failed] - svc is required; it must not be null");

		svc.setSn(snDao.generate(Sn.Type.PLATFORM_SERVICE));

		return super.save(svc);
	}

}