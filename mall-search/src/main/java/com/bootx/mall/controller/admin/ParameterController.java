
package com.bootx.mall.controller.admin;

import javax.inject.Inject;

import com.bootx.mall.common.Pageable;
import com.bootx.mall.common.Results;
import com.bootx.mall.entity.BaseEntity;
import com.bootx.mall.entity.Parameter;
import com.bootx.mall.service.ParameterService;
import com.bootx.mall.service.ProductCategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.AndPredicate;
import org.apache.commons.collections4.functors.UniquePredicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 参数
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("adminParameterController")
@RequestMapping("/admin/parameter")
public class ParameterController extends BaseController {

	@Inject
	private ParameterService parameterService;
	@Inject
	private ProductCategoryService productCategoryService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(Long sampleId, ModelMap model) {
		model.addAttribute("maxNameSize", Parameter.MAX_NAME_SIZE);
		model.addAttribute("sample", parameterService.find(sampleId));
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		return "admin/parameter/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity<?> save(Parameter parameter, Long productCategoryId) {
		CollectionUtils.filter(parameter.getNames(), new AndPredicate(new UniquePredicate(), new Predicate() {
			public boolean evaluate(Object object) {
				String name = (String) object;
				return StringUtils.isNotEmpty(name);
			}
		}));
		parameter.setProductCategory(productCategoryService.find(productCategoryId));
		if (!isValid(parameter, BaseEntity.Save.class)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		parameterService.save(parameter);
		return Results.OK;
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("maxNameSize", Parameter.MAX_NAME_SIZE);
		model.addAttribute("parameter", parameterService.find(id));
		return "admin/parameter/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(Parameter parameter) {
		CollectionUtils.filter(parameter.getNames(), new AndPredicate(new UniquePredicate(), new Predicate() {
			public boolean evaluate(Object object) {
				String name = (String) object;
				return StringUtils.isNotEmpty(name);
			}
		}));
		if (!isValid(parameter, BaseEntity.Update.class)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		parameterService.update(parameter, "productCategory");
		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", parameterService.findPage(pageable));
		return "admin/parameter/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(Long[] ids) {
		parameterService.delete(ids);
		return Results.OK;
	}

}