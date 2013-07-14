/*
 * Copyright 2013 Robert Hollencamp
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
package net.thewaffleshop.passwd.api.crypto;

import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author Robert Hollencamp
 */
public class EncryptedString
{
	private String encryptedData;
	private String iv;
	private String salt;

	/**
	 * Get the encrypted data
	 *
	 * @return encrypted data as Base64 encoded string
	 */
	public String getEncryptedData()
	{
		return encryptedData;
	}

	/**
	 * Set the encrypted data
	 *
	 * @param encryptedData Base64 encoded
	 */
	public void setEncryptedData(String encryptedData)
	{
		this.encryptedData = encryptedData;
	}

	/**
	 * Set the encrypted data
	 *
	 * @param encryptedData
	 */
	public void setEncryptedData(byte[] encryptedData)
	{
		this.encryptedData = Base64.encodeBase64String(encryptedData);
	}

	/**
	 * Get the initialization vector
	 *
	 * @return Base64 encoded IV
	 */
	public String getIv()
	{
		return iv;
	}

	/**
	 * Set the initialization vector
	 *
	 * @param iv Base64 encoded IV
	 */
	public void setIv(String iv)
	{
		this.iv = iv;
	}

	/**
	 * Set the initialization vector
	 *
	 * @param iv
	 */
	public void setIv(byte[] iv)
	{
		this.iv = Base64.encodeBase64String(iv);
	}

	/**
	 * Get the salt
	 *
	 * @return Base64 encoded salt
	 */
	public String getSalt()
	{
		return salt;
	}

	/**
	 * Set the salt value
	 *
	 * @param salt Base64 encoded salt
	 */
	public void setSalt(String salt)
	{
		this.salt = salt;
	}

	/**
	 * Set the salt value
	 *
	 * @param salt
	 */
	public void setSalt(byte[] salt)
	{
		this.salt = Base64.encodeBase64String(salt);
	}
}
