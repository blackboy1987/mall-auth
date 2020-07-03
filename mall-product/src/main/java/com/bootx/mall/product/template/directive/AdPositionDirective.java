
package com.bootx.mall.product.template.directive;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.bootx.mall.product.entity.AdPosition;
import com.bootx.mall.product.service.AdPositionService;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 广告位
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class AdPositionDirective extends BaseDirective {

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "adPosition";

	@Resource
	private AdPositionService adPositionService;

	public static AdPositionDirective adPositionDirective;

	@PostConstruct
	public void init() {
		adPositionDirective = this;
		adPositionDirective.adPositionService = this.adPositionService;
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
		Long id = getId(params);
		boolean useCache = useCache(params);

		AdPosition adPosition = adPositionService.find(id, useCache);
		setLocalVariable(VARIABLE_NAME, adPosition, env, body);
	}

}