
package com.bootx.mall.auth.service;

import java.util.List;

import com.bootx.mall.auth.entity.SpecificationItem;
import com.bootx.mall.auth.entity.SpecificationValue;

/**
 * Service - 规格值
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface SpecificationValueService {

	/**
	 * 规格值验证
	 * 
	 * @param specificationItems
	 *            规格项
	 * @param specificationValues
	 *            规格值
	 * @return 验证结果
	 */
	boolean isValid(List<SpecificationItem> specificationItems, List<SpecificationValue> specificationValues);

}