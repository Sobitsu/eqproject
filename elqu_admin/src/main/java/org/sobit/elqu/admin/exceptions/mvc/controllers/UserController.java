package org.sobit.elqu.admin.exceptions.mvc.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hc.jp.db.entities.User;
import org.hc.jp.mvc.controllers.ControllerHelper;
import org.hc.jp.js.JSCommon;
import org.hc.jp.js.JSUser;
import org.hc.jp.users.UserAuth;
import org.sobit.elqu.admin.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Ctac
 */
//@Controller
//@RequestMapping(value="api/user")
public class UserController
{
	@Autowired
	private UserAuth userAuth;
	
	@RequestMapping(value="auth", method = RequestMethod.POST)
    public @ResponseBody Object auth(@RequestBody JSUser js, HttpSession session, HttpServletResponse response)
	{
		User user = userAuth.auth(js, session);
		
		Cookie cookieToken = removeUserTokenCookie();
		if(js.remember != null)
			if(js.remember.booleanValue())
				 cookieToken = createUserTokenCookie(user);
		response.addCookie(cookieToken);
		
		JSUser jsUser = new JSUser();
		jsUser.name = user.login;
		JSCommon jsResponse = new JSCommon();
		jsResponse.user = jsUser;
		return jsResponse;
    }
	
	@RequestMapping(value="logout", method = RequestMethod.GET)
    public @ResponseBody Object logout(HttpSession session, HttpServletResponse response)
	{
		userAuth.logout(session, userAuth.getCurrentUser(session));
		response.addCookie(removeUserTokenCookie());
		return "{}";
    }
	
	private Cookie createUserTokenCookie(User user)
	{
		String token = userAuth.createAndSaveUserToken(user);
		Cookie cookie = new Cookie(Constants.KEY_USER_AUTHED_TOKEN, token);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60*60*24*365);
		cookie.setPath("/");
		
		return cookie;
	}
	
	private Cookie removeUserTokenCookie()
	{
		Cookie cookie = new Cookie(Constants.KEY_USER_AUTHED_TOKEN, null);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		
		return cookie;
	}
	
	@RequestMapping(value="getCurrentUser", method = RequestMethod.GET)
    public @ResponseBody Object getCurrentUser(HttpSession session)
	{
		User user = userAuth.getCurrentUser(session);
		JSCommon js = new JSCommon();
		js.user = new JSUser(user.login);
		
		return js;
    }

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public void handleException(Exception exception, HttpServletRequest request, HttpServletResponse response)
	{
		ControllerHelper.handleException(exception, request, response);
	}
}
