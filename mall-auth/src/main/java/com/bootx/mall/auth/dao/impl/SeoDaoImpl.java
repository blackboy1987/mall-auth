
package com.bootx.mall.auth.dao.impl;

import javax.persistence.NoResultException;

import com.bootx.mall.auth.dao.SeoDao;
import com.bootx.mall.auth.entity.Seo;
import org.springframework.stereotype.Repository;

/**
 * Dao - SEO设置
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class SeoDaoImpl extends BaseDaoImpl<Seo, Long> implements SeoDao {

	@Override
	public Seo find(Seo.Type type) {
		if (type == null) {
			return null;
		}
		try {
			String jpql = "select seo from Seo seo where seo.type = :type";
			return entityManager.createQuery(jpql, Seo.class).setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}