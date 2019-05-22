package com.xyy.athena.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CORS跨域访问
 *
 * @author: 熊亚运
 * @date: 2019-05-22
 */
@Configuration
public class CorsConfiguration {
    public static final String _HEADER_TOKEN_NAME_ = "Access-Token";

    @Bean
    @Order(Integer.MIN_VALUE)
    public CorsFilter corsFilter() {
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        // 授权的时间
        config.setMaxAge(0L);
        // 跨域session共享
        config.setAllowCredentials(true);
        config.addAllowedHeader(_HEADER_TOKEN_NAME_);
        config.addExposedHeader(_HEADER_TOKEN_NAME_);
        config.addExposedHeader("Vary");
        config.addExposedHeader("Cache-Control");
        config.addExposedHeader("Content-Language");
        config.addExposedHeader("Content-Type");
        config.addExposedHeader("Expires");
        config.addExposedHeader("Last-Modified");
        config.addExposedHeader("Pragma");
        config.addExposedHeader("Content-Disposition");
        // 1 设置访问源地址
        config.addAllowedOrigin("*");
        // 2 设置访问源请求头
        config.addAllowedHeader("*");
        // 3 设置访问源请求方法
        config.addAllowedMethod("*");


        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        return new CorsFilter(configSource);
    }
}
