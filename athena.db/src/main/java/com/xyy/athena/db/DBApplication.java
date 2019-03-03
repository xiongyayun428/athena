package com.xyy.athena.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * DBApplication
 *
 * @author Yayun.Xiong
 * @date 2019/03/03
 */
@SpringBootApplication
@EnableAdminServer
public class DBApplication {
    public static void main(String[] args) {
        SpringApplication.run(DBApplication.class, args);
    }
}
