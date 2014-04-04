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

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Robert Hollencamp
 */
public class ExtAjaxResponse
{
	public boolean success;
	public String msg;
	public Object data;
	public Map<String, String> errors;

	public ExtAjaxResponse()
	{
	}

	public ExtAjaxResponse(boolean success)
	{
		this.success = success;
	}

	public ExtAjaxResponse(boolean success, String fieldName, String fieldError)
	{
		this.success = success;
		errors = new HashMap<>(1);
		errors.put(fieldName, fieldError);
	}

	public void addFieldError(String field, String message)
	{
		if (errors == null) {
			errors = new HashMap<>();
		}
		errors.put(field, message);
	}
}
