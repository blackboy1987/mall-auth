
package com.bootx.mall.auth.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.entity.Consultation;
import com.bootx.mall.auth.entity.StoreProductTag;
import com.bootx.mall.auth.service.StoreProductTagService;
import com.bootx.mall.auth.util.FreeMarkerUtils;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 店铺商品标签
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class StoreProductTagDirective extends BaseDirective {

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "storeProductTags";

	@Resource
	private StoreProductTagService storeProductTagService;

	/**
	 * "店铺ID"参数名称
	 */
	private static final String STORE_ID_PARAMETER_NAME = "storeId";

	public static StoreProductTagDirective storeProductTagDirective;

	@PostConstruct
	public void init() {
		storeProductTagDirective = this;
		storeProductTagDirective.storeProductTagService = this.storeProductTagService;
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
		Long storeId = FreeMarkerUtils.getParameter(STORE_ID_PARAMETER_NAME, Long.class, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, Consultation.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(params);

		List<StoreProductTag> storeProductTags = storeProductTagService.findList(storeId, true, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, storeProductTags, env, body);
	}

}