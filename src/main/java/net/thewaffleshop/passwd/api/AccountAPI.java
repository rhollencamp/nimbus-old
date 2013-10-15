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
package net.thewaffleshop.passwd.api;

import javax.annotation.Resource;
import net.thewaffleshop.passwd.model.Account;
import net.thewaffleshop.passwd.model.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 *
 * @author Robert Hollencamp
 */
public class AccountAPI
{
	@Resource
	private PasswordEncoder passwordEncoder;

	@Resource
	private AccountRepository accountRepository;

	public Account load(String userName)
	{
		Account ret = accountRepository.findByUserName(userName);
		return ret;
	}

	public void save(Account account)
	{
		accountRepository.persist(account);
	}

	/**
	 * Set the password for an account
	 *
	 * @param account
	 * @param password
	 */
	public void setPassword(Account account, String password)
	{
		String encodedPassword = passwordEncoder.encode(password);
		account.setPassword(encodedPassword);
	}

	/**
	 * Test to see if the given password matches the one set on the account
	 *
	 * @param account
	 * @param password
	 * @return {@code true} if the passwords match and {@code false} if they do not
	 */
	public boolean checkPassword(Account account, String password)
	{
		return passwordEncoder.matches(password, account.getPassword());
	}
}
