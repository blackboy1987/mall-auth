
package com.bootx.mall.auth.template.directive;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.entity.Review;
import com.bootx.mall.auth.service.ReviewService;
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
 * 模板指令 - 评论
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class ReviewListDirective extends BaseDirective {

	/**
	 * "会员ID"参数名称
	 */
	private static final String MEMBER_ID_PARAMETER_NAME = "memberId";

	/**
	 * "商品ID"参数名称
	 */
	private static final String PRODUCT_ID_PARAMETER_NAME = "productId";

	/**
	 * "类型"参数名称
	 */
	private static final String TYPE_PARAMETER_NAME = "type";

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "reviews";

	@Resource
	private ReviewService reviewService;

	public static ReviewListDirective reviewListDirective;

	@PostConstruct
	public void init() {
		reviewListDirective = this;
		reviewListDirective.reviewService = this.reviewService;
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
		Long memberId = FreeMarkerUtils.getParameter(MEMBER_ID_PARAMETER_NAME, Long.class, params);
		Long productId = FreeMarkerUtils.getParameter(PRODUCT_ID_PARAMETER_NAME, Long.class, params);
		Review.Type type = FreeMarkerUtils.getParameter(TYPE_PARAMETER_NAME, Review.Type.class, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, Review.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(params);

		List<Review> reviews = reviewService.findList(memberId, productId, type, true, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, reviews, env, body);
	}

}