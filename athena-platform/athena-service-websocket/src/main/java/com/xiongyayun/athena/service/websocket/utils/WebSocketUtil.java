package com.xiongyayun.athena.service.websocket.utils;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.http.HttpSession;

/**
 * WebSocketUtil
 *
 * @author 熊亚运
 * @date 2019-06-12
 */
public class WebSocketUtil {

    /**
     * 获取HttpSession
     * @see org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor
     *
     * @param request
     * @return
     */
    public static HttpSession getSession(ServerHttpRequest request, boolean create) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(create);
        }
        return null;
    }

    /**
     * 获取HttpSession
     *
     * @param request
     * @return
     */
    public static HttpSession getSession(ServerHttpRequest request) {
        return getSession(request, false);
    }
}
