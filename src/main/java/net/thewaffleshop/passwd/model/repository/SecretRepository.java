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
import net.thewaffleshop.passwd.model.Secret;


/**
 *
 * @author Robert Hollencamp
 */
public class SecretRepository extends BaseRepository<Secret>
{
	public SecretRepository()
	{
		super(Secret.class);
	}

	/**
	 * Find all secrets for a given account
	 *
	 * @param account
	 * @return
	 */
	public List<Secret> findByAccount(Account account)
	{
		String hql = "SELECT s FROM Secret s WHERE s.account = :account";
		List<Secret> results = em
				.createQuery(hql, Secret.class)
				.setParameter("account", account)
				.getResultList();
		return results;
	}
}
