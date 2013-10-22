/*
 * Copyright 2013 Robert Hollencamp.
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
