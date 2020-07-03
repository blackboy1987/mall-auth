
package com.bootx.mall.product.service.impl;

import javax.inject.Inject;

import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.dao.MemberDepositLogDao;
import com.bootx.mall.product.entity.Member;
import com.bootx.mall.product.entity.MemberDepositLog;
import com.bootx.mall.product.service.MemberDepositLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 会员预存款记录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class MemberDepositLogServiceImpl extends BaseServiceImpl<MemberDepositLog, Long> implements MemberDepositLogService {

	@Inject
	private MemberDepositLogDao memberDepositLogDao;

	@Override
	@Transactional(readOnly = true)
	public Page<MemberDepositLog> findPage(Member member, Pageable pageable) {
		return memberDepositLogDao.findPage(member, pageable);
	}

}