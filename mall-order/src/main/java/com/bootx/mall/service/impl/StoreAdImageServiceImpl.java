
package com.bootx.mall.service.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import com.bootx.mall.common.Filter;
import com.bootx.mall.common.Order;
import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.dao.StoreAdImageDao;
import com.bootx.mall.dao.StoreDao;
import com.bootx.mall.entity.Store;
import com.bootx.mall.entity.StoreAdImage;
import com.bootx.mall.service.StoreAdImageService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 店铺广告图片
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class StoreAdImageServiceImpl extends BaseServiceImpl<StoreAdImage, Long> implements StoreAdImageService {

	@Inject
	private StoreAdImageDao storeAdImageDao;
	@Inject
	private StoreDao storeDao;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "storeAdImage", condition = "#useCache")
	public List<StoreAdImage> findList(Long storeId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		Store store = storeDao.find(storeId);
		if (storeId != null && store == null) {
			return Collections.emptyList();
		}

		return storeAdImageDao.findList(store, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<StoreAdImage> findPage(Store store, Pageable pageable) {
		return storeAdImageDao.findPage(store, pageable);
	}

	@Override
	@CacheEvict(value = "storeAdImage", allEntries = true)
	public StoreAdImage save(StoreAdImage entity) {
		return super.save(entity);
	}

	@Override
	@CacheEvict(value = "storeAdImage", allEntries = true)
	public StoreAdImage update(StoreAdImage entity) {
		return super.update(entity);
	}

	@Override
	@CacheEvict(value = "storeAdImage", allEntries = true)
	public StoreAdImage update(StoreAdImage entity, String... ignoreProperties) {
		return super.update(entity, ignoreProperties);
	}

	@Override
	@CacheEvict(value = "storeAdImage", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@CacheEvict(value = "storeAdImage", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@CacheEvict(value = "storeAdImage", allEntries = true)
	public void delete(StoreAdImage entity) {
		super.delete(entity);
	}

}