package com.xiongyayun.athena.core.configuration;

import com.xiongyayun.athena.core.constant.CommonConstant;
import com.xiongyayun.athena.core.utils.SpringContextUtil;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
		} else if (StringUtils.hasLength(originHeader)) {
			res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, originHeader);
		}
		// 设置访问源请求头
		List<String> allowHeaders = new ArrayList<>();
		allowHeaders.add(HttpHeaders.ORIGIN);
		allowHeaders.add("X-Requested-With");
		allowHeaders.add(HttpHeaders.CONTENT_TYPE);
		allowHeaders.add(HttpHeaders.ACCEPT);
		allowHeaders.add(HttpHeaders.AUTHORIZATION);
		allowHeaders.add(CommonConstant.ACCESS_TOKEN);
		res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, String.join(",", allowHeaders));
		// 跨域session共享
		res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString());
		// 设置访问源请求方法
		res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.joining(",")));
		// 设置返回请求头
		List<String> exposeHeaders = new ArrayList<>();
		exposeHeaders.add(HttpHeaders.CACHE_CONTROL);
		exposeHeaders.add(HttpHeaders.CONTENT_LANGUAGE);
		exposeHeaders.add(HttpHeaders.CONTENT_TYPE);
		exposeHeaders.add(HttpHeaders.CONTENT_RANGE);
		exposeHeaders.add(HttpHeaders.CONTENT_LENGTH);
		exposeHeaders.add(HttpHeaders.CONTENT_ENCODING);
		exposeHeaders.add(HttpHeaders.CONTENT_DISPOSITION);
		exposeHeaders.add(HttpHeaders.LAST_MODIFIED);
		exposeHeaders.add(HttpHeaders.PRAGMA);
		exposeHeaders.add(CommonConstant.ACCESS_TOKEN);
		exposeHeaders.add(CommonConstant.REFRESH_TOKEN);
		res.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, String.join(",", exposeHeaders));
		// 授权的时间
		res.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
		if (HttpMethod.OPTIONS.matches(req.getMethod())) {
			res.getWriter().println(HttpStatus.OK.name());
			return;
		}
		super.doFilter(req, res, chain);
	}
}
