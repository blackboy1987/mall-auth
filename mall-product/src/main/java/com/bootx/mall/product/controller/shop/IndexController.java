
package com.bootx.mall.product.controller.shop;

import com.bootx.mall.product.service.SeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 * Controller - 首页
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("shopIndexController")
@RequestMapping("/")
public class IndexController extends BaseController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 首页
	 */
	@GetMapping
	public String index(ModelMap model) {
		stringRedisTemplate.opsForValue().set("abc","abcedfg");

		System.out.println(stringRedisTemplate.opsForValue().get("abc"));
		return "shop/index";
	}

}