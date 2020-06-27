
package com.bootx.mall.service.impl;

import javax.inject.Inject;

import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.dao.BusinessDepositLogDao;
import com.bootx.mall.entity.Business;
import com.bootx.mall.entity.BusinessDepositLog;
import com.bootx.mall.service.BusinessDepositLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 商家预存款记录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class BusinessDepositLogServiceImpl extends BaseServiceImpl<BusinessDepositLog, Long> implements BusinessDepositLogService {

	@Inject
	private BusinessDepositLogDao businessDepositLogDao;

	@Override
	@Transactional(readOnly = true)
	public Page<BusinessDepositLog> findPage(Business business, Pageable pageable) {
		return businessDepositLogDao.findPage(business, pageable);
	}

}