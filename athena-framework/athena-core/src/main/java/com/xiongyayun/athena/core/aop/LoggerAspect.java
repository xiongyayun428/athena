package com.xiongyayun.athena.core.aop;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiongyayun.athena.core.annotation.Logger;
import com.xiongyayun.athena.core.utils.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志切面
 *
 * @author: Yayun.Xiong
 * @date 2019-05-26
 */
@Aspect
@Component
@Slf4j
public class LoggerAspect {
	private ObjectMapper mapper = new ObjectMapper();
    private boolean limitLength = false;
    private int limit = 1024;

    private static ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> uuidThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<com.xiongyayun.athena.core.model.support.Logger> loggerThreadLocal = new ThreadLocal<>();

	@Resource
	private ObjectMapper defaultObjectMapper;

    public LoggerAspect() {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.xiongyayun.athena.core.annotation.Logger)")
    public void doAspect() {}

    /**
     * 前置通知（Before advice） ：在某连接点（JoinPoint）——核心代码（类或者方法）之前执行的通知，但这个通知不能阻止连接点前的执行。
     * @param point
     */
    @Before("doAspect()")
    public void before(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
		String[] parameterNames = signature.getParameterNames();
		Object[] parameterValues = point.getArgs();
        Logger annotation = method.getAnnotation(Logger.class);
        if (annotation == null) {
        	return;
		}
		startTimeThreadLocal.set(System.currentTimeMillis());
		String uuid = IdUtil.simpleUUID();
		uuidThreadLocal.set(uuid);
		Map<String, Object> args = new HashMap<>(parameterNames.length);
		if (parameterNames != null && parameterNames.length > 0) {
			for (int i = 0; i < parameterNames.length; i++) {
				args.put(parameterNames[i], parameterValues[i]);
			}
		}
		String params;
		try {
			params = mapper.writeValueAsString(args);
		} catch (JsonProcessingException e) {
			params = Arrays.toString(point.getArgs());
			log.error("序列化请求参数异常", e);
		}
		if (annotation.save()) {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
//            SystemUtil.logRequest(request);

			com.xiongyayun.athena.core.model.support.Logger logger = new com.xiongyayun.athena.core.model.support.Logger();
//			logger.setId(IdUtil.);
			logger.setAnnotation(String.join(",", Arrays.asList(annotation.value())));
			logger.setHttpMethod(request.getMethod());
			logger.setClassMethod(signature.getDeclaringTypeName() + "." + signature.getName());
			String queryString = request.getQueryString();
			String queryClause = (StringUtils.hasLength(queryString) ? "?" + queryString : "");
			logger.setUrl(request.getRequestURL().toString() + queryClause);
			logger.setIp(SystemUtil.getClientIP(request));
//			loggerThreadLocal.get().setArgs(JSONUtil.toJsonStr(point.getArgs()));
			logger.setRequestBody(params);
//        loggerThreadLocal.get().setLogType(AppConstants.LOG_TYPE_HTTP);
//			String params = request.getParameterMap().entrySet().stream()
//					.map(entry -> entry.getKey() + ":" + Arrays.toString(entry.getValue()))
//					.collect(Collectors.joining(", "));
//			System.out.println(params);
//			loggerThreadLocal.get().setReqParams(params);
			loggerThreadLocal.set(logger);
		}
		log.info("[{}] {} 请求参数：{}", String.join(",", Arrays.asList(annotation.value())), uuid, params);
    }

    /**
     * 返回后通知（After return advice） ：在某连接点正常完成后执行的通知，不包括抛出异常的情况。
     * @param resultValue
     */
    @AfterReturning(returning = "resultValue", pointcut = "doAspect()")
    public void afterReturning(JoinPoint point, Object resultValue) {
		long spendTime = System.currentTimeMillis() - startTimeThreadLocal.get();
		String uuid = uuidThreadLocal.get();
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Logger annotation = method.getAnnotation(Logger.class);
		String annotationValue = String.join(",", Arrays.asList(annotation.value()));
        if (null == resultValue) {
			log.trace("[{}] {} 耗时：{}ms，未返回数据", annotationValue, uuid, spendTime);
            return;
        }
        String value = resultValue.toString();
        try {
            value = defaultObjectMapper.writeValueAsString(resultValue);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        String truncatedValue = (limitLength && value.length() > limit ? value.substring(0, limit) + " (truncated)..." : value);

        if (annotation.save()) {
            loggerThreadLocal.get().setSpendTime(spendTime);
			loggerThreadLocal.get().setResponseBody(truncatedValue);
            // TODO 保存
//            System.out.println(loggerThreadLocal.get());
        }
        log.info("[{}] {} 耗时: {}ms", annotationValue, uuid, spendTime);
        if (log.isDebugEnabled()) {
			System.err.println(uuid + "=====>" + annotationValue + "<=====" + System.lineSeparator() +
					truncatedValue + System.lineSeparator() + uuid + "=====>" + annotationValue + "<=====");
		}
    }

	/**
	 * 当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）
	 * @param joinPoint
	 */
	@After("doAspect()")
	public void after(JoinPoint joinPoint) {
		log.info("已经记录下操作日志@After 方法执行后-->" + joinPoint.getSignature().getName());

		clear();
	}

    /**
     * 抛出异常后通知（After throwing advice） ： 在方法抛出异常退出时执行的通知。
     * @param e
     */
    @AfterThrowing(throwing="e", pointcut = "doAspect()")
    public void afterThrowing(Throwable e) {
        Throwable cause = lastThrowable(e);
        log.error("===>执行异常：{}", cause.getMessage() );
    }

    private Throwable lastThrowable(Throwable e) {
        Throwable cause = e.getCause();
        if (cause != null) {
            return lastThrowable(cause);
        }
        return e;
    }

    private void clear() {
		loggerThreadLocal.remove();
		uuidThreadLocal.remove();
		startTimeThreadLocal.remove();
	}

}
