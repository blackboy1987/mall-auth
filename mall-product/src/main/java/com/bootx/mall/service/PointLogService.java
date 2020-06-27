
package com.bootx.mall.service;

import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.entity.Member;
import com.bootx.mall.entity.PointLog;

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