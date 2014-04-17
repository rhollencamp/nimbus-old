/*
 * Copyright 2014 Robert Hollencamp.
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
package net.thewaffleshop.passwd.service;

import javax.annotation.Resource;
import net.thewaffleshop.passwd.test.TestBase;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 * @author Robert Hollencamp
 */
public class AccountServiceTest extends TestBase
{
	@Resource
	private AccountService accountService;

	@Test
	public void testDuplicateUserName()
	{
		try {
			accountService.createAccount("FOOFOO", "FOOFOO");
			accountService.createAccount("FOOFOO", "FOOFOO");
			Assert.fail("Expected Exception");
		} catch (ReportableException e) {
			Assert.assertEquals("User name already in use", e.getMessage());
		}
	}
}
