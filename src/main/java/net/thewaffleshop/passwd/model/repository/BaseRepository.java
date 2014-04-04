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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Robert Hollencamp
 */
@Transactional(propagation = Propagation.MANDATORY)
public abstract class BaseRepository<T>
{
	private final Class<T> clss;

	@PersistenceContext
	protected EntityManager em;

	protected BaseRepository(Class clss)
	{
		this.clss = clss;
	}

	public void persist(T entity)
	{
		em.persist(entity);
	}

	public T load(long uid)
	{
		T ret = em.find(clss, uid);
		return ret;
	}
}
