
package com.bootx.mall.auth.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.dao.ProductCategoryDao;
import com.bootx.mall.auth.entity.ProductCategory;
import com.bootx.mall.auth.entity.Store;
import com.bootx.mall.auth.entity.vo.ProductCategoryTreeVo;
import com.bootx.mall.auth.service.ProductCategoryService;
import com.bootx.mall.auth.util.GJsonUtils;
import com.bootx.mall.auth.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 商品分类
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategory, Long> implements ProductCategoryService {

	@Inject
	private ProductCategoryDao productCategoryDao;

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findList(Store store, Integer count, List<Filter> filters, List<Order> orders) {
		return productCategoryDao.findList(store, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findRoots() {
		return productCategoryDao.findRoots(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findRoots(Integer count) {
		return productCategoryDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "productCategory", condition = "#useCache")
	public List<ProductCategory> findRoots(Integer count, boolean useCache) {
		return productCategoryDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findParents(ProductCategory productCategory, boolean recursive, Integer count) {
		return productCategoryDao.findParents(productCategory, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "productCategory", condition = "#useCache")
	public List<ProductCategory> findParents(Long productCategoryId, boolean recursive, Integer count, boolean useCache) {
		ProductCategory productCategory = productCategoryDao.find(productCategoryId);
		if (productCategoryId != null && productCategory == null) {
			return Collections.emptyList();
		}
		return productCategoryDao.findParents(productCategory, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findTree() {
		return productCategoryDao.findChildren(null, true, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findChildren(ProductCategory productCategory, boolean recursive, Integer count) {
		return productCategoryDao.findChildren(productCategory, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "productCategory", condition = "#useCache")
	public List<ProductCategory> findChildren(Long productCategoryId, boolean recursive, Integer count, boolean useCache) {
		ProductCategory productCategory = productCategoryDao.find(productCategoryId);
		if (productCategoryId != null && productCategory == null) {
			return Collections.emptyList();
		}
		return productCategoryDao.findChildren(productCategory, recursive, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory" }, allEntries = true)
	public ProductCategory save(ProductCategory productCategory) {
		Assert.notNull(productCategory, "[Assertion failed] - productCategory is required; it must not be null");

		setValue(productCategory);
		stringRedisTemplate.delete("productCategoryTree");
		return super.save(productCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory" }, allEntries = true)
	public ProductCategory update(ProductCategory productCategory) {
		Assert.notNull(productCategory, "[Assertion failed] - productCategory is required; it must not be null");

		setValue(productCategory);
		for (ProductCategory children : productCategoryDao.findChildren(productCategory, true, null)) {
			setValue(children);
		}
		stringRedisTemplate.delete("productCategoryTree");
		return super.update(productCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory" }, allEntries = true)
	public ProductCategory update(ProductCategory productCategory, String... ignoreProperties) {
		stringRedisTemplate.delete("productCategoryTree");
		return super.update(productCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory" }, allEntries = true)
	public void delete(Long id) {
		stringRedisTemplate.delete("productCategoryTree");
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory" }, allEntries = true)
	public void delete(Long... ids) {
		stringRedisTemplate.delete("productCategoryTree");
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory" }, allEntries = true)
	public void delete(ProductCategory productCategory) {
		stringRedisTemplate.delete("productCategoryTree");
		super.delete(productCategory);
	}

	/**
	 * 设置值
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	private void setValue(ProductCategory productCategory) {
		if (productCategory == null) {
			return;
		}
		ProductCategory parent = productCategory.getParent();
		if (parent != null) {
			productCategory.setTreePath(parent.getTreePath() + parent.getId() + ProductCategory.TREE_PATH_SEPARATOR);
		} else {
			productCategory.setTreePath(ProductCategory.TREE_PATH_SEPARATOR);
		}
		productCategory.setGrade(productCategory.getParentIds().length);
	}

	@Override
	public List<ProductCategoryTreeVo> tree(boolean recursive){
		String treeStr = stringRedisTemplate.opsForValue().get("productCategoryTree");
		List<ProductCategoryTreeVo> productCategoryTreeVos = new ArrayList<>();
		if(StringUtils.isNotEmpty(treeStr)){
			productCategoryTreeVos = JsonUtils.toObject(treeStr, new TypeReference<LinkedList<ProductCategoryTreeVo>>() {
			});
			System.out.println(JsonUtils.toJson(productCategoryTreeVos));
			System.out.println(GJsonUtils.toJson(treeStr));
			return productCategoryTreeVos;
		}
		synchronized (this){
			System.out.println("=====================数据库");
			productCategoryTreeVos = productCategoryDao.tree(null,recursive);
			stringRedisTemplate.opsForValue().set("productCategoryTree",JsonUtils.toJson(productCategoryTreeVos));
			return productCategoryTreeVos;
		}
	}

    @Override
    public List<ProductCategoryTreeVo> findRootsVo(Integer count, boolean useCache) {
		String treeStr = stringRedisTemplate.opsForValue().get("productCategoryTree_"+count);
		if(StringUtils.isNotEmpty(treeStr)){
			return JsonUtils.toObject(treeStr, new TypeReference<List<ProductCategoryTreeVo>>() {
			});
		}
		List<ProductCategoryTreeVo> productCategoryTreeVos = productCategoryDao.tree(count,false);
		stringRedisTemplate.opsForValue().set("productCategoryTree_"+count,JsonUtils.toJson(productCategoryTreeVos));
		return productCategoryTreeVos;
    }

	@Override
	public List<ProductCategoryTreeVo> findParentsVo(Long productCategoryId, boolean recursive, Integer count, boolean useCache) {
		return null;
	}

	@Override
	public List<ProductCategoryTreeVo> findChildrenVo(Long productCategoryId, boolean recursive, Integer count, boolean useCache) {
		String treeStr = stringRedisTemplate.opsForValue().get("productCategoryTree_productCategoryId_"+count);
		if(StringUtils.isNotEmpty(treeStr)){
			return JsonUtils.toObject(treeStr, new TypeReference<List<ProductCategoryTreeVo>>() {
			});
		}
		List<ProductCategoryTreeVo> productCategoryTreeVos = productCategoryDao.tree(count,productCategoryId,false);
		stringRedisTemplate.opsForValue().set("productCategoryTree_productCategoryId_",JsonUtils.toJson(productCategoryTreeVos));
		return productCategoryTreeVos;
	}
}