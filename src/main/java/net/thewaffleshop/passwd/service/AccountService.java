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
