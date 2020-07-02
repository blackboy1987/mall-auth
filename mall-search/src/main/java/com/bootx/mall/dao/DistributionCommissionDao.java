
package com.bootx.mall.dao;

import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.entity.DistributionCommission;
import com.bootx.mall.entity.Distributor;

/**
 * Dao - 分销佣金
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface DistributionCommissionDao extends BaseDao<DistributionCommission, Long> {

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