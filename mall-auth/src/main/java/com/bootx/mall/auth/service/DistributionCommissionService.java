
package com.bootx.mall.auth.service;

import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.entity.DistributionCommission;
import com.bootx.mall.auth.entity.Distributor;

/**
 * Service - 分销佣金
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface DistributionCommissionService extends BaseService<DistributionCommission, Long> {

	/**
	 * 查找分销佣金记录分页
	 * 
	 * @param distributor
	 *            分销员
	 * @param pageable
	 *            分页信息
	 * @return 分销佣金记录分页
	 */
	Page<DistributionCommission> findPage(Distributor distributor, Pageable pageable);

}