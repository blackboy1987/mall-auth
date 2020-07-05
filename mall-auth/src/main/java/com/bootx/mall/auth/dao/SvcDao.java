
package com.bootx.mall.auth.dao;

import java.util.List;

import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.entity.Store;
import com.bootx.mall.auth.entity.StoreRank;
import com.bootx.mall.auth.entity.Svc;

/**
 * Dao - 服务
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface SvcDao extends BaseDao<Svc, Long> {

	/**
	 * 查找服务
	 * 
	 * @param store
	 *            店铺
	 * @param promotionPluginId
	 *            促销插件Id
	 * @param storeRank
	 *            店铺等级
	 * @param orders
	 *            排序
	 * @return 服务
	 */
	List<Svc> find(Store store, String promotionPluginId, StoreRank storeRank, List<Order> orders);

}