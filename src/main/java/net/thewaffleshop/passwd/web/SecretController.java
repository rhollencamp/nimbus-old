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

import java.util.List;
import javax.annotation.Resource;
import javax.crypto.SecretKey;
import net.thewaffleshop.passwd.dto.SecretDTO;
import net.thewaffleshop.passwd.model.Account;
import net.thewaffleshop.passwd.service.SecretService;
import net.thewaffleshop.passwd.web.ajax.AjaxResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author Robert Hollencamp
 */
@RequestMapping("/passwords/")
@SessionAttributes({"secretKey", "account"})
public class SecretController
{
	private final Logger LOG = LoggerFactory.getLogger(SecretController.class);

	@Resource
	private SecretService secretService;

	/*
	 * List secrets
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(
			@ModelAttribute("account") Account account,
			@ModelAttribute("secretKey") SecretKey secretKey)
	{
		TemplatedModelAndView mav = new TemplatedModelAndView("passwords");
		return mav;
	}

	/*
	 * Save secret
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public AjaxResponse save(
			@ModelAttribute("account") Account account,
			@ModelAttribute("secretKey") SecretKey secretKey,
			@RequestBody SecretDTO secretDTO)
	{
		try {
			secretService.saveSecret(account, secretKey, secretDTO);
			return new AjaxResponse(true);
		} catch (Exception e) {
			LOG.error("Exception saving secret", e);
			return new AjaxResponse(false);
		}
	}

	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public Object list(
			@ModelAttribute("account") Account account,
			@ModelAttribute("secretKey") SecretKey secretKey)
	{
		List<SecretDTO> secrets = secretService.listSecrets(account, secretKey);
		return secrets;
	}
}
