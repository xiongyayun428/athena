package com.xiongyayun.athena.core.context;

/**
 * 临时保存当前请求号
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/21
 */
public class RequestNoContext {
	private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

	/**
	 * 保存请求号
	 *
	 * @author yubaoshan
	 * @date 2020/6/21 20:17
	 */
	public static void set(String requestNo) {
		CONTEXT_HOLDER.set(requestNo);
	}

	/**
	 * 获取请求号
	 *
	 * @author yubaoshan
	 * @date 2020/6/21 20:17
	 */
	public static String get() {
		return CONTEXT_HOLDER.get();
	}

	/**
	 * 清除请求号
	 *
	 * @author yubaoshan
	 * @date 2020/6/21 20:17
	 */
	public static void clear() {
		CONTEXT_HOLDER.remove();
	}
}
