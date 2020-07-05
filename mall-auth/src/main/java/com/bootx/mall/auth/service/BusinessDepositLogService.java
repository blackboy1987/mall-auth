
package com.bootx.mall.auth.service;

import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.entity.Business;
import com.bootx.mall.auth.entity.BusinessDepositLog;

/**
 * Service - 商家预存款记录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface BusinessDepositLogService extends BaseService<BusinessDepositLog, Long> {

	/**
	 * 查找商家预存款记录分页
	 * 
	 * @param business
	 *            商家
	 * @param pageable
	 *            分页信息
	 * @return 商家预存款记录分页
	 */
	Page<BusinessDepositLog> findPage(Business business, Pageable pageable);

}