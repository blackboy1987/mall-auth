
package com.bootx.mall.auth.service;

import java.util.List;

import com.bootx.mall.auth.entity.SitemapIndex;
import com.bootx.mall.auth.entity.SitemapUrl;

/**
 * Service - Sitemap索引
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface SitemapIndexService {

	/**
	 * 生成Sitemap索引
	 * 
	 * @param type
	 *            类型
	 * @param maxSitemapUrlSize
	 *            最大Sitemap URL数量
	 * @return Sitemap索引
	 */
	List<SitemapIndex> generate(SitemapUrl.Type type, int maxSitemapUrlSize);

}