
package com.bootx.mall.dao;

import com.bootx.mall.entity.AuditLog;

/**
 * Dao - 审计日志
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface AuditLogDao extends BaseDao<AuditLog, Long> {

	/**
	 * 删除所有
	 */
	void removeAll();

}