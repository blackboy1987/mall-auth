
package com.bootx.mall.product.controller.admin;

import javax.inject.Inject;

import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.service.DistributionCommissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 分销佣金
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("adminDistributionCommissionController")
@RequestMapping("/admin/distribution_commission")
public class DistributionCommissionController extends BaseController {

	@Inject
	private DistributionCommissionService distributionCommissionService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", distributionCommissionService.findPage(pageable));
		return "admin/distribution_commission/list";
	}

}