package com.xyy.athena.user.controller;

import cn.hutool.crypto.asymmetric.RSA;
import com.xyy.athena.core.annotation.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RsaController
 *
 * @author: 熊亚运
 * @date: 2019-06-13
 */
@Slf4j
@RestController
@RequestMapping("/rsa")
public class RsaController {

    @Logger("RSA PublicKey")
    @GetMapping("publicKey")
    public String publicKey() {
        RSA rsa = new RSA();
        log.info(rsa.getPublicKeyBase64());
        log.info(rsa.getPrivateKeyBase64());
        return rsa.getPublicKeyBase64();
    }

    @Logger("RSA PrivateKey")
    @GetMapping("privateKey")
    public String privateKey() {
        RSA rsa = new RSA();
        log.info(rsa.getPublicKeyBase64());
        log.info(rsa.getPrivateKeyBase64());
        return rsa.getPrivateKeyBase64();
    }
}
