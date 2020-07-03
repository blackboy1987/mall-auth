
package com.bootx.mall.product.service.impl;

import javax.inject.Inject;

import com.bootx.mall.product.dao.SnDao;
import com.bootx.mall.product.entity.Sn;
import com.bootx.mall.product.service.SnService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 序列号
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class SnServiceImpl implements SnService {

	@Inject
	private SnDao snDao;

	@Override
	@Transactional
	public String generate(Sn.Type type) {
		return snDao.generate(type);
	}

}