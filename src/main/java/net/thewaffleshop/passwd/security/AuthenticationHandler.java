/*
 * Copyright (C) 2014 The Waffleshop
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.thewaffleshop.passwd.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


/**
 *
 * @author Robert Hollencamp
 */
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler
{
	private static final String TARGET_URL = "/passwords/";

	public AuthenticationHandler()
	{
	}

	/**
	 * Successful login; send the redirect URL
	 *
	 * @param request
	 * @param response
	 * @param authentication
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
	{
		AccountAuthenticationToken auth = (AccountAuthenticationToken) authentication;

		HttpSession session = request.getSession();
		session.setAttribute("account", auth.getAccount());
		session.setAttribute("secretKey", auth.getSecretKey());

		String url = ServletUriComponentsBuilder.fromContextPath(request).path(TARGET_URL).build().toUriString();

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.getWriter().write("{\"success\": true, \"url\": \"" + StringEscapeUtils.escapeJavaScript(url) + "\"}");
	}

	/**
	 * Unsuccessful login; send error
	 *
	 * @param request
	 * @param response
	 * @param exception
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
	{
		String msg = exception.getMessage();

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.getWriter().write("{\"success\": false, \"msg\": \"" + StringEscapeUtils.escapeJavaScript(msg) + "\"}");
	}
}
