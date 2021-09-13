package com.xiongyayun.athena.components.common.doc;

import com.xiongyayun.athena.components.common.AppMessage;

import java.util.List;

/**
 * ExtendPrintMsg
 *
 * @author Yayun.Xiong
 * @date 2021/9/13
 */
@FunctionalInterface
public interface ExtendPrintMsg {
	/**
	 * 打印扩展消息
	 * @param appMessage
	 * @return
	 */
	List<String> printMsg(AppMessage appMessage);
}
