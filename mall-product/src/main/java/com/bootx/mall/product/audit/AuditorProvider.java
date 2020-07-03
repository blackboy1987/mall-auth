
package com.bootx.mall.product.audit;

/**
 * Audit - 审计者Provider
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface AuditorProvider<T> {

	/**
	 * 获取当前审计者
	 * 
	 * @return 当前审计者
	 */
	T getCurrentAuditor();

}