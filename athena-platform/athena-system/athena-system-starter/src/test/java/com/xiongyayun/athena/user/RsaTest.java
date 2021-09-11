package com.xiongyayun.athena.user;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.MD5;
import junit.framework.TestCase;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * RsaTest
 *
 * @author 熊亚运
 * @date 2019-06-17
 */
public class RsaTest extends TestCase {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RsaTest.class);

    public void testNewRsa() {
		byte[] salt = "for_$n(@RenSheng)_$n+=\"die\"".getBytes();
        RSA rsa = new RSA("RSA");
		KeyPair kp = SecureUtil.generateKeyPair("RSA", 2048, salt);

		final PublicKey publicKey = kp.getPublic();
		final PrivateKey privateKey = kp.getPrivate();

        log.info(Base64.encode(publicKey.getEncoded()));
        log.info(Base64.encode(privateKey.getEncoded()));
    }

    public void testMD5() {
        byte[] salt = "for_$n(@RenSheng)_$n+=\"die\"".getBytes();
        MD5 md5 = new MD5(salt, 2, 1);
        log.info(String.valueOf(md5.getDigestLength()));
        log.info(md5.digestHex("adminadmin"));
        log.info(md5.digestHex16("adminadmin"));
    }
}
