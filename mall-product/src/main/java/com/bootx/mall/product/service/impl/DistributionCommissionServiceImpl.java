
package com.bootx.mall.product.service.impl;

import javax.inject.Inject;

import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.dao.DistributionCommissionDao;
import com.bootx.mall.product.entity.DistributionCommission;
import com.bootx.mall.product.entity.Distributor;
import com.bootx.mall.product.service.DistributionCommissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 分销佣金
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class DistributionCommissionServiceImpl extends BaseServiceImpl<DistributionCommission, Long> implements DistributionCommissionService {

	@Inject
	private DistributionCommissionDao distributionCommissionDao;

	@Override
	@Transactional(readOnly = true)
	public Page<DistributionCommission> findPage(Distributor distributor, Pageable pageable) {
		return distributionCommissionDao.findPage(distributor, pageable);
	}

}