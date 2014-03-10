/*
 * Copyright 2013 Robert Hollencamp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
