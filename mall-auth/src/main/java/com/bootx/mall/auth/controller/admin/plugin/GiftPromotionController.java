
package com.bootx.mall.auth.controller.admin.plugin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.bootx.mall.auth.common.Results;
import com.bootx.mall.auth.controller.admin.BaseController;
import com.bootx.mall.auth.entity.PluginConfig;
import com.bootx.mall.auth.plugin.GiftPromotionPlugin;
import com.bootx.mall.auth.plugin.PaymentPlugin;
import com.bootx.mall.auth.plugin.PromotionPlugin;
import com.bootx.mall.auth.service.PluginConfigService;
import com.bootx.mall.auth.service.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller - 赠品
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("adminPluginGiftPromotionController")
@RequestMapping("/admin/plugin/gift_promotion")
public class GiftPromotionController extends BaseController {

	@Inject
	private GiftPromotionPlugin giftPromotionPlugin;
	@Inject
	private PluginConfigService pluginConfigService;
	@Inject
	private PromotionService promotionService;

	/**
	 * 安装
	 */
	@PostMapping("/install")
	public ResponseEntity<?> install() {
		if (!giftPromotionPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = new PluginConfig();
			pluginConfig.setPluginId(giftPromotionPlugin.getId());
			pluginConfig.setIsEnabled(false);
			pluginConfig.setAttributes(null);
			pluginConfigService.save(pluginConfig);
		}
		return Results.OK;
	}

	/**
	 * 卸载
	 */
	@PostMapping("/uninstall")
	public ResponseEntity<?> uninstall() {
		if (giftPromotionPlugin.getIsInstalled()) {
			pluginConfigService.deleteByPluginId(giftPromotionPlugin.getId());
			promotionService.shutDownPromotion(giftPromotionPlugin);
		}
		return Results.OK;
	}

	/**
	 * 设置
	 */
	@GetMapping("/setting")
	public String setting(ModelMap model) {
		PluginConfig pluginConfig = giftPromotionPlugin.getPluginConfig();
		model.addAttribute("pluginConfig", pluginConfig);
		return "/admin/plugin/gift_promotion/setting";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(String displayName, BigDecimal serviceCharge, String description, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order) {
		if (serviceCharge == null || serviceCharge.compareTo(BigDecimal.ZERO) < 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		PluginConfig pluginConfig = giftPromotionPlugin.getPluginConfig();
		Map<String, String> attributes = new HashMap<>();
		attributes.put(PaymentPlugin.DISPLAY_NAME_ATTRIBUTE_NAME, displayName);
		attributes.put(PromotionPlugin.SERVICE_CHARGE, String.valueOf(serviceCharge));
		attributes.put(PaymentPlugin.DESCRIPTION_ATTRIBUTE_NAME, description);
		pluginConfig.setAttributes(attributes);
		pluginConfig.setIsEnabled(isEnabled);
		pluginConfig.setOrder(order);
		pluginConfigService.update(pluginConfig);
		if (!pluginConfig.getIsEnabled()) {
			promotionService.shutDownPromotion(giftPromotionPlugin);
		}
		return Results.OK;
	}

}