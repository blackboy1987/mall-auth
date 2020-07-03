
package com.bootx.mall.product.listener;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.bootx.mall.product.entity.Article;
import com.bootx.mall.product.entity.Product;
import com.bootx.mall.product.entity.Store;
import com.bootx.mall.product.service.ConfigService;
import com.bootx.mall.product.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener - 初始化
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class InitListener {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(InitListener.class.getName());

	@Value("${system.version}")
	private String systemVersion;

	@Inject
	private ConfigService configService;
	@Inject
	private SearchService searchService;

	/**
	 * 事件处理
	 * 
	 * @param contextRefreshedEvent
	 *            ContextRefreshedEvent
	 */
	@EventListener
	public void handle(ContextRefreshedEvent contextRefreshedEvent) {
		if (contextRefreshedEvent.getApplicationContext() == null || contextRefreshedEvent.getApplicationContext().getParent() != null) {
			return;
		}

		String info = "I|n|i|t|i|a|l|i|z|i|n|g| |S|H|O|P|+|+| |B|2|B|2|C| |" + systemVersion;
		LOGGER.info(info.replace("|", StringUtils.EMPTY));
		configService.init();
		searchService.index(Article.class);
		searchService.index(Product.class);
		searchService.index(Store.class);
	}

}