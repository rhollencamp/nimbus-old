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
