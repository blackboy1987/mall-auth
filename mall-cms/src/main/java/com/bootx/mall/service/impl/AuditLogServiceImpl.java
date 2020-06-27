
package com.bootx.mall.service.impl;

import javax.inject.Inject;

import com.bootx.mall.dao.AuditLogDao;
import com.bootx.mall.entity.AuditLog;
import com.bootx.mall.service.AuditLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service - 审计日志
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class AuditLogServiceImpl extends BaseServiceImpl<AuditLog, Long> implements AuditLogService {

	@Inject
	private AuditLogDao auditLogDao;

	@Override
	@Async
	public void create(AuditLog auditLog) {
		auditLogDao.persist(auditLog);
	}

	@Override
	public void clear() {
		auditLogDao.removeAll();
	}

}