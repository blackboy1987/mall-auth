
package com.bootx.mall.product.template.directive;

import com.bootx.mall.product.common.Filter;
import com.bootx.mall.product.common.Order;
import com.bootx.mall.product.entity.BusinessAttribute;
import com.bootx.mall.product.service.BusinessAttributeService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 模板指令 - 商家注册项列表
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class BusinessAttributeListDirective extends BaseDirective {

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "businessAttributes";

	@Resource
	private BusinessAttributeService businessAttributeService;
	public static BusinessAttributeListDirective businessAttributeListDirective;

	@PostConstruct
	public void init() {
		businessAttributeListDirective = this;
		businessAttributeListDirective.businessAttributeService = this.businessAttributeService;
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
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, BusinessAttribute.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(params);

		List<BusinessAttribute> businessAttributes = businessAttributeService.findList(true, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, businessAttributes, env, body);
	}

}