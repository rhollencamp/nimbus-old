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

package net.thewaffleshop.passwd.service;


/**
 * An exception that can be reported back to the user; the message has meaning to the user
 *
 * @author Robert Hollencamp
 */
public class ReportableException extends RuntimeException
{
	public ReportableException()
	{
		super();
	}

	public ReportableException(String message)
	{
		super(message);
	}

	public ReportableException(Throwable cause)
	{
		super(cause);
	}

	public ReportableException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
