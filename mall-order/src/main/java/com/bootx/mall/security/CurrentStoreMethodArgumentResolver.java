
package com.bootx.mall.security;

import javax.inject.Inject;

import com.bootx.mall.entity.Store;
import com.bootx.mall.service.StoreService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Security - 当前店铺MethodArgumentResolver
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public class CurrentStoreMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Inject
	private StoreService storeService;

	/**
	 * 支持参数
	 * 
	 * @param methodParameter
	 *            MethodParameter
	 * @return 是否支持参数
	 */
	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.hasParameterAnnotation(CurrentStore.class) && Store.class.isAssignableFrom(methodParameter.getParameterType());
	}

	/**
	 * 解析变量
	 * 
	 * @param methodParameter
	 *            MethodParameter
	 * @param modelAndViewContainer
	 *            ModelAndViewContainer
	 * @param nativeWebRequest
	 *            NativeWebRequest
	 * @param webDataBinderFactory
	 *            WebDataBinderFactory
	 * @return 变量
	 */
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		return storeService.getCurrent();
	}

}