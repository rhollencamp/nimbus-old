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

import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;


/**
 *
 * @author Robert Hollencamp
 */
@Component
public class CryptoAPI
{
	private static final int ITERATIONS = 65536;
	private static final int KEYLEN_BITS = 128;
	private static final int SALT_LEN = 8;
	private static final Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * Calculate a {@link SecretKey} for the given password and salt
	 *
	 * TODO make the algorithms configurable
	 *
	 * @param password
	 * @param salt
	 * @return
	 */
	private SecretKey generateSecretKey(byte[] salt, String password)
	{
		try {
			KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEYLEN_BITS);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			SecretKey tmpKey = secretKeyFactory.generateSecret(keySpec);

			SecretKeySpec secretKey = new SecretKeySpec(tmpKey.getEncoded(), "AES");
			return secretKey;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Encrypt data
	 *
	 * @param password
	 * @param data
	 * @return
	 */
	public EncryptedString encrypt(String password, String data) {
		try {
			// create key
			byte[] salt = generateSalt();
			SecretKey secretKey = generateSecretKey(salt, password);

			// initialize cipher
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			// save IV from cipher
			AlgorithmParameters parameters = cipher.getParameters();
			byte[] iv = parameters.getParameterSpec(IvParameterSpec.class).getIV();

			// encrypt data
			byte[] dataBytes = data.getBytes(CHARSET);
			byte[] encryptedData = cipher.doFinal(dataBytes);

			EncryptedString ret = new EncryptedString();
			ret.setIv(iv);
			ret.setSalt(salt);
			ret.setEncryptedData(encryptedData);
			return ret;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Decrypt data
	 *
	 * @param password
	 * @param data
	 * @return
	 */
	public String decrypt(String password, EncryptedString data)
	{
		try {
			// create key
			byte[] salt = Base64.decodeBase64(data.getSalt());
			SecretKey secretKey = generateSecretKey(salt, password);

			// get IV
			byte[] iv = Base64.decodeBase64(data.getIv());
			IvParameterSpec ivps = new IvParameterSpec(iv);

			// initialize cipher
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivps);

			// decrypt
			byte[] encryptedData = Base64.decodeBase64(data.getEncryptedData());
			byte[] dataBytes = cipher.doFinal(encryptedData);
			String ret = new String(dataBytes, CHARSET);
			return ret;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Generate a salt value using {@link SecureRandom}
	 *
	 * @return
	 */
	private byte[] generateSalt()
	{
		byte[] salt = new byte[SALT_LEN];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(salt);
		return salt;
	}
}
