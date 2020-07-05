
package com.bootx.mall.auth.controller.business;

import javax.inject.Inject;

import com.bootx.mall.auth.common.Results;
import com.bootx.mall.auth.entity.AftersalesSetting;
import com.bootx.mall.auth.entity.Store;
import com.bootx.mall.auth.service.AftersalesSettingService;
import com.bootx.mall.auth.security.CurrentStore;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 售后设置
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("businessAftersalesSettingController")
@RequestMapping("business/aftersales_setting")
public class AftersalesSettingController extends BaseController {

	@Inject
	private AftersalesSettingService aftersalesSettingService;

	/**
	 * 查看
	 */
	@GetMapping("/view")
	public String view(@CurrentStore Store currentStore, ModelMap model) {
		AftersalesSetting aftersalesSetting = aftersalesSettingService.findByStore(currentStore);
		if (aftersalesSetting == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		model.addAttribute("aftersalesSetting", aftersalesSetting);
		return "business/aftersales_setting/view";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(AftersalesSetting aftersalesSettingForm, @CurrentStore Store currentStore) {
		AftersalesSetting aftersalesSetting = aftersalesSettingService.findByStore(currentStore);
		if (aftersalesSetting == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		BeanUtils.copyProperties(aftersalesSettingForm, aftersalesSetting, "id", "store");
		aftersalesSettingService.update(aftersalesSetting);
		return Results.OK;
	}

}