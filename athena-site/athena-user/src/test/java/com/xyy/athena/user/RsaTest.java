package com.xiongyayun.athena.user;

import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.MD5;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

/**
 * RsaTest
 *
 * @author: 熊亚运
 * @date: 2019-06-17
 */
@Slf4j
public class RsaTest extends TestCase {

    public void testNewRsa() {
        RSA rsa = new RSA("RSA");
        log.info(rsa.getPublicKeyBase64());
        log.info(rsa.getPrivateKeyBase64());
    }

    public void testMD5() {
        byte[] salt = "for_$n(@RenSheng)_$n+=\"die\"".getBytes();
        MD5 md5 = new MD5(salt, 2, 1);
        log.info(String.valueOf(md5.getDigestLength()));
        log.info(md5.digestHex("adminadmin"));
        log.info(md5.digestHex16("adminadmin"));
    }
}
