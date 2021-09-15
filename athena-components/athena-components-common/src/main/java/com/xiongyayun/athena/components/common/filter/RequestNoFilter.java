package com.xiongyayun.athena.components.common.filter;

import cn.hutool.core.util.IdUtil;
import com.xiongyayun.athena.components.common.constant.CommonConstant;
import com.xiongyayun.athena.components.common.context.RequestNoContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对请求生成唯一编码
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/21
 */
public class RequestNoFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			// 生成唯一请求号uuid
			String requestNo = IdUtil.simpleUUID();;

			// 增加响应头的请求号
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			httpServletResponse.addHeader(CommonConstant.REQUEST_NO_HEADER_NAME, requestNo);

			// 临时存储
			RequestNoContext.set(requestNo);

			// 放开请求
			chain.doFilter(request, response);

		} finally {
			// 清除临时存储的唯一编号
			RequestNoContext.clear();
		}
	}

	@Override
	public void destroy() {
	}
}
