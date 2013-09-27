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
package net.thewaffleshop.passwd.test;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.thewaffleshop.passwd.model.Account;
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
public class TestPersistence extends TestBase
{
	@PersistenceContext
	EntityManager em;

	@Test
	public void testStuff()
	{
		Account user = new Account();
		user.setUserName("lol@wut.com");
		user.setPassword("hihihi");
		em.persist(user);
		em.flush();

		// make sure ID gets assigned
		em.refresh(user);
		Assert.assertNotNull(user.getUid());

		List<Account> resultList = em.createQuery("SELECT a FROM Account a", Account.class).getResultList();
		Assert.assertNotNull(resultList);
		Assert.assertEquals(1, resultList.size());
	}
}
