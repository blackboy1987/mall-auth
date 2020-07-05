
package com.bootx.mall.auth.service;

import com.bootx.mall.auth.entity.AuditLog;

/**
 * Service - 审计日志
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface AuditLogService extends BaseService<AuditLog, Long> {

	/**
	 * 创建审计日志(异步)
	 * 
	 * @param auditLog
	 *            审计日志
	 */
	void create(AuditLog auditLog);

	/**
	 * 清空审计日志
	 */
	void clear();

}