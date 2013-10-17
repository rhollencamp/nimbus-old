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
