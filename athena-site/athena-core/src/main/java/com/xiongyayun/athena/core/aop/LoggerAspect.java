package com.xiongyayun.athena.core.aop;

import cn.hutool.json.JSONUtil;
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

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

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
    private boolean limitLength = true;
    private int limit = 300;
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    ThreadLocal<com.xiongyayun.athena.core.model.support.Logger> loggerThreadLocal = new ThreadLocal<>();

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
        Logger annotation = method.getAnnotation(Logger.class);
        if (annotation != null) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
//            SystemUtil.logRequest(request);
            if (annotation.save()) {
                startTime.set(System.currentTimeMillis());
                loggerThreadLocal.set(new com.xiongyayun.athena.core.model.support.Logger());
                loggerThreadLocal.get().setHttpMethod(request.getMethod());
                loggerThreadLocal.get().setClassMethod(signature.getDeclaringTypeName() + "." + signature.getName());
                String queryString = request.getQueryString();
                String queryClause = (StringUtils.hasLength(queryString) ? "?" + queryString : "");

                loggerThreadLocal.get().setUrl(request.getRequestURL().toString() + queryClause);
                loggerThreadLocal.get().setIp(SystemUtil.getClientIP(request));
                loggerThreadLocal.get().setArgs(JSONUtil.toJsonStr(point.getArgs()));
//        loggerThreadLocal.get().setLogType(AppConstants.LOG_TYPE_HTTP);
                String params = request.getParameterMap().entrySet().stream()
                        .map(entry -> entry.getKey() + ":" + Arrays.toString(entry.getValue()))
                        .collect(Collectors.joining(", "));
                loggerThreadLocal.get().setReqParams(params);
            }
            log.info("===>【{}】请求参数：{}", String.join(",", Arrays.asList(annotation.value())), Arrays.toString(point.getArgs()));
        }
    }

    /**
     * 当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）
     * @param joinPoint
     */
    @After("doAspect()")
    public void after(JoinPoint joinPoint) {
        log.info("已经记录下操作日志@After 方法执行后-->" + joinPoint.getSignature().getName());

    }

    /**
     * 返回后通知（After return advice） ：在某连接点正常完成后执行的通知，不包括抛出异常的情况。
     * @param resultValue
     */
    @AfterReturning(returning = "resultValue", pointcut = "doAspect()")
    public void afterReturning(Object resultValue) {
        if (null == resultValue) {
            log.info("未返回数据");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        String value = resultValue.toString();
        try {
            value = mapper.writeValueAsString(resultValue);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        String truncatedValue = (limitLength && value.length() > limit ? value.substring(0, limit) + " (truncated)..." : value);

        if (null != loggerThreadLocal.get()) {
            loggerThreadLocal.get().setSpendTime(System.currentTimeMillis() - startTime.get());
            loggerThreadLocal.get().setRespParams(truncatedValue);
            // TODO 保存
//            System.out.println(loggerThreadLocal.get());
        }
        log.info("===>返回数据：{}", truncatedValue);
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

}
