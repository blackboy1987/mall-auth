
package com.bootx.mall.auth.controller.member;

import javax.inject.Inject;

import com.bootx.mall.auth.controller.shop.BaseController;
import com.bootx.mall.auth.entity.Member;
import com.bootx.mall.auth.entity.Order;
import com.bootx.mall.auth.security.CurrentUser;
import com.bootx.mall.auth.service.CouponCodeService;
import com.bootx.mall.auth.service.MessageService;
import com.bootx.mall.auth.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bootx.mall.auth.service.ConsultationService;
import com.bootx.mall.auth.service.ProductFavoriteService;
import com.bootx.mall.auth.service.ProductNotifyService;
import com.bootx.mall.auth.service.ReviewService;

/**
 * Controller - 首页
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("memberIndexController")
@RequestMapping("/member/index")
public class IndexController extends BaseController {

	/**
	 * 最新订单数量
	 */
	private static final int NEW_ORDER_SIZE = 3;

	@Inject
	private OrderService orderService;
	@Inject
	private CouponCodeService couponCodeService;
	@Inject
	private MessageService messageService;
	@Inject
	private ProductFavoriteService productFavoriteService;
	@Inject
	private ProductNotifyService productNotifyService;
	@Inject
	private ReviewService reviewService;
	@Inject
	private ConsultationService consultationService;

	/**
	 * 首页
	 */
	@GetMapping
	public String index(@CurrentUser Member currentUser, ModelMap model) {
		model.addAttribute("pendingPaymentOrderCount", orderService.count(null, Order.Status.PENDING_PAYMENT, null, currentUser, null, null, null, null, null, null, false));
		model.addAttribute("pendingShipmentOrderCount", orderService.count(null, Order.Status.PENDING_SHIPMENT, null, currentUser, null, null, null, null, null, null, false));
		model.addAttribute("shippedOrderCount", orderService.count(null, Order.Status.SHIPPED, null, currentUser, null, null, null, null, null, null, null));
		model.addAttribute("unreadMessageCount", messageService.unreadMessageCount(null, currentUser));
		model.addAttribute("couponCodeCount", couponCodeService.count(null, currentUser, null, false, false));
		model.addAttribute("productFavoriteCount", productFavoriteService.count(currentUser));
		model.addAttribute("productNotifyCount", productNotifyService.count(currentUser, null, null, null));
		model.addAttribute("reviewCount", reviewService.count(currentUser, null, null, null));
		model.addAttribute("consultationCount", consultationService.count(currentUser, null, null));
		model.addAttribute("newOrders", orderService.findList(null, null, null, currentUser, null, null, null, null, null, null, null, NEW_ORDER_SIZE, null, null));
		return "member/index";
	}

}