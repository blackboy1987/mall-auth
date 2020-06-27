
package com.bootx.mall.dao;

import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.entity.StockLog;
import com.bootx.mall.entity.Store;

/**
 * Dao - 库存记录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface StockLogDao extends BaseDao<StockLog, Long> {

	/**
	 * 查找库存记录分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 库存记录分页
	 */
	Page<StockLog> findPage(Store store, Pageable pageable);

}