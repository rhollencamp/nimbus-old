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

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author Robert Hollencamp
 */
public class EncryptionAPI
{
	public static final int SALT_LEN = 8;
	public static final int IV_LEN = 16;

	private final SecureRandom secureRandom;

	public EncryptionAPI()
	{
		secureRandom = new SecureRandom();
	}

	/**
	 * Generate a random salt
	 *
	 * @return
	 */
	public byte[] generateSalt()
	{
		byte[] ret = new byte[SALT_LEN];
		secureRandom.nextBytes(ret);
		return ret;
	}

	/**
	 * Generate a random initialization vector
	 *
	 * @return
	 */
	public byte[] generateIv()
	{
		byte[] ret = new byte[IV_LEN];
		secureRandom.nextBytes(ret);
		return ret;
	}

	/**
	 * Generate a {@link SecretKey} from a password and salt
	 *
	 * @param password
	 * @param salt
	 * @return
	 */
	public SecretKey createSecretKey(String password, byte[] salt)
	{
		try {
			PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 1024, 256);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			SecretKey secretKey = factory.generateSecret(keySpec);
			return new SecretKeySpec(secretKey.getEncoded(), "AES");
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Generate a random AES {@link SecretKey}
	 *
	 * @return
	 */
	public SecretKey createSecretKey()
	{
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(256);
			SecretKey key = keyGen.generateKey();
			return key;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Serialize a secret key
	 *
	 * @param sk
	 * @return
	 */
	public byte[] serializeSecretKey(SecretKey sk)
	{
		return sk.getEncoded();
	}

	/**
	 * Deserialize a secret key
	 *
	 * @param sk
	 * @return
	 */
	public SecretKey deserializeSecretKey(byte[] sk)
	{
		return new SecretKeySpec(sk, 0, sk.length, "AES");
	}

	/**
	 * Encrypt
	 *
	 * @param secretKey
	 * @param iv
	 * @param secret
	 * @return
	 */
	public byte[] encrypt(SecretKey secretKey, byte[] iv, byte[] secret)
	{
		try {
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
			byte[] ret = cipher.doFinal(secret);
			return ret;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Decrypt
	 *
	 * @param secretKey
	 * @param iv
	 * @param secret
	 * @return
	 */
	public byte[] decrypt(SecretKey secretKey, byte[] iv, byte[] secret)
	{
		try {
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
			byte[] ret = cipher.doFinal(secret);
			return ret;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
