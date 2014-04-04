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
