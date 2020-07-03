
package com.bootx.mall.product.service.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import com.bootx.mall.product.common.Filter;
import com.bootx.mall.product.common.Order;
import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.dao.InstantMessageDao;
import com.bootx.mall.product.dao.StoreDao;
import com.bootx.mall.product.entity.InstantMessage;
import com.bootx.mall.product.entity.Store;
import com.bootx.mall.product.service.InstantMessageService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 即时通讯
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class InstantMessageServiceImpl extends BaseServiceImpl<InstantMessage, Long> implements InstantMessageService {

	@Inject
	private InstantMessageDao instantMessageDao;
	@Inject
	private StoreDao storeDao;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "instantMessage", condition = "#useCache")
	public List<InstantMessage> findList(InstantMessage.Type type, Long storeId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		Store store = storeDao.find(storeId);
		if (storeId != null && store == null) {
			return Collections.emptyList();
		}

		return instantMessageDao.findList(type, store, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<InstantMessage> findPage(Store store, Pageable pageable) {
		return instantMessageDao.findPage(store, pageable);
	}

	@Override
	@CacheEvict(value = "instantMessage", allEntries = true)
	public InstantMessage save(InstantMessage entity) {
		return super.save(entity);
	}

	@Override
	@CacheEvict(value = "instantMessage", allEntries = true)
	public InstantMessage update(InstantMessage entity) {
		return super.update(entity);
	}

	@Override
	@CacheEvict(value = "instantMessage", allEntries = true)
	public InstantMessage update(InstantMessage entity, String... ignoreProperties) {
		return super.update(entity, ignoreProperties);
	}

	@Override
	@CacheEvict(value = "instantMessage", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@CacheEvict(value = "instantMessage", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@CacheEvict(value = "instantMessage", allEntries = true)
	public void delete(InstantMessage entity) {
		super.delete(entity);
	}

}