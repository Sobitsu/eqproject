package org.hc.nps.exceptions.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hc.jp.mvc.controllers.ControllerHelper;
import org.hc.jp.users.UserAuth;

import org.hc.nps.exceptions.mvc.js.JSReportTable;
import org.hc.nps.reports.ReportsTableGetter;
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
@Controller
@RequestMapping(value="api/reports")
public class ReportsController 
{
	@Autowired
	private UserAuth userAuth;
	
	@Autowired
	private ReportsTableGetter reportsTableGetter;
	@Autowired
	
	@RequestMapping(value="/getData", method = RequestMethod.POST)
    public @ResponseBody Object getPersonalTable(@RequestBody JSReportTable js, HttpSession session)
	{
		return reportsTableGetter.getDataForTable(js, userAuth.getCurrentUser(session));
    }
	
	@RequestMapping(value="/generateFiles", method = RequestMethod.POST)
    public @ResponseBody Object generateFies(@RequestBody JSReportTable js, HttpSession session)
	{
		try
		{
//			if (js.items.length != 0)  export230.exportlist230(js);
			return "{\"success\":true}";
		}
		catch (Exception e){
			return "{\"success\":false}";}
			
	}


	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public void handleException(Exception exception, HttpServletRequest request, HttpServletResponse response)
	{
		ControllerHelper.handleException(exception, request, response);
	}
}
