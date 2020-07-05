
package com.bootx.mall.auth.dao.impl;

import javax.persistence.NoResultException;

import com.bootx.mall.auth.dao.MessageConfigDao;
import com.bootx.mall.auth.entity.MessageConfig;
import org.springframework.stereotype.Repository;

/**
 * Dao - 消息配置
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class MessageConfigDaoImpl extends BaseDaoImpl<MessageConfig, Long> implements MessageConfigDao {

	@Override
	public MessageConfig find(MessageConfig.Type type) {
		if (type == null) {
			return null;
		}
		try {
			String jpql = "select messageConfig from MessageConfig messageConfig where messageConfig.type = :type";
			return entityManager.createQuery(jpql, MessageConfig.class).setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}