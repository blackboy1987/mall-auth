
package com.bootx.mall.product.dao;

import com.bootx.mall.product.entity.MessageConfig;

/**
 * Dao - 消息配置
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface MessageConfigDao extends BaseDao<MessageConfig, Long> {

	/**
	 * 查找消息配置
	 * 
	 * @param type
	 *            类型
	 * @return 消息配置
	 */
	MessageConfig find(MessageConfig.Type type);

}