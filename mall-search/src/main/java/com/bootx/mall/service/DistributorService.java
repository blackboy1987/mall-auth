
package com.bootx.mall.service;

import com.bootx.mall.entity.Distributor;
import com.bootx.mall.entity.Member;

/**
 * Service - 分销员
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface DistributorService extends BaseService<Distributor, Long> {

	/**
	 * 创建
	 * 
	 * @param member
	 *            会员
	 */
	void create(Member member);

	/**
	 * 创建
	 * 
	 * @param member
	 *            会员
	 * @param spreadMember
	 *            推广会员
	 */
	void create(Member member, Member spreadMember);

	/**
	 * 修改
	 * 
	 * @param distributor
	 *            分销员
	 * @param spreadMember
	 *            推广会员
	 */
	void modify(Distributor distributor, Member spreadMember);

}