
package com.bootx.mall.auth.template.directive;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.entity.MemberAttribute;
import com.bootx.mall.auth.service.MemberAttributeService;
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
 * 模板指令 - 会员注册项列表
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class MemberAttributeListDirective extends BaseDirective {

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "memberAttributes";

	@Resource
	private MemberAttributeService memberAttributeService;

	public static MemberAttributeListDirective memberAttributeListDirective;

	@PostConstruct
	public void init() {
		memberAttributeListDirective = this;
		memberAttributeListDirective.memberAttributeService = this.memberAttributeService;
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
		List<Filter> filters = getFilters(params, MemberAttribute.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(params);

		List<MemberAttribute> memberAttributes = memberAttributeService.findList(true, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, memberAttributes, env, body);
	}

}