package com.xiongyayun.athena.service.websocket.config;

import com.xiongyayun.athena.service.websocket.handler.WebSocketHander;
import com.xiongyayun.athena.service.websocket.interceptor.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket配置类
 *
 * @author 熊亚运
 * @date 2019-06-12
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * 注册消息处理器，并映射连接地址
     *
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册消息处理器，并添加自定义拦截器，支持websocket的连接访问
        registry.addHandler(new WebSocketHander(), "/spring/websocket")
                .addInterceptors(new WebSocketHandshakeInterceptor());

        /*
        注册消息处理器，并添加自定义拦截器,添加不支持websocket的连接访问
        SockJs是一个WebSocket的通信js库，Spring对这个js库进行了后台的自动支持，
        也就是说，如果使用SockJs，那么我们就不需要对后台进行更多的配置，只需要加上withSockJS()这一句就可以了
         */
        registry.addHandler(new WebSocketHander(), "/spring/stomp/websocket")
                .addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS();
    }
}
