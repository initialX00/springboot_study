package com.korit.springboot_study.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimerAspect {

    @Pointcut("execution(* com.korit.springboot_study.service.PostService.getPostById(..))")
    private void executionPointCut() {} //위치로 포인트컷 지정

    @Pointcut("@annotation(com.korit.springboot_study.aspect.annotation.TimerAop)")
    private void annotationPointCut() {} //어노테이션으로 포인트컷 지정

    @Around("executionPointCut()||annotationPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("전처리");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        System.out.println("후처리");
        stopWatch.stop();
        System.out.println("메소드 실행시간: " + stopWatch.getTotalTimeMillis());
        return result;
    }
}
