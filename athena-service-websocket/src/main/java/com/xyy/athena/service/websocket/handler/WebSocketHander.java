package com.xyy.athena.service.websocket.handler;

import com.xyy.athena.service.websocket.model.WebSocketSpring;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息处理中心
 *
 * @author: 熊亚运
 * @date: 2019-06-12
 */
@Slf4j
@Component("webSocketHander")
public class WebSocketHander extends AbstractWebSocketHandler {

    /**
     * 用来存放每个客户端对应的webSocket对象。
     */
    private static Map<String, WebSocketSpring> webSocketInfo;

    static {
        // concurrent包的线程安全map
        webSocketInfo = new ConcurrentHashMap<String, WebSocketSpring>();
    }

    // 服务器与客户端初次websocket连接成功执行
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        log.debug("websocket 连接成功......");

        // 连接成功当前对象放入websocket对象集合
        WebSocketSpring bean = new WebSocketSpring();
        bean.setSession(session);

        webSocketInfo.put(session.getId(), bean);

        log.info("客户端连接服务器session id :" + session.getId() + "，当前连接数：" + webSocketInfo.size());

    }

    // 接受消息处理消息
    @Override
    public void handleMessage(WebSocketSession webSocketSession,
                              WebSocketMessage<?> webSocketMessage)
            throws Exception {
        /*
        获取客户端发送的消息,这里使用文件消息，也就是字符串进行接收
        消息可以通过字符串，或者字节流进行接收
        TextMessage String/byte[]接收均可以
        BinaryMessage byte[]接收
        */
        log.info("客户端发送消息" + webSocketMessage.getPayload().toString());
        TextMessage message = new TextMessage(webSocketMessage.getPayload().toString());
        /*
        这里直接是字符串，做群发，如果要指定发送，可以在前台平均ID，后台拆分后获取需要发送的人。
        也可以做一个单独的controller，前台把ID传递过来，调用方法发送，在登录的时候把所有好友的标识符传递到前台，
        然后通过标识符发送私信消息
        */
        this.batchSendMessage(message);

    }

    // 连接错误时触发
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }

        log.debug("链接出错，关闭链接......");
        webSocketInfo.remove(webSocketSession.getId());
    }

    // 关闭websocket时触发
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

        log.debug("链接关闭......" + closeStatus.toString());
        webSocketInfo.remove(webSocketSession.getId());
    }

    /**
     * 给所有在线用户发送消息（这里用的文本消息）
     *
     * @param message
     */
    public void batchSendMessage(TextMessage message) {

        Set<Map.Entry<String, WebSocketSpring>> setInfo =
                webSocketInfo.entrySet();
        for (Map.Entry<String, WebSocketSpring> entry : setInfo) {
            WebSocketSpring bean = entry.getValue();
            try {
                bean.getSession().sendMessage(message);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 给指定用户发送消息
     *
     * @param userId
     * @param message
     */
    public void sendMessage(String userId, TextMessage message) {
        WebSocketSpring bean = webSocketInfo.get(userId);
        try {
            bean.getSession().sendMessage(message);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
