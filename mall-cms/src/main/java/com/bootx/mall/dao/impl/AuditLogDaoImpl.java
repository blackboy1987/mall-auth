
package com.bootx.mall.dao.impl;

import com.bootx.mall.dao.AuditLogDao;
import com.bootx.mall.entity.AuditLog;
import org.springframework.stereotype.Repository;

/**
 * Dao - 审计日志
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class AuditLogDaoImpl extends BaseDaoImpl<AuditLog, Long> implements AuditLogDao {

	@Override
	public void removeAll() {
		String jpql = "delete from AuditLog auditLog";
		entityManager.createQuery(jpql).executeUpdate();
	}

}