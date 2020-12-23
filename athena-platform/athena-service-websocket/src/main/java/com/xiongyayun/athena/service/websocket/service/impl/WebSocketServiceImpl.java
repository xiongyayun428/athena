package com.xiongyayun.athena.service.websocket.service.impl;

import com.xiongyayun.athena.service.websocket.config.HttpSessionConfigurator;
import com.xiongyayun.athena.service.websocket.model.WebSocket;
import com.xiongyayun.athena.service.websocket.service.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketServiceImpl
 *
 * @author 熊亚运
 * @date 2019-06-12
 */
@Slf4j
@ServerEndpoint(value = "/javax/websocket", configurator = HttpSessionConfigurator.class)
@Component("webSocketService")
public class WebSocketServiceImpl implements WebSocketServer {

    /**
     * 错误最大重试次数
     */
    private static final int MAX_ERROR_NUM = 10;

    /**
     * 用来存放每个客户端对应的webSocket对象。
     */
    private static Map<String, WebSocket> webSocketInfo;

    static {
        // concurrent包的线程安全map
        webSocketInfo = new ConcurrentHashMap<>();
    }

    @OnOpen
    @Override
    public void onOpen(Session session, EndpointConfig config) {

        // 如果是session没有激活的情况，就是没有请求获取或session，这里可能会取出空，需要实际业务处理
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        if (httpSession != null) {
            log.info("获取到httpsession" + httpSession.getId());
        } else {
            log.error("未获取到httpsession");
        }

        // 连接成功当前对象放入websocket对象集合
        WebSocket bean = new WebSocket();
        bean.setSession(session);
        webSocketInfo.put(session.getId(), bean);

        log.info("客户端连接服务器session id :" + session.getId() + "，当前连接数：" + webSocketInfo.size());
    }

    @OnClose
    @Override
    public void onClose(Session session) {

        // 客户端断开连接移除websocket对象
        webSocketInfo.remove(session.getId());
        log.info("客户端断开连接，当前连接数：" + webSocketInfo.size());

    }

    @OnMessage
    @Override
    public void onMessage(Session session, String message) {

        log.info("客户端 session id: " + session.getId() + "，消息:" + message);

        // 此方法为客户端给服务器发送消息后进行的处理，可以根据业务自己处理，这里返回页面
        sendMessage(session, "服务端返回" + message);

    }

    @OnError
    @Override
    public void onError(Session session, Throwable throwable) {

        log.error("发生错误" + throwable.getMessage(), throwable);
    }

    @Override
    public void sendMessage(Session session, String message) {

        try {
            // 发送消息
            session.getBasicRemote().sendText(message);

            // 清空错误计数
            webSocketInfo.get(session.getId()).cleanErrorNum();
        } catch (Exception e) {
            log.error("发送消息失败" + e.getMessage(), e);
            int errorNum = webSocketInfo.get(session.getId()).getErroerLinkCount();

            // 小于最大重试次数重发
            if (errorNum <= MAX_ERROR_NUM) {
                sendMessage(session, message);
            } else {
                log.error("发送消息失败超过最大次数");
                // 清空错误计数
                webSocketInfo.get(session.getId()).cleanErrorNum();
            }
        }
    }

    @Override
    public void batchSendMessage(String message) {
        Set<Map.Entry<String, WebSocket>> set = webSocketInfo.entrySet();
        for (Map.Entry<String, WebSocket> map : set) {
            sendMessage(map.getValue().getSession(), message);
        }
    }
}
