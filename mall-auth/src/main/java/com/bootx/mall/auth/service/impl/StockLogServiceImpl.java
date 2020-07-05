
package com.bootx.mall.auth.service.impl;

import javax.inject.Inject;

import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.dao.StockLogDao;
import com.bootx.mall.auth.entity.StockLog;
import com.bootx.mall.auth.entity.Store;
import com.bootx.mall.auth.service.StockLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 库存记录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class StockLogServiceImpl extends BaseServiceImpl<StockLog, Long> implements StockLogService {

	@Inject
	private StockLogDao stockLogDao;

	@Override
	@Transactional(readOnly = true)
	public Page<StockLog> findPage(Store store, Pageable pageable) {
		return stockLogDao.findPage(store, pageable);
	}

}