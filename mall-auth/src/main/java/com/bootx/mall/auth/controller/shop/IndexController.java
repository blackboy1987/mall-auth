
package com.bootx.mall.auth.controller.shop;

import com.bootx.mall.auth.entity.vo.ProductCategoryTreeVo;
import com.bootx.mall.auth.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

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

	@Inject
	private ProductCategoryService productCategoryService;

	/**
	 * 首页
	 */
	@GetMapping
	public String index(ModelMap model) {
		// model.addAttribute("productCategoryTree",productCategoryService.tree());
		return "shop/index";
	}

	@GetMapping("product/tree")
	public @ResponseBody
	List<ProductCategoryTreeVo> productTree() {
		long start = System.currentTimeMillis();
		List<ProductCategoryTreeVo> tree = productCategoryService.tree(true);
		System.out.println(System.currentTimeMillis()-start);
		return tree;
	}
}