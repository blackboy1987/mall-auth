
package com.bootx.mall.product.controller.shop;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bootx.mall.product.entity.SocialUser;
import com.bootx.mall.product.plugin.LoginPlugin;
import com.bootx.mall.product.service.PluginService;
import com.bootx.mall.product.service.SocialUserService;
import com.bootx.mall.product.security.SocialUserAuthenticationToken;
import com.bootx.mall.product.service.UserService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller - 社会化用户登录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("shopSocialUserLoginController")
@RequestMapping("/social_user_login")
public class SocialUserLoginController extends BaseController {

	@Inject
	private UserService userService;
	@Inject
	private SocialUserService socialUserService;
	@Inject
	private PluginService pluginService;

	/**
	 * 首页
	 */
	@RequestMapping
	public String index(String loginPluginId, HttpServletRequest request, HttpServletResponse response) {
		LoginPlugin loginPlugin = pluginService.getLoginPlugin(loginPluginId);
		if (loginPlugin == null || BooleanUtils.isNotTrue(loginPlugin.getIsEnabled())) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		return "redirect:" + loginPlugin.getPreSignInUrl(loginPlugin);
	}

	/**
	 * 登录前处理
	 */
	@RequestMapping({ "/pre_sign_in_{loginPluginId:[^_]+}", "/pre_sign_in_{loginPluginId[^_]+}_{extra}" })
	public ModelAndView preSignIn(@PathVariable String loginPluginId, @PathVariable(required = false) String extra, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginPlugin loginPlugin = pluginService.getLoginPlugin(loginPluginId);
		if (loginPlugin == null || BooleanUtils.isNotTrue(loginPlugin.getIsEnabled())) {
			return new ModelAndView(UNPROCESSABLE_ENTITY_VIEW);
		}

		ModelAndView modelAndView = new ModelAndView();
		loginPlugin.preSignInHandle(loginPlugin, extra, request, response, modelAndView);
		return modelAndView;
	}

	/**
	 * 登录处理
	 */
	@RequestMapping({ "/sign_in_{loginPluginId:[^_]+}", "/sign_in_{loginPluginId[^_]+}_{extra}" })
	public ModelAndView signIn(@PathVariable String loginPluginId, @PathVariable(required = false) String extra, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginPlugin loginPlugin = pluginService.getLoginPlugin(loginPluginId);
		if (loginPlugin == null || BooleanUtils.isNotTrue(loginPlugin.getIsEnabled())) {
			return new ModelAndView(UNPROCESSABLE_ENTITY_VIEW);
		}

		ModelAndView modelAndView = new ModelAndView();
		loginPlugin.signInHandle(loginPlugin, extra, request, response, modelAndView);
		return modelAndView;
	}

	/**
	 * 登录后处理
	 */
	@RequestMapping({ "/post_sign_in_{loginPluginId:[^_]+}", "/post_sign_in_{loginPluginId[^_]+}_{extra}" })
	public ModelAndView postSignIn(@PathVariable String loginPluginId, @PathVariable(required = false) String extra, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		LoginPlugin loginPlugin = pluginService.getLoginPlugin(loginPluginId);
		if (loginPlugin == null || BooleanUtils.isNotTrue(loginPlugin.getIsEnabled())) {
			return new ModelAndView(UNPROCESSABLE_ENTITY_VIEW);
		}

		SocialUser socialUser = null;
		boolean isSignInSuccess = loginPlugin.isSignInSuccess(loginPlugin, extra, request, response);
		if (isSignInSuccess) {
			String uniqueId = loginPlugin.getUniqueId(request);
			if (StringUtils.isEmpty(uniqueId)) {
				return new ModelAndView(UNPROCESSABLE_ENTITY_VIEW);
			}
			socialUser = socialUserService.find(loginPluginId, uniqueId);
			if (socialUser != null) {
				if (socialUser.getUser() != null) {
					userService.login(new SocialUserAuthenticationToken(socialUser, false, request.getRemoteAddr()));
				}
			} else {
				socialUser = new SocialUser();
				socialUser.setLoginPluginId(loginPluginId);
				socialUser.setUniqueId(uniqueId);
				socialUser.setUser(null);
				socialUserService.save(socialUser);
			}
		}
		ModelAndView modelAndView = new ModelAndView();
		loginPlugin.postSignInHandle(loginPlugin, socialUser, extra, isSignInSuccess, request, response, modelAndView);
		return modelAndView.hasView() ? modelAndView : null;
	}

}