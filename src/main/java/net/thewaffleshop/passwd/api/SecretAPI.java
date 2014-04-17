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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.annotation.Resource;
import javax.crypto.SecretKey;
import net.thewaffleshop.passwd.dto.SecretDTO;
import net.thewaffleshop.passwd.model.Secret;


/**
 *
 * @author Robert Hollencamp
 */
public class SecretAPI
{
	@Resource
	private EncryptionAPI encryptionAPI;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final Charset charset = Charset.forName("UTF-8");

	public void encryptSecret(Secret secret, SecretKey sk, SecretDTO secretDTO)
	{
		// serialize DTO
		String json;
		try {
			json = objectMapper.writeValueAsString(secretDTO);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		// encrypt
		byte[] iv = getIv(secret);
		byte[] eb = encryptionAPI.encrypt(sk, iv, json.getBytes(charset));
		secret.setEncryptedSecret(eb);
	}

	public SecretDTO decryptSecret(Secret secret, SecretKey sk)
	{
		byte[] iv = getIv(secret);
		byte[] eb = secret.getEncryptedSecret();
		byte[] db = encryptionAPI.decrypt(sk, iv, eb);

		// de-serialize JSON
		SecretDTO ret;
		try {
			String json = new String(db, charset);
			ret = objectMapper.readValue(json, SecretDTO.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// copy UID
		ret.uid = secret.getUid();

		return ret;
	}

	/**
	 * Get the IV for a given secret. If the secret does not have an IV set, one is generated
	 *
	 * @param secret
	 * @return
	 */
	private byte[] getIv(Secret secret)
	{
		byte[] iv = secret.getIv();

		// generate IV if one is not set
		if (iv == null) {
			iv = encryptionAPI.generateIv();
			secret.setIv(iv);
		}

		return iv;
	}
}
