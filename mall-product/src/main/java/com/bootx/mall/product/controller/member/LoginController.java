
package com.bootx.mall.product.controller.member;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.bootx.mall.product.entity.Member;
import com.bootx.mall.product.entity.SocialUser;
import com.bootx.mall.product.security.CurrentUser;
import com.bootx.mall.product.service.PluginService;
import com.bootx.mall.product.service.SocialUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 会员登录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("memberLoginController")
@RequestMapping("/member/login")
public class LoginController extends BaseController {

	@Value("${security.member_login_success_url}")
	private String memberLoginSuccessUrl;

	@Inject
	private PluginService pluginService;
	@Inject
	private SocialUserService socialUserService;

	/**
	 * 登录页面
	 */
	@GetMapping
	public String index(Long socialUserId, String uniqueId, @CurrentUser Member currentUser, HttpServletRequest request, ModelMap model) {
		if (socialUserId != null && StringUtils.isNotEmpty(uniqueId)) {
			SocialUser socialUser = socialUserService.find(socialUserId);
			if (socialUser == null || socialUser.getUser() != null || !StringUtils.equals(socialUser.getUniqueId(), uniqueId)) {
				return UNPROCESSABLE_ENTITY_VIEW;
			}
			model.addAttribute("socialUserId", socialUserId);
			model.addAttribute("uniqueId", uniqueId);
		}
		model.addAttribute("memberLoginSuccessUrl", memberLoginSuccessUrl);
		model.addAttribute("loginPlugins", pluginService.getActiveLoginPlugins(request));
		return "/member/login/index";
	}

}