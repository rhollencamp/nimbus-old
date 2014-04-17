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
 */package net.thewaffleshop.passwd.model;

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
	@Size(min = 5, max = 255)
	private String userName;

	@Min(1)
	private Long sessionTimeout;

	@NotNull
	@Size(min = 60, max = 60)
	private String passwordHash;

	@NotNull
	@Size(min = 48, max = 48)
	private byte[] secretKeyEncrypted;

	@NotNull
	@Size(min = 8, max = 8)
	private byte[] secretKeySalt;

	@NotNull
	@Size(min = 16, max = 16)
	private byte[] secretKeyIv;

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

	public byte[] getSecretKeyEncrypted()
	{
		return secretKeyEncrypted;
	}

	public void setSecretKeyEncrypted(byte[] secretKeyEncrypted)
	{
		this.secretKeyEncrypted = secretKeyEncrypted;
	}

	public byte[] getSecretKeySalt()
	{
		return secretKeySalt;
	}

	public void setSecretKeySalt(byte[] secretKeySalt)
	{
		this.secretKeySalt = secretKeySalt;
	}

	public byte[] getSecretKeyIv()
	{
		return secretKeyIv;
	}

	public void setSecretKeyIv(byte[] secretKeyIv)
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
