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

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author Robert Hollencamp
 */
@RequestMapping("/passwords/")
@SessionAttributes("textEncryptor")
public class PasswordController
{
	@RequestMapping("/")
	public ModelAndView index(@ModelAttribute("textEncryptor") TextEncryptor te)
	{
		return new TemplatedModelAndView("passwords");
	}
}
