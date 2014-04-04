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
package net.thewaffleshop.passwd.web;

import javax.annotation.Resource;
import net.thewaffleshop.passwd.service.AccountService;
import net.thewaffleshop.passwd.service.ReportableException;
import net.thewaffleshop.passwd.service.ReportableFieldException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author Robert Hollencamp
 */
@RequestMapping("/")
public class RootController
{
	final Logger logger = LoggerFactory.getLogger(RootController.class);

	@Resource
	private AccountService accountService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index()
	{
		return new TemplatedModelAndView("home");
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Object register(@RequestParam("userName") String userName, @RequestParam("password") String password)
	{
		ExtAjaxResponse response = new ExtAjaxResponse();

		try {
			accountService.createAccount(userName, password);
			response.success = true;
		} catch (ReportableFieldException e) {
			response.success = false;
			response.msg = "Please correct all field errors";
			response.addFieldError(e.getField(), e.getMessage());
		} catch (ReportableException e) {
			response.success = false;
			response.msg = e.getMessage();
		} catch (Exception e) {
			response.success = false;
			logger.error("Exception registering user", e);
		}

		return response;
	}
}
