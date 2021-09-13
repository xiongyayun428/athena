package com.xiongyayun.athena.components.druid;

import com.alibaba.druid.util.Utils;

import javax.servlet.*;
import java.io.IOException;

/**
 * 除去页面底部的广告的过滤器
 *
 * @author Yayun.Xiong
 * @date 2021/9/13
 */
public class RemoveDruidAdFilter implements Filter {
	/**
	 *
	 */
	private static final String FILE_PATH = "support/http/resources/js/common.js";


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
		// 重置缓冲区，响应头不会被重置
		response.resetBuffer();
		// 获取common.js
		String text = Utils.readFromResource(FILE_PATH);
		// 正则替换banner, 除去底部的广告信息
		text = text.replaceAll("<a.*?banner\"></a><br/>", "");
		text = text.replaceAll("powered.*?shrek.wang</a>", "");
		response.getWriter().write(text);
	}

	@Override
	public void destroy() {
	}
}
