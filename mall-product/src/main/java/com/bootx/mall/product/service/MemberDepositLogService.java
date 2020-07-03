
package com.bootx.mall.product.service;

import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.entity.Member;
import com.bootx.mall.product.entity.MemberDepositLog;

/**
 * Service - 会员预存款记录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface MemberDepositLogService extends BaseService<MemberDepositLog, Long> {

	/**
	 * 查找会员预存款记录分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 会员预存款记录分页
	 */
	Page<MemberDepositLog> findPage(Member member, Pageable pageable);

}