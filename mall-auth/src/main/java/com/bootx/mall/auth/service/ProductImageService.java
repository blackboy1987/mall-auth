
package com.bootx.mall.auth.service;

import java.util.List;

import com.bootx.mall.auth.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service - 商品图片
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface ProductImageService {

	/**
	 * 商品图片过滤
	 * 
	 * @param productImages
	 *            商品图片
	 */
	void filter(List<ProductImage> productImages);

	/**
	 * 生成商品图片
	 * 
	 * @param multipartFile
	 *            文件
	 * @return 商品图片
	 */
	ProductImage generate(MultipartFile multipartFile);

}