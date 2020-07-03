
package com.bootx.mall.product.service;

import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.entity.Member;
import com.bootx.mall.product.entity.PointLog;

/**
 * Service - 积分记录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface PointLogService extends BaseService<PointLog, Long> {

	/**
	 * 查找积分记录分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 积分记录分页
	 */
	Page<PointLog> findPage(Member member, Pageable pageable);

}