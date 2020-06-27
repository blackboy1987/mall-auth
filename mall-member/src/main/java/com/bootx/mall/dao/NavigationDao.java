
package com.bootx.mall.dao;

import java.util.List;

import com.bootx.mall.common.Filter;
import com.bootx.mall.common.Order;
import com.bootx.mall.entity.Navigation;
import com.bootx.mall.entity.NavigationGroup;

/**
 * Dao - 导航
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface NavigationDao extends BaseDao<Navigation, Long> {

	/**
	 * 查找导航
	 * 
	 * @param navigationGroup
	 *            导航组
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 导航
	 */
	List<Navigation> findList(NavigationGroup navigationGroup, Integer count, List<Filter> filters, List<Order> orders);

}