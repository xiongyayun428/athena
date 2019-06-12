package com.xyy.athena.service.websocket.model;

import java.security.Principal;

/**
 * <websocket登录连接对象>
 * <用于保存websocket连接过程中需要存储的业务参数>
 *
 * @author: 熊亚运
 * @date: 2019-06-12
 */
public class WebSocketUserAuthentication implements Principal {

    /**
     * 用户身份标识符
     */
    private String token;

    public WebSocketUserAuthentication(String token) {
        this.token = token;
    }

    /**
     * 获取用户登录令牌
     * @return
     */
    @Override
    public String getName() {
        return token;
    }
}
