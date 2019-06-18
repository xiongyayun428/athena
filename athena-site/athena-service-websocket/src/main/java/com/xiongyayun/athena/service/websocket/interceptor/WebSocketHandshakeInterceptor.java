package com.xiongyayun.athena.service.websocket.interceptor;

import com.xiongyayun.athena.service.websocket.utils.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * WebSocketHandshakeInterceptor
 *
 * @author: 熊亚运
 * @date: 2019-06-12
 */
@Slf4j
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        // websocket握手建立前调用，获取HttpSession
//        if (request instanceof ServletServerHttpRequest) {
//
//            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
//            // 这里从request中获取session,获取不到不创建，可以根据业务处理此段
//            HttpSession session = serverRequest.getServletRequest().getSession(this.isCreateSession());
//            if (session != null) {
//                // 这里打印一下session id 方便等下对比和springMVC获取到HttpSession是不是同一个
//                log.info("HttpSession key：" + session.getId());
//                // 获取到HttpSession后，可以根据自身业务，操作其中的信息，这里只是单纯的和websocket进行关联
//                attributes.put("HTTP.SESSION", session);
//                attributes.put("HTTP_SESSION", session);
//            } else {
//                log.warn("HttpSession is null");
//            }
//        }
        HttpSession httpSession = WebSocketUtil.getSession(request);
        if (httpSession == null) {
            log.error("未登录系统!");
            return false;
        }
        // 调用父类方法
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception ex) {
        // websocket握手建立后调用
        log.info("WebSocket连接握手成功");
    }
}