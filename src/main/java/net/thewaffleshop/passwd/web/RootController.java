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
package net.thewaffleshop.passwd.web;

import javax.annotation.Resource;
import net.thewaffleshop.passwd.service.AccountService;
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
		accountService.createAccount(userName, password);
		return "success";
	}
}
