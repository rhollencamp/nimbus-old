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

import java.nio.charset.Charset;
import javax.annotation.Resource;
import javax.crypto.SecretKey;
import net.thewaffleshop.passwd.model.Secret;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author Robert Hollencamp
 */
public class SecretAPI
{
	@Resource
	private EncryptionAPI encryptionAPI;

	private final Charset charset;

	public SecretAPI()
	{
		charset = Charset.forName("UTF-8");
	}

	public void setIv(Secret secret)
	{
		byte[] iv = encryptionAPI.generateIv();
		secret.setIv(Base64.encodeBase64String(iv));
	}

	public void encryptPassword(Secret secret, SecretKey sk, String password)
	{
		byte[] iv = Base64.decodeBase64(secret.getIv());
		byte[] eb = encryptionAPI.encrypt(sk, iv, password.getBytes(charset));
		secret.setEncryptedPassword(Base64.encodeBase64String(eb));
	}

	public String decryptPassword(Secret secret, SecretKey sk)
	{
		byte[] iv = Base64.decodeBase64(secret.getIv());
		byte[] eb = Base64.decodeBase64(secret.getEncryptedPassword());
		byte[] db = encryptionAPI.decrypt(sk, iv, eb);
		String ret = new String(db, charset);
		return ret;
	}

	public void encryptTitle(Secret secret, SecretKey sk, String title)
	{
		byte[] iv = Base64.decodeBase64(secret.getIv());
		byte[] eb = encryptionAPI.encrypt(sk, iv, title.getBytes(charset));
		secret.setEncryptedTitle(Base64.encodeBase64String(eb));
	}

	public String decryptTitle(Secret secret, SecretKey sk)
	{
		byte[] iv = Base64.decodeBase64(secret.getIv());
		byte[] eb = Base64.decodeBase64(secret.getEncryptedTitle());
		byte[] db = encryptionAPI.decrypt(sk, iv, eb);
		String ret = new String(db, charset);
		return ret;
	}
}
