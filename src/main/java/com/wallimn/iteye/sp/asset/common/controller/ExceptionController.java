package com.wallimn.iteye.sp.asset.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wallimn.iteye.sp.asset.common.config.GlobalConst;
import com.wallimn.iteye.sp.asset.common.exception.ExceptionDetail;

@RestController
@RequestMapping("/api/exception")
public class ExceptionController {

	@Autowired
	HttpServletRequest request;

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ExceptionDetail getException() {
		ExceptionDetail exception = (ExceptionDetail) this.request.getSession()
				.getAttribute(GlobalConst.globalExceptionKey);
		return exception;
	}

	/**
	 * 测试方法。增加一个异常，用于测试异常显示页面。
	 * @return
	 */
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String setException() {
		ExceptionDetail ed = new ExceptionDetail();
		ed.setCode(1);
		ed.setMsg("测试异常");
		ed.setUri("/api/exception/detail");
		request.getSession().setAttribute(GlobalConst.globalExceptionKey, ed);
		return "ok";
	}

	@RequestMapping(value = "detail", method = RequestMethod.DELETE)
	public String removeException() {
		request.getSession().removeAttribute(GlobalConst.globalExceptionKey);
		return "ok";
	}
}
