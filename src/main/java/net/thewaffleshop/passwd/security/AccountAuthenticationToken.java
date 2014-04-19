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
package net.thewaffleshop.passwd.security;

import java.util.Collection;
import javax.crypto.SecretKey;
import net.thewaffleshop.passwd.model.Account;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


/**
 *
 * @author Robert Hollencamp
 */
public class AccountAuthenticationToken extends UsernamePasswordAuthenticationToken
{
	private final Account account;
	private final SecretKey secretKey;

	public AccountAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Account account, SecretKey secretKey)
	{
		super(principal, credentials, authorities);
		this.account = account;
		this.secretKey = secretKey;
	}

	public Account getAccount()
	{
		return account;
	}

	public SecretKey getSecretKey()
	{
		return secretKey;
	}
}
