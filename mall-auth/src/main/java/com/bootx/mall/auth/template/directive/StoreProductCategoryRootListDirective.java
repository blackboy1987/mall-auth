
package com.bootx.mall.auth.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.bootx.mall.auth.entity.StoreProductCategory;
import com.bootx.mall.auth.service.StoreProductCategoryService;
import com.bootx.mall.auth.util.FreeMarkerUtils;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 顶级店铺商品分类列表
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class StoreProductCategoryRootListDirective extends BaseDirective {

	/**
	 * "店铺ID"参数名称
	 */
	private static final String STORE_ID = "storeId";

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "storeProductCategories";

	@Resource
	private StoreProductCategoryService storeProductCategoryService;

	public static StoreProductCategoryRootListDirective storeProductCategoryRootListDirective;

	@PostConstruct
	public void init() {
		storeProductCategoryRootListDirective = this;
		storeProductCategoryRootListDirective.storeProductCategoryService = this.storeProductCategoryService;
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
		Long storeId = FreeMarkerUtils.getParameter(STORE_ID, Long.class, params);
		Integer count = getCount(params);
		boolean useCache = useCache(params);

		List<StoreProductCategory> storeProductCategories = storeProductCategoryService.findRoots(storeId, count, useCache);
		setLocalVariable(VARIABLE_NAME, storeProductCategories, env, body);
	}

}