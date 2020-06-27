
package com.bootx.mall.template.directive;

import com.bootx.mall.common.Filter;
import com.bootx.mall.common.Order;
import com.bootx.mall.entity.Consultation;
import com.bootx.mall.entity.StoreAdImage;
import com.bootx.mall.service.StoreAdImageService;
import com.bootx.mall.util.FreeMarkerUtils;
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
 * 模板指令 - 店铺广告图片列表
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class StoreAdImageListDirective extends BaseDirective {

	/**
	 * "店铺ID"参数名称
	 */
	private static final String STORE_ID_PARAMETER_NAME = "storeId";

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "storeAdImages";

	@Resource
	private StoreAdImageService storeAdImageService;

	public static StoreAdImageListDirective storeAdImageListDirective;

	@PostConstruct
	public void init() {
		storeAdImageListDirective = this;
		storeAdImageListDirective.storeAdImageService = this.storeAdImageService;
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

		List<StoreAdImage> storeAdImages = storeAdImageService.findList(storeId, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, storeAdImages, env, body);
	}

}