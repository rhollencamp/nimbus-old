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
import net.thewaffleshop.passwd.test.TestBase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Robert Hollencamp
 */
public class AccountAPITest extends TestBase
{
	@Resource
	private AccountAPI accountAPI;

	@Test
	public void testPassword()
	{
		Account account = new Account();
		String password = "1234";
		accountAPI.setPassword(account, password);

		assertThat("Encoded password should not equal raw password", account.getPasswordHash(), not(equalTo(password)));
		assertTrue(accountAPI.checkPassword(account, password));
	}
}
