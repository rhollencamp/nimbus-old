/*
 * Copyright 2013 rhollencamp.
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

import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 *
 * @author Robert Hollencamp
 */
public class Account implements Serializable
{
	private Long uid;

	@NotNull
	@Size(min = 5)
	private String userName;

	@Min(1)
	private Long sessionTimeout;

	@NotNull
	@Size(min = 60, max = 60)
	private String passwordHash;

	@NotNull
	@Size(min = 44, max = 44)
	private String secretKeyEncrypted;

	@NotNull
	@Size(min = 12, max = 12)
	private String secretKeySalt;

	@NotNull
	@Size(min = 24, max = 24)
	private String secretKeyIv;

	private long version;

	public Long getUid()
	{
		return uid;
	}

	public void setUid(Long uid)
	{
		this.uid = uid;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPasswordHash()
	{
		return passwordHash;
	}

	public void setPasswordHash(String hash)
	{
		this.passwordHash = hash;
	}

	public long getVersion()
	{
		return version;
	}

	public void setVersion(long version)
	{
		this.version = version;
	}

	public String getSecretKeyEncrypted()
	{
		return secretKeyEncrypted;
	}

	public void setSecretKeyEncrypted(String secretKeyEncrypted)
	{
		this.secretKeyEncrypted = secretKeyEncrypted;
	}

	public String getSecretKeySalt()
	{
		return secretKeySalt;
	}

	public void setSecretKeySalt(String secretKeySalt)
	{
		this.secretKeySalt = secretKeySalt;
	}

	public String getSecretKeyIv()
	{
		return secretKeyIv;
	}

	public void setSecretKeyIv(String secretKeyIv)
	{
		this.secretKeyIv = secretKeyIv;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Account)) {
			return false;
		}

		Account lhs = this;
		Account rhs = (Account) obj;
		return new EqualsBuilder()
				.append(lhs.getUserName(), rhs.getUserName())
				.isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
				.append(getUserName())
				.toHashCode();
	}
}
