package com.xiongyayun.athena.system.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * AuthenticationFactory
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/15
 */
public class AuthenticationFactory {

	public static Authentication extractAuthentication(HttpServletRequest req, String authKey) {
		Authentication authentication = null;
		HttpSession httpSession = req.getSession(false);
		if (httpSession != null) {
			Object object = httpSession.getAttribute(authKey);
			if (object instanceof Authentication)
				authentication = (Authentication)object;
		}
//		if (authentication == null) {
//			ExtraClassManagerProvider extraClassManagerProvider = (ExtraClassManagerProvider)PluginModule.getAgent(PluginModule.ExtraCore);
//			if (extraClassManagerProvider == null)
//				return null;
//			ClusterServerProcessor clusterServerProcessor = (ClusterServerProcessor)extraClassManagerProvider.getSingle("ClusterServerProcessor", DefaultClusterServerProcessor.class);
//			Map map = clusterServerProcessor.getUseInfoParaByCookie(paramHttpServletRequest);
//			return (Authentication)map.get("fr_authentication_key");
//		}
		return authentication;
	}
}
