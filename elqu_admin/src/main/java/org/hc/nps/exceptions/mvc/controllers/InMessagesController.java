package org.hc.nps.exceptions.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hc.jp.mvc.controllers.ControllerHelper;
import org.hc.nps.exceptions.mvc.js.JSInMessageTable;
import org.hc.nps.inMessage.InMessageTableGeter;
import org.hc.jp.users.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Ctac
 */
@Deprecated
@Controller
@RequestMapping(value="api/inMessages")
public class InMessagesController 
{
	@Autowired
	private UserAuth userAuth;
	
	@Autowired
	private InMessageTableGeter inMessageTableGeter;
	
	@RequestMapping(value="/getInMessagesTable", method = RequestMethod.POST)
    public @ResponseBody Object getPersonalTable(@RequestBody JSInMessageTable js, HttpSession session)
	{
		//return inMessageTableGeter.getInMessageTable(js, userAuth.getCurrentUser(session));
		return "";
    }
	
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public void handleException(Exception exception, HttpServletRequest request, HttpServletResponse response)
	{
		ControllerHelper.handleException(exception, request, response);
	}
}
