
package com.bootx.mall.auth.service.impl;

import javax.inject.Inject;

import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.dao.PointLogDao;
import com.bootx.mall.auth.entity.Member;
import com.bootx.mall.auth.entity.PointLog;
import com.bootx.mall.auth.service.PointLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 积分记录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class PointLogServiceImpl extends BaseServiceImpl<PointLog, Long> implements PointLogService {

	@Inject
	private PointLogDao pointLogDao;

	@Override
	@Transactional(readOnly = true)
	public Page<PointLog> findPage(Member member, Pageable pageable) {
		return pointLogDao.findPage(member, pageable);
	}

}