
package com.bootx.mall.auth.template.directive;

import com.bootx.mall.auth.entity.StoreProductCategory;
import com.bootx.mall.auth.service.StoreProductCategoryService;
import com.bootx.mall.auth.util.FreeMarkerUtils;
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
 * 模板指令 - 上级店铺商品分类列表
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class StoreProductCategoryParentListDirective extends BaseDirective {

	/**
	 * "店铺商品分类ID"参数名称
	 */
	private static final String PRODUCT_CATEGORY_ID_PARAMETER_NAME = "storeProductCategoryId";

	/**
	 * "是否递归"参数名称
	 */
	private static final String RECURSIVE_PARAMETER_NAME = "recursive";

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "storeProductCategories";

	@Resource
	private StoreProductCategoryService storeProductCategoryService;
	public static StoreProductCategoryParentListDirective storeProductCategoryParentListDirective;

	@PostConstruct
	public void init() {
		storeProductCategoryParentListDirective = this;
		storeProductCategoryParentListDirective.storeProductCategoryService = this.storeProductCategoryService;
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
		Long storeProductCategoryId = FreeMarkerUtils.getParameter(PRODUCT_CATEGORY_ID_PARAMETER_NAME, Long.class, params);
		Boolean recursive = FreeMarkerUtils.getParameter(RECURSIVE_PARAMETER_NAME, Boolean.class, params);
		Integer count = getCount(params);
		boolean useCache = useCache(params);

		List<StoreProductCategory> storeProductCategories = storeProductCategoryService.findParents(storeProductCategoryId, recursive != null ? recursive : true, count, useCache);
		setLocalVariable(VARIABLE_NAME, storeProductCategories, env, body);
	}

}