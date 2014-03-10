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
package net.thewaffleshop.passwd.service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import net.thewaffleshop.passwd.api.AccountAPI;
import net.thewaffleshop.passwd.model.Account;
import net.thewaffleshop.passwd.model.repository.AccountRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Robert Hollencamp
 */
public class AccountService
{
	@Resource
	private AccountAPI accountAPI;

	@Resource
	private AccountRepository accountRepository;

	@Transactional(readOnly = true)
	public Account authenticateUser(String userName, String password) throws AuthenticationException
	{
		Account account = accountRepository.findByUserName(userName);
		if (account == null) {
			throw new UsernameNotFoundException("Authentication failed; check your username and password");
		}
		if (!accountAPI.checkPassword(account, password)) {
			throw new BadCredentialsException("Authentication failed; check your username and password");
		}
		return account;
	}

	@Transactional
	public Account createAccount(String userName, String password)
	{
		Account account = new Account();
		account.setUserName(userName);
		accountAPI.setPassword(account, password);
		accountAPI.setSecretKey(account, password);

		try {
			accountRepository.persist(account);
			return account;
		} catch (javax.validation.ConstraintViolationException e) {
			// @todo ability to throw all violations in one exception...ReportableFieldsException mebe?
			for (ConstraintViolation cv : e.getConstraintViolations()) {
				throw new ReportableFieldException(cv.getPropertyPath().toString(), cv.getMessage());
			}
			throw e;
		} catch (DataIntegrityViolationException e) {
			// determine if this was caused by username unique constraint
			Throwable cause = e.getCause();
			if (cause instanceof org.hibernate.exception.ConstraintViolationException) {
				org.hibernate.exception.ConstraintViolationException cve = (org.hibernate.exception.ConstraintViolationException) cause;
				if ("USERNAMEUNIQUE".equals(cve.getConstraintName())) {
					throw new ReportableFieldException("userName", "User name already in use", e);
				}
			}
			throw e;
		}
	}
}
