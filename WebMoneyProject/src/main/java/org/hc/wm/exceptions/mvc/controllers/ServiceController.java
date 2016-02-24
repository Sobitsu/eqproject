package org.hc.wm.exceptions.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hc.jp.mvc.controllers.ControllerHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Ctac
 */
@Controller
@RequestMapping(value="api/service")
public class ServiceController 
{

	
	@RequestMapping(value="appClose", method = RequestMethod.GET)
    public @ResponseBody Object logout()
	{
		System.exit(1);
		return "{\"success\":true}";
    }
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public void handleException(Exception exception, HttpServletRequest request, HttpServletResponse response)
	{
		ControllerHelper.handleException(exception, request, response);
	}
}
