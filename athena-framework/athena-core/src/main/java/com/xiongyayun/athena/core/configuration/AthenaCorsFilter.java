package com.xiongyayun.athena.core.configuration;

import com.xiongyayun.athena.core.utils.SpringContextUtil;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * AthenaCorsFilter
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/11/16
 */
@Order
@Component
public class AthenaCorsFilter extends HttpFilter {
	private static final long serialVersionUID = -8387103310559517243L;

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
		String allowOrigin = SpringContextUtil.getProperty("app.cors.allow-origin");
		String originHeader = req.getHeader(HttpHeaders.ORIGIN);
		// 设置访问源地址，不能使用*
		if (StringUtils.hasLength(allowOrigin)) {
			Set<String> allowOriginSet = new HashSet<>(Arrays.asList(allowOrigin.split(",")));
			if (allowOriginSet.contains(originHeader)) {
				res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, allowOrigin);
			}
		} else {
			res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, originHeader);
		}
		// 设置访问源请求头
		res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, x-requested-with, Content-Type, Accept, Authorization");
		// 跨域session共享
		res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		// 设置访问源请求方法
		res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, OPTIONS, DELETE");
		// 设置返回请求头
		res.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Cache-Control, Content-Language, Content-Type, Expires, Last-Modified, Pragma");
		// 授权的时间
		res.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
		if ("OPTIONS".equals(req.getMethod())) {
			res.getWriter().println("ok");
			return;
		}
		super.doFilter(req, res, chain);
	}
}
