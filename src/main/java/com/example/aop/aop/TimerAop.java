package com.example.aop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component // bean은 클래스 위에 붙일 수 없음, 클래스에는 @Component 붙여서 컴포넌트 단위로 스프링에 등록
public class TimerAop {

    @Pointcut("execution(* com.example.aop.controller..*.*(..))")
    private void cut(){}

    @Pointcut("@annotation(com.example.aop.annotation.Timer)") // timer 제약
    private void enableTimer(){} // logging 처리

    @Around("cut() && enableTimer()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable{ // 이걸 작성함으로써, RestApiController에 정의된 메서드안에 StopWatch를 반복적으로 코딩, 생성하지 않아도 됨
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed(); // 실행전
        stopWatch.stop();
        System.out.println("total time : " + stopWatch.getTotalTimeSeconds()); //method 실행시간 측정


    }
}
