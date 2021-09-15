package com.xiongyayun.athena.components.druid;

import com.xiongyayun.athena.components.common.ApplicationMessage;
import com.xiongyayun.athena.components.common.doc.ExtendPrintMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * DruidExtendPrintMsg
 *
 * @author Yayun.Xiong
 * @date 2021/9/13
 */
public class DruidExtendPrintMsg implements ExtendPrintMsg {
	/**
	 * 打印扩展消息
	 *
	 * @param appMessage
	 * @return
	 */
	@Override
	public List<String> printMsg(ApplicationMessage appMessage) {
		List<String> msg = new ArrayList<>();
		msg.add("Druid Monitor: \t" + appMessage.getProtocol() + "://" + appMessage.getIp() + ":" + appMessage.getPort() + appMessage.getContextPath() + "druid/");
			return msg;
	}
}
