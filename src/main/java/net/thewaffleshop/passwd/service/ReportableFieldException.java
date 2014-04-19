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
 *
 * @author Robert Hollencamp
 */
public class ReportableFieldException extends ReportableException
{
	private final String field;

	public ReportableFieldException(String field, String message)
	{
		// @todo use a message formatter
		super(message);

		this.field = field;
	}

	public ReportableFieldException(String field, String message, Throwable cause)
	{
		// @todo use a message formatter
		super(message, cause);

		this.field = field;
	}

	public String getField()
	{
		return field;
	}
}
