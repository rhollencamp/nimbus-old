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

import java.util.List;
import javax.annotation.Resource;
import javax.crypto.SecretKey;
import net.thewaffleshop.passwd.dto.SecretDTO;
import net.thewaffleshop.passwd.model.Account;
import net.thewaffleshop.passwd.service.ReportableException;
import net.thewaffleshop.passwd.service.SecretService;
import net.thewaffleshop.passwd.web.ajax.AjaxResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * Show secrets page
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

	/*
	 * Delete secret
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ExtAjaxResponse delete(
			@ModelAttribute("account") Account account,
			@RequestBody SecretDTO secretDTO)
	{
		ExtAjaxResponse response = new ExtAjaxResponse();
		try {
			secretService.deleteSecret(account, secretDTO.uid);
			response.success = true;
		} catch (ReportableException e) {
			response.success = false;
			response.msg = e.getMessage();
		} catch (Exception e) {
			response.success = false;
		}

		return response;
	}

	/*
	 * List secrets
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public List<SecretDTO> list(
			@ModelAttribute("account") Account account,
			@ModelAttribute("secretKey") SecretKey secretKey)
	{
		List<SecretDTO> secrets = secretService.listSecrets(account, secretKey);
		return secrets;
	}

	@ResponseBody
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ExtAjaxResponse view(
			@ModelAttribute("account") Account account,
			@ModelAttribute("secretKey") SecretKey secretKey,
			@RequestParam("uid") long uid)
	{
		ExtAjaxResponse response = new ExtAjaxResponse(true);
		response.data = secretService.loadSecret(account, secretKey, uid);
		return response;
	}
}
