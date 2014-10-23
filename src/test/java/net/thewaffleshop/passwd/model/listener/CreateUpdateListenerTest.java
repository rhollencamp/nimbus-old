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
package net.thewaffleshop.passwd.model.listener;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.thewaffleshop.passwd.api.AccountAPI;
import net.thewaffleshop.passwd.model.Account;
import net.thewaffleshop.passwd.model.repository.AccountRepository;
import net.thewaffleshop.passwd.test.TestBase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Robert Hollencamp
 */
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CreateUpdateListenerTest extends TestBase
{
	@Resource
	private AccountRepository accountRepository;

	@Resource
	private AccountAPI accountAPI;

	@PersistenceContext
	protected EntityManager em;

	@Test
	public void testPrePersist()
	{
		Account account = createAccount();
		Assert.assertNotNull(account.getCreated());
		Assert.assertNotNull(account.getUpdated());
		Assert.assertEquals(account.getCreated(), account.getUpdated());
	}

	@Test
	public void testPreUpdate()
	{
		Account account = createAccount();
		Long created = account.getCreated();

		// update account
		account.setSessionTimeout(5L);
		accountRepository.persist(account);
		em.flush();

		Assert.assertEquals(created, account.getCreated());
		Assert.assertNotEquals(account.getUpdated(), account.getCreated());
		Assert.assertTrue(account.getUpdated() > account.getCreated());
	}

	private Account createAccount()
	{
		Account ret = new Account();
		ret.setUserName("userName");
		accountAPI.setPassword(ret, "password");
		accountAPI.setSecretKey(ret, "password");
		accountRepository.persist(ret);
		return ret;
	}
}
