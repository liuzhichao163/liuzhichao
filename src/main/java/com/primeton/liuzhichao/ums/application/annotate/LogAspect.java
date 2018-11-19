package com.primeton.liuzhichao.ums.application.annotate;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.primeton.liuzhichao.ums.application.entity.ResultEnum;
import com.primeton.liuzhichao.ums.application.exception.DemoException;

import io.swagger.models.auth.In;
/**
 * 登录拦截处理
 * @author ASUS
 *
 */
@Component
@Aspect
public class LogAspect {
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("@annotation(log)")
    public void beforeTest(JoinPoint joinPoint, LogAop log) throws Throwable {
    	logger.info("进入：" + log.name());
    	logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        logger.info("参数 : " + Arrays.toString(joinPoint.getArgs()));
        //获取RequestAttributes  
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        if(null == session.getAttribute("id")) {
        	throw new DemoException(ResultEnum.ERROR_USER_LOGIN);
        }
    }

    @After("@annotation(log)")
    public void afterTest(JoinPoint point, LogAop log) {
    	logger.info("退出：" + log.name());
        System.out.println("退出：" + log.name());
    }
}


