
package com.bootx.mall.auth.template.directive;

import com.bootx.mall.auth.plugin.PromotionPlugin;
import com.bootx.mall.auth.service.PluginService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 模板指令 - 促销插件
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class PromotionPluginDirective extends BaseDirective {

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "promotionPlugin";

	@Resource
	private PluginService pluginService;

	public static PromotionPluginDirective promotionPluginDirective;

	@PostConstruct
	public void init() {
		promotionPluginDirective = this;
		promotionPluginDirective.pluginService = this.pluginService;
	}
	/**
	 * 执行
	 * 
	 * @param env
	 *            环境变量
	 * @param params
	 *            参数
	 * @param loopVars
	 *            循环变量
	 * @param body
	 *            模板内容
	 */
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<PromotionPlugin> promotionPlugins = pluginService.getPromotionPlugins(true);

		setLocalVariable(VARIABLE_NAME, CollectionUtils.isNotEmpty(promotionPlugins), env, body);
	}

}