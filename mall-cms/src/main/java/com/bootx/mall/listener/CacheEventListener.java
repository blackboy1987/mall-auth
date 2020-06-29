
package com.bootx.mall.listener;

import com.bootx.mall.entity.Article;
import com.bootx.mall.service.ArticleService;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Listener - 缓存
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class CacheEventListener extends CacheEventListenerAdapter {

	@Inject
	private ArticleService articleService;

	/**
	 * 元素过期调用
	 * 
	 * @param ehcache
	 *            缓存
	 * @param element
	 *            元素
	 */
	@Override
	public void notifyElementExpired(Ehcache ehcache, Element element) {
		String cacheName = ehcache.getName();
		if (StringUtils.equals(cacheName, Article.HITS_CACHE_NAME)) {
			Long id = (Long) element.getObjectKey();
			Long hits = (Long) element.getObjectValue();
			Article article = articleService.find(id);
			if (article != null && hits != null && hits > 0 && hits > article.getHits()) {
				article.setHits(hits);
				articleService.update(article);
			}
		}
	}

}