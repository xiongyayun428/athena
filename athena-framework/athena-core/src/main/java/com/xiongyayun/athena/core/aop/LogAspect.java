package com.xiongyayun.athena.core.aop;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.xiongyayun.athena.core.annotation.Log;
import com.xiongyayun.athena.core.model.support.Journal;
import com.xiongyayun.athena.core.utils.SystemUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;
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
 * @author Yayun.Xiong
 * @date 2019-05-26
 */
@Aspect
@Component
public class LogAspect {
	private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
    private static final boolean LIMIT_LENGTH = false;
    private static final int LIMIT = 1024;

    private static final ThreadLocal<Long> START_TIME_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<String> UUID_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<Journal> LOGGER_THREAD_LOCAL = new ThreadLocal<>();


	private ObjectMapper indentOutputObjectMapper;
	private ObjectMapper objectMapper;

	@Resource
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper.copy();
		this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// without 禁用
//		this.objectMapper.setConfig(objectMapper.getSerializationConfig().without(SerializationFeature.INDENT_OUTPUT));
		this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);

		this.indentOutputObjectMapper = objectMapper.copy();
		this.indentOutputObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		// with 启用
//		this.indentOutputObjectMapper.setConfig(objectMapper.getSerializationConfig().with(SerializationFeature.INDENT_OUTPUT));
	}

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.xiongyayun.athena.core.annotation.Log)")
    public void doAspect() {}

    /**
     * 前置通知（Before advice） ：在某连接点（JoinPoint）——核心代码（类或者方法）之前执行的通知，但这个通知不能阻止连接点前的执行。
     * @param point 连接点
     */
    @Before("doAspect()")
    public void before(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
		String[] parameterNames = signature.getParameterNames();
		Object[] parameterValues = point.getArgs();
        Log annotation = method.getAnnotation(Log.class);
        if (annotation == null) {
        	return;
		}
		START_TIME_THREAD_LOCAL.set(System.currentTimeMillis());
		String uuid = IdUtil.simpleUUID();
		UUID_THREAD_LOCAL.set(uuid);
		Map<String, Object> args = new HashMap<>(parameterNames.length);
		if (parameterNames.length > 0) {
			for (int i = 0; i < parameterNames.length; i++) {
				args.put(parameterNames[i], parameterValues[i]);
			}
		}
		String params;
		try {
			// TODO 这里还存在问题
			params = objectMapper.writeValueAsString(args);
		} catch (JsonProcessingException e) {
			params = Arrays.toString(point.getArgs());
			LOG.error("序列化请求参数异常", e);
		}
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		Journal journal = new Journal();
		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
//            SystemUtil.logRequest(request);
//			logger.setId(IdUtil.);
			journal.setAnnotation(String.join(",", Arrays.asList(annotation.value())));
			journal.setHttpMethod(request.getMethod());
			journal.setClassMethod(signature.getDeclaringTypeName() + "." + signature.getName());
			String queryString = request.getQueryString();
			String queryClause = (StringUtils.hasLength(queryString) ? "?" + queryString : "");
			journal.setUrl(request.getRequestURL().toString() + queryClause);
			journal.setIp(SystemUtil.getClientIP(request));
//			LOGGER_THREAD_LOCAL.get().setArgs(JSONUtil.toJsonStr(point.getArgs()));
			journal.setRequestBody(params);
//        LOGGER_THREAD_LOCAL.get().setLogType(AppConstants.LOG_TYPE_HTTP);
//			String params = request.getParameterMap().entrySet().stream()
//					.map(entry -> entry.getKey() + ":" + Arrays.toString(entry.getValue()))
//					.collect(Collectors.joining(", "));
//			System.out.println(params);
//			LOGGER_THREAD_LOCAL.get().setReqParams(params);
			LOGGER_THREAD_LOCAL.set(journal);
		}
		if (annotation.save()) {

		}
		LOG.info("{} [{}] {} 请求参数：{}", journal.getUrl(), String.join(",", Arrays.asList(annotation.value())), uuid, params);
    }

    /**
     * 返回后通知（After return advice） ：在某连接点正常完成后执行的通知，不包括抛出异常的情况。
     * @param resultValue	值
     */
    @AfterReturning(returning = "resultValue", pointcut = "doAspect()")
    public void afterReturning(JoinPoint point, Object resultValue) {
		long spendTime = System.currentTimeMillis() - START_TIME_THREAD_LOCAL.get();
		String uuid = UUID_THREAD_LOCAL.get();
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Log annotation = method.getAnnotation(Log.class);
		String annotationValue = String.join(",", Arrays.asList(annotation.value()));
        if (null == resultValue) {
			LOG.info("{} [{}] {} 耗时：{}ms，未返回数据", LOGGER_THREAD_LOCAL.get().getUrl(), annotationValue, uuid, spendTime);
            return;
        }
        String value = resultValue.toString();
        try {
            value = indentOutputObjectMapper.writeValueAsString(resultValue);
        } catch (JsonProcessingException e) {
			LOG.error(e.getMessage(), e);
        }
        String truncatedValue = (LIMIT_LENGTH && value.length() > LIMIT ? value.substring(0, LIMIT) + " (truncated)..." : value);

        if (annotation.save()) {
            LOGGER_THREAD_LOCAL.get().setSpendTime(spendTime);
			LOGGER_THREAD_LOCAL.get().setResponseBody(truncatedValue);
            // TODO 保存
//            System.out.println(LOGGER_THREAD_LOCAL.get());
        }
		LOG.info("[{}] {} 耗时: {}ms", annotationValue, uuid, spendTime);
        if (LOG.isTraceEnabled()) {
			System.err.println(uuid + "=====>" + annotationValue + "<=====" + System.lineSeparator() +
					truncatedValue + System.lineSeparator() + uuid + "=====>" + annotationValue + "<=====");
		}
    }

	/**
	 * 当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）
	 * @param joinPoint	连接点
	 */
	@After("doAspect()")
	public void after(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
//		LOG.info("已经记录下操作日志@After 方法执行后-->" + signature.getDeclaringTypeName() + "." + signature.getName());

		clear();
	}

    /**
     * 抛出异常后通知（After throwing advice） ： 在方法抛出异常退出时执行的通知。
     * @param e	异常
     */
    @AfterThrowing(throwing="e", pointcut = "doAspect()")
    public void afterThrowing(Throwable e) {
        Throwable cause = lastThrowable(e);
		LOG.error("===>执行异常：{}", cause.getMessage() );
    }

    private Throwable lastThrowable(Throwable e) {
        Throwable cause = e.getCause();
        if (cause != null) {
            return lastThrowable(cause);
        }
        return e;
    }

    private void clear() {
		LOGGER_THREAD_LOCAL.remove();
		UUID_THREAD_LOCAL.remove();
		START_TIME_THREAD_LOCAL.remove();
	}

}
