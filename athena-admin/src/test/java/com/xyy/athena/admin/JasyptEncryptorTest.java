package com.xyy.athena.admin;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * JasyptTest
 *
 * @author Yayun.Xiong
 * @date 2019-05-26
 */
public class JasyptEncryptorTest {
    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("xiongyayun428");
        String password = textEncryptor.encrypt("app");

        System.out.println(password);
        System.out.println(textEncryptor.decrypt(password));
    }
}
