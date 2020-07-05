
package com.bootx.mall.auth.service.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.dao.MemberDao;
import com.bootx.mall.auth.dao.StoreFavoriteDao;
import com.bootx.mall.auth.entity.Member;
import com.bootx.mall.auth.entity.Store;
import com.bootx.mall.auth.entity.StoreFavorite;
import com.bootx.mall.auth.service.StoreFavoriteService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 店铺收藏
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class StoreFavoriteServiceImpl extends BaseServiceImpl<StoreFavorite, Long> implements StoreFavoriteService {

	@Inject
	private StoreFavoriteDao storeFavoriteDao;
	@Inject
	private MemberDao memberDao;

	@Override
	@Transactional(readOnly = true)
	public boolean exists(Member member, Store store) {
		return storeFavoriteDao.exists(member, store);
	}

	@Override
	@Transactional(readOnly = true)
	public List<StoreFavorite> findList(Member member, Integer count, List<Filter> filters, List<Order> orders) {
		return storeFavoriteDao.findList(member, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<StoreFavorite> findPage(Member member, Pageable pageable) {
		return storeFavoriteDao.findPage(member, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Member member) {
		return storeFavoriteDao.count(member);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "storeFavorite", condition = "#useCache")
	public List<StoreFavorite> findList(Long memberId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		Member member = memberDao.find(memberId);
		if (memberId != null && member == null) {
			return Collections.emptyList();
		}
		return storeFavoriteDao.findList(member, count, filters, orders);
	}

	@Override
	@CacheEvict(value = "storeFavorite", allEntries = true)
	public StoreFavorite save(StoreFavorite storeFavorite) {
		return super.save(storeFavorite);
	}

	@Override
	@CacheEvict(value = "storeFavorite", allEntries = true)
	public StoreFavorite update(StoreFavorite storeFavorite) {
		return super.update(storeFavorite);
	}

	@Override
	@CacheEvict(value = "storeFavorite", allEntries = true)
	public StoreFavorite update(StoreFavorite storeFavorite, String... ignoreProperties) {
		return super.update(storeFavorite, ignoreProperties);
	}

	@Override
	@CacheEvict(value = "storeFavorite", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@CacheEvict(value = "storeFavorite", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@CacheEvict(value = "storeFavorite", allEntries = true)
	public void delete(StoreFavorite storeFavorite) {
		super.delete(storeFavorite);
	}

}