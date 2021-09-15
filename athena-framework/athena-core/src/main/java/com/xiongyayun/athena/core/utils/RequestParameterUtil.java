package com.xiongyayun.athena.core.utils;

import com.xiongyayun.athena.components.common.exception.AthenaException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestUtil
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/1/18
 */
public class RequestParameterUtil {
	/**
	 * 获取整型非空参数，类型不匹配或参数为空抛异常
	 */
	public static Integer getIntegerParamNotNull(String parameter, String name) throws AthenaException {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			Integer result = ServletRequestUtils.getIntParameter(request, parameter);
			if (null == result) {
				throw new AthenaException("输入参数[" + name + "] 不能为空");
			}
			return result;
		} catch (ServletRequestBindingException e) {
			throw new AthenaException("参数[" + name + "]传入值错误");
		}
	}
}
