package com.xyy.athena.core.aop;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyy.athena.core.annotation.Logger;
import com.xyy.athena.core.utils.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
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
 * @author Yayun.Xiong
 * @date 2019-05-26
 */
@Aspect
@Component
@Slf4j
public class LoggerAspect {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    private boolean limitLength = true;
    private int limit = 300;
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    ThreadLocal<com.xyy.athena.core.model.support.Logger> loggerThreadLocal = new ThreadLocal<>();

    @Pointcut("@annotation(com.xyy.athena.core.annotation.Logger)")
    public void doAspect() {}

    @Before("doAspect()")
    public void around(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Logger annotation = method.getAnnotation(Logger.class);
        if (annotation != null) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
//            SystemUtil.logRequest(request);
            if (!annotation.save()) {
                startTime.set(System.currentTimeMillis());
                loggerThreadLocal.set(new com.xyy.athena.core.model.support.Logger());
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
            logger.info("===>【{}】请求参数：{}", String.join(",", Arrays.asList(annotation.value())), Arrays.toString(point.getArgs()));
        }
    }

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
            logger.error(e.getMessage(), e);
        }
        String truncatedValue = (limitLength && value.length() > limit ? value.substring(0, limit) + " (truncated)..." : value);

        if (null != loggerThreadLocal.get()) {
            loggerThreadLocal.get().setSpendTime(System.currentTimeMillis() - startTime.get());
            loggerThreadLocal.get().setRespParams(truncatedValue);
            // TODO 保存
            System.out.println(loggerThreadLocal.get());
        }

        if (logger.isDebugEnabled()) {
            logger.debug("===>返回数据：{}", truncatedValue);
        } else if (logger.isInfoEnabled()) {
            logger.info("===>返回数据：{}", truncatedValue);
        }
    }

    @After("doAspect()")
    public void after(JoinPoint joinPoint) {
        log.info("已经记录下操作日志@After 方法执行后-->" + joinPoint.getSignature().getName());

    }

}
