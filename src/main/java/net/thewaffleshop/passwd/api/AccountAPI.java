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
package net.thewaffleshop.passwd.api;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import net.thewaffleshop.passwd.model.Account;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 *
 * @author Robert Hollencamp
 */
public class AccountAPI
{
	@Resource
	private PasswordEncoder passwordEncoder;

	@Resource
	private EncryptionAPI encryptionAPI;

	/**
	 * Set the password for an account
	 *
	 * @param account
	 * @param password
	 */
	public void setPassword(Account account, String password)
	{
		String encodedPassword = passwordEncoder.encode(password);
		account.setPasswordHash(encodedPassword);
	}

	/**
	 * Test to see if the given password matches the one set on the account
	 *
	 * @param account
	 * @param password
	 * @return {@code true} if the passwords match and {@code false} if they do not
	 */
	public boolean checkPassword(Account account, String password)
	{
		return passwordEncoder.matches(password, account.getPasswordHash());
	}

	/**
	 * Generate a new {@link SecretKey} for an account
	 *
	 * @param account
	 * @param password
	 */
	public void setSecretKey(Account account, String password)
	{
		// do not overwrite an existing key
		if (account.getSecretKeyEncrypted() != null) {
			throw new IllegalStateException();
		}

		byte[] secretKey = encryptionAPI.serializeSecretKey(encryptionAPI.createSecretKey());

		// encrypt the secret key
		byte[] salt = encryptionAPI.generateSalt();
		byte[] iv = encryptionAPI.generateIv();
		SecretKey sk = encryptionAPI.createSecretKey(password, salt);
		byte[] esk = encryptionAPI.encrypt(sk, iv, secretKey);

		// store on account
		account.setSecretKeyEncrypted(Base64.encodeBase64String(esk));
		account.setSecretKeyIv(Base64.encodeBase64String(iv));
		account.setSecretKeySalt(Base64.encodeBase64String(salt));
	}

	/**
	 * Decrypt and return the secret key for an account
	 *
	 * @param account
	 * @param password
	 * @return
	 */
	public SecretKey getSecretKey(Account account, String password)
	{
		byte[] esk = Base64.decodeBase64(account.getSecretKeyEncrypted());

		byte[] salt = Base64.decodeBase64(account.getSecretKeySalt());
		byte[] iv = Base64.decodeBase64(account.getSecretKeyIv());
		SecretKey sk = encryptionAPI.createSecretKey(password, salt);
		byte[] secretKey = encryptionAPI.decrypt(sk, iv, esk);
		return encryptionAPI.deserializeSecretKey(secretKey);
	}
}
