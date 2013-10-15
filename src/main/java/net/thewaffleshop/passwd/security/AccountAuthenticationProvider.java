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
import net.thewaffleshop.passwd.api.AccountAPI;
import net.thewaffleshop.passwd.model.Account;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


/**
 *
 * @author Robert Hollencamp
 */
public class AccountAuthenticationProvider implements AuthenticationProvider
{
	@Resource
	private AccountAPI accountAPI;

	@Resource
	TransactionTemplate transactionTemplate;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		final String userName = authentication.getName();
		final String password = authentication.getCredentials().toString();

		transactionTemplate.execute(new TransactionCallbackWithoutResult()
		{
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus ts)
			{
				Account account = accountAPI.load(userName);
				if (account == null) {
					throw new UsernameNotFoundException(userName);
				}

				if (!accountAPI.checkPassword(account, password)) {
					throw new BadCredentialsException(null);
				}
			}
		});

		List<SimpleGrantedAuthority> auths = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		Authentication auth = new UsernamePasswordAuthenticationToken(userName, password, auths);
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
