
package com.bootx.mall.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.bootx.mall.auth.common.Setting;
import com.bootx.mall.auth.dao.ArticleDao;
import com.bootx.mall.auth.dao.ProductDao;
import com.bootx.mall.auth.entity.Article;
import com.bootx.mall.auth.entity.Product;
import com.bootx.mall.auth.entity.SitemapUrl;
import com.bootx.mall.auth.util.SystemUtils;
import com.bootx.mall.auth.service.SitemapUrlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - Sitemap URL
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class SitemapUrlServiceImpl implements SitemapUrlService {

	@Inject
	private ArticleDao articleDao;
	@Inject
	private ProductDao productDao;

	@Override
	@Transactional(readOnly = true)
	public List<SitemapUrl> generate(SitemapUrl.Type type, SitemapUrl.Changefreq changefreq, float priority, Integer first, Integer count) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");
		Assert.notNull(changefreq, "[Assertion failed] - changefreq is required; it must not be null");

		Setting setting = SystemUtils.getSetting();
		List<SitemapUrl> sitemapUrls = new ArrayList<>();
		switch (type) {
		case ARTICLE:
			List<Article> articles = articleDao.findList(first, count, null, null);
			for (Article article : articles) {
				SitemapUrl sitemapUrl = new SitemapUrl();
				sitemapUrl.setLoc(setting.getSiteUrl() + article.getPath());
				sitemapUrl.setLastmod(article.getLastModifiedDate());
				sitemapUrl.setChangefreq(changefreq);
				sitemapUrl.setPriority(priority);
				sitemapUrls.add(sitemapUrl);
			}
			break;
		case PRODUCT:
			List<Product> products = productDao.findList(null, null, true, true, null, null, first, count);
			for (Product product : products) {
				SitemapUrl sitemapUrl = new SitemapUrl();
				sitemapUrl.setLoc(setting.getSiteUrl() + product.getPath());
				sitemapUrl.setLastmod(product.getLastModifiedDate());
				sitemapUrl.setChangefreq(changefreq);
				sitemapUrl.setPriority(priority);
				sitemapUrls.add(sitemapUrl);
			}
			break;
		}
		return sitemapUrls;
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(SitemapUrl.Type type) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");

		switch (type) {
		case ARTICLE:
			return articleDao.count();
		case PRODUCT:
			return productDao.count(null, null, true, null, null, true, null, null);
		}
		return 0L;
	}

}