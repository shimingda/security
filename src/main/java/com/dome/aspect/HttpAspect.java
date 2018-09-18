package com.dome.aspect;

import com.dome.redis.CacheUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
/**
 * 预防暴力访问和记录url访问信息
 *
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);
    Map<String,LinkedList<Long>> apiMap=new HashMap<>();
    LinkedList<Long> timeList=null;
    @Pointcut("execution(public * com.dome.controller.*.*(..))")
    public void log() {
        System.out.println(123);
    }
    //todo
    //现在存放在session中，应该放在redis
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        String sessionId=request.getSession().getId();

        StringBuffer url =request.getRequestURL();
        url.append(request.getMethod()).append(sessionId);

        timeList= CacheUtils.getBean(url.toString(),LinkedList.class);
        if(timeList==null||timeList.isEmpty()){
            timeList=new LinkedList<>();
        }
        Long createTime = System.currentTimeMillis();
        timeList.add(createTime);

        int count=timeList.size();
        if(count>10){
            Long lastTime=timeList.getLast();
            Long last2Time=timeList.get(count-1);
            System.out.println(lastTime);
            System.out.println(last2Time);
            if(lastTime-last2Time<3000){
                Long firstTime=timeList.getFirst();
                System.out.println(firstTime);
                if(lastTime-firstTime<30000){
                    throw new Exception("调用太过频繁");
                }
            }
        }
        logger.info("url:{}访问次数为count={}}", request.getRequestURL(), count);
        //url method ip
        logger.info("利用AOP记录每次请求的有关信息，url={}，method={}，ip={}", request.getRequestURL(), request.getMethod(), request.getRemoteAddr());
        //类方法 参数
        logger.info("class_method={}, args={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(), joinPoint.getArgs());
        CacheUtils.saveBean(url.toString(),timeList);
    }

    @After("log()")
    public void doAfter() {

    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {

    }

}
