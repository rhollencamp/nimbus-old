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

import java.util.List;
import net.thewaffleshop.passwd.model.Account;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Robert Hollencamp
 */
@Repository
public class AccountRepository extends BaseRepository<Account>
{
	public AccountRepository()
	{
		super(Account.class);
	}

	/**
	 * Load account by username
	 *
	 * @param userName
	 * @return {@code null} if no account found
	 */
	public Account findByUserName(String userName)
	{
		String hql = "SELECT a FROM Account a WHERE a.userName LIKE :userName";
		List<Account> results = em
				.createQuery(hql, Account.class)
				.setParameter("userName", userName)
				.getResultList();
		for (Account a : results) {
			return a;
		}
		return null;
	}
}
