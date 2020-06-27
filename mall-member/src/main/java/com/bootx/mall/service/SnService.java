
package com.bootx.mall.service;

import com.bootx.mall.entity.Sn;

/**
 * Service - 序列号
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface SnService {

	/**
	 * 生成序列号
	 * 
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	String generate(Sn.Type type);

}