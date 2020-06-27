
package com.bootx.mall.service.impl;

import javax.inject.Inject;

import com.bootx.mall.dao.StoreCategoryDao;
import com.bootx.mall.entity.StoreCategory;
import com.bootx.mall.service.StoreCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 店铺分类
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class StoreCategoryServiceImpl extends BaseServiceImpl<StoreCategory, Long> implements StoreCategoryService {

	@Inject
	private StoreCategoryDao storeCategoryDao;

	@Override
	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		return storeCategoryDao.exists("name", name, true);
	}

}