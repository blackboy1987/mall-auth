
package com.bootx.mall.auth.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.dao.SocialUserDao;
import com.bootx.mall.auth.entity.SocialUser;
import com.bootx.mall.auth.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 社会化用户
 * 
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class SocialUserDaoImpl extends BaseDaoImpl<SocialUser, Long> implements SocialUserDao {

	@Override
	public SocialUser find(String loginPluginId, String uniqueId) {
		if (StringUtils.isEmpty(loginPluginId) || StringUtils.isEmpty(uniqueId)) {
			return null;
		}
		try {
			String jpql = "select socialUser from SocialUser socialUser where socialUser.loginPluginId = :loginPluginId and socialUser.uniqueId = :uniqueId";
			return entityManager.createQuery(jpql, SocialUser.class).setParameter("loginPluginId", loginPluginId).setParameter("uniqueId", uniqueId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Page<SocialUser> findPage(User user, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SocialUser> criteriaQuery = criteriaBuilder.createQuery(SocialUser.class);
		Root<SocialUser> root = criteriaQuery.from(SocialUser.class);
		criteriaQuery.select(root);
		if (user != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("user"), user));
		}
		return super.findPage(criteriaQuery, pageable);
	}

}