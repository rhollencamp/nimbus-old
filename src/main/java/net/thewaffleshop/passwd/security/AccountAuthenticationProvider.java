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

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.crypto.SecretKey;
import net.thewaffleshop.passwd.api.AccountAPI;
import net.thewaffleshop.passwd.model.Account;
import net.thewaffleshop.passwd.service.AccountService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


/**
 *
 * @author Robert Hollencamp
 */
public class AccountAuthenticationProvider implements AuthenticationProvider
{
	@Resource
	private AccountService accountService;

	@Resource
	private AccountAPI accountAPI;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		final String userName = authentication.getName();
		final String password = authentication.getCredentials().toString();

		Account user = accountService.authenticateUser(userName, password);
		SecretKey sk = accountAPI.getSecretKey(user, password);

		List<SimpleGrantedAuthority> auths = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		Authentication auth = new AccountAuthenticationToken(userName, password, auths, user, sk);
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
