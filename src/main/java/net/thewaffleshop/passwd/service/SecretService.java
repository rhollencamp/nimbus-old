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
package net.thewaffleshop.passwd.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.crypto.SecretKey;
import net.thewaffleshop.passwd.api.SecretAPI;
import net.thewaffleshop.passwd.dto.SecretDTO;
import net.thewaffleshop.passwd.model.Account;
import net.thewaffleshop.passwd.model.Secret;
import net.thewaffleshop.passwd.model.repository.SecretRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Robert Hollencamp
 */
public class SecretService
{
	@Resource
	private SecretRepository secretRepository;

	@Resource
	private SecretAPI secretAPI;

	@Transactional
	public void saveSecret(Account account, SecretKey secretKey, SecretDTO secretDTO)
	{
		Secret secret = getSecret(account, secretDTO);
		if (secretDTO.password != null) {
			secretAPI.encryptPassword(secret, secretKey, secretDTO.password);
		}
		if (secretDTO.title != null) {
			secretAPI.encryptTitle(secret, secretKey, secretDTO.title);
		}
		secretRepository.persist(secret);
	}

	@Transactional(readOnly = true)
	public List<SecretDTO> listSecrets(Account account, SecretKey secretKey)
	{
		List<Secret> secrets = secretRepository.findByAccount(account);
		ArrayList<SecretDTO> ret = new ArrayList<SecretDTO>(secrets.size());
		for (Secret secret : secrets) {
			SecretDTO dto = new SecretDTO();
			dto.uid = secret.getUid();
			dto.title = secretAPI.decryptTitle(secret, secretKey);
			ret.add(dto);
		}
		return ret;
	}

	@Transactional
	public SecretDTO loadSecret(Account account, SecretKey secretKey, long uid)
	{
		Secret secret = secretRepository.load(uid);
		SecretDTO ret = new SecretDTO();
		ret.uid = uid;
		ret.title = secretAPI.decryptTitle(secret, secretKey);
		ret.password = secretAPI.decryptPassword(secret, secretKey);
		return ret;
	}

	private Secret getSecret(Account account, SecretDTO secretDTO)
	{
		Secret secret;
		if (secretDTO.uid != null) {
			secret = secretRepository.load(secretDTO.uid);
			if (!account.equals(secret.getAccount())) {
				throw new IllegalStateException();
			}
		} else {
			secret = new Secret();
			secret.setAccount(account);
			secretAPI.setIv(secret);
		}
		return secret;
	}
}
