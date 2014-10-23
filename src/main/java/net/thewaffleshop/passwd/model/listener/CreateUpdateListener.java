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

import java.util.Date;
import net.thewaffleshop.passwd.model.Entity;

/**
 * JPA Entity Listener for maintaining created / updated timestamps
 *
 * @author Robert Hollencamp
 */
public class CreateUpdateListener
{
	/**
	 * When an entity is persisted for the first time, set created and updated timestamps
	 *
	 * @param entity
	 */
	public void prePersist(Entity entity)
	{
		long timestamp = new Date().getTime();
		entity.setCreated(timestamp);
		entity.setUpdated(timestamp);
	}

	/**
	 * When an entity is updated, set the updated timestamp
	 *
	 * @param entity
	 */
	public void preUpdate(Entity entity)
	{
		long timestamp = new Date().getTime();
		entity.setUpdated(timestamp);
	}
}
