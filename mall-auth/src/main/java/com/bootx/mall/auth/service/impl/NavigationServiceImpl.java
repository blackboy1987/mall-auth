
package com.bootx.mall.auth.service.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.dao.NavigationDao;
import com.bootx.mall.auth.dao.NavigationGroupDao;
import com.bootx.mall.auth.entity.Navigation;
import com.bootx.mall.auth.entity.NavigationGroup;
import com.bootx.mall.auth.service.NavigationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 导航
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class NavigationServiceImpl extends BaseServiceImpl<Navigation, Long> implements NavigationService {

	@Inject
	private NavigationDao navigationDao;
	@Inject
	private NavigationGroupDao navigationGroupDao;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "navigation", condition = "#useCache")
	public List<Navigation> findList(Long navigationGroupId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		NavigationGroup navigationGroup = navigationGroupDao.find(navigationGroupId);
		if (navigationGroupId != null && navigationGroup == null) {
			return Collections.emptyList();
		}

		return navigationDao.findList(navigationGroup, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public Navigation save(Navigation navigation) {
		return super.save(navigation);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public Navigation update(Navigation navigation) {
		return super.update(navigation);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public Navigation update(Navigation navigation, String... ignoreProperties) {
		return super.update(navigation, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public void delete(Navigation navigation) {
		super.delete(navigation);
	}

}