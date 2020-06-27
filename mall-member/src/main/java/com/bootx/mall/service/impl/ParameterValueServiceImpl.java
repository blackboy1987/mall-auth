
package com.bootx.mall.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bootx.mall.entity.ParameterValue;
import com.bootx.mall.service.ParameterValueService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Service - 参数值
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class ParameterValueServiceImpl implements ParameterValueService {

	@Override
	public void filter(List<ParameterValue> parameterValues) {
		CollectionUtils.filter(parameterValues, new Predicate() {
			public boolean evaluate(Object object) {
				ParameterValue parameterValue = (ParameterValue) object;
				if (parameterValue == null || StringUtils.isEmpty(parameterValue.getGroup())) {
					return false;
				}
				CollectionUtils.filter(parameterValue.getEntries(), new Predicate() {
					private Set<String> set = new HashSet<>();

					public boolean evaluate(Object object) {
						ParameterValue.Entry entry = (ParameterValue.Entry) object;
						return entry != null && StringUtils.isNotEmpty(entry.getName()) && StringUtils.isNotEmpty(entry.getValue()) && set.add(entry.getName());
					}
				});
				return CollectionUtils.isNotEmpty(parameterValue.getEntries());
			}
		});
	}

}