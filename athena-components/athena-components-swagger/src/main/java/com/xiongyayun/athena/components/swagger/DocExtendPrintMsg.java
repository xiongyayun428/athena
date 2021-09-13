package com.xiongyayun.athena.components.swagger;

import com.xiongyayun.athena.components.common.doc.ExtendPrintMsg;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * DocExtendPrintMsg
 *
 * @author Yayun.Xiong
 * @date 2021/9/13
 */
@Configuration
public class DocExtendPrintMsg {

	@Bean
	public ExtendPrintMsg extendPrintMsg() {
		return appMessage -> {
			List<String> msg = new ArrayList<>();
			msg.add("Api Docs: \t\t" + appMessage.getProtocol() + "://" + appMessage.getIp() + ":" + appMessage.getPort() + appMessage.getContextPath() + "swagger-ui/\n\t");
			msg.add("Api Docs: \t\t" + appMessage.getProtocol() + "://" + appMessage.getIp() + ":" + appMessage.getPort() + appMessage.getContextPath() + "doc.html");
			return msg;
		};
	}

	@Bean
	public ExtendPrintMsg druidExtendPrintMsg() {
		return appMessage -> {
			List<String> msg = new ArrayList<>();
			msg.add("Druid Monitor: \t" + appMessage.getProtocol() + "://" + appMessage.getIp() + ":" + appMessage.getPort() + appMessage.getContextPath() + "druid/");
			return msg;
		};
	}
}
