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
