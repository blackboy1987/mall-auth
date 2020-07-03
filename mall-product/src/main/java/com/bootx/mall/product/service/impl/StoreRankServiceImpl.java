
package com.bootx.mall.product.service.impl;

import java.util.List;

import javax.inject.Inject;

import com.bootx.mall.product.common.Filter;
import com.bootx.mall.product.common.Order;
import com.bootx.mall.product.dao.StoreRankDao;
import com.bootx.mall.product.entity.StoreRank;
import com.bootx.mall.product.service.StoreRankService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 店铺等级
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class StoreRankServiceImpl extends BaseServiceImpl<StoreRank, Long> implements StoreRankService {

	@Inject
	private StoreRankDao storeRankDao;

	@Override
	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		return storeRankDao.exists("name", name);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean nameUnique(Long id, String name) {
		return storeRankDao.unique(id, "name", name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<StoreRank> findList(Boolean isAllowRegister, List<Filter> filters, List<Order> orders) {
		return storeRankDao.findList(isAllowRegister, filters, orders);
	}

}