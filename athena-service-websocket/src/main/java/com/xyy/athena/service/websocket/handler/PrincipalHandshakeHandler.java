package com.xyy.athena.service.websocket.handler;

import com.xyy.athena.service.websocket.model.WebSocketUserAuthentication;
import com.xyy.athena.service.websocket.utils.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * 设置认证用户信息
 *
 * @author: 熊亚运
 * @date: 2019-06-12
 */
@Slf4j
public class PrincipalHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        HttpSession httpSession = WebSocketUtil.getSession(request);
        if (httpSession == null) {
            log.error("未登录系统，禁止登录!");
            return null;
        }
        String user = (String) httpSession.getAttribute("loginName");

        if(StringUtils.isEmpty(user)){
            log.error("未登录系统，禁止登录websocket!");
            return null;
        }
        log.info(" MyDefaultHandshakeHandler login = " + user);
        return new WebSocketUserAuthentication(user);
    }

}
