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
package net.thewaffleshop.passwd.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *
 * @author Robert Hollencamp
 */
public class Secret
{
	private Long uid;

	private Long version;

	@NotNull
	private Account account;

	@NotNull
	private String encryptedTitle;

	private String encryptedMetadata;

	@NotNull
	private String encryptedPassword;

	@NotNull
	@Size(min = 24, max = 24)
	private String iv;

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public String getEncryptedTitle()
	{
		return encryptedTitle;
	}

	public void setEncryptedTitle(String encryptedTitle)
	{
		this.encryptedTitle = encryptedTitle;
	}

	public String getEncryptedMetadata()
	{
		return encryptedMetadata;
	}

	public void setEncryptedMetadata(String encryptedMetadata)
	{
		this.encryptedMetadata = encryptedMetadata;
	}

	public String getEncryptedPassword()
	{
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword)
	{
		this.encryptedPassword = encryptedPassword;
	}

	public String getIv()
	{
		return iv;
	}

	public void setIv(String iv)
	{
		this.iv = iv;
	}

	public Long getUid()
	{
		return uid;
	}

	public void setUid(Long uid)
	{
		this.uid = uid;
	}

	public Long getVersion()
	{
		return version;
	}

	public void setVersion(Long version)
	{
		this.version = version;
	}
}
