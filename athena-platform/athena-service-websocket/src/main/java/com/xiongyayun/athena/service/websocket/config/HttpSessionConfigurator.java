package com.xiongyayun.athena.service.websocket.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * websocket获取HttpSession
 *
 * @author 熊亚运
 * @date 2019-06-12
 */
@Slf4j
public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        try {
            sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
        } catch (Exception e) {
            log.error("获取HttpSession失败" + e.getMessage(), e);
        }
    }
}
