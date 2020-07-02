
package com.bootx.mall.controller.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.common.Results;
import com.bootx.mall.entity.vo.SkuVo;
import com.bootx.mall.exception.ResourceNotFoundException;
import com.bootx.mall.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.bootx.mall.entity.Attribute;
import com.bootx.mall.entity.BaseEntity;
import com.bootx.mall.entity.Brand;
import com.bootx.mall.entity.Product;
import com.bootx.mall.entity.ProductCategory;
import com.bootx.mall.entity.ProductTag;
import com.bootx.mall.entity.Promotion;
import com.bootx.mall.entity.Store;
import com.bootx.mall.entity.StoreProductCategory;

/**
 * Controller - 商品
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@RestController("shopSearchController")
@RequestMapping("/search")
public class SearchController extends BaseController {

	@Inject
	private SearchService searchService;

	@GetMapping("/product")
	private Page<SkuVo> search(String keyword, Product.Type type, Store.Type storeType, Store store, Boolean isOutOfStock, Boolean isStockAlert, BigDecimal startPrice, BigDecimal endPrice, Product.OrderType orderType, Pageable pageable){
		return searchService.search(keyword, type, storeType, store, isOutOfStock, isStockAlert, startPrice, endPrice, orderType, pageable);
	}
}