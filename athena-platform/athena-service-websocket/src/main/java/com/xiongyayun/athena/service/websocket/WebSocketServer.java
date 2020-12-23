//package com.xiongyayun.athena.service.websocket;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.concurrent.CopyOnWriteArraySet;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * WebSocket
// * ws://localhost:9090/admin/websocket
// *
// * @author 熊亚运
// * @date 2019-06-06
// */
//@Slf4j
//@ServerEndpoint(value = "/websocket")
//@Component
//public class WebSocketServer {
//    /**
//     * 记录当前在线连接数。
//     */
//    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);
//    /**
//     * 存放每个客户端对应的Session对象。
//     */
//    private static CopyOnWriteArraySet<Session> SESSIONS = new CopyOnWriteArraySet<>();
//
//    /**
//     * 连接建立成功调用的方法
//     */
//    @OnOpen
//    public void onOpen(Session session) {
//        SESSIONS.add(session);
//        // 在线数加1
//        int cnt = ONLINE_COUNT.incrementAndGet();
//        log.info("有连接加入，当前连接数为：{}", cnt);
//        try {
//            send(session, "连接成功");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose(Session session) {
//        SESSIONS.remove(session);
//        int cnt = ONLINE_COUNT.decrementAndGet();
//        log.info("有连接关闭，当前连接数为：{}", cnt);
//    }
//
//    /**
//     * 收到客户端消息后调用的方法
//     *
//     * @param message 客户端发送过来的消息
//     */
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        log.info("来自客户端的消息：{}", message);
//        try {
//            send(session, "收到消息，消息内容：" + message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 出现错误
//     *
//     * @param session
//     * @param e
//     */
//    @OnError
//    public void onError(Session session, Throwable e) {
//        log.error("发生错误：{}，Session ID： {}", e.getMessage(), session.getId());
//        e.printStackTrace();
//    }
//
//    /**
//     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
//     *
//     * @param session
//     * @param message   消息
//     * @throws IOException
//     */
//    public static void send(Session session, String message) throws IOException {
//        try {
//            session.getBasicRemote().sendText(String.format("%s (From Server，Session ID=%s)", message, session.getId()));
//        } catch (IOException e) {
//            log.error("发送消息出错：" + e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    /**
//     * 指定Session发送消息
//     *
//     * @param sessionId
//     * @param message   消息
//     * @throws IOException
//     */
//    public static void send(String sessionId, String message) throws IOException {
//        Session session = null;
//        for (Session s : SESSIONS) {
//            if (s.getId().equals(sessionId)) {
//                session = s;
//                break;
//            }
//        }
//        if (session != null) {
//            send(session, message);
//        } else {
//            log.warn("没有找到你指定ID的会话：{}", sessionId);
//        }
//    }
//
//    /**
//     * 群发消息
//     *
//     * @param message   消息
//     * @throws IOException
//     */
//    public static void sendAll(String message) throws IOException {
//        for (Session session : SESSIONS) {
//            if (session.isOpen()) {
//                send(session, message);
//            }
//        }
//    }
//
//
//}
