
package com.bootx.mall.auth.dao;

import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.entity.Business;
import com.bootx.mall.auth.entity.BusinessCash;

/**
 * Dao - 商家提现
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface BusinessCashDao extends BaseDao<BusinessCash, Long> {

	/**
	 * 查找商家提现记录分页
	 * 
	 * @param status
	 *            状态
	 * @param bank
	 *            收款银行
	 * @param account
	 *            收款账号
	 * @param business
	 *            商家
	 * @param pageable
	 *            分页信息
	 * @return 商家提现记录分页
	 */
	Page<BusinessCash> findPage(BusinessCash.Status status, String bank, String account, Business business, Pageable pageable);

	/**
	 * 查找商家提现数量
	 * 
	 * @param status
	 *            状态
	 * @param bank
	 *            收款银行
	 * @param account
	 *            收款账号
	 * @param business
	 *            商家
	 * @return 商家提现数量
	 */
	Long count(BusinessCash.Status status, String bank, String account, Business business);

}