/*
 * Copyright 2013 Robert Hollencamp
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
