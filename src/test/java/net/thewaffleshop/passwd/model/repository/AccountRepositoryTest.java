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
package net.thewaffleshop.passwd.model.repository;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import net.thewaffleshop.passwd.model.Account;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 * @author rhollencamp
 */
public class AccountRepositoryTest extends RepositoryTestBase
{
	@Resource
	private AccountRepository accountRepository;

	@Test
	public void testLoad()
	{
		Account createdAccount = createAccount();
		em.clear();

		Account loadedAccount = accountRepository.load(createdAccount.getUid());
		Assert.assertEquals(createdAccount, loadedAccount);
	}

	@Test
	public void testFindByUserName()
	{
		Account createdAccount = createAccount();
		em.clear();

		Account foundAccount = accountRepository.findByUserName(createdAccount.getUserName());
		Assert.assertEquals(createdAccount, foundAccount);
	}

	private Account createAccount()
	{
		Account ret = new Account();
		ret.setUserName("userName");
		ret.setPassword("password");
		accountRepository.persist(ret);
		em.flush();
		return ret;
	}
}
