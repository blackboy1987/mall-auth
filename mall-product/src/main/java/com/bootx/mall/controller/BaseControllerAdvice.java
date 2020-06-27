
package com.bootx.mall.controller;

import java.util.Date;

import com.bootx.mall.common.DateEditor;
import com.bootx.mall.common.StringEditor;
import com.bootx.mall.exception.ResourceNotFoundException;
import com.bootx.mall.exception.UnauthorizedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ControllerAdvice - 基类
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@ControllerAdvice("com.bootx.mall.controller")
public class BaseControllerAdvice {

	/**
	 * 数据绑定
	 * 
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Date.class, new DateEditor(true));
		binder.registerCustomEditor(String.class, "password", new StringEditor(true));
	}

	/**
	 * 异常处理
	 * 
	 * @param resourceNotFoundException
	 *            资源不存在
	 * @return 视图
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String exceptionHandler(ResourceNotFoundException resourceNotFoundException) {
		return "common/error/not_found";
	}

	/**
	 * 异常处理
	 * 
	 * @param typeMismatchException
	 *            类型配比错误
	 * @return 视图
	 */
	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String exceptionHandler(TypeMismatchException typeMismatchException) {
		return "common/error/type_mismatch";
	}

	/**
	 * 异常处理
	 * 
	 * @param unauthorizedException
	 *            无此访问权限
	 * @return 视图
	 */
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String exceptionHandler(UnauthorizedException unauthorizedException) {
		return "common/error/unauthorized";
	}

}