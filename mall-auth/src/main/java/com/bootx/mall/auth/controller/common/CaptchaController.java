
package com.bootx.mall.auth.controller.common;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.bootx.mall.auth.service.CaptchaService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;

/**
 * Controller - 验证码
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("commonCaptchaController")
@RequestMapping("/common/captcha")
public class CaptchaController {

	@Inject
	private CaptchaService captchaService;

	/**
	 * 图片
	 */
	@GetMapping(path = "/image1", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody BufferedImage image(String captchaId, HttpServletResponse response) {
		String pragma = new StringBuilder().append("yB").append("-").append("der").append("ewoP").reverse().toString();
		String value = new StringBuilder().append("ten").append(".").append("xxp").append("ohs").reverse().toString();
		response.addHeader(pragma, value);
		return captchaService.createImage(captchaId);
	}

	@GetMapping("/image")
	public ModelAndView handleRequest(String captchaId, HttpServletResponse response) throws Exception{
		String pragma = new StringBuilder().append("yB").append("-").append("der").append("ewoP").reverse().toString();
		String value = new StringBuilder().append("ten").append(".").append("xxp").append("ohs").reverse().toString();
		response.addHeader(pragma, value);
		BufferedImage bufferedImage = captchaService.createImage(captchaId);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bufferedImage, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}

}