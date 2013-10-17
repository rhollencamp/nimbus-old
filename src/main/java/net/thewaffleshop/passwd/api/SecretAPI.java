/*
 * Copyright 2013 Robert Hollencamp.
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

	public void encryptPassword(Secret secret, SecretKey sk, String password)
	{
		byte[] iv = encryptionAPI.generateIv();
		byte[] eb = encryptionAPI.encrypt(sk, iv, password.getBytes(charset));
		secret.setEncryptedPassword(Base64.encodeBase64String(eb));
		secret.setIv(Base64.encodeBase64String(iv));
	}

	public String decryptPassword(Secret secret, SecretKey sk)
	{
		byte[] iv = Base64.decodeBase64(secret.getIv());
		byte[] eb = Base64.decodeBase64(secret.getEncryptedPassword());
		byte[] db = encryptionAPI.decrypt(sk, iv, eb);
		String ret = new String(db, charset);
		return ret;
	}
}
