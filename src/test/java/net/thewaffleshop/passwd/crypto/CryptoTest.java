package net.thewaffleshop.passwd.crypto;

import javax.annotation.Resource;
import net.thewaffleshop.passwd.api.crypto.CryptoAPI;
import net.thewaffleshop.passwd.api.crypto.EncryptedString;
import net.thewaffleshop.passwd.test.TestBase;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 * @author Robert Hollencamp
 */
public class CryptoTest extends TestBase
{
	@Resource
	private CryptoAPI cryptoAPI;

	@Test
	public void testEncryptDecrypt()
	{
		final String password = "PASSWORD";
		final String data = "1234";

		EncryptedString encrypted = cryptoAPI.encrypt(password, data);
		String decryptedData = cryptoAPI.decrypt(password, encrypted);
		Assert.assertEquals(data, decryptedData);
	}
}
