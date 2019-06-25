package com.xiongyayun.athena.admin;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * JasyptTest
 *
 * @author: Yayun.Xiong
 * @date 2019-05-26
 */
public class JasyptEncryptorTest {
    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        // 密钥
        textEncryptor.setPassword("xiongyayun428");
        // 被加密字段
        String password = textEncryptor.encrypt("");

        System.out.println(password);
        System.out.println(textEncryptor.decrypt(password));
    }
}
