package com.xiongyayun.athena.components.swagger;

import com.xiongyayun.athena.components.common.ApplicationMessage;
import com.xiongyayun.athena.components.common.doc.ExtendPrintMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * SwaggerExtendPrintMsg
 *
 * @author Yayun.Xiong
 * @date 2021/9/13
 */
public class SwaggerExtendPrintMsg implements ExtendPrintMsg {
	/**
	 * 打印扩展消息
	 *
	 * @param appMessage
	 * @return
	 */
	@Override
	public List<String> printMsg(ApplicationMessage appMessage) {
		List<String> msg = new ArrayList<>();
		msg.add("Api Docs: \t\t" + appMessage.getProtocol() + "://" + appMessage.getIp() + ":" + appMessage.getPort() + appMessage.getContextPath() + "swagger-ui/\n\t");
		msg.add("Api Docs: \t\t" + appMessage.getProtocol() + "://" + appMessage.getIp() + ":" + appMessage.getPort() + appMessage.getContextPath() + "doc.html");
		return msg;
	}
}
