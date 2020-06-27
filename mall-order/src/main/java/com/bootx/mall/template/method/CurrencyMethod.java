
package com.bootx.mall.template.method;

import com.bootx.mall.common.Setting;
import com.bootx.mall.util.FreeMarkerUtils;
import com.bootx.mall.util.SystemUtils;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

/**
 * 模板方法 - 货币格式化
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class CurrencyMethod implements TemplateMethodModelEx {

	public static CurrencyMethod currencyMethod;

	@PostConstruct
	public void init() {
		currencyMethod = this;
	}

	/**
	 * 执行
	 * 
	 * @param arguments
	 *            参数
	 * @return 结果
	 */
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		BigDecimal amount = FreeMarkerUtils.getArgument(0, BigDecimal.class, arguments);
		Boolean showSign = FreeMarkerUtils.getArgument(1, Boolean.class, arguments);
		Boolean showUnit = FreeMarkerUtils.getArgument(2, Boolean.class, arguments);
		if (amount != null) {
			Setting setting = SystemUtils.getSetting();
			String price = String.valueOf(setting.setScale(amount));
			if (showSign != null && showSign) {
				price = setting.getCurrencySign() + price;
			}
			if (showUnit != null && showUnit) {
				price += setting.getCurrencyUnit();
			}
			return new SimpleScalar(price);
		}
		return null;
	}

}