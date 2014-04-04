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
