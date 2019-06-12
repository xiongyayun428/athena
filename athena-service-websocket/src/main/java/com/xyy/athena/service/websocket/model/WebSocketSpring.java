package com.xyy.athena.service.websocket.model;

import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocketSpring
 *
 * @author: 熊亚运
 * @date: 2019-06-12
 */
public class WebSocketSpring {
    private WebSocketSession session;

    /**
     * 连接错误次数
     */
    private AtomicInteger erroerLinkCount = new AtomicInteger(0);

    public int getErroerLinkCount() {
        // 线程安全,以原子方式将当前值加1，注意：这里返回的是自增前的值
        return erroerLinkCount.getAndIncrement();
    }

    public void cleanErrorNum() {
        // 清空计数
        erroerLinkCount = new AtomicInteger(0);
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "WebSocketBean{" + "session=" + session + ", erroerLinkCount="
                + erroerLinkCount + '}';
    }
}
