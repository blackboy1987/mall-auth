
package com.bootx.mall.product.dao.impl;

import javax.persistence.NoResultException;

import com.bootx.mall.product.dao.StorePluginStatusDao;
import com.bootx.mall.product.entity.Store;
import com.bootx.mall.product.entity.StorePluginStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 店铺插件状态
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class StorePluginStatusDaoImpl extends BaseDaoImpl<StorePluginStatus, Long> implements StorePluginStatusDao {

	public StorePluginStatus find(Store store, String pluginId) {
		if (store == null || StringUtils.isEmpty(pluginId)) {
			return null;
		}
		try {
			String jpql = "select storePluginStatus from StorePluginStatus storePluginStatus where storePluginStatus.store = :store and storePluginStatus.pluginId = :pluginId";
			return entityManager.createQuery(jpql, StorePluginStatus.class).setParameter("store", store).setParameter("pluginId", pluginId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}