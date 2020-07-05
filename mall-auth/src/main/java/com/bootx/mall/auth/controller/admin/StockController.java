
package com.bootx.mall.auth.controller.admin;

import javax.inject.Inject;

import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.service.StockLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 库存
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("adminStockController")
@RequestMapping("/admin/stock")
public class StockController extends BaseController {

	@Inject
	private StockLogService stockLogService;

	/**
	 * 记录
	 */
	@GetMapping("/log")
	public String log(Pageable pageable, ModelMap model) {
		model.addAttribute("page", stockLogService.findPage(pageable));
		return "admin/stock/log";
	}

}