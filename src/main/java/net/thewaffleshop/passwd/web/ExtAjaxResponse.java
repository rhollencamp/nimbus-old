/*
 * Copyright 2014 Robert Hollencamp.
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
