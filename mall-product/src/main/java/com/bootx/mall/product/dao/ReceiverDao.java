
package com.bootx.mall.product.dao;

import java.util.List;

import com.bootx.mall.product.common.Page;
import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.entity.Member;
import com.bootx.mall.product.entity.Receiver;

/**
 * Dao - 收货地址
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface ReceiverDao extends BaseDao<Receiver, Long> {

	/**
	 * 查找默认收货地址
	 * 
	 * @param member
	 *            会员
	 * @return 默认收货地址，若不存在则返回最新收货地址
	 */
	Receiver findDefault(Member member);

	/**
	 * 查找收货地址
	 * 
	 * @param member
	 *            会员
	 * @return 收货地址
	 */
	List<Receiver> findList(Member member);

	/**
	 * 查找收货地址分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 收货地址分页
	 */
	Page<Receiver> findPage(Member member, Pageable pageable);

	/**
	 * 清除默认
	 * 
	 * @param member
	 *            会员
	 */
	void clearDefault(Member member);

	/**
	 * 清除默认
	 * 
	 * @param member
	 *            会员
	 * @param exclude
	 *            排除收货地址
	 */
	void clearDefault(Member member, Receiver exclude);

}