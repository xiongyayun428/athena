package com.xiongyayun.athena.service.websocket.service;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;

/**
 * 基于javax websocket通讯
 *
 * @author 熊亚运
 * @date 2019-06-12
 */
public interface WebSocketServer {
    /**
     * 连接建立成功调用的方法
     * @param session session 对象
     */
    void onOpen(Session session, EndpointConfig config);

    /**
     * 断开连接方法
     */
    void onClose(Session session);

    /**
     * 收到客户端消息后调用的方法
     * @param session session 对象
     * @param message 返回客户端的消息
     */
    void onMessage(Session session, String message);

    /**
     * 发生异常时触发的方法
     * @param session session 对象
     * @param throwable 抛出的异常
     */
    void onError(Session session,Throwable throwable);

    /**
     * 向单个客户端发送消息
     * @param session session 对象
     * @param message 发送给客户端的消息
     */
    void sendMessage(Session session, String message);

    /**
     * 向所有在线用户群发消息
     * @param message 发送给客户端的消息
     */
    void batchSendMessage(String message);
}
